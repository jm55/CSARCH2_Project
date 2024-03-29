package simulation;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;
/***
 * Controller
 * 
 * Bridges GUI and other components of the program
 */
public class Controller implements ActionListener {
	private GUI gui;
	private Checker check;
	private FileWriter file;
	private ArrayList<Unicode> outputs; //In case we have to record previous inputs and save it as a txt/csv file
	private boolean saved;
	
	public Controller(GUI g) {
		this.gui = g;
		this.gui.setListener(this);
		file = new FileWriter();
		check = new Checker();
		outputs = new ArrayList<Unicode>();
		saved = true; // initially as true since nothing changed in an empty outputs<>
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String[] actionCommands = {"checkUnicode", "saveOutput", "clearList", "aboutProgram", "endProgram"};
		
		if(e.getActionCommand() == actionCommands[0]) { //Checking and Converting Unicode
			String raw_input = gui.getUnicodeInput();
			boolean CSVMode = gui.isCSV();
			
			if(easterEgg(raw_input)) //easter egg function
				return;

			//INPUT CHECKING, ACCEPTANCE, AND CONVERSION
			if(CSVMode) {
				String[] raw_list = raw_input.split(",");
				for(int i = 0; i < raw_list.length; i++)
					addUnicode(raw_list[i]);
			}else {
				addUnicode(raw_input);
			}
			
			//DISPLAYING OUTPUT
			displayOutput(outputs);		
			saved = false;
		}
		
		if(e.getActionCommand() == actionCommands[1]) { //Saving contents of ArrayList outputs as file
			System.out.println("Save Output");
			//If file write successful: saved = true;
			int choice = gui.saveAs();
			if(choice == 0)
				saved = file.saveAsTxt(outputs);
			if(choice == 1)
				saved = file.saveAsCSV(outputs);
		}
		
		if(e.getActionCommand() == actionCommands[2]) { //Clearing previous Unicode conversions
			System.out.println("Clear List");
			outputs.clear();
			gui.clearIO();
		}
		
		if(e.getActionCommand() == actionCommands[3]) { //Displays about info of program via pop-up message.
			System.out.println("About Program");
			gui.popMessage(null, "CSARCH2 Project - Unicode\nS13 - Group 7\n© 2022\n\nAlon-alon, Jason Miguel\n"
					+ "Cruz, Julianne Felice\n"
					+ "De Guzman, Cyril Ethan\n"
					+ "Escalona, Jose Miguel\n"
					+ "Rebong, Leana Hyacinth\n"
					+ "Roncal, Raphael\n"
					+ "Turk, Chadi", "About", JOptionPane.INFORMATION_MESSAGE);
		}
		
		if(e.getActionCommand() == actionCommands[4]) { //Ends program
			System.out.println("End Program");
			
			//If saved==false, notify user to save if needed.
			if(!saved) {
				System.out.println("Recent changes not saved...");
			}
			
			System.exit(0);
		}
	}
	
	/**
	 * Easter Egg
	 * 
	 * Enter the correct 'keyword' then it proceeds with a bruteforce attempt of running through every Unicode value specified by user.
	 * 
	 * Keyword: 'bruteforce' (any case) or its binary equivalent (must be exact)
	 * @param input Keyword input of user.
	 * @return True when valid for Easter Egg, false if otherwise.
	 */
	private boolean easterEgg(String input) {
		if(input.replaceAll("\s+","").equals("01100010011100100111010101110100011001010110011001101111011100100110001101100101") || input.toLowerCase().equals("bruteforce")) {
			String minHex = check.CheckInput(gui.inputDialog("Starting value (From U+00): "));
			String maxHex = check.CheckInput(gui.inputDialog("End Value (Up to U+10FFFFF): "));
			long min = Long.parseLong(minHex,16), max = Long.parseLong(maxHex,16);
			if(max-min >= 69631) //triggered if running test size >= 10FFF
				gui.popMessage(null, "Number of elements to be computed is big, it may take a while and will consume a significant amount of memory.\nPlease press OK to continue", "Bruteforce size too big", JOptionPane.WARNING_MESSAGE);
			System.out.println("Bruteforce: " + minHex + " to " + maxHex);
			try {
				Unicode u = new Unicode();
				for(long i = min; i <= max; i++) {
					u.SetUnicode(Long.toHexString(i));
					//outputs.add(u);
					//displayOutput(u);
					//double percentage = ((double)i/(double)max)*100;
					//System.out.println("Bruteforce Completed: " + (percentage) + "%");
				}
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
			//displayOutput(outputs);
			return true;
		}else
			return false;
	}
	
	/**
	 * Displays the output on the Window via a CSV format
	 * @param list ArrayList containing the computed Unicode values being displayed.
	 */
	private void displayOutput(ArrayList<Unicode> list) {
		String output = "Unicode, UTF8, UTF16, UTF32, Character\n";
		output += "================================\n";
		for(int i  = 0; i < list.size(); i++) {
			output += list.get(i).GetCSV(true,true);
			output += "\n";
		}
		gui.setOutputText(output);
	}
	
	/**
	 * Displays individual Unicode on the Window via a CSV format
	 * @param u Individual computed Unicode to be displayed
	 */
	private void displayOutput(Unicode u) {
		String output = "";
		if(!gui.getOutputText().contentEquals("Unicode, UTF8, UTF16, UTF32, Character\n"))
			output = "Unicode, UTF8, UTF16, UTF32, Character\n" + "================================\n";
		output = gui.getOutputText() + u.GetCSV(true, true) + "\n";
		gui.setOutputText(output);
	}
	
	/**
	 * Adds acceptable Unicode values to outputs ArrayList.
	 * Method conducts checking before adding to outputs ArrayList;
	 * @param input Unicode value, whether checked or unchecked.
	 */
	private void addUnicode(String input) {
		String checkedInput = check.CheckInput(input);
		if(checkedInput != null) {
			System.out.println("Input Accepted: " + checkedInput);
			outputs.add(new Unicode(checkedInput));
		}else {
			gui.popMessage(null,"Invalid input: " + input, "Invalid Input", JOptionPane.WARNING_MESSAGE);
		}
	}
}
