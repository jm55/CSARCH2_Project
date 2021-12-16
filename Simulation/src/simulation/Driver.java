package simulation;

import java.util.*;


public class Driver {
	public static void main(String[] args) {
		Driver d = new Driver();
		
		d.test();
		
	}
	
	
//TEST ZONE=================================================
	
	void test(){
		BCD_Generator bcd = new BCD_Generator();
		String[] unpacked = bcd.Unpacked("1234567890");
		String[] packed = bcd.Packed("1234567890");
		
		System.out.println("Unpacked:");
		printArray(unpacked);
		System.out.println("Packed:");
		printArray(packed);
		
		BCD_Densely densely = new BCD_Densely();
	}
	
	void printArray(int[] input) {
		for(int i = 0; i < input.length; i++)
			System.out.println(input[i]);
	}
	void printArray(String[] input) {
		for(int i = 0; i < input.length; i++)
			System.out.println(input[i]);
	}
}
