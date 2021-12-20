package simulation;

/**
 * Checker
 * 
 * Will manage the checking and validation of inputs 
 * prior to conversion to equivalent UTF value.
 * 
 * @author <author>
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
	 * @param input Unicode value without any prefixes
	 * @return True if input is a valid Unicode value, False if otherwise.
	 */
	public boolean CheckInput(String input) {
		long min = Long.parseLong("0000", 16), max = Long.parseLong("10FFFFF",16); //RANGE LIMIT FOR UNICODE VALUES
		
		//will only accept hex inputs
		try {
			long inputLong = Long.parseLong(input,16);
			if(inputLong < min || inputLong > max) //check if outside range of allowed hex values
				return false;
		}catch(NumberFormatException n) {
			return false;
		}
		return true;
	}
//===INTERNAL FUNCTIONALITY===
//other private methods here
}
