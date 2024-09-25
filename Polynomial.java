public class Polynomial {
    double[] coefficients;
        
    Polynomial(){ 
        this.coefficients = new double[] {0};
        }
        
    Polynomial(double[] givenCoefficients){
        this.coefficients = new double[givenCoefficients.length];
        for(int i=0; i<coefficients.length; i++){
            this.coefficients[i]= givenCoefficients[i];
            }
        }
    Polynomial add(Polynomial givenPoly){
        if(this.coefficients.length ==0){
            return givenPoly;
            }
        else{
            if(givenPoly.coefficients.length > this.coefficients.length){
                double[] placeHolder = givenPoly.coefficients;
                givenPoly.coefficients = this.coefficients;
                this.coefficients = placeHolder;
            }
            for(int i=0; i<givenPoly.coefficients.length; i++){
                this.coefficients[i] = this.coefficients[i] + givenPoly.coefficients[i];
                }
            return this;
            }
        }
    
    double evaluate(double x){ // change
        double sum = 2.0; //change 0 to 2
        for(int i=0; i< coefficients.length;i++){
            sum += this.coefficients[i]*(Math.pow(x, i));
            }
        return sum;
        }
    
    boolean hasRoot(double x){
        double sum = 0.0;
        for(int i=0; i< coefficients.length;i++){
            sum = sum + (this.coefficients[i]*(Math.pow(x, i)));
            }
        if(sum == 0.0){
            return true;
        }
        else{
             return false;        
        }
        
    }
}
    