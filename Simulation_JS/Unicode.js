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
	TestUnicode(){
        console.log("Unicode online!");
	}
	
    constructor(){

    }
    /**
     * Sets new Unicode value for conversion to equivalent UTF8,-16,-32
     * values.
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
     * @returns String array containing Unicode,UTF-8,-16,-32,Char equivalent of the Unicode respectively, null if no Unicode was found.
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

        list[0] = this.unicode;
        list[1] = this.utf8;
        list[2] = this.utf16;
        list[3] = this.utf32;
        list[4] = this.unicodeChar;

        //list = [this.unicode, this.utf8, this.utf16, this.utf32, this.unicodeChar]

        return list;
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
        return String.fromCharCode(parseInt(input,16));
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
        
        var numVal = parseInt(input , 16); //validated, turns out that 'int' in js has x64 range
        var binary = numVal.toString(2); //validated (String)

        var size = this.findByteSize(input);

        binary = this.Resize(binary, 21);

        binary = this.buildBinaryUTF8(binary, this.findLabel(size));

        return this.Resize(parseInt(binary , 2).toString(16).toUpperCase(),8); // Long.toHexString(Long.parseLong(binary,2)).toUpperCase();
    }

    /**
     * PRIVATE
     * Computes for the UTF16 equivalent of the input value
     * @param {String} input Valid input Unicode value in hexadecimal from 0x0000 to 0x10FFFF, without prefix.
     * @returns UTF16 equivalent of the input value
     */
    FindUTF16(input){
        var output = this.Resize(input, 8);
        var numVal = parseInt(input, 16);
        
        if(numVal > parseInt("FFFF",16)){
            output = "";
            //subtract 0x10000 to the input value
            var tempVal = numVal - parseInt("010000",16);
            //convert to binary and split to left and right segments
            var binary = this.Resize(tempVal.toString(2),20);
            var binLeft = binary.substr(0,10);
            var binRight = binary.substr(10,10);
            
            var left = parseInt(binLeft,2) + parseInt("D800",16);
            var right = parseInt(binRight,2) + parseInt("DC00",16);

            output += left.toString(16) + right.toString(16) + "";
        }
        return this.Resize(output,8).toUpperCase();
    }

    /**
     * PRIVATE
     * Computes for the UTF32 equivalent of the input value
     * @param {String} input Valid input Unicode value in hexadecimal as long as it is at most 8 hex digits.
     * @returns UTF32 equivalent of the input value
     */
    FindUTF32(input){
        return this.Resize(input, 8).toUpperCase();
    }

    /**
     * PRIVATE
     * For both binary and hexadecimal values.
     * Adjusts the String to the specified binary/hex digits by filling in zeroes on its left side.
     * @param {String} input String value to resize, either in hexadecimal or binary.
     * @param {Number} size Number of specified binary/hex digits
     * @returns Resized equivalent of the input value
     */
    Resize(input, size){
        var output = "";
        if(input.length < size)
            for(var i = 0; i < size-input.length; i++)
                output += "0";
        return output+input;
    }
    /**
     * PRIVATE
     * Finds the byte size of a given hexadecimal input.
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

        return -1;
    }

    /**
     * PRIVATE
     * Determines the UTF8 label equivalent to the given the byte size
     * @param {Number} size Byte size (1,2,3,or 4)
     * @returns Label value of the given input (7,11,16,or 21)
     */
    findLabel(size){
        if(size === 1) //1byte
            return 7;
        if(size === 2) //2bytes
            return 11;
        if(size === 3) //3bytes
            return 16;
        if(size === 4) //4bytes
            return 21;
        
        return -1; //assumes invalid input
    }

    /**
     * PRIVATE
     * Builds the binary equivalent of the UTF8 of the given Unicode
     * @param {String} input Unicode value that was resized to 21 binary digits where 0 MSBs are just place holders if incase not all 21 bits are not used
     * @param {Number} label Label value of the input value which will dictate the number of characters to use from input parameter.
     * @returns Binary UTF8 of the input 
     */
    buildBinaryUTF8(input, label){
        var output = "";
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
         *  		  xxx(3) xxxxxx(2) xxxxxx(1) xxxxxx(0)
         * U+245D6 === 000(3) 100100(2) 010111(1) 010110(0) (in 21 characters with effective indices of 0-20)
         * Range: 32bits === 11110xxx(3) 10xxxxxx(2) 10xxxxxx(1) 10xxxxxx(0)
         * Thus it will make use of the following index values:
         * {-1,-1,-1,-1,-2,0,1,2,-1,-2,3,4,5,6,7,8,-1,-2,9,10,11,12,13,14,-1,-2,15,16,17,18,19,20}
         *   1  1  1  1  0 0 0 0  1  0 1 0 0 1 0 0  1  0 0  1  0  1  1  1  1  0  0  1  0  1  1  0
         */
        const indexRef = [
            [-2,14,15,16,17,18,19,20], //0xxxxxxx 8
            [-1,-1,-2,10,11,12,13,14,-1,-2,15,16,17,18,19,20], //110xxxxx 10xxxxxx 16
            [-1,-1,-1,-2,5,6,7,8,-1,-2,9,10,11,12,13,14,-1,-2,15,16,17,18,19,20], //1110xxxx 10xxxxxx 10xxxxxx 24
            [-1,-1,-1,-1,-2,0,1,2,-1,-2,3,4,5,6,7,8,-1,-2,9,10,11,12,13,14,-1,-2,15,16,17,18,19,20] //11110xxx 10xxxxxx 10xxxxxx 10xxxxxx 32
        ];

        var idx = 0;
        var range = 0;
        
        if(label === 7) {
            idx = 0;
            range = 8;
        }
        else if(label === 11) {
            idx = 1;
            range = 16;
        }
        else if(label === 16) {
            idx = 2;
            range = 24;
        }
        else if(label === 21) {
            idx = 3;
            range = 32;
        }
        for(var i = 0; i < range; i++) {
            if(indexRef[idx][i] === -2)
                output += "0";
            else if(indexRef[idx][i] === -1)
                output += "1";
            else
                output += input.charAt(indexRef[idx][i]) + "";
        }
        return output;
    }
}

export {Unicode};