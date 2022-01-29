@echo off
echo "Compiling..."
javac Tester_Bruteforce.java
echo "Executing..."
java Tester_Bruteforce < input.txt > out.csv
echo "Execution Completed!"
out.csv