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
	void testDateArray() {
		//Emma
		
		//in total there is 95 days + 1 for header
		assertEquals(96, this.file.dateArray().length);
	}

	@Test
	void testMonthArray() {
		//Emma
		
		//in total there is 4 month + 1 for header
		assertEquals(5, this.file.monthArray().length);
	}

}
