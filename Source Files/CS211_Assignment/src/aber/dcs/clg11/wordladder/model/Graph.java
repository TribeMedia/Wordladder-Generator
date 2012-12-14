package aber.dcs.clg11.wordladder.model;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Vector;


/**
 * Main data class within the application used to represent the Graph data structure. 
 * Contains both search algorithms (BFS and DLS) and the adjacency list implementation via a hash table.
 * 
 * @author Connor Goddard (clg11)
 */
public class Graph {

	/** Adjacency list implementation by using a Hash table. */
	private Hashtable<String, Node> nodeDirectory = new Hashtable<String, Node>();
	
	/** OutputStack used to store search results. */
	private OutputStack outputStack = new OutputStack();

	/**
	 * Default constructor for Graph.
	 */
	public Graph() {

	}
	
	/**
	 * Instantiates a new Graph, with the ability to generate the graph using a 
	 * Vector of strings.
	 *
	 * @param input Vector containing data to populate the graph.
	 */
	public Graph(Vector<String> input) {
		
		createGraph(input);
	}

	/**
	 * Populates the adjacency list structure using data obtained 
	 * via the {@link aber.dcs.clg11.wordladder.fileIO.ReadFile} class.
	 * 
	 * @param input Vector containing data to populate the graph 
	 */
	public void createGraph(Vector<String> input) {

		//For every element in the vector of words 
		for (String s : input) {

			//Create a new Node object to represent the current word
			Node newNode = new Node(s);

			//Iterate through existing elements in hash table
			Enumeration<String> e = nodeDirectory.keys();

			//Check that the has table size > 0
			if (nodeDirectory.size() > 0) {

				//For every existing Node in the hash table
				while (e.hasMoreElements()) {

					String nextElement = e.nextElement();

					//Compare the new word with the current node in the hash table
					//Check if the two words are one letter apart
					if (checkWords(newNode.getNodeData(), nextElement)) {

						//If they are one letter apart..
						
						//Create a temporary object to represent the identified node within the hash table
						Node otherNode = nodeDirectory.get(nextElement);

						//Create an edge between the two nodes
						newNode.addLink(otherNode);
						otherNode.addLink(newNode);

					}
				}

			}

			//Add the new Node object to the hash table 
			nodeDirectory.put(s, newNode);
		}

	}

	/**
	 * Compares two strings to identify if they are one letter apart. 
	 *
	 * @param word1 The first word to compare
	 * @param word2 The second word to compare
	 * @return true If the two words are one letter apart
	 */
	public boolean checkWords(String word1, String word2) {

		//If the lengths of the two words are different
		if (word1.length() != word2.length()) {

			//Return false as the words are going to be different
			return false;

			//Otherwise..
		} else {

			int differ = 0;
			
			//For every character that makes up the first word..
			for (int i = 0; i < word1.length(); i++) {
				
				//Check if the letter at the position in word one, matches the letter at the same position in word 2
				if (word1.charAt(i) != word2.charAt(i)) {
					
					//If the letters do not match, increment the value
					differ++;
				}
			}

			//Return true if the value is 1 (i.e. there is a one letter difference)
			return (differ == 1);

		}
	}


	/**
	 * Depth limited search that traverses through the graph from a specified root node to 
	 * create a word ladder of specified length.
	 *
	 * @param word The specified root node (also used as part of the recursive function).
	 * @param currentDepth The current depth of the search within the graph (used as part of the recursive function)
	 * @param maxDepth The maximum depth that the search can traverse to (i.e. the specified word ladder length)
	 * @return true Returns if path is able to be retrieved.
	 */
	public boolean depthSearch(String word, int currentDepth, int maxDepth) {

		//Declare boolean used within recursive algorithm
		Boolean resultFound;

		//Node object used to represent the current Node being accessed
		Node searchNode;
		
		//Check if the specified root node exists in the graph
		if (getNode(word) == null) {
			
			//If it does not exist, return false.
			return false;
		
			//Otherwise if the word does exist..
		} else {
			
			//Set the local Node variable to the current node being accessed by the algorithm
			searchNode = getNode(word);
			
			//Set the Node as being searched to prevent it being searched again and loops forming
			searchNode.setIsSearched(true);
		}

		//Check if the algorithm has searched to the maximum specified depth
		if (currentDepth < maxDepth) {

			//If it has not..
			
			//For every node that has an edge to the current node
			for (Node neighbour : searchNode.getNodeList()) { 

				//If the neighbour has not already been searched
				if (neighbour.getIsSearched().equals(false)) {

					//Set the neighbour to 'isSearched' value to true
					neighbour.setIsSearched(true);

					//Recursively run the algorithm again for this neighbour node
					resultFound = depthSearch(neighbour.getNodeData(), currentDepth + 1, maxDepth);
					
					//If the recursive algorithm returns true..
					if (resultFound) {
						
						//Push this neighbour to the output stack
						outputStack.push(neighbour.getNodeData());
						
						//Return true to trigger the previous recursive algorithm to terminate
						return true; 

					}

				}

			}

		}

		//Reset all node search variables
		resetSearch();
		
		//Return true to exit algorithm
		return true;

	}

	/**
	 * Returns the {@link aber.dcs.clg11.wordladder.model.Node} object that
	 * represents the search term entered by the user.
	 *
	 * @param searchTerm The word that is to be returned.
	 * @return The node object that represents the specified word.
	 */
	public Node getNode(String searchTerm) {

		return nodeDirectory.get(searchTerm);
	}

	/**
	 * Resets search variables contained within every {@link aber.dcs.clg11.wordladder.model.Node} object 
	 * currently within the {@link aber.dcs.clg11.wordladder.model.Graph}.
	 */
	public void resetSearch() {

		//Iterate through the hash table
		Enumeration<String> e = nodeDirectory.keys();

		//For every Node object..
		while (e.hasMoreElements()) {

			Node reset = nodeDirectory.get(e.nextElement());

			//Reset the search variables
			reset.setIsSearched(false);
			reset.setParent(null);
		}

	}

	/**
	 * Breadth first search algorithm that traverses from the specified root node through every 
	 * level of the {@link aber.dcs.clg11.wordladder.model.Graph} until it locates the specified target node.
	 * The algorithm then returns the shortest possible path (i.e. word ladder) between the root and target nodes.
	 * 
	 * Code modified from original source code available at: http://www.codeproject.com/script/Articles/ViewDownloads.aspx?aid=32212
	 *
	 * @param word The specified root node.
	 * @param target The specified target node.
	 * @return true Returns if a path is able to be retrieved.
	 */
	public boolean breadthFirstSearch(String word, String target) {

		//Create a new queue used for storing nodes that are still to be checked
		LinkedList<Node> q = new LinkedList<Node>();

		//If the either the root, or target word cannot be located as a node within the graph..
		if (getNode(word) == null || getNode(target) == null) {
			
			//Terminate the search algorithm
			return false;
		} 
			
		//Otherwise, retrieve the start word node from the graph
		Node root = getNode(word);	
		
		//Add this node to the process queue
		q.add(root);

		//Set the 'isSearched' value to true to prevent the Node being checked again
		root.setIsSearched(true);

		//While there are still Nodes to be checked
		while(! q.isEmpty()) {

			//Remove the next Node to be checked from the queue and set to local variable
			Node n = q.remove();

			//For every Node with an edge to the current Node
			for (Node child : n.getNodeList()) {

				//If that Node has not already been checked..
				if (child.getIsSearched().equals(false)) {

					//Prevent it being checked again for this search
					child.setIsSearched(true);
					
					//Set the parent node of this Node to the Node that it is "child" of (i.e neighbour)
					//Used to return the path (word ladder) once a search has completed
					child.setParent(n);
					
					//Add this Node to the process queue to allow its neighbours to be searched
					q.add(child);

				}

			}
		}

		//Once all nodes have been searched..
		
		//Retrieve the Node representing the target node
		Node current = getNode(target);
		
		//Push this Node to the stack (word ladder)
		outputStack.push(current.getNodeData());

		//Until the start word node is reached
		while(! current.equals(getNode(word))) {
		
			//Retrieve the parent of the 'current' node
			current = current.getParent();
			
			//If one of the nodes does not have a parent
			//There is no word ladder available between the two words
			if (current == null) {
				
				//Therefore reset the node search variables
				resetSearch();
				
				//Terminate the search algorithm informing the GUI that a ladder cannot be found
				return false;
			}

			//Otherwise push the 'current' node to the output stack
			outputStack.push(current.getNodeData());
		}
		
		//Reset the node search variables
		resetSearch();
		
		//Terminate the algorithm informing the GUI that a ladder was located
		return true;
	}

	/**
	 * Retrieves the {@link aber.dcs.clg11.wordladder.model.OutputStack} object used to store the result
	 * path of either search algorithm
	 *
	 * @return the output stack containing the word ladder path generated by the search algorithms.
	 */
	public OutputStack getOutputStack() {
		return outputStack;
	}
	
	/**
	 * Retrieves the total size of the graph by counting all the elements (Nodes) contained within the hash table.
	 *
	 * @return the number of elements within the hash table (adjacency list). 
	 */
	public int getGraphSize() {
		
		return nodeDirectory.size();
		
	}

}