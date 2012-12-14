package aber.dcs.clg11.wordladder.fileIO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;


/**
 * Accesses and parses internal/external dictionary files. Takes contents of dictionary file and returns the contents
 * as a Vector.
 * 
 * @author Connor Goddard (clg11)
 */
public class ReadFile {

	/**
	 * Reads in the contents of internal/external dictionary files and places the contents into a Vector 
	 * which is then returned, and used to populate the {@link aber.dcs.clg11.wordladder.model.Graph}.
	 *
	 * @param fileName The directory of the file to be parsed.
	 * @return Vector containing the contents of the parsed file.
	 */
	public Vector<String> readIn(String fileName) {

		try {

			//Create File IO objects
			FileReader fileReader;
			BufferedReader bufferedReader;

			//Initialise Vector used to store file contents (i.e. collection of words)
			Vector<String> words = new Vector<String>();

			//Initialise the File IO objects, passing in the selected file path
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);

			//Initialise local variable used to store the current line being read in
			String line = null;


			//While there are still lines to read in from the file (i.e. read in every line in the file)
			while ((line = bufferedReader.readLine()) != null) {

				//Add the current word to the Vector of words
				words.add(line);

			}

			//Once completed, safely close the file reader
			bufferedReader.close();

			//Return the vector of words obtained from the file
			return words;

		//If any IO exceptions occur...
		} catch (IOException iOE) {
			
			//.. return nothing.
			return null;
		}

	}
}
