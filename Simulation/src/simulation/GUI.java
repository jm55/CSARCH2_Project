package simulation;

import javax.swing.*;
import javax.swing.BorderFactory;
import java.awt.*;
import java.awt.event.*;


public class GUI extends JFrame{
	private boolean debug = false;
	
	/**
	 * Default constructor that builds the window.
	 */
	public GUI() {
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setTitle(WindowTitle);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		testMode();
	}
	
	public void setListener(Controller c) {
		listener = c;
	}
	
	/**
	 * Builds the default JPanel build of the program.
	 */
	public void setDefaultDisplay() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		titleLabel = createLabel(WindowTitle, newFont(Font.BOLD, 24), WIDTH/2-200,(int)(HEIGHT*.05),400,24, SwingConstants.CENTER, SwingConstants.TOP);
		panel.add(titleLabel);
		
		unicodeLabel = createLabel("Unicode:", newFont(Font.BOLD, 16),32,64,128,32, SwingConstants.LEFT, SwingConstants.CENTER);
		panel.add(unicodeLabel);
		
		outputLabel = createLabel("Output:", newFont(Font.BOLD, 16),256+64,64,128,32, SwingConstants.LEFT, SwingConstants.CENTER);
		panel.add(outputLabel);
		
		
		//INPUT/OUTPUT FIELDS/AREAS
		unicodeField = createTextField(newFont(Font.PLAIN, 16),32,64+32,256,32);
		panel.add(unicodeField);
		outputArea = createTextArea(newFont(Font.PLAIN, 16),256+64,64+32,656,400,true);
		outputArea.setText("Unicode, UTF8, UTF16, UTF32\n" + "================================\n");
		panel.add(outputArea);
		
		//CHECKBOX
		csvCheckBox = createCheckBox("Comma Separated", newFont(Font.BOLD, 16),32,64*2,256,32, false);
		panel.add(csvCheckBox);
		
		//BUTTONS
		checkBtn = createButton("Check", newFont(Font.BOLD,16),32,64*3,256,50,listener,"checkUnicode");
		panel.add(checkBtn);
		saveBtn = createButton("Save", newFont(Font.BOLD,16),32,64*4,256,50,listener,"saveOutput");
		panel.add(saveBtn);
		clearBtn = createButton("Clear", newFont(Font.BOLD,16),32,64*5,256,50,listener,"clearList");
		panel.add(clearBtn);
		aboutBtn = createButton("About",newFont(Font.BOLD,16), 32, 64*6, 256, 50, listener,"aboutProgram");
		panel.add(aboutBtn);
		exitBtn = createButton("Exit",newFont(Font.BOLD,16), 32, 64*7, 256, 50, listener,"endProgram");
		panel.add(exitBtn);
		
		
		add(panel);
		revalidate();
	}
	
	/**
	 * Shows a simple pop-up message using the given parameters.
	 * @param c Parent Component of the pop-up
	 * @param message Message of the pop-up
	 * @param title Title of the pop-up
	 * @param type Type of message, e.g. JOptionPane.PLAIN_MESSAGE, JOptionPane.ERROR_MESSAGE,etc.
	 */
	public void popMessage(Component c, String message, String title, int type) {
		JOptionPane.showMessageDialog(c, message, WindowTitle, type);
	}
	
	public String getUnicodeInput() {
		return unicodeField.getText();
	}
	
	public boolean isCSV() {
		return csvCheckBox.isSelected();
	}
	
	public void setOutputText(String text) {
		outputArea.setText("");
		outputArea.setText(text);
	}
	
	
	//PRIVATE VALUES AND METHODS
	private final int WIDTH = 1024, HEIGHT = 600;
	private String WindowTitle = "CSARCH2 - Unicode";
	private String typeFace = "Calibri";
	private ActionListener listener;
	private JLabel titleLabel, unicodeLabel, outputLabel;
	private JTextField unicodeField;
	private JTextArea outputArea;
	private JCheckBox csvCheckBox;
	private JButton checkBtn, saveBtn, clearBtn, aboutBtn, exitBtn;
	
	private Font newFont(int style, int size) {
		return new Font(this.typeFace, style, size);
	}
	
	private Font newFont(String typeFace, int style, int size) {
		return new Font(typeFace, style, size);
	}
	
	/**
	 * Builds a JLabel object given the text data, font, position & size, and text alignment.
	 * @param text Initial value of the JLabel
	 * @param f Font of the JLabel
	 * @param x X position of the JLabel (via JLabel.setBounds)
	 * @param y Y position of the JLabel (via JLabel.setBounds)
	 * @param width Width of the JLabel (via JLabel.setBounds)
	 * @param height Height of the JLabel (via JLabel.setBounds)
	 * @param hAlignment Horizontal alignment of the text in JLabel (e.g. "SwingConstants.LEFT", "SwingConstants.CENTER", "SwingConstants.RIGHT", "SwingConstants.LEADING", "SwingConstants.TRAILING")
	 * @param vAlignment Vertical alignment of the text in JLabel (e.g. "SwingConstants.TOP", "SwingConstants.CENTER", "SwingConstants.BOTTOM")
	 * @return JLabel object configured using the given basic parameters.
	 */
	private JLabel createLabel(String text, Font f, int x, int y, int width, int height, int hAlignment, int vAlignment) {
		JLabel l = new JLabel(text);
		l.setFont(f);
		l.setBounds(x,y,width,height);
		l.setHorizontalAlignment(hAlignment);
		l.setVerticalAlignment(vAlignment);
		
		if(this.debug) {
			l.setOpaque(true);
			l.setBackground(Color.CYAN);
		}
		
		return l;
	}
	
	private JTextField createTextField(Font f, int x, int y, int width, int height) {
		JTextField tf = new JTextField();
		tf.setFont(f);
		tf.setBounds(x,y,width,height);	
		return tf;
	}
	
	
	private JButton createButton(String text, Font f, int x, int y, int width, int height, ActionListener listener, String actionCommand) {
		JButton b = new JButton(text);
		b.setFont(f);
		b.setBounds(x,y,width,height);
		b.addActionListener(listener);
		b.setActionCommand(actionCommand);
		return b;
	}
	
	private JCheckBox createCheckBox(String text, Font f, int x, int y, int width, int height, boolean selected) {
		JCheckBox cb = new JCheckBox(text,selected);
		cb.setFont(f);
		cb.setBounds(x,y,width,height);	
		return cb;
	}
	
	private JTextArea createTextArea(Font f, int x, int y, int width, int height, boolean locked) {
		JTextArea ta = new JTextArea();
		ta.setFont(f);
		ta.setBounds(x,y,width,height);
		ta.setEditable(!locked);
		ta.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		ta.setAutoscrolls(true);
		ta.setLineWrap(true);
		
		JScrollPane scroll = new JScrollPane(ta);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(50, 100, WIDTH-125, 250);
		
		if(debug) {
			ta.setText(lorem_ipsum);
		}
		
		return ta;
	}
	
	private void testMode(){
		if(debug)
			JOptionPane.showMessageDialog(null, "GUI under construction", WindowTitle, JOptionPane.INFORMATION_MESSAGE);
	}
	
	private String lorem_ipsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
}
