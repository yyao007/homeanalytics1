package sql;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class sql {

	public static void main(String[] args) throws IOException {

		String file2 = "/home/niloufar/Desktop/redfinTest/1600.sql";

		FileWriter fw = new FileWriter(file2, true);

		BufferedWriter bw = new BufferedWriter(fw);

		Scanner sc = new Scanner(new File("/home/niloufar/Desktop/zip.txt"));
		List<String> lines = new ArrayList<String>();
		while (sc.hasNextLine()) {
			lines.add(sc.nextLine());
		}
		String concat = null;

		String[] arr = lines.toArray(new String[0]);
		
		
		for (int i = 0; i < arr.length; ++i) {
			concat = "LOAD DATA LOCAL INFILE '/home/nhoss003/redfin/" + arr[i]
					+ ".csv'  INTO TABLE redfin FIELDS TERMINATED BY ','"
					+ " ENCLOSED BY '\"'  LINES TERMINATED BY '\n'  "
					+ "IGNORE 1 LINES "
					+ "(sale_type,property_type,address,city,state,zip,price,beds,baths,"
					+ "location,sqft,lot_size,year_built,days_on_market,status,"
					+ " @dummy, @dummy,url,source,listing_id,@dummy,@dummy,latitude,longitude);";

			bw.write(concat);
			bw.newLine();
			bw.flush();
		}
		
		bw.close();

	}

}
