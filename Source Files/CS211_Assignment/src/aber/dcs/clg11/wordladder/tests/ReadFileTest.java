package aber.dcs.clg11.wordladder.tests;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import aber.dcs.clg11.wordladder.fileIO.ReadFile;

/**
 * JUnit test class for scrutinising the {@link aber.dcs.clg11.wordladder.fileIO.ReadFile} class.
 * 
 * @author Connor Goddard (clg11)
 */
public class ReadFileTest {

	ReadFile testReadFile = new ReadFile();
	
	/**
	 * Tests that a valid file can be parsed and red-in correctly.
	 */
	@Test
	public void testReadInValidFile() {
		
		Vector<String> testVector = testReadFile.readIn("testWords.txt");

		assertEquals("Vector size should be 5 to match number of words in file.", 5, testVector.size());
	}
	
	/**
	 * Tests that an invalid file does not get read in, and null is returned instead.
	 */
	@Test
	public void testReadInInvalidFile() {
		
		Vector<String> testVector = null;
		
		testVector = testReadFile.readIn("fake.txt");
		assertNull("Vector size should be 0 to match number of words in file.", testVector);
	}

}
