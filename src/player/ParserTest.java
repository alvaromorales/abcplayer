package player;

import java.util.ArrayList;
import org.junit.Test;
import player.AST.*;
import static org.junit.Assert.*;

/**
 * Tests the Parser
 * Testing Strategy:
 *  - test parsing of header
 *  - test parsing of individual tokens into the appropriate NoteElements
 *  - test parsing of a song with multiple voices
 *  - test repeats
 *  - test ParserExceptions
 */
public class ParserTest {

    private final String correctHeader = "X:0\nT:t\nK:Cm\n"; // used for parser tests as the parser requires a correct header
    
    
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
    @Test
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
    
    /**
     * Test the parsing of a SingleNote
     */
    @Test
    public void parseSingleNoteTest() {
        Lexer lexer = new Lexer(correctHeader + "^G,,8");
        Parser parser = new Parser(lexer);
        parser.parse();
        
        Song expected = makeSongFromCorrectHeader();
        expected.add(new SingleNote('G',new RationalNumber(8, 1),-2,1));
        assertEquals(expected, parser.getSong());
    }
    
    /**
     * Test the parsing of a Rest
     */
    @Test
    public void parseRestTest() {
        Lexer lexer = new Lexer(correctHeader + "z/");
        Parser parser = new Parser(lexer);
        parser.parse();
        
        Song expected = makeSongFromCorrectHeader();
        expected.add(new Rest(new RationalNumber(1, 2)));
        assertEquals(expected, parser.getSong());
    }
    
    /**
     * Test the parsing of a Chord
     */
    @Test
    public void parseChordTest() {
        Lexer lexer = new Lexer(correctHeader + "[E16G16]");
        Parser parser = new Parser(lexer);
        parser.parse();
        
        Song expected = makeSongFromCorrectHeader();
        ArrayList<NoteElement> notes = new ArrayList<NoteElement>();
        notes.add(new SingleNote('E',new RationalNumber(16, 1),0,0));
        notes.add(new SingleNote('G',new RationalNumber(16, 1),0,0));

        expected.add(new Chord(new RationalNumber(16, 1),notes));
        assertEquals(expected, parser.getSong());
    }
    
    /**
     * Test the parsing of a Duplet
     */
    @Test
    public void parseDupletTest() {
        Lexer lexer = new Lexer(correctHeader + "(2GG");
        Parser parser = new Parser(lexer);
        parser.parse();
        
        Song expected = makeSongFromCorrectHeader();
        
        SingleNote dupletNote = new SingleNote('G', new RationalNumber(3, 2), 0, 0);       
        expected.add(new Duplet(dupletNote,dupletNote));

        assertEquals(expected, parser.getSong());
    }
    
    /**
     * Test the parsing of a malformed Duplet
     */
    @Test(expected = ParserException.class)
    public void parseIncorrectDuplet() {
        Lexer lexer = new Lexer(correctHeader + "(2G");
        Parser parser = new Parser(lexer);
        parser.parse();
    }
    
    /**
     * Tests the parsing of a Triplet
     */
    @Test
    public void parseTripletTest() {
        Lexer lexer = new Lexer(correctHeader + "(3GGG");
        Parser parser = new Parser(lexer);
        parser.parse();
        
        Song expected = makeSongFromCorrectHeader();
        
        SingleNote tripletNote = new SingleNote('G', new RationalNumber(2, 3), 0, 0);       
        expected.add(new Triplet(tripletNote,tripletNote,tripletNote));

        assertEquals(expected, parser.getSong());
    }
    
    /**
     * Test the parsing of a malformed Triplet
     */
    @Test(expected = ParserException.class)
    public void parseIncorrectTriplet() {
        Lexer lexer = new Lexer(correctHeader + "(3GG");
        Parser parser = new Parser(lexer);
        parser.parse();
    }
    
    /**
     * Tests the parsing of a Quadruplet
     */
    @Test
    public void parseQuadrupletTest() {
        Lexer lexer = new Lexer(correctHeader + "(4GGGG");
        Parser parser = new Parser(lexer);
        parser.parse();
        
        Song expected = makeSongFromCorrectHeader();
        
        SingleNote quadNote = new SingleNote('G', new RationalNumber(3, 4), 0, 0);       
        expected.add(new Quadruplet(quadNote,quadNote,quadNote,quadNote));

        assertEquals(expected, parser.getSong());
    }
    
    /**
     * Test the parsing of a malformed Quadruplet
     */
    @Test(expected = ParserException.class)
    public void parseIncorrectQuadruplet() {
        Lexer lexer = new Lexer(correctHeader + "(4GGG");
        Parser parser = new Parser(lexer);
        parser.parse();
    }
    
    /**
     * Test the parsing of a song with repeats
     */
    //@Test
    //TODO should work when Lexer works
    public void parseRepeatsTest() {
        Lexer lexer = new Lexer(correctHeader + "|:G:|");
        Parser parser = new Parser(lexer);
        parser.parse();
        
        Song expected = makeSongFromCorrectHeader();
        SingleNote repeated = new SingleNote('G', new RationalNumber(1, 1), 0, 0);
        expected.add(repeated);
        expected.add(repeated);
        assertEquals(expected, parser.getSong());
    }
    
    
    
}
