package player;

import static org.junit.Assert.*;
import org.junit.Test;
import player.AST.*;

/**
 * Tests the DurationVisitor
 * Testing Strategy:
 *  - test visiting an empty song
 *  - test visiting a simple one-voice song
 *  - test visiting a song with multiple voices
 */
public class DurationVisitorTest {

    /**
     * Tests the duration visitor with an empty song
     */
    @Test
    public void emptySongTest() {
        Song s = new Song();
        s.addVoice(new Voice());
        DurationVisitor visitor = new DurationVisitor();
        visitor.visit(s);
        assertEquals(0,visitor.getTicksPerQuarter());
    }
    
    /**
     * Tests the duration visitor on a basic song
     */
    @Test
    public void basicSongTest() {
        Voice v1 = new Voice();
        v1.addNote(new SingleNote('C',new RationalNumber(1, 8),0,0));
        v1.addNote(new SingleNote('C',new RationalNumber(3, 4),0,0));
        v1.addNote(new SingleNote('C',new RationalNumber(1, 4),0,0));
        v1.addNote(new Triplet(
                new SingleNote('C',new RationalNumber(2, 3),0,0),
                new SingleNote('C',new RationalNumber(2, 3),0,0),
                new SingleNote('C',new RationalNumber(2, 3),0,0)
                ));
        Song s = new Song();
        s.addVoice(v1);
        
        DurationVisitor visitor = new DurationVisitor();
        visitor.visit(s);
        assertEquals(24, visitor.getTicksPerQuarter());
    }
    
    /**
     * Tests the duration visitor on a song with multiple voices
     */
    @Test
    public void multipleVoiceSongTest() {
        Voice v1 = new Voice("Voice 1");
        v1.addNote(new SingleNote('C',new RationalNumber(1, 16),0,0));
        v1.addNote(new SingleNote('C',new RationalNumber(1, 35),0,0));
        v1.addNote(new SingleNote('C',new RationalNumber(1, 28),0,0));
        
        Voice v2 = new Voice("Voice 2");
        v2.addNote(new SingleNote('C',new RationalNumber(2, 1),0,0));
        v2.addNote(new Triplet(
                new SingleNote('C',new RationalNumber(1, 23),0,0),
                new SingleNote('C',new RationalNumber(1, 23),0,0),
                new SingleNote('C',new RationalNumber(1, 23),0,0)
                ));
        v2.addNote(new Duplet(
                new SingleNote('C',new RationalNumber(1, 7),0,0),
                new SingleNote('C',new RationalNumber(1, 7),0,0)
                ));
        v2.addNote(new SingleNote('C',new RationalNumber(1, 8),0,0));
        
        Song s = new Song();
        s.addVoice(v1);
        s.addVoice(v2);
        
        DurationVisitor visitor = new DurationVisitor();
        visitor.visit(s);
        assertEquals(12880, visitor.getTicksPerQuarter());
    }
    
}
