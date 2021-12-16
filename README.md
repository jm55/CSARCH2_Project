# CSARCH2_Project
Simulation Project
Topic: Unicode

The goal of the project is to create an application that is capable of turning a Unicode value into its equivalent UTF-8, UTF-16, and UTF-32 value.

## Requirements
1. Either in Java (GUI), Web (GUI), or HDL.
2. If in Java it must be an executable such as in a .jar file.
3. Demo will be when needed

## Description
1. Input: Unicode (with invalid Unicode Check)
2. Output: UTF-8,UTF-16,UTF-32[format: xx xx xx; where x is a hex nibble]
3. Additonal Functionality: Output with option to paste result in notepad

## Current Implementation
1. Uses Java language to make use of its built-in conversion methods.

## Suggestions:
1. For checking unicode validity, try determining if it is only hexadecimal and if it is within each UTF values range of acceptable inputs by converting to decimal equivalent.
