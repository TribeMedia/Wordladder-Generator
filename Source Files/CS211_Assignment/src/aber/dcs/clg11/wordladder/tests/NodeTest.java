package aber.dcs.clg11.wordladder.tests;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import aber.dcs.clg11.wordladder.model.Node;


/**
 * JUnit test class for scrutinising the {@link aber.dcs.clg11.wordladder.model.Node} class.
 * 
 * @author Connor Goddard (clg11)
 */
public class NodeTest {


	Node testNode;

	/**
	 * Sets up the testNode variable for each test.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {

		testNode = new Node();
	}

	/**
	 * Tests the Node default constructor.
	 */
	@Test
	public void testNodeDefaultConstructor() {

		assertNull("No name has been set, so should return null", testNode.getNodeData());
	}

	/**
	 * Tests the Node constructor that allows the node name to be specified.
	 */
	@Test
	public void testNodeDataConstructor() {

		testNode = new Node("testNode");

		assertEquals("Node data should equal 'testNode'", "testNode", testNode.getNodeData());
	}

	/**
	 * Tests the get/set predeccessor methods.
	 */
	@Test
	public void testGetSetPredeccessor() {

		Node testNeighbourNode = new Node("testNeighbourNode");

		testNode.setParent(testNeighbourNode);

		assertEquals("Returned parent should be 'testNeighbourNode", testNeighbourNode, testNode.getParent());

	}

	/**
	 * Tests the get/set nodeData methods.
	 */
	@Test
	public void testGetSetNodeData() {

		testNode.setNodeData("testNodeDataValue");

		assertEquals("Node data should return 'testNodeDataValue'", "testNodeDataValue", testNode.getNodeData());
	}


	/**
	 * Tests the get/set nodeList methods.
	 */
	@Test
	public void testGetSetNodeList() {

		Vector<Node> testNodeList = new Vector<Node>();

		for (int i = 0; i < 10; i++) {

			testNodeList.add(new Node("testNodeElement" + i));
		}

		testNode.setNodeList(testNodeList);

		for (int i = 0; i > 9; i++) {

			assertEquals("Node list element should match", "testNodeElement" + i, testNode.getNodeList().get(i));

		}

	}

	/**
	 * Tests the addLink method to ensure neighbour nodes can be referenced.
	 */
	@Test
	public void testAddLink() {

		assertEquals("List of neighbour nodes should equal 0", 0, testNode.getNodeList().size());

		for (int i = 0; i < 25; i++) {

			testNode.addLink(new Node("testNodeElement" + i));
		}

		assertEquals("List of neighbour nodes should equal 25", 25, testNode.getNodeList().size());
	}

	/**
	 * Tests the get/set isSearched methods.
	 */
	@Test
	public void testGetSetSearched() {

		assertFalse(testNode.getIsSearched());

		testNode.setIsSearched(true);

		assertTrue(testNode.getIsSearched());
	}


}
