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

## Recommended use:
1. Check input for validity using Checker's CheckInput() or CheckInputBool() depending on your preference. However, CheckInput() 'fixes' any fixable issues with the input text that allows it to be accepted in the end, simply check if the return values is not or null (accepted and not accepted respectively).
2. 

## Reference
**JS**
1. parseInt - https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/parseInt
2. tOString - https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Number/toString

## Note
1. As of Jan 24, Unicode does not return values in accordance to the output specified in the instructions, where it says that the output (for UTF-32?) is xx xx xx where each 'x' is a hex nibble. This shall be clarified from the professor.

## Import/Use (path assumes Unicode and Checker is on the same folder as the caller file)
1. For Node, use:
	import {Checker} from './Checker.js';<br>
	import {Unicode} from './Unicode.js';<br>
	
	let _ = new Unicode();<br>
	let _ = new Unicode();<br>
2. For React, use:
	import './Checker.js';<br>
	import './Unicode.js';<br>
	
	let _ = new Unicode();<br>
	let _ = new Unicode();<br>
3. For demonstration, simply run `node Main_Terminal.js` to see a sample test run of the Unicode and Checker functionality. You may check outputs on this website [https://r12a.github.io/app-conversion/]