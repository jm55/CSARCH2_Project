# CSARCH2_Project
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

## Requirements
1. Either in Java (GUI), Web (GUI), HDL, C, C++, Python.
2. OOP languages and Python must be submitteed as an executable file.
3. Demo will be asked for when needed.
4. Provide a readme file containing user’s manual on how to use your apps.

## Description
1. Input: Unicode (with invalid Unicode Check).
2. Output: UTF-8,UTF-16,UTF-32(format: xx xx xx; where x is a hex nibble).
3. Additonal Functionality: Output with option to paste result in notepad.

## Current Testing Implementation
1. Uses Java language to make use of its built-in number conversion methods.
3. Eclipse IDE was used for the Unicode engine part of the project but I think you can use Visual Code (and the likes) as well.
4. Directory path for Java files: Simulation>src>simulation.
5. Simply open and modify Driver.java's test() for testing however way you'd like.
6. Currently the output values are hexadecimal raw, lowercased, with prefix for Java(0x), without spacing and division. Recommended to arrange output values before use.
7. JS version also implemented for web app implementation. (Found in Simulation>js) 

## Suggestions:
1. For checking unicode validity, determine if it is only hexadecimal and if it is within each UTF values range of acceptable inputs by converting to decimal equivalent.

## Reference:
**Java**
1. Long - https://docs.oracle.com/javase/7/docs/api/java/lang/Long.html
2. Character - https://docs.oracle.com/javase/8/docs/api/java/lang/Character.html (Prefer to use when converting a characer directly instead of its Unicode value.)<br>

**JS**
1. parseInt - https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/parseInt
2. tOString - https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Number/toString
