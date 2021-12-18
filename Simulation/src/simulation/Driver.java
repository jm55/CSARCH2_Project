package simulation;

public class Driver {
	private static Unicode u;
	public static void main(String[] args) {
		Driver d = new Driver();
		u = new Unicode();
		//d.test();
		d.bruteforceTest();
	}
	
	
//TEST ZONE=================================================
	
	/**
	 * Prints test output from a list of inputs in input[].
	 * Formatted in CSV
	 */
	void test(){
		String[] input = {"245D6"};
		
		System.out.println("Input Unicode,UTF8,UTF16, UTF32");
		for(int i = 0; i < input.length; i++) {
			u.SetUnicode(input[i]);
			System.out.println(input[i] + "," + u.GetUTF8() + "," + u.GetUTF16() + "," + u.GetUTF32());
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
