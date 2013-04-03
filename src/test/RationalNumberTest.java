package test;

import static org.junit.Assert.*;
import org.junit.Test;
import player.RationalNumber;

/**
 * Tests the RationalNumber ADT
 * Testing strategy:
 *  - test illegal inputs
 *  - test the getValue method
 *  - test the addition of two numbers
 *  - test the chained addition of numbers
 *  - test the multiplication by a constant
 *  - test the multiplication of two RationalNumbers
 *  - test the lcm and gcd methods
 */
public class RationalNumberTest {
    
    /**
     * Tests creating a RationalNumber with denominator == 0
     */
    @Test(expected = RuntimeException.class)
    public void denominatorZeroTest() {
        new RationalNumber(1, 0);
    }
    
    /**
     * Tests creating a negative RationalNumber with a negative denominator
     */
    @Test(expected = RuntimeException.class)
    public void negativeDenominatorTest() {
        new RationalNumber(1, -1);
    }
    
    /**
     * Tests creating a negative RationalNumber with a negative numerator
     */
    @Test(expected = RuntimeException.class)
    public void negativeNumeratorTest() {
        new RationalNumber(-1, 1);
    }
    
    /**
     * Tests creating a positive RationalNumber with negative inputs
     */
    @Test
    public void positiveNumberTest() {
        RationalNumber expected = new RationalNumber(1, 1);
        assertEquals(expected, new RationalNumber(-1, -1));
    }
    
    /**
     * Tests the getValue method
     */
    @Test
    public void getValueTest() {
        RationalNumber expectedOutput = new RationalNumber(3, 8);
        assertEquals(new Double(0.375), new Double(expectedOutput.getValue()));
    }
    
    /**
     * Tests the addition of two numbers
     */
    @Test
    public void addTest() {
        RationalNumber a = new RationalNumber(3, 8);
        RationalNumber b = new RationalNumber(2, 4);
        
        RationalNumber expected = new RationalNumber(7, 8);
        assertEquals(expected, a.add(b));
    }
    
    /**
     * Tests the chained addition of numbers
     */
    @Test
    public void chainedAddTest() {
        RationalNumber a = new RationalNumber(3, 8);
        RationalNumber b = new RationalNumber(2, 4);
        RationalNumber c = new RationalNumber(5, 16);
        RationalNumber d = new RationalNumber(1, 2);
        
        RationalNumber expected = new RationalNumber(27, 16);
        assertEquals(expected, a.add(b).add(c).add(d));
    }
    
    /**
     * Tests the multiplication of a RationalNumber by a constant
     */
    @Test
    public void mulCTest() {
        RationalNumber a = new RationalNumber(3, 8);
        RationalNumber expected = new RationalNumber(6, 8);

        assertEquals(expected, a.mulC(2));
    }
    
    /**
     * Tests the multiplication of two RationalNumbers
     */
    @Test
    public void mulTest() {
        RationalNumber a = new RationalNumber(3, 8);
        RationalNumber b = new RationalNumber(2, 4);
        
        RationalNumber expected = new RationalNumber(6, 32);
        assertEquals(expected, a.mul(b));
    }
    
    /**
     * Tests the gcd method
     */
    @Test
    public void basicGcdTest() {
        assertEquals(2, RationalNumber.gcd(294, 78292));
    }
    
    
    /**
     * Tests the lcm method
     */
    @Test
    public void basicLcmTest() {
        assertEquals(0, RationalNumber.lcm(0, 56));
    }
    
    /**
     * Tests the lcm method with more complex inputs
     */
    @Test
    public void complexLcmTest() {
        assertEquals(404865780, RationalNumber.lcm(9834, 82340));
    }
  
}
