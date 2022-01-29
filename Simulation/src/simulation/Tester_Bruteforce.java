package simulation;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Tester_Bruteforce {
	static Checker check;
	static Scanner cscan, fscan;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		check = new Checker();
		cscan = new Scanner(System.in);
		int choice;
		do {
			choice = MainMenu();
			if(choice == 1) {
				String[] list = Manual();
				easterEgg(list[0], list[1]);
				System.out.println("");
			}else if(choice == 2) {
				ArrayList<String> list = fileBased();
				int repeat = 1;
				//System.out.print("Enter number of repetitions: ");
				//repeat = Integer.parseInt(cscan.nextLine());
				for(int j = 1; j <= repeat; j++) {
					for(int i = 0; i < list.size(); i++) {
						String[] s = list.get(i).split(",");
						easterEgg(s[0],s[1]);
					}
					System.out.println("");
				}
			}
			System.out.println("");
		}while(choice > 0 && choice < 3);
		cscan.close();
		System.exit(0);
	}
	
	private static String[] Manual() {
		System.out.print("Starting value (From U+00): ");
		String minHex = check.CheckInput(cscan.nextLine());
		System.out.print("End Value (Up to U+10FFFFF): ");
		String maxHex = check.CheckInput(cscan.nextLine());
		String[] out = {minHex, maxHex};
		return out;
	}
	
	private static int MainMenu() {
		System.out.print("1 - Individual Entry, 2 - File Entry, 3 - Exit: ");
		return Integer.parseInt(cscan.nextLine());
	}
	
	private static ArrayList<String> fileBased() {
		ArrayList<String> out = new ArrayList<String>();
		System.out.print("Enter filename (including .csv): ");
		String filename = cscan.nextLine();
		try {
			fscan = new Scanner(new File(filename));
			while(fscan.hasNextLine())
				out.add(fscan.nextLine());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out;
	}
	
	private static void easterEgg(String minHex, String maxHex) {
		ArrayList<Unicode> outputs = new ArrayList<Unicode>();
		long min = Long.parseLong(minHex,16), max = Long.parseLong(maxHex,16);
		//if(max-min >= 69631) //triggered if running test size >= 10FFF
			//System.out.println("Number of elements to be computed is big, it may take a while and will consume a significant amount of memory.");
		//System.out.println("Bruteforce: " + minHex + " to " + maxHex);
		Diagnostics d = new Diagnostics();
		try {
			d.start();
			Unicode u = new Unicode();
			for(long i = min; i <= max; i++) {
				u.SetUnicode(Long.toHexString(i));
				//outputs.add(u);
				//displayOutput(u);
				//double percentage = ((double)i/(double)max)*100;
				//System.out.println("Bruteforce Completed: " + (percentage) + "%");
			}
			d.end();
			System.out.print(d.getTotalTime() + ", ");
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		//displayOutput(outputs);
	}
	private static void displayOutput(ArrayList<Unicode> u) {
		for(int i = 0; i < u.size(); i++) {
			System.out.println(u.get(i).GetUnicode() + u.get(i).GetFormatted());
		}
	}
	
	private static void displayOutput(Unicode u) {
			System.out.println(u.GetUnicode() + u.GetFormatted());
	}
}
