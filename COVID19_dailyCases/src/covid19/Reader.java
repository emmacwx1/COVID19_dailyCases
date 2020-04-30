package covid19;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Read daily update from github repository:
 * Clean up, reorganize, aggregate data for controller
 * @author Emma Chen &amp; Shruthi Kannan
 *
 */
public class Reader {
	
	//instance variables
	private String filename;
	private List<List<String>> cleanFile;
	private ArrayList<String> date = new ArrayList<String>();
	private ArrayList<String> month = new ArrayList<String>();
	private ArrayList<String> day = new ArrayList<String>();
	private ArrayList<String> county = new ArrayList<String>();
	private ArrayList<String> state = new ArrayList<String>();
	private ArrayList<String> dailyCase = new ArrayList<String>();
	private ArrayList<String> dailyDeath = new ArrayList<String>();

	
	
	//constructor
	public Reader(String filename){
		this.filename = filename;
	}
	
	//methods
	
	/**
	 * separate date format to month and day
	 * create month and day columns
	 * delete FIPS code column
	 * @return list of list for writing into file
	 */
	public List<List<String>> cleanContent() {
		//TODO
		
		//check what files are in the project
		//File file = new File(".");
		//for(String fileNames : file.list()) System.out.println(fileNames);
		
		//create file
		File fileToClean = new File(this.filename);
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		try {
			fileReader = new FileReader(fileToClean);
			
			bufferedReader = new BufferedReader(fileReader);
			
			//consume first line and ignore
			bufferedReader.readLine();
			
			String line;
			
			while((line = bufferedReader.readLine()) != null) {
				
				//each line is represented by column, split by - or , 
				String[] column = line.trim().split(","); //date not separated
				String[] specificCol = line.trim().split("-|,"); //date separated
				/*
				 * [0] year --> to be removed, all cases are in 2020 now
				 * [1] month
				 * [2] day 
				 * [3] county
				 * [4] state
				 * [5] fips, county & state code --> to be removed, will use county and state instead
				 * [6] confirmed cases
				 * [7] deaths
				 */
				List<String> colList = new ArrayList<String>();
				List<String> specificColList = new ArrayList<String>();
				
				colList = Arrays.asList(column);
				specificColList = Arrays.asList(specificCol);
				
				this.date.add(colList.get(0));
				this.month.add(specificColList.get(1));
				this.day.add(specificColList.get(2));
				this.county.add(specificColList.get(3));
				this.state.add(specificColList.get(4));
				this.dailyCase.add(specificColList.get(6));
				this.dailyDeath.add(specificColList.get(7));
				
				this.cleanFile = Arrays.asList(date, month, day, county, state, dailyCase, dailyDeath);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
				bufferedReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return this.cleanFile;
	}
	
	
	/**
	 * Remove duplicate value
	 * Allow GUI to access the date column
	 * @return String Array of date
	 */
	public String[] dateArray(){
		ArrayList<String> uniqueDate = new ArrayList<String>();
		
		Collections.sort(this.date);
		
		for(String eachRowDate : this.date) {
			if(!uniqueDate.contains(eachRowDate)) {
				uniqueDate.add(eachRowDate);
			}
		}
		
		String[] dateStringArray = new String[uniqueDate.size() + 1];
		dateStringArray[0] = "DATE";
		for(int i = 1; i < dateStringArray.length; i++) {
			dateStringArray[i] = uniqueDate.get(i - 1);
		}
		
		return dateStringArray;
	}
	
	
	/**
	 * Remove duplicate value
	 * Allow GUI to access the date column
	 * @return String Array of month
	 */
	public String[] monthArray(){
		ArrayList<String> uniqueMonth = new ArrayList<String>();
		
		Collections.sort(this.month);
		
		for(String eachRowMonth : this.month) {
			if(!uniqueMonth.contains(eachRowMonth)) {
				uniqueMonth.add(eachRowMonth);
			}
		}
		
		String[] monthStringArray = new String[uniqueMonth.size() + 1];
		monthStringArray[0] = "MONTH";
		for(int i = 1; i < monthStringArray.length; i++) {
			monthStringArray[i] = uniqueMonth.get(i - 1);
		}
		
		return monthStringArray;
	}
}
