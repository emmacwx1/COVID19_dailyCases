package covid19;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderTest {

	Reader file;
	
	@BeforeEach
	void setUp() throws Exception {
		this.file = new Reader("us-counties.csv");
		this.file.cleanContent();
	}

	@Test
	void testUniqueDate() {
		//in total there are 95 days
		assertEquals(95, this.file.uniqueDate().size());
	}
	
	@Test
	void testDateOnDay() {
		assertEquals(95, this.file.dateOnDay("2020-04-24"));
		assertEquals(1, this.file.dateOnDay("2020-01-21"));
	}
	
	
	@Test
	void testDateArray() {
		//in total there are 95 days + 1 for header
		assertEquals(96, this.file.dateArray().length);
	}

	@Test
	void testMonthArray() {
		//in total there are 4 months + 1 for header
		assertEquals(5, this.file.monthArray().length);
	}

}
