package player;

import org.junit.Test;
import player.AST.*;
import static org.junit.Assert.*;

/**
 * Tests the Parser
 * Testing Strategy:
 *  - test parsing of header
 *  - test parsing of individual tokens into the appropriate NoteElements
 *  - test parsing of a song with multiple voices
 *  - test ParserExceptions
 */
public class ParserTest {

    private final String correctHeader = "X:0\nT:t\nK:Cm"; // used for parser tests as the parser requires a correct header
    
    /**
     * Returns a Song with the header information from correctHeader
     * Helper function for parser tests
     * @return a Song with the header information from correctHeader
     */
    private Song makeSongFromCorrectHeader() {
        Song s = new Song();
        s.setIndex(0);
        s.setTitle("t");
        s.setKeySignature("Cm");
        return s;
    }
    
    /**
     * Tests the parsing of the header
     */
    //@Test
    public void headerTest() {
        Lexer lexer = new Lexer("X:8628\nT:Title\nC:Johann Sebastian Bach\nM:4/4\nL:1/16\nQ:280\nV:1\nV:2\nV:3\nK:C\n");
        Parser parser = new Parser(lexer);
        parser.parse();

        Song expected = new Song();
        expected.setIndex(8628);
        expected.setTitle("Title");
        expected.setComposer("Johann Sebastian Bach");
        expected.setMeter(new RationalNumber(4, 4));
        expected.setDefaultDuration(new RationalNumber(1, 16));
        expected.setTempo(280);
        expected.addVoice(new Voice("1"));
        expected.addVoice(new Voice("2"));
        expected.addVoice(new Voice("3"));
        expected.setKeySignature("C");
        
        assertEquals(expected,parser.getSong());
    }
    
    /**
     * Tests the parsing of a song with a complete header in an incorrect order
     */
    //@Test(expected = ParserException.class)
    public void incorrectHeaderTest() {
        Lexer lexer = new Lexer("K:Me\nX:500\nT:1\nGGG");
        Parser parser = new Parser(lexer);
        parser.parse();
    }
    
    /**
     * Test the parsing of a song with an incomplete header
     */
    //@Test(expected = ParserException.class)
    public void incompleteHeaderTest() {
        Lexer lexer = new Lexer("C:Me\nQ:500\nGGG");
        Parser parser = new Parser(lexer);
        parser.parse();
    }
    
    /**
     * Test the parsing of a KEYNOTE token
     */
    @Test
    public void keynoteTokenTest() {
        Lexer lexer = new Lexer(correctHeader + "^G,,8");
        Parser parser = new Parser(lexer);
        parser.parse();
        
        Song expected = makeSongFromCorrectHeader();
        expected.add(new SingleNote('G',new RationalNumber(8, 1),-2,1));
        assertEquals(expected, parser.getSong());
    }
    
    
    
}
