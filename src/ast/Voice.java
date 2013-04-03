package ast;

import java.util.ArrayList;
import java.util.List;
import player.RationalNumber;

/**
 * Class representing a voice
 */
public class Voice implements NoteElement {
    private List<NoteElement> notes = new ArrayList<NoteElement>();
    private String name;
    
    /**
     * Creates a Voice object
     * @param name, the name of the voice
     */
    public Voice(String name) {
        this.name=name;
    }
    
    /**
     * Creates an anonymous Voice object
     */
    public Voice(){
        this.name = null;
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
     * Gets the name of the voice
     * @return the name of the voice
     */
    public String getName() {
        return name;
    }
    
    /**
     * Accepts a visitor
     */
    @Override
    public <E> E accept(Visitor<E> v) {
        return v.visit(this);
    }

    /**
     * Gets the RationalNumber duration of all the NoteElements in the Voice
     * @return the RationalNumber duration of all the NoteElements in the Voice
     */
    @Override
    public RationalNumber getDuration() {
        RationalNumber result = new RationalNumber(0, 1);
        for (NoteElement n: notes)
            result = result.add(n.getDuration());
        return result;
    }
    
    /**
     * Checks if an Voice is equal to another Voice
     * @param o the Object to compare to
     * @return true if equal, else false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Voice other = (Voice) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (notes == null) {
            if (other.notes != null)
                return false;
        } else if (!notes.equals(other.notes))
            return false;
        return true;
    }       

    /**
     * Gets the string representation of a voice
     * @return the string representation of a voice
     */
    @Override
    public String toString() {
        return "Voice [name=" + name + ",notes=" + notes + "]";
    }
    
}