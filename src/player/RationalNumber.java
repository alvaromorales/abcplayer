package player;

/**
 * ADT used to represent the rational number durations of notes
 */
public class RationalNumber {
    private Integer numerator = 1;
    private Integer denominator = 2; //default values for notes
    
    /**
     * Creates a new RationalNumber object with default values
     */
    public RationalNumber() {
        
    }
    
    /**
     * Creates a new RationalNumber object
     * @param numerator the numerator
     * @param denominator the denominator
     */
    public RationalNumber(Integer numerator, Integer denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }
    
    /**
     * Gets the numerator of the RationalNumber
     * @return the numerator
     */
    public Integer getNumerator() {
        return this.numerator;
    }
    
    /**
     * Gets the denominator of the RationalNumber
     * @return the denominator
     */
    public Integer getDenominator() {
        return this.denominator;
    }
    
    /**
     * Gets the Double value of the RationalNumber
     * @return the evaluated value of the RationalNumber
     */
    public Double getValue() {
        return new Double(numerator.doubleValue() / denominator.doubleValue());
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
        
        return this.numerator.equals(other.numerator) && this.denominator.equals(other.denominator);
    }
}
