# CSARCH2_Project
Simulation Project
Topic: Unicode

The goal of the project is to create an application that is capable of turning a Unicode value into its equivalent UTF-8, UTF-16, and UTF-32 value.

## Requirements
1. Either in Java (GUI), Web (GUI), HDL, C, C++, Python.
2. OOP languages and Python must be submitteed as an executable file
3. Demo will be when needed
4. Provide a readme file containing user’s manual on how to use your apps

## Description
1. Input: Unicode (with invalid Unicode Check)
2. Output: UTF-8,UTF-16,UTF-32(format: xx xx xx; where x is a hex nibble)
3. Additonal Functionality: Output with option to paste result in notepad

## Current Testing Implementation
1. Uses Java language to make use of its built-in number conversion methods.
3. Eclipse IDE was used for the Unicode engine part of the project but I think pwede naman gumamit ng Visual Code (and the likes) as well.
4. Directory path for Java files: Simulation>src>simulation
5. Simply open and modify Driver.java's test() for testing however way you'd like.
6. Currently the output values are hexadecimal raw, lowercased, with prefix (0x), without spacing and division. Recommended to arrange output values before use.

## Suggestions:
1. For checking unicode validity, determine if it is only hexadecimal and if it is within each UTF values range of acceptable inputs by converting to decimal equivalent.
