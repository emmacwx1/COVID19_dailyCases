package covid19;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GUI implements ActionListener {
	
	//instance variables
	static Reader file = new Reader("us-counties.csv");
	static MakeMaps cleanFile = new MakeMaps("cleanFile.csv");
	private JFrame frame;
	private JPanel panel;
	private JComboBox<String> mainCB;
	private JComboBox<String> secondCB;
	private JComboBox<String> thirdCB;
	private Hashtable<Integer, String[]> secondCBItems = new Hashtable<Integer, String[]>();
	
	
	
	/*
	 * TODO look into how to solve this in swing
	 * first dropdown: couty or state
	 * second dropdown: daily, month, sum
	 * how to generate next dropdown based on previous dropdown choice
	 */
	public GUI() {
		this.frame = new JFrame(); 
		this.panel = new JPanel();
		
		//setup panel
		//should we change pixels? 30, 30, 10, 30
		this.panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		
		//is this what we want?? or combobox dropdown menu
		this.panel.setLayout(new GridLayout(0, 1));
		
		//setup
		this.frame.add(this.panel, BorderLayout.CENTER);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setTitle("COVID-19 Cases and Deaths");
	
		this.panel.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		
		/*
		//welcome message
		JLabel welcome = new JLabel("Welcome!");
		welcome.setHorizontalAlignment(JLabel.LEFT);
		this.panel.add(welcome, gbc);
		
		JLabel welcome2 = new JLabel("We will provide you with most updated COVID-19 cases and deaths county-sepcific!"); 
		welcome2.setHorizontalAlignment(JLabel.LEFT);
		this.panel.add(welcome2, gbc);
		
		
		JLabel dataSource = new JLabel("Data collected from The New York Times, based on reports from state and local health agencies.", SwingConstants.LEFT);
		this.panel.add(dataSource, gbc);
		*/
		
		//main drop down label: create and add
		JLabel optionTitle = new JLabel("What would you like to do?");
		this.panel.add(optionTitle);
		optionTitle = new JLabel(" ");
		this.panel.add(optionTitle);
		
		//main drop down combobox: create and add
		this.mainCB = new JComboBox<String>(this.getOptionArray());	
		this.mainCB.addActionListener(this);
		
		//GridBagConstraints constraints = new GridBagConstraints();
		//set constrains details 
		//constraints.gridx = 1;
		
		//prevent action events from being fired when the up/down arrow keys are used 
		this.mainCB.putClientProperty("JComboBox.isTableCellEduitor", Boolean.TRUE);
		this.panel.add(mainCB);
		//this.panel.add(this.mainCB, constraints);
		
		//second drop down label: create and add
		
		JLabel secondCBTitle = new JLabel("Please Select: ", SwingConstants.LEFT);
		this.panel.add(secondCBTitle);
		secondCBTitle = new JLabel("");
		this.panel.add(secondCBTitle);
		
		//  Create sub combo box with multiple models
        this.secondCB = new JComboBox<String>();
       // this.secondCB.addActionListener(this);
        
        
        this.secondCB.setPrototypeDisplayValue("XXXXXXXXXX"); // JDK1.4
        this.panel.add(this.secondCB);
        
        //get different possible dropdown for second CB here
        String[] secondCBDate = this.getDateArray();
        this.secondCBItems.put(1, secondCBDate);
        this.secondCBItems.put(2, secondCBDate);
        
        String[] secondCBMonth = this.getMonthArray();
        this.secondCBItems.put(3, secondCBMonth);
        this.secondCBItems.put(4, secondCBMonth);
        
        String[] secondCBCounty = this.getCSArray();
        this.secondCBItems.put(5, secondCBCounty);
        this.secondCBItems.put(6, secondCBCounty);
        
        
        //third dropdown label: create and add
        JLabel thirdCBTitle = new JLabel("Please Select: ", SwingConstants.LEFT);
		this.panel.add(thirdCBTitle);
		thirdCBTitle = new JLabel("");
		this.panel.add(thirdCBTitle);
		
        
        //Create sub combo box with multiple models
        this.thirdCB = new JComboBox<String>();
        this.thirdCB.setPrototypeDisplayValue("XXXXXXXXXX"); // JDK1.4
        this.panel.add(this.thirdCB);
        
		
		
        
        
        
		
		/*
		JComboBox<String> dateChoice = new JComboBox<String>(this.getDateArray());
		this.panel.setLayout(new GridBagLayout());
		constraints = new GridBagConstraints();
		//set constrains details 
		constraints.gridx = 1;
		this.panel.add(dateChoice, constraints);
		*/
		
		
		this.frame.pack();
		this.frame.setVisible(true);
	}
	
	
	/**
	 * Options for first dropdown menu
	 * @return String Array for options 1 to 6
	 */
	public String[] getOptionArray() {
		String[] options = {
				"OPTIONS",
				"Option 1: Get daily confirmed cases of chosen county",
				"Option 2: Get daily death count of chosen county",
				"Option 3: Get monthly confirmed cases of chosen county",
				"Option 4: Get monthly death counts of chosen county",
				"Option 5: Get total confirmed cases of chosen county",
				"Option 6: Get total death counts of chosen county"};
		
		return options;
	}
	
	
	/**
	 * For Option 1 and 2 drop down list, to choose from dates available
	 * @return String Array with dates
	 */
	public String[] getDateArray() {
		GUI.file.cleanContent();
		return GUI.file.dateArray();
	}
	
	
	/**
	 * For Option 3 and 4 drop down list, to choose from months available
	 * @return String array of months
	 */
	public String[] getMonthArray() {
		GUI.file.cleanContent();
		return GUI.file.monthArray();
	}
	
	/**
	 * For Option 5 and 6 drop down list, to choose from county, state available
	 * @return String array of county, state
	 */
	public String[] getCSArray() {
		//clean file first and get dates available to choose from date ArrayList
		GUI.file.cleanContent();
		GUI.cleanFile.cleanFileRow();
		return GUI.cleanFile.countyStateArray();
	}

	
	public static void main(String[] args) {
		
		new GUI();
		
		/*
		//read file
		//Reader file = new Reader("us-counties.csv");
		//file.cleanContent();
		
		//get clean file
		MakeMaps cleanFile = new MakeMaps("cleanFile.csv");
		cleanFile.cleanFileRow();
		
		
		//generate daily case map first
		//cleanFile.dailyMap("case");
		//System.out.println(cleanFile.dailyCaseMap.get(86951).size());
		cleanFile.dateCSMap2();
		System.out.println(cleanFile.dateCSMap2.get("2020-04-11").size());
		System.out.println(cleanFile.dateCSMap2.size());
		cleanFile.csForDate("2020-04-09");
		
		/*
		//get daily cases
		int dailyCase = cleanFile.getDailyCase("2020-04-09", "Philadelphia", "Pennsylvania");
		
		System.out.println("Philly case: " + dailyCase);
		
		
		List<Map<String, String>> dayCountyPair = cleanFile.getDateCSMap();
		
		System.out.println(dayCountyPair.get(0).size()); //86952
		*/
		
		
		//List<Map<String, String>> dayCountyPair = cleanFile.getDateCSMap();
		
		//System.out.println(dayCountyPair.size());
		//csOfDate("2020-04-09");
		
		//System.out.println(cleanFile.csForDate("2020-04-11").length);
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		//https://stackoverflow.com/questions/31362898/how-to-change-combobox-options-depending-on-the-selected-item-of-a-different-com
		
		//see which option is chosen from 1 to 6
		int option = (int) this.mainCB.getSelectedIndex();
		//get value from hashtable
		Object o = this.secondCBItems.get(option);
		
		if(o == null) {
			this.secondCB.setModel(new DefaultComboBoxModel());
		}
		else {
			this.secondCB.setModel(new DefaultComboBoxModel((String[]) o));
		}
		
		
		String dateChosen = (String) this.secondCB.getSelectedItem();
		
		GUI.cleanFile.cleanFileRow();
		GUI.cleanFile.dateCSMap2();
		o = GUI.cleanFile.csForDate((dateChosen));
		
		if(o == null) {
			this.thirdCB.setModel(new DefaultComboBoxModel());
		}
		else {
			this.thirdCB.setModel(new DefaultComboBoxModel((String[]) o));
		}
		
		
	}

}
