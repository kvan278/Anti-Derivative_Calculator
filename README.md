# Anti-Derivative Calculator

This Java project implements an anti-derivative calculator that can compute both indefinite and definite integrals from mathematical expressions. It reads equations from a file, processes each line to identify whether the integral is indefinite or definite, and outputs the corresponding anti-derivative.

## Features
- **Indefinite Anti-Derivatives**: For equations without specified bounds, the program calculates the indefinite integral and displays the result in the form of anti-derivatives with the constant \(C\).
- **Definite Anti-Derivatives**: For equations with specified bounds, it computes the definite integral and outputs the result formatted to three decimal places.
- **Binary Search Tree**: Utilizes a binary search tree to store and organize terms of the polynomial expressions for efficient processing.

## How It Works
1. **Input**: The program prompts the user to enter the path of a text file containing mathematical expressions. Each line represents an equation that will be processed.
2. **Processing**:
   - If an expression starts with a pipe (`|`), it is treated as an indefinite integral.
   - For expressions with specified bounds, the program extracts the lower and upper limits for the definite integral.
3. **Output**: The program prints the calculated anti-derivatives or definite integral results directly to the console.

## Usage
To run the project:
1. Compile the Java files.
2. Execute the `Main` class.
3. Input the path to the text file containing the equations when prompted.

### Example Input
| 2x^2 + 3x - 5 0 | 2 4x^3 - 3x^2 + 2

### Example Output
(2/3)x^3 + (3/2)x^2 - 5x + C , 0|2 = 25.000


## Implementation Details
- The project uses a stack to handle the terms of the expression, ensuring they are processed in the correct order.
- Each term is represented as an object of the `Term` class, which holds the coefficient and exponent, enabling flexible calculations.
- The use of a binary search tree allows for efficient insertion and traversal of polynomial terms.

## Authors
- [Khanh Van](https://www.github.com/kvan278)
