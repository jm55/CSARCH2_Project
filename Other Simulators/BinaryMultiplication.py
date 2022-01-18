#Python implementation of Binary Multiplication for CSARCH2
#Done for fun but also as practice for examinations and coding.
#It does not however follow the specifications for actual projects.
#エスカローナミゲル
import os

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
        combined = ''.join(reversed(q))[0:1] + q1
        if(combined == "10"): #A <- A-M
            a = Dec2Bin(Bin2Dec(a) + Bin2Dec(mneg))
        if(combined == "01"): #A <- A+M
            a = Dec2Bin(Bin2Dec(a) + Bin2Dec(m))
        if(len(a) < len(m)): #issue on a length occurs, thus need to fill msb
            a = fill(len(m)-len(a), a[0:1]) + a
        a,q,q1 = r_arithmetic_shift(len(a),a,q,q1)      
        print("After A: ", a, ", Q: ", q, ", Q-1: ", q1)
        print("")
    print("Product: ", a+q, "(", Bin2Dec(a+q), ")")

def r_arithmetic_shift(a_size,a,q,q1):
    combined = a + q + q1
    combined = combined[0:1] + combined[0:len(combined)-1]
    a = combined[0:len(a)]
    q = combined[len(a):len(a)+len(q)]
    q1 = combined[len(combined)-1:len(combined)]
    return a,q,q1

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
        n = n[0:1] + n
    n = n + "0" #append 0
    output = ""
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
    print("Rules:\000=0    100=2\n001=+1    101=-1\n010=+1    110=-1\n011=+2    111=0")

def booth():
    clear()
    print("Booth's Algorithm")
    m = Dec2Bin(askInt("M (Multiplicand): "))
    mneg = two_complement(m)
    n = Dec2Bin(askInt("N (Multiplier): "))
    n,m = resize(n,m)

    nOut = n + "0"
    nOut = getBoothEquivalent(nOut)
    print("=============================")
    print("M (Multiplicand): ", m)
    print("N (Multiplier): ", n)
    print("Booth Equivalent: ", nOut)
    print("Rules:\n0*m=0\n1*m=m\n-1*m=-m")

def askInt(message):
    return int(input(message))

def getBoothEquivalent(n):
    nOut = ""
    for i in range(len(n)-1):
        subset = n[i:i+2]
        if(subset=="00" or subset=="11"):
            nOut = nOut + "0 "
        elif(subset=="01"):
            nOut = nOut + "+1 "
        elif(subset=="10"):
            nOut = nOut + "-1 "
    return nOut

def fill(size, value):
    out = ""
    for i in range(size):
        out=out+value
    return out

def Bin2Dec(binary):
    if(binary[0:1] == "1"):
        return int(two_complement(binary),2)*-1
    else:
        return int(binary,2)

def Dec2Bin(dec):
    decAbs = "0"+str(bin(abs(dec)))[2:]
    if(dec < 0):
        return two_complement(decAbs)
    return decAbs

#resizes the dest on the size of src or vice versa (whichever is longest), uses msb as the fill value
def resize(dest,src):
    n = dest
    m = src
    if(len(n) < len(m)):
        n = fill(len(m)-len(n),n[0:1]) + n
    if(len(m) < len(n)):
        m = fill(len(n)-len(m),m[0:1]) + m
    return n,m

def findBinProduct(m,n,size):
    mAbs = abs(int(m))
    nAbs = abs(int(n))
    prod = bin(mAbs * nAbs) #non-negative
    output = ""
    neg = "0"
    if((int(m) < 0) ^ (int(n) < 0)): #negative on one, either m or n
        output = str(two_complement(prod))
        neg = "1"
    else:
        output =  "0"+str(prod[2:len(prod)])
    return fill(int(size)-len(output), neg)+output

def pencil_paper():
    clear()
    print("Pencil Paper")
    raw_m = input("M (Multiplicand): ")
    raw_n = input("N (Multiplier): ")
    m = Dec2Bin(int(raw_m))
    mneg = two_complement(m)
    n = Dec2Bin(int(raw_n))
    n,m = resize(n,m)

    neg = m[0:1]
    steps = len(n)
    size = len(m+n) #first intermediate product size
    print("=============================")
    print("M (Multiplicand): ", m, Bin2Dec(m), ", -M: ", mneg)
    print("N (Multiplier): ", n, Bin2Dec(n))
    print("Steps: ", steps, ", Size: ", size)
    print("Neg: ", (int(m[0:1]) or int(n[0:1])))
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
    print("Answer: ", fill(len(m+n)-len(prod),prod[0:1])+prod)

#returns binary string of reversed
def two_complement(binary):
    binary = reversed(binary)
    output = ""
    first_one = False
    for i in binary:
        if(not first_one and i=="1"):
            first_one = True
            output = output + i
        elif(not first_one and i=="0"):
            output = output + i
        else:
            if(i == "0"):
                output = output + "1"
            if(i == "1"):
                output = output + "0"
    return ''.join(reversed(output))

def clear():
     os.system('cls' if os.name=='nt' else 'clear')
     return("")

def getch():
    input("Press any key to continue...")

def direct():
    m = input("M: ")
    n = input("N: ")
    size = input("Target Size: ")
    print(findBinProduct(m,n,size))

def main_menu():
    print("Binary Multiplication")
    print("Main Menu: ")
    print("1 - Pencil and Paper\n2 - Booth\n3 - Ext. Booth\n4 - Sequential Circuit\n5 - Direct\n0 - Exit")
    return input("Enter choice: ")

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
        
if __name__ == "__main__":
    main()

