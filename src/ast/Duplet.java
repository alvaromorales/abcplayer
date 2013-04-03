package ast;

import player.RationalNumber;

/**
 * Class representing a Duplet
 */
public class Duplet implements NoteElement, Tuple {
    private NoteElement first;
    private NoteElement second;
    
    /**
     * Creates a Duplet object
     * @param first the first NoteElement
     * @param second the second NoteElement
     */
    public Duplet(NoteElement first, NoteElement second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Gets the duration of the duplet
     * A duplet plays 2 notes in the duration of 3
     * @return the duration of the duplet
     */
    public RationalNumber getDuration() {
        return first.getDuration().add(second.getDuration());
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
     * Accepts a visitor
     */
    @Override
    public <E> E accept(Visitor<E> v) {
        return v.visit(this);
    }
    
    /**
     * Checks if an Duplet is equal to another Duplet
     * @param o the Object to compare to
     * @return true if equal, else false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) { // quick check
            return true;
        }

        if (o == null || !(o instanceof Duplet)) {
            return false;
        }

        Duplet other = (Duplet)o;
        return this.first.equals(other.first) && this.second.equals(other.second);
    }

    /**
     * Gets the string representation of a Duplet
     * @return the string representation of a Duplet
     */
    @Override
    public String toString() {
        return "Duplet [first=" + first + ", second=" + second + "]";
    }
    
}