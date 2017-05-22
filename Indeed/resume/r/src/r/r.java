package r;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class r {
	public static String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";
	

	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, SQLException {
		
		
	
		String str1=null;
		
		
		String n = args[0];
		if (n.equals("one")){
			for(int i=1; i<=10 ; ++i)
			{
				
				if(i==1){str1="DC";
				System.out.println("DC");
				}
					
				else if(i==2){str1="OH";
				System.out.println("OH");
				}
				
				else if(i==3){str1="MI";
				System.out.println("MI");
				}
				
				else if(i==4){str1="IN";
				System.out.println("IN");
				}
				
				else if(i==5){str1="KS";
				System.out.println("KS");
				}
				
				else if(i==6){str1="MD";}
				
				else if(i==7){str1="CO";}
				
				else if(i==8){str1="MT";}
				
				else if(i==9){str1="UT";}
				
				else if(i==10){str1="WY";}
				
				crawlIndeedResume(str1);
			}
			
		}
		
		if (n.equals("two")){
			for(int i=1; i<=10 ; ++i)
			{
				
			 if(i==1){str1="TX";}
					
			else if(i==2){str1="MO";}
				
			else if(i==3){str1="KY";}
				
			else if(i==4){str1="WV";}
				
			else if(i==5){str1="AL";}
				
			else if(i==6){str1="NE";}
				
			else if(i==7){str1="MS";}
				
			else if(i==8){str1="ND";}
				
			else if(i==9){str1="CT";}
				
			else if(i==10){str1="HI";}
				
			 crawlIndeedResume(str1);
			}
				
		
		}
		
		if (n.equals("three")){
			for(int i=1; i<=10 ; ++i)
			{
				
				if(i==1){str1="NY";}
					
				else if(i==2){str1="FL";}
				
				else if(i==3){str1="NC";}
				
				else if(i==4){str1="OK";}
				
				else if(i==5){str1="AR";}
				
				else if(i==6){str1="LA";}
				
				else if(i==7){str1="ME";}
				
				else if(i==8){str1="SD";}
				
				else if(i==9){str1="VT";}
				
				else if(i==10){str1="NV";}
				
				crawlIndeedResume(str1);
			}
				
		
		}
		
		if (n.equals("four")){
			for(int i=1; i<=10 ; ++i)
			{
				
				if(i==1){str1="CA";}
					
				else if(i==2){str1="IA";}
				
				else if(i==3){str1="VA";}
				
				else if(i==4){str1="GA";}
				
				else if(i==5){str1="WA";}
				
				else if(i==6){str1="MA";}
				
				else if(i==7){str1="OR";}
				
				else if(i==8){str1="AK";}
				
				else if(i==9){str1="ID";}
				
				else if(i==10){str1="PR";}
				
				crawlIndeedResume(str1);
			}
				
		
		}
		
		if (n.equals("five")){
			for(int i=1; i<=10 ; ++i)
			{
				
				if(i==1){str1="IL";}
					
				else if(i==2){str1="MN";}
				
				else if(i==3){str1="WI";}
				
				else if(i==4){str1="NJ";}
				
				else if(i==5){str1="TN";}
				
				else if(i==6){str1="SC";}
				
				else if(i==7){str1="NM";}
				
				else if(i==8){str1="AZ";}
				
				else if(i==9){str1="NH";}
				
				else if(i==10){str1="DE";}
				
				crawlIndeedResume(str1);
			}
				
	
		}
		if (n.equals("six")){
			for(int i=1; i<=2 ; ++i)
			{
				
				if(i==1){str1="RI";}
					
				else if(i==2){str1="PA";}
				

				crawlIndeedResume(str1);
				
			}
				
		
		}
		else{
			// To crawl a single state
			System.out.println(str1);
			crawlIndeedResume(str1);
		}
}
	
	public static void crawlIndeedResume(String st1) throws ClassNotFoundException, SQLException, InterruptedException, IOException
	{
		System.out.println("********************************************");
		System.out.println(st1);
		System.out.println("********************************************");
		
		
		Document doc = null;
			int k = 0;
		long waitDuration = 0;
		String y0_1 = null;
		String y1_2 = null;
		String y3_5 = null;
		String y6_10 = null;
		String y10_ = null;
		String doctor = null;
		String master = null;
		String bachelor = null;
		String associate = null;
		String diploma = null;
		String companyName = null;
		String companyCount = null;
		String jobTitle = null;
		String jobCount = null;
		Connection myConn;
		PreparedStatement preparedStatement;
		try {
			myConn = DriverManager.getConnection("jdbc:mysql://localhost/business?autoReconnect=true", "root", "home123");

			// myConn.setAutoCommit(false);

			Statement myStmt = myConn.createStatement();

			ResultSet rs = myStmt.executeQuery("SELECT distinct city,state FROM cities WHERE state='"+st1+"' order by state");

			
			  FirefoxProfile profile = new FirefoxProfile();
				profile.setPreference(FirefoxProfile.ALLOWED_HOSTS_PREFERENCE, "localhost");
		WebDriver driver = new FirefoxDriver(profile);
			
			
			while (rs.next()) {
				Timestamp crawl_time = null;
				crawl_time = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
				String city = rs.getString("city");
				String state = rs.getString("state");
				waitDuration = (long) ((10 * Math.random() + 2) * 1000);
				//System.out.println(waitDuration);
				Thread.sleep(waitDuration);
//				Document doc = Jsoup
//						.connect("https://www.indeed.com/resumes?q=any&l=" + city + "," + state + "&co=US&radius=0")
//						.userAgent(userAgent).get();
				
				
				driver.get("https://www.indeed.com/resumes?q=any&l=" + city + "," + state + "&co=US&radius=0");

				String page = driver.getPageSource();
				 doc = Jsoup.parse(page);
				
				String source = "https://www.indeed.com/resumes?q=any&l=" + city + "," + state + "&co=US&radius=0";
				System.out.println(k + "  " + source);

				if (doc.select("div[id=refinements] > ul[data-group-title=Years of Work Experience] > li > a ")
						.toString().length() > 1) {
					for (int i = 0; i < doc
							.select("div[id=refinements] > ul[data-group-title=Years of Work Experience] > li > a ")
							.size(); ++i) {
						Elements elem = doc
								.select("div[id=refinements] > ul[data-group-title=Years of Work Experience]> li > a");
						Elements count = doc.select(
								"div[id=refinements] > ul[data-group-title=Years of Work Experience] > li > span");
						if (elem.get(i).text().contains("Less")) {
							y0_1 = count.get(i).text().replaceAll("[^0-9]", "");
						} else if (elem.get(i).text().contains("1-2")) {
							y1_2 = count.get(i).text().replaceAll("[^0-9]", "");
						} else if (elem.get(i).text().contains("3-5")) {
							y3_5 = count.get(i).text().replaceAll("[^0-9]", "");
						} else if (elem.get(i).text().contains("6-10")) {
							y6_10 = count.get(i).text().replaceAll("[^0-9]", "");
						} else if (elem.get(i).text().contains("More")) {
							y10_ = count.get(i).text().replaceAll("[^0-9]", "");
						}

					}
				}

				if (doc.select("div[id=refinements] > ul[data-group-title=Education] > li > a ").toString()
						.length() > 1) {

					for (int i = 0; i < doc.select("div[id=refinements] > ul[data-group-title=Education] > li > a ")
							.size(); ++i) {

						Elements elem = doc.select("div[id=refinements] > ul[data-group-title=Education]> li > a");
						Elements count = doc.select("div[id=refinements] > ul[data-group-title=Education] > li > span");
						if (elem.get(i).text().contains("Doctorate")) {
							doctor = count.get(i).text().replaceAll("[^0-9]", "");
						} else if (elem.get(i).text().contains("Masters")) {
							master = count.get(i).text().replaceAll("[^0-9]", "");
						} else if (elem.get(i).text().contains("Bachelors")) {
							bachelor = count.get(i).text().replaceAll("[^0-9]", "");
						} else if (elem.get(i).text().contains("Associates")) {
							associate = count.get(i).text().replaceAll("[^0-9]", "");
						} else if (elem.get(i).text().contains("Diploma")) {
							diploma = count.get(i).text().replaceAll("[^0-9]", "");
						}

					}
				}

				String sqlresume = "Insert into resume(city, state, crawl_time,y0_1, y1_2,y3_5,y6_10,y10_,doctor,master,bachelor,associate,diploma) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
				preparedStatement = myConn.prepareStatement(sqlresume);
				preparedStatement.setString(1, city.trim());
				preparedStatement.setString(2, state.trim());
				preparedStatement.setTimestamp(3, crawl_time);
				if (y0_1 != null)
					preparedStatement.setInt(4, Integer.parseInt(y0_1.trim()));
				else
					preparedStatement.setString(4, null);
				if (y1_2 != null)
					preparedStatement.setInt(5, Integer.parseInt(y1_2.trim()));
				else
					preparedStatement.setString(5, null);
				if (y3_5 != null)
					preparedStatement.setInt(6, Integer.parseInt(y3_5.trim()));
				else
					preparedStatement.setString(6, null);
				if (y6_10 != null)
					preparedStatement.setInt(7, Integer.parseInt(y6_10.trim()));
				else
					preparedStatement.setString(7, null);
				if (y10_ != null)
					preparedStatement.setInt(8, Integer.parseInt(y10_.trim()));
				else
					preparedStatement.setString(8, null);
				if (doctor != null)
					preparedStatement.setInt(9, Integer.parseInt(doctor.trim()));
				else
					preparedStatement.setString(9, null);
				if (master != null)
					preparedStatement.setInt(10, Integer.parseInt(master.trim()));
				else
					preparedStatement.setString(10, null);
				if (bachelor != null)
					preparedStatement.setInt(11, Integer.parseInt(bachelor.trim()));
				else
					preparedStatement.setString(11, null);
				if (associate != null)
					preparedStatement.setInt(12, Integer.parseInt(associate.trim()));
				else
					preparedStatement.setString(12, null);
				if (diploma != null)
					preparedStatement.setInt(13, Integer.parseInt(diploma.trim()));
				else
					preparedStatement.setString(13, null);

				preparedStatement.executeUpdate();
				// myConn.commit();
				preparedStatement.close();

				// System.out.println(y0_1 );
				// System.out.println(y1_2);
				// System.out.println(y3_5);
				// System.out.println(y6_10 );
				// System.out.println(y10_);
				// System.out.println(doctor );
				// System.out.println(master);
				// System.out.println(bachelor);
				// System.out.println(associate);
				// System.out.println(diploma);

				if (doc.select("div[id=refinements] > ul[data-group-title=Job Titles] > li > a ").toString()
						.length() > 1) {
					Elements elem = doc.select("div[id=refinements] > ul[data-group-title=Job Titles] > li > a ");
					Elements count = doc
							.select("div[id=refinements] > ul[data-group-title=Job Titles] > li > span");
					
					for (int i = 0; i < doc.select("div[id=refinements] > ul[data-group-title=Job Titles] > li > a ")
							.size(); i++) {
						Thread.sleep(1000);
						
						jobTitle = elem.get(i).text();
						jobCount = count.get(i).text().replaceAll("[^0-9]", "");

						crawl_time = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

						String sqljob = "Insert into resumeJobs(city, state, crawl_time,jobTitle, jobCount) values(?,?,?,?,?)";
						preparedStatement = myConn.prepareStatement(sqljob);
						preparedStatement.setString(1, city.trim());
						preparedStatement.setString(2, state.trim());
						preparedStatement.setTimestamp(3, crawl_time);
						preparedStatement.setString(4, jobTitle.trim());
						preparedStatement.setInt(5, Integer.parseInt(jobCount.trim()));
						preparedStatement.executeUpdate();
						// myConn.commit();
						preparedStatement.close();

						// System.out.println(jobTitle);
						// System.out.println(jobCount);
					}
				}

				if (doc.select("div[id=refinements] > ul[data-group-title=Companies] > li > a ").toString()
						.length() > 1) {
					
					Elements elem = doc.select("div[id=refinements] > ul[data-group-title=Companies] > li > a ");
					Elements count = doc.select("div[id=refinements] > ul[data-group-title=Companies] > li > span");
					
					for (int i = 0; i < doc.select("div[id=refinements] > ul[data-group-title=Companies] > li > a ")
							.size(); i++) {
						Thread.sleep(1000);
						
						companyName = elem.get(i).text();
						companyCount = count.get(i).text().replaceAll("[^0-9]", "");
						crawl_time = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

						String sqlcompany = "Insert into resumeCompanies(city, state, crawl_time,companyName, companyCount) values(?,?,?,?,?)";
						preparedStatement = myConn.prepareStatement(sqlcompany);
						preparedStatement.setString(1, city.trim());
						preparedStatement.setString(2, state.trim());
						preparedStatement.setTimestamp(3, crawl_time);
						preparedStatement.setString(4, companyName.trim());
						preparedStatement.setInt(5, Integer.parseInt(companyCount.trim()));
						preparedStatement.executeUpdate();
						// myConn.commit();
						preparedStatement.close();

						// System.out.println(companyName);
						// System.out.println(companyCount);
					}
				}
				++k;
			}
			
			myConn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
		 System.out.println("==================================================================");
	}

}
