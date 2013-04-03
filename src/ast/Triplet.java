package ast;

import player.RationalNumber;

/**
 * Class representing a Triplet
 */
public class Triplet implements NoteElement, Tuple {
    private NoteElement first;
    private NoteElement second;
    private NoteElement third;
    
    /**
     * Creates a Triplet object
     * @param first the first NoteElement
     * @param second the second NoteElement
     * @param third the third NoteElement
     */
    public Triplet(NoteElement first, NoteElement second, NoteElement third) {
        this.first = first;
        this.second = second;
        this.third = third;
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
     * Gets the duration of the triplet
     * A triplet plays 3 notes in the time of 2
     * @return the duration of the triplet
     */
    public RationalNumber getDuration() {
        return first.getDuration().add(second.getDuration().add(third.getDuration()));
    }

    /**
     * Accepts a visitor
     */
    @Override
    public <E> E accept(Visitor<E> v) {
        return v.visit(this);
    }
    
    /**
     * Checks if an Triplet is equal to another Triplet
     * @param o the Object to compare to
     * @return true if equal, else false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) { // quick check
            return true;
        }

        if (o == null || !(o instanceof Triplet)) {
            return false;
        }

        Triplet other = (Triplet)o;
        return this.first.equals(other.first) && this.second.equals(other.second) && this.third.equals(other.third);
    }

    /**
     * Gets the string representation of a Triplet
     * @return the string representation of a Triplet
     */
    @Override
    public String toString() {
        return "Triplet [first=" + first + ", second=" + second
                + ", third=" + third + "]";
    }
    
}