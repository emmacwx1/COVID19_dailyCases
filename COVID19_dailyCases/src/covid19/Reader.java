package covid19;

import java.util.HashMap;
import java.util.Map;

/**
 * Read daily update from github repository:
 * Clean up, reorganize, aggregate data for controller
 * @author Emma Chen &amp; Shruthi Kannan
 *
 */
public class Reader {
	
	//instance variables
	private String file;
	
	
	//constructor
	public Reader(String file){
		this.file = file;
	}
	
	//methods
	
	//1
	//get clean content
	/*
	 * get column: date, county, state, cases, deaths
	 */
	public void cleanContent(String file) {
		//TODO
		
		//get column: date, county, state, cases, deaths
		
		//separate the date
		//delete year, because it starts in 2020-01-21
		//look for first dash, get the month, create new column for month
		//look for second dash, get the day, create new column for day
		
		//6 columns x n rows dependent on dates --> ask TA  
		//final columns: month, day, county, state, cases, deaths
		
		//write into new file (csv format?)
		
	}
	
	//per day look up in county file
	/**
	 * Return daily confirmed cases based on chosen county and date
	 * key = county, value = daily cases
	 * @param geo chosen, county here
	 * @param date chosen
	 * @return confirmed cases
	 */
	public Map<String, Integer> dailyCases(String geo, String date) {
		//TODO
		//TODO stateReader override this. look at state column
		
		Map<String, Integer> dailyCases = new HashMap<String, Integer>();
		//look at county column
		
		return dailyCases;
		
	}
	
	
	/**
	 * Return daily deaths based on chosen county and date
	 * key = county, value = daily deaths
	 * @param geo chosen (county here)
	 * @param date
	 * @return deaths
	 */
	public Map<String, Integer> dailyDeaths(String geo, String date) {
		//TODO implement this 
		//TODO stateReader override this, look at state column
		
		Map<String, Integer> dailyDeaths = new HashMap<String, Integer>();
		
		return dailyDeaths;
	}
	
	/**
	 * Returns monthly confirmed cases based on chosen geo and date
	 * key = county, value = monthly cases
	 * @param geo chosen
	 * @param month chosen
	 * @return
	 */
	public Map<String, Integer> monthlyCases(String geo, String month) {
		//TODO
		//stateReader overrides
		
		Map<String, Integer> monthlyCases = new HashMap<String, Integer>();
		
		
		//locate cases of the same month
		//add all these cases 
		
		return monthlyCases;
	}
	
	/**
	 * Returns monthly deaths based on chosen geo and date
	 * @param geo
	 * @param month
	 * @return HashMap
	 */
	public Map<String, Integer> monthlyDeaths(String geo, String month) {
		//TODO
		//stateReader overrides
		
		Map<String, Integer> monthlyDeaths = new HashMap<String, Integer>();
		
		//locate deaths of the same month
		//add all these deaths count
		
		return monthlyDeaths;
	}
	
	/**
	 * Return total confirmed cases of chosen geo (county here)
	 * @param geo
	 * @return total confirmed cases
	 */
	public Map<String, Integer> sumCases(String geo){
		//TODO
		//stateReader overrides
		
		Map<String, Integer> sumCases = new HashMap<String, Integer>();
		
		//locate cases of the chosen geo (county here)
		//add all these cases 
		
		return sumCases;
		
	}
	
	/**
	 * Return total deaths of chosen geo (county here)
	 * @param geo
	 * @return total deaths
	 */
	public Map<String, Integer> sumDeaths(String geo){
		//TODO
		//stateReader overrides
		
		Map<String, Integer> sumDeaths = new HashMap<String, Integer>();
		
		//locate cases of the chosen geo (county here)
		//add all these cases 
		
		return sumDeaths;
		
	}
	
	
	
}
