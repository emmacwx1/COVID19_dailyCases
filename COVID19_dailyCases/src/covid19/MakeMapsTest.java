package covid19;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MakeMapsTest {

	Reader file;
	MakeMaps cleanFile;
	
	@BeforeEach
	void setUp() throws Exception {
		this.file = new Reader("us-counties.csv");
		file.cleanContent();
		
		this.cleanFile = new MakeMaps("cleanFile.csv");
		cleanFile.cleanFileRow();
	}

	@Test
	void testCsForDate() {
		this.cleanFile.dateCSMap2();
		
		//first date
		String[] csForDate = new String [] {"County,State", "Snohomish,Washington"};
		assertEquals(java.util.Arrays.toString(csForDate), java.util.Arrays.toString(this.cleanFile.csForDate("2020-01-21")));
		
		//test a date with multiple county,state
		//test sort alphabetically
		csForDate = new String [] {"County,State", "Cook,Illinois", "Orange,California", "Snohomish,Washington"};
		assertEquals(java.util.Arrays.toString(csForDate), java.util.Arrays.toString(this.cleanFile.csForDate("2020-01-25")));
		
		//test length of string array for last date on file
		//record found in csv is 2809, + 1  = 2810 for the County,State header 
		assertEquals(2810, this.cleanFile.csForDate("2020-04-24").length);
		
	}

	@Test
	void testCountyStateArray() {
		//2843 county,state in total + 1 for header
		assertEquals(2844, this.cleanFile.countyStateArray().length);
		
		String countyState = "Abbeville,South Carolina";
		assertEquals(countyState, this.cleanFile.countyStateArray()[1]);
		countyState = "Acadia,Louisiana";
		assertEquals(countyState, this.cleanFile.countyStateArray()[2]);
		countyState = "Accomack,Virginia";
		assertEquals(countyState, this.cleanFile.countyStateArray()[3]);
		
	}

	@Test
	void testGetDailyCase() {
		this.cleanFile.dailyMap("case");
		
		int dailyCase = this.cleanFile.getDailyCase("2020-04-24", "New York City", "New York");
		assertEquals(4629, dailyCase);
		
		//no record for NYC yet, return 0
		dailyCase = this.cleanFile.getDailyCase("2020-01-24", "New York City", "New York");
		assertEquals(0, dailyCase);
		
		//edge case, 1st day should be current day data
		dailyCase = this.cleanFile.getDailyCase("2020-01-21", "Snohomish", "Washington");
		assertEquals(1, dailyCase);
		
		//edge case, 2nd day of Snohomish, Washington does not have any increase
		dailyCase = this.cleanFile.getDailyCase("2020-01-22", "Snohomish", "Washington");
		assertEquals(0, dailyCase);
		
		//edge case, 1st day of Cook Illinois is 2020-01-24, there is no previous day data, so return current date's cases
		dailyCase = this.cleanFile.getDailyCase("2020-01-24", "Cook", "Illinois");
		assertEquals(1, dailyCase);
		
		//edge case, Cook Illinois did not have increase from 2nd day
		dailyCase = this.cleanFile.getDailyCase("2020-01-27", "Cook", "Illinois");
		assertEquals(0, dailyCase);
	}

	@Test
	void testGetDailyDeath() {
		this.cleanFile.dailyMap("death");
		
		//normal case
		int dailyDeath = this.cleanFile.getDailyDeath("2020-04-24", "New York City", "New York");
		assertEquals(268, dailyDeath);
		
		//edge case, NYC did not have current date or previous date here yet, return 0
		dailyDeath = this.cleanFile.getDailyDeath("2020-01-24", "New York City", "New York");
		assertEquals(0, dailyDeath);
		
		//edge case, 1st day should be current day data
		dailyDeath = this.cleanFile.getDailyDeath("2020-03-01", "New York City", "New York");
		assertEquals(0, dailyDeath);
				
		//edge case, 1st day of Cook Illinois is 2020-01-28, there is no previous day data, so return current date's death
		dailyDeath = this.cleanFile.getDailyDeath("2020-01-28", "Cook", "Illinois");
		assertEquals(0, dailyDeath);
		
		//edge case, Cook Illinois did not have increase death from previous date, test transition from end of month to start of next month
		dailyDeath = this.cleanFile.getDailyDeath("2020-02-01", "Cook", "Illinois");
		assertEquals(0, dailyDeath);
		
		//Cook first day to have any death
		dailyDeath = this.cleanFile.getDailyDeath("2020-03-17", "Cook", "Illinois");
		assertEquals(1, dailyDeath);
	}

	@Test
	void testGetMonthlyCase() {
		this.cleanFile.monthlyMap("case");
		
		//philadelphia's first date of record is in March, should just return march cases
		int monthlyCase = this.cleanFile.getMonthlyCase("03", "Philadelphia", "Pennsylvania");
		assertEquals(1315, monthlyCase);
		
		//04, SF case = 1343, 03, SF case = 400; 1343 - 400 = 943
		monthlyCase = this.cleanFile.getMonthlyCase("04", "San Francisco", "California");
		assertEquals(943, monthlyCase);
		
		//04, Nassau, NY case = 8544, no March data
		monthlyCase = this.cleanFile.getMonthlyCase("03", "Nassau", "New York");
		assertEquals(8544, monthlyCase);
		
		//04, Sullivan, NYC case = 628; 03, Sullivan, NYC case = 109; 628 - 109 = 519
		monthlyCase = this.cleanFile.getMonthlyCase("04", "Sullivan", "New York");
		assertEquals(519, monthlyCase);
		
		//04 NYC case = 150484, 03 NYC case = 43139; 150484 - 43139 = 107345
		monthlyCase = this.cleanFile.getMonthlyCase("04", "New York City", "New York");
		assertEquals(107345, monthlyCase);
	}

	@Test
	void testGetMonthlyDeath() {
		this.cleanFile.monthlyMap("death");
		
		//philadelphia's first date of record is in March, should just return march cases
		int monthlyDeath = this.cleanFile.getMonthlyDeath("03", "Philadelphia", "Pennsylvania");
		assertEquals(14, monthlyDeath);
		
		//04, SF death = 22, 03, SF death = 6; 22 - 6 = 16
		monthlyDeath = this.cleanFile.getMonthlyDeath("04", "San Francisco", "California");
		assertEquals(16, monthlyDeath);
		
		//04, Nassau, NY death = 63, no Mardata
		monthlyDeath = this.cleanFile.getMonthlyDeath("03", "Nassau", "New York");
		assertEquals(63, monthlyDeath);
		
		//04, Sullivan, NY death = 9; 03, Sullivan, NYC death = 1; 9 - 1 = 8
		monthlyDeath = this.cleanFile.getMonthlyDeath("04", "Sullivan", "New York");
		assertEquals(8, monthlyDeath);
		
		//03, NYC death = 11157, 03 NYC death = 1096; 11157 - 1096 = 10061
		monthlyDeath = this.cleanFile.getMonthlyDeath("04", "New York City", "New York");
		assertEquals(10061, monthlyDeath);
		
	}

	@Test
	void testGetSumCase() {
		this.cleanFile.sumMap("case");
		
		int totalCasesInThisCounty = this.cleanFile.getSumCase("Snohomish", "Washington");
		assertEquals(2267, totalCasesInThisCounty);
		
		totalCasesInThisCounty = this.cleanFile.getSumCase("Philadelphia", "Pennsylvania");
		assertEquals(11877, totalCasesInThisCounty);
		
		totalCasesInThisCounty = this.cleanFile.getSumCase("Addison", "Vermont");
		assertEquals(61, totalCasesInThisCounty);
		
	}

	@Test
	void testGetSumDeath() {		
		this.cleanFile.sumMap("death");
		
		int totalDeathsInThisCounty = this.cleanFile.getSumDeath("Snohomish", "Washington");
		assertEquals(102, totalDeathsInThisCounty);
		
		totalDeathsInThisCounty = this.cleanFile.getSumDeath("Philadelphia", "Pennsylvania");
		assertEquals(449, totalDeathsInThisCounty);
		
		totalDeathsInThisCounty = this.cleanFile.getSumDeath("Addison", "Vermont");
		assertEquals(2, totalDeathsInThisCounty);
		
	}

	@Test
	void testCsForMonth() {
		//Shruthi 
		
		//creating monthCSMap2 using the correct method
		this.cleanFile.monthCSMap2();
		
		//county,state + 1 for header
		assertEquals(7, this.cleanFile.csForMonth("01").length);
		assertEquals(23, this.cleanFile.csForMonth("02").length);
		assertEquals(2182, this.cleanFile.csForMonth("03").length);
		assertEquals(2840, this.cleanFile.csForMonth("04").length);
		
	}
}