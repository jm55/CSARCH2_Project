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

class Unicode{
    constructor(){
        this.TestUnicode();
        this.unicode = "";
        this.utf8 = "";
        this.utf16 = "";
        this.utf32 = "";
        this.unicodeChar = "";
    }
    /**
     * Sets new Unicode value for conversion to equivalent UTF8,-16,-32, and char values.
     * @param {String} input Valid Unicode value to be set as new Unicode for conversion
     */
    SetUnicode(input){
        this.unicode = input;
        this.utf8 = this.FindUTF8(this.unicode);
        this.utf16 = this.FindUTF16(this.unicode);
        this.utf32 = this.FindUTF32(this.unicode);
        this.unicodeChar = this.FindChar(this.unicode);
    }

    /**
     * Returns all UTF equivalents as a String[].
     * Ensure that the Unicode value was set 
     * prior to use, otherwise it will return an empty value.
     * @returns String array containing [Unicode,UTF-8,-16,-32,Char] equivalent of the Unicode respectively, null if no Unicode was found.
     */
    get GetAll(){
        var list = new Array(5);

        if(this.unicode.length === 0)
            return null;

        if(this.utf8.length === 0)
            this.utf8 = this.FindUTF8(this.unicode);
        if(this.utf16.length === 0)
            this.utf16 = this.FindUTF16(this.unicode);
        if(this.utf32.length === 0)
            this.utf32 = this.FindUTF32(this.unicode);
        if(this.unicodeChar.length === 0)
            this.unicodeChar = this.FindChar(this.unicode);

        list = [this.unicode, this.utf8, this.utf16, this.utf32, this.unicodeChar];

        return list;
    }

    /**
     * Returns the input unicode value
     * @returns Unicode input that was set via SetUnicode();
     */
    get GetUnicode(){
        if(this.unicode.length === 0)
            return null;
        return this.unicode;
    }

    /**
     * Returns the computed UTF-8 value
     * @returns UTF8 value of the Unicode, returns null if no Unicode was given prior to call.
     */
    get GetUTF8(){
        if(this.unicode.length === 0)
            return null;
        return this.utf8;
    }

    /**
     * Returns the computed UTF-16 value
     * @returns UTF16 value of the Unicode, returns null if no Unicode was given prior to call.
     */
    get GetUTF16(){
        if(this.unicode.length === 0)
            return null;
        return this.utf16;
    }

    /**
     * Returns the computed UTF-16 value
     * @returns UTF32 value of the Unicode, returns null if no Unicode was given prior to call.
     */
    get GetUTF32(){
        if(this.unicode.length === 0)
            return null;
        return this.utf32;
    }

    /***
     * Returns the equivalent char of the Unicode.
     * @returns Char equivalent of the Unicode, returns null if no Unicode was given prior to call.
     */
    get GetChar(){
        if(this.unicode.length === 0)
            return null;
        return this.unicodeChar;
    }

    /***
     * Returns the formatted output string as specified in the specifications.
     * @returns Formatted output xx xx xx where each xx is the UTF-8, UTF-16, and UTF-32 respectively
     */
    get GetFormatted(){
        return this.GetUTF8 + " " + this.GetUTF16 + " " + this.GetUTF32;
    }

    //====INTERNAL FUNCTIONALITY====
    /**
     * PRIVATE
     * Computes for the char equivalent of the input value
     * @param {String} input Valid input Unicode in hexadecimal from 0x0000 to 0x10FFFF, without prefix
     * @returns Char equivalent of the input value.
     */
    FindChar(input){
        return String.fromCharCode(parseInt(input,16));//turns out it is only capable of reading utf-16 values
        //https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/fromCharCode 
    }

    /**
     * PRIVATE
     * Computes for the UTF8 equivalent of the input value
     * @param {String} input Valid input Unicode in hexadecimal from 0x0000 to 0x10FFFF, without prefix
     * @returns UTF8 equivalent of the input value.
     */
    FindUTF8(input){
        if(parseInt(input, 16) > parseInt("1FFFFF", 16)) //check if value is too big for UTF8
            return "N/A";
        var binary = this.Resize(parseInt(input, 16).toString(2),21); //hex->dec->bin; validated (String) then resize to 21bits
        binary = this.buildBinaryUTF8(binary, this.findLabel(this.findByteSize(input))); //convert binary to utf8
        return this.Resize(parseInt(binary,2).toString(16).toUpperCase(),8); //return resulting hex value, resized to 8 hex digits
    }

    /**
     * PRIVATE
     * Computes for the UTF16 equivalent of the input value
     * @param {String} input Valid input Unicode value in hexadecimal from 0x0000 to 0x10FFFF, without prefix.
     * @returns UTF16 equivalent of the input value
     */
    FindUTF16(input){
        var numVal = parseInt(input, 16); //decimal equivalent of hex input
        if(numVal > parseInt("FFFF",16)){
            //0x10000 = 65536
            //D800 = 55296
            //DC00 = 56320
            var binary = this.Resize((numVal-65536).toString(2),20); //subtract 0x10000 from input then convert to binary and split to left and right segments
            var left = (parseInt(binary.substring(0,10),2) + 55296).toString(16); //add left_bin and 0xD800 return as hex
            var right = (parseInt(binary.substring(10,20),2) + 56320).toString(16); //add right_bin and 0xDC00 return as hex
            return this.Resize((left+right).toUpperCase(),8); //return resulting hex value, resized to 8 hex digits
        } 
        return this.Resize(input.toUpperCase(),8); 
    }

    /**
     * PRIVATE
     * Computes for the UTF32 equivalent of the input value
     * @param {String} input Valid input Unicode value in hexadecimal as long as it is at most 8 hex digits.
     * @returns UTF32 equivalent of the input value
     */
    FindUTF32(input){
        return this.Resize(input.toUpperCase(),8); //return the same input hex value, resized to 8 hex digits
    }

    /**
     * PRIVATE
     * For both binary and hexadecimal values.
     * Adjusts the String to the specified binary/hex digits by filling in zeroes on its left side.
     * @param {String} input String value to resize, either in hexadecimal or binary.
     * @param {Number} size Number of specified binary/hex digits
     * @param {boolean} msb True if use msb as fill-in value, false (default) if use 0 as fill-in value.
     * @returns Resized equivalent of the input value
     */
    Resize(input, size, msb=false){
        if(input.length >= size)
            return input;
        else{
            var c = "0";
            if(msb)
                c = input.substring(0,1);
            for(var i = 0; i < size-input.length; i++) //create fill-in by difference off target size and input length
                c += c.substring(0,1);
            return c+input; //return resized input
        }
    }

    /**
     * PRIVATE
     * Finds the byte size of a given hexadecimal input.
     * Used for UTF8
     * @param {String} input Hexadecimal value from 0x0000 to 0x1FFFFF
     * @returns Byte size of the given input (1-4)
     */
    findByteSize(input){
        /**
         * Byte equivalent of hexadecimal ranges
         * 1byte: 	0<=I<=127
         * 2bytes: 	128<=I<=2047
         * 3bytes: 	2048<=I<=65535
         * 4bytes: 	65536<=I<=2097151
         */
        var numVal = parseInt(input, 16);

        if(0<=numVal && numVal<=127)
            return 1;
        else if(128<=numVal && numVal<=2047)
            return 2;
        else if(2048<=numVal && numVal<=65535)
            return 3;
        else if(65536<=numVal && numVal<=2097151)
            return 4;
        else
            return -1;
    }

    /**
     * PRIVATE
     * Determines the UTF8 label equivalent to the given the byte size
     * Used for UTF8
     * @param {Number} size Byte size (1,2,3,or 4)
     * @returns Label value of the given input (7,11,16,or 21)
     */
    findLabel(size){
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
     * PRIVATE
     * Builds the binary equivalent of the UTF8 of the given Unicode
     * @param {String} input Unicode value that was resized to 21 binary digits where 0 MSBs are just place holders if incase not all 21 bits are not used
     * @param {Number} label Label value of the input value which will dictate the number of characters to use from input parameter.
     * @returns Binary UTF8 of the input 
     */
    buildBinaryUTF8(input, label){
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
        const indexRef = [
            [-2,14,15,16,17,18,19,20], //0xxxxxxx 8
            [-1,-1,-2,10,11,12,13,14,-1,-2,15,16,17,18,19,20], //110xxxxx 10xxxxxx 16
            [-1,-1,-1,-2,5,6,7,8,-1,-2,9,10,11,12,13,14,-1,-2,15,16,17,18,19,20], //1110xxxx 10xxxxxx 10xxxxxx 24
            [-1,-1,-1,-1,-2,0,1,2,-1,-2,3,4,5,6,7,8,-1,-2,9,10,11,12,13,14,-1,-2,15,16,17,18,19,20] //11110xxx 10xxxxxx 10xxxxxx 10xxxxxx 32
        ];

        var output = "";
        var idx = 0;
        var range = 0;
        
        switch(label){
            case 7:
                idx = 0;
                range = 8;
                break;
            case 11:
                idx = 1;
                range = 16;
                break;
            case 16:
                idx = 2;
                range = 24;
                break;
            case 21:
                idx = 3;
                range = 32;
                break;
            default:
                break;
        }
        //iterate through the constants and input char indexes
        for(var i = 0; i < range; i++) {
            switch(indexRef[idx][i]) {
				case -2:
					output += '0';
					break;
				case -1:
					output += '1';
					break;
				default:
					output += input.charAt(indexRef[idx][i]);
					break;
			}
        }
        return output;
    }
    TestUnicode(){
        console.log("Unicode online!");
	}
}

export {Unicode};