package player;

import org.junit.Test;

/**
 * Tests the Parser
 * Testing Strategy:
 *  - test parsing of header
 *  - test parsing of individual tokens into the appropriate NoteElements
 *  - test parsing of a song with multiple voices
 *  - test ParserExceptions
 */
public class ParserTest {

    /**
     * Tests the parsing of the header
     */
    public void headerTest() {
        
    }
    
    /**
     * Tests the parsing of a song with a complete header in an incorrect order
     */
    @Test(expected = ParserException.class)
    public void incorrectHeaderTest() {
        Lexer lexer = new Lexer("K:Me\nX:500\nT:1\nGGG");
        Parser parser = new Parser(lexer);
        parser.parse();
    }
    
    /**
     * Test the parsing of a song with an incomplete header
     */
    @Test(expected = ParserException.class)
    public void incompleteHeaderTest() {
        Lexer lexer = new Lexer("C:Me\nQ:500\nGGG");
        Parser parser = new Parser(lexer);
        parser.parse();
    }
    
}
