/**
 * BCD_Generator.java
 * @author ESCALONA-LTP02
 * 
 * BCD Generator
 */
package simulation;

import java.util.*;
import java.lang.Integer.*;



public class BCD_Generator {
	/**
	 * Returns an array of a packed BCD equivalent of a given input
	 * @param input - Assumes clean input
	 * @return Packed BCD equivalent per digit of the input (String array)
	 */
	public String[] Packed(String input) {
		return Convert2Binary(input, 4);
	}
	
	/**
	 * Returns an array of an unpacked BCD equivalent of a given input
	 * @param input - Assumes clean input
	 * @return Unpacked BCD equivalent per digit of the input (String array)
	 */
	public String[] Unpacked(String input) {
		return Convert2Binary(input, 8);
	}
	
	
//=================================================================================
	
	
	/**
	 * Converts any input string into binary equivalent
	 * @param input 
	 * @param bit - 8 for unpacked and 4 for packed
	 * @return String[] BCD equivalent of the input depending whether unpacked or packeds
	 */
	private String[] Convert2Binary(String input, int bit) { //assumes clean input (i.e. no letters, non-numericals, and whitespaces)
		String [] output = new String[input.length()];
		int[] list = String2IntArr(input);
		for(int i=0; i < list.length; i++)
			output[i] = BinaryResize(Integer.toBinaryString(list[i]),bit);
		return output;
	}
	
	/***
	 * Resizes the binary on the specified bit size
	 * @param input - Input to resize
	 * @param bit - Bit Size
	 * @return Resized input string equal to specified bit size
	 */
	private String BinaryResize(String input, int bit) {
		String output = "";
		for(int i = 0; i < bit-input.length(); i++)
			output += "0";
		return output+input;
	}
	
	/***
	 * Converts input string to an array of int values
	 * @param input - Clean input value to be converted
	 * @return int array equivalent of the input value
	 */
	private int[] String2IntArr(String input) {
		int[] arr = new int[input.length()];
		for(int i = 0; i < input.length(); i++)
			arr[i] = Integer.parseInt(input.charAt(i)+"");
		return arr;
	}
	
}
