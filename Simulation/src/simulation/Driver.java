package simulation;

import java.util.*;


public class Driver {
	public static void main(String[] args) {
		Driver d = new Driver();
		d.test();
	}
	
	
//TEST ZONE=================================================
	
	void test(){
		Unicode u = new Unicode();
		String[] input = ["245D6"];
		System.out.println("Input Unicode: ");
		System.out.println("UTF8 = " + u.GetUTF8(input));
		System.out.println("UTF16 = " + u.GetUTF16(input));
		System.out.println("UTF32 = " + u.GetUTF32(input));
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
