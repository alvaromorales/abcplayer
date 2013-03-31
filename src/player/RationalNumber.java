package player;

/**
 * ADT used to represent the positive rational number durations of notes
 */
public class RationalNumber {
    private int numerator;
    private int denominator;
       
    /**
     * Creates a new RationalNumber object.
     * Represents a positive rational number.
     * @param numerator the numerator. Must be >= 0
     * @param denominator the denominator. Must be > 0
     * @throws RuntimeException if denominator is 0 or the inputs represent a negative number
     */
    public RationalNumber(int numerator, int denominator) {        
        if (denominator == 0){
            throw new RuntimeException("Error creating a rational number with denominator of 0");
        } 
        
        if (numerator < 0 && denominator < 0) {
            numerator *= -1;
            denominator *= -1;
        } else if (numerator < 0 || denominator < 0){
            throw new RuntimeException("A RationalNumber duration cannot be negative");
        }
        
        this.numerator = numerator;
        this.denominator = denominator;
    }
    
    /**
     * Gets the numerator of the RationalNumber
     * @return the numerator
     */
    public int getNumerator() {
        return this.numerator;
    }
    
    /**
     * Gets the denominator of the RationalNumber
     * @return the denominator
     */
    public int getDenominator() {
        return this.denominator;
    }
    
    /**
     * Gets the double value of the RationalNumber
     * @return the evaluated value of the RationalNumber
     */
    public double getValue() {
        return (double)numerator/denominator;
    }
    
    /**
     * Creates a new RationalNumber that is the result from the addition of two RationalNumbers
     * @param other the other RationalNumber to add
     * @return a new RationalNumber, the result of the addition of two RationalNumbers
     */
    public RationalNumber add(RationalNumber other) {
        if (this.denominator == other.denominator) {
            return new RationalNumber(this.numerator + other.numerator, this.denominator);
        } else {
            int lcm = lcm(this.denominator,other.denominator);
            
            int firstNumerator = this.numerator*lcm/this.denominator;
            int secondNumerator = other.numerator*lcm/other.denominator;
            return new RationalNumber(firstNumerator + secondNumerator, lcm);
        }
    }
    
    /**
     * Creates a new RationalNumber that is the result from the multiplication of a RationalNumber and a constant
     * @param c the constant
     * @return a new RationalNumber, the result of the multiplication by a constant
     */
    public RationalNumber mulC(int c) {
        return new RationalNumber(this.numerator*c, this.denominator);
    }
    
    /**
     * Creates a new RationalNumber that is the result from multiplying two RationalNumbers
     * @param other the other RationalNumber to multiply
     * @return a new RationalNumber, the result of the multiplication of two RationalNumbers
     */
    public RationalNumber mul(RationalNumber other) {
        return new RationalNumber(this.numerator * other.numerator, this.denominator * other.denominator);
    }
    
    /**
     * Gets the String representation of a RationalNumber
     * @return a string representation of a RationalNumber
     */
    @Override
    public String toString() {
        return "" + numerator + "/" + denominator;
    }
    
    /**
     * Checks if a RationalNumber is equal to another RationalNumber
     * @param o the Object to compare to
     * @return true if equal, else false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) { // quick check
            return true;
        }

        if (o == null || !(o instanceof RationalNumber)) {
            return false;
        }

        RationalNumber other = (RationalNumber)o;
        
        return this.numerator == other.numerator && this.denominator == other.denominator;
    }
    
    /**
     * Computes the gcd (greatest common divisor) of two numbers.
     * @param a the first number. Must be >= 0
     * @param b the second number. Must be >= 0
     * @return the gcd of two numbers
     */
    public static int gcd(int a, int b) {
        if (a<b) {
            int temp = a;
            a = b;
            b = temp;
        }
        
        if (b == 0) {
            return a;
        }
        
        return gcd(b,a % b);
    }
    
    /**
     * Computes the lcm (lowest common multiple) of two numbers
     * @param a the first number. Must be > 0
     * @param b the second number. Must be > 0
     * @return the lcm of two numbers
     */
    public static int lcm(int a, int b) {
        return (a*b)/gcd(a,b);
    }
}
