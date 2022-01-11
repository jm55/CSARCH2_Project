package simulation;

import java.util.*;
import java.nio.*;
/***
 * 
 * @author ESCALONA-LTP02
 *
 */
public class File {
	private static boolean DemoPrint = true;
	public File() {
		
	}
	
	public boolean saveAsCSV(ArrayList<Unicode> list) {
		String[] output = new String[list.size()+1];
		output[0] = "Input,UTF-8,UTF-16,UTF-32";
		for(int i = 0; i < list.size(); i++)
			output[i+1] = list.get(i).GetCSV(true,false);
		DemoPrint(output);
		return true;
	}
	
	public boolean saveAsTxt(ArrayList<Unicode> list) {
		String tempOutput = "";
		ArrayList<String> output = new ArrayList<String>();
		for(int i = 0; i < list.size(); i++) {
			tempOutput = ""; //reset for new input
			
			Unicode u = list.get(i);
			tempOutput = "Input: " + u.GetUnicode() + "\n";
			tempOutput += "UTF-8: " + u.GetUTF8() + "\n";
			tempOutput += "UTF-16: " + u.GetUTF16() + "\n";
			tempOutput += "UTF-32: " + u.GetUTF32() + "\n";
			output.add(tempOutput);
		}
		DemoPrint(output.toArray(new String[output.size()]));
		return true;
	}
	
	private void DemoPrint(String output) {
		if(!DemoPrint)
			return;
		System.out.println(output);
	}
	
	private void DemoPrint(String[] outputs) {
		if(!DemoPrint)
			return;
		for(int i = 0; i < outputs.length; i++)
			DemoPrint(outputs[i]);
	}
}
