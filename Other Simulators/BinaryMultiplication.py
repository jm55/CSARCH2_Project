#Python implementation of Binary Multiplication for CSARCH2
#Done for fun but also as practice for examinations and coding.
#It does not however follow the specifications for actual projects.
#エスカローナミゲル
import os

"""Multiplication using Sequential Circuit Method
"""
def seq_circuit():
    clear()
    print("Sequential Circuit")
    m = Dec2Bin(askInt("M (Multiplicand): "))
    mneg = two_complement(m)
    q = Dec2Bin(askInt("Q (Multiplier): "))
    passes = len(q)
    print("=============================")
    print("M (Multiplicand): ", m)
    print("Q (Multiplier): ", q)
    print("Passes: ", passes)
    print("")
    a = fill(passes, "0")
    q1 = "0"
    for i in range(0,passes):
        print("Pass: ", i+1)        
        print("Before A: ", a, ", Q: ", q, ", Q-1: ", q1)
        combined = ''.join(reversed(q))[0:1] + q1 #q and q1 combined
        
        #determine what operation to use
        if(combined == "10"): #A <- A-M
            a = Dec2Bin(Bin2Dec(a) + Bin2Dec(mneg))
        if(combined == "01"): #A <- A+M
            a = Dec2Bin(Bin2Dec(a) + Bin2Dec(m))
        
        #issue on a length occurs, thus need to fill msb
        if(len(a) < len(m)): 
            a = fill(len(m)-len(a), a[0:1]) + a
        
        #right arithmetic shift
        a,q,q1 = r_arithmetic_shift(a,q,q1)      
        
        print("After A: ", a, ", Q: ", q, ", Q-1: ", q1)
        print("")
    print("Product: ", a+q, "(", Bin2Dec(a+q), ")") #show product

"""Right Arithmetic Shift for Sequential Circuit
:param: a_size slen()
:param: a A
:param: q Q
:param: q1 Q-sub-1
:returns: a,q,q1
"""
def r_arithmetic_shift(a,q,q1):
    combined = a + q + q1
    combined = combined[0:1] + combined[0:len(combined)-1]
    a = combined[0:len(a)]
    q = combined[len(a):len(a)+len(q)]
    q1 = combined[len(combined)-1:len(combined)]
    return a,q,q1

"""Multiplication using Extended Booth Method
"""
def ext_booth():
    clear()
    print("Extended Booth's Algorithm")
    m = Dec2Bin(askInt("M (Multiplicand): "))
    mneg = two_complement(m)
    n = Dec2Bin(askInt("N (Multiplier: "))
    n,m = resize(n,m)
    print("=============================")
    print("M (Multiplicand): ", m)
    print("N (Multiplier): ", n)

    if(len(n)%2!=0): #if odd number of bits
        n = n[0:1] + n #append msb of n to msb of itself
    n = n + "0" #append 0
    output = ""

    #append output for every 3 bits
    for i in range(0,len(n)-2,2):
        substr = n[i:i+3]
        if(substr == "000" or substr == "111"):
            output = output + "0 "
        elif(substr == "001" or substr == "010"):
            output = output + "+1 "
        elif(substr == "011"):
            output = output + "+2 "
        elif(substr == "100"):
            output = output + "-2 "
        elif(substr == "101" or substr == "110"):
            output = output + "-1 "
    print("Ext Booth Equivalent: ", output)
    print("Rules:\000=0    100=2\n001=+1    101=-1\n010=+1    110=-1\n011=+2    111=0") #too lazy to finish

"""Multplication using Booth's Method
"""
def booth():
    clear()
    print("Booth's Algorithm")
    m = Dec2Bin(askInt("M (Multiplicand): "))
    mneg = two_complement(m)
    n = Dec2Bin(askInt("N (Multiplier): "))
    n,m = resize(n,m) #resize both variables to have the same lengths

    nOut = n + "0"
    nOut = getBoothEquivalent(nOut) #weirdly I refactored something that could be placed here but whatever
    print("=============================")
    print("M (Multiplicand): ", m)
    print("N (Multiplier): ", n)
    print("Booth Equivalent: ", nOut)
    print("Rules:\n0*m=0\n1*m=m\n-1*m=-m")

"""Returns an int casted input with a given message
:param message: message to display when asking for number (int)
:returns: casted int of input
"""
def askInt(message):
    return int(input(message))

"""Returns a spaced Booth equivalent of a given binary value
:param n: binary number to be converted to booth equivalent
:returns: Booth equivalent of n (e.g. "+1 0 -1 0")
"""
def getBoothEquivalent(n):
    nOut = ""
    for i in range(len(n)-1): #iterate through each pair from left to right
        subset = n[i:i+2]
        if(subset=="00" or subset=="11"):
            nOut = nOut + "0 "
        elif(subset=="01"):
            nOut = nOut + "+1 "
        elif(subset=="10"):
            nOut = nOut + "-1 "
    return nOut

"""Returns a filler str value using the given parameters/specifications
:param size: length of the filler string
:param value: value to be used for filling
:returns: filler str obj
"""
def fill(size, value):
    out = ""
    for i in range(size):
        out=out+str(value)
    return out

"""Converts a binary to decimal equivalent; Accepts signed integers
:param binary: binary value to be converted
:returns: decimal equivalent of the binary
"""
def Bin2Dec(binary):
    if(binary[0:1] == "1"): #if engative
        return int(two_complement(binary),2)*-1 #for simplicity but not necessarily faster, find positive equivalent as dec then multiply by -1
    else:
        return int(binary,2) #int(val,base) only reads it as unsigned so negative values won't work

"""Converts a decimal to appropriately sized(str length) binary equivalent
:param dec: decimal to be converted
:returns: binary equivalent of input decimal
"""
def Dec2Bin(dec):
    decAbs = "0"+str(bin(abs(dec)))[2:] #find absolute equivalent 
    if(dec < 0): #if originally as negative
        return two_complement(decAbs)
    return decAbs

"""Resizes the parameters to whichever the two is longer by length; Uses MSB of the 'dominant' parameter
as the filler value

Parameter layout follows assembly way of moving variables (e.g. mov eax, ebx where eax will have the contents of ebx)
:param dest: supposed dest (but interchangable)
:param src: supposed src (but interchangable)
:returns: resized dest and src (whichever is largest)
"""
def resize(dest,src):
    n = dest
    m = src
    if(len(n) < len(m)): #if m is dominant
        n = fill(len(m)-len(n),n[0:1]) + n
    if(len(m) < len(n)): #if n is dominant
        m = fill(len(n)-len(m),m[0:1]) + m
    return n,m

"""Shortcut method of finding product from decimal input (m,n) with a specified binary length (str length)
:param m: decimal multiplicand
:param n: decimal multiplier
:param size: resulting length of the binary product
:returns: binary product of m and n
"""
def findBinProduct(m,n,size):
    mAbs = abs(int(m))
    nAbs = abs(int(n))
    prod = bin(mAbs * nAbs) #non-negative
    output = ""
    neg = "0"  
    if((int(m) < 0) ^ (int(n) < 0)): #(m<0 xor n<0) will produce a -result
        output = str(two_complement(prod))
        neg = "1"
    else: #to produce a positive result
        output =  "0"+str(prod[2:len(prod)])
    return fill(int(size)-len(output), neg)+output

"""Multiplication using Pencil and Paper Method
"""
def pencil_paper():
    clear()
    print("Pencil Paper")
    raw_m = input("M (Multiplicand): ")
    raw_n = input("N (Multiplier): ")
    m = Dec2Bin(int(raw_m))
    mneg = two_complement(m)
    n = Dec2Bin(int(raw_n))
    n,m = resize(n,m)

    neg = (int(m[0:1]) or int(n[0:1]))
    steps = len(n)
    size = len(m+n) #first intermediate product size
    print("=============================")
    print("M (Multiplicand): ", m, Bin2Dec(m), ", -M: ", mneg)
    print("N (Multiplier): ", n, Bin2Dec(n))
    print("Steps: ", steps, ", Size: ", size)
    print("Neg: ", (int(m[0:1]) or int(n[0:1]))) #whichever of m or n has 1 has msb
    print("Intermediates:")
    for i in reversed(n):
        if(i == "0"): #multiplier is 0
            output = fill(size,"0")
        elif(i == "1"): #multiplier is 1
            output = fill(size-len(m),neg)+m
        print(output)
        size-=1
    if(n[0:1]=="1"):
        print(mneg)
    prod = findBinProduct(raw_m, raw_n,size)
    prod = fill(len(m+n)-len(prod),prod[0:1]) + prod
    print("Answer: ", prod, "(", Bin2Dec(prod), ")")

"""Finds the two's complement of a given binary
:param binary: value to find its negative equivalent
:returns: str length retained negative equivalent of the input 
"""
def two_complement(binary):
    binary = reversed(binary) #read through backwards a str (e.g. input: 01011, read as 11010 where its 0th char is the lsb of the binary)
    output = ""
    first_one = False
    for i in binary:
        if(not first_one and i=="1"): #first 1 detected
            first_one = True
            output = output + i
        elif(not first_one and i=="0"): #no 1 detected and value is ith char 0
            output = output + i
        else: #switching (after first 1s)
            if(i == "0"):
                output = output + "1"
            if(i == "1"):
                output = output + "0"
    return ''.join(reversed(output))

"""Like a macro for cls in the terminal (works only on os terminals and not on python IDLE)
"""
def clear():
     os.system('cls' if os.name=='nt' else 'clear')
     return("")

"""Press any key to continue...
"""
def getch():
    input("Press any key to continue...")

"""Direct multiplication to binary
"""
def direct():
    m = input("M: ")
    n = input("N: ")
    size = input("Target Size: ")
    print(findBinProduct(m,n,size))

"""Self-explanatory
"""
def main_menu():
    print("Binary Multiplication")
    print("Main Menu: ")
    print("1 - Pencil and Paper\n2 - Booth\n3 - Ext. Booth\n4 - Sequential Circuit\n5 - Direct\n0 - Exit")
    return input("Enter choice: ")

"""Program won't run accordingly without this
"""
def main():
    while True:
        clear()  
        choice = main_menu()
        if(choice == ""):
            clear()
        else:
            if(choice == "1"):
                pencil_paper()
            elif(choice == "2"):
                booth()
            elif(choice == "3"):
                ext_booth()
            elif(choice == "4"):
                seq_circuit()
            elif(choice == "5"):
                direct()
            elif(choice == "0"):
                break
            getch()

"""Obligatory if function if you are using Python as OOP instead of writing script(s)
"""  
if __name__ == "__main__":
    main()

