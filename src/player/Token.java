package player;


/**
 * A token is a lexical item that the parser uses.
 */
public class Token {
    /**
     * All the types of tokens that can be made.
     */
    public static enum Type {COMPOSER, KEY, LENGTH, METER, TEMPO, TITLE, INDEX, KEYNOTE,  
                            TIME, REST, CHORD_START, CHORD_END, DUPLET_START, TRIPLET_START,
                            QUAD_START, BAR, REPEAT_START, REPEAT_END, REPEAT_NUMBER, 
                            END_LINE, VOICE};
                            
    
    private String value;
    private Type type;
    private int octave;
    private int accidental;
    
    
    /**
     * Gets the octave of the Token
     * @return the octave
     */
    public int getOctave() {
        return this.octave;
    }


    
    /**
     * Gets the accidental of the Token
     * @return the accidental
     */
    public int getAccidential() {
        return this.accidental;
    }

    
    /**
     * Gets the type of the Token
     * @return the type
     */
    public Type getType() {
        return this.type;
    }
    
    
    /**
     * Gets the value of the Token
     * @return the value
     */
    public String getValue() {
        return this.value;
    }
    
    
    /**
     * Gets the rational number from the value
     * @return RationalNumber
     */
        
    public RationalNumber getRationalValue(){
        int nominator = 0, denominator = 0;
        int index = 0;
        
        while (index < value.length() && value.charAt(index) != '/'){
            nominator *= 10;
            nominator += value.charAt(index) - '0';
            index ++;
        }
        
        index ++;
        
        while (index < value.length() ){
            denominator *= 10;
            denominator += value.charAt(index);
            index ++;
        }
        
        RationalNumber number = new RationalNumber(nominator, denominator);

        return number;
    }
    
    // give type to newly created Token
    public Token(Type type){
        this.type = type;
    }
    
}