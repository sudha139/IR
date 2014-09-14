/**
 * 
 */
package edu.buffalo.cse.irf14.document;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import edu.buffalo.cse.irf14.document.Document;

/**
 * @author nikhillo
 * Class that parses a given file into a Document
 */
public class Parser {
	/**
	 * Static method to parse the given file into the Document object
	 * @param filename : The fully qualified filename to be parsed
	 * @return The parsed and fully loaded Document object
	 * @throws ParserException In case any error occurs during parsing
	 */
	public static Document parse(String filename) throws ParserException, IOException {
		Document d = new Document();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line = null;
			while ((line = getNonEmptyString(reader)) != null) {
								
				System.out.println(line);
			}
		}
		catch (FileNotFoundException fnfe) {
			System.out.println("File does not exists");
		}



		return null;
	}
	
	public static String getNonEmptyString(BufferedReader reader) throws IOException{
		String line = null;
		while ((line = reader.readLine()) != null) {
			if (line.isEmpty() || line.trim().equals("") || line.trim().equals("\n")) {
				System.out.println("Skipped blank line");
				continue;
			}
			else {
				return line;
			}
		}
		return null;
	}


}
