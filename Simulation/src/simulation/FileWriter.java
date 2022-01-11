package simulation;

import java.util.*;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import java.io.*;
/***
 * 
 * @author ESCALONA-LTP02
 *
 */
public class FileWriter {
	private static boolean DemoPrint = false;
	public FileWriter() {
		//N/A
	}
	
	/**
	 * Saves list of Unicode objects as CSV
	 * Format:
	 * Input Unicode, UTF-8, UTF-16, UTF-32
	 * @param list Unicode List
	 * @return True if saving successful, false if otherwise.
	 */
	public boolean saveAsCSV(ArrayList<Unicode> list) {
		String[] output = new String[list.size()+1];
		output[0] = "Input,UTF-8,UTF-16,UTF-32\n";
		for(int i = 0; i < list.size(); i++)
			output[i+1] = list.get(i).GetCSV(true,false) + "\n";
		
		DemoPrint(output);
		return saveFile(output, ".csv");
	}
	
	/**
	 * Saves list of Unicode objects as TXT
	 * Format:
	 * Input: [Input]
	 * UTF-8: [UTF-8]
	 * UTF-16: [UTF-16]
	 * UTF-32: [UTF-32]
	 * @param list Unicode List
	 * @return True if saving successful, false if otherwise.
	 */
	public boolean saveAsTxt(ArrayList<Unicode> list) {
		String tempOutput = "";
		ArrayList<String> output = new ArrayList<String>();
		for(int i = 0; i < list.size(); i++) {
			tempOutput = ""; //reset for new input	
			Unicode u = list.get(i);
			tempOutput = "Input: " + u.GetUnicode() + "\n";
			tempOutput += "UTF-8: " + u.GetUTF8() + "\n";
			tempOutput += "UTF-16: " + u.GetUTF16() + "\n";
			tempOutput += "UTF-32: " + u.GetUTF32() + "\n\n";
			output.add(tempOutput);
		}
		DemoPrint(output.toArray(new String[output.size()]));
		return saveFile(output.toArray(new String[output.size()]), ".txt");
	}
	
	/**
	 * Abstracted version of the technicalities of saving a file.
	 * @param contents Lines of String/text to be saved in the file.
	 * @param extension File extension of the file.
	 * @return True if saving successful, false if otherwise.
	 */
	private boolean saveFile(String[] contents, String extension) {
		String homeDir = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
		JFileChooser jf = new JFileChooser(homeDir);
		jf.setMultiSelectionEnabled(false);
		
		int selection = jf.showSaveDialog(null);
		File f = jf.getSelectedFile();

		//save a file
		if(selection == jf.APPROVE_OPTION) {
			System.out.println("Saving file as " + extension);			
			
			if(f == null) //file does not exist
				return false;
			if(!f.getName().toLowerCase().endsWith(extension)) //filename without extension
				f = new File(f.getParentFile(), f.getName() + extension);
			
			try {
				FileOutputStream fileStream = new FileOutputStream(f);
				BufferedOutputStream buffer = new BufferedOutputStream(fileStream);
				for(int i = 0; i < contents.length; i++) { //write each element of the array
					byte[] bytes = contents[i].getBytes();
					buffer.write(bytes);
				}
				//close filewriting components
				buffer.close();
				fileStream.close();	
			}catch(Exception e) {
				new GUI().popMessage(null, "Error writing file, please try again...", "Error", JOptionPane.ERROR_MESSAGE);
				System.out.println("File Writing Error Occured...");
				return false;
			}
			System.out.println("Save File Complete!");
			return true;
		}else //either cancelled or error occurred
			return false;
	}
	
	/**
	 * Test Printing via terminal of how a filewrite will look on the text file.
	 * @param output Single String to display
	 */
	private void DemoPrint(String output) {
		if(!DemoPrint)
			return;
		System.out.println(output);
	}
	
	/**
	 * Test Printing via terminal of how a filewrite will look on the text file.
	 * @param outputs Array of Strings to display
	 */
	private void DemoPrint(String[] outputs) {
		if(!DemoPrint)
			return;
		for(int i = 0; i < outputs.length; i++)
			DemoPrint(outputs[i]);
	}
}
