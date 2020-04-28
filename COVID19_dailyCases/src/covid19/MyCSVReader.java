package covid19;

import java.io.FileReader;
import java.util.Map;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderHeaderAware;


public class MyCSVReader {
	CSVReader reader = new CSVReader(new FileReader("us-counties.csv"));
	
	String[] nextLine;
	while((nextLine = reader.readNext()) !=null) {
		String key = nextLine[0] + nextLine[1];
		System.out.println(nextLine[0] + nextLine[1]);
	}
	
}
