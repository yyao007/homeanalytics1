package indeedplace;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class indeedplace {
	public static int quota = 0;
	public static String addr_status = null;
	protected static LinkedList<String> keys = new LinkedList<String>();
	protected static Map<String, String> proxies = new HashMap<String, String>();
	protected static int keysUsed = 0;
	protected static int numLimitFail = 0;

	public static void readKeysAndProxies(String filename) {
		keys.clear();
		proxies.clear();
		try {
			Scanner scanner = new Scanner(new File(filename));

			while (scanner.hasNextLine()) {
				String[] line = scanner.nextLine().split(",");

				keys.add(line[0]);
				proxies.put(line[0], line[1]);
			}

			scanner.close();

			if (keys.size() == 0 || keys.size() != proxies.size()) {
				System.out.println("File " + filename + " has no keys or isn't properly formatted.");
				System.exit(1);
			}

			// Configuration for using the first key's proxy.
			System.setProperty("http.proxyHost", proxies.get(keys.getFirst()));
			System.setProperty("http.proxyPort", "3128");
		} catch (FileNotFoundException e) {
			System.out.println("File " + filename + " not found.");
			System.exit(1);
		}
	}

	protected static void switchKey() throws Exception {
		keysUsed++;

		// If all of the keys have reached their usage limits, sleep until
		// midnight.
		if (keysUsed == keys.size()) {
			try {
				System.out.println("Query limit reached; sleeping.");

				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

				String time1 = sdf.format(cal.getTime());
				String parts[] = time1.split(":");
				long hrs = Long.parseLong(parts[0]);
				 long min=Long.parseLong(parts[1]);
				 long sec=Long.parseLong(parts[2]);
				long wait = (24 - hrs) * 60 * 60 * 1000;
				System.out.println(wait / 3600000);
				Thread.sleep(wait);
				Thread.sleep(86500000-(hrs*min*sec*1000));
			} catch (InterruptedException e) {
				System.out.println("Sleep interrupted.");
			}

			keysUsed = 0;
		}

		keys.add(keys.removeFirst());
		System.setProperty("http.proxyHost", proxies.get(keys.getFirst()));
		System.out.println(proxies.get(keys.getFirst()));
		System.out.println("*****************************************************************");
	}

	public static JSONObject getGeoResponse(String address) {
		String strJSON = "";

		try {
			int responseCode = 0;

			String strURL = "https://maps.googleapis.com/maps/api/place/textsearch/json?query="
					+ URLEncoder.encode(address, "UTF-8") + "&key=" + keys.getFirst();
			quota++;
			System.out.printf("quota %d", quota);
			System.out.println(" ");

			URL url = new URL(strURL);
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.connect();
			responseCode = httpConnection.getResponseCode();
			if (responseCode == 200) {
				// Read response result from the httpConnection via the
				// bufferedReader
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(httpConnection.getInputStream()));
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					strJSON += line;
				}
				bufferedReader.close();

				// Check if the response object is valid
				JSONObject jsonObj = new JSONObject(strJSON);
				String status = (String) jsonObj.get("status");

				if (status.equals("OK")) {
					addr_status = status;
					numLimitFail = 0;
					return jsonObj;
				} else if (status.equals("OVER_QUERY_LIMIT")) {
					System.out.println("Geocoding API error - response status: " + status);
					if (numLimitFail < 10) // Access too frequently per second
					{
						numLimitFail += 1;
						Thread.sleep(1000);
						return getGeoResponse(address);
					} else // Over 2500 limit per day
					{
						numLimitFail = 0;
						switchKey();
					
						return getGeoResponse(address);
					}
				} else {
					addr_status = status;
					System.out.println("Geocoding API error - response status: " + status);
					numLimitFail = 0;
					return null;
				}
			} else {
				System.out.println("Geocoding API connection fails.");
				numLimitFail = 0;
				return null;
			}
		} catch (Exception ex) {
			// System.out.println("Geocoding API error occurs.");
			return null;
		}
	}

	public static double[] getCoordinate(JSONObject responseJSONObj) throws Exception {

		if (responseJSONObj == null) {
			System.out.println("Geocoding API error occurs.");
			return null;
		}

		JSONArray jsonArray = (JSONArray) responseJSONObj.get("results");
		JSONObject jsonObject = (JSONObject) jsonArray.get(0);
		JSONObject geometryObj = (JSONObject) jsonObject.get("geometry");
		JSONObject locationObj = (JSONObject) geometryObj.get("location");
		double latitude = Double.parseDouble(locationObj.get("lat").toString());
		double longitude = Double.parseDouble(locationObj.get("lng").toString());

		return new double[] { latitude, longitude };
	}

	public static String getStandardAddress(JSONObject responseJSONObj) throws Exception {
		if (responseJSONObj == null) {
			System.out.println("Geocoding API error occurs.");
			return null;
		}

		JSONArray jsonArray = (JSONArray) responseJSONObj.get("results");
		JSONObject jsonObject = (JSONObject) jsonArray.get(0);
		String strAddr = jsonObject.get("formatted_address").toString();
		return strAddr;
	}

	public static void main(String[] args) throws Exception {
		readKeysAndProxies("googleplacesapi.txt");

		int size = 0;

		String address = null;
		String city = null;
		String state = null;
		String company = null;
		double latitude = 0.0;
		double longitude = 0.0;

		Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost/business?autoReconnect=true", "root", "home123");

		myConn.setAutoCommit(false);

		Statement myStmt = myConn.createStatement();

		ResultSet myRs = myStmt.executeQuery(
				"select distinct city,state,company from companies where address is null and addr_status is null limit 100000");

		while (myRs.next()) {

			latitude = 0.0;
			longitude = 0.0;
			size = 0;
			address = null;
			addr_status = null;
			city = myRs.getString("city").trim();

			state = myRs.getString("state").trim();

			company = myRs.getString("company").trim();
			company = company.replace("'", "\\'");
			try {

				String addr = company + "," + city + "," + state;
				System.out.printf("company %s ",company);
				System.out.printf(" city %s ",city);
				System.out.printf("state %s ",state);
				JSONObject response = getGeoResponse(addr);

				if (response != null) {

					JSONArray jsonArray = (JSONArray) response.get("results");
					size = (int) jsonArray.length();
					//System.out.print(size);
					//System.out.print("****** ");
					//System.out.println(company);
					
					if (size == 1) {
						double latLongs[] = getCoordinate(response);
						if (latLongs != null) {
							latitude = latLongs[0];
							longitude = latLongs[1];

						} 
						address = getStandardAddress(response);

					}else {
						addr_status = "More than one";
					}
				}
			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			} catch (JSONException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			} catch (Exception e) {

				e.printStackTrace();
			}

			String query2 = "UPDATE `business`.`companies` SET address=?, latitude=? , longitude=? , addr_status=? where city='"
					+ city + "' and state='" + state + "' and company='" + company + "'";
			PreparedStatement ps2 = myConn.prepareStatement(query2);
			ps2.setString(1, address);
			ps2.setDouble(2, latitude);
			ps2.setDouble(3, longitude);
			ps2.setString(4, addr_status);
			ps2.executeUpdate();
			myConn.commit();
			ps2.close();

		}
		myConn.close();
	}
}