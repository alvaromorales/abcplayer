package ast;

import player.RationalNumber;

/**
 * Class representing a quadruplet
 */
public class Quadruplet implements NoteElement, Tuple {
    private NoteElement first;
    private NoteElement second;
    private NoteElement third;
    private NoteElement fourth;
    
    /**
     * Creates a Quadruplet object
     * @param first the first NoteElement
     * @param second the second NoteElement
     * @param third the third NoteElement
     * @param fourth the fourth NoteElement
     */
    public Quadruplet(NoteElement first, NoteElement second, NoteElement third, NoteElement fourth) {
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
     * Gets the duration of the quadruplet
     * A quadruplet plays 4 notes in the time of 3 notes
     * @return the duration of the quadruplet
     */
    public RationalNumber getDuration() {
        return first.getDuration().add(second.getDuration().add(third.getDuration().add(fourth.getDuration())));
    }

    /**
     * Accepts a visitor
     */
    @Override
    public <E> E accept(Visitor<E> v) {
        return v.visit(this);
    }
    
    /**
     * Checks if an Quadruplet is equal to another Quadruplet
     * @param o the Object to compare to
     * @return true if equal, else false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) { // quick check
            return true;
        }

        if (o == null || !(o instanceof Quadruplet)) {
            return false;
        }

        Quadruplet other = (Quadruplet)o;
        return this.first.equals(other.first) && this.second.equals(other.second) && this.third.equals(other.third) && this.fourth.equals(other.fourth);
    }

    /**
     * Gets the string representation of a Quadruplet
     * @return the string representation of a Quadruplet
     */
    @Override
    public String toString() {
        return "Quadruplet [first=" + first + ", second=" + second
                + ", third=" + third + ", fourth=" + fourth + "]";
    }
    
}