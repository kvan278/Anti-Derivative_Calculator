// Khanh Van | kxv230013 | CS 2336 | Project 3 Final Submission
public class Node<T extends Comparable<T>> {
	private Node<T> leftPointer;
	private Node<T> rightPointer;
	private Term data;

	// Creating new Node
	public Node(Term data) {
		this.data = data;
		this.leftPointer = null;
		this.rightPointer = null;
	}

	// Set the data for the Node if there are modification
	public void setData(Term term) {
		this.data = term;
	}

	// Getter to get the content of the current Node
	public Term getData() {
		return this.data;
	}

	// Set the left child of current Node
	public void setLeft(Node<T> leftNode) {
		this.leftPointer = leftNode;
	}

	// Set the right child of the current Node
	public void setRight(Node<T> rightNode) {
		this.rightPointer = rightNode;
	}

	// Getter for the left child of the current Node
	public Node<T> getLeft() {
		return this.leftPointer;
	}

	// Getter for the right child of the current Node
	public Node<T> getRight() {
		return this.rightPointer;
	}

	// compareTo method of the two Nodes
	public int compareTo(Node<T> otherNode) {
		return this.data.compareTo(otherNode.getData());
	}

}
