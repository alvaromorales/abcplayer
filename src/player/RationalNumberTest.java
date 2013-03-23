package player;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests the RationalNumber ADT
 * Testing strategy:
 *  - test the getValue method
 *  - test the addition of two numbers
 *  - test the multiplication by a constant
 *  - test the multiplication of two RationalNumbers
 */
public class RationalNumberTest {
    
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
  
}
