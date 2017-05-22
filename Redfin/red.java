import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class red {
	public static int counter = 0;

	public static void main(String[] args) {
// 8  and 10 for counter less than 60000
		// 7 and 9 for counter less between 60000 and 120000
		// 5 and 6 for counter greater than 120

		if (args.length == 0) {
			System.out.println("Usage: java -jar redfin.jar d");
			java.lang.System.exit(0);
		}
		
		String n = args[0];
		String[] PROXY = new String[2];
		String range = null;
		int count1 = 40;
		int count2 = 20;
		int count_sleep = 30;
		int delay = 10;
		if (n.equals("d")) {
			PROXY[0] = "http://127.0.0.1:8123";
			PROXY[1] = "http://127.0.0.1:8123";
			range = "id >= 1";
			count1 = 150;
			count2 = 100;
			count_sleep = 100;
			delay = 3;
		}
		if (n.equals("1")) {
			PROXY[0] = "d12.cs.ucr.edu:3128";
			PROXY[1] = "d11.cs.ucr.edu:3128";
			range = "id < 60000";
		}
		else if (n.equals("2")) {
			PROXY[0] = "d04.cs.ucr.edu:3128";
			PROXY[1] = "d03.cs.ucr.edu:3128";
			range = "id >= 60000 and id < 120000";
		}
		else if (n.equals("3")) {
			PROXY[0] = "d06.cs.ucr.edu:3128";
			PROXY[1] = "d05.cs.ucr.edu:3128";
			range = "id >= 120000";
		}
		
		org.openqa.selenium.Proxy proxy = null;
		DesiredCapabilities cap = null;
		WebDriver driver = null;

		proxy = new org.openqa.selenium.Proxy();
		proxy.setHttpProxy(PROXY[0]).setFtpProxy(PROXY[0]).setSslProxy(PROXY[0]);
		cap = new DesiredCapabilities();
		cap.setCapability(CapabilityType.PROXY, proxy);
		driver = new FirefoxDriver(cap);
		

		 //WebDriver driver = new FirefoxDriver();
		 
		 
		String prefix = "http://webcache.googleusercontent.com/search?q=cache:";
		String suffix = "&num=1&strip=1&vwsrc=0";

		java.sql.Timestamp timeStampDate = null;
		String cache_time = null;

		long waitDuration = 0;

		// initialize first element

		Connection myConn;
		try {
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/homeDB", "root", "home123");

			myConn.setAutoCommit(false);
			int zipcode = 0;
			Statement myStmt = myConn.createStatement();

			ResultSet myRs2 = null;

			int Redfin_Estimate = 0;
			Statement myStmt2 = myConn.createStatement();
			WebElement ele = null;
			while (counter < 20000) {

				if (counter % count1 == 0) {
					driver.close();
					proxy = new org.openqa.selenium.Proxy();
					proxy.setHttpProxy(PROXY[0]).setFtpProxy(PROXY[0]).setSslProxy(PROXY[0]);
					cap = new DesiredCapabilities();
					cap.setCapability(CapabilityType.PROXY, proxy);
					System.out.println(PROXY);
					System.out.println("****************************************");
					driver = new FirefoxDriver(cap);

				} else if (counter % count2 == 0) {

					driver.close();
					proxy = new org.openqa.selenium.Proxy();
					proxy.setHttpProxy(PROXY[1]).setFtpProxy(PROXY[1]).setSslProxy(PROXY[1]);
					cap = new DesiredCapabilities();
					cap.setCapability(CapabilityType.PROXY, proxy);
					System.out.println(PROXY);
					System.out.println("****************************************");
					driver = new FirefoxDriver(cap);
				}
				

				Double r = 183553 * Math.random() + 1;

				counter++;

				ResultSet rs = myStmt
						.executeQuery("select zip from z2 where cumulative_sum< " + r + "order by id desc limit 1");

				if (rs.next()) {
					zipcode = rs.getInt(1);

				}

				//

				// WebDriver driver = new FirefoxDriver();
				
				myRs2 = myStmt2.executeQuery("select id , url from redfin_estimates where " + range + " and zip="
						+ zipcode + " limit 1");

				if (myRs2.next()) {

					String urll = myRs2.getString("url").trim();
					String[] uu = urll.split("http");

					String uuu = "https" + uu[1];

					String u = prefix + uuu + suffix;

					int id = myRs2.getInt("id");

					if (u.isEmpty()) {
						Redfin_Estimate = 0;
					} else {
						driver.get(u);
						List<WebElement> elems = driver.findElements(By.className("avmValue"));
						if (elems.size() == 0) {
							Redfin_Estimate = 0;
							// System.out.printf("zerooo id %d", id);
						} else {
							ele = driver.findElement(By.className("avmValue"));
							if (ele.getText().isEmpty()) {
								Redfin_Estimate = 0;

							}

							else {

								WebElement ele2 = driver.findElement(By.id("google-cache-hdr"));
								String t = ele2.getText();

								String[] d = t.split("It is a snapshot of the page as it appeared on ");

								String[] d1 = d[1].split(" GMT");

								String[] d2 = d1[0].split("2016");

								cache_time = d2[0].trim() + " 2016";

								DateFormat formatter;
								formatter = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
								Date date;
								try {
									date = (Date) formatter.parse(cache_time);

									timeStampDate = new Timestamp(date.getTime());

								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								String ans = ele.getText();
								NumberFormat format = NumberFormat.getCurrencyInstance();
								Number number;
								try {
									number = format.parse(ans);

									String nn = number.toString();
									Redfin_Estimate = Integer.parseInt(nn);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						}
					}

					if (Redfin_Estimate != 0) {
						System.out.print("Find itttttttttttttttttttttt ");
						System.out.print(Redfin_Estimate);
						System.out.print(" id  ");
						System.out.println(id);

					} else {
						System.out.println("not found id " + id + "---counter: " + counter);
					}

					if (Redfin_Estimate != 0) {
						ResultSet myRs3 = myStmt2.executeQuery("select * from redfin_estimates where id = " + id);
						if (myRs3.next()) {
							String query2 = "insert into redfin (sale_type, property_type, address, city, state, zip,"
									+ "price,Redfin_Estimate,beds,baths,location,sqft,lot_size,year_built,"
									+ "days_on_market,status,url,source,listing_id,latitude,longitude,crawl_time) "
									+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
							PreparedStatement ps2 = myConn.prepareStatement(query2);
							ps2.setString(1, myRs3.getString("sale_type"));
							ps2.setString(2, myRs3.getString("property_type"));
							ps2.setString(3, myRs3.getString("address"));
							ps2.setString(4, myRs3.getString("city"));
							ps2.setString(5, myRs3.getString("state"));
							ps2.setInt(6, myRs3.getInt("zip"));
							ps2.setInt(7, myRs3.getInt("price"));
							ps2.setInt(8, Redfin_Estimate);
							ps2.setInt(9, myRs3.getInt("beds"));
							ps2.setInt(10, myRs3.getInt("baths"));
							ps2.setString(11, myRs3.getString("location"));
							ps2.setInt(12, myRs3.getInt("sqft"));
							ps2.setInt(13, myRs3.getInt("lot_size"));
							ps2.setInt(14, myRs3.getInt("year_built"));
							ps2.setInt(15, myRs3.getInt("days_on_market"));
							ps2.setString(16, myRs3.getString("status"));
							ps2.setString(17, myRs3.getString("url"));
							ps2.setString(18, myRs3.getString("source"));						
							ps2.setInt(19, myRs3.getInt("listing_id"));
							ps2.setDouble(20, myRs3.getDouble("latitude"));
							ps2.setDouble(21, myRs3.getDouble("longitude"));
				
							if (!cache_time.isEmpty()) {
								Timestamp t1 = timeStampDate;
								Timestamp t2 = myRs3.getTimestamp("crawl_time");
								if (t1.getYear() == t2.getYear() && t1.getMonth() == t2.getMonth() && t1.getDay() == t2.getDay()) {
									System.out.println("Nothing changed, ignoring url: " + u);
									continue;
								}
								
								ps2.setTimestamp(22, timeStampDate);
							
							}
							else {
								Date date = new Date();
								timeStampDate = new Timestamp(date.getTime());
								ps2.setTimestamp(22, timeStampDate);
							}
							
							try {
								ps2.executeUpdate();
								myStmt2.executeUpdate("insert into redfin select * from redfin_estimates where id = " + id);
								myStmt2.executeUpdate("delete from redfin_estimates where id=" + id);
								myConn.commit();
							}
							catch (Exception e){
								System.out.println("Ignore duplicate record...");
								myConn.rollback();
							}
							
							ps2.close();
						}
					} 
					else {
//						String query2 = "UPDATE redfin SET Redfin_Estimate=? where id=" + id;
//
//						PreparedStatement ps2 = myConn.prepareStatement(query2);
//						ps2.setString(1, null);
//						ps2.executeUpdate();
//
//						myConn.commit();
//						ps2.close();
						
						myStmt2.executeUpdate("delete from redfin_estimates where id=" + id);
						myConn.commit();
					}
					
					Random random = new java.util.Random();
					waitDuration = (long) ((10 * random.nextFloat() + delay) * 1000);
					if (counter % count_sleep == 0) {

						Thread.sleep(20000);
						System.out.println("20000 sleep");
					}

					Thread.sleep(waitDuration);
					System.out.print("waitDuration  ");
					System.out.print(waitDuration);
				}

			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (NoSuchElementException e) {
			System.out.println("NoSuchElementException");
		}

	}

}
