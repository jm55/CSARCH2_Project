@echo off
echo "Compiling..."
javac Tester.java
echo "Executing..."
java Tester < input_test.txt
echo "Execution Completed!"