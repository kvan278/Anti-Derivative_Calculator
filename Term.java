// Khanh Van | kxv230013 | CS 2336 | Project 3 Final Submission
public class Term implements Comparable<Term> {

	private String term;
	private double coefficient;
	private int power;

	// Creating a new Term object with the String expression
	public Term(String term) {
		this.term = term;
		// Getting coefficient for this Term
		coefficient();
		// Getting exponent for this Term
		power();
	}

	// Method to get coefficient for this Term
	private void coefficient() {
		String coefficientStr = "";
		// Read and append the String expression until reach 'x'
		for (int i = 0; i < term.length(); i++) {
			if (term.charAt(i) == 'x') {
				break;
			} else {
				if (term.charAt(i) != ' ') {
					coefficientStr += term.charAt(i);
				}
			}
		}
		// If nothing is appended, coefficient will be 1
		if (coefficientStr == "") {
			coefficient = 1;
			return;
			// If '-' is appended and nothing else -> coefficient = -1
			// If '-' is appended and another '-' is appended -> make coefficient positive
			// If '-' is appended and '+' is appended -> make coefficient negative
		} else if (coefficientStr.charAt(0) == '-') {
			if (coefficientStr.length() == 1) {
				coefficient = -1;
				return;
			}
			if (coefficientStr.charAt(1) == '-') {
				if (coefficientStr.length() == 2) {
					coefficient = 1;
					return;
				}
				coefficientStr = coefficientStr.substring(2);
			} else if (coefficientStr.charAt(1) == '+') {
				if (coefficientStr.length() == 2) {
					coefficient = -1;
					return;
				}
				coefficientStr = "-" + coefficientStr.substring(2);
			}
			// If '+' is appended and nothing else -> coefficient = 1
			// If '+' is appended and another '+' is appended -> make coefficient positive
			// If '+' is appended and '-' is appended -> make coefficient negative
		} else if (coefficientStr.charAt(0) == '+') {
			if (coefficientStr.length() == 1) {
				coefficient = 1;
				return;
			}
			if (coefficientStr.charAt(1) == '-') {
				if (coefficientStr.length() == 2) {
					coefficient = -1;
					return;
				}
				coefficientStr = "-" + coefficientStr.substring(2);
			} else if (coefficientStr.charAt(1) == '+') {
				if (coefficientStr.length() == 2) {
					coefficient = 1;
					return;
				}
				coefficientStr = coefficientStr.substring(2);
			}
		}
		// Return coefficient as double it can be calculated for the result of definite
		// integrals
		coefficient = Double.parseDouble(coefficientStr);
	}

	private void power() {
		// Removing all the white spaces from the Term String
		String powerStr = "";
		String newTerm = term.replaceAll("\\s", "");
		// Read until reach 'x'
		for (int i = 0; i < newTerm.length(); i++) {
			// If reached 'x' and nothing else left -> means the power is 1
			if (newTerm.charAt(i) == 'x') {
				if (i == newTerm.length() - 1) {
					power = 1;
					return;
				} else {
					// Append the Character to make the exponent number
					for (int j = i + 1; j < newTerm.length(); j++) {
						if (newTerm.charAt(j) != ' ' && newTerm.charAt(j) != '^') {
							powerStr += newTerm.charAt(j);
						}
					}
					power = Integer.parseInt(powerStr);
					return;
				}
			}
		}

		// If nothing is appended, it means the there is no 'x' and power is 0
		if (powerStr == "") {
			power = 0;
			return;
		}
	}

	// Getter method to get the coefficient
	public double getCoefficient() {
		return coefficient;
	}

	// Getter method to get the exponent
	public int getPower() {
		return power;
	}

	// Comparing two terms based on the exponent
	@Override
	public int compareTo(Term otherTerm) {
		// Return 1 if larger exponent than other Term
		// Return -1 if smaller exponent than other Term
		// Return 0 if equal exponent with other Term
		if (this.getPower() > otherTerm.getPower()) {
			return 1;
		} else if (this.getPower() < otherTerm.getPower()) {
			return -1;
		} else {
			return 0;
		}
	}

	// Get the Anti-Derivitive result for the current Term (First-Term)
	// If it's the first Term of the Anti-Derivitive result of the expression
	// and it's fraction, it would return (-x/x) instead of -(x/x)
	public String getAntiDF() {
		// If it's only an integer (no x, no exponent)
		// Anti-Derivitve would be either x | -x | []x
		if (this.getPower() == 0) {
			if (coefficient == 1) {
				return "x";
			} else if (coefficient == -1) {
				return "-x";
			}
			String coefficientStr = Integer.toString((int) coefficient);
			return coefficientStr + "x";
		} else {
			int newPower = power + 1;
			String newPowerStr = Integer.toString(newPower);
			// If it's not a fraction return Anti-Derivitive for current Term
			if (coefficient % newPower == 0) {
				// Get new coefficient by divide current coefficient with new exponent
				int newCoefficient = (int) coefficient / newPower;
				String newCoefficientStr = Integer.toString(newCoefficient);
				// Print out simply x^[] or -x^[] if coefficient = 1 or -1
				if (newCoefficient == 1) {
					return "x^" + newPowerStr;
				} else if (newCoefficient == -1) {
					return "-x^" + newPowerStr;
				}
				// Print out []x^[]
				return newCoefficientStr + "x^" + newPowerStr;
			} else {
				// If it's a fraction, use reducedFraction helper method to create the return
				// Anti-Derivitive
				// reducedFractionF (First-Term) - return (-x/x) instead of -(x/x)
				String newCoefficientStr = reducedFractionF((int) coefficient, newPower);
				return newCoefficientStr + "x^" + newPowerStr;
			}
		}
	}

	// Get the Anti-Derivitive result for the current Term (Other-Term)
	// If it's the first Term of the Anti-Derivitive result of the expression
	// and it's fraction, it would return - (x/x) instead of (-x/x)
	public String getAntiD() {
		// If it's only an integer (no x, no exponent)
		// Anti-Derivitve would be either x | -x | []x
		if (this.getPower() == 0) {
			if (coefficient == 1) {
				return "x";
			} else if (coefficient == -1) {
				return "-x";
			}
			String coefficientStr = Integer.toString((int) coefficient);
			return coefficientStr + "x";
		} else {
			int newPower = power + 1;
			String newPowerStr = Integer.toString(newPower);
			// If it's not a fraction return Anti-Derivitive for current Term
			if (coefficient % newPower == 0) {
				// Get new coefficient by divide current coefficient with new exponent
				int newCoefficient = (int) coefficient / newPower;
				String newCoefficientStr = Integer.toString(newCoefficient);
				// Print out simply x^[] or -x^[] if coefficient = 1 or -1
				if (newCoefficient == 1) {
					return "x^" + newPowerStr;
				} else if (newCoefficient == -1) {
					return "-x^" + newPowerStr;
				}
				// Print out []x^[]
				return newCoefficientStr + "x^" + newPowerStr;
			} else {
				// If it's a fraction, use reducedFraction helper method to create the return
				// Anti-Derivitive
				// reducedFraction (Other-Term) - return - (x/x) instead of (-x/x)
				String newCoefficientStr = reducedFraction((int) coefficient, newPower);
				return newCoefficientStr + "x^" + newPowerStr;
			}
		}
	}

	// This method is help with the calculation of the result of definite integrals
	public String getAntiDInt() {
		// Return 1x or -1x instead of just x or -x for calculation
		if (this.getPower() == 0) {
			if (coefficient == 1) {
				return "1x";
			} else if (coefficient == -1) {
				return "-1x";
			}
			String coefficientStr = Double.toString(coefficient);
			return coefficientStr + "x";
		} else {
			int newPower = power + 1;
			String newPowerStr = Integer.toString(newPower);
			double newCoefficient = coefficient / newPower;
			String newCoefficientStr = Double.toString(newCoefficient);
			// Return 1x or -1x instead of just x or -x for calculation
			if (newCoefficient == 1) {
				return "1x^" + newPowerStr;
			} else if (newCoefficient == -1) {
				return "-1x^" + newPowerStr;
			}
			return newCoefficientStr + "x^" + newPowerStr;
		}
	}

	// Method to get greatest common divisor for fraction
	private int gdc(int a, int b) {
		while (b != 0) {
			int temp = b;
			b = a % b;
			a = temp;
		}
		return a;
	}

	// Helper method to print fraction outputs if it's the largest exponent Term
	private String reducedFractionF(int numerator, int denominator) {
		// Reducing the fraction
		int gdc = gdc(numerator, denominator);
		numerator /= gdc;
		denominator /= gdc;

		// Check if the fraction is negative
		if (numerator < 0 || denominator < 0) {
			return "(-" + Math.abs(numerator) + "/" + Math.abs(denominator) + ")";
		} else {
			return "(" + numerator + "/" + denominator + ")";
		}
	}

	// Helper method to print fraction outputs if it's the other exponent Term
	private String reducedFraction(int numerator, int denominator) {
		// Reducing the fraction
		int gdc = gdc(numerator, denominator);
		numerator /= gdc;
		denominator /= gdc;

		// Check if the fraction is negative
		if (numerator < 0 || denominator < 0) {
			return "-(" + Math.abs(numerator) + "/" + Math.abs(denominator) + ")";
		} else {
			return "(" + numerator + "/" + denominator + ")";
		}
	}

	// Getter to get the current Term
	public String toString() {
		return term;
	}

	// Helper method for the Binary and Node class to add two Term together when
	// there is two Terms that have the same exponent
	public Term addTerm(Term otherTerm) {
		// Creating a new Term with the two coefficient added together
		int newCoefficient = (int) this.getCoefficient() + (int) otherTerm.getCoefficient();
		return new Term("" + newCoefficient + "x^" + this.getPower());
	}
}
