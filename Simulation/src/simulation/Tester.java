package simulation;

import java.util.*;
import java.io.*;

public class Tester {

	static ArrayList<String> unicode;
	static ArrayList<String> utf8;
	static ArrayList<String> utf16;
	static ArrayList<String> utf32;
	static Checker c;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		unicode = new ArrayList<String>();
		utf8 = new ArrayList<String>();
		utf16 = new ArrayList<String>();
		utf32 = new ArrayList<String>();
		
		Scanner cscan = new Scanner(System.in);
		System.out.println("Tester\n\nUse CSV file for input and expected outputs. Format are as follows:\nUnicode,UTF-8,UTF-16,UTF-32\nPlease include the header: \"Unicode,UTF-8,UTF-16,UTF-32\""
				+ " as the first line of the file.\n\n");
		System.out.print("Enter filename.csv and file directory (use \\ instead of /) if not on root: ");
		String filename = cscan.nextLine();
		cscan.close();
		
		System.out.println("\n");
		double[] init_acceptance = ReadFile(filename);
		double acceptance = Check();
		System.out.println("Input Acceptance: " + (init_acceptance[0]-init_acceptance[1]) + " entries (" + ((init_acceptance[0]-init_acceptance[1])/init_acceptance[0])*100 + "%)");
		System.out.println("Accuracy Rating: " + (acceptance*100) + "%");
	}
	
	public static double[] ReadFile(String filename) {
		double total = 0, skipped = 0;
		Scanner fscan;	 
		c = new Checker();
		try {
			fscan = new Scanner(new File(filename));
			boolean header = true;
			while(fscan.hasNext()) {
				if(header) { //skip first line
					fscan.nextLine();
					header = false;
				} 
				else {
					String line = fscan.nextLine();
					String[] lineSplit = line.split(",");
					if(c.CheckInputBool(lineSplit[0])) {
						unicode.add(lineSplit[0].toUpperCase());
						utf8.add(lineSplit[1].toUpperCase());
						utf16.add(lineSplit[2].toUpperCase());
						utf32.add(lineSplit[3].toUpperCase());
						total++;
					}
					else {
						System.out.println("Unicode hex \"" + lineSplit[0] + "\" not accepted!");
						total++;
						skipped++;
					}	
				}
			}
			fscan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double[] d = {total,skipped};
		return d;
	}
	
	public static double Check() {
		double acceptance = 0, total = unicode.size()*3;
		
		Unicode u;
		for(int i = 0; i < unicode.size(); i++) {
			u = new Unicode(c.CheckInput(unicode.get(i)));
			if(u.GetUTF8().equals(utf8.get(i).toUpperCase()))
				acceptance++;
			else 
				System.out.println(u.GetUnicode() + " UTF-8 mismatch (Ans/Target): " + u.GetUTF8() + "/" + utf8.get(i).toUpperCase());
			
			if(u.GetUTF16().equals(utf16.get(i).toUpperCase()))
				acceptance++;
			else
				System.out.println(u.GetUnicode() + " UTF-16 mismatch (Ans/Target): " + u.GetUTF16() + "/" + utf16.get(i).toUpperCase());
			
			if(u.GetUTF32().toUpperCase().equals(utf32.get(i).toUpperCase()))
				acceptance++;
			else
				System.out.println(u.GetUnicode() + " UTF-32 mismatch (Ans/Target): " + u.GetUTF32() + "/" + utf32.get(i).toUpperCase());
			
			System.out.println(u.GetUnicode() + "(" + u.GetChar() + ") checked!");
		}
		acceptance /= total;
		return acceptance;
	}
}