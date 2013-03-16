package sound;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

/**
 * Tests SequencePlayer by playing the warmup exercises
 * @category no_didit
 *
 */

public class SequencePlayerTest {

    /**
     * Plays piece1.abc
     */
    @Test
    public void piece1Test() {
        SequencePlayer player;
        try {
            player = new SequencePlayer(120, 2);
            
            player.addNote(new Pitch('C').toMidiNote(), 0, 1);
            player.addNote(new Pitch('D').toMidiNote(), 1, 1);
            player.addNote(new Pitch('E').toMidiNote(), 2, 1);
            player.addNote(new Pitch('F').toMidiNote(), 3, 1);
            player.addNote(new Pitch('G').toMidiNote(), 4, 1);
            player.addNote(new Pitch('A').toMidiNote(), 5, 1);
            player.addNote(new Pitch('B').toMidiNote(), 6, 1);
            player.addNote(new Pitch('C').transpose(Pitch.OCTAVE).toMidiNote(), 7, 1);
            player.addNote(new Pitch('B').toMidiNote(), 8, 1);
            player.addNote(new Pitch('A').toMidiNote(), 9, 1);
            player.addNote(new Pitch('G').toMidiNote(), 10, 1);
            player.addNote(new Pitch('F').toMidiNote(), 11, 1);
            player.addNote(new Pitch('E').toMidiNote(), 12, 1);
            player.addNote(new Pitch('D').toMidiNote(), 13, 1);
            player.addNote(new Pitch('C').toMidiNote(), 14, 1);
            
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }

    }
}
