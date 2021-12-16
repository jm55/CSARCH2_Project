package simulation;

import java.lang.Integer.*;
import java.util.*;

/**
 * Conducts the finds the UTF equivalent of a given Unicode value.
 * 
 * @author Escalona, <add names of other coders and debuggers>
 *
 */
public class Unicode {
	public Unicode(){
		
	}
	
	/***
	 * Returns the UTF8 equivalent of the input value
	 * @param input Valid input Unicode value
	 * @return
	 */
	public String GetUTF8(String input) {
		return null;
	}
	
	/***
	 * Returns the UTF16 equivalent of the input value
	 * @param input Valid input Unicode value (0x0000 to 0x10FFFF, without prefix)
	 * @return UTF16 equivalent of the input value
	 */
	public String GetUTF16(String input) {
		String output = "";
		int numVal = Integer.parseInt(input,16); 	//converts hex string into decimal equivalent
		
		if(numVal <= Integer.parseInt("FFFF",16))	//code points 0x0000-0xFFFF; represent as is
			output = Resize(input, 4);
		else{ //code points 0x10000-0x10FFFF
			//subtract 0x10000 to the input value
			int tempVal = numVal - Integer.parseInt("010000",16); 
			
			//convert to binary and split into left and right segments
			String binary = Resize(Integer.toBinaryString(tempVal),20); //convert to binary
			String binLeft = binary.substring(0,10), binRight = binary.substring(10,20); //split into left & right
			
			//Add d800 and dc00 to left and right respectively
			int left = Integer.parseInt(binLeft,2) +  Integer.parseInt("D800",16);
			int right = Integer.parseInt(binRight,2) + Integer.parseInt("DC00",16);
			
			output += Integer.toHexString(left) + Integer.toHexString(right);
		}	
		return output;
	}
	
	/**
	 * Returns the UTF32 equivalent of the input value
	 * @param input Valid input Unicode value
	 * @return UTF32 equivalent of the input value
	 */
	public String GetUTF32(String input) {
		
		return null;
	}
	
	/**
	 * For both binary and hexadecimal values.
	 * Adjusts the String to the specified binary/hex digits by filling in zeroes on its left side.
	 * @param input String value to resize
	 * @param hexSize Number of specified hex digits
	 * @return Resized equivalent of the input value.
	 */
	private String Resize(String input, int hexSize) {
		String output = "";
		if(input.length() < hexSize)
			for(int i = 0; i < hexSize-input.length(); i++)
				output += '0';
		return output+input;
	}
	
	/**
	 * For hexadecimal only.
	 * Adjusts the String to the specified number of hex digits by filling in zeroes on its left side.
	 * @param input Integer value to resize. Will be converted automatically to hex equivalent.
	 * @param hexSize Number of specified hex digits
	 * @return Resized equivalent of the input value.
	 */
	private String Resize(int input, int hexSize) {
		String converted = Integer.toHexString(input);
		String output = "";
		if(converted.length() < hexSize)
			for(int i = 0; i < hexSize-converted.length(); i++)
				output += '0';
		return output+converted;
	}
}
