package player;

import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;
import player.AST.*;
import sound.SequencePlayer;

/**
 * Tests the PlayerVisitor class
 * Testing Strategy:
 *  TODO
 *  @category no_didit
 */
public class PlayerVisitorTest {
    
    @Test
    public void songTest() {
        Song s = new Song();
        Voice v = new Voice();
        
        v.addNote(new SingleNote('C',new RationalNumber(1, 1),0,0));
        v.addNote(new SingleNote('C',new RationalNumber(1, 1),0,0));
        v.addNote(new SingleNote('C',new RationalNumber(3, 4),0,0));
        v.addNote(new SingleNote('D',new RationalNumber(1, 4),0,0));
        v.addNote(new SingleNote('E',new RationalNumber(1, 1),0,0));
        
        v.addNote(new SingleNote('E',new RationalNumber(3, 4),0,0));
        v.addNote(new SingleNote('D',new RationalNumber(1, 4),0,0));
        v.addNote(new SingleNote('E',new RationalNumber(3, 4),0,0));
        v.addNote(new SingleNote('F',new RationalNumber(1, 4),0,0));
        v.addNote(new SingleNote('G',new RationalNumber(2, 1),0,0));

        v.addNote(new Triplet(
                new SingleNote('C',new RationalNumber(2, 3),1,0),
                new SingleNote('C',new RationalNumber(2, 3),1,0),
                new SingleNote('C',new RationalNumber(2, 3),1,0),
                new RationalNumber(2, 3)
                ));
        v.addNote(new Triplet(
                new SingleNote('G',new RationalNumber(2, 3),0,0),
                new SingleNote('G',new RationalNumber(2, 3),0,0),
                new SingleNote('G',new RationalNumber(2, 3),0,0),
                new RationalNumber(2, 3)
                ));
        v.addNote(new Triplet(
                new SingleNote('E',new RationalNumber(2, 3),0,0),
                new SingleNote('E',new RationalNumber(2, 3),0,0),
                new SingleNote('E',new RationalNumber(2, 3),0,0),
                new RationalNumber(2, 3)
                ));
        v.addNote(new Triplet(
                new SingleNote('C',new RationalNumber(2, 3),0,0),
                new SingleNote('C',new RationalNumber(2, 3),0,0),
                new SingleNote('C',new RationalNumber(2, 3),0,0),
                new RationalNumber(2, 3)
                ));
        
        v.addNote(new SingleNote('G',new RationalNumber(3, 4),0,0));
        v.addNote(new SingleNote('F',new RationalNumber(1, 8),0,0));
        v.addNote(new SingleNote('E',new RationalNumber(3, 4),0,0));
        v.addNote(new SingleNote('D',new RationalNumber(1, 8),0,0));
        v.addNote(new SingleNote('C',new RationalNumber(2, 1),0,0));

        s.addVoice(v);
        
        DurationVisitor durationV = new DurationVisitor();
        durationV.visit(s);
        
        PlayerVisitor visitor = new PlayerVisitor(140, durationV.findTicksPerQuarter());
        visitor.visit(s);
        SequencePlayer player = visitor.getPlayer();
        try {
            player.play();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }
}
