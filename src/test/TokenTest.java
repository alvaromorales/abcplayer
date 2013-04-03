package test;

import static org.junit.Assert.*;

import org.junit.Test;
import player.Token;
import player.RationalNumber;

/**
 * Tests the Token ADT 
 * Testing strategy:
 *  - test initialization with some type
 *  - test the getIntValue method
 *  - test the getRationalValue method
 */
public class TokenTest {
    
    /**
     * Tests the initialization method
     */
    @Test
    public void initTest() {
        Token test = new Token(Token.Type.BAR);
        assertEquals(test.getType(), Token.Type.BAR);
    }
    
    
    /**
     * Tests getIntValue
     */
    @Test
    public void getIntValueTest(){
        Token test = new Token(Token.Type.TEMPO);
        test.setValue("300");
        assertEquals(test.getIntValue(), 300);
    }
    
    /**
     * Tests getRationalNumber
     */
    @Test
    public void getRationalNumberTest(){
        Token test = new Token(Token.Type.LENGTH);
        test.setValue("1/4");
        RationalNumber expected  = new RationalNumber(1,4);
        assertEquals(test.getRationalValue(), expected);
    }
}
