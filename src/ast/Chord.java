package ast;

import java.util.ArrayList;
import player.RationalNumber;

/**
 * Class representing a Chord
 */
public class Chord implements NoteElement {
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
    
    /**
     * Checks if an Chord is equal to another Chord
     * @param o the Object to compare to
     * @return true if equal, else false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) { // quick check
            return true;
        }

        if (o == null || !(o instanceof Chord)) {
            return false;
        }

        Chord other = (Chord)o;
        return this.duration.equals(other.duration) && this.notes.equals(other.notes);
    }

    /**
     * Gets the string representation of a Chord
     * @return the string representation of a Chord
     */
    @Override
    public String toString() {
        return "Chord [duration=" + duration + ", notes=" + notes + "]";
    }
    
}