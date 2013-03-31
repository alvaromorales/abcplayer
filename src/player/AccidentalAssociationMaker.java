package player;

import java.util.HashMap;
import java.util.Map;

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
    		this.setAccidental(Note, 0);
    	this.changelog.putAll(accidentals);
    }
    
    /**
     * Public Constructor
     * Makes a Note -> Note+Accidental association Map for a given key.
     * @param Key
     */
    
	public AccidentalAssociationMaker(String Key){
		this.init();
		
    	switch(Key){ //assigns keys to notes according to the key signature
    	case "Cm":
    	case "A":
    	case "Gm":
    		this.setAccidental('F', +1);
    	case "E":
    		this.setAccidental('F',+1);
    	case "Dm":
    		this.setAccidental('F', +1);
    		this.setAccidental('C', +1);
    	case "B":
    		this.setAccidental('F', +1);
    		this.setAccidental('C', +1);
    	case "Am":
    		this.setAccidental('F', +1);
    		this.setAccidental('C', +1);
    		this.setAccidental('G', +1);
    	case "F#":
    		this.setAccidental('F', +1);
    		this.setAccidental('C', +1);
    		this.setAccidental('G', +1);
    	case "Em":
    		this.setAccidental('F', +1);
    		this.setAccidental('C', +1);
    		this.setAccidental('G', +1);
    		this.setAccidental('D', +1);
    	case "C#":
    		this.setAccidental('F', +1);
    		this.setAccidental('C', +1);
    		this.setAccidental('G', +1);
    		this.setAccidental('D', +1);
    	case "Bm":
    		this.setAccidental('F', +1);
    		this.setAccidental('C', +1);
    		this.setAccidental('G', +1);
    		this.setAccidental('D', +1);
    		this.setAccidental('A', +1);
    	case "G#":
    		this.setAccidental('F', +1);
    		this.setAccidental('C', +1);
    		this.setAccidental('G', +1);
    		this.setAccidental('D', +1);
    		this.setAccidental('A', +1);
    	case "F#m":
    		this.setAccidental('F', +1);
    		this.setAccidental('C', +1);
    		this.setAccidental('G', +1);
    		this.setAccidental('D', +1);
    		this.setAccidental('A', +1);
    		this.setAccidental('E', +1);
    	case "D#":
    		this.setAccidental('F', +1);
    		this.setAccidental('C', +1);
    		this.setAccidental('G', +1);
    		this.setAccidental('D', +1);
    		this.setAccidental('A', +1);
    		this.setAccidental('E', +1);
    	case "C#m":
    		this.setAccidental('F', +1);
    		this.setAccidental('C', +1);
    		this.setAccidental('G', +1);
    		this.setAccidental('D', +1);
    		this.setAccidental('A', +1);
    		this.setAccidental('E', +1);
    		this.setAccidental('B', +1);
    	case "A#":
    		this.setAccidental('F', +1);
    		this.setAccidental('C', +1);
    		this.setAccidental('G', +1);
    		this.setAccidental('D', +1);
    		this.setAccidental('A', +1);
    		this.setAccidental('E', +1);
    		this.setAccidental('B', +1);
    	// Starting flats
    	case "Fm":
    		this.setAccidental('B', -1);
    	case "D":
    		this.setAccidental('B', -1);
    	case "Bbm":
    		this.setAccidental('B', -1);
    		this.setAccidental('E', -1);
    	case "G":
    		this.setAccidental('B', -1);
    		this.setAccidental('E', -1);
    	case "Ebm":
    		this.setAccidental('B', -1);
    		this.setAccidental('E', -1);
    		this.setAccidental('A', -1);
    	case "C":
    		this.setAccidental('B', -1);
    		this.setAccidental('E', -1);
    		this.setAccidental('A', -1);
    	case "Abm":
    		this.setAccidental('B', -1);
    		this.setAccidental('E', -1);
    		this.setAccidental('A', -1);
    		this.setAccidental('D', -1);
    	case "F":
    		this.setAccidental('B', -1);
    		this.setAccidental('E', -1);
    		this.setAccidental('A', -1);
    		this.setAccidental('D', -1);
    	case "Dbm":
    		this.setAccidental('B', -1);
    		this.setAccidental('E', -1);
    		this.setAccidental('A', -1);
    		this.setAccidental('D', -1);
    		this.setAccidental('G', -1);
    	case "Bb":
    		this.setAccidental('B', -1);
    		this.setAccidental('E', -1);
    		this.setAccidental('A', -1);
    		this.setAccidental('D', -1);
    		this.setAccidental('G', -1);
    	case "Gbm":
    		this.setAccidental('B', -1);
    		this.setAccidental('E', -1);
    		this.setAccidental('A', -1);
    		this.setAccidental('D', -1);
    		this.setAccidental('G', -1);
    		this.setAccidental('C', -1);
    	case "Eb":
    		this.setAccidental('B', -1);
    		this.setAccidental('E', -1);
    		this.setAccidental('A', -1);
    		this.setAccidental('D', -1);
    		this.setAccidental('G', -1);
    		this.setAccidental('C', -1);
    	case "Cbm":
    		this.setAccidental('B', -1);
    		this.setAccidental('E', -1);
    		this.setAccidental('A', -1);
    		this.setAccidental('D', -1);
    		this.setAccidental('G', -1);
    		this.setAccidental('C', -1);
    		this.setAccidental('F', -1);
    	case "Ab":
    		this.setAccidental('B', -1);
    		this.setAccidental('E', -1);
    		this.setAccidental('A', -1);
    		this.setAccidental('D', -1);
    		this.setAccidental('G', -1);
    		this.setAccidental('C', -1);
    		this.setAccidental('F', -1);
    	}
    	
    	this.changelog.putAll(accidentals); //copy accidentals to changelog
	}
}
