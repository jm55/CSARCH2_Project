package simulation;

import java.lang.Integer.*;
import java.util.*;

/**
 * "Unicode Engine"
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
	 * @param input Valid input Unicode value in hexadecimal from 0x0000 to 0x1FFFFF
	 * @return UTF8 equivalent of the input value.
	 */
	public String GetUTF8(String input) {
		String output = "";
		long numVal = Long.parseLong(input,16);
		String binary = Long.toBinaryString(numVal);
		
		//determine byte size and initial raw length of binary
		int size = findByteSize(input), length = binary.length();
		
		//resize binary to 21 bits regardless of label/# of bytes
		binary = Resize(binary, 21);
		
		//get new binary
		binary = buildBinaryUTF8(binary, findLabel(size));
			
		return Long.toHexString(Long.parseLong(binary,2));
	}
	
	/***
	 * Returns the UTF16 equivalent of the input value
	 * @param input Valid input Unicode value in hexadecimal from 0x0000 to 0x10FFFF, without prefix.
	 * @return UTF16 equivalent of the input value
	 */
	public String GetUTF16(String input) {
		String output = "";
		long numVal = Long.parseLong(input,16); 	//converts hex string into decimal equivalent

		if(numVal > Long.parseLong("FFFF",16)){ //code points 0x10000-0x10FFFF
			
			//subtract 0x10000 to the input value
			long tempVal = numVal - Long.parseLong("010000",16); 
			
			//convert to binary and split into left and right segments
			String binary = Resize(Long.toBinaryString(tempVal),20); //convert to binary
			String binLeft = binary.substring(0,10), binRight = binary.substring(10,20); //split into left & right
			
			//Add d800 and dc00 to left and right respectively
			long left = Long.parseLong(binLeft,2) +  Long.parseLong("D800",16);
			long right = Long.parseLong(binRight,2) + Long.parseLong("DC00",16);
			
			output += Long.toHexString(left) + Long.toHexString(right);
		}	
		
		//default state where input is only 0x0000 to 0xFFFF
		return Resize(input, 4);
	}
	
	/**
	 * Returns the UTF32 equivalent of the input value
	 * @param input Valid input Unicode value in hexadecimal.
	 * @return UTF32 equivalent of the input value
	 */
	public String GetUTF32(String input) {
		return Resize(input, 8);
	}
	
	
//===INTERNAL FUNCTIONALITY===
	
	
	/**
	 * For both binary and hexadecimal values.
	 * Adjusts the String to the specified binary/hex digits by filling in zeroes on its left side.
	 * @param input String value to resize, either in hexadecimal or binary.
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
	
	/**
	 * Finds the byte size of a given hexadecimal input.
	 * @param input Hexadecimal value from 0x0000 to 0x1FFFFF
	 * @return Byte size of the given input (1-4)
	 */
	private int findByteSize(String input) { //placed here incase it will be converted as public method
		/**
		 * 1byte: 	0<=I<=127
		 * 2bytes: 	128<=I<=2047
		 * 3bytes: 	2048<=I<=65535
		 * 4bytes: 	65536<=I<=2097151
		 */
		int numVal = Integer.parseInt(input,16); //convert to decimal equivalent
		
		if(0<=numVal && numVal<=127)
			return 1;
		else if(128<=numVal && numVal<=2047)
			return 2;
		else if(2048<=numVal && numVal<=65535)
			return 3;
		else if(65536<=numVal && numVal<=2097151)
			return 4;
		
		return -1; //invalid since value not within valid input range
	}
	
	/**
	 * Determines the UTF8 label equivalent to the given Unicode input
	 * @param input Hexadecimal value from 0x0000 to 0x1FFFFF
	 * @return Label value of the given input (7,11,16,21)
	 */
	private int findLabel(String input) {
		/**
		 * Decimal equivalents of the hexadecimal ranges
		 * 7: 	0<=I<=127
		 * 11: 	128<=I<=2047
		 * 16: 	2048<=I<=65535
		 * 21: 	65536<=I<=2097151
		 */
		int numVal = Integer.parseInt(input,16); //convert to decimal equivalent
		
		if(0<=numVal && numVal<=127)
			return 7;
		else if(128<=numVal && numVal<=2047)
			return 11;
		else if(2048<=numVal && numVal<=65535)
			return 16;
		else if(65536<=numVal && numVal<=2097151)
			return 21;
		
		return -1; //invalid since value not within valid input range
	}
	
	/**
	 * Determines the UTF8 label equivalent to the given the byte size
	 * @param input Byte size (1,2,3,4)
	 * @return Label value of the given input (7,11,16,21)
	 */
	private int findLabel(int size) {
		if(size == 1)
			return 7;
		if(size == 2)
			return 11;
		if(size == 3)
			return 16;
		if(size == 4)
			return 21;
		
		return -1;
	}
	
	/**
	 * Builds the binary equivalent of the UTF8 of the given Unicode
	 * @param input Unicode value that was resized to 21 binary digits
	 * @param label Label value of the input value
	 * @return Binary UTF8 of the input
	 */
	private String buildBinaryUTF8(String input, int label) {
		String output = "";
		//Index Reference: points to the individual characters in String binary
		//Note that -2 and -1 represent 0 and 1 respectively
		int[][] indexRef = {
				{-1,14,15,16,17,18,19,20}, //0xxxxxxx 8
				{-1,-1,-2,10,11,12,13,14,-1,-2,15,16,17,18,19,20}, //110xxxxx 10xxxxxx 16
				{-1,-1,-1,-2,5,6,7,8,-1,-2,9,10,11,12,13,14,-1,-2,15,16,17,18,19,20}, //1110xxxx 10xxxxxx 10xxxxxx 24
				{-1,-1,-1,-1,-2,0,1,2,-1,-2,3,4,5,6,7,8,-1,-2,9,10,11,12,13,14,-1,-2,15,16,17,18,19,20} //11110xxx 10xxxxxx 10xxxxxx 10xxxxxx 32
		};
		
		//determine master index value
		int idx = 0, range = 0;
		if(label == 7) {
			idx = 0;
			range = 8;
		}
		else if(label == 11) {
			idx = 1;
			range = 16;
		}
		else if(label == 16) {
			idx = 2;
			range = 24;
		}
		else if(label == 21) {
			idx = 3;
			range = 32;
		}
			
		
		//build the utf-8 binary string
		for(int i = 0; i < range; i++) {
			if(indexRef[idx][i] == -2)
				output += "0";
			else if(indexRef[idx][i] == -1)
				output += "1";
			else
				output += input.charAt(indexRef[idx][i]) + "";
		}
		
		return output;
	}
}
