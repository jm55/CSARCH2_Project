package simulation;

/**
 * Checker
 * 
 * Will manage the checking and validation of inputs 
 * prior to conversion to equivalent UTF value.
 *
 */
public class Checker {
	public Checker() {
		//null class
	}
	
	/**
	 * Checks and validates input value if allowed to be 
	 * converted to equivalent UTF value.
	 * 
	 * It also checks if the input value contains "U+", which it will automatically
	 * omit.
	 * 
	 * @param input Unicode value without any prefixes
	 * @return True if input is a valid Unicode value, False if otherwise.
	 */
	public String CheckInput(String input) {
		input = input.toUpperCase();
		if(input.isEmpty())
			return null;
		input = input.replaceAll("\s+",""); //removes excess spaces
		if(input.length() == 4 && (input.equalsIgnoreCase("U+0X") || input.equalsIgnoreCase("0XU+")))
				return null;
		if(input.length()>=4)
			if(input.substring(0,4).equalsIgnoreCase("U+0X") || input.substring(0,4).equalsIgnoreCase("0XU+"))
				input = input.substring(4,input.length());
		else if(input.substring(0,2).equalsIgnoreCase("U+") || input.substring(0,2).equalsIgnoreCase("0X")) //string less than 3 characters
			input = input.substring(2, input.length());
		
		if(input.length()==0) //if trimming resulted to 'empty' string
			return null;
		
		long min = Long.parseLong("0000", 16), max = Long.parseLong("10FFFFF",16); //RANGE LIMIT FOR UNICODE VALUES
		
		//will only accept hex inputs via issuing an exception call
		try {
			long inputLong = Long.parseLong(input,16);
			if(inputLong < min || inputLong > max) //check if outside range of allowed hex values
				return null;
		}catch(NumberFormatException n) {
			return null;
		}
		return input;
	}
	
	/**
	 * Simply returns whether input Unicode is valid (true) or not (false).
	 * 
	 * Checks and validates input value if allowed to be 
	 * converted to equivalent UTF value.
	 * 
	 * It also checks if the input value contains "U+", which it will automatically
	 * omit.
	 * @param input Unicode value with("U+")/without prefix.
	 * @return True if a valid Unicode (after fixing) or not. 
	 */
	public boolean CheckInputBool (String input) {
		if(CheckInput(input) != null)
			return true;
		return false;
	}
}
