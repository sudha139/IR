/**
 * 
 */
package edu.buffalo.cse.irf14.document;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import edu.buffalo.cse.irf14.document.Document;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	public static Document parse(String filename) throws  IOException, ParserException {
		Document d = new Document();
		try {
                    
                        if ((filename == null) || (filename == "")){
                            throw new ParserException();
                        }
                        File f = new File(filename);
                        if (!f.exists()) {
                            throw new ParserException();
                        }
                        
                        
			String fileid = null;
			String category = null;
			String title = null;
			String[] authors = null;
			String place = null;
                        String place_1 = null;
			String date = null;

			String parts[] = filename.split("/");
			fileid = parts[parts.length - 1];
			d.setField(FieldNames.FILEID, fileid);
			category = parts[parts.length - 2];
			d.setField(FieldNames.CATEGORY, category);

			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (line.isEmpty() || line.trim().equals("") || line.trim().equals("\n")) {
					continue;
				}
				break;
			}
			title = line.trim();
                     
			d.setField(FieldNames.TITLE, title);
			while ((line = reader.readLine()) != null) {
				if (line.isEmpty() || line.trim().equals("") || line.trim().equals("\n")) {
					continue;
				}
				break;
			}
			String pattern = "<AUTHOR> *[Bb][Yy] *(.+?) *</AUTHOR>";
			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(line);
                        
			if (m.find()) {
                              
				String author_line = m.group(1);
                                
				String[] temp = author_line.split(",");
				String authors_temp = temp[0];
				String AuthorOrg = null;
				if (temp.length > 1) {
					AuthorOrg = temp[1].trim();
					d.setField(FieldNames.AUTHORORG, AuthorOrg);
				}

				authors = authors_temp.split(" and ");
				d.setField(FieldNames.AUTHOR, authors);
                                while ((line = reader.readLine()) != null) {
				if (line.isEmpty() || line.trim().equals("") || line.trim().equals("\n")) {
					continue;
				}
				break;
			}
			
			}
                        
                                if (line.split("-")[0].split(",").length  == 3)  {
                                    place = line.split("-")[0].split(",")[0].trim();
                                    place_1 = line.split("-")[0].split(",")[1].trim();
                                    date = line.split("-")[0].split(",")[2].trim();
                                    d.setField(FieldNames.PLACE, place + ", " + place_1);
                                }
                                else {
                                    place = line.split("-")[0].split(",")[0].trim();
                                    date = line.split("-")[0].split(",")[1].trim();
                                    d.setField(FieldNames.PLACE, place);
                                }
                                                                   
				
				
				d.setField(FieldNames.NEWSDATE, date);	

					

			
		}
		catch (FileNotFoundException fnfe) {
			System.out.println("File does not exists");
		}
               



		return d;
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
