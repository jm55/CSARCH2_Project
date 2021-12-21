package simulation;

public class Driver {
	private static Unicode u;
	private static Checker c;
	public static void main(String[] args) {
		Driver d = new Driver();
		u = new Unicode();
		c = new Checker();
		d.test();
		//d.bruteforceTest();
	}
	
	
//TEST ZONE=================================================
	
	/**
	 * Prints test output from a list of inputs in input[].
	 * Formatted in CSV
	 */
	void test(){
		String[] input = {"245D6","1CAFE","42069","Youtube","Meta","10FFFFF","1FFFFF",""};
		System.out.println("Input Unicode,UTF8,UTF16, UTF32");
		for(int i = 0; i < input.length; i++) {
			if(c.CheckInput(input[i])) {
				u.SetUnicode(input[i]);
				System.out.println(input[i] + "," + u.GetUTF8() + "," + u.GetUTF16() + "," + u.GetUTF32());
			}else {
				System.out.print("Invalid entry: ");
				if(input[i].isEmpty())
					System.out.println("EMPTY INPUT");
				else
					System.out.println(input[i]);
			}	
		}
			
	}
	
	void bruteforceTest() {
		String minHex = "0000", maxHex = "10F"; //Range: 0x0 to 0x10FFFFF
		System.out.println("Input Unicode,UTF8,UTF16,UTF32");
		try {
			for(int i = Integer.parseInt(minHex,16); i <= Integer.parseInt(maxHex,16); i++) {
				u.SetUnicode(Integer.toHexString(i));
				String[] arr = u.GetAll();
				System.out.println(Integer.toHexString(i) + "," + arr[0] + "," + arr[1] + "," + arr[2]);
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	void printArray(int[] input) {
		for(int i = 0; i < input.length; i++)
			System.out.println(input[i]);
	}
	void printArray(String[] input) {
		for(int i = 0; i < input.length; i++)
			System.out.println(input[i]);
	}
}
