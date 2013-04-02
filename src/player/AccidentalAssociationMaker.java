package player;

import java.util.HashMap;
import java.util.Map;

/**
 * Class 
 */
public class AccidentalAssociationMaker {

    private Map<Character, Integer> accidentals = new HashMap<Character, Integer>(0);
    private Map<Character, Integer> changelog = new HashMap<Character, Integer>(0);

    /**
     * Adds an accidental assignment in the accidentals hashmap ("A" -> +1 (flat) )
     * @param pitch to assign an accidental to (i.e: "A")
     * @param 0, +1 or -1, accidental to assign to that note (i.e: +1)
     */
    public void setAccidental(char pitch, int acc){
        accidentals.put(pitch,acc);
    }

    /**
     * Returns the accidental for that note for this specific Voice.
     * @param pitch the pitch to look-up in the accidentals map
     * @return accidental, +1, 0 or -1, the change in pitch of that note
     */
    public int getAccidental(char pitch){
        return accidentals.get(pitch);
    }

    /**
     * Reverts the accidentals map to the default values for the key signature of the piece.
     */
    public void revert(){
        accidentals.putAll(changelog);
    }

    /**
     * Initializes the hashmap with no sharps or flats for all notes.
     */
    public AccidentalAssociationMaker(){
        for(char pitch='A';pitch<='G';pitch++)
            setAccidental(pitch, 0);
        this.changelog.putAll(accidentals);
    }

    /**
     * Public Constructor
     * Makes a Note -> Note+Accidental association Map for a given key.
     * @param key
     */
    public AccidentalAssociationMaker(String key){
        this(); 		//calls the no-argument constructor

        if (key.equals("G") || key.equals("Em"))
            setAccidental('F', +1);
        else if (key.equals("D") || key.equals("Bm")) {
            setAccidental('F', +1);
            setAccidental('C', +1);
        }  else if (key.equals("A") || key.equals("F#m")) {
            setAccidental('F', +1);
            setAccidental('C', +1);
            setAccidental('G', +1);
        }  else if (key.equals("E") || key.equals("C#m")) {
            setAccidental('F', +1);
            setAccidental('C', +1);
            setAccidental('G', +1);
            setAccidental('D', +1);
        }  else if (key.equals("B") || key.equals("G#m")) {
            setAccidental('F', +1);
            setAccidental('C', +1);
            setAccidental('G', +1);
            setAccidental('D', +1);
            setAccidental('A', +1);
        }  else if (key.equals("F#") || key.equals("D#m")) {
            setAccidental('F', +1);
            setAccidental('C', +1);
            setAccidental('G', +1);
            setAccidental('D', +1);
            setAccidental('A', +1);
            setAccidental('E', +1);
        } else if (key.equals("C#") || key.equals("A#m")) {
            setAccidental('F', +1);
            setAccidental('C', +1);
            setAccidental('G', +1);
            setAccidental('D', +1);
            setAccidental('A', +1);
            setAccidental('E', +1);
            setAccidental('B', +1);
        } 
        // begin looking into minor scale keys
        else if (key.equals("F") || key.equals("Dm"))
            setAccidental('B', -1);
        else if (key.equals("Bb") || key.equals("Gm")) {
            setAccidental('B', -1);
            setAccidental('E', -1);
        } else if (key.equals("Eb") || key.equals("Cm")) {
            setAccidental('B', -1);
            setAccidental('E', -1);
            setAccidental('A', -1);
        } else if (key.equals("Ab") || key.equals("Fm")) {
            setAccidental('B', -1);
            setAccidental('E', -1);
            setAccidental('A', -1);
            setAccidental('D', -1);
        } else if (key.equals("Db") || key.equals("Bbm")) {
            setAccidental('B', -1);
            setAccidental('E', -1);
            setAccidental('A', -1);
            setAccidental('D', -1);
            setAccidental('G', -1);
        } else if (key.equals("Gb") || key.equals("Ebm")) {
            setAccidental('B', -1);
            setAccidental('E', -1);
            setAccidental('A', -1);
            setAccidental('D', -1);
            setAccidental('G', -1);
            setAccidental('C', -1);
        } else if (key.equals("Cb") || key.equals("Abm")) {
            setAccidental('B', -1);
            setAccidental('E', -1);
            setAccidental('A', -1);
            setAccidental('D', -1);
            setAccidental('G', -1);
            setAccidental('C', -1);
            setAccidental('F', -1);
        }

        this.changelog.putAll(accidentals); //copy accidentals to changelog
    }
    
    /**
     * Checks if an AccidentalAssociationMaker is equal to another AccidentalAssociationMaker
     * @param o the Object to compare to
     * @return true if equal, else false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) { // quick check
            return true;
        }

        if (o == null || !(o instanceof AccidentalAssociationMaker)) {
            return false;
        }

        AccidentalAssociationMaker other = (AccidentalAssociationMaker)o;
        return this.accidentals.equals(other.accidentals);
    }

    /**
     * Gets the string representation of the AccidentalAssociationMaker
     * @return the string representation of the AccidentalAssociationMaker
     */
    @Override
    public String toString() {
        return "AccidentalAssociationMaker [accidentals=" + accidentals + "]";
    }

}
