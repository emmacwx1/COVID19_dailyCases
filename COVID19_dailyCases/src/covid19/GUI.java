package covid19;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;

/**
 * GUI to provide users options to see daily, monthly, total confirmed cases or death of COVID-19, on their chosen county & state
 * @author Emma Chen &amp; Shruthi Kannan
 *
 */
public class GUI implements ActionListener {
	
	//instance variables
	private Reader file;
	private MakeMaps cleanFile;
	private JFrame frame;
	private JPanel panel;
	private JComboBox<String> mainCB;
	private JComboBox<String> secondCB;
	private JComboBox<String> thirdCB;
	private Hashtable<Integer, String[]> secondCBItems = new Hashtable<Integer, String[]>();

	public GUI() {
		this.file = new Reader("us-counties.csv");
		this.cleanFile = new MakeMaps("cleanFile.csv");
		this.cleanFile.cleanFileRow();
		
		//create all the maps
		this.cleanFile.dateCSMap2();
		this.cleanFile.monthCSMap2();
		
		this.cleanFile.dailyMap("case");
		this.cleanFile.dailyMap("death");
		
		this.cleanFile.monthlyMap("case");
		this.cleanFile.monthlyMap("death");
		
		this.cleanFile.sumMap("case");
		this.cleanFile.sumMap("death");
		
		//initial setup with frame and border
		this.frame = new JFrame(); 
		this.panel = new JPanel();
		
		this.panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		
		//set Layout
		this.panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		
		//setup of JFrame
		//The statement below adds the JFrame to the panel at the center.
		this.frame.add(this.panel, BorderLayout.CENTER);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setTitle("COVID-19 Cases and Deaths");

		//Welcome message: add dialog box to the frame. 
		JOptionPane.showMessageDialog(this.frame, "Welcome to the COVID-19 data access!"
	    		+ "\n"
	    		+ "We will provide you with most updated COVID-19 cases and deaths county-specific!"
	    		+ "\n"
	    		+ "Data collected from The New York Times, based on reports from state and local health agencies.");
		

		//main drop down: create label and add
		JLabel optionTitle = new JLabel("What would you like to do?");
		this.panel.add(optionTitle);
		optionTitle = new JLabel(" ");
		this.panel.add(optionTitle);

		this.mainCB = new JComboBox<String>(this.getOptionArray());	
		this.mainCB.addActionListener(this);
		
		
		//GridBagConstraints constraints = new GridBagConstraints();
		//set constrains details 
		//constraints.gridx = 1;
		
		//prevent action events from being fired when the up/down arrow keys are used 
		this.mainCB.putClientProperty("JComboBox.isTableCellEduitor", Boolean.TRUE);
		this.panel.add(mainCB);
		
		//second drop down: create label and add
		JLabel secondCBTitle = new JLabel("Please Select: ", SwingConstants.LEFT);
		this.panel.add(secondCBTitle);
		secondCBTitle = new JLabel("");
		this.panel.add(secondCBTitle);

        this.secondCB = new JComboBox<String>();
        this.secondCB.addActionListener(this);
        
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
        
        //third dropdown: create label and add
        JLabel thirdCBTitle = new JLabel("Please Select: ", SwingConstants.LEFT);
		this.panel.add(thirdCBTitle);
		thirdCBTitle = new JLabel("");
		this.panel.add(thirdCBTitle);

        this.thirdCB = new JComboBox<String>();
        this.thirdCB.addActionListener(this);
        
        this.thirdCB.setPrototypeDisplayValue("XXXXXXXXXX"); // JDK1.4
        this.panel.add(this.thirdCB);
        
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
				"Option 1: Daily confirmed cases of chosen county",
				"Option 2: Daily death count of chosen county",
				"Option 3: Monthly confirmed cases of chosen county",
				"Option 4: Monthly death count of chosen county",
				"Option 5: Total confirmed cases of chosen county",
				"Option 6: Total death count of chosen county"};
		
		return options;
	}
	
	
	/**
	 * For Option 1 and 2 drop down list, to choose from dates available
	 * @return String Array with dates
	 */
	public String[] getDateArray() {
		this.file.cleanContent();
		this.file.uniqueDate();
		return this.file.dateArray();
	}
	
	
	/**
	 * For Option 3 and 4 drop down list, to choose from months available
	 * @return String array of months
	 */
	public String[] getMonthArray() {
		this.file.cleanContent();
		this.file.uniqueDate();
		return this.file.monthArray();
	}
	
	/**
	 * For Option 5 and 6 drop down list, to choose from county, state available
	 * @return String array of county, state
	 */
	public String[] getCSArray() {
		//clean file first and get dates available to choose from date ArrayList
		this.file.cleanContent();
		this.cleanFile.cleanFileRow();
		
		return this.cleanFile.countyStateArray();
	}

	
	public static void main(String[] args) {

		new GUI();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		//https://stackoverflow.com/questions/31362898/how-to-change-combobox-options-depending-on-the-selected-item-of-a-different-com
		
		//see which option is chosen from 1 to 6
		int option = (int) this.mainCB.getSelectedIndex();
		//store second and third dropdown menu's choices
		String secondChoice = (String) this.secondCB.getSelectedItem();		
		String thirdChoice = (String) this.thirdCB.getSelectedItem();
			
		//https://programming.vip/docs/java-swing-three-ways-to-implement-actionlistener-listener.html
		
		//action for selection on first drop down menu, determine to display date, or month, or county,state
		if (e.getSource() == this.mainCB) {
			
			//get value from hashtable, key is option 1-6, value is the date, month and county,state array based on option
			Object secondCB = this.secondCBItems.get(option);
			
			if(secondCB == null) {
				this.secondCB.setModel(new DefaultComboBoxModel());
			}
			else {
				this.secondCB.setModel(new DefaultComboBoxModel((String[]) secondCB));
			}
		}
		
		//action for selection on second drop down menu, determine third dropdown menu whether to display coutny,states based on second choice, or to display sum cases or death
		if (e.getSource() == this.secondCB) {
			
			if(option == 1 || option == 2) {
				Object thirdCB = this.cleanFile.csForDate((secondChoice));
				this.thirdCB.setModel(new DefaultComboBoxModel((String[]) thirdCB));

			}
			
			else if (option == 3 || option == 4) {
				Object thirdCB = this.cleanFile.csForMonth(secondChoice);
				this.thirdCB.setModel(new DefaultComboBoxModel((String[]) thirdCB));
			}
			
			//if chose to see sum of cases or death, no need to select from third menu and directly display result
			
			//option 5: sum cases
			else if (option == 5) {
				
				String[] countyState = secondChoice.split(",");
				String county = countyState[0];
				String state = countyState[1];

				int sumCase = this.cleanFile.getSumCase(county, state);
				
				//show pop up window
				JOptionPane.showMessageDialog(this.frame, "In total, there are " + sumCase + " confirmed cases in " + secondChoice + ".", "Total Cases", JOptionPane.INFORMATION_MESSAGE);
				
				Object thirdCB = null;
				this.thirdCB.setModel(new DefaultComboBoxModel());
			}
			
			//option 6: sum death
			else if (option == 6) {
				String[] countyState = secondChoice.split(",");
				String county = countyState[0];
				String state = countyState[1];
				
				int sumDeath = this.cleanFile.getSumDeath(county, state);
				
				//show pop up window
				JOptionPane.showMessageDialog(this.frame, "In total, there are " + sumDeath + " cases in " + secondChoice + ".", "Total Death", JOptionPane.INFORMATION_MESSAGE);
				
				Object thirdCB = null;
				this.thirdCB.setModel(new DefaultComboBoxModel());
			}
		}
		
		//action for third drop down menu's selection, further determine which result to show based on option 1-6
		/*
		 * Option 1: daily cases
		 * Option 2: daily death
		 * Option 3: monthly case
		 * Option 4: monthly death
		 * */
		if(e.getSource() == this.thirdCB) {
			String[] countyState2 = thirdChoice.split(",");
			String county2 = countyState2[0];
			String state2 = countyState2[1];
			
			if(option == 1) {
				int dailyCase = this.cleanFile.getDailyCase(secondChoice, county2, state2);
				JOptionPane.showMessageDialog(this.frame, "On " + secondChoice + ", there are " + dailyCase + " confirmed cases in " + thirdChoice + ".", "Daily Cases", JOptionPane.INFORMATION_MESSAGE);
			}
			
			else if (option == 2) {
				int dailyDeath = this.cleanFile.getDailyDeath(secondChoice, county2, state2);
				JOptionPane.showMessageDialog(this.frame, "On " + secondChoice + ", there are " + dailyDeath + " death in " + thirdChoice + ".", "Daily Death", JOptionPane.INFORMATION_MESSAGE);
			}
			
			else if (option == 3) {
				int monthlyCase = this.cleanFile.getMonthlyCase(secondChoice, county2, state2);
				JOptionPane.showMessageDialog(this.frame, "In this month-" + secondChoice + ", there are " + monthlyCase + " confirmed cases in " + thirdChoice + ".", "Monthly Cases", JOptionPane.INFORMATION_MESSAGE);
			}
			
			else if (option == 4) {
				int monthlyDeath = this.cleanFile.getMonthlyDeath(secondChoice, county2, state2);
				JOptionPane.showMessageDialog(this.frame, "In this month-" + secondChoice + ", there are " + monthlyDeath + " death in " + thirdChoice + ".", "Monthly Cases", JOptionPane.INFORMATION_MESSAGE);
			}	
		}
	}
}