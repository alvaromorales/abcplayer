package player;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests the RationalNumber ADT
 * Testing strategy:
 *  - test initialization with default values
 *  - test the getValue method
 */
public class RationalNumberTest {

    /**
     * Tests that the default value of a RationalNumber is 1/2
     */
    @Test
    public void defaultValueTest() {
        RationalNumber expectedOutput = new RationalNumber();
        assertTrue(expectedOutput.getNumerator().equals(new Integer(1)) && expectedOutput.getDenominator().equals(new Integer(2)));
    }
    
    /**
     * Tests the getValue method
     */
    @Test
    public void getValueTest() {
        RationalNumber expectedOutput = new RationalNumber(3, 8);
        assertEquals(new Double(0.375), expectedOutput.getValue());
    }
    
}
