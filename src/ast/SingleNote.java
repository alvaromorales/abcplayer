package ast;

import player.RationalNumber;

/**
 * Class representing a SingleNote
 */
public class SingleNote implements NoteElement {
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

    /**
     * Checks if an SingleNote is equal to another SingleNote
     * @param o the Object to compare to
     * @return true if equal, else false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) { // quick check
            return true;
        }

        if (o == null || !(o instanceof SingleNote)) {
            return false;
        }

        SingleNote other = (SingleNote)o;
        return this.pitch == other.pitch && this.accidental == other.accidental && this.octave == other.octave && this.duration.equals(other.duration);
    }

    /**
     * Gets the string representation of a SingleNote
     * @return the string representation of a SingleNote
     */
    @Override
    public String toString() {
        return "SingleNote [pitch=" + pitch + ", duration=" + duration
                + ", octave=" + octave + ", accidental=" + accidental + "]";
    }
}