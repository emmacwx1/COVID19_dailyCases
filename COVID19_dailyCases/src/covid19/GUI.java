package covid19;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI implements ActionListener {

	/*
	 * TODO look into how to solve this in swing
	 * first dropdown: couty or state
	 * second dropdown: daily, month, sum
	 * how to generate next dropdown based on previous dropdown choice
	 */
	public GUI() {
		JFrame frame = new JFrame(); 
		JPanel panel = new JPanel();
		
		//combobox dropdown for states
		String[] statesList = {"Pennsylvania", "California", "New York"};
		
		//setup panel
		//should we change pixels? 30, 30, 10, 30
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		
		//is this what we want?? or combobox dropdown menu
		panel.setLayout(new GridLayout(0, 1));
		
		//setup
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("COVID-19 Cases and Deaths");
		frame.pack();
		frame.setVisible(true);
		
		//create the combo box, 
		//indices starts at 0
		JComboBox states = new JComboBox(statesList);
		states.setSelectedIndex(0);
		states.addActionListener(this);	
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
