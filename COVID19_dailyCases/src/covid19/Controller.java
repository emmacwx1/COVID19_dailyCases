package covid19;

import java.util.ArrayList;
import java.util.List;

/**
 * Provide user daily update of confirmed cases and deaths of chosen city, country, or date
 * @author Emma Chen &amp; Shruthi Kannan
 *
 */
public class Controller {

	/*
	 * Q1: go online and download updated csv OR set a date and download the latest confirmed cases
	 * Q2: GUI class needed? 
	 * Q3: can we refer to file column as dropdown menu strings list
	 * 
	 */
	
	//instance variables
	
	GUI gui = new GUI();
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//set file name as string
		String fileName = "us-counties.csv";
		
		//create file
		Reader file = new Reader(fileName);
		
		List<List<String>> cleanFile = file.cleanContent();
		
		MyFileWriter.writeFile("cleanFile.csv", cleanFile, true);
		
		
		//option1: look up per day, single day increase in chosen county
		//option2: look up per day, single day increase in chosen state
		//option3: look up per month, monthly increase in chosen county
		//option4: look up per month, monthly increase in chosen state
		//option5: look up sum of cases, up till the chosen date, in chosen county
		//option6: look up sum of cases, up till the chosen date, in chosen state
		
		//new GUI();
	
	
	}

}
