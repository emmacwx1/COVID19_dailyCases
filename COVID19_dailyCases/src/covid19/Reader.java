package covid19;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
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
	
	//1
	//get clean content
	/*
	 * get column: date, county, state, cases, deaths
	 */
	public List<List<String>> cleanContent() {
		//TODO
		
		//check what files are in the project
		File file = new File(".");
		for(String fileNames : file.list()) System.out.println(fileNames);
		
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
				//
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
	
	//per day look up in county file
	/**
	 * Return daily confirmed cases based on chosen county and date
	 * key = county, value = daily cases
	 * @param county chosen
	 * @param date chosen
	 * @return confirmed cases
	 */
	public Map<String, Integer> dailyCases(String date, String county) {
		//TODO
		//TODO stateReader override this. look at state column
		
		Map<String, Integer> dailyCases = new HashMap<String, Integer>();
		//look at county column
		
		System.out.println(this.date.get(0));
		
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
