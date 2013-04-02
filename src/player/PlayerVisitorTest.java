package player;
import java.util.ArrayList;
import javax.sound.midi.MidiUnavailableException;
import org.junit.Test;
import player.AST.*;
import sound.SequencePlayer;

/**
 * Tests the PlayerVisitor class
 * Testing Strategy
 *  - play piece1
 *  - play piece2
 *  @category no_didit
 */
public class PlayerVisitorTest {
    
    /**
     * Plays piece1
     */
    @Test
    public void pieceOneTest() {
        Song s = new Song();
        s.setTempo(140);
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
                new SingleNote('C',new RationalNumber(2, 6),1,0),
                new SingleNote('C',new RationalNumber(2, 6),1,0),
                new SingleNote('C',new RationalNumber(2, 6),1,0)
                ));
        v.addNote(new Triplet(
                new SingleNote('G',new RationalNumber(2, 6),0,0),
                new SingleNote('G',new RationalNumber(2, 6),0,0),
                new SingleNote('G',new RationalNumber(2, 6),0,0)
                ));
        v.addNote(new Triplet(
                new SingleNote('E',new RationalNumber(2, 6),0,0),
                new SingleNote('E',new RationalNumber(2, 6),0,0),
                new SingleNote('E',new RationalNumber(2, 6),0,0)
                ));
        v.addNote(new Triplet(
                new SingleNote('C',new RationalNumber(2, 6),0,0),
                new SingleNote('C',new RationalNumber(2, 6),0,0),
                new SingleNote('C',new RationalNumber(2, 6),0,0)
                ));
        
        v.addNote(new SingleNote('G',new RationalNumber(3, 4),0,0));
        v.addNote(new SingleNote('F',new RationalNumber(1, 4),0,0));
        v.addNote(new SingleNote('E',new RationalNumber(3, 4),0,0));
        v.addNote(new SingleNote('D',new RationalNumber(1, 4),0,0));
        v.addNote(new SingleNote('C',new RationalNumber(2, 1),0,0));

        s.addVoice(v);
        
        DurationVisitor durationV = new DurationVisitor();
        durationV.visit(s);
        
        PlayerVisitor visitor = new PlayerVisitor(durationV.getTicksPerQuarter(),s.getTempo(),s.getDefaultNoteLength());
        visitor.visit(s);
        SequencePlayer player = visitor.getPlayer();
        try {
            player.play();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Plays piece2
     */
    @Test
    public void pieceTwoTest() {
        Song s = new Song();
        s.setTempo(200);
        s.setDefaultDuration(new RationalNumber(1, 4));
        Voice v = new Voice();
        
        // First bar
        
        ArrayList<NoteElement> notesInChord1 = new ArrayList<NoteElement>();
        notesInChord1.add(new SingleNote('F',new RationalNumber(1, 2),0,1));
        notesInChord1.add(new SingleNote('E',new RationalNumber(1, 2),1,0));
        
        v.addNote(new Chord(new RationalNumber(1, 2), notesInChord1));
        v.addNote(new Chord(new RationalNumber(1, 2), notesInChord1));
        v.addNote(new Rest(new RationalNumber(1, 2)));
        v.addNote(new Chord(new RationalNumber(1, 2), notesInChord1));
        v.addNote(new Rest(new RationalNumber(1, 2)));

        ArrayList<NoteElement> notesInChord2 = new ArrayList<NoteElement>();
        notesInChord2.add(new SingleNote('F',new RationalNumber(1, 2),0,1));
        notesInChord2.add(new SingleNote('C',new RationalNumber(1, 2),1,0));
        v.addNote(new Chord(new RationalNumber(1, 2), notesInChord2));
        
        ArrayList<NoteElement> notesInChord3 = new ArrayList<NoteElement>();
        notesInChord3.add(new SingleNote('F',new RationalNumber(1, 1),0,1));
        notesInChord3.add(new SingleNote('E',new RationalNumber(1, 1),1,0));
        v.addNote(new Chord(new RationalNumber(1, 1), notesInChord3));
        
        // Second bar

        ArrayList<NoteElement> notesInChord4 = new ArrayList<NoteElement>();
        notesInChord4.add(new SingleNote('G',new RationalNumber(1, 1),0,0));
        notesInChord4.add(new SingleNote('B',new RationalNumber(1, 1),0,0));
        notesInChord4.add(new SingleNote('G',new RationalNumber(1, 1),0,0));

        v.addNote(new Chord(new RationalNumber(1, 1), notesInChord4));
        v.addNote(new Rest(new RationalNumber(1, 1)));
        v.addNote(new SingleNote('G',new RationalNumber(1, 1),0,0));
        v.addNote(new Rest(new RationalNumber(1, 1)));
              
        // Third bar
        v.addNote(new SingleNote('C',new RationalNumber(3, 2),1,0));
        v.addNote(new SingleNote('G',new RationalNumber(1, 2),0,0));
        v.addNote(new Rest(new RationalNumber(1, 1)));
        v.addNote(new SingleNote('E',new RationalNumber(1, 1),0,0));

        // Fourth bar
        v.addNote(new SingleNote('E',new RationalNumber(1, 2),0,0));
        v.addNote(new SingleNote('A',new RationalNumber(1, 1),0,0));
        v.addNote(new SingleNote('B',new RationalNumber(1, 1),0,0));
        v.addNote(new SingleNote('B',new RationalNumber(1, 2),0,-1));
        v.addNote(new SingleNote('A',new RationalNumber(1, 1),0,0));

        // Fifth bar
        v.addNote(new SingleNote('G',new RationalNumber(2, 3),0,0));
        v.addNote(new SingleNote('E',new RationalNumber(2, 3),1,0));
        v.addNote(new SingleNote('G',new RationalNumber(2, 3),1,0));
        v.addNote(new SingleNote('A',new RationalNumber(1, 1),1,0));
        v.addNote(new SingleNote('F',new RationalNumber(1, 2),1,0));
        v.addNote(new SingleNote('G',new RationalNumber(1, 2),1,0));

        // Sixth bar
        v.addNote(new Rest(new RationalNumber(1, 2)));
        v.addNote(new SingleNote('E',new RationalNumber(1, 1),1,0));
        v.addNote(new SingleNote('C',new RationalNumber(1, 2),1,0));
        v.addNote(new SingleNote('D',new RationalNumber(1, 2),1,0));
        v.addNote(new SingleNote('B',new RationalNumber(3, 4),0,0));      
        
        s.addVoice(v);
        
        DurationVisitor durationV = new DurationVisitor();
        durationV.visit(s);
        
        PlayerVisitor visitor = new PlayerVisitor(durationV.getTicksPerQuarter(),s.getTempo(),s.getDefaultNoteLength());
        visitor.visit(s);
        SequencePlayer player = visitor.getPlayer();
        try {
            player.play();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }
}
