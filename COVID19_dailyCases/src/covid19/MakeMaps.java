package covid19;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MakeMaps {

	//instance variables
	Reader file = new Reader("us-counties.csv");
	
	private String cleanFileName;
	List<String> cleanFileRow = new ArrayList<String>();
	
	//maps for GUI
	public Map<String, List<String>> dateCSMap2 = new HashMap<String, List<String>>();
	public Map<String, List<String>> monthCSMap2 = new HashMap<String, List<String>>();
	
	
	//maps for daily info retrieval
	private HashMap<String, Integer> dailyCaseMap = new HashMap<String, Integer>(); 
	private HashMap<String, Integer> dailyDeathMap = new HashMap<String, Integer>(); 
	
	//maps for monthly info retrieval 
	private HashMap<String, Integer> monthlyCaseMap = new HashMap<String, Integer>(); 
	private HashMap<String, Integer> monthlyDeathMap = new HashMap<String, Integer>(); 
	
	//maps for sum info retrieval
	private HashMap<String, Integer> sumCaseMap = new HashMap<String, Integer>(); 
	private HashMap<String, Integer> sumDeathMap = new HashMap<String, Integer>(); 
	
	
	//constructor
	public MakeMaps(String cleanFileName) {
		this.cleanFileName = cleanFileName;
	}
	
	
	/**
	 * Generate lists of strings which is each row of clean file
	 * clean file is written from raw file's first clean up
	 * this clean file: 
	 * column 0 = date in the format of "yyyy-mm-dd"
	 * column 1 = month in the format of "mm"
	 * column 2 = day in the format of "dd"
	 * column 3 = county 
	 * column 4 = state
	 * column 5 = case
	 * column 6 = death
	 * @return list of strings of each row's information
	 */
	public List<String> cleanFileRow(){
		
		File fileToMap = new File(this.cleanFileName);
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		try {
			fileReader = new FileReader(fileToMap);
			bufferedReader = new BufferedReader(fileReader);
			
			String line;
			
			//skip first row
			line = bufferedReader.readLine();
			
			while((line = bufferedReader.readLine()) != null) {
				this.cleanFileRow.add(line);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this.cleanFileRow;
	}
	
	
	/**
	 * generate dailyCaseMap if String = case;
	 * generate dailyDeathMap if String = death;
	 * key = date, county, state; (as a string)
	 * value = case or death dependent on caseOrDeath
	 * @param caseOrDeath
	 */
	public void dailyMap(String caseOrDeath) {
		
		String[] lineArray;
		String key;
		int value = 0;
		
		//Iterate over list of rows info
		for(String row : this.cleanFileRow) {
			
			lineArray = row.split("[,]+");
			
			//get key
			key = lineArray[0].strip() + "," + lineArray[3].strip() + "," + lineArray[4].strip();
			
			//get value
			if(caseOrDeath.equals("case")) {
				value = Integer.parseInt(lineArray[5].strip());
				this.dailyCaseMap.put(key, value);
			}
			
			if(caseOrDeath.equals("death")) {
				value = Integer.parseInt(lineArray[6].strip());
				this.dailyDeathMap.put(key, value);
			}
		}
	}
	
	
	/**
	 * return how many daily cases were reported on the chosen date, county and state
	 * subtract current date's cases from previous date's cases
	 * @param date
	 * @param county
	 * @param state
	 * @return daily cases
	 */
	public int getDailyCase(String date, String county, String state) {
		
		this.file.cleanContent();
		
		int dailyCase = 0;
		String currentKey = String.join(",", date, county, state);
		//which day is this
		int currentDay = this.file.dateOnDay(date);
		
		int previousDay = currentDay - 1;
		String previousDate = null;
		
		//get previous day's date format by looking into Reader's map of date and day number
		for(Map.Entry<String, Integer> entry: this.file.getDateOnDayMap().entrySet()) {
			if(previousDay != 0 && previousDay == entry.getValue()) {
				previousDate = entry.getKey();
				
				//join previous date, county, state back to match map's key format
				String previousKey = String.join(",", previousDate, county, state);
				
				int todayCases = 0;
				if (this.dailyCaseMap.containsKey(currentKey)) {
					todayCases = this.dailyCaseMap.get(currentKey);
				}
				
				int yesterdayCases = 0;
				if (this.dailyCaseMap.containsKey(previousKey)) {
					yesterdayCases = this.dailyCaseMap.get(previousKey);
				}
				
				return todayCases - yesterdayCases;
			}
			else if (previousDay == 0) {
				return this.dailyCaseMap.get(currentKey);
			}
		}
		return dailyCase;
	}
	
	
	/**
	 * return how many daily deaths were reported on the chosen date, county and state
	 * subtract current date's death from previous date's death
	 * @param date
	 * @param county
	 * @param state
	 * @return daily deaths
	 */
	public int getDailyDeath(String date, String county, String state) {
			
		this.file.cleanContent();
		
		int dailyDeath = 0;
		String currentKey = String.join(",", date, county, state);
		//which day is this
		int currentDay = this.file.dateOnDay(date);
		
		int previousDay = currentDay - 1;
		String previousDate = null;
		
		//get previous day's date format by looking into Reader's map of date and day number
		for(Map.Entry<String, Integer> entry: this.file.getDateOnDayMap().entrySet()) {
			if(previousDay != 0 && previousDay == entry.getValue()) {
				previousDate = entry.getKey();
				
				//join previous date, county, state back to match map's key format
				String previousKey = String.join(",", previousDate, county, state);
				
				int todayDeath = 0;
				if (this.dailyDeathMap.containsKey(currentKey)) {
					todayDeath = this.dailyDeathMap.get(currentKey);
				}
				
				int yesterdayDeath = 0;
				if (this.dailyDeathMap.containsKey(previousKey)) {
					yesterdayDeath = this.dailyDeathMap.get(previousKey);
				}
				
				return todayDeath - yesterdayDeath;
			}
			else if (previousDay == 0) {
				return this.dailyDeathMap.get(currentKey);
			}
		}
		return dailyDeath;
	}
	

	/**
	 * Generate dateCSMap2: map of date as key, list of county,state as key on each date
	 * It is used for the dropdown menu in GUI that popping up the county,state available to choose from after users select a date to look at
	 */
	public void dateCSMap2() {
		//initiate map of string and list
		//https://stackoverflow.com/questions/8229473/hashmap-one-key-multiple-values/8229494
		
		String[] lineArray;
		
		//date is key
		String key;
		
		//countyState of the same date
		String countyState;
		
		//list of (county,state) values
		List<String> value = new ArrayList<String>();
		
		//iterate over each line in clean file
		for(String row : this.cleanFileRow) {
			lineArray = row.split("[,]+");
			
			//get key, which is date in index 0.
			key = lineArray[0].strip();
			
			//get value, which is county in index 3 and state in index 4
			countyState = lineArray[3].strip() + "," + lineArray[4].strip();

			if(!this.dateCSMap2.containsKey(key)) {
				this.dateCSMap2.put(key, new ArrayList<String>());
			}
			
			//add to the value list unless it's not already in the list
			value = this.dateCSMap2.get(key);
			if (!value.contains(countyState)) {
				value.add(countyState);
			}
		}
	}
	
	
	/**
	 * Return value (county,state) of dateCSMap2 based on the chosen date
	 * Convert and copy map's list to string array 
	 * @param date chosen
	 * @return value (list of county,state)
	 */
	public String[] csForDate(String date) {
		
		List<String> existedCS = new ArrayList<String>();
		if (this.dateCSMap2.containsKey(date)) {
			existedCS = this.dateCSMap2.get(date);
		}
		
		Collections.sort(existedCS);
		
		String[] csForDate = new String[existedCS.size() + 1];
		csForDate[0] = "County,State";
		for(int i = 1; i < csForDate.length; i++) {
			csForDate[i] = existedCS.get(i - 1);
		}
		return csForDate;
	}

	
	/**
	 * Generate a string array of county,state, remove duplicate value
	 * Allow GUI to access the county + state columns (combine) as the 2nd dropdown box
	 * @return String Array of county,state
	 */
	public String[] countyStateArray(){
		
		ArrayList<String> uniqueCountyState = new ArrayList<String>();
		String[] lineArray;
		String csOfRow;
		
		//iterate each row in the clean file
		for(String row : this.cleanFileRow) {
			
			//split by ,
			lineArray = row.split("[,]+");
			
			//join "county,state" as string
			csOfRow = lineArray[3].strip() + "," + lineArray[4].strip();
			
			//not have this string yet
			if(!uniqueCountyState.contains(csOfRow)) {
				//add string to arraylist
				uniqueCountyState.add(csOfRow);
			}
		}
		
		//sort arraylist alphabetically
		Collections.sort(uniqueCountyState);
		
		String[] csStringArray = new String[uniqueCountyState.size() + 1];
		
		csStringArray[0] = "COUNTY,STATE";
		for(int i = 1; i < csStringArray.length; i ++) {
			csStringArray[i] = uniqueCountyState.get(i - 1);
		}
		
		return csStringArray;
	}
		
	
	/**
	 * generate monthlyCaseMap if String = case;
	 * generate dailyDeathMap if String = death;
	 * key = month, county, state; (as a string)
	 * value = case or death dependent on caseOrDeath
	 * @param caseOrDeath
	 */
	public void monthlyMap(String caseOrDeath) {
		String[] lineArray;
		String key;
		int count = 0;
		//HashMap<String, Integer> rowInformation = new HashMap<String, Integer>();
		
		for(String row : this.cleanFileRow) {
			
			lineArray = row.split("[,]+");
			
			//month, county, state 
			key = lineArray[1].strip() + "," + lineArray[3].strip() + "," + lineArray[4].strip();
			
			//get value 
			if(caseOrDeath.equals("case")) {
				//parse case number of the row into value
				int rowCount = Integer.parseInt(lineArray[5].strip());
				
				if (!this.monthlyCaseMap.containsKey(key)) {
					this.monthlyCaseMap.put(key, rowCount);
				}
				else {
					count = this.monthlyCaseMap.get(key);
					this.monthlyCaseMap.put(key, Math.max(count, rowCount));
				}
			}
			
			if(caseOrDeath.equals("death")) {
				
				int rowCount = Integer.parseInt(lineArray[6].strip());
				
				if(!this.monthlyDeathMap.containsKey(key)) {
					this.monthlyDeathMap.put(key, rowCount);
				}
				else {
					count = this.monthlyDeathMap.get(key);
					this.monthlyDeathMap.put(key, Math.max(count, rowCount));
				}
			}
		}
	}
	
	
	/**
	 * Return how many monthly Cases were reported on the chosen month, county and state
	 * @param month
	 * @param county
	 * @param state
	 * @return monthly case
	 */
	public int getMonthlyCase(String month, String county, String state) {
		
		int monthlyCase = -1;
		
		String requestKey = month + "," + county + "," + state;
		
		//if month is 01, get first month's last date
		if(month.equals("01")) {
			monthlyCase = this.monthlyCaseMap.get(requestKey);
			return monthlyCase;
		}
		
		//if month is 02, 03, 04, subtract from previous month's cases
		else if (month.equals("02")) {
			String lastMonthKey = String.join(",", "01", county, state);
			if(this.monthlyCaseMap.get(lastMonthKey) != null) {
				monthlyCase = this.monthlyCaseMap.get(requestKey) - this.monthlyCaseMap.get(lastMonthKey);
				return monthlyCase;
			}
			else {
				return this.monthlyCaseMap.get(requestKey);
			}
		}
		else if (month.equals("03")) {
			String lastMonthKey = String.join(",", "02", county, state);
			if(this.monthlyCaseMap.get(lastMonthKey) != null) {
				monthlyCase = this.monthlyCaseMap.get(requestKey) - this.monthlyCaseMap.get(lastMonthKey);
				return monthlyCase;
			}
			else {
				return this.monthlyCaseMap.get(requestKey);
			}
		}
		else {
			String lastMonthKey = String.join(",", "03", county, state);
			if(this.monthlyCaseMap.get(lastMonthKey) != null) {
				monthlyCase = this.monthlyCaseMap.get(requestKey) - this.monthlyCaseMap.get(lastMonthKey);
				return monthlyCase;
			}
			else {
				return this.monthlyCaseMap.get(requestKey);
			}
		}
	}
	
	
	/**
	 * Return how many monthly deaths were reported on the chosen month, county and state
	 * @param month
	 * @param county
	 * @param state
	 * @return monthly death
	 */
	public int getMonthlyDeath(String month, String county, String state) {
		int monthlyDeath = -1;
		
		String requestKey = month + "," + county + "," + state;
		
		//if month is 01, get first month's last date
		if(month.equals("01")) {
			monthlyDeath = this.monthlyDeathMap.get(requestKey);
			return monthlyDeath;
		}
		
		//if month is 02, 03, 04, subtract from previous month's Deaths
		else if (month.equals("02")) {
			String lastMonthKey = String.join(",", "01", county, state);
			if(this.monthlyDeathMap.get(lastMonthKey) != null) {
				monthlyDeath = this.monthlyDeathMap.get(requestKey) - this.monthlyDeathMap.get(lastMonthKey);
				return monthlyDeath;
			}
			else {
				return this.monthlyDeathMap.get(requestKey);
			}
		}
		else if (month.equals("03")) {
			String lastMonthKey = String.join(",", "02", county, state);
			if(this.monthlyDeathMap.get(lastMonthKey) != null) {
				monthlyDeath = this.monthlyDeathMap.get(requestKey) - this.monthlyDeathMap.get(lastMonthKey);
				return monthlyDeath;
			}
			else {
				return this.monthlyDeathMap.get(requestKey);
			}
		}
		else {
			String lastMonthKey = String.join(",", "03", county, state);
			if(this.monthlyDeathMap.get(lastMonthKey) != null) {
				monthlyDeath = this.monthlyDeathMap.get(requestKey) - this.monthlyDeathMap.get(lastMonthKey);
				return monthlyDeath;
			}
			else {
				return this.monthlyDeathMap.get(requestKey);
			}
		}
	}

	/**
	 * generate sumCaseMap if String = case;
	 * generate sumDeathMap if String = death;
	 * key = county, state; (as a string)
	 * value = case or death dependent on caseOrDeath
	 * @param caseOrDeath
	 */
	public void sumMap(String caseOrDeath) {
		String[] lineArray;
		String key;
		int count = 0;

		for(String row : this.cleanFileRow) {

			lineArray = row.split("[,]+");

			//get key
			key = lineArray[3].strip() + "," + lineArray[4].strip();

			if(caseOrDeath.equals("case")) {
				int rowCount = Integer.parseInt(lineArray[5].strip());
				if (!this.sumCaseMap.containsKey(key)) {
					this.sumCaseMap.put(key, rowCount);
				}
				else {
					count = this.sumCaseMap.get(key);
					this.sumCaseMap.put(key, Math.max(count, rowCount));
				}
			}

			if(caseOrDeath.equals("death")) {
				int rowCount = Integer.parseInt(lineArray[6].strip());
				if(!this.sumDeathMap.containsKey(key)) {
					this.sumDeathMap.put(key, rowCount);
				}
				else {
					count = this.sumDeathMap.get(key);
					
					//ignore the smaller number, store the larger number
					this.sumDeathMap.put(key, Math.max(count, rowCount));
				}
			}			
		}
	}
	
	
	/**
	 * Return how many cases were reported in total for chosen county and state
	 * @param county
	 * @param state
	 * @return total case
	 */
	public int getSumCase(String county, String state) {
		String requestKey = county + "," + state;
		return this.sumCaseMap.get(requestKey);
	}
	
	
	/**
	 * Return how many death were reported in total for chosen county and state
	 * @param county
	 * @param state
	 * @return total case
	 */
	public int getSumDeath(String county, String state) {
		String requestKey = county + "," + state;
		return this.sumDeathMap.get(requestKey);
	}
	
	
	/**
	 * Generate monthCSMap2: map of month as key, list of county,state as key on each date
	 * It is used for the drop down menu in GUI that popping up the county,state available to choose from after users select a month to look at (connection between 1st and 2nd drop down menu) 
	 */ 
	public void monthCSMap2() {
		String[] lineArray;
		
		//date is key
		String key;
		
		//countyState of the same date
		String countyState;
		
		//list of (county,state) values
		List<String> value = new ArrayList<String>();
		
		//iterate over each line in clean file
		for(String row : this.cleanFileRow) {
			lineArray = row.split("[,]+");
			
			//get key, which is month in index 1 
			key = lineArray[1].strip();
			
			//get value, which is county in index 3 and state in index 4
			countyState = lineArray[3].strip() + "," + lineArray[4].strip();
			
			if(!this.monthCSMap2.containsKey(key)) {
				this.monthCSMap2.put(key, new ArrayList<String>());
			}

			//add to the value list unless it's not already in the list
			value = this.monthCSMap2.get(key);
			if (!value.contains(countyState)) {
				value.add(countyState);
			}
		}
	}
	
	
	/**
	 * Returns the String array of counties and states for the chosen month
	 * Convert and copy map's list to string array 
	 * @param month chosen
	 * @return value (string array of county,state)
	 */
	public String[] csForMonth(String month) {
		
		List<String> existedCS = new ArrayList<String>();
		if (this.monthCSMap2.containsKey(month)) {
			existedCS = this.monthCSMap2.get(month);
		}
		
		//sorting the existed counties, state list 
		Collections.sort(existedCS);
		
		String[] csForMonth = new String[existedCS.size() + 1];
		//adding a county state column in the beginning and then changing the index
		csForMonth[0] = "County,State";
		for(int i = 1; i < csForMonth.length; i++) {
			csForMonth[i] = existedCS.get(i - 1);
		}
		return csForMonth;
	}
}
