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
		//Emma
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
		//Emma
		
		//2843 county,state in total + 1 for header
		assertEquals(2844, this.cleanFile.countyStateArray().length);
		
		//we will measure if string array is sorted here
		String countyState = "Abbeville,South Carolina";
		assertEquals(countyState, this.cleanFile.countyStateArray()[1]);
		countyState = "Acadia,Louisiana";
		assertEquals(countyState, this.cleanFile.countyStateArray()[2]);
		countyState = "Accomack,Virginia";
		assertEquals(countyState, this.cleanFile.countyStateArray()[3]);
		
	}

	@Test
	void testGetDailyCase() {
		//Emma
		this.cleanFile.dailyMap("case");
		
		//random row chosen from file
		int dailyCase = this.cleanFile.getDailyCase("2020-04-09", "Kansas City", "Missouri");
		assertEquals(257, dailyCase);
		
		//first row of info
		dailyCase = this.cleanFile.getDailyCase("2020-01-21", "Snohomish", "Washington");
		assertEquals(1, dailyCase);
		
		//last row of info
		dailyCase = this.cleanFile.getDailyCase("2020-04-24", "Washakie", "Wyoming");
		assertEquals(4, dailyCase);
	}

	@Test
	void testGetDailyDeath() {
		//Emma
		this.cleanFile.dailyMap("death");
		
		//random row chosen from file
		int dailyDeath = this.cleanFile.getDailyDeath("2020-01-28", "Cook", "Illinois");
		assertEquals(0, dailyDeath);
		
		//first row of info
		dailyDeath = this.cleanFile.getDailyDeath("2020-01-21", "Snohomish", "Washington");
		assertEquals(0, dailyDeath);
		
		//last row of info
		dailyDeath = this.cleanFile.getDailyDeath("2020-04-24", "Washakie", "Wyoming");
		assertEquals(0, dailyDeath);
	}

	@Test
	void testGetMonthlyCase() {
		//Shruthi
		this.cleanFile.monthlyMap("case");
		
		//random row chosen from file
		int monthlyCase = this.cleanFile.getMonthlyCase("01", "Snohomish", "Washington");
		assertEquals(11, monthlyCase);
	}

	@Test
	void testGetMonthlyDeath() {
		//Shruthi
		this.cleanFile.monthlyMap("death");
		
		//random row chosen from file
		int monthlyDeath = this.cleanFile.getMonthlyDeath("03", "Philadelphia", "Pennsylvania");
		assertEquals(35, monthlyDeath);
	}

	@Test
	void testGetSumCase() {
		this.cleanFile.sumMap("case");
		
		int totalCasesInThisCounty = this.cleanFile.getSumCase("Snohomish", "Washington");
		assertEquals(55663, totalCasesInThisCounty);
		
		//Shruthi I think you might listed the last date's cases instead of sum of all cases, might need to change the number
		
		//totalCasesInThisCounty = this.cleanFile.getSumCase("Philadelphia", "Pennsylvania");
		//assertEquals(11877, totalCasesInThisCounty);
		
		//totalCasesInThisCounty = this.cleanFile.getSumCase("Addison", "Vermont");
		//assertEquals(61, totalCasesInThisCounty);
	}

	@Test
	void testGetSumDeath() {
		this.cleanFile.sumMap("death");
		
		int totalDeathsInThisCounty = this.cleanFile.getSumDeath("Snohomish", "Washington");
		assertEquals(1979, totalDeathsInThisCounty);
		
		//totalDeathsInThisCounty = this.cleanFile.getSumCase("Philadelphia", "Pennsylvania");
		//assertEquals(449, totalDeathsInThisCounty);
		
		//totalDeathsInThisCounty = this.cleanFile.getSumCase("Addison", "Vermont");
		//assertEquals(2, totalDeathsInThisCounty);
		
	}

	@Test
	void testCsForMonth() {
		//Shruthi 
		//creating monthCSMap2 using the correct method
		this.cleanFile.monthCSMap2();
		
		//6 county,state + 1 for header
		assertEquals(7, this.cleanFile.csForMonth("01").length);
		
	}
}