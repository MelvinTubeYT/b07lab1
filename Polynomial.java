import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;



public class Polynomial {
    double[] coefficients;
    int[] exponents;

    Polynomial() {
        this.coefficients = new double[]{0};
        this.exponents = new int[]{0};
    }

    Polynomial(double[] givenCoefficients, int[] givenExponents) {
        this.coefficients = givenCoefficients;
        this.exponents = givenExponents;
}
    
    Polynomial(File file) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String poly = reader.readLine(); //poly now contains the polynomial
        String[] arrPoly = poly.split("+");
        int max = 0;
        for (int i = 0; i < arrPoly.length; i++) {
            String[] testPoly = arrPoly[i].split("-");
            if (testPoly.length > max) {
                max = testPoly.length;
            }
        }
        String[][] element = new String[arrPoly.length][max];
        for (int i = 0; i < element.length; i++) {
            for (int j = 0; j < element[i].length; j++) {
                element[i] = arrPoly[i].split("\\-");
            }
        }
        int count = 0;
        //count number of elements excluding blank space
        for (int i = 0; i < element.length; i++) {
            for (int j = 0; j < element[i].length; j++) {
                if(element[i][j] != ""){
                    count++;
                }
            }
        }
        double[] finalCoefficients = new double[count];
        int[] finalExponents = new int[count];
        int counter = 0; //counter for indexing the finalcoef and finalexp arrays
        //put elements into final arrays
        for (int i = 0; i < element.length; i++) {
            for (int j = 0; j < element[i].length; j++) {
                if(element[i][j] != ""){ //to prevent adding blank space to array
                    String[] placeHolder = element[i][j].split("x");
                    if(j==0){ //first element is always postive, rest are negative
                        if(placeHolder.length == 1){
                            finalCoefficients[counter] = Double.parseDouble(placeHolder[0]);
                            finalExponents[counter] = 0;
                            counter++;
                        }
                        else{
                            finalCoefficients[counter] = Double.parseDouble(placeHolder[0]);
                            finalExponents[counter] = Integer.parseInt(placeHolder[1]);
                            counter++;
                        }

                    }
                    else{ //negative elements
                        placeHolder[0] = placeHolder[0].strip();
                        if(placeHolder.length == 1){
                            finalCoefficients[counter] = Double.parseDouble(placeHolder[0]);
                            finalCoefficients[counter] = -finalCoefficients[counter]; //make it negative
                            finalExponents[counter] = 0;
                            counter++;
                        }
                        else{

                            finalCoefficients[counter] = Double.parseDouble(placeHolder[0]);
                            finalCoefficients[counter] = -finalCoefficients[counter]; //make it negative
                            finalExponents[counter] = Integer.parseInt(placeHolder[1]);
                            counter++;
                        }
                    }
                }
            }
        }
        this.coefficients = finalCoefficients;
        this.exponents = finalExponents;
    }

    void saveToFile(String fileName){ // turning the double and int into a string to the file
        //use String.valueOf()
        String poly = "";
        for(int i=0; i<this.exponents.length;i++){
            if(i==0){ //first element => no "+" symbol
                if(this.exponents[i]==0){ //constant, i.e, no variable
                    poly += this.coefficients[i]; //a constant => no addition symbol, no variable x
                }
                else{ //not a constant => has x varaible
                    poly += this.coefficients[i]+"x"+this.exponents[i];
                }
            }
            else{ //there is "+" or "-" symbol
                if(this.coefficients[i] < 0) {//negative coeff then can add directly
                    if(this.exponents[i]==0){ //constant => no variable
                        poly += this.coefficients[i];
                    }
                    else{ //not constant
                        poly += this.coefficients[i]+"x"+this.exponents[i];
                    }
                }
                else{ //positive => need "+" sign
                    if(this.exponents[i]==0){ //positive coeff
                        poly += "+" + this.coefficients[i];
                    }
                    else{
                        poly += "+" + this.coefficients[i]+"x"+this.exponents[i];
                    }
                }


            }
        }
        try{
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(poly);
            myWriter.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public Polynomial add(Polynomial givenPoly) {
        if (this.coefficients.length == 0) {
            return givenPoly;
        }

        int counter = this.exponents.length + givenPoly.exponents.length;

        // Adjust for duplicate exponents
        for (int i = 0; i < this.exponents.length; i++) {
            for (int j = 0; j < givenPoly.exponents.length; j++) {
                if (this.exponents[i] == givenPoly.exponents[j]) {
                    counter--; // Reduce counter for matching exponents
                    break;
                }
            }
        }

        double[] newCoefficients = new double[counter];
        int[] newExponents = new int[counter];
        int newCounter = 0;
        int j = 0; // Pointer for givenPoly

        for (int i = 0; i < this.exponents.length; i++) {
            // Merge terms from both polynomials
            while (j < givenPoly.exponents.length) {
                if (this.exponents[i] == givenPoly.exponents[j]) {
                    // Matching exponents, add the coefficients
                    newCoefficients[newCounter] = this.coefficients[i] + givenPoly.coefficients[j];
                    newExponents[newCounter] = this.exponents[i];
                    newCounter++;
                    j++; // Move forward in givenPoly
                    break; // Move to the next term in this.exponents
                } else if (this.exponents[i] > givenPoly.exponents[j]) {
                    // Add the term from givenPoly (its exponent is smaller)
                    newCoefficients[newCounter] = givenPoly.coefficients[j];
                    newExponents[newCounter] = givenPoly.exponents[j];
                    newCounter++;
                    j++; // Move forward in givenPoly
                } else {
                    // Add the term from this (its exponent is larger)
                    newCoefficients[newCounter] = this.coefficients[i];
                    newExponents[newCounter] = this.exponents[i];
                    newCounter++;
                    break; // Move to the next term in this.exponents
                }
            }

            // If givenPoly is exhausted, add remaining terms from this polynomial
            if (j >= givenPoly.exponents.length) {
                newCoefficients[newCounter] = this.coefficients[i];
                newExponents[newCounter] = this.exponents[i];
                newCounter++;
            }
        }

        // Add any remaining terms from givenPoly
        while (j < givenPoly.exponents.length) {
            newCoefficients[newCounter] = givenPoly.coefficients[j];
            newExponents[newCounter] = givenPoly.exponents[j];
            newCounter++;
            j++;
        }

        // Return the new polynomial
        Polynomial newPolynomial = new Polynomial(newCoefficients, newExponents);
        return newPolynomial;
    }


    double evaluate(double x){ 
        double sum = 0.0; 
        for(int i=0; i< this.exponents.length;i++){
            sum += this.coefficients[i]*(Math.pow(x, this.exponents[i]));
            }
        return sum;
        }
    
    boolean hasRoot(double x){
        double sum = 0.0;
        for(int i=0; i< this.coefficients.length;i++){
            sum = sum + (this.coefficients[i]*(Math.pow(x, this.exponents[i])));
            }
        if(sum == 0.0){
            return true;
        }
        else{
             return false;        
        }
        
    }

    public Polynomial multiply(Polynomial other) {
        int newSize = this.coefficients.length * other.coefficients.length;
        double[] newCoefficients = new double[newSize];
        int[] newExponents = new int[newSize];

        int index = 0;
        for (int i = 0; i < this.coefficients.length; i++) {
            for (int j = 0; j < other.coefficients.length; j++) {
                newCoefficients[index] = this.coefficients[i] * other.coefficients[j];
                newExponents[index] = this.exponents[i] + other.exponents[j];
                index++;
            }
        }
        return new Polynomial(newCoefficients, newExponents);
    }
}
    