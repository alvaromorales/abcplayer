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
    
    private ArrayList<Token> makeTokensFromCorrectHeader() {
        //Header: "X:0\nT:t\nK:Cm\n"
        
        Token first = new Token(Token.Type.INDEX);
        first.setValue("0");
        
        Token second = new Token(Token.Type.TITLE);
        second.setValue("t");

        Token third = new Token(Token.Type.KEY);
        third.setValue("Cm");
        
        ArrayList<Token> tokens = new ArrayList<Token>(0);
        tokens.add(first);
        tokens.add(second);
        tokens.add(third);
        
        return tokens;
    }
    
    /**
     * Returns a Song with the header information from correctHeader
     * Helper function for parser tests
     * @return a Song with the header information from correctHeader
     */
    private Song makeSongFromCorrectHeader() {
        //Header: "X:0\nT:t\nK:Cm\n"
        
        Song s = new Song();
        s.setIndex(0);
        s.setTitle("t");
        s.setKeySignature("Cm");
        return s;
    }
    
//    /**
//     * Tests the parsing of the header
//     */
//    @Test
//    public void headerTest() {
//        Lexer lexer = new Lexer("X:8628\nT:Title\nC:Johann Sebastian Bach\nM:4/4\nL:1/16\nQ:280\nV:1\nV:2\nV:3\nK:C\n");
//        Parser parser = new Parser(lexer);
//        parser.parse();
//
//        Song expected = new Song();
//        expected.setIndex(8628);
//        expected.setTitle("Title");
//        expected.setComposer("Johann Sebastian Bach");
//        expected.setMeter(new RationalNumber(4, 4));
//        expected.setDefaultDuration(new RationalNumber(1, 16));
//        expected.setTempo(280);
//        expected.addVoice(new Voice("1"));
//        expected.addVoice(new Voice("2"));
//        expected.addVoice(new Voice("3"));
//        expected.setKeySignature("C");
//        
//        assertEquals(expected,parser.getSong());
//    }
//    
//    /**
//     * Tests the parsing of a song with a complete header in an incorrect order
//     */
//    @Test(expected = ParserException.class)
//    public void incorrectHeaderTest() {
//        Lexer lexer = new Lexer("K:Me\nX:500\nT:1\nGGG");
//        Parser parser = new Parser(lexer);
//        parser.parse();
//    }
//    
//    /**
//     * Test the parsing of a song with an incomplete header
//     */
//    @Test(expected = ParserException.class)
//    public void incompleteHeaderTest() {
//        Lexer lexer = new Lexer("C:Me\nQ:500\nGGG");
//        Parser parser = new Parser(lexer);
//        parser.parse();
//    }
    
    /**
     * Test the parsing of a SingleNote
     */
    @Test
    public void parseSingleNoteTest() {
        //Parse "^G,,8"
        Token keyNote = new Token(Token.Type.KEYNOTE);
        keyNote.setAccidental(1);
        keyNote.setDuration(new RationalNumber(8, 1));
        keyNote.setOctave(-2);
        keyNote.setValue("G");
        
        ArrayList<Token> tokens = makeTokensFromCorrectHeader();
        tokens.add(keyNote);
        
        Parser parser = new Parser();
        parser.parse(tokens);
        
        Song expected = makeSongFromCorrectHeader();
        expected.add(new SingleNote('G',new RationalNumber(8, 1),-2,1));
        assertEquals(expected, parser.getSong());
    }
    
    /**
     * Test the parsing of a Rest
     */
    @Test
    public void parseRestTest() {
        //Parse "z/"
        Token rest = new Token(Token.Type.REST);
        rest.setDuration(new RationalNumber(1, 2));
        
        ArrayList<Token> tokens = makeTokensFromCorrectHeader();
        tokens.add(rest);
        
        Parser parser = new Parser();
        parser.parse(tokens);
        
        Song expected = makeSongFromCorrectHeader();
        expected.add(new Rest(new RationalNumber(1, 2)));
        assertEquals(expected, parser.getSong());
    }
    
    /**
     * Test the parsing of a Chord
     */
    @Test
    public void parseChordTest() {
        //Parse "[E16G16]"
        Token chordStart = new Token(Token.Type.CHORD_START);
        chordStart.setValue("[");
        
        Token firstNote = new Token(Token.Type.KEYNOTE);
        firstNote.setAccidental(Integer.MAX_VALUE);
        firstNote.setDuration(new RationalNumber(16, 1));
        firstNote.setOctave(0);
        firstNote.setValue("E");
        
        Token secondNote = new Token(Token.Type.KEYNOTE);
        secondNote.setAccidental(Integer.MAX_VALUE);
        secondNote.setDuration(new RationalNumber(16, 1));
        secondNote.setOctave(0);
        secondNote.setValue("G");
        
        Token chordEnd = new Token(Token.Type.CHORD_END);
        chordStart.setValue("]");
        
        ArrayList<Token> tokens = makeTokensFromCorrectHeader();
        tokens.add(chordStart);
        tokens.add(firstNote);
        tokens.add(secondNote);
        tokens.add(chordEnd);
        
        Parser parser = new Parser();
        parser.parse(tokens);
        
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
        //Parse "(2GG"
        Token dupletStart = new Token(Token.Type.DUPLET_START);
        dupletStart.setValue("(2");
        
        Token note = new Token(Token.Type.KEYNOTE);
        note.setAccidental(Integer.MAX_VALUE);
        note.setDuration(new RationalNumber(1, 1));
        note.setOctave(0);
        note.setValue("G");
        
        ArrayList<Token> tokens = makeTokensFromCorrectHeader();
        tokens.add(dupletStart);
        tokens.add(note);
        tokens.add(note);
        
        Parser parser = new Parser();
        parser.parse(tokens);
        
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
        //Parse "(2G"
        Token dupletStart = new Token(Token.Type.DUPLET_START);
        dupletStart.setValue("(2");
        
        Token note = new Token(Token.Type.KEYNOTE);
        note.setAccidental(Integer.MAX_VALUE);
        note.setDuration(new RationalNumber(1, 1));
        note.setOctave(0);
        note.setValue("G");
        
        ArrayList<Token> tokens = makeTokensFromCorrectHeader();
        tokens.add(dupletStart);
        tokens.add(note);
        
        Parser parser = new Parser();
        parser.parse(tokens);
    }

    /**
     * Tests the parsing of a Triplet
     */
    @Test
    public void parseTripletTest() {
        //Parse "(3GGG"
        Token tripletStart = new Token(Token.Type.TRIPLET_START);
        tripletStart.setValue("(3");
        
        Token note = new Token(Token.Type.KEYNOTE);
        note.setAccidental(Integer.MAX_VALUE);
        note.setDuration(new RationalNumber(1, 1));
        note.setOctave(0);
        note.setValue("G");
        
        ArrayList<Token> tokens = makeTokensFromCorrectHeader();
        tokens.add(tripletStart);
        tokens.add(note);
        tokens.add(note);
        tokens.add(note);
        
        Parser parser = new Parser();
        parser.parse(tokens);

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
        //Parse "(3GG"
        Token tripletStart = new Token(Token.Type.TRIPLET_START);
        tripletStart.setValue("(3");
        
        Token note = new Token(Token.Type.KEYNOTE);
        note.setAccidental(Integer.MAX_VALUE);
        note.setDuration(new RationalNumber(1, 1));
        note.setOctave(0);
        note.setValue("G");
        
        ArrayList<Token> tokens = makeTokensFromCorrectHeader();
        tokens.add(tripletStart);
        tokens.add(note);
        tokens.add(note);
        
        Parser parser = new Parser();
        parser.parse(tokens);
    }
    
    /**
     * Tests the parsing of a Quadruplet
     */
    @Test
    public void parseQuadrupletTest() {
        //Parse "(4GGGG"
        Token quadStart = new Token(Token.Type.QUAD_START);
        quadStart.setValue("(4");
        
        Token note = new Token(Token.Type.KEYNOTE);
        note.setAccidental(Integer.MAX_VALUE);
        note.setDuration(new RationalNumber(1, 1));
        note.setOctave(0);
        note.setValue("G");
        
        ArrayList<Token> tokens = makeTokensFromCorrectHeader();
        tokens.add(quadStart);
        tokens.add(note);
        tokens.add(note);
        tokens.add(note);
        tokens.add(note);
        
        Parser parser = new Parser();
        parser.parse(tokens);
        
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
        //Parse "(4GGG"
        Token quadStart = new Token(Token.Type.QUAD_START);
        quadStart.setValue("(4");
        
        Token note = new Token(Token.Type.KEYNOTE);
        note.setAccidental(Integer.MAX_VALUE);
        note.setDuration(new RationalNumber(1, 1));
        note.setOctave(0);
        note.setValue("G");
        
        ArrayList<Token> tokens = makeTokensFromCorrectHeader();
        tokens.add(quadStart);
        tokens.add(note);
        tokens.add(note);
        tokens.add(note);
        
        Parser parser = new Parser();
        parser.parse(tokens);
    }
    
    /**
     * Test the parsing of a song with repeats
     */
    @Test
    public void parseRepeatsTest() {
        //Parse "|:G:|"
        Token repeatStart = new Token(Token.Type.REPEAT_START);
        repeatStart.setValue("|:");
        
        Token note = new Token(Token.Type.KEYNOTE);
        note.setAccidental(Integer.MAX_VALUE);
        note.setDuration(new RationalNumber(1, 1));
        note.setOctave(0);
        note.setValue("G");
        
        Token repeatEnd = new Token(Token.Type.REPEAT_END);
        repeatEnd.setValue(":|");
        
        ArrayList<Token> tokens = makeTokensFromCorrectHeader();
        tokens.add(repeatStart);
        tokens.add(note);
        tokens.add(repeatEnd);
        
        Parser parser = new Parser();
        parser.parse(tokens);
        
        Song expected = makeSongFromCorrectHeader();
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
        //Parse "|:A[1G:|[2C"
        // should be parsed as AGAC
        
        Token repeatStart = new Token(Token.Type.REPEAT_START);
        repeatStart.setValue("|:");
        
        Token repeatEnd = new Token(Token.Type.REPEAT_END);
        repeatEnd.setValue(":|");
        
        Token repeat1 = new Token(Token.Type.REPEAT_NUMBER);
        repeat1.setValue("[1");
        
        Token repeat2 = new Token(Token.Type.REPEAT_NUMBER);
        repeat2.setValue("[2");
        
        Token noteA = new Token(Token.Type.KEYNOTE);
        noteA.setAccidental(Integer.MAX_VALUE);
        noteA.setDuration(new RationalNumber(1, 1));
        noteA.setOctave(0);
        noteA.setValue("A");
        
        Token noteC = new Token(Token.Type.KEYNOTE);
        noteC.setAccidental(Integer.MAX_VALUE);
        noteC.setDuration(new RationalNumber(1, 1));
        noteC.setOctave(0);
        noteC.setValue("C");
        
        Token noteG = new Token(Token.Type.KEYNOTE);
        noteG.setAccidental(Integer.MAX_VALUE);
        noteG.setDuration(new RationalNumber(1, 1));
        noteG.setOctave(0);
        noteG.setValue("G");
        
        ArrayList<Token> tokens = makeTokensFromCorrectHeader();
        tokens.add(repeatStart);
        tokens.add(noteA);
        tokens.add(repeat1);
        tokens.add(noteG);
        tokens.add(repeatEnd);
        tokens.add(repeat2);
        tokens.add(noteC);
        
        Parser parser = new Parser();
        parser.parse(tokens);
        
        Song expected = makeSongFromCorrectHeader();
        SingleNote parsedG = new SingleNote('G', new RationalNumber(1, 1), 0, 0);
        SingleNote parsedA = new SingleNote('A', new RationalNumber(1, 1), 0, 0);
        SingleNote parsedC = new SingleNote('C', new RationalNumber(1, 1), 0, 0);

        expected.add(parsedA);
        expected.add(parsedG);
        expected.add(parsedA);
        expected.add(parsedC);

        assertEquals(expected, parser.getSong());
    }
    
}
