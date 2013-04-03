package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Test;
import ast.*;
import player.*;

/**
 * Tests the Parser class
 * Integration test with the Lexer
 * These are the same tests as in ParserTest.java, but using the Lexer for input to the Parser
 * 
 * Testing Strategy:
 *  - test parsing of header
 *  - test parsing of individual tokens into the appropriate NoteElements
 *  - test splitTokensByVoice
 *  - test repeats
 *  - test ParserExceptions
 */
public class ParserIntegrationTest {

    // Correct header for the lexer to lex
    private final String correctHeader = "X:8628\nT:Title\nK:C\n";
    
    /**
     * Tests the parsing of the header
     */
    @Test
    public void headerTest() {
        //Header: "X:8628\nT:Title\nC:Johann Sebastian Bach\nM:4/4\nL:1/16\nQ:280\nV:1\nV:2\nV:3\nK:C\n"
        Lexer lexer = new Lexer("X:8628\nT:Title\nC:Johann Sebastian Bach\nM:4/4\nL:1/16\nQ:280\nV:1\nV:2\nV:3\nK:C\n");
        
        Parser parser = new Parser();
        parser.parseHeader(lexer.lexHead());

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
        Lexer lexer = new Lexer("K:C\nX:8628\nT:Title\n");
        Parser parser = new Parser();
        parser.parseHeader(lexer.lexHead());
    }
    
    
    /**
     * Test the parsing of a song with an incomplete header
     */
    @Test(expected = ParserException.class)
    public void incompleteHeaderTest() {
        Lexer lexer = new Lexer("C:Me\nK:C\nQ:500\n");
        Parser parser = new Parser();
        parser.parseHeader(lexer.lexHead());
    }
    
    /**
     * Test the parsing of a SingleNote
     */
    @Test
    public void parseSingleNoteTest() {
        Lexer lexer = new Lexer(correctHeader + "^G,,8");
        Parser parser = new Parser();
        parser.parseVoice(lexer.lexBody());

        Song expected = new Song();
        expected.add(new SingleNote('G',new RationalNumber(8, 1),-2,1));
        
        assertEquals(expected, parser.getSong());
    }
    
    /**
     * Test the parsing of a Rest
     */
    @Test
    public void parseRestTest() {
        Lexer lexer = new Lexer(correctHeader + "z/");
                
        Parser parser = new Parser();
        parser.parseVoice(lexer.lexBody());
        
        Song expected = new Song();
        expected.add(new Rest(new RationalNumber(1, 2)));
        assertEquals(expected, parser.getSong());
    }
    
    /**
     * Test the parsing of a Chord
     */
    @Test
    public void parseChordTest() {
        Lexer lexer = new Lexer(correctHeader + "[E16G16]");
        
        Parser parser = new Parser();
        parser.parseVoice(lexer.lexBody());
        
        Song expected = new Song();
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
        Parser parser = new Parser();
        parser.parseVoice(lexer.lexBody());
        
        Song expected = new Song();
        
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
        Parser parser = new Parser();
        parser.parseVoice(lexer.lexBody());
    }

    /**
     * Tests the parsing of a Triplet
     */
    @Test
    public void parseTripletTest() {
        Lexer lexer = new Lexer(correctHeader + "(3GGG");
        Parser parser = new Parser();
        parser.parseVoice(lexer.lexBody());

        Song expected = new Song();

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
        Parser parser = new Parser();
        parser.parseVoice(lexer.lexBody());
    }
    
    /**
     * Tests the parsing of a Quadruplet
     */
    @Test
    public void parseQuadrupletTest() {
        Lexer lexer = new Lexer(correctHeader + "(4GGGG");
        
        Parser parser = new Parser();
        parser.parseVoice(lexer.lexBody());
        
        Song expected = new Song();
        
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
        Parser parser = new Parser();
        parser.parseVoice(lexer.lexBody());
    }
    
    /**
     * Test the parsing of a song with repeats
     */
    @Test
    public void parseRepeatsTest() {
        Lexer lexer = new Lexer(correctHeader + "|:G:|");
                
        Parser parser = new Parser();
        parser.parseVoice(lexer.lexBody());
        
        Song expected = new Song();
        SingleNote repeated = new SingleNote('G', new RationalNumber(1, 1), 0, 0);
        expected.add(repeated);
        expected.add(repeated);
        assertEquals(expected, parser.getSong());
    }
    
    /**
     * Test the parsing of a song with numbered repeats
     */
    @Test
    public void parseNumberedRepeatsTest() {
        Lexer lexer = new Lexer(correctHeader + "|:A[1G:|[2C");
        // should be parsed as AGAC
        
        Parser parser = new Parser();
        parser.parseVoice(lexer.lexBody());
        
        Song expected = new Song();
        SingleNote parsedG = new SingleNote('G', new RationalNumber(1, 1), 0, 0);
        SingleNote parsedA = new SingleNote('A', new RationalNumber(1, 1), 0, 0);
        SingleNote parsedC = new SingleNote('C', new RationalNumber(1, 1), 0, 0);

        expected.add(parsedA);
        expected.add(parsedG);
        expected.add(parsedA);
        expected.add(parsedC);

        assertEquals(expected, parser.getSong());
    }
    
    /**
     * Tests the parsing of an expression with unbalanced repeats
     */
    @Test(expected = ParserException.class)
    public void balancedRepeatTest() {
        Lexer lexer = new Lexer(correctHeader + "|:GGG||");
        Parser parser = new Parser();
        parser.parseVoice(lexer.lexBody());
    }
    
    /**
     * Tests splitTokensByVoice
     */
    public void splitVoicesTest() {
        Lexer lexer = new Lexer(correctHeader + "V:2\nB\nV:1\nA\nV:2\nB\nV:1\nAA\nV:2\nB\nV:1\nA");
                
        Token voice1 = new Token(Token.Type.VOICE);
        voice1.setValue("1");
        
        Token voice2 = new Token(Token.Type.VOICE);
        voice2.setValue("2");
        
        Token noteA = new Token(Token.Type.KEYNOTE);
        noteA.setAccidental(Integer.MAX_VALUE);
        noteA.setDuration(new RationalNumber(1, 1));
        noteA.setOctave(0);
        noteA.setValue("A");
        
        Token noteB = new Token(Token.Type.KEYNOTE);
        noteB.setAccidental(Integer.MAX_VALUE);
        noteB.setDuration(new RationalNumber(1, 1));
        noteB.setOctave(0);
        noteB.setValue("B");
        
        // Parse: 
        //  V2 -> B
        //  V1 -> A
        //  V2 -> B
        //  V1 -> A A
        //  V2 -> B
        //  V1 -> A
        // Into:
        // V1 -> A A A A
        // V2 -> B B B
       
        HashMap<String, ArrayList<Token>> expected = new HashMap<String, ArrayList<Token>>();
        
        ArrayList<Token> voice1Tokens = new ArrayList<Token>(0);
        voice1Tokens.add(noteA);
        voice1Tokens.add(noteA);
        voice1Tokens.add(noteA);
        voice1Tokens.add(noteA);

        ArrayList<Token> voice2Tokens = new ArrayList<Token>(0);
        voice2Tokens.add(noteB);
        voice2Tokens.add(noteB);
        voice2Tokens.add(noteB);

        expected.put("1", voice1Tokens);
        expected.put("2", voice2Tokens);
        
        Parser parser = new Parser();
        
        assertEquals(expected, parser.splitTokensByVoice(lexer.lexBody()));
    }
    
}
