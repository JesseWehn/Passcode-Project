import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


	import java.util.*;
	public class PasswordChecker
	{
		// Constants
		private final char TERMINATE_CHAR = '.'; // set this to whatever, but can't be a letter within English alphabet
		
		// Fields
		Node rootNode;	// points to the 1st node on the 1st level. It does not have any peers, and only 1 child node
		
		/**
		 * Constructor
		 */
		public PasswordChecker()
		{
			rootNode = new Node();
		}
		
		public boolean add(String s)
		{
			// append a terminator at the end of string
			s = s + TERMINATE_CHAR;
			Node currentNode = rootNode;
			boolean added = false;
			for (int i = 0; i < s.length(); i++)
			{
				char c = s.charAt(i);
				NodeAddResult result = addChildNode(currentNode, c);
				currentNode = result.node;
				added = result.added; // the node is added if it did not exist before
			}
			
			return added;
		}
		
		public int search(StringBuilder key)
		{
			Node currentNode = rootNode;
			for (int i = 0; i < key.length(); i++)
			{
				char c = key.charAt(i);
				currentNode = getChildNode(currentNode, c);
				
				if (currentNode == null)
				{
					// Not a word, not a prefix
					return 0;
				}
			}
			
			// Method has not returned yet
			// currentNode is at the last char of the string, without terminator char
			// Find the node with terminator in currentNode's child nodes
			Node terminatorNode = getChildNode(currentNode, TERMINATE_CHAR);
			if (terminatorNode == null)
			{
				// The last char in the string does not have a terminator
				// Not a word, but a prefix
				return 1;
			}
			else if (terminatorNode.peerNode == null)
			{
				// There is a terminator node, but it does not have peers
				// A word, but not a prefix
				return 2;
			}
			else
			{
				// There is a terminator node and it has peers
				// A word, and a prefix
				return 3;
			}
		}
		
		/**
		 * Searches for char c in the peer nodes of this node.
		 * @param peerStart The first peer node on the level of search.
		 * @param c The containing char of the node to search for.
		 * @return The peer node that contains the char. Returns null if not found.
		 */
		private Node getPeerNode(Node peerStart, char c)
		{
			Node nextPeer = peerStart;
			while (nextPeer != null)
			{
				// break if data matches, so the node will be retained
				if (nextPeer.value == c) break;
				
				// go to next node if it ain't the char you are looking for
				nextPeer = nextPeer.peerNode;
			}
			return nextPeer;
		}
		
		/**
		 * Searches for char c in the child nodes of this node
		 * @param parentNode The parent node of the child nodes
		 * @param c The containing char of the node to search for.
		 * @return The child node that contains the char. Returns null if not found.
		 */
		private Node getChildNode(Node parentNode, char c)
		{
			return getPeerNode(parentNode.childNode, c);
		}
		
		/**
		 * Adds a peer node on this level.
		 * @param peerStart The first peer node on the level.
		 * @param c The char to be added
		 * @return The node added. If a node containing the char already exists, that node will be returned.
		 */
		private NodeAddResult addPeerNode(Node peerStart, char c)
		{
			if (peerStart == null)
			{
				peerStart = new Node(c);
				return new NodeAddResult(peerStart, true);
			}
			else
			{
				Node nextPeer = peerStart;
				while (nextPeer.peerNode != null)
				{
					if (nextPeer.value == c) break;
					nextPeer = nextPeer.peerNode;
				}
				// right now nextPeer is either a node that contains char,
				// or the last node on the level and char is not found
				if (nextPeer.value == c)
				{
					// This node has the right data, no need to create a new one
					return new NodeAddResult(nextPeer, false);
				}
				else
				{
					// Did not find a node with specified value, create one in the end of the chain
					nextPeer.peerNode = new Node(c);
					return new NodeAddResult(nextPeer.peerNode, true);
				}
			}
		}
		
		/**
		 * Adds a child node to this parent node.
		 * @param parentNode The parent node where the child node should be added to
		 * @param c The char to be added
		 * @return The node added. If a node containing the char already exists, that node will be returned.
		 */
		private NodeAddResult addChildNode(Node parentNode, char c)
		{
			// Check if parentNode has a child node or not
			if (parentNode.childNode == null)
			{
				// Parent node does not have any children
				// Simply create a node as its child node and return it
				parentNode.childNode = new Node(c);
				return new NodeAddResult(parentNode.childNode, true);
			}
			else
			{
				// Parent node has a child node
				// Call addPeerNode() using the child node
				return addPeerNode(parentNode.childNode, c);
			}
		}
	}

	/**
	 * Represents a node in DLB trie
	 */
	class Node
	{
		Node peerNode;
		Node childNode;
		char value;
		
		public Node() { }
		
		public Node(char value)
		{
			this(value, null, null);
		}
		
		public Node(char value, Node peerNode, Node childNode)
		{
			this.value = value;
			this.peerNode = peerNode;
			this.childNode = childNode;
		}
	}

	/**
	 * The result of an add node operation.
	 * node is the node the method added, or an existing node that contains the value we are looking for
	 * added is true if the method added this node, false if the node already exists and method did not add the node.
	 */
	class NodeAddResult
	{
		Node node;
		boolean added;
		
		public NodeAddResult(Node node, boolean added)
		{
			this.node = node;
			this.added = added;
		}
	}


	
	

