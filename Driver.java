import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Driver {
    public static void main(String[] args) {
        try {
            // Lab 1 functionality tests

            // Test default constructor (zero polynomial)
            Polynomial pZero = new Polynomial();
            System.out.println("pZero evaluated at x=1: " + pZero.evaluate(1)); // Expect 0.0

            // Test constructor with coefficients and exponents
            double[] coeff1 = {6, -2, 5}; // Represents 6 - 2x + 5x^3
            int[] exp1 = {0, 1, 3};
            Polynomial p1 = new Polynomial(coeff1, exp1);

            double[] coeff2 = {3, -3, 7}; // Represents 3 - 3x + 7x^2
            int[] exp2 = {0, 1, 2};
            Polynomial p2 = new Polynomial(coeff2, exp2);

            // Evaluate polynomials
            System.out.println("p1 evaluated at x=1: " + p1.evaluate(1)); // Expect 9.0
            System.out.println("p2 evaluated at x=1: " + p2.evaluate(1)); // Expect 7.0

            // Test addition
            Polynomial sum = p1.add(p2); // Expect sum to represent 9 - 5x + 7x^2 + 5x^3
            System.out.println("Sum evaluated at x=1: " + sum.evaluate(1)); // Expect 16.0

            // Test multiplication
            Polynomial product = p1.multiply(p2);
            System.out.println("Product evaluated at x=1: " + product.evaluate(1)); // Test case for product

            // Test if polynomial has a root
            System.out.println("p1 has root at x=1: " + p1.hasRoot(1)); // Expect false

            // Lab 2 functionality tests

            // Test constructor from a file
            File polyFile = new File("polynomial.txt");
            Polynomial pFile = new Polynomial(polyFile);
            System.out.println("File-based polynomial evaluated at x=1: " + pFile.evaluate(1));

            // Test saving polynomial to file
            sum.saveToFile("sumPolynomial.txt");
            System.out.println("Sum polynomial saved to sumPolynomial.txt");

            // Check saved file by loading it back and evaluating
            Polynomial pFromFile = new Polynomial(new File("sumPolynomial.txt"));
            System.out.println("Loaded polynomial from file, evaluated at x=1: " + pFromFile.evaluate(1));

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO error: " + e.getMessage());
        }
    }
}
