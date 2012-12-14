package aber.dcs.clg11.wordladder.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import aber.dcs.clg11.wordladder.model.OutputStack;

/**
 * JUnit test class for scrutinising the {@link aber.dcs.clg11.wordladder.model.OutputStack} class.
 * 
 * @author Connor Goddard (clg11)
 */
public class OutputStackTest {
	
	OutputStack testStack;

	/**
	 * Sets up the testGraph variable for each test.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		
		testStack = new OutputStack();
	}

	/**
	 * Tests the OutputStack default constructor.
	 */
	@Test
	public void testStack() {

		assertEquals("Stack size should be 0 as new OutputStack object has been created.", 0, testStack.sizeOf());
	}

	/**
	 * Tests that a new element can be pushed onto the stack.
	 */
	@Test
	public void testPush() {
		
		testStack.push("testElement1");
		testStack.push("testElement2");
		
		assertEquals(2, testStack.sizeOf());
		assertEquals("testElement2", testStack.getHead());	
		
	}

	/**
	 * Tests that an element can be removed from the top of the stack.
	 */
	@Test
	public void testPop() {
		
		testStack.push("testElement1");
		testStack.push("testElement2");
		
		assertEquals(2, testStack.sizeOf());
		
		testStack.pop();
		assertEquals(1, testStack.sizeOf());
		
		testStack.pop();
		assertEquals(0, testStack.sizeOf());
	}

	/**
	 * Tests that the correct size of the stack can be returned.
	 */
	@Test
	public void testSizeOf() {
		
		assertEquals("Size should return 0, as the stack is new", 0, testStack.sizeOf());
		
		testStack.push("testElement1");
		testStack.push("testElement2");
		
		assertEquals("Size should return 2, as there are now two elements in the stack", 2, testStack.sizeOf());
	}

	/**
	 * Tests that the status of the stack being empty can be returned correctly.
	 */
	@Test
	public void testIsEmpty() {
		
		assertTrue("Stack should be empty as it has been newly created", testStack.isEmpty());
		
		testStack.push("testElement1");
		testStack.push("testElement2");
		
		assertFalse("Stack should not be empty as two elements are now in the stack", testStack.isEmpty());
		
		testStack.pop();
		testStack.pop();
		
		assertTrue("Stack should be empty as all elements have been removed.", testStack.isEmpty());
		
	}

/**
  * Tests that the element at the top of the stack can be returned correctly.
  */
 @Test
	public void testGetHead() {
		
		for (int i = 0; i < 5; i++) {
			
			testStack.push("testElement" + i);
			
		}
		
		for (int i = 4; i > 0; i--) {
			
			assertEquals("testElement" + i, testStack.getHead());
			testStack.pop();
			
		}
	}

}
