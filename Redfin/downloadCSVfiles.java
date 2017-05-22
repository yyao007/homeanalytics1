package downloadCSVfiles;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class downloadCSVfiles {

	public static void main(String[] args) throws IOException, InterruptedException {
		try
		{
	
			Scanner sc = new Scanner(new File("zip.txt"));
			List<String> lines = new ArrayList<String>();
			while (sc.hasNextLine()) {
			  lines.add(sc.nextLine());
			}

			String[] arr = lines.toArray(new String[0]);
			
			File fout = new File("out.txt");
			FileOutputStream fos = new FileOutputStream(fout);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			
			for(int i =0 ; i< arr.length ; ++i){
			
			
		Document document = Jsoup.connect("https://www.redfin.com/zipcode/"+arr[i]).userAgent("Mozilla/5.0") .timeout(30000) .get();
		
		 Thread.sleep(2000);
		    Elements links1 = document.getElementsByClass("downloadLink");
		    System.out.println(links1.attr("abs:href"));
		    bw.write(links1.attr("abs:href"));
			bw.newLine();
			}
			bw.flush();
			bw.close();
			} 
		catch (IOException e) 
		{
		    e.printStackTrace();
		}
		

	}

}
