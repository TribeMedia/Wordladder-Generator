package aber.dcs.clg11.wordladder.model;

import java.util.Vector;

/**
 * Data class used to represent each word read in from a dictionary file as a 
 * 'Node' element within the {@link aber.dcs.clg11.wordladder.model.Graph} data structure.
 * 
 * @author Connor Goddard (clg11)
 */
public class Node {
	
	/** The name of the word that the Node represents */
	private String nodeData;
	
	/** Vector that contains a list of all other nodes that has an edge to this node. */
	private Vector<Node> nodeList = new Vector<Node>();
	
	/** Used by the search algorithm to determine if this node has already been searched */
	private boolean isSearched = false;
	
	/** Used by BFS to traverse through an identified path (word ladder) */
	private Node parent = null;
	
	/**
	 * Default constructor for a new Node.
	 */
	public Node() {
		
	}
	
	/**
	 * Constructor for a new Node that allows the name of the node to be specified.
	 *
	 * @param data the data
	 */
	public Node(String data) {
		setNodeData(data);
	}
	
	/**
	 * Returns the parent Node of this Node.
	 *
	 * @return the parent node
	 */
	public Node getParent() {
		return parent;
	}

	/**
	 * Sets the parent Node of this Node.
	 *
	 * @param newParent The new parent node to be set.
	 */
	public void setParent(Node newParent) {
		parent = newParent;
	}

	/**
	 * Returns the name of the node (i.e. the word this node represents)
	 *
	 * @return the name of the node
	 */
	public String getNodeData() {
		return nodeData;
	}
	
	/**
	 * Sets the name of the node.
	 *
	 * @param nodeData The new name for the node.
	 */
	public void setNodeData(String nodeData) {
		this.nodeData = nodeData;
	}
	
	/**
	 * Returns the vector that contains a list of all neighbour Nodes.
	 *
	 * @return the vector of neighbour nodes.
	 */
	public Vector<Node> getNodeList() {
		return nodeList;
	}
	
	/**
	 * Sets the list of neighbour nodes.
	 *
	 * @param newNodeList The new vector of neighbour nodes to be set.
	 */
	public void setNodeList(Vector<Node> newNodeList) {
		nodeList = newNodeList;
	}
	
	/**
	 * Creates a reference to a new neighbour node in the list of neighbour nodes.
	 *
	 * @param newNode the new node to be added.
	 */
	public void addLink(Node newNode) {
		nodeList.add(newNode);
	}
	
	/**
	 * Sets the isSearched boolean which is used by the search algorithms.
	 *
	 * @param search The boolean to set the isSearched boolean.
	 */
	public void setIsSearched(Boolean search) {
		isSearched = search;
	}
	
	/**
	 * Returns the isSearched boolean.
	 *
	 * @return the boolean the determines if the node has already been searched.
	 */
	public Boolean getIsSearched() {
		return isSearched;
	}
}
