package aber.dcs.clg11.wordladder.model;

import java.util.ArrayList;

/**
 * Class used to represent a Stack data structure. Utilised DLS and BFS by the {@link aber.dcs.clg11.wordladder.model.Graph}
 * class to output the result of word ladder searches.
 * 
 * @author Connor Goddard (clg11)
 */
public class OutputStack {
	
	/** Array list used as the data structure for the stack.  */
	private ArrayList<String> stack;
	
	/**
	 * Instantiates a new OutputStack.
	 */
	public OutputStack() {
		
		stack = new ArrayList<String>();
		
	}
	
	/**
	 * Pushes a new element to the top of the stack.
	 *
	 * @param element The element to be pushed onto the stack.
	 */
	public void push(String element) {
			
		stack.add(element);
		
	}
	
	/**
	 * Removes the top element from the stack.
	 */
	public void pop() {
		
		if (stack.isEmpty()) {
			
			throw new StackOverflowError("Stack is empty");
			
		} else {
			
			stack.remove(stack.size() - 1);
	
		}
	}
	
	/**
	 * Returns the total size of the stack.
	 *
	 * @return the size of the stack.
	 */
	public int sizeOf() {
		
		return stack.size();
		
	}
	
	/**
	 * Checks if the stack is empty.
	 *
	 * @return true if the stack is empty.
	 */
	public boolean isEmpty() {
		
		return stack.isEmpty();
	}
	
	/**
	 * Returns the top element of the stack.
	 *
	 * @return the top element of the stack.
	 */
	public String getHead() {
		
		return stack.get(stack.size() - 1);
	}
	
}
