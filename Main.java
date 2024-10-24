// Khanh Van | kxv230013 | CS 2336 | Project 3 Final Submission
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.Stack;

public class Main {
	public static void main(String args[]) throws FileNotFoundException {

		Scanner sc = new Scanner(System.in);
		File file = new File(sc.nextLine());
		sc = new Scanner(file);

		// Scanning the file and finding the anti-derivitives for each line (expression)
		while (sc.hasNextLine()) {
			String equation = sc.nextLine();
			// In case new line at the end of file - break the loop - exit program
			if (equation == "") {
				break;
			} else {
				// If the pipe don't have any bounds - perform indefinite anti-derivitives
				if (equation.charAt(0) == '|') {
					indefinite(equation);
				} else {
					// If the pipe do have bounds - perform definite anti-derivitives
					definite(equation);
				}
			}
		}
		// Closing the file scanner
		sc.close();
	}

	// Performing indefinite anti-derivitives
	public static void indefinite(String equation) {
		// Creating a new tree and stack to traverse the tree
		BinTree<Term> tree = new BinTree<>();
		Stack<Term> st = new Stack<>();
		String currentTerm = "";
		Character prevChar = ' ';
		// Loop to get each term of the expression (use arithmetic as delimiter)
		for (int i = 0; i < equation.length(); i++) {
			// Ignoring the pipe since it's indefinite
			if (equation.charAt(i) == '|') {
				continue;
			}
			// Appending the characters for current term
			currentTerm += equation.charAt(i);
			// Keep track of previous Character to see if the arithmetic is indeed delimiter
			// or not
			if (equation.charAt(i) != ' ') {
				prevChar = equation.charAt(i);
			}
			// If the next character of the String is either + - or d
			if (equation.charAt(i + 1) == '+' || equation.charAt(i + 1) == '-' || equation.charAt(i + 1) == 'd') {
				// If the previous Character appended is ^ - the arithmetic is only sign
				// (positive/negative)
				if (prevChar == '^') {
					continue;
				}
				// If the previous Character appended is either an integer or 'x' - the
				// arithmetic is delimiter
				if (Character.isDigit(prevChar) || prevChar == 'x') {
					// Add the fully appended String (Term) into the binary search tree
					tree.insert(new Term(currentTerm));
					currentTerm = "";
					// If next character is 'd', we finish with the current expression
					if (equation.charAt(i + 1) == 'd') {
						break;
					}
					continue;
				}
				// If next character is 'd', we finish with the current expression
				if (equation.charAt(i + 1) == 'd') {
					break;
				}
			}
		}
		// Traverse the tree In-Order Traversal and put the Terms into Stack from
		// smallest exponent to largest exponent
		// So that when popping the stack, we have results from largest exponent to
		// smallest exponent
		st = treeTraverse(tree);
		// If the stack is empty, means that there is no anti-derivitive
		if (st.isEmpty()) {
			System.out.println("0 + C");
		} else {
			// Popping the stack and displaying the anti-derivitive results for the
			// expression
			System.out.print(st.pop().getAntiDF());
			while (!st.isEmpty()) {
				System.out.print(outputForm(st.pop().getAntiD()));
			}
			System.out.print(" + C");
			System.out.println();
		}
	}

	// Performing definite anti-derivitive
	public static void definite(String equation) {
		// Creating new Tree and Stack to traverse the tree
		BinTree<Term> tree = new BinTree<>();
		Stack<Term> st = new Stack<>();
		String currentTerm = "";
		// String for lower bound and upper bound since it's definite integral
		String lower = "";
		String upper = "";
		int index = 0;
		// Getting the lower and upper bound for the expression
		while (equation.charAt(index) != '|') {
			lower += equation.charAt(index);
			index++;
		}
		index++;
		while (equation.charAt(index) != ' ') {
			upper += equation.charAt(index);
			index++;
		}

		// Getting int value for lower and upper bound
		int lowerInt = Integer.parseInt(lower);
		int upperInt = Integer.parseInt(upper);

		// Getting all the terms of the current expression
		Character prevChar = ' ';
		for (int i = index + 1; i < equation.length(); i++) {
			// Appending the current Character into the String (Term)
			currentTerm += equation.charAt(i);
			// Keep track of previously appended character
			if (equation.charAt(i) != ' ') {
				prevChar = equation.charAt(i);
			}
			// If next character is either + - or d -> check if it's delimiter or not
			if (equation.charAt(i + 1) == '+' || equation.charAt(i + 1) == '-' || equation.charAt(i + 1) == 'd') {
				// If previously appended character is ^, the arithmetic is just sign
				if (prevChar == '^') {
					continue;
				}
				// If previously appended character is a number or 'x' - the arithmetic is
				// delimiter
				if (Character.isDigit(prevChar) || prevChar == 'x') {
					// Add the current term into the tree
					tree.insert(new Term(currentTerm));
					currentTerm = "";
					// If next Character is 'd', we finished with current expression
					if (equation.charAt(i + 1) == 'd') {
						break;
					}
					continue;
				}
				// If next Character is 'd', we finished with current expression
				if (equation.charAt(i + 1) == 'd') {
					break;
				}
			}
		}

		// Traversing the tree In-Order Traversal
		st = treeTraverse(tree);
		// If Tree is empty
		if (st.isEmpty()) {
			System.out.println("0 + C");
		} else {
			// Popping the stack and get the anti-derivitive from largest to lowest exponent
			System.out.print(st.pop().getAntiDF());
			while (!st.isEmpty()) {
				System.out.print(outputForm(st.pop().getAntiD()));
			}
			// Creating a new stack from the current tree to get the answer for lower bound
			st = treeTraverse(tree);
			double lowerBoundAns = getDefiniteAns(st, lowerInt);
			// Creating a new stack from the current tree to get the answer for upper bound
			st = treeTraverse(tree);
			double upperBoundAns = getDefiniteAns(st, upperInt);
			// The result for definite integral is upper bound - lower bound
			double result = upperBoundAns - lowerBoundAns;
			DecimalFormat df = new DecimalFormat("#.###");
			// Displaying the result for definite integral in 3 decimal places.
			System.out.print(", " + lowerInt + "|" + upperInt + " = ");
			System.out.printf("%1.3f", result);
			System.out.println();
		}
	}

	// Helper method to display the anti-derivitve with appropriate spaces
	public static String outputForm(String s) {
		if (s.charAt(0) == '-') {
			// Display negative anti-derivitive with appropriate spaces
			return " - " + s.substring(1);
		} else {
			// Display positive anti-derivitive with appropriate spaces
			return " + " + s;
		}
	}

	// Traversing the tree In-Order and put Terms into stack
	public static Stack<Term> treeTraverse(BinTree<Term> tree) {
		Stack<Term> st = new Stack<>();
		traverseHelper(tree.getRoot(), st);
		return st;
	}

	// Traversing the Tree In-Order and put all the Terms in stack from smallest to
	// largest
	// When popping the stack, it will pop from largest to smallest exponent
	public static void traverseHelper(Node<Term> node, Stack<Term> st) {
		if (node == null) {
			return;
		}
		traverseHelper(node.getLeft(), st);
		// In case if two Terms with same exponent added together and cancel out - it
		// will not be put into the stack
		if (node.getData().getCoefficient() != 0) {
			st.add(node.getData());
		}
		traverseHelper(node.getRight(), st);
	}

	// Helper method to get the answer for definite integrals
	public static double getDefiniteAns(Stack<Term> st, int bound) {
		double result = 0;
		double tempResult = 0;
		// Going through the stack of the Term Tree
		while (!st.isEmpty()) {
			// Getting the coefficient as well as the power of the Terms and calculate the
			// result for the current Term
			String antiDwithx = st.pop().getAntiDInt();
			Term temp = new Term(antiDwithx);
			if (antiDwithx.contains("x")) {
				if (antiDwithx.contains("^")) {
					tempResult = Math.pow(bound, temp.getPower());
					tempResult = tempResult * temp.getCoefficient();
				} else {
					tempResult = bound * temp.getCoefficient();
				}

			}
			// Add the current Term result to the overall result
			result += tempResult;
		}
		// Return the expression result given the bound
		return result;
	}
}
