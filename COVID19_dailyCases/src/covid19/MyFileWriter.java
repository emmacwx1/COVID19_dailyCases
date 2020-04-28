package covid19;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class MyFileWriter {

public static void writeFile(String fileName, List<List<String>> cleanFile, boolean append) {
		
		//File file = new File(fileName);
		FileWriter fileWriter = null;
		PrintWriter printWriter = null;
		
		//declare fileWriter and printWriter
		try {
			fileWriter = new FileWriter(fileName);
			printWriter = new PrintWriter(fileWriter);
			fileWriter.append("Date");
			fileWriter.append(",");
			fileWriter.append("Month");
			fileWriter.append(",");
			fileWriter.append("Day");
			fileWriter.append(",");
			fileWriter.append("County");
			fileWriter.append(",");
			fileWriter.append("State");
			fileWriter.append(",");
			fileWriter.append("Confirmed Cases");
			fileWriter.append(",");
			fileWriter.append("Deaths");
			fileWriter.append("\n");
			
			for(int row = 0; row <= cleanFile.get(0).size() - 1; row ++) {
				for (int col = 0; col <= cleanFile.size() - 1; col++) {
					fileWriter.append(cleanFile.get(col).get(row));
					fileWriter.append(",");
				}
				fileWriter.append("\n");
			}
			printWriter.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fileWriter.close();
				printWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
}
