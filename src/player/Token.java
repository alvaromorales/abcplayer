package player;

/**
 * A token is a lexical item that the parser uses.
 */
public class Token {
    /**
     * All the types of tokens that can be made.
     */
    public static enum Type {
            COMPOSER, 
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
            DOUBLE_BAR,
            REPEAT_START,
            REPEAT_END,
            REPEAT_NUMBER,
            VOICE;
    };

    private String value;
    private Type type;
    private int octave;
    private int accidental;
    private RationalNumber duration;
    
    /**
     * Creates a new Token object with Type type
     * @param type
     */
    public Token(Type type){
        this.type = type;

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
    public int getAccidental() {
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
        // if the token is METER and its C or C| we need to return constant rational number
        if (this.getType() == Token.Type.METER){
            if (this.getValue().charAt(0) == 'C'){
                if (this.getValue().length() == 1){
                    return new RationalNumber(4,4);
                } else
                    return new RationalNumber(2,2);
            }
        }
        
        
        // getting rational number for every other type of token
        
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
     * parses the octave, accidental, key , and duration from value
     */
    public void parseValue(){
        int octave = 0;
        int accidental = 0;
        char keynote = 'z', c;
        int nominator = 0, denominator = 0; 
        boolean isRational = false, isNeutral = false;
        
        for (int i=0; i<this.value.length(); i++){
            c = this.value.charAt(i);
            // find octave of the keynote
            if (c == ',')
                octave --;
            if (c == '\'')
                octave ++;
            
            // find accidental of the keynote
            if (c == '^')
                accidental ++;
            if (c == '_')
                accidental --;
            if (c == '=')
                isNeutral = true;
            
            // find keynote 
            if (c >='a' && c <= 'g'){
                keynote = Character.toUpperCase(c);
                octave ++;
            }
            if (c >= 'A' && c<= 'G'){
                keynote = c;
            }
            
            // find duration
            if (c >= '0' && c <= '9'){
                if (! (isRational)){
                    nominator *= 10;
                    nominator += c - '0';
                } else
                {
                    denominator *= 10;
                    denominator += c - '0';
                }
                
            }
            if (c == '/'){
                isRational = true; 
            }
            
        }
        
        
        
        if (!isRational && nominator != 0)
            denominator = 1;
        if (denominator == 0 && isRational)
            denominator = 2;
        if (nominator == 0 && isRational)
            nominator = 1;
        if (nominator == 0 && denominator == 0)
            nominator = denominator = 1;
        
        if (isNeutral) {
            accidental = 0;
        } else if (accidental == 0) {
            accidental = Integer.MAX_VALUE;
        }
        
        this.setOctave(octave);
        this.setAccidental(accidental);
        this.setDuration(new RationalNumber(nominator,denominator));
        this.value = Character.toString(keynote);
        

    }
    
    

    /**
     * Sets the value of Token
     * @param value
     */
    public void setValue(String value){
        this.value = value.trim();
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
    
    /**
     * Checks if a Token is equal to another Token
     * @param obj the Object to compare to
     * @return true if equal, else false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) { // quick check
            return true;
        }

        if (o == null || !(o instanceof Token)) {
            return false;
        }

        Type otherType = ((Token) o).type;      
        String otherValue = ((Token) o).value;
        int otherOctave = ((Token) o).octave;
        int otherAccidental = ((Token) o).accidental;
        RationalNumber otherDuration = ((Token) o).duration;
        
        //all tokens have a type, value and header
        //KEYNOTE tokens have octave, accidental and duration
        if (otherType.equals(Token.Type.KEYNOTE) && otherDuration != null) {
            return this.octave == otherOctave && this.accidental == otherAccidental && this.duration.equals(otherDuration);
        } else {
            return this.type.equals(otherType) && this.value.equals(otherValue);
        }
    }
    
    /**
     * Gets the string representation of a Token
     */
    @Override
    public String toString() {
        return "Token: " + type + " : " + value;
    }
}
