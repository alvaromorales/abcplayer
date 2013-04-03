package ast;

import player.RationalNumber;

/**
 * Class representing a Rest
 */
public class Rest implements NoteElement {
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
    
    /**
     * Checks if an Rest is equal to another Rest
     * @param o the Object to compare to
     * @return true if equal, else false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) { // quick check
            return true;
        }

        if (o == null || !(o instanceof Rest)) {
            return false;
        }

        Rest other = (Rest)o;
        return this.duration.equals(other.duration);
    }

    /**
     * Gets the string representation of a Rest
     * @return the string representation of a Rest
     */
    @Override
    public String toString() {
        return "Rest [duration=" + duration + "]";
    }
    
}
