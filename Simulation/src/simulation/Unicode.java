	package simulation;

	/**
	 * Unicode
	 * 
	 * Conducts the finds the UTF equivalent of a given Unicode value.
	 * 
	 * Beware that this class does not yet check the validity of the input. 
	 * It is recommended for the user to check and validate the values being entered
	 * such that is within the allowable range of UTF-8, UTF-16, UTF-32.
	 * 
	 */
	public class Unicode {
		/**
		 * Constructor with Unicode parameter required.
		 * @param input Validated Unicode value in hexadecimal (Without prefixes)
		 */
		public Unicode(String input){
			this.unicode = input;
			this.utf8 = FindUTF8(input);
			this.utf16 = FindUTF16(input);
			this.utf32 = FindUTF32(input);
		}
		
		/**
		 * Constructor with no Unicode parameter required.
		 * Do ensure to set Unicode value using SetUnicode()
		 * prior to use of Get functions.
		 */
		public Unicode() {
			this.utf8 = this.utf16 = this.utf32 = ""; //default state
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
		}
		
		/**
		 * Returns all UTF equivalents as a String[].
		 * Ensure that the Unicode value was set 
		 * prior to use, otherwise it will return an empty value.
		 * @return String array containing UTF8,-16,-32 equivalent of the Unicode respectively, null if no Unicode was found.
		 */
		public String[] GetAll() {
			String[] list = new String[3];
			//no unicode value, thus cannot or didn't compute
			if(unicode.isEmpty())
				return null;
			
			//Find UTF values if for some reason it does not exist
			//even if it should at this stage
			if(this.utf8.isEmpty())
				this.utf8 = FindUTF8(unicode);
			if(this.utf16.isEmpty())
				this.utf16 = FindUTF16(unicode);
			if(utf32.isEmpty())
				this.utf32 = FindUTF32(unicode);
			
			list[0] = this.utf8;
			list[1] = this.utf16;
			list[2] = this.utf32;
			return list;
		}
		
		/**
		 * Returns the computed UTF-8 value
		 * @return UTF8 value of the Unicode, returns null if no Unicode was given prior to call.
		 */
		public String GetUTF8() {
			if(this.utf8.isEmpty())
				return null;
			return this.utf8;
		}
		
		/**
		 * Returns the computed UTF-16 value
		 * @return UTF16 value of the Unicode, returns null if no Unicode was given prior to call.
		 */
		public String GetUTF16() {
			if(this.utf16.isEmpty())
				return null;
			return this.utf16;
		}
		
		/**
		 * Returns the computed UTF-16 value
		 * @return UTF32 value of the Unicode, returns null if no Unicode was given prior to call.
		 */
		public String GetUTF32() {
			if(this.utf32.isEmpty())
				return null;
			return this.utf32;
		}
	//===INTERNAL FUNCTIONALITY===

		private String unicode;
		private String utf8;
		private String utf16;
		private String utf32;
		
		/***
		 * Computes for the UTF8 equivalent of the input value
		 * @param input Valid input Unicode value in hexadecimal from 0x0000 to 0x1FFFFF, without prefix
		 * @return UTF8 equivalent of the input value.
		 */
		private String FindUTF8(String input) {
			long numVal = Long.parseLong(input,16);
			String binary = Long.toBinaryString(numVal);
			
			//determine byte size and initial raw length of binary
			int size = findByteSize(input);
			
			//resize binary to 21 bits regardless of label/# of bytes
			binary = Resize(binary, 21);
			
			//get new binary
			binary = buildBinaryUTF8(binary, findLabel(size));
				
			return Long.toHexString(Long.parseLong(binary,2)).toUpperCase();
		}
		
		/***
		 * Computes for the UTF16 equivalent of the input value
		 * @param input Valid input Unicode value in hexadecimal from 0x0000 to 0x10FFFF, without prefix.
		 * @return UTF16 equivalent of the input value
		 */
		private String FindUTF16(String input) {
			String output = Resize(input, 4); //Default state where input is only from 0x0000 to 0xFFFF
			long numVal = Long.parseLong(input,16); 	//converts hex string into decimal equivalent

			if(numVal > Long.parseLong("FFFF",16)){ //code points 0x10000-0x10FFFF
				output = "";//reset output 
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
			return output.toUpperCase();
		}
		
		/**
		 * Computes for the UTF32 equivalent of the input value
		 * @param input Valid input Unicode value in hexadecimal as long as it is at most 8 hex digits.
		 * @return UTF32 equivalent of the input value
		 */
		private String FindUTF32(String input) {
			return Resize(input, 8).toUpperCase();
		}
		
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
			if(size == 1) //1byte
				return 7;
			if(size == 2) //2bytes
				return 11;
			if(size == 3) //3bytes
				return 16;
			if(size == 4) //4bytes
				return 21;
			
			return -1; //assumes invalid input
		}
		
		/**
		 * Builds the binary equivalent of the UTF8 of the given Unicode
		 * @param input Unicode value that was resized to 21 binary digits where 0 MSBs are just place holders if incase not all 21 bits are not used
		 * @param label Label value of the input value which will dictate the number of characters to use from input parameter.
		 * @return Binary UTF8 of the input
		 */
		private String buildBinaryUTF8(String input, int label) {
			String output = "";
			//Index Reference: points to the individual characters in String input
			//Note that -2 and -1 represent 0 and 1 respectively
			int[][] indexRef = {
					{-1,14,15,16,17,18,19,20}, //0xxxxxxx 8
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
