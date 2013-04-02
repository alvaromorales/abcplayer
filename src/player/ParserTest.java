package player;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;
import player.AST.*;
import static org.junit.Assert.*;

/**
 * Tests the Parser
 * Testing Strategy:
 *  - test parsing of header
 *  - test parsing of individual tokens into the appropriate NoteElements
 *  - test splitTokensByVoice
 *  - test parsing of a song with multiple voices
 *  - test repeats
 *  - test ParserExceptions
 */
public class ParserTest {
    
    /**
     * Tests the parsing of the header
     */
    @Test
    public void headerTest() {
        //Header: "X:8628\nT:Title\nC:Johann Sebastian Bach\nM:4/4\nL:1/16\nQ:280\nV:1\nV:2\nV:3\nK:C\n"
        Token index = new Token(Token.Type.INDEX);
        index.setValue("8628");
        
        Token title = new Token(Token.Type.TITLE);
        title.setValue("Title");

        Token composer = new Token(Token.Type.COMPOSER);
        composer.setValue("Johann Sebastian Bach");
        
        Token meter = new Token(Token.Type.METER);
        meter.setValue("4/4");
        
        Token defaultNoteLength = new Token(Token.Type.LENGTH);
        defaultNoteLength.setValue("1/16");
        
        Token tempo = new Token(Token.Type.TEMPO);
        tempo.setValue("280");
        
        Token voice1 = new Token(Token.Type.VOICE);
        voice1.setValue("1");

        Token voice2 = new Token(Token.Type.VOICE);
        voice2.setValue("2");
        
        Token voice3 = new Token(Token.Type.VOICE);
        voice3.setValue("3");
        
        Token key = new Token(Token.Type.KEY);
        key.setValue("C");
        
        ArrayList<Token> tokens = new ArrayList<Token>(0);
        tokens.add(index);
        tokens.add(title);
        tokens.add(composer);
        tokens.add(meter);
        tokens.add(defaultNoteLength);
        tokens.add(tempo);
        tokens.add(voice1);
        tokens.add(voice2);
        tokens.add(voice3);
        tokens.add(key);
        
        Parser parser = new Parser();
        parser.parseHeader(tokens);

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
        
        Token index = new Token(Token.Type.INDEX);
        index.setValue("8628");
        
        Token title = new Token(Token.Type.TITLE);
        title.setValue("Title");
        
        Token key = new Token(Token.Type.KEY);
        key.setValue("C");
        
        ArrayList<Token> tokens = new ArrayList<Token>(0);
        tokens.add(key);
        tokens.add(index);
        tokens.add(title);
        
        Parser parser = new Parser();
        parser.parseHeader(tokens);
    }
    
    
    /**
     * Test the parsing of a song with an incomplete header
     */
    @Test(expected = ParserException.class)
    public void incompleteHeaderTest() {
        //Parse: "C:Me\nQ:500\n"
        
        Token composer = new Token(Token.Type.COMPOSER);
        composer.setValue("Johann Sebastian Bach");
        
        Token tempo = new Token(Token.Type.TEMPO);
        tempo.setValue("280");
        
        ArrayList<Token> tokens = new ArrayList<Token>(0);
        tokens.add(composer);
        tokens.add(tempo);
        
        Parser parser = new Parser();
        parser.parseHeader(tokens);
    }
    
    /**
     * Test the parsing of a SingleNote
     */
    //@Test
    public void parseSingleNoteTest() {
        //Parse "^G,,8"
        Token keyNote = new Token(Token.Type.KEYNOTE);
        keyNote.setAccidental(1);
        keyNote.setDuration(new RationalNumber(8, 1));
        keyNote.setOctave(-2);
        keyNote.setValue("G");
        
        ArrayList<Token> tokens = new ArrayList<Token>(0);
        tokens.add(keyNote);
        
        Parser parser = new Parser();
        parser.parseVoice(tokens);

        Song expected = new Song();
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
        
        ArrayList<Token> tokens = new ArrayList<Token>(0);
        tokens.add(rest);
        
        Parser parser = new Parser();
        parser.parseVoice(tokens);
        
        Song expected = new Song();
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
        
        ArrayList<Token> tokens = new ArrayList<Token>(0);
        tokens.add(chordStart);
        tokens.add(firstNote);
        tokens.add(secondNote);
        tokens.add(chordEnd);
        
        Parser parser = new Parser();
        parser.parseVoice(tokens);
        
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
        //Parse "(2GG"
        Token dupletStart = new Token(Token.Type.DUPLET_START);
        dupletStart.setValue("(2");
        
        Token note = new Token(Token.Type.KEYNOTE);
        note.setAccidental(Integer.MAX_VALUE);
        note.setDuration(new RationalNumber(1, 1));
        note.setOctave(0);
        note.setValue("G");
        
        ArrayList<Token> tokens = new ArrayList<Token>(0);
        tokens.add(dupletStart);
        tokens.add(note);
        tokens.add(note);
        
        Parser parser = new Parser();
        parser.parseVoice(tokens);
        
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
        //Parse "(2G"
        Token dupletStart = new Token(Token.Type.DUPLET_START);
        dupletStart.setValue("(2");
        
        Token note = new Token(Token.Type.KEYNOTE);
        note.setAccidental(Integer.MAX_VALUE);
        note.setDuration(new RationalNumber(1, 1));
        note.setOctave(0);
        note.setValue("G");
        
        ArrayList<Token> tokens = new ArrayList<Token>(0);
        tokens.add(dupletStart);
        tokens.add(note);
        
        Parser parser = new Parser();
        parser.parseVoice(tokens);
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
        
        ArrayList<Token> tokens = new ArrayList<Token>(0);
        tokens.add(tripletStart);
        tokens.add(note);
        tokens.add(note);
        tokens.add(note);
        
        Parser parser = new Parser();
        parser.parseVoice(tokens);

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
        //Parse "(3GG"
        Token tripletStart = new Token(Token.Type.TRIPLET_START);
        tripletStart.setValue("(3");
        
        Token note = new Token(Token.Type.KEYNOTE);
        note.setAccidental(Integer.MAX_VALUE);
        note.setDuration(new RationalNumber(1, 1));
        note.setOctave(0);
        note.setValue("G");
        
        ArrayList<Token> tokens = new ArrayList<Token>(0);
        tokens.add(tripletStart);
        tokens.add(note);
        tokens.add(note);
        
        Parser parser = new Parser();
        parser.parseVoice(tokens);
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
        
        ArrayList<Token> tokens = new ArrayList<Token>(0);
        tokens.add(quadStart);
        tokens.add(note);
        tokens.add(note);
        tokens.add(note);
        tokens.add(note);
        
        Parser parser = new Parser();
        parser.parseVoice(tokens);
        
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
        //Parse "(4GGG"
        Token quadStart = new Token(Token.Type.QUAD_START);
        quadStart.setValue("(4");
        
        Token note = new Token(Token.Type.KEYNOTE);
        note.setAccidental(Integer.MAX_VALUE);
        note.setDuration(new RationalNumber(1, 1));
        note.setOctave(0);
        note.setValue("G");
        
        ArrayList<Token> tokens = new ArrayList<Token>(0);
        tokens.add(quadStart);
        tokens.add(note);
        tokens.add(note);
        tokens.add(note);
        
        Parser parser = new Parser();
        parser.parseVoice(tokens);
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
        
        ArrayList<Token> tokens = new ArrayList<Token>(0);
        tokens.add(repeatStart);
        tokens.add(note);
        tokens.add(repeatEnd);
        
        Parser parser = new Parser();
        parser.parseVoice(tokens);
        
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
        
        ArrayList<Token> tokens = new ArrayList<Token>(0);
        tokens.add(repeatStart);
        tokens.add(noteA);
        tokens.add(repeat1);
        tokens.add(noteG);
        tokens.add(repeatEnd);
        tokens.add(repeat2);
        tokens.add(noteC);
        
        Parser parser = new Parser();
        parser.parseVoice(tokens);
        
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
     * Tests splitTokensByVoice
     */
    public void splitVoicesTest() {
        ArrayList<Token> tokens = new ArrayList<Token>(0);
        
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
        
        tokens.add(voice2);
        tokens.add(noteB);
        tokens.add(voice1);
        tokens.add(noteA);
        tokens.add(voice2);
        tokens.add(noteB);
        tokens.add(voice1);
        tokens.add(noteA);
        tokens.add(noteA);
        tokens.add(voice2);
        tokens.add(noteB);
        tokens.add(voice1);
        tokens.add(noteA);
        
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
        
        assertEquals(expected, parser.splitTokensByVoice(tokens));
    }
    
    /**
     * Tests parsing a song with a single anonymous voice
     */
    @Test
    public void parseSingleVoiceSongTest() {
        Token index = new Token(Token.Type.INDEX);
        index.setValue("8628");
        
        Token title = new Token(Token.Type.TITLE);
        title.setValue("Title");
        
        Token key = new Token(Token.Type.KEY);
        key.setValue("C");
        
        Token noteAToken = new Token(Token.Type.KEYNOTE);
        noteAToken.setAccidental(Integer.MAX_VALUE);
        noteAToken.setDuration(new RationalNumber(1, 1));
        noteAToken.setOctave(0);
        noteAToken.setValue("A");
        
        ArrayList<Token> headerTokens = new ArrayList<Token>(0);
        headerTokens.add(index);
        headerTokens.add(title);
        headerTokens.add(key);
        
        ArrayList<Token> bodyTokens = new ArrayList<Token>(0);
        bodyTokens.add(noteAToken);
        bodyTokens.add(noteAToken);
        bodyTokens.add(noteAToken);
        bodyTokens.add(noteAToken);
        
        SingleNote noteA = new SingleNote('A', new RationalNumber(1, 1), 0, 0);
        
        Song expected = new Song();
        expected.setIndex(8628);
        expected.setTitle("Title");
        expected.setKeySignature("C");
        
        System.out.println(expected.accidentalAssociator);
        expected.add(noteA);
        expected.add(noteA);
        expected.add(noteA);
        expected.add(noteA);

        Parser parser = new Parser();
        parser.parse(headerTokens, bodyTokens);
        
        assertEquals(expected, parser.getSong());
        
    }
    
    /**
     * Tests parsing a song with multiple voices
     */
    @Test
    public void multipleVoicesParseTest() {
        // input
        
        Token index = new Token(Token.Type.INDEX);
        index.setValue("8628");
        
        Token title = new Token(Token.Type.TITLE);
        title.setValue("Title");
        
        Token key = new Token(Token.Type.KEY);
        key.setValue("C");
        
        Token voice1Token = new Token(Token.Type.VOICE);
        voice1Token.setValue("1");
        
        Token voice2Token = new Token(Token.Type.VOICE);
        voice2Token.setValue("2");
        
        ArrayList<Token> headerTokens = new ArrayList<Token>(0);
        headerTokens.add(index);
        headerTokens.add(title);
        headerTokens.add(voice1Token);
        headerTokens.add(voice2Token);
        headerTokens.add(key);
        
        Token noteAToken = new Token(Token.Type.KEYNOTE);
        noteAToken.setAccidental(Integer.MAX_VALUE);
        noteAToken.setDuration(new RationalNumber(1, 1));
        noteAToken.setOctave(0);
        noteAToken.setValue("A");
        
        Token noteBToken = new Token(Token.Type.KEYNOTE);
        noteBToken.setAccidental(Integer.MAX_VALUE);
        noteBToken.setDuration(new RationalNumber(1, 1));
        noteBToken.setOctave(0);
        noteBToken.setValue("B");
        
        ArrayList<Token> bodyTokens = new ArrayList<Token>(0);
        bodyTokens.add(voice2Token);
        bodyTokens.add(noteBToken);
        bodyTokens.add(voice1Token);
        bodyTokens.add(noteAToken);
        bodyTokens.add(voice2Token);
        bodyTokens.add(noteBToken);
        bodyTokens.add(voice1Token);
        bodyTokens.add(noteAToken);
        bodyTokens.add(noteAToken);
        bodyTokens.add(voice2Token);
        bodyTokens.add(noteBToken);
        bodyTokens.add(voice1Token);
        bodyTokens.add(noteAToken);
        
        // expected output

        SingleNote noteA = new SingleNote('A', new RationalNumber(1, 1), 0, 0);
        SingleNote noteB = new SingleNote('B', new RationalNumber(1, 1), 0, 0);
        
        Voice voice1 = new Voice("1");
        voice1.addNote(noteA);
        voice1.addNote(noteA);
        voice1.addNote(noteA);
        voice1.addNote(noteA);

        Voice voice2 = new Voice("2");
        voice2.addNote(noteB);
        voice2.addNote(noteB);
        voice2.addNote(noteB);
        
        Song expected = new Song();
        expected.setIndex(8628);
        expected.setTitle("Title");
        expected.addVoice(voice1);
        expected.addVoice(voice2);
        expected.setKeySignature("C");
        
        Parser parser = new Parser();
        parser.parse(headerTokens, bodyTokens);
        
        assertEquals(expected, parser.getSong());
    }
    
}
