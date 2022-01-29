/**
 * Checker
 * 
 * Will manage the checking and validation of inputs 
 * prior to conversion to equivalent UTF value.
 */

class Checker{
    TestChecker(){
        console.log("Checker online!");
    }

    constructor(){

    }
    /**
     * Checks and validates input value if allowed to be 
     * converted to equivalent UTF value.
     * @param {String} input Unicode value without any prefixes
     * @returns True if input is a valid Unicode value, False if otherwise.
     */
    CheckInput(input){
        var min = parseInt("0000", 16), max = parseInt("10FFFFF",16);
        /**
         * Will resort to Naive Approach 
         * since the initial implementation
         * of Regex does not work properly on all cases. 
         * 
         * If there are better solutions that does the same thing
         * albeit faster, please do add.
         * 
         * Simply check if it only contains the characters: ABCDEF0123456789 Non-Case Sensitive
         */
        var inputCaps = input.toUpperCase(); //just to simplify conditions (will only check ascii values of uppercase letters and numbers)
        if(inputCaps.length === 0)
            return null;
        inputCaps = inputCaps.replace(/\s/gm,""); //remove any whitespaces
        if(inputCaps.length === 4 && (inputCaps==="U+0X" || inputCaps === "0XU+"))
            return null;
        if(inputCaps.length>=4) 
            if(inputCaps.substring(0,4)===("U+0X") || inputCaps.substring(0,4)===("0XU+"))
                inputCaps = inputCaps.substring(4,input.length);
        else if(inputCaps.substring(0,2)===("U+") || inputCaps.substring(0,2)===("0X"))
            inputCaps = inputCaps.substring(2, inputCaps.length);

        if(inputCaps.length === 0) //if trimming resulted to empty string
            return null;

        for(var i = 0; i < inputCaps.length; i++) //65-70 = A-F & 48-57 = 0-9
            if((inputCaps.charCodeAt(i) < 48 || inputCaps.charCodeAt(i) > 57) && (inputCaps.charCodeAt(i) < 65 || inputCaps.charCodeAt(i) > 70))
                return null;

        if(parseInt(inputCaps,16) < min || parseInt(inputCaps, 16) > max)
            return null;
        
        return inputCaps;
    }

    /**
     * Checks and validates input value if allowed to be 
     * converted to equivalent UTF value.
     * @param {String} input  Unicode value with("U+")/without prefix.
     * @returns True if a valid Unicode (after fixing) or not. 
     */
    CheckInputBool(input){
        if(this.CheckInput(input) != null)
            return true;
        return false;
    }
}

export {Checker};