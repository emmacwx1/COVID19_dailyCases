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
import java.util.Map.Entry;
import java.util.Set;

public class MakeMaps {

	//instance variables
	private String cleanFileName;
	List<String> cleanFileRow = new ArrayList<String>();
	
	//maps
	
	//maps for daily info retrieval
	public List<HashMap<String, Integer>> dailyCaseMap = new ArrayList<HashMap<String, Integer>>(); //created
	private List<HashMap<String, Integer>> dailyDeathMap = new ArrayList<HashMap<String, Integer>>(); //created
	
	//maps for monthly info retrieval 
	private List<HashMap<String, Integer>> monthlyCaseMap = new ArrayList<HashMap<String, Integer>>(); //created
	private List<HashMap<String, Integer>> monthDeathMap = new ArrayList<HashMap<String, Integer>>(); //created
	
	//maps for sum info retrieval
	private HashMap<String, Integer> sumCaseMap = new HashMap<String, Integer>(); //created
	private HashMap<String, Integer> sumDeathMap = new HashMap<String, Integer>(); //created
	
	//map for sum
	private Map<String, Integer> sumReferenceMap = new HashMap<String, Integer>(); //created
	
	
	//maps for GUI
	//public List<HashMap<String, String>> dateCSMap = new ArrayList<HashMap<String, String>>();
	//commented this map out as we don't need it. 
	public Map<String, List<String>> dateCSMap2 = new HashMap<String, List<String>>();
	public Map<String, List<String>> monthCSMap2 = new HashMap<String, List<String>>();
	
	
	//constructor
	public MakeMaps(String cleanFileName) {
		this.cleanFileName = cleanFileName;
	}
	
	
	/**
	 * Generate lists of strings which is each row of clean file
	 * @return
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
		//TODO
		//TODO stateReader override this. look at state column
		
		String[] lineArray;
		String key;
		int value = 0;
		HashMap<String, Integer> rowInfo = new HashMap<String, Integer>();
		
		//Iterate over list of rows info
		for(String row : this.cleanFileRow) {
			
			lineArray = row.split("[,]+");
			
			//get key
			key = lineArray[0].strip() + "," + lineArray[3].strip() + "," + lineArray[4].strip();
			
			//get value
			if(caseOrDeath.equals("case")) {
				value = Integer.parseInt(lineArray[5].strip());
			}
			
			if(caseOrDeath.equals("death")) {
				value = Integer.parseInt(lineArray[6].strip());
			}
			//System.out.println(value);
			
			//create map of date&county key and confirm cases value
			rowInfo.put(key, value);
			
			if(caseOrDeath.equals("case")) {
				//add map to list of maps
				this.dailyCaseMap.add(rowInfo);
			}
			if(caseOrDeath.equals("death")){
				this.dailyDeathMap.add(rowInfo);
			}
		}
	}
	
	
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
			
			//look if key is already in the map
			//if key NOT in map yet
			if(!this.dateCSMap2.containsKey(key)) {
				
				//initiate list to store new countyState
				List<String> newValue = new ArrayList<String>();
				
				//add countyState to the list
				newValue.add(countyState);
				
				//put key:value pair in map
				this.dateCSMap2.put(key, newValue);
			}
			//if key already in map
			else {
				//get the value (list of countyState)
				value = this.dateCSMap2.get(key);
				
				//add countyState to existed value of list
				value.add(countyState);
				
				//update and put back into Map
				this.dateCSMap2.put(key, value);
			}	
		}	
	}
	
	
	public String[] csForDate(String date) {
		
		String existedDate;
		List<String> existedCS = new ArrayList<String>();
		
		for(Entry<String, List<String>> entry : this.dateCSMap2.entrySet()) {
			
			//get date which is key
			existedDate = entry.getKey();
			
			//if existed date == provide key
			if(existedDate.equals(date)) {
				
				//return existed county,state
				existedCS = entry.getValue();
			}
		}
		
		Collections.sort(existedCS);
		
		String[] csForDate = new String[existedCS.size() + 1];
		csForDate[0] = "County,State";
		for(int i = 1; i < csForDate.length; i++) {
			csForDate[i] = existedCS.get(i - 1);
		}
		
		return csForDate;
		
	}
	
	
	/*
	
	public void dateCSMap(){
		
		String[] lineArray;
		
		//date is key, string
		String key;
		
		//county+state is value, string
		String value;
		
		HashMap<String, String> rowInfo = new HashMap<String, String>();
		
		for(String row : this.cleanFileRow) {
			lineArray = row.split("[,]+");
			
			//get key, which is date in index 0.
			key = lineArray[0].strip();
			
			//get value, which is county in index 3 and state in index 4
			value = lineArray[3].strip() + "," + lineArray[4].strip();
			
			value = lineArray[3].strip();
			
			rowInfo.put(key, value);
			
			//add this to dayCountyPair list of maps
			this.dateCSMap.add(rowInfo);
		}
	}
	
	
	
	public String[] csOfDate(String date) {
		
		//set size later depends on the size of list of values found in hashmap
		
		String csValue;
		
		List<String> csValueLs = new ArrayList<String>();
		//iterate over list of maps
		for(Map<String, String> info : this.dateCSMap) {
			
			//get set of entries containing keys and values
			Set<Entry<String, String>> infoEntries = info.entrySet();
			
			//System.out.println(infoEntries.size()); //95
			
			//iteration happens once for each map
			for(Entry<String, String> infoEntry : infoEntries) {
				
				//if select date is in the map's keys
				if(date.equals(infoEntry.getKey())) {
					
					//System.out.println(infoEntry.getKey());
					//get value
					csValue = infoEntry.getValue();
					//System.out.println(infoEntry.getValue());
					
					//append value to list
					csValueLs.add(csValue);
				}
			}
		}
		
		//get the length of list, which represent amount of values in the list
		int lsLength = csValueLs.size();
		
		//initiate String array for this list
		String[] csValueArray = new String[lsLength + 1];
		csValueArray[0] = "County,State";
		
		//convert list back to arraylist
		ArrayList<String> csValueAS = new ArrayList<String>(csValueLs);
		for(int i = 1; i < csValueArray.length; i++) {
			csValueArray[i] = csValueAS.get(i - 1);
		}
		
		//System.out.println(java.util.Arrays.toString(csValueArray));
		return csValueArray;
	}
	*/
	
	/**
	 * Remove duplicate value
	 * Allow GUI to access the county + state columns (combine)
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
	 * return how many daily cases were reported on the chosen date, county and state
	 * @param date
	 * @param county
	 * @param state
	 * @return daily cases
	 */
	public int getDailyCase(String date, String county, String state) {
		
		int dailyCase = -1;
		
		String requestKey = date + "," + county + "," + state;
		
		//iterate over list of maps
		for(HashMap<String, Integer> info : this.dailyCaseMap) {
			
			//get set of entries containing keys and values
			Set<Entry<String, Integer>> infoEntries = info.entrySet();
			
			System.out.println(infoEntries.size()); //86952
			//iteration happens once for each map
			for(Entry<String, Integer> infoEntry : infoEntries) {
				if(requestKey.equals(infoEntry.getKey())){
					System.out.println(infoEntry.getKey());
					dailyCase = infoEntry.getValue();
					System.out.println(infoEntry.getValue());
					return dailyCase;
				}
			}
		}
		return dailyCase;
	}
	
	
	/**
	 * return how many daily deaths were reported on the chosen date, county and state
	 * @param date
	 * @param county
	 * @param state
	 * @return daily deaths
	 */
	public int getDailyDeath(String date, String county, String state) {
			
		int dailyDeath = -1;
		
		String requestKey = date + "," + county + "," + state;
		
		//iterate over list of maps
		for(HashMap<String, Integer> info : this.dailyDeathMap) {
			
			//get set of entries containing keys and values
			Set<Entry<String, Integer>> infoEntries = info.entrySet();
			
			//iteration happens once for each map
			for(Entry<String, Integer> infoEntry : infoEntries) {
				if(requestKey.equals(infoEntry.getKey())){
					System.out.println(infoEntry.getKey());
					dailyDeath = infoEntry.getValue();
					System.out.println(infoEntry.getValue());
					return dailyDeath;
				}
			}
		}
		return dailyDeath;
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
		int value = 0;
		HashMap<String, Integer> rowInformation = new HashMap<String, Integer>();
		
		for(String row : this.cleanFileRow) {
			
			lineArray = row.split("[,]+");
			
			//month, county, state 
			key = lineArray[1].strip() + "," + lineArray[3].strip() + "," + lineArray[4].strip();
			
			//get value 
			if(caseOrDeath.equals("case")) {
				value = Integer.parseInt(lineArray[5].strip());
			}
			
			if(caseOrDeath.equals("death")) {
				value = Integer.parseInt(lineArray[6].strip());
			}
			
			//obtaining the value 
			System.out.println(value);
			
			rowInformation.put(key, value);
			
			if((caseOrDeath).equals("case")){
				this.monthlyCaseMap.add(rowInformation);
			}
			if((caseOrDeath).equals("death")) {
				this.monthDeathMap.add(rowInformation);
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

		for(HashMap<String, Integer> information : this.dailyCaseMap) {

			Set<Entry<String, Integer>> informationEntries = information.entrySet();
			System.out.println(informationEntries.size());

			for(Entry<String, Integer> infoEntry : informationEntries) {
				if(requestKey.equals(infoEntry.getKey())){
					System.out.println(infoEntry.getKey());
					monthlyCase= infoEntry.getValue();
					System.out.println(infoEntry.getValue());
					return monthlyCase;
				}
			}
		}
		return monthlyCase;

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

		for(HashMap<String, Integer> information : this.dailyCaseMap) {

			Set<Entry<String, Integer>> informationEntries = information.entrySet();
			System.out.println(informationEntries.size());

			for(Entry<String, Integer> infoEntry : informationEntries) {
				if(requestKey.equals(infoEntry.getKey())){
					System.out.println(infoEntry.getKey());
					monthlyDeath = infoEntry.getValue();
					System.out.println(infoEntry.getValue());
					return monthlyDeath;
				}
			}
		}
		return monthlyDeath;
	}

	/**
	 * This creates the sum reference map based on whether the case count or the death count is asked for.
	 * key = county, state (as a string)
	 * value = case or death depending on caseOrDeath
	 * @param caseOrDeath
	 */
	public void sumReferenceMap(String caseOrDeath) {
		String[] lineArray;
		String key;
		int value = 0;
		
		for(String row : this.cleanFileRow) {
			
			lineArray = row.split("[,]+");
			
			//get key
			key = lineArray[3].strip() + "," + lineArray[4].strip();
			
			//get value 
			if(caseOrDeath.equals("case")) {
				value = Integer.parseInt(lineArray[5].strip());
			}
			
			if(caseOrDeath.equals("death")) {
				value = Integer.parseInt(lineArray[6].strip());
			}
			
			//obtaining the value 
			System.out.println(value);
			
			this.sumReferenceMap.put(key, value);
			
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
		int value = 0;

		for(String row : this.cleanFileRow) {

			lineArray = row.split("[,]+");

			//get key
			key = lineArray[3].strip() + "," + lineArray[4].strip();

			if(caseOrDeath.equals("case")) {
				Set<Entry<String, Integer>> sumInformationEntries = this.sumReferenceMap.entrySet();

				for(Entry<String, Integer> infoEntry : sumInformationEntries) {
					int countOfCases = 0;
					if(key.equals(infoEntry.getKey())) {
						System.out.println(infoEntry.getKey());

						countOfCases += infoEntry.getValue();

						System.out.println(infoEntry.getValue());
						value = countOfCases;
					}
				}
			}

			if(caseOrDeath.equals("death")) {
				Set<Entry<String, Integer>> sumInformationEntries1 = this.sumReferenceMap.entrySet();

				for(Entry<String, Integer> infoEntry1 : sumInformationEntries1) {
					int countOfDeaths = 0;
					if(key.equals(infoEntry1.getKey())) {
						System.out.println(infoEntry1.getKey());

						countOfDeaths += infoEntry1.getValue();

						System.out.println(infoEntry1.getValue());
						value = countOfDeaths;
					}
				}
			}

			this.sumCaseMap.put(key, value);
		}
	}
	
	/**
	 * Return how many cases were reported in total for chosen county and state
	 * @param county
	 * @param state
	 * @return total case
	 */
	public int getSumCase(String county, String state) {
		int sumCase = -1;

		String requestKey = county + "," + state;

			Set<Entry<String, Integer>> informationEntries = this.sumCaseMap.entrySet();
			System.out.println(informationEntries.size());

			for(Entry<String, Integer> infoEntry : informationEntries) {
				
				if(requestKey.equals(infoEntry.getKey())){
					System.out.println(infoEntry.getKey());
					
					sumCase = infoEntry.getValue();
					
					System.out.println(infoEntry.getValue());
					return sumCase;
				}
			}
		return sumCase;
	}
	
	
	/**
	 * Return how many death were reported in total for chosen county and state
	 * @param county
	 * @param state
	 * @return total case
	 */
	public int getSumDeath(String county, String state) {
		int sumDeath = -1;

		String requestKey = county + "," + state;

			Set<Entry<String, Integer>> informationEntries = this.sumDeathMap.entrySet();
			System.out.println(informationEntries.size());

			for(Entry<String, Integer> infoEntry : informationEntries) {
				if(requestKey.equals(infoEntry.getKey())){
					System.out.println(infoEntry.getKey());
					sumDeath = infoEntry.getValue();
					System.out.println(infoEntry.getValue());
					return sumDeath;
				}
			}
		return sumDeath;
	}
	
	
	public void monthCSMap2() {
		
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
			
			//get key, which is month in index 1 
			key = lineArray[1].strip();
			
			//get value, which is county in index 3 and state in index 4
			countyState = lineArray[3].strip() + "," + lineArray[4].strip();
			
			//look if key is already in the map
			//if key NOT in map yet
			if(!this.monthCSMap2.containsKey(key)) {
				
				//initiate list to store new countyState
				List<String> newValue = new ArrayList<String>();
				
				//add countyState to the list
				newValue.add(countyState);
				
				//put key:value pair in map
				this.monthCSMap2.put(key, newValue);
			}
			//if key already in map
			else {
				//get the value (list of countyState)
				value = this.monthCSMap2.get(key);
				
				//add countyState to existed value of list
				value.add(countyState);
				
				//update and put back into Map
				this.monthCSMap2.put(key, value);
			}	
		}	
	}
	
	
	public String[] csForMonth(String month) {
		
		String existedMonth;
		List<String> existedCS = new ArrayList<String>();
		
		for(Entry<String, List<String>> entry : this.monthCSMap2.entrySet()) {
			
			//get date which is key
			existedMonth = entry.getKey();
			
			//if existed date == provide key
			if(existedMonth.equals(month)) {
				
				//return existed county,state
				existedCS = entry.getValue();
			}
		}
		
		Collections.sort(existedCS);
		
		String[] csForMonth = new String[existedCS.size() + 1];
		csForMonth[0] = "County,State";
		for(int i = 1; i < csForMonth.length; i++) {
			csForMonth[i] = existedCS.get(i - 1);
		}
		
		return csForMonth;
		
	}
}
