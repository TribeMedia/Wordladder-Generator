package aber.dcs.clg11.wordladder.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import aber.dcs.clg11.wordladder.fileIO.ReadFile;
import aber.dcs.clg11.wordladder.model.Graph;
import aber.dcs.clg11.wordladder.model.Node;
import aber.dcs.clg11.wordladder.model.OutputStack;


/**
 * JUnit test class for scrutinising the {@link aber.dcs.clg11.wordladder.model.Graph} class.
 * 
 * @author Connor Goddard (clg11)
 */
public class GraphTest {
	
	private Graph testGraph;
	
	private ReadFile fileIO = new ReadFile();

	/**
	 * Sets up the testGraph variable for each test.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		
		testGraph = new Graph();
	}
	
	/**
	 * Method used to generate the testGraph object used within the JUnit tests.
	 *
	 * @param filePath The file path of the file to load in.
	 * @return the vector of the contents read in from the file.
	 */
	public Vector<String> createGraph(String filePath) {
		
		Vector<String> output = new Vector<String>();
		
		for (String s : fileIO.readIn(filePath)) {
			
			output.add(s);
			
		}
		
		return output;
	}
	

	/**
	 * Loads the file containing four words.
	 */
	public void loadFourLetterWords() {
		
		testGraph = new Graph(createGraph("dict4.dat"));

	}
	

	/**
	 * Loads the file containing five words.
	 */
	public void loadFiveLetterWords() {
	
		testGraph = new Graph(createGraph("dict5.txt"));
	}
	

	/**
	 * Loads the file containing six words.
	 */
	public void loadSixLetterWords() {

		testGraph = new Graph(createGraph("dict6.dat"));

	}
	
	/**
	 * Loads the file containing a sample of test words.
	 */
	public void loadTestWords() {
	
		testGraph = new Graph(createGraph("testWords.txt"));
	
	}

	/**
	 * Test method for {@link aber.dcs.clg11.wordladder.model.Graph#Graph()}.
	 */
	@Test
	public void testDefaultConstructor() {
		
		testGraph = new Graph();
		
		assertEquals("Graph size should equal 0", 0, testGraph.getGraphSize());
		

	}

	/**
	 * Test method for {@link aber.dcs.clg11.wordladder.model.Graph#Graph(java.lang.String)}.
	 */
	@Test
	public void testFilenameConstructor() {

		loadTestWords();
		
		assertEquals("Graph size should be 5", 5, testGraph.getGraphSize());
	}

	/**
	 * Test method for {@link aber.dcs.clg11.wordladder.model.Graph#createGraph(java.lang.String)}.
	 */
	@Test
	public void testCreateGraph() {
		
		loadTestWords();
		
		assertEquals("Graph size should be 5", 5, testGraph.getGraphSize());
	}

	/**
	 * Test method for {@link aber.dcs.clg11.wordladder.model.Graph#checkWords(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testCheckWords() {

		assertTrue("Should return true", testGraph.checkWords("test", "teat"));
		
		assertFalse("Should return false as words are more than one letter apart", testGraph.checkWords("test", "feat"));
		
		assertFalse("Should return false as words are equal and so not one letter apart", testGraph.checkWords("test", "test"));
		
		assertTrue("Should return true", testGraph.checkWords("setter", "getter"));
	
	}
	
	/**
	 * Test method for checking invalid four letter words within the graph.
	 */
	@Test
	public void testDepthSearchFourLetterInvalidTerm() {
		
		loadFourLetterWords();
		
		assertFalse("Algortihm should not have returned a result as this word does not exist", testGraph.depthSearch("breat", 0, 3));
		
		assertEquals("Stack should be empty as no search can take place", 0, testGraph.getOutputStack().sizeOf());

	}

	/**
	 * Test method for {@link aber.dcs.clg11.wordladder.model.Graph#depthSearch(java.lang.String, int, int)}.
	 */
	@Test
	public void testDepthSearchFourLetterValidTerm() {
		
		loadFourLetterWords();
		
		assertTrue("Method should have found result and so returns true", testGraph.depthSearch("test", 0, 4));
		
		OutputStack testStack = testGraph.getOutputStack();
		
		ArrayList<String> testArray = new ArrayList<String>();
		testArray.add("teat");
		testArray.add("teas");
		testArray.add("tear");
		testArray.add("team");
		
		for (int i = 0; i < testArray.size(); i++) {
			
			assertEquals(testArray.get(i), testStack.getHead());
			testStack.pop();
			
		}

	}
	
	/**
	 * Test method for checking invalid five letter words within the graph.
	 */
	@Test
	public void testDepthSearchFiveLetterInvalidTerm() {
		
		loadFiveLetterWords();
		
		assertFalse("Algortihm should not have returned a result as this word does not exist", testGraph.depthSearch("teff", 0, 4));
		
		assertEquals("Stack should be empty as no search can take place", 0, testGraph.getOutputStack().sizeOf());

	}
	
	/**
	 * Test method for {@link aber.dcs.clg11.wordladder.model.Graph#depthSearch(java.lang.String, int, int)}.
	 */
	@Test
	public void testDepthSearchFiveLetterValidTerm() {
		
		loadFiveLetterWords();
		
		assertTrue("Method should have found result and so returns true", testGraph.depthSearch("light", 0, 3));
		
		OutputStack testStack = testGraph.getOutputStack();
		
		ArrayList<String> testArray = new ArrayList<String>();
		testArray.add("eight");
		testArray.add("bight");
		testArray.add("bigot");

		
		for (int i = 0; i < testArray.size(); i++) {
			
			assertEquals(testArray.get(i), testStack.getHead());
			testStack.pop();
			
		}

	}
	
	/**
	 * Test method for checking invalid six letter words within the graph.
	 */
	@Test
	public void testDepthSearchSixLetterInvalidTerm() {
		
		loadSixLetterWords();
		
		assertFalse("Algortihm should not have returned a result as this word does not exist", testGraph.depthSearch("costos", 0, 4));
		
		assertEquals("Stack should be empty as no search can take place", 0, testGraph.getOutputStack().sizeOf());

	}
	
	/**
	 * Test method for {@link aber.dcs.clg11.wordladder.model.Graph#depthSearch(java.lang.String, int, int)}.
	 */
	@Test
	public void testDepthSearchSixLetterValidTerm() {
		
		loadSixLetterWords();
		
		assertTrue("Method should have found result and so returns true", testGraph.depthSearch("pelves", 0, 5));
		
		OutputStack testStack = testGraph.getOutputStack();
		
		ArrayList<String> testArray = new ArrayList<String>();
		testArray.add("delves");
		testArray.add("deaves");
		testArray.add("heaves");
		testArray.add("heaven");
		testArray.add("heaved");

		
		for (int i = 0; i < testArray.size(); i++) {
			
			assertEquals(testArray.get(i), testStack.getHead());
			testStack.pop();
			
		}

	}


	/**
	 * Test method for {@link aber.dcs.clg11.wordladder.model.Graph#getNode(java.lang.String)}.
	 */
	@Test
	public void testGetNode() {
	
		loadFourLetterWords();
		
		assertEquals("Returned node should be Coil", testGraph.getNode("coil").getNodeData(), "coil");
	}

	/**
	 * Test method for {@link aber.dcs.clg11.wordladder.model.Graph#resetSearch()}.
	 */
	@Test
	public void testResetSearch() {
		
		loadFourLetterWords();
		
		Node testNode = new Node("Parent");
		
		testGraph.getNode("test").setIsSearched(true);
		testGraph.getNode("test").setParent(testNode);
		
		assertTrue(testGraph.getNode("test").getIsSearched());
		assertEquals(testGraph.getNode("test").getParent(), testNode);
		
		testGraph.resetSearch();
		
		assertFalse(testGraph.getNode("test").getIsSearched());
		assertEquals(testGraph.getNode("test").getParent(), null);
		
	}

	/**
	 * Test method for {@link aber.dcs.clg11.wordladder.model.Graph#breadthFirstSearch(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testBreadthFirstSearchFourLetter() {
		
		loadFourLetterWords();
		
		assertTrue("Path can be obtained so BFS should return true",testGraph.breadthFirstSearch("head", "foot"));
		
		assertEquals("Total path size should be 6 as this is the shortest path", 6, testGraph.getOutputStack().sizeOf());
		
	}
	
	/**
	 * Test method for {@link aber.dcs.clg11.wordladder.model.Graph#breadthFirstSearch(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testBreadthFirstSearchFourLetterInvalidValue() {
		
		loadFourLetterWords();
		
		assertFalse("Origin word contains too many characters so BFS should return false", testGraph.breadthFirstSearch("trail", "foot"));
		
		assertEquals("Total path size should be 0 as this is the shortest path", 0, testGraph.getOutputStack().sizeOf());
	}
	
	/**
	 * Test method for {@link aber.dcs.clg11.wordladder.model.Graph#breadthFirstSearch(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testBreadthFirstSearchFiveLetter() {
		
		loadFiveLetterWords();
		
		assertTrue("Path can be obtained so BFS should return true", testGraph.breadthFirstSearch("train", "plate"));
		
		assertEquals("Total path size should be 8 as this is the shortest path", 8, testGraph.getOutputStack().sizeOf());
		
	}
	
	/**
	 * Test method for {@link aber.dcs.clg11.wordladder.model.Graph#breadthFirstSearch(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testBreadthFirstSearchFiveLetterInvalidValue() {
		
		loadFiveLetterWords();
		
		assertFalse("Target word does not exist, so BFS should return false",testGraph.breadthFirstSearch("train", "qwert"));
		
		assertEquals("Total path size should be 0", 0, testGraph.getOutputStack().sizeOf());
	}
	
	/**
	 * Test method for {@link aber.dcs.clg11.wordladder.model.Graph#breadthFirstSearch(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testBreadthFirstSearchSixLetter() {
		
		loadSixLetterWords();
		
		assertTrue("Path can be obtained so BFS should return true", testGraph.breadthFirstSearch("dunned", "grocer"));
		
		assertEquals("Total path size should be 13 as this is the shortest path", 13, testGraph.getOutputStack().sizeOf());
		
	}
	
	/**
	 * Test method for {@link aber.dcs.clg11.wordladder.model.Graph#breadthFirstSearch(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testBreadthFirstSearchSixLetterInvalidValue() {
		
		loadSixLetterWords();
		
		assertFalse("BFS has no target word so should return false", testGraph.breadthFirstSearch("abacus", ""));
		
		assertEquals("Total path size should be 0", 0, testGraph.getOutputStack().sizeOf());
	}


	/**
	 * Test method for {@link aber.dcs.clg11.wordladder.model.Graph#getOutputStack()}.
	 */
	@Test
	public void testGetOutputStack() {

		testGraph.getOutputStack().push("testElement1");
		testGraph.getOutputStack().push("testElement2");
		
		OutputStack testStack = testGraph.getOutputStack();
		
		assertEquals("testElement2", testStack.getHead());
		
		testStack.pop();
		
		assertEquals("testElement1", testStack.getHead());
		
	}

	/**
	 * Test method for {@link aber.dcs.clg11.wordladder.model.Graph#getGraphSize()}.
	 */
	@Test
	public void testGetGraphSize() {

		loadTestWords();
		
		assertEquals("Graph size should match number of words stored in testWords.txt", 5, testGraph.getGraphSize());
	}

}
