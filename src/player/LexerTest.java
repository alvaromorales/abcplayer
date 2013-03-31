package player;

import static org.junit.Assert.*;


import org.junit.Test;

public class LexerTest {

    @Test
    public void test1() {
        Lexer.Lexer("ABC1/4,,DDG");
        assertEquals(1,1);
    }

}
