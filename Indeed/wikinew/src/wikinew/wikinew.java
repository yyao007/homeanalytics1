package wikinew;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class wikinew {

	public static void main(String[] args) {
		String comment = null;
		
		long waitDuration = 0;
		String url = null;
		String unit = null;
		String company = null;
		String type = null;
		String industry = null;
		// char currency = '\0';
		double revenue = 0.0;
		int number_of_employees = 0;
		int number_of_locations = 0;
		Document linksDoc = null;
		Document doc = null;
		Connection myConn;
		try {
			myConn = DriverManager.getConnection("jdbc:mysql://localhost/business?autoReconnect=true", "root",
					"home123");

			myConn.setAutoCommit(false);

			Statement myStmt = myConn.createStatement();

			ResultSet myRs = myStmt.executeQuery(
					"select distinct company from companies where type is null and industry is null  and number_of_employees= 0 and revenue=0.0 and comment is null");

			while (myRs.next()) {

				comment = null;
				url = null;
				unit = null;
				company = null;
				type = null;
				industry = null;
				revenue = 0.0;
				number_of_employees = 0;
				number_of_locations = 0;

				company = myRs.getString("company").trim();
				company = company.replace("'", "\\'");
				System.out.println(company);
				 url="https://en.wikipedia.org/wiki/"+company ;
			
	

				org.jsoup.Connection con = Jsoup.connect(url).userAgent(
						"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21");
				con.timeout(180000).ignoreHttpErrors(true).followRedirects(true);
				Response resp;
				try {
					resp = con.execute();

					if (resp.statusCode() == 200) {
						try {
							doc = con.get();

							if (doc.select("table[class=infobox vcard]").size() > 0) {

								Element table = doc.select("table[class=infobox vcard]").get(0);

								Elements rows = table.select("tr");

								for (int i = 1; i < rows.size(); i++) { // first
																		// row
																		// is
																		// the
																		// col
																		// names
																		// so
																		// skip
																		// it.
									Element row = rows.get(i);
									Elements header = row.select("th");

									if (header.text().equals("Type")) {
										Elements cols = row.select("td");
										String s = cols.get(0).text();
										//System.out.println(s);
										//System.out.println(s.length());
										//System.out.println(s.toCharArray().length);
										if (s.length() != s.toCharArray().length) {
											s = s.substring(0, 50);
										}
//System.out.println(type);
										type = s;
										//System.out.println(type);

									}
									if (header.text().equals("Industry")) {
										Elements cols = row.select("td");
										String s = cols.get(0).text();
										industry = s;
										industry = industry.replaceAll("[()]", "");

										industry = industry.toLowerCase();
										//System.out.println(industry);
										if (s.length() != s.toCharArray().length) {
											s = s.substring(0, 50);
											industry =s;
										}

									}

									if (header.text().equals("Revenue")) {
										
										int loc = 0;
										
										Elements cols = row.select("td");
										String s = cols.get(0).text();

										if (s.contains("billion")) {
											unit = "billion";
										} else if (s.contains("million")) {
											unit = "million";
										}

										if (s.contains("(")) {
											String[] ss = s.split("\\(");
											ss[0] = ss[0].replaceAll("[^0-9.]", "").trim();
											//System.out.println(ss[0]);
											int count=0;
											for(int k = 0; k < ss[0].length(); k++)
											{
											if(ss[0].charAt(k) == '.'){
											count+=1;
											}
											}
											if(count>1){
												 loc = ss[0].lastIndexOf('.');
											}
											ss[0]= ss[0].substring(0, loc);
											
											
											
											boolean isNumber = Pattern.matches("[0-9]+", ss[0]);
											if(isNumber) {
												revenue = Double.parseDouble(ss[0]);

											}
											
											
										
										}else{
											
											boolean isNumber = Pattern.matches("[0-9]+", s);
											if(isNumber) {
												revenue = Double.parseDouble(s);

											}
											
											
										}
										
										if(unit!=null){
											if (  unit.equals("billion")) {

												revenue = revenue * 1000000000;
											} else if (unit.equals("million")) {

												revenue = revenue * 1000000;
											}
											}

										// System.out.println(revenue);
									}

									if (header.text().equals("Number of employees")) {
										Elements cols = row.select("td");
										String s = cols.get(0).text();
										//System.out.println(s);
										int j;
										boolean flag = false;
									
										for ( j = 0 ; j<s.length() ; ++j){
											if(Character.isLetter(s.charAt(j))){
												flag = true;
												break;
											}
										}
										if(flag){
											
										s = s.substring(0, j).replaceAll("[^0-9]", "").trim();
										
										}
										if (s.contains("(")) {
											String[] ss = s.split("\\(");

											if (ss[0].contains("[")) {

												String[] sss = ss[0].split("\\[");
												sss[0] = sss[0].replaceAll("[^0-9]", "").trim();
												
												boolean isNumber = Pattern.matches("[0-9]+", sss[0] );
												if(isNumber) {
													number_of_employees = Integer.parseInt(sss[0]);

												}
												

											} else {
												ss[0] = ss[0].replaceAll("[^0-9]", "").trim();
												
												boolean isNumber = Pattern.matches("[0-9]+", ss[0]);
												if(isNumber) {
													number_of_employees = Integer.parseInt(ss[0]);

												}
												
												

											}
										} else if (s.contains("[")) {
											String[] ss = s.split("\\[");
											ss[0] = ss[0].replaceAll("[^0-9]", "").trim();
											boolean isNumber = Pattern.matches("[0-9]+", ss[0]);
											if(isNumber) {
												number_of_employees = Integer.parseInt(ss[0]);

											}
											
											
										} else {
											s = s.replaceAll("[^0-9]", "").trim();

											boolean isNumber = Pattern.matches("[0-9]+", s);
											if(isNumber) {
												number_of_employees = Integer.parseInt(s);

											}
											
											
										}
									}

									if (header.text().equals("Number of locations")) {
										Elements cols = row.select("td");
										String s = cols.get(0).text();
										
										int j;
										boolean flag = false;
									
										for ( j = 0 ; j<s.length() ; ++j){
											if(Character.isLetter(s.charAt(j))){
												flag = true;
												break;
											}
										}
										if(flag){
											
										s = s.substring(0, j).replaceAll("[^0-9]", "").trim();
										}
										
										if (s.contains("[")) {
											String[] ss = s.split("\\[");

											if (ss[0].contains("(")) {
												String[] sss = ss[0].split("\\(");
												if(sss[0].contains(":")){
													String colon[]=sss[0].split(":");
													colon[1] = colon[1].replaceAll("[^0-9]", "").trim();
													
													boolean isNumber = Pattern.matches("[0-9]+", colon[1]);
													if(isNumber) {
														number_of_locations = Integer.parseInt(colon[1]);
													}
													
													
												}
												else{
												sss[0] = sss[0].replaceAll("[^0-9]", "").trim();
												
												
												boolean isNumber = Pattern.matches("[0-9]+", sss[0]);
												if(isNumber) {
													number_of_locations = Integer.parseInt(sss[0]);
												}
												
												}

											} else {

												ss[0] = ss[0].replaceAll("[^0-9]", "").trim();
												boolean isNumber = Pattern.matches("[0-9]+", ss[0] );
												if(isNumber) {
													number_of_locations = Integer.parseInt(ss[0]);
												}
												

											}

										} else if (s.contains("(")) {
											String[] ss = s.split("\\(");
											ss[0] = ss[0].replaceAll("[^0-9]", "").trim();
											
											boolean isNumber = Pattern.matches("[0-9]+", ss[0] );
											if(isNumber) {
												number_of_locations = Integer.parseInt(ss[0]);
											}
											

										} else {
											
											
											s = s.replaceAll("[^0-9]", "").trim();
											
											boolean isNumber = Pattern.matches("[0-9]+", s);
											if(isNumber) {
												number_of_locations = Integer.parseInt(s);
											}
											
											

										}
									}
								}

							} else {
								System.out.println("Wikipedia with no table");

								comment = "Wikipedia with no table";
							}
						} catch (IOException e) {

							e.printStackTrace();
						}
					} else {
						System.out.println("No wikipedia page");
						comment = "No Wikipedia page";
					}

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if(comment==null){
					if(type==null && industry==null && revenue==0.0 && number_of_employees==0 && number_of_locations==0){
						comment="No info";
					}
				}
				
				String query2 = "UPDATE `business`.`companies` SET type=?, industry=? , revenue=? , number_of_employees=? , number_of_locations=? , comment=? where company='"
						+ company + "'";
				PreparedStatement ps2 = myConn.prepareStatement(query2);
				ps2.setString(1, type);
				ps2.setString(2, industry);
				// ps2.setString(3, String.valueOf(currency));
				ps2.setDouble(3, revenue);
				ps2.setInt(4, number_of_employees);
				ps2.setInt(5, number_of_locations);
				ps2.setString(6, comment);
				ps2.executeUpdate();
				myConn.commit();
				ps2.close();
				waitDuration = (long) ((1 * Math.random() + 3) * 1000);

				try {
					Thread.sleep(waitDuration);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			myConn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
