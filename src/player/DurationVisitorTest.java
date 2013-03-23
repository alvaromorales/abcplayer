package player;

import org.junit.Test;

import player.AST.*;

/**
 * Tests the DurationVisitor
 * TODO write testing strategy
 */
public class DurationVisitorTest {

    @Test
    public void durationTest() {
        Voice v1 = new Voice();
        v1.addNote(new SingleNote('C',new RationalNumber(1, 1),0,0));
        v1.addNote(new SingleNote('C',new RationalNumber(3, 4),0,0));
        v1.addNote(new SingleNote('C',new RationalNumber(1, 4),0,0));
        
        Voice v2 = new Voice();
        v2.addNote(new SingleNote('C',new RationalNumber(2, 1),0,0));
        v2.addNote(new Triplet(
                new SingleNote('C',new RationalNumber(1, 1),0,0),
                new SingleNote('C',new RationalNumber(1, 1),0,0),
                new SingleNote('C',new RationalNumber(1, 1),0,0),
                new RationalNumber(1, 1)
                ));
        v2.addNote(new SingleNote('C',new RationalNumber(1, 8),0,0));
        
        Song s = new Song();
        s.addVoice(v1);
        s.addVoice(v2);
        
        DurationVisitor visitor = new DurationVisitor();
        visitor.visit(s);
        System.out.println(visitor.findTicksPerQuarter());

    }
    
}
