package player;

import java.util.HashMap;
import java.util.Map;

/**
 * Class 
 */
public class AccidentalAssociationMaker {

    private Map<Character, Integer> accidentals = new HashMap<Character, Integer>();
    private Map<Character, Integer> changelog = new HashMap<Character, Integer>();

    /**
     * Adds an accidental assignment in the accidentals hashmap ("A" -> +1 (flat) )
     * @param Note to assign an accidental to (i.e: "A")
     * @param 0, +1 or -1, accidental to assign to that note (i.e: +1)
     */
    public void setAccidental(char Note, int acc){
        accidentals.put(Note,acc);
    }

    /**
     * Returns the accidental for that note for this specific Voice.
     * @param Note the Note to look-up in the accidentals map
     * @return accidental, +1, 0 or -1, the change in pitch of that note
     */
    public int getAccidental(char Note){
        return accidentals.get(Note);
    }

    /**
     * Reverts the accidentals map to the default values for the key signature of the piece.
     */
    public void revert(){
        accidentals.putAll(changelog);
    }

    /**
     * Initializes the hashmap
     */
    public void init(){
        for(char Note='A';Note<='G';Note++)
            setAccidental(Note, 0);
        this.changelog.putAll(accidentals);
    }

    /**
     * Public Constructor
     * Makes a Note -> Note+Accidental association Map for a given key.
     * @param key
     */
    public AccidentalAssociationMaker(String key){
        this.init();


        if (key.equals("Cm") || key.equals("A") || key.equals("Gm")) {
            setAccidental('F', +1);
        } else if (key.equals("E")) {
            setAccidental('F',+1);
        } else if (key.equals("Dm")) {
            setAccidental('F', +1);
            setAccidental('C', +1);
        } else if (key.equals("B")) {
            setAccidental('F', +1);
            setAccidental('C', +1);
        } else if (key.equals("Am")) {
            setAccidental('F', +1);
            setAccidental('C', +1);
            setAccidental('G', +1);
        } else if (key.equals("F#")) {
            setAccidental('F', +1);
            setAccidental('C', +1);
            setAccidental('G', +1);
        } else if (key.equals("Em")) {
            setAccidental('F', +1);
            setAccidental('C', +1);
            setAccidental('G', +1);
            setAccidental('D', +1);
        } else if (key.equals("C#")){
            setAccidental('F', +1);
            setAccidental('C', +1);
            setAccidental('G', +1);
            setAccidental('D', +1);
        } else if (key.equals("Bm")) {
            setAccidental('F', +1);
            setAccidental('C', +1);
            setAccidental('G', +1);
            setAccidental('D', +1);
            setAccidental('A', +1);
        } else if (key.equals("G#")) {
            setAccidental('F', +1);
            setAccidental('C', +1);
            setAccidental('G', +1);
            setAccidental('D', +1);
            setAccidental('A', +1);
        } else if (key.equals("F#m")) {
            setAccidental('F', +1);
            setAccidental('C', +1);
            setAccidental('G', +1);
            setAccidental('D', +1);
            setAccidental('A', +1);
            setAccidental('E', +1);
        } else if (key.equals("D#")) {
            setAccidental('F', +1);
            setAccidental('C', +1);
            setAccidental('G', +1);
            setAccidental('D', +1);
            setAccidental('A', +1);
            setAccidental('E', +1);
        } else if (key.equals("C#m")) {
            setAccidental('F', +1);
            setAccidental('C', +1);
            setAccidental('G', +1);
            setAccidental('D', +1);
            setAccidental('A', +1);
            setAccidental('E', +1);
            setAccidental('B', +1);
        } else if (key.equals("A#")) {
            setAccidental('F', +1);
            setAccidental('C', +1);
            setAccidental('G', +1);
            setAccidental('D', +1);
            setAccidental('A', +1);
            setAccidental('E', +1);
            setAccidental('B', +1);
        } else if (key.equals("Fm")) {
            setAccidental('B', -1);
        } else if (key.equals("D")) {
            setAccidental('B', -1);
        } else if (key.equals("Bbm")) {
            setAccidental('B', -1);
            setAccidental('E', -1);
        } else if (key.equals("G")) {
            setAccidental('B', -1);
            setAccidental('E', -1);
        } else if (key.equals("Ebm")) {
            setAccidental('B', -1);
            setAccidental('E', -1);
            setAccidental('A', -1);
        } else if (key.equals("C")) {
            setAccidental('B', -1);
            setAccidental('E', -1);
            setAccidental('A', -1);
        } else if (key.equals("Abm")) {
            setAccidental('B', -1);
            setAccidental('E', -1);
            setAccidental('A', -1);
            setAccidental('D', -1);
        } else if (key.equals("F")) {
            setAccidental('B', -1);
            setAccidental('E', -1);
            setAccidental('A', -1);
            setAccidental('D', -1);
        } else if (key.equals("Dbm")) {
            setAccidental('B', -1);
            setAccidental('E', -1);
            setAccidental('A', -1);
            setAccidental('D', -1);
            setAccidental('G', -1);
        } else if (key.equals("Bb")) {
            setAccidental('B', -1);
            setAccidental('E', -1);
            setAccidental('A', -1);
            setAccidental('D', -1);
            setAccidental('G', -1);
        } else if (key.equals("Gbm")) {
            setAccidental('B', -1);
            setAccidental('E', -1);
            setAccidental('A', -1);
            setAccidental('D', -1);
            setAccidental('G', -1);
            setAccidental('C', -1);
        } else if (key.equals("Eb")) {
            setAccidental('B', -1);
            setAccidental('E', -1);
            setAccidental('A', -1);
            setAccidental('D', -1);
            setAccidental('G', -1);
            setAccidental('C', -1);
        } else if (key.equals("Cbm")) {
            setAccidental('B', -1);
            setAccidental('E', -1);
            setAccidental('A', -1);
            setAccidental('D', -1);
            setAccidental('G', -1);
            setAccidental('C', -1);
            setAccidental('F', -1);
        } else if (key.equals("Ab")) {
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
}
