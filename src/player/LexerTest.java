package player;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;

/**
 * Tests the Lexer class
 * Testing Strategy:
 *  - Each individual token type is correctly lexed
 *  - Whitespace is ignored
 *  - Linefeeds are ignored and lexing continues after a newline character
 *  - Comments are ignored
 *  - Characters outside the grammar throw exceptions
 */
public class LexerTest {

    
    /**
     * Tests that a comment is ignored
     */
    @Test
    public void ignoreCommentTest() {
        Lexer lexer = new Lexer("G,,8\n%comment\nV:1\n");
        ArrayList<Token> expected = new ArrayList<Token>(0);
        Token first = new Token(Token.Type.KEYNOTE);
        first.setValue("G");
        first.setOctave(-2);
        first.setAccidental(Integer.MAX_VALUE);
        first.setDuration(new RationalNumber(8, 1));
        
        Token second = new Token(Token.Type.VOICE);
        second.setValue("1");
        
        expected.add(first);
        expected.add(second);
        
        
        ArrayList<Token> lexx=lexer.lexBody();
        System.out.println();
        System.out.println(lexx.toString());
        System.out.println(expected.toString());
        
        assertEquals(expected.size(),lexx.size());
        
        for (int i =0;i<expected.size();++i){
            assertEquals(expected.get(i), lexx.get(i));
        }

    }
    
    
    /**
     * Tests that a COMPOSER token is correctly lexed
     */
    @Test
    public void composerTest() {
        Lexer lexer = new Lexer("C:Wolfgang Amadeus-Mozart\nK: C\n");
        Token expected = new Token(Token.Type.COMPOSER);
        expected.setValue("Wolfgang Amadeus-Mozart");
        expected.setHeader(true);
        System.out.println(lexer.lexHead().toString());
        assertEquals(expected, lexer.lexHead().get(0));
    }
    
    /**
     * Tests that a KEY token is correctly lexed
     */
    @Test
    public void keyTest() {
        Lexer lexer = new Lexer("X: 1\nT: empty\nK:A#m\n");
        Token expected = new Token(Token.Type.KEY);
        expected.setValue("A#m");
        expected.setHeader(true);
        assertEquals(expected, lexer.lexHead().get(0));
    }
    
    /**
     * Tests that a LENGTH token is correctly lexed
     */
    @Test
    public void lengthTest() {
        Lexer lexer = new Lexer("L:1/8K: C\n");
        Token expected = new Token(Token.Type.LENGTH);
        expected.setValue("1/8");
        expected.setHeader(true);
        assertEquals(expected, lexer.lexHead().get(0));
    }
    
    /**
     * Tests that a METER token is correctly lexed
     */
    @Test
    public void meterTest() {
        Lexer lexer = new Lexer("M:C|%Comment\n");
        Token expected = new Token(Token.Type.METER);
        expected.setValue("C|");
        System.out.printf(" orig: %s\n expected: %s\n",lexer.lexHead().toString(),expected.toString());
        assertEquals(expected, lexer.lexHead().get(0));
    }
    
    /**
     * Tests that a TEMPO token is correctly lexed
     */
    @Test
    public void tempoTest() {
        Lexer lexer = new Lexer("Q:240%Comment\n");
        Token expected = new Token(Token.Type.TEMPO);
        expected.setValue("240");
        assertEquals(expected, lexer.lexHead().get(0));
    }
    
    /**
     * Tests that a TITLE token is correctly lexed
     */
    @Test
    public void titleTest() {
        Lexer lexer = new Lexer("T:Title of song%Comment\n");
        Token expected = new Token(Token.Type.TITLE);
        expected.setValue("Title of song");
        assertEquals(expected, lexer.lexHead().get(0));
    }
    
    /**
     * Tests that a INDEX token is correctly lexed
     */
    @Test
    public void indexTest() {
        Lexer lexer = new Lexer("X:57%Comment\n");
        Token expected = new Token(Token.Type.INDEX);
        expected.setValue("57");
        assertEquals(expected, lexer.lexHead().get(0));
    }
    
    /**
     * Tests that a VOICE token is correctly lexed
     */
    @Test
    public void voiceTest() {
        Lexer lexer = new Lexer("V:Voice Number 1\n");
        Token expected = new Token(Token.Type.VOICE);
        expected.setValue("Voice Number 1");
        assertEquals(expected, lexer.lexHead().get(0));
    }
    
    /**
     * Tests that a KEYNOTE token is correctly lexed, with all fields
     */
    @Test
    public void keynoteFullTest() {
        Lexer lexer = new Lexer("_E,7");
        Token expected = new Token(Token.Type.KEYNOTE);
        expected.setValue("E");
        expected.setAccidental(-1);
        expected.setOctave(-1);
        expected.setDuration(new RationalNumber(7, 1));
        assertEquals(expected, lexer.lexHead().get(0));
    }
    
    /**
     * Tests that a KEYNOTE token is correctly lexed, if KEYNOTE is only a basenote
     */
    @Test
    public void keynoteTest() {
        Lexer lexer = new Lexer("E");
        Token expected = new Token(Token.Type.KEYNOTE);
        expected.setValue("E");
        expected.setAccidental(Integer.MAX_VALUE);
        expected.setOctave(0);
        expected.setDuration(new RationalNumber(1, 1));
        assertEquals(expected, lexer.lexHead().get(0));
    }
    
    /**
     * Tests that a REST token is correctly lexed
     */
    @Test
    public void restTest() {
        Lexer lexer = new Lexer("z 1/ 2");
        Token expected = new Token(Token.Type.REST);
        expected.setDuration(new RationalNumber(1,2));
        expected.setValue("z");
        assertEquals(expected, lexer.lexBody().get(0));
    }
    
    /**
     * Tests that a CHORD_START token is correctly lexed
     */
    @Test
    public void chordStartTest() {
        Lexer lexer = new Lexer("[");
        Token expected = new Token(Token.Type.CHORD_START);
        expected.setValue("[");
        assertEquals(expected, lexer.lexBody().get(0));
    }
    
    /**
     * Tests that a CHORD_END token is correctly lexed
     */
    @Test
    public void chordEndTest() {
        Lexer lexer = new Lexer("]");
        Token expected = new Token(Token.Type.CHORD_END);
        expected.setValue("]");
        assertEquals(expected, lexer.lexBody().get(0));
    }
    
    /**
     * Tests that a chord is lexed correctly
     */
    @Test
    public void chordTest() {
       Lexer lexer = new Lexer("[E16G16]");
       ArrayList<Token> expected = new ArrayList<Token>(0);
       Token first = new Token(Token.Type.CHORD_START);
       first.setValue("[");
       
       Token second = new Token(Token.Type.KEYNOTE);
       second.setAccidental(Integer.MAX_VALUE);
       second.setDuration(new RationalNumber(16, 1));
       second.setOctave(0);
       second.setValue("E");
       
       Token third = new Token(Token.Type.KEYNOTE);
       third.setAccidental(Integer.MAX_VALUE);
       third.setDuration(new RationalNumber(16, 1));
       third.setOctave(0);
       third.setValue("G");
       
       Token fourth = new Token(Token.Type.CHORD_END);
       fourth.setValue("]");
    
       expected.add(first);
       expected.add(second);
       expected.add(third);
       expected.add(fourth);
       
       assertEquals(expected, lexer.lexBody());
    }
    
    /**
     * Tests that a DUPLET_START token is correctly lexed
     */
    @Test
    public void dupletStartTest() {
        Lexer lexer = new Lexer("(2");
        Token expected = new Token(Token.Type.DUPLET_START);
        expected.setValue("(2");
        assertEquals(expected, lexer.lexBody().get(0));
    }
    
    /**
     * Tests that a TRIPLET_START token is correctly lexed
     */
    @Test
    public void tripletStartTest() {
        Lexer lexer = new Lexer("(3");
        Token expected = new Token(Token.Type.TRIPLET_START);
        expected.setValue("(3");
        assertEquals(expected, lexer.lexBody().get(0));
    }
    
    /**
     * Tests that a QUAD_START token is correctly lexed
     */
    @Test
    public void quadStartTest() {
        Lexer lexer = new Lexer("(4");
        Token expected = new Token(Token.Type.QUAD_START);
        expected.setValue("(4");
        assertEquals(expected, lexer.lexBody().get(0));
    }
    
    /**
     * Tests that a BAR token is correctly lexed
     */
    @Test
    public void barTest() {
        Lexer lexer = new Lexer("|");
        Token expected = new Token(Token.Type.BAR);
        expected.setValue("|");
        assertEquals(expected, lexer.lexBody().get(0));
    }
    
    /**
     * Tests that a DOUBLE_BAR token is correctly lexed
     */
    @Test
    public void doubleBarTest() {
        Lexer lexer = new Lexer("||");
        Token expected = new Token(Token.Type.DOUBLE_BAR);
        expected.setValue("||");
        assertEquals(expected, lexer.lexBody().get(0));
    }
    
    /**
     * Tests that a REPEAT_START token is correctly lexed
     */
    @Test
    public void repeatStartTest() {
        Lexer lexer = new Lexer("|:");
        Token expected = new Token(Token.Type.REPEAT_START);
        expected.setValue("|:");
        assertEquals(expected, lexer.lexBody().get(0));
    }
    
    /**
     * Tests that a REPEAT_END token is correctly lexed
     */
    @Test
    public void repeatEndTest() {
        Lexer lexer = new Lexer(":|");
        Token expected = new Token(Token.Type.REPEAT_END);
        expected.setValue(":|");
        assertEquals(expected, lexer.lexBody().get(0));
    }
    
    /**
     * Tests that a REPEAT_NUMBER token is correctly lexed, for repeat #1
     */
    @Test
    public void repeatNumber1Test() {
        Lexer lexer = new Lexer("[1");
        Token expected = new Token(Token.Type.REPEAT_NUMBER);
        expected.setValue("[1");
        assertEquals(expected, lexer.lexBody().get(0));
    }
    
    /**
     * Tests that a REPEAT_NUMBER token is correctly lexed, for repeat #2
     */
    @Test
    public void repeatNumber2Test() {
        Lexer lexer = new Lexer("[2");
        Token expected = new Token(Token.Type.REPEAT_NUMBER);
        expected.setValue("[2");
        assertEquals(expected, lexer.lexBody().get(0));
    }
    
    
    
    /**
     * Tests that whitespace is ignored
     * Whitespace should not be ignored in header fields
     */
    @Test
    public void ignoreWhitespaceTest() {
        Lexer lexer = new Lexer("T:Paddy O'Rafferty");
        ArrayList<Token> expected = new ArrayList<Token>();
        Token first = new Token(Token.Type.TITLE);
        first.setValue("Paddy O'Rafferty");
        
        RationalNumber singleDuration = new RationalNumber(1, 1);
        
        Token second = new Token(Token.Type.KEYNOTE);
        second.setValue("D");
        second.setAccidental(Integer.MAX_VALUE);
        second.setOctave(1);
        second.setDuration(singleDuration);
        
        Token third = new Token(Token.Type.KEYNOTE);
        third.setValue("F");
        third.setAccidental(Integer.MAX_VALUE);
        third.setOctave(1);
        third.setDuration(singleDuration);
        
        Token fourth = new Token(Token.Type.KEYNOTE);
        fourth.setValue("C");
        fourth.setAccidental(Integer.MAX_VALUE);
        fourth.setOctave(1);
        fourth.setDuration(singleDuration);
        
        Token fifth = new Token(Token.Type.KEYNOTE);
        fifth.setValue("E");
        fifth.setAccidental(Integer.MAX_VALUE);
        fifth.setOctave(1);
        fifth.setDuration(singleDuration);
        
        expected.add(first);
        expected.add(second);
        expected.add(third);
        expected.add(fourth);
        expected.add(fifth);
        
        assertEquals(expected, lexer.lexHead());
    }
    
    /**
     * Tests that a repeated expression is correctly lexed
     */
    @Test
    public void repeatLexerTest() {
        Lexer lexer = new Lexer("|:C:|");
        
        ArrayList<Token> expected = new ArrayList<Token>();
        Token first = new Token(Token.Type.REPEAT_START);
        first.setValue("|:");
        expected.add(first);
        
        Token second = new Token(Token.Type.KEYNOTE);
        second.setValue("C");
        second.setDuration(new RationalNumber(1, 1));
        second.setOctave(0);
        second.setAccidental(Integer.MAX_VALUE);
        expected.add(second);
        
        Token third = new Token(Token.Type.REPEAT_END);
        third.setValue(":|");
        expected.add(third);
        
        assertEquals(expected, lexer.lexBody());
    }
    
            
    /**
     * extra test
     */
    @Test
    public void Test1() {
        Lexer lexer = new Lexer("V: lower\nC8 C8 | C8 C8 | B,8 B,8 | C8 C8 |]");
        ArrayList<Token> expected = new ArrayList<Token>();
        
        
        Token first = new Token(Token.Type.VOICE);
        first.setValue("lower");
        
        
        Token second = new Token(Token.Type.KEYNOTE);
        second.setValue("C");
        second.setAccidental(Integer.MAX_VALUE);
        second.setOctave(0);
        second.setDuration(new RationalNumber(8,1));
        
        Token bar = new Token(Token.Type.BAR);
        bar.setValue("|");
        
        Token third = new Token(Token.Type.KEYNOTE);
        third.setValue("B");
        third.setAccidental(Integer.MAX_VALUE);
        third.setOctave(-1);
        third.setDuration(new RationalNumber(8,1));
        

        Token fifth = new Token(Token.Type.CHORD_END);
        fifth.setValue("]");
        
        expected.add(first);
        
        expected.add(second);
        expected.add(second);
        expected.add(bar);
        
        expected.add(second);
        expected.add(second);
        expected.add(bar);
        
        expected.add(third);
        expected.add(third);
        expected.add(bar);
        
        expected.add(second);
        expected.add(second);
        expected.add(bar);

        expected.add(fifth);
        
        
        assertEquals(expected, lexer.lexBody());
    }
    
}
