// Khanh Van | kxv230013 | CS 2336 | Project 3 Final Submission
public class BinTree<T extends Comparable<T>> {
	private Node<Term> root;

	// Creating a new binary search tree with null root
	public BinTree() {
		this.root = null;
	}

	// Insert method
	public void insert(Term data) {
		this.root = insertHelper(this.root, data);
	}

	// Getter to get the tree root
	public Node<Term> getRoot() {
		return this.root;
	}

	// Helper for insert method that recursive
	private Node<Term> insertHelper(Node<Term> currNode, Term data) {
		// If the current child is null, insert the Node into current child
		if (currNode == null) {
			return new Node<Term>(data);
			// If the inserting Node is same exponent -> add inserting Node with current
			// Node
		} else if (data.compareTo(currNode.getData()) == 0) {
			currNode.setData(currNode.getData().addTerm(data));
			return currNode;
		}
		// If inserting Node is less than currentNode, go to left child
		if (data.compareTo(currNode.getData()) < 0) {
			currNode.setLeft(insertHelper(currNode.getLeft(), data));
		} else {
			// If inserting Node is greater than currentNode, go to right child
			currNode.setRight(insertHelper(currNode.getRight(), data));
		}
		return currNode;
	}

	// Delete method of BinTree class
	public void delete(Term data) {
		root = deleteHelper(this.root, data);
	}

	// Helper method to recursively delete a Node from the tree
	private Node<Term> deleteHelper(Node<Term> currNode, Term data) {
		// Base case, if the current Node is null -> the Term doesn't exists
		if (currNode == null) {
			return null;
		}
		// Going through the tree left and right accordingly to the comparison
		if (currNode.getData().compareTo(data) > 0) {
			currNode.setLeft(deleteHelper(currNode.getLeft(), data));
		} else if (currNode.getData().compareTo(data) < 0) {
			currNode.setRight(deleteHelper(currNode.getRight(), data));
		} else {
			// If desired delete Node is found
			if (currNode.getLeft() == null && currNode.getRight() == null) {
				// Return null if current Node don't have any children
				return null;
			} else if (currNode.getLeft() == null) {
				// Return the right children if current Node don't have left children
				return currNode.getRight();
			} else if (currNode.getRight() == null) {
				// Return the left children if current Node don't have right children
				return currNode.getLeft();
			} else {
				// If Node has both children, find the smallest Node the the right subtree
				// of the current Node and set that Node to current Node
				Node<Term> smallRightNode = findSmallest(currNode.getRight());
				currNode.setData(smallRightNode.getData());
				currNode.setRight(deleteHelper(currNode.getRight(), smallRightNode.getData()));
			}
		}
		return currNode;
	}

	// Helper method to find the smallest Node in the right subtree
	private Node<Term> findSmallest(Node<Term> currNode) {
		while (currNode.getLeft() != null) {
			currNode = currNode.getLeft();
		}
		return currNode;
	}

	// Method to search for a specific Term
	public boolean search(Term data) {
		return searchHelper(this.root, data);
	}

	// Helper method to traverse the tree recursively to find the Term
	private boolean searchHelper(Node<Term> currNode, Term data) {
		// Base case, the term never found -> return false;
		if (currNode == null) {
			return false;
		}
		// Base case, the term is found -> return true;
		if (currNode.getData().compareTo(data) == 0) {
			return true;
			// If current looking for Term is smaller than current Node Term -> go left
		} else if (currNode.getData().compareTo(data) > 0) {
			return searchHelper(currNode.getLeft(), data);
			// If current looking for Term is larger than current Node Term -> go right
		} else {
			return searchHelper(currNode.getRight(), data);
		}
	}
}
