

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

//import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
//import org.openqa.selenium.support.ui.Select;
import org.json.JSONException;
//import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class indeed
{
	public static void main(String args[]) throws ClassNotFoundException, IOException, SQLException, InterruptedException, JSONException
	{	String str1=null;
	
	
		String n = args[0];
		String states = "AL AK AZ AR CA CO CT DC DE FL GA HI ID IL IN IA KS KY LA ME MD MA MI MN MS MO MT NE NV NH NJ NM NY NC ND OH OK OR PA PR RI SC SD TN TX UT VT VA WA WV WI WY";
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
				
				crawlIndeed(str1);
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
				
			 crawlIndeed(str1);
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
				
				crawlIndeed(str1);
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
				
				crawlIndeed(str1);
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
				
				crawlIndeed(str1);
			}
				
	
		}
		if (n.equals("six")){
			for(int i=1; i<=2 ; ++i)
			{
				
				if(i==1){str1="RI";}
					
				else if(i==2){str1="PA";}
				

				crawlIndeed(str1);
				
			}
				
		
		}
		else {
			for (String s: args) {
				if (states.contains(s)) {
					crawlIndeed(s);
				}
			}
		}
	}
	/*private static Connection newConnection() throws ClassNotFoundException, SQLException
    {
		Class.forName("com.mysql.jdbc.Driver");
		String urldb = "jdbc:mysql://localhost:8889/homeparseddata";
		String user = "root";
		String password = "root";
		Connection conn = DriverManager.getConnection(urldb, user, password);
		return conn;
    }*/
	private static LinkedList<String> keys = new LinkedList<String>();         // to store all the keys which are read from keys.txt file and that has been taken care by the function readKeysAndProxies
	private static Map<String, String> proxies = new HashMap<String, String>(); 
	private static Connection newConnection() throws ClassNotFoundException, SQLException
    {
		Class.forName("com.mysql.jdbc.Driver");
		String urldb = "jdbc:mysql://localhost/business";
		String user = "root";
		String password = "home123";
		Connection conn = DriverManager.getConnection(urldb, user, password);
		return conn;
    }
	public static void crawlIndeed(String st1) throws ClassNotFoundException, SQLException, InterruptedException, IOException, JSONException
	{	
		Connection myConn1 = newConnection();
		
		Statement st = null ;
		ResultSet rs = null;
		int k=0;
		Document doc = null;
		//String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";
        //int timeout = 200000;
        st = myConn1.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
        st.setFetchSize(Integer.MIN_VALUE);
	    rs = st.executeQuery("SELECT city,state FROM cities WHERE state='"+st1+"' order by state");
	   // readKeysAndProxies("keys.txt");
	    FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference(FirefoxProfile.ALLOWED_HOSTS_PREFERENCE, "localhost");
		WebDriver driver = new FirefoxDriver(profile);
	   while(rs.next())
	   {
		   String fullTime =null;
		   String partTime =null;
		   String intern =null;
		   String contract =null;
		   String comm =null;
		   String temp =null;
		   String k20 =null;
		   String k25 =null;
		   String k30 =null;
		   String k35 =null;
		   String k40 =null;
		   String k45 =null;
		   String k50 =null;
		   String k55 =null;
		   String k60 =null;
		   String k65 =null;
		   String k70 =null;
		   String k75 =null;
		   String k80 =null;
		   String k85 =null;
		   String k90 =null;
		   String k95 =null;
		   Timestamp crawl_time=null;
		   String city  = rs.getString("city");
		   String state = rs.getString("state");
		  // String city ="Irvine";
		  // String state = "CA";
		   
			driver.get("http://www.indeed.com/jobs?l="+city+","+state+"&radius=0");
			// Getting the text box and inserting the value to be searched in textbox 
			
			Thread.sleep(10000);
			String page = driver.getPageSource();
		   String source = "http://www.indeed.com/jobs?l="+city+","+state+"&radius=0";
           System.out.println(k+"  "+source);

          // Jsoup.connect(source).wait(1000);
           doc = Jsoup.parse(page);
           
		   crawl_time = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
           if(doc.select("div[id=SALARY_rbo] > ul >li").toString().length()>1)
           {
        	   for(int i=0;i<doc.select("div[id=SALARY_rbo] > ul >li").size();i++)
        	   {
        		 //  System.out.println(doc.select("div[id=SALARY_rbo] > ul >li").get(i).text());
        		   String parts[] = doc.select("div[id=SALARY_rbo] > ul >li").get(i).text().split(" +");
        		  // System.out.println(parts[0].replaceAll("[^0-9]", ""));
        		  // System.out.println(parts[1].replaceAll("[^0-9]", ""));
        		   if(parts[0].replaceAll("[^0-9]", "").equals("20000"))
        			   k20 = parts[1].replaceAll("[^0-9]", "");
        		   if(parts[0].replaceAll("[^0-9]", "").equals("25000"))
        			   k25 = parts[1].replaceAll("[^0-9]", "");
        		   if(parts[0].replaceAll("[^0-9]", "").equals("30000"))
        			   k30 = parts[1].replaceAll("[^0-9]", "");
        		   if(parts[0].replaceAll("[^0-9]", "").equals("35000"))
        			   k35 = parts[1].replaceAll("[^0-9]", "");
        		   if(parts[0].replaceAll("[^0-9]", "").equals("40000"))
        			   k40 = parts[1].replaceAll("[^0-9]", "");
        		   if(parts[0].replaceAll("[^0-9]", "").equals("45000"))
        			   k45 = parts[1].replaceAll("[^0-9]", "");
        		   if(parts[0].replaceAll("[^0-9]", "").equals("50000"))
        			   k50 = parts[1].replaceAll("[^0-9]", "");
        		   if(parts[0].replaceAll("[^0-9]", "").equals("55000"))
        			   k55 = parts[1].replaceAll("[^0-9]", "");
        		   if(parts[0].replaceAll("[^0-9]", "").equals("60000"))
        			   k60 = parts[1].replaceAll("[^0-9]", "");
        		   if(parts[0].replaceAll("[^0-9]", "").equals("65000"))
        			   k65 = parts[1].replaceAll("[^0-9]", "");
        		   if(parts[0].replaceAll("[^0-9]", "").equals("70000"))
        			   k70 = parts[1].replaceAll("[^0-9]", "");
        		   if(parts[0].replaceAll("[^0-9]", "").equals("75000"))
        			   k75 = parts[1].replaceAll("[^0-9]", "");
        		   if(parts[0].replaceAll("[^0-9]", "").equals("80000"))
        			   k80 = parts[1].replaceAll("[^0-9]", "");
        		   if(parts[0].replaceAll("[^0-9]", "").equals("85000"))
        			   k85 = parts[1].replaceAll("[^0-9]", "");
        		   if(parts[0].replaceAll("[^0-9]", "").equals("90000"))
        			   k90 = parts[1].replaceAll("[^0-9]", "");
        		   if(parts[0].replaceAll("[^0-9]", "").equals("95000"))
        			   k95 = parts[1].replaceAll("[^0-9]", "");
        		   
        	   }
           }
           if(doc.select("div[id=JOB_TYPE_rbo]").toString().length()>1)
           {
        	   for(int i=0;i<doc.select("div[id=JOB_TYPE_rbo] > ul >li").size();i++)
        	   {
        		   String parts[] = doc.select("div[id=JOB_TYPE_rbo] > ul >li").get(i).text().split(" +");
        		  // System.out.println(parts[0]);
        		  // System.out.println(parts[1].replaceAll("[^0-9]", ""));
        		   if(parts[0].contains("Full"))
        			   fullTime= parts[1].replaceAll("[^0-9]", "");
        		   if(parts[0].contains("Part"))
        			   partTime= parts[1].replaceAll("[^0-9]", "");
        		   if(parts[0].contains("Contract"))
        			   contract= parts[1].replaceAll("[^0-9]", "");
        		   if(parts[0].contains("Temporary"))
        			   temp= parts[1].replaceAll("[^0-9]", "");
        		   if(parts[0].contains("Commission"))
        			   comm= parts[1].replaceAll("[^0-9]", "");
        		   if(parts[0].contains("Internship"))
        			   intern= parts[1].replaceAll("[^0-9]", "");
        	   }
        	   
           }
           Connection myConn2 = newConnection();
           if(doc.select("div[id=COMPANY_rbo]").toString().length()>1)
           {
        	  
        	   for(int i=0;i<doc.select("div[id=COMPANY_rbo] > ul >li").size();i++)
        	   {
        		  String companyName = doc.select("div[id=COMPANY_rbo] > ul >li").get(i).text();
        		  companyName = companyName.substring(0,companyName.indexOf('(')).trim();
        		  String jobs = doc.select("div[id=COMPANY_rbo] > ul >li").get(i).text();
        		  jobs = jobs.substring(jobs.lastIndexOf('(')+1,jobs.lastIndexOf(')')).trim();

        		  String sql4location="Insert into companies(city, state, crawl_time,company, jobsNumber) values(?,?,?,?,?)";
        		  PreparedStatement preparedStatement = myConn2.prepareStatement(sql4location);
      	          preparedStatement.setString(1, city.trim());
      	          preparedStatement.setString(2, state.trim());
      	          preparedStatement.setTimestamp(3, crawl_time);
      	          preparedStatement.setString(4, companyName.trim());
      	          preparedStatement.setInt(5, Integer.parseInt(jobs.trim()));
      	          preparedStatement.executeUpdate();
      	          preparedStatement.close();
        	   }
        	  
           }
           
           String sql4location="Insert into jobs(city, state, crawl_time,fullTime, partTime,contract,temp,commission,intern,k20,k25,k30,k35,k40,k45,k50,k55,k60,k65,k70,k75,k80,k85,k90,k95) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  		  PreparedStatement preparedStatement = myConn2.prepareStatement(sql4location);
	          preparedStatement.setString(1, city.trim());
	          preparedStatement.setString(2, state.trim());
	          preparedStatement.setTimestamp(3, crawl_time);
	   	        if(fullTime!=null)
	   	    	  preparedStatement.setInt(4, Integer.parseInt(fullTime.trim()));
	   	    	else
	   	    	  preparedStatement.setString(4, null);
	   	    	if(partTime!=null)
	   	    	  preparedStatement.setInt(5, Integer.parseInt(partTime.trim()));
	   	    	else
	   	    	  preparedStatement.setString(5, null);
	   	    	if(contract!=null)
	   	    	  preparedStatement.setInt(6, Integer.parseInt(contract.trim()));
	   	    	else
	   	    	  preparedStatement.setString(6, null);
	   	    	if(temp!=null)
	   	    	  preparedStatement.setInt(7, Integer.parseInt(temp.trim()));
	   	    	else
	   	    	  preparedStatement.setString(7, null);
	   	    	if(comm!=null)
	   	    	  preparedStatement.setInt(8, Integer.parseInt(comm.trim()));
	   	    	else
	   	    	  preparedStatement.setString(8, null);
	   	    	if(intern!=null)
		   	      preparedStatement.setInt(9, Integer.parseInt(intern.trim()));
		   	    else
		   	      preparedStatement.setString(9, null);
	   	    	if(k20!=null)
	   	    	  preparedStatement.setInt(10, Integer.parseInt(k20.trim()));
	   	    	else
	   	    	  preparedStatement.setString(10, null);
	   	    	if(k25!=null)
	   	    	  preparedStatement.setInt(11, Integer.parseInt(k25.trim()));
	   	    	else
	   	    	  preparedStatement.setString(11, null);
	   	    	if(k30!=null)
	   	    	  preparedStatement.setInt(12, Integer.parseInt(k30.trim()));
	   	    	else
	   	    	  preparedStatement.setString(12, null);
	   	    	if(k35!=null)
	   	    	  preparedStatement.setInt(13, Integer.parseInt(k35.trim()));
	   	    	else
	   	    	  preparedStatement.setString(13, null);
	   	    	if(k40!=null)
	   	    	  preparedStatement.setInt(14, Integer.parseInt(k40.trim()));
	   	    	else
	   	    	  preparedStatement.setString(14, null);
	   	    	if(k45!=null)
	   	    	  preparedStatement.setInt(15, Integer.parseInt(k45.trim()));
	   	    	else
	   	    	  preparedStatement.setString(15, null);
	   	    	if(k50!=null)
	   	    	  preparedStatement.setInt(16, Integer.parseInt(k50.trim()));
	   	    	else
	   	    	  preparedStatement.setString(16, null);
	   	    	if(k55!=null)
	   	    	  preparedStatement.setInt(17, Integer.parseInt(k55.trim()));
	   	    	else
	   	    	  preparedStatement.setString(17, null);
	   	    	if(k60!=null)
	   	    	  preparedStatement.setInt(18, Integer.parseInt(k60.trim()));
	   	    	else
	   	    	  preparedStatement.setString(18, null);
	   	    	if(k65!=null)
	   	    	  preparedStatement.setInt(19, Integer.parseInt(k65.trim()));
	   	    	else
	   	    	  preparedStatement.setString(19, null);
	   	    	if(k70!=null)
	   	    	  preparedStatement.setInt(20, Integer.parseInt(k70.trim()));
	   	    	else
	   	    	  preparedStatement.setString(20, null);
	   	    	if(k75!=null)
	   	    	  preparedStatement.setInt(21, Integer.parseInt(k75.trim()));
	   	    	else
	   	    	  preparedStatement.setString(21, null);
	   	    	if(k80!=null)
	   	    	  preparedStatement.setInt(22, Integer.parseInt(k80.trim()));
	   	    	else
	   	    	  preparedStatement.setString(22, null);
	   	    	if(k85!=null)
	   	    	  preparedStatement.setInt(23, Integer.parseInt(k85.trim()));
	   	    	else
	   	    	  preparedStatement.setString(23, null);
	   	    	if(k90!=null)
	   	    	  preparedStatement.setInt(24, Integer.parseInt(k90.trim()));
	   	    	else
	   	    	  preparedStatement.setString(24, null);
	   	    	if(k95!=null)
	   	    	  preparedStatement.setInt(25, Integer.parseInt(k95.trim()));
	   	    	else
	   	    	  preparedStatement.setString(25, null);
	          preparedStatement.executeUpdate();
	          preparedStatement.close();
	          myConn2.close();
	          
	         if(fullTime==null && partTime==null && intern==null && comm==null && temp==null && contract==null)
	         {
	        	 FileWriter fb = new FileWriter("check/"+city+","+state+".txt",true);
	    			BufferedWriter bb = new BufferedWriter(fb);
	    			bb.write(""+page);
	    			bb.close();
	    			fb.close();
	         }
		   k++;
	   }
	  driver.close();
	   
	}
	public static void readKeysAndProxies(String filename)
	{
	   try
	   {
	      Scanner scanner = new Scanner(new File(filename));
	
	      while(scanner.hasNextLine())
	      {
	         String[] line = scanner.nextLine().split(",");
	
	         keys.add(line[0]);
	         proxies.put(line[0], line[1]);
	      }
	      System.out.println(keys.size());
	      System.out.println(proxies.size());
	      scanner.close();
	
	      if(keys.size() == 0 || keys.size() != proxies.size())
	      {
	         System.out.println("File " + filename + " has no keys or isn't properly formatted.");
	         System.exit(1);
	      }
	
	      //Configuration for using the first key's proxy.
	      System.setProperty("http.proxyHost", proxies.get(keys.getFirst()));
	      System.setProperty("http.proxyPort", "3128");
	   
	   }
	   catch(FileNotFoundException e)
	   {
	      System.out.println("File " + filename + " not found.");
	      System.exit(1);
	   }
	}
/*
	private static int switchKey() throws SQLException, ClassNotFoundException, IOException, JSONException
	{
			//keysUsed++;
		   System.out.println("switchKey");
		   System.out.println(keys.size());
		   System.out.println(proxies.size());
		  // System.out.println("keys used "+keysUsed);
		   //If all of the keys have reached their usage limits, sleep until midnight.
		 //  System.out.println(keysUsed+"   "+keys.size());
		  
		   System.out.println("coming here");
		   //Switch to the next key and its corresponding proxy.
		      keys.add(keys.removeFirst());
		      System.setProperty("http.proxyHost", proxies.get(keys.getFirst()));
		      return 1;
	 }
	*/
}
