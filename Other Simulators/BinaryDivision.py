import os
from tkinter.simpledialog import askinteger

def non_restoring():
    clear()
    print("Non-Restoring Division")

def restoring():
    clear()
    print("Restoring Division")
    q = Dec2Bin(askInt("Q (Dividend): "))
    m = Dec2Bin(askInt("M (Divisor): "))
    m = "0" + m
    a = fill(len(m)+1,m[0:1])
    a,m = resize(a,m)
    mneg = two_complement(m)
    passes = len(q)
    
    print("=============================")
    print("Q: ", q)
    print("M: ", m)
    print("")
    a_len = len(a)
    q_len = len(q)
    for i in range(passes):
        print("Pass: ", i+1)
        print("Before: A: ", a, ", Q: ", q, ", M: ", m, ", -M: ", mneg)

        a,q = shift_left(a,q)

        a = Dec2Bin(Bin2Dec(a) + Bin2Dec(mneg))
        if(len(a) < a_len):
            a = fill(a_len-len(a),a[0:1]) + a

        if(a[0:1]=="1"):
            a = Dec2Bin(Bin2Dec(a) + Bin2Dec(m))
            if(len(a) < a_len):
                a = fill(a_len-len(a),a[0:1]) + a
            q = q + "0"
        else:
            q = q + "1"


        print("After: A: ", a, ", Q: ", q, ", M: ", m, ", -M: ", mneg)
        print("")
    print("Remainder: ", a , "(", Bin2Dec(a), ")")
    print("Quotient: ", q, "(", Bin2Dec(q), ")")
    
def shift_left(a,q):
    a_len = len(a)
    q_len = len(q)
    combined = a+q
    combined = combined[1:len(combined)]
    return combined[0:a_len], combined[a_len:a_len+q_len] #return a, q


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

"""Returns an int casted input with a given message
:param message: message to display when asking for number (int)
:returns: casted int of input
"""
def askInt(message):
    return int(input(message))

"""Like a macro for cls in the terminal (works only on os terminals and not on python IDLE)
"""
def clear():
     os.system('cls' if os.name=='nt' else 'clear')
     return("")

"""Press any key to continue...
"""
def getch():
    input("Press any key to continue...")

"""Self-explanatory
"""
def main_menu():
    print("Restoring and Non-Restoring Division")
    print("Main Menu: ")
    print("1 - Restoring Division\n2 - Non-Restoring Division\n0 - Exit")
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
                restoring()
            elif(choice == "2"):
                non_restoring()
            elif(choice == "0"):
                break
            getch()



"""Obligatory if function if you are using Python as OOP instead of writing script(s)
"""  
if __name__ == "__main__":
    main()
