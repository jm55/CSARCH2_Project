package simulation;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Controller implements ActionListener {
	private GUI g;
	private Checker check;
	private ArrayList<Unicode> outputs; //In case we have to record previous inputs and save it as a txt/csv file
	
	public Controller(GUI g) {
		this.g = g;
		this.g.setListener(this);
		check = new Checker();
		outputs = new ArrayList<Unicode>();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String[] actionCommands = {"checkUnicode", "saveOutput", "clearList", "aboutProgram", "endProgram"};
		
		if(e.getActionCommand() == actionCommands[0]) {
			String raw_input = g.getUnicodeInput();
			boolean CSVMode = g.isCSV();

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
		}
		if(e.getActionCommand() == actionCommands[1]) { //Saving contents of ArrayList outputs as file
			System.out.println("Save Output");
		}
		if(e.getActionCommand() == actionCommands[2]) {
			System.out.println("Clear List");
			outputs.clear();
			displayOutput(outputs);
		}
		if(e.getActionCommand() == actionCommands[3]) {
			System.out.println("About Program");
			g.popMessage(null, "CSARCH2 Project - Unicode\nGroup 7\n© 2022\n\nAlon-alon, Jason Miguel\n"
					+ "Cruz, Julianne Felice\n"
					+ "De Guzman, Cyril Ethan\n"
					+ "Escalona, Jose Miguel\n"
					+ "Rebong, Leana Hyacinth\n"
					+ "Roncal, Raphael\n"
					+ "Turk, Chadi", "About", JOptionPane.INFORMATION_MESSAGE);
		}
		if(e.getActionCommand() == actionCommands[4]) {
			System.out.println("End Program");
			System.exit(0);
		}
	}
	
	/**
	 * Displays the output on the Window via a CSV format
	 * @param list ArrayList containing the computed Unicode values being displayed.
	 */
	private void displayOutput(ArrayList<Unicode> list) {
		String output = "Unicode, UTF8, UTF16, UTF32\n";
		output += "================================\n";
		for(int i  = 0; i < list.size(); i++) {
			output += list.get(i).GetCSV(true);
			output += "\n";
		}
		g.setOutputText(output);
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
			g.popMessage(g,"Invalid input: " + input, "Invalid Input", JOptionPane.WARNING_MESSAGE);
		}
	}
}
