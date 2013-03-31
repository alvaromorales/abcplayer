package player;


/**
 * A token is a lexical item that the parser uses.
 */
public class Token {
    /**
     * All the types of tokens that can be made.
     */
    public static enum Type {COMPOSER, 
            KEY,
            LENGTH,
            METER,
            TEMPO,
            TITLE,
            INDEX,
            KEYNOTE,
            REST,
            CHORD_START,
            CHORD_END,
            DUPLET_START,
            TRIPLET_START,
            QUAD_START,
            BAR,
            REPEAT_START,
            REPEAT_END,
            REPEAT_NUMBER,
            END_LINE,
            VOICE;
    
    };
                            
    
    private String value;
    private Type type;
    private int octave;
    private int accidental;
    private boolean header=false;
    private RationalNumber duration;
    
    /**
     * Creates a new Token object with Type type
     * @param type
     */
    public Token(Type type){
        this.type = type;
        if(type == Type.COMPOSER || 
           type == Type.KEY || 
           type == Type.LENGTH || 
           type == Type.METER || 
           type == Type.TEMPO ||
           type == Type.TITLE ||
           type == Type.INDEX){
        	this.header=true;
        }
        	
    }
    
    /**
     * Gets whether this is a header token or not.
     * @return A boolean, true if this is a header token or false otherwise.
     */
    public boolean inHeader(){
    	return this.header;
    }
    
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
     * Gets the duration of Token
     * @return the duration
     */
    public RationalNumber getDuration() {
        return this.duration;
    }
    
    /**
     * Gets the rational number from the value
     * @return RationalNumber
     */
        
    public RationalNumber getRationalValue(){
        int nominator = 0, denominator = 0;
        int index = -1;
        
        index = value.indexOf('/');
        
        nominator = Integer.parseInt(value.substring(0,index));
        denominator = Integer.parseInt(value.substring(index+1));

        RationalNumber number = new RationalNumber(nominator, denominator);

        return number;
    }
    
    
    /**
     * Gets integer number from value
     * @return integer value
     */
    
    public int getIntValue(){
        return Integer.parseInt(value);
    }
    
    

    /**
     * Sets the value of Token
     * @param value
     */
    public void setValue(String value){
        this.value = value;
    }
    
    
    /**
     * Sets the octave of Token
     * @param octave
     */
    public void setOctave(int octave){
        this.octave = octave;
    }
    
    /**
     * Sets the accidental of Token
     * @param accidental
     */
    public void setAccidental(int accidental){
        this.accidental = accidental;
    }
    
    /**
     * Sets the duration of Token
     * @param duration
     */
    public void setDuration(RationalNumber duration){
        this.duration = duration;
    }
}