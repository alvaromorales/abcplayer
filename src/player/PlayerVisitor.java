package player;
import java.util.ArrayList;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import player.AST.*;
import player.NoteElement.Visitor;
import sound.Pitch;
import sound.SequencePlayer;

public class PlayerVisitor implements Visitor<Void> {
    private SequencePlayer player;
    private int ticksPerQuarterNote;
    private RationalNumber defaultNoteLength;
    private int currentTick = 0;
    
    /**
     * Creates a PlayerVisitor object
     * @param beatsPerMinute the beats per minute specified in the header of the abc song
     * @param ticksPerQuarterNote the number of ticks per quarter note, calculated by the DurationVisitor
     */
    public PlayerVisitor(int ticksPerQuarterNote, int tempo, RationalNumber defaultNoteLength) {
        try {
            this.ticksPerQuarterNote = 4*ticksPerQuarterNote;
            this.defaultNoteLength = defaultNoteLength;
            int beatsPerMinute = (int)(tempo * defaultNoteLength.getValue() * 4);
            player = new SequencePlayer(beatsPerMinute, ticksPerQuarterNote);
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Adds the SingleNote to the player
     * @param s the SingleNote to add
     */
    @Override
    public Void visit(SingleNote s) {
        //add note
        player.addNote(new Pitch(s.getPitch()).transpose(Pitch.OCTAVE*s.getOctave()+s.getAccidental()).toMidiNote(), currentTick, (int)(ticksPerQuarterNote*s.getDuration().mul(defaultNoteLength).getValue()));
        
        //advance song
        currentTick += (int)(s.getDuration().mul(defaultNoteLength).getValue()*ticksPerQuarterNote);
        return null;
    }

    /**
     * Adds a Rest to the player
     * @param r the Rest to add
     */
    @Override
    public Void visit(Rest r) {
        //advance song
        currentTick += (int)(r.getDuration().mul(defaultNoteLength).getValue()*ticksPerQuarterNote);        
        return null;
    }

    /**
     * Adds a Chord to the player
     * @param c the Chord to add
     */
    @Override
    public Void visit(Chord c) {        
        //add notes
        for (NoteElement n: c.getNotes()){
            n.accept(this);
            //rewind song, all notes in the chord are played at the same time
            currentTick -= (int)(n.getDuration().mul(defaultNoteLength).getValue()*ticksPerQuarterNote);
        }
        
        //advance song
        currentTick += (int)(c.getDuration().mul(defaultNoteLength).getValue()*ticksPerQuarterNote);
        return null;
    }
    
    /**
     * Adds notes in a tuple to a song
     * @param notes the notes to add
     */
    public void addTupleNotes(ArrayList<NoteElement> notes) {
        for (NoteElement n: notes) {
            n.accept(this);
        }
    }
    
    /**
     * Adds a Duplet to the player
     * @param d the Duplet to add
     */
    @Override
    public Void visit(Duplet d) {
        ArrayList<NoteElement> notes = new ArrayList<NoteElement>();
        notes.add(d.getFirst());
        notes.add(d.getSecond());
        
        //add notes
        addTupleNotes(notes);
        
        return null;
    }

    /**
     * Adds a Triplet to the player
     * @param t the Triplet to add
     */
    @Override
    public Void visit(Triplet t) {
        ArrayList<NoteElement> notes = new ArrayList<NoteElement>();
        notes.add(t.getFirst());
        notes.add(t.getSecond());
        notes.add(t.getThird());
                
        //add notes
        addTupleNotes(notes);
        
        return null;
    }

    /**
     * Adds a Quadruplet to the player
     * @param q the Quadruplet to add
     */
    @Override
    public Void visit(Quadruplet q) {
        ArrayList<NoteElement> notes = new ArrayList<NoteElement>();
        notes.add(q.getFirst());
        notes.add(q.getSecond());
        notes.add(q.getThird());
        
        //add notes
        addTupleNotes(notes);

        return null;
    }

    /**
     * Adds a Voice to the player
     * @param v the Voice to add
     */
    @Override
    public Void visit(Voice v) {
        for (NoteElement n: v.getNotes()) {
            n.accept(this);
        }
        
        return null;
    }

    /**
     * Adds a Song to the player
     * @param s the Song to add
     */
    @Override
    public Void visit(Song s) {
        for (Voice v : s.getVoices()){
            currentTick = 0;
            v.accept(this);
        }
        
        return null;
    }
    
    /**
     * Gets the SequencePlayer
     * @return the SequencePlayer
     */
    public SequencePlayer getPlayer() {
        return player;
    }

}
