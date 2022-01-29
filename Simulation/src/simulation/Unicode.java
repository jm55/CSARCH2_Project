package simulation;

/***
 * Unicode
 * 
 * Conducts the finds the UTF equivalent of a given Unicode value.
 * 
 * Beware that this class does not yet check the validity of the input. 
 * It is recommended for the user to check and validate the values being entered
 * such that is within the allowable range of UTF-8, UTF-16, UTF-32.
 */
public class Unicode {
	/**
	 * Constructor with Unicode parameter required.
	 * @param input Validated Unicode value in hexadecimal (Without prefixes)
	 */
	public Unicode(String input){
		this.unicode = input.toUpperCase();
		this.utf8 = FindUTF8(this.unicode);
		this.utf16 = FindUTF16(this.unicode);
		this.utf32 = FindUTF32(this.unicode);
		this.unicodeChar = FindChar(this.unicode);
	}
	
	/**
	 * Constructor with no Unicode parameter required.
	 * Do ensure to set Unicode value using SetUnicode()
	 * prior to use of Get functions.
	 */
	public Unicode() {
		this.utf8 = this.utf16 = this.utf32 = ""; //default state
		this.unicodeChar = '\0';
	}
	
	/**
	 * Sets new Unicode value for conversion to equivalent UTF8,-16,-32
	 * values.
	 * @param input Valid Unicode value to be set as new Unicode for conversion
	 */
	public void SetUnicode(String input){
		this.unicode = input;
		this.utf8 = FindUTF8(this.unicode);
		this.utf16 = FindUTF16(this.unicode);
		this.utf32 = FindUTF32(this.unicode);
		this.unicodeChar = FindChar(this.unicode);
	}
	
	/**
	 * Returns the input Unicode value of this object.
	 * @return Returns the inputted Unicode if available, null if otherwise.
	 */
	public String GetUnicode() {
		if(this.unicode.isEmpty())
			return null;
		return this.unicode;
	}
	
	/**
	 * Returns all UTF equivalents as a String[].
	 * Ensure that the Unicode value was set 
	 * prior to use, otherwise it will return an empty value.
	 * @return String array containing Unicode,UTF8,-16,-32,Char equivalent of the Unicode respectively, null if no Unicode was found.
	 */
	public String[] GetAll() {
		String[] list = new String[5];
		//no unicode value, thus cannot or didn't compute
		if(unicode.isEmpty())
			return null;
		
		//Find UTF values if for some reason it does not exist
		//even if it should at this stage
		if(this.utf8.isEmpty())
			this.utf8 = FindUTF8(unicode);
		if(this.utf16.isEmpty())
			this.utf16 = FindUTF16(unicode);
		if(this.utf32.isEmpty())
			this.utf32 = FindUTF32(unicode);
		if(this.unicodeChar == '\0')
			this.unicodeChar = FindChar(unicode);
		
		list[0] = this.unicode;
		list[1] = this.utf8;
		list[2] = this.utf16;
		list[3] = this.utf32;
		list[4] = this.unicodeChar + "";
		return list;
	}
	
	/**
	 * Returns the CSV format of the Unicode value
	 * @param input True if include input, false if otherwise
	 * @return 
	 */
	public String GetCSV(boolean input, boolean spaced) {
		String[] arr = GetAll();
		String output = "";
		if(input)
			output = this.unicode + spacedComma(spaced);
		
		output += arr[1] + spacedComma(spaced) + arr[2] + spacedComma(spaced) + arr[3] + spacedComma(spaced) + arr[4];
		return output;
	}
	
	/**
	 * Returns the computed UTF-8 value
	 * @return UTF8 value of the Unicode, returns null if no Unicode was given prior to call.
	 */
	public String GetUTF8() {
		if(this.unicode.isEmpty())
			return null;
		return this.utf8;
	}
	
	/**
	 * Returns the computed UTF-16 value
	 * @return UTF16 value of the Unicode, returns null if no Unicode was given prior to call.
	 */
	public String GetUTF16() {
		if(this.unicode.isEmpty())
			return null;
		return this.utf16;
	}
	
	/**
	 * Returns the computed UTF-16 value
	 * @return UTF32 value of the Unicode, returns null if no Unicode was given prior to call.
	 */
	public String GetUTF32() {
		if(this.unicode.isEmpty())
			return null;
		return this.utf32;
	}
	
	/**
	 * Returns the equivalent char of the Unicode value
	 * @return Char equivalent of the Unicode, returns null if no Unicode was given prior to call.
	 */
	public char GetChar() {
		if(this.unicode.isEmpty())
			return '\0';
		return this.unicodeChar;
	}

	public String GetFormatted(){
		return this.GetUTF8() + " " + this.GetUTF16() + " " + this.GetUTF32();
	}
//===INTERNAL FUNCTIONALITY===

	private String unicode;
	private String utf8;
	private String utf16;
	private String utf32;
	private char unicodeChar;
	
	/**
	 * Returns the equivalent character for a given hexadecimal Unicode value
	 * @param input Valid Unicode value in hexadecimal
	 * @return Character equivalent of the input Unicode value
	 */
	private char FindChar(String input) {
		return (char)Long.parseLong(input,16);
	}
	
	/***
	 * Computes for the UTF8 equivalent of the input value
	 * @param input Valid Unicode value in hexadecimal from 0x0000 to 0x1FFFFF, without prefix
	 * @return UTF8 equivalent of the input Unicode value.
	 */
	private String FindUTF8(String input) {
		if(Long.parseLong(input,16) > Long.parseLong("1FFFFF",16)) //check if value is too big for UTF8
			return "N/A";

		//convert input to longdecimal then to binary for retrieving bits
		String binary = Long.toBinaryString(Long.parseLong(input,16)); 	
		
		//determine byte size and initial raw length of binary
		int size = findByteSize(input);
		
		//resize binary to 21 bits regardless of label/# of bytes
		binary = Resize(binary, 21,false);

		//get new binary
		binary = buildBinaryUTF8(binary, findLabel(size));

		String output = Resize(Long.toHexString(Long.parseLong(binary,2)).toUpperCase(),8,false);
		return Resize(output,8,false).toUpperCase();
	}
	
	/***
	 * Computes for the UTF16 equivalent of the input value
	 * @param input Valid input Unicode value in hexadecimal from 0x0000 to 0x10FFFF, without prefix.
	 * @return UTF16 equivalent of the input value
	 */
	private String FindUTF16(String input) {
		String output = Resize(input, 8,false); //Default state where input is only from 0x0000 to 0xFFFF
		long numVal = Long.parseLong(input,16); 	//converts hex string into decimal equivalent

		if(numVal > Long.parseLong("FFFF",16)){ //for code points 0x10000-0x10FFFF
			output = "";//reset output 
			
			long tempVal = numVal - Long.parseLong("010000",16); //subtract 0x10000 to the input value

			//convert to binary and split into left and right segments
			String binary = Resize(Long.toBinaryString(tempVal),20,false); //convert to binary
			String binLeft = binary.substring(0,10), binRight = binary.substring(10,20); //split into left & right
			//Add d800 and dc00 to left and right respectively
			long left = Long.parseLong(binLeft,2) +  Long.parseLong("D800",16);
			long right = Long.parseLong(binRight,2) + Long.parseLong("DC00",16);
			//combine resulting values as hex string
			output = Long.toHexString(left) + Long.toHexString(right); 
		}
		return Resize(output,8,false).toUpperCase();
	}
	
	/**
	 * Computes for the UTF32 equivalent of the input value
	 * @param input Valid input Unicode value in hexadecimal as long as it is at most 8 hex digits.
	 * @return UTF32 equivalent of the input value
	 */
	private String FindUTF32(String input) {
		return Resize(input, 8,false).toUpperCase(); //simply resize input to have 8 hex digits
	}
	
	/**
	 * For both binary and hexadecimal values.
	 * Adjusts the String to the specified binary/hex digits by filling in MSB (true) or zeroes (false) on its left side.
	 * @param input String value to resize, either in hexadecimal or binary.
	 * @param size Number of specified binary/hex digits
	 * @param msb True if use MSB as fill-in, false (default) if use 0 as fill-in
	 * @return Resized equivalent of the input value.
	 */
	private String Resize(String input, int size, boolean msb) {
		String output = "";
		if(input.length() < size)
			for(int i = 0; i < size-input.length(); i++)
				if(msb)
					output += input.charAt(0);
				else
					output += '0';
		return output+input;
	}
	
	/**
	 * For hexadecimal only.
	 * Adjusts the String to the specified number of hex digits by filling in zeroes on its left side.
	 * @param input Integer value to resize. Will be converted automatically to hex equivalent.
	 * @param size Number of specified binary/hex digits
	 * @return Resized equivalent of the input value.
	 */
	private String Resize(int input, int size) {
		String converted = Integer.toHexString(input);
		String output = "";
		if(converted.length() < size)
			for(int i = 0; i < size-converted.length(); i++)
				output += '0';
		return output+converted;
	}
	
	/**
	 * Finds the byte size of a given hexadecimal input.
	 * @param input Hexadecimal value from 0x0000 to 0x1FFFFF
	 * @return Byte size of the given input (1-4)
	 */
	private int findByteSize(String input) {
		/**
		 * Byte equivalent of hexadecimal ranges
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
		 * Label equivalents of the hexadecimal ranges
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
	 * @param size Byte size (1,2,3,or 4)
	 * @return Label value of the given input (7,11,16,or 21)
	 */
	private int findLabel(int size) {
		switch(size) {
		case 1:
			return 7;
		case 2:
			return 11;
		case 3:
			return 16;
		case 4:
			return 21;
		default:
			return -1;
		}
	}
	
	/**
	 * Builds the binary equivalent of the UTF8 of the given Unicode
	 * @param input Unicode value that was resized to 21 binary digits where 0 MSBs are just place holders if incase not all 21 bits are not used
	 * @param label Label value of the input value which will dictate the number of characters to use from input parameter.
	 * @return Binary UTF8 of the input
	 */
	private String buildBinaryUTF8(String input, int label) {
		String output = "";
		/**
         * Index Reference: points to the individual characters in String input
         * Note that -2 and -1 represent 0 and 1 respectively
         * 
         * Given a resized binary input (w/ 21 binary digits regardless of Unicode value),
         * it will assemble the UTF8 value by incrementing from 0->(range-1) 
         * indices of the resulting output string. For each iteration, it will 
         * use the char index of the input parameter to point to which and what binary digit it should use.
         * 
         * Example:
         *  		   abc(3) defghi(2) jklmno(1) pqrstu(0)
         * U+245D6 === 000(3) 100100(2) 010111(1) 010110(0) (in 21 characters with effective indices of 0-20)
         * Range: 32bits === 11110abc(3) 10defghi(2) 10jklmno(1) 10pqrstu(0)
         * Thus it will make use of the following index values:
         * [-1,-1,-1,-1,-2,0,1,2,-1,-2,3,4,5,6,7,8,-1,-2,9,10,11,12,13,14,-1,-2,15,16,17,18,19,20]
         *   1  1  1  1  0 0 0 0  1  0 1 0 0 1 0 0  1  0 0  1  0  1  1  1  1  0  0  1  0  1  1  0
         */
		int[][] indexRef = {
				{-2,14,15,16,17,18,19,20}, //0xxxxxxx 8
				{-1,-1,-2,10,11,12,13,14,-1,-2,15,16,17,18,19,20}, //110xxxxx 10xxxxxx 16
				{-1,-1,-1,-2,5,6,7,8,-1,-2,9,10,11,12,13,14,-1,-2,15,16,17,18,19,20}, //1110xxxx 10xxxxxx 10xxxxxx 24
				{-1,-1,-1,-1,-2,0,1,2,-1,-2,3,4,5,6,7,8,-1,-2,9,10,11,12,13,14,-1,-2,15,16,17,18,19,20} //11110xxx 10xxxxxx 10xxxxxx 10xxxxxx 32
		};
		
		//Determine master index value
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
		
		//Build the UTF-8 binary string
		for(int i = 0; i < range; i++) {
			switch(indexRef[idx][i]) {
				case -2:
					output += "0";
					break;
				case -1:
					output += "1";
					break;
				default:
					output += input.charAt(indexRef[idx][i]) + "";
					break;
			}
		}
		return output;
	}
	
	/**
	 * Prints comma and space combination (i.e. "," or ", ") whenever spaced is True
	 * @param spaced Indicates if a space should follow the comma
	 * @return String of comma or spaced-comma
	 */
	private String spacedComma(boolean spaced) {
		if(spaced)
			return ", ";
		return ",";
	}
}
