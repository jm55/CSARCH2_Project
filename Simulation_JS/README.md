# CSARCH2_Project (JS Build)
Simulation Project
Topic: Unicode

The goal of the project is to create an application that is capable of turning a Unicode value into its equivalent UTF-8, UTF-16, and UTF-32 value.

## Group 7
1. Alon-alon, Jason Miguel
2. Cruz, Julianne Felice
3. De Guzman, Cyril Ethan
4. Escalona, Jose Miguel
5. Rebong, Leana Hyacinth
6. Roncal, Raphael
7. Turk, Chadi

## Description
1. JS Implementation of the Project
2. Tested on Node.js via Terminal Outputs
3. Contains the same capabilities as the Java version.
## Sample use:
```
import {Checker} from './Checker.js';
import {Unicode} from './Unicode.js'

let c = new Checker();
let u = new Unicode();
var list = ["245D6","1CAFE","42069","Youtube","Meta","ABCDEF","10FFFFF",
        "1FFFFF","","U+ABCDEF","U+3041","U+0x3086","0xU+308C","3044","0x306A","zxcvbnm","0x10FFFF",
        "0x10FFF","10FF", "u+10F", "U+10","0x","U+0x","U+","U+AB","0xAB"];

for(var i = 0; i < list.length; i++){
    if(c.CheckInput(list[i]) != null
        //SET
        u.SetUnicode(c.CheckInput(list[i])); //Set the Unicode for computation
        
        //GET
        var all = u.GetAll; //Get as list containing [Unicode,UTF-8,-16,-32,Char]
        var formatted = u.GetFormatted; //Get as a formatted string output (xx xx xx)
        var unicode = u.GetUnicode; //Get Unicode value only
        var utf8 = u.GetUTF8; //Get UTF-8 value only
        var utf16 = u.GetUTF16; //Get UTF-16 value only
        var utf32 = u.GetUTF32; //Get UTF-32 value only
        var char = u.GetChar; //Get Char value only
    }
}
```
Try executing [Main_Terminal.js](https://github.com/cyril-deguzman/ascii-converter/blob/master/others/Simulation_JS/Main_Terminal.js) via `node Main_Terminal.js` on cmd for sample run of similar code.
### Recommended Methods/Functions
* **Checker**
	* **CheckInput(input)**
		* **Input**: input (String): Raw unicode input
		* **Output**: Cleaned-up version of the string (valid), or null (!valid).
		* **Notes**:
			* Check input if valid for use. 
			* Attempts to clean-up the input if possible (i.e. ommitting prefixes U+,0x,U+0x).
			* Disregards casing for checking.
			* Only accepts inputs 'ABCDEF0123456789' in any case.
			* Sample cases
	* **CheckInputBool(input)**
		* **Input**: input (String) - Raw unicode input
		* **Output**: Cleaned-up version of the string (valid), or null (!valid).
		* **Note**: Acts same to `Checkinput()` but returns true if valid and false if !valid
    * **TestChecker()**
    	* Simply call to check if class file can be called externally (e.g. via importation)
    	* Prints `Checker online!`
* **Unicode**
	* **SetUnicode(input)**
		* **Input**: input (String) - Valid unicode value without prefixes.
		* **Note**: Must be called prior to use of getters. 
	* **GetAll**: Returns a String array containing [Unicode,UTF-8,-16,-32,Char]
	* **GetFormatted**: Formatted output xx xx xx where each xx is the UTF-8, UTF-16, and UTF-32 respectively
	* **GetUnicode, GetUTF8, GetUTF16, GetUTF32, GetChar**
		* **Notes**: 
			* UTF8,UTF16,UTF32 values are already set at a 'xx' format where each 'x' is a hex nibble. 
			* The output of GetChar will ultimately depend on how it is read by the system which may not run on a Unicode encoding format. Thus it may return as something similar to '□'. However, based on testing, it sometimes shows on terminal outputs normally.
	* **TestUnicode()**
    	* Simply call to check if class file can be called externally (e.g. via importation)
    	* Prints `Unicode online!`
## Reference
**JS**
1. parseInt - https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/parseInt
2. tOString - https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Number/toString

## Sidenotes
*No sidenotes for the moment*

## Import/Use (path assumes Unicode and Checker is on the same folder as the caller file)
1. For Node, use:
	```
    import {Checker} from './Checker.js';
	import {Unicode} from './Unicode.js';
	
	let _ = new Unicode();
	let _ = new Unicode();
    ```
2. For React, use:
	```
	import './Checker.js';
	import './Unicode.js';
	
	let _ = new Unicode();
	let _ = new Unicode();
    ```
3. For demonstration, simply run `node Main_Terminal.js` to see a sample test run of the Unicode and Checker functionality. You may check outputs on this website: <https://r12a.github.io/app-conversion/>

## Performance
<img src="performance.png" alt="Unicode Java Performance"/>