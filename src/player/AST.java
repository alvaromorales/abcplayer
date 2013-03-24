package player;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the AST for the abcplayer
 */
public class AST {

    /**
     * Class representing a SingleNote
     */
    public static class SingleNote implements NoteElement {
        private char pitch;
        private RationalNumber duration;
        private int octave;
        private int accidental; // sharp = +1, flat = -1, neutral = 0
        
        /**
         * Creates a new SingleNote object
         * @param pitch the pitch of the note, e.g. 'A'
         * @param duration the RationalNumber duration of the note
         * @param octave the octave of the note
         * @param accidental the accidental of the note
         */
        public SingleNote(char pitch, RationalNumber duration, int octave, int accidental) {
            this.pitch = pitch;
            this.duration = duration;
            this.octave = octave;
            this.accidental = accidental;
        }

        /**
         * Gets the pitch of the note
         * @return the pitch of the note
         */
        public char getPitch() {
            return pitch;
        }

        /**
         * Gets the duration of the note
         * @return the duration of the note
         */
        public RationalNumber getDuration() {
            return duration;
        }

        /**
         * Gets the octave of the note
         * @return the octave of the note
         */
        public int getOctave() {
            return octave;
        }

        /**
         * Gets the accidental of the note
         * @return the accidental of the note
         */
        public int getAccidental() {
            return accidental;
        }

        /**
         * Accepts a visitor
         */
        @Override
        public <E> E accept(Visitor<E> v) {
            return v.visit(this);
        }        
        
    }
    
    /**
     * Class representing a Rest
     */
    public static class Rest implements NoteElement {
        private RationalNumber duration;
        
        /**
         * Creates a new Rest object
         * @param duration the duration of the rest
         */
        public Rest(RationalNumber duration) {
            this.duration = duration;
        }

        /**
         * Gets the duration of the rest
         * @return the duration of the rest
         */
        public RationalNumber getDuration() {
            return duration;
        }

        /**
         * Accepts a visitor
         */
        @Override
        public <E> E accept(Visitor<E> v) {
            return v.visit(this);
        }
        
    }
    
    /**
     * Class representing a Chord
     */
    public static class Chord implements NoteElement {
        private RationalNumber duration;
        private ArrayList<NoteElement> notes;
        
        /**
         * Creates a new Chord object
         * @param duration the duration of the chord
         * @param notes the notes in the chord, to be played simultaneously
         */
        public Chord(RationalNumber duration, ArrayList<NoteElement> notes) {
            this.duration = duration;
            this.notes = notes;
        }

        /**
         * Gets the duration of the chord
         * @return the duration of the chord
         */
        public RationalNumber getDuration() {
            return duration;
        }

        /**
         * Gets the notes of the chord
         * @return the notes of the chord
         */
        public ArrayList<NoteElement> getNotes() {
            return notes;
        }

        /**
         * Accepts a visitor
         */
        @Override
        public <E> E accept(Visitor<E> v) {
            return v.visit(this);
        }
        
    }
    
    /**
     * Class representing a Duplet
     */
    public static class Duplet implements NoteElement, Tuple {
        private NoteElement first;
        private NoteElement second;
        private RationalNumber noteDuration;
        
        /**
         * Creates a Duplet object
         * @param first the first NoteElement
         * @param second the second NoteElement
         * @param noteDuration the duration of a note, which is equal across all the notes in a tuple
         */
        public Duplet(NoteElement first, NoteElement second, RationalNumber noteDuration) {
            this.first = first;
            this.second = second;
            this.noteDuration = noteDuration;
        }

        /**
         * Gets the duration of the duplet
         * A duplet plays 2 notes in the duration of 3
         * @return the duration of the duplet
         */
        public RationalNumber getDuration() {
            return noteDuration.mul(new RationalNumber(3, 2)).mulC(2);
        }
        
        /**
         * Gets the first note in the duplet
         * @return the first note in the duplet
         */
        public NoteElement getFirst() {
            return first;
        }

        /**
         * Gets the second note in the duplet
         * @return the second note in the duplet
         */
        public NoteElement getSecond() {
            return second;
        }

        /**
         * Gets the duration of a single note in the duplet
         * The duration is the same across all notes in the duplet
         * @return the duration of a single note in the duplet
         */
        public RationalNumber getNoteDuration() {
            return noteDuration;
        }

        /**
         * Accepts a visitor
         */
        @Override
        public <E> E accept(Visitor<E> v) {
            return v.visit(this);
        }
    }
    
    /**
     * Class representing a Triplet
     */
    public static class Triplet implements NoteElement, Tuple {
        private NoteElement first;
        private NoteElement second;
        private NoteElement third;
        private RationalNumber noteDuration;
        
        /**
         * Creates a Triplet object
         * @param first the first NoteElement
         * @param second the second NoteElement
         * @param third the third NoteElement
         * @param noteDuration the duration of a note, which is equal across all the notes in a tuple
         */
        public Triplet(NoteElement first, NoteElement second, NoteElement third, RationalNumber noteDuration) {
            this.first = first;
            this.second = second;
            this.third = third;
            this.noteDuration = noteDuration;
        }

        /**
         * Gets the first note in the triplet
         * @return the first note in the triplet
         */
        public NoteElement getFirst() {
            return first;
        }

        /**
         * Gets the second note in the triplet
         * @return the second note in the triplet
         */
        public NoteElement getSecond() {
            return second;
        }

        /**
         * Gets the third note in the triplet
         * @return the third note in the triplet
         */
        public NoteElement getThird() {
            return third;
        }

        /**
         * Gets the duration of a single note in the triplet
         * The duration is the same across all notes in the triplet
         * @return the duration of a single note in the triplet
         */
        public RationalNumber getNoteDuration() {
            return noteDuration;
        }
        
        /**
         * Gets the duration of the triplet
         * A triplet plays 3 notes in the time of 2
         * @return the duration of the triplet
         */
        public RationalNumber getDuration() {
            return noteDuration.mul(new RationalNumber(2, 3)).mulC(3);
        }

        /**
         * Accepts a visitor
         */
        @Override
        public <E> E accept(Visitor<E> v) {
            return v.visit(this);
        }
        
    }
    
    /**
     * Class representing a quadruplet
     */
    public static class Quadruplet implements NoteElement, Tuple {
        private NoteElement first;
        private NoteElement second;
        private NoteElement third;
        private NoteElement fourth;
        private RationalNumber noteDuration;
        
        /**
         * Creates a Quadruplet object
         * @param first the first NoteElement
         * @param second the second NoteElement
         * @param third the third NoteElement
         * @param fourth the fourth NoteElement
         * @param noteDuration the duration of a note, which is equal across all the notes in a tuple
         */
        public Quadruplet(NoteElement first, NoteElement second, NoteElement third, NoteElement fourth, RationalNumber noteDuration) {
            this.first = first;
            this.second = second;
            this.third = third;
            this.fourth = fourth;
        }

        /**
         * Gets the first note in the quadruplet
         * @return the first note in the quadruplet
         */
        public NoteElement getFirst() {
            return first;
        }

        /**
         * Gets the second note in the quadruplet
         * @return the second note in the quadruplet
         */
        public NoteElement getSecond() {
            return second;
        }

        /**
         * Gets the third note in the quadruplet
         * @return the third note in the quadruplet
         */
        public NoteElement getThird() {
            return third;
        }

        /**
         * Gets the fourth note in the quadruplet
         * @return the fourth note in the quadruplet
         */
        public NoteElement getFourth() {
            return fourth;
        }

        /**
         * Gets the duration of a single note in the quadruplet
         * The duration is the same across all notes in the quadruplet
         * @return the duration of a single note in the quadruplet
         */
        public RationalNumber getNoteDuration() {
            return noteDuration;
        }
        
        /**
         * Gets the duration of the quadruplet
         * A quadruplet plays 4 notes in the time of 3 notes
         * @return the duration of the quadruplet
         */
        public RationalNumber getDuration() {
            return noteDuration.mul(new RationalNumber(3, 4)).mulC(4);
        }

        /**
         * Accepts a visitor
         */
        @Override
        public <E> E accept(Visitor<E> v) {
            return v.visit(this);
        }
        
    }
    
    /**
     * Class representing a voice
     */
    public static class Voice implements NoteElement {
        private List<NoteElement> notes = new ArrayList<NoteElement>();
        
        /**
         * Creates a Voice object
         */
        public Voice() {
            
        }
        
        /**
         * Adds a NoteElement to the voice
         * @param e the NoteElement to add
         */
        public void addNote(NoteElement e) {
            notes.add(e);
        }
        
        /**
         * Gets the notes in the voice
         * @return the notes in the voice
         */
        public List<NoteElement> getNotes() {
            return notes;
        }

        /**
         * Accepts a visitor
         */
        @Override
        public <E> E accept(Visitor<E> v) {
            return v.visit(this);
        }
    }
    
    /**
     * Class representing a song
     */
    public static class Song implements NoteElement {
        private List<Voice> voices = new ArrayList<Voice>();
        
        /**
         * Creates a Song object
         */
        public Song() {
            
        }
        
        /**
         * Adds a Voice to the song
         * @param v the Voice to add
         */
        public void addVoice(Voice v) {
            voices.add(v);
        }

        /**
         * Accepts a visitor
         */
        @Override
        public <E> E accept(Visitor<E> v) {
            return v.visit(this);
        }
        
        /**
         * Gets the voices in the song
         * @return the voices in the song
         */
        public List<Voice> getVoices() {
            return voices;
        }
    }

}
