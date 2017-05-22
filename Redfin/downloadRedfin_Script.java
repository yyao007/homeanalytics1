package downloadRedfin_Script;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class downloadRedfin_Script {

	public static void main(String[] args) throws IOException {
		
		String file1="/home/niloufar/Desktop/out.txt";
         String file2 = "/home/niloufar/Desktop/RedfinLinks.sh";
		
		

		FileWriter fw = new FileWriter(file2, true);

		BufferedWriter bw = new BufferedWriter(fw);

		String line = null;
		Scanner sc = new Scanner(new File("/home/niloufar/Desktop/zip.txt"));
		List<String> lines = new ArrayList<String>();
		while (sc.hasNextLine()) {
		  lines.add(sc.nextLine());
		}

		String[] arr = lines.toArray(new String[0]);

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(file1);

			BufferedReader bufferedReader = new BufferedReader(fileReader);
			bw.write("#!/bin/bash");
			bw.newLine();
			bw.flush();
			int counter = 0;
			String concat = null;
			while (((line = bufferedReader.readLine()) != null)) {
				counter++;
				concat = null;
				if (counter % 10 == 0) {
					concat = "wget --tries=2 --timeout=3 --wait=30 -U \"Mozilla\" -O /home/nhoss003/redfin/"+ arr[counter]+".csv " + "'"+line+"'";
				} else {
					concat = "wget --tries=2 --timeout=3 --wait=10 -U \"Mozilla\" -O /home/nhoss003/redfin/"+ arr[counter]+".csv " + "'"+line+"'";
				}
				concat=concat.replace("(", "\\(");
				concat=concat.replace(")", "\\)");
				bw.write(concat);
				bw.newLine();
				bw.flush();

			}

			// Always close files.
			bufferedReader.close();
			bw.close();

		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + file1 + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + file1 + "'");

		}


	}

}
