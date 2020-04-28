package covid19;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
	private List<HashMap<String, Integer>> dailyCaseMap = new ArrayList<HashMap<String, Integer>>();
	private List<HashMap<String, Integer>> dailyDeathMap = new ArrayList<HashMap<String, Integer>>();
	private List<HashMap<String, Integer>> monthlyCaseMap = new ArrayList<HashMap<String, Integer>>();
	private List<HashMap<String, Integer>> monthDeathMap = new ArrayList<HashMap<String, Integer>>();
	private List<HashMap<String, Integer>> sumCaseMap = new ArrayList<HashMap<String, Integer>>();
	private List<HashMap<String, Integer>> sumDeathMap = new ArrayList<HashMap<String, Integer>>();
	
	
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
		HashMap<String, Integer> rowInfo;
		
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
			rowInfo = new HashMap<String, Integer>();
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
		//TODO
	}
	
	
	/**
	 * Return how many monthly Cases were reported on the chosen month, county and state
	 * @param month
	 * @param county
	 * @param state
	 * @return monthly case
	 */
	public int getMonthlyCase(String month, String county, String state) {
		//TODO
		
		int monthlyCase = -1;
		
		
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
		//TODO
		
		int monthlyDeath = -1;
		
		
		return monthlyDeath;
	}
	
	
	/**
	 * generate sumCaseMap if String = case;
	 * generate sumDeathMap if String = death;
	 * key = county, state; (as a string)
	 * value = case or death dependent on caseOrDeath
	 * @param caseOrDeath
	 */
	public void sumMap(String caseOrDeath) {
		//TODO
	}
	
	/**
	 * Return how many cases were reported in total for chosen county and state
	 * @param county
	 * @param state
	 * @return total case
	 */
	public int getSumCase(String county, String state) {
		//TODO
		
		int sumCase = -1;
		
		
		return sumCase;
	}
	
	
	/**
	 * Return how many death were reported in total for chosen county and state
	 * @param county
	 * @param state
	 * @return total case
	 */
	public int getSumDeath(String county, String state) {
		//TODO
		
		int sumDeath = -1;
		
		
		return sumDeath;
	}
}
