package player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import ast.*;

public class Parser {

    private Song song = new Song();

    /**
     * Constructor, attaches lexer to parser, assuming lexer is initialized
     * @param lexer, an initialized lexer instance
     */
    public Parser(){

    }

    /**
     * Gets the parsed Song
     * Call parse() before trying to access the song, otherwise it won't be of any use.
     * @return the parsed Song
     */
    public Song getSong(){
        return song;
    }

    /**
     * Returns the index of the next token with a specific type
     * @param inp, the input list to search
     * @param type, the type we are looking for
     * @return the index of the first occurrence of that type. Returns -1 if not found
     */
    public int findNextType(List<Token> inp, Token.Type type){
        for(int i=0;i<inp.size();++i)
            if(inp.get(i).getType() == type){
                return i;
            }
        return -1;
    }

    /**
     * Apply accidental association table to a note's attributes to get new a SingleNote
     * @param pitch, the pitch of the to-be-created SingleNote
     * @param duration, the duration of the to-be-created SingleNote
     * @param octave, the octave of the to-be-created SingleNote
     * @param accidental, the accidental of the to-be-created SingleNote, after cross-checking with the associator
     * @return the new SingleNote with the correct accidentals applied
     */
    public SingleNote applyAccidental(char pitch, RationalNumber duration, int octave, int accidental){
        if(accidental == Integer.MAX_VALUE) {
            return new SingleNote(pitch, 
                    duration, 
                    octave, 
                    song.accidentalAssociator.getAccidental(pitch)); //if no accidental set fetch default from table
        }

        song.accidentalAssociator.setAccidental(pitch, accidental);
        return new SingleNote(pitch, duration, octave, accidental);
    }

    /**
     * Parses a list of keyNotes into a Chord
     * @param inp, list of tokens to parse into a Chord
     */
    public Chord parseChord(RationalNumber duration, List<Token> chordTokens){
        ArrayList<NoteElement> buffer = new ArrayList<NoteElement>(0);
        for(Token j: chordTokens){
            if(j.getType() != Token.Type.KEYNOTE)
                throw new ParserException("Found non-single note inside a chord");

            buffer.add(applyAccidental(j.getValue().charAt(0), j.getDuration(), j.getOctave(), j.getAccidental()));
        }
        return new Chord(duration,buffer);
    }

    /**
     * Create a duplet from a list of Tokens
     * @param tokens, a list of tokens, tokens.size()==2
     * @return duplet, a new duplet 
     */
    public Duplet parseDuplet(List<Token> tokens){
        try{
            SingleNote note1 = applyAccidental(
                    tokens.get(0).getValue().charAt(0), 
                    tokens.get(0).getDuration().mul(new RationalNumber(3, 2)), 
                    tokens.get(0).getOctave(), 
                    tokens.get(0).getAccidental());

            SingleNote note2 = applyAccidental(
                    tokens.get(1).getValue().charAt(0), 
                    tokens.get(1).getDuration().mul(new RationalNumber(3, 2)), 
                    tokens.get(1).getOctave(), 
                    tokens.get(1).getAccidental());

            return new Duplet(note1 , note2);

        }
        catch(ArrayIndexOutOfBoundsException e){ // in case i+1 or i+2 fails
            throw new ParserException(e.getMessage()); //throw ParserException
        }
    }

    /**
     * Create a triplet from a list of Tokens
     * @param inp, a list of tokens, inp.size()==3
     * @return triplet, a new triplet 
     */
    public Triplet parseTriplet(List<Token> inp){
        try{
            SingleNote note1=applyAccidental(inp.get(0).getValue().charAt(0), 
                    inp.get(0).getDuration().mul(new RationalNumber(2, 3)), 
                    inp.get(0).getOctave(), 
                    inp.get(0).getAccidental());

            SingleNote note2=applyAccidental(inp.get(1).getValue().charAt(0), 
                    inp.get(1).getDuration().mul(new RationalNumber(2, 3)), 
                    inp.get(1).getOctave(), 
                    inp.get(1).getAccidental());

            SingleNote note3=applyAccidental(inp.get(1).getValue().charAt(0), 
                    inp.get(2).getDuration().mul(new RationalNumber(2, 3)), 
                    inp.get(2).getOctave(), 
                    inp.get(2).getAccidental());

            return new Triplet(note1 , note2, note3);

        }
        catch(ArrayIndexOutOfBoundsException e){ // in case 0, 1 or 2 fails
            throw new ParserException(e.getMessage()); //throw ParserException
        }
    }

    /**
     * Create a Quadruplet from a list of Tokens
     * @param tokens, a list of tokens, where token.size()==4
     * @return a new Quadruplet with the appropriate notes 
     */
    public Quadruplet parseQuad(List<Token> tokens){
        SingleNote note1 = applyAccidental(tokens.get(0).getValue().charAt(0), 
                tokens.get(0).getDuration().mul(new RationalNumber(3, 4)), 
                tokens.get(0).getOctave(), 
                tokens.get(0).getAccidental());

        SingleNote note2 = applyAccidental(tokens.get(1).getValue().charAt(0), 
                tokens.get(1).getDuration().mul(new RationalNumber(3, 4)), 
                tokens.get(1).getOctave(), 
                tokens.get(1).getAccidental());

        SingleNote note3 = applyAccidental(tokens.get(2).getValue().charAt(0), 
                tokens.get(2).getDuration().mul(new RationalNumber(3, 4)), 
                tokens.get(2).getOctave(), 
                tokens.get(2).getAccidental());

        SingleNote note4 = applyAccidental(tokens.get(3).getValue().charAt(0), 
                tokens.get(3).getDuration().mul(new RationalNumber(3, 4)), 
                tokens.get(3).getOctave(), 
                tokens.get(3).getAccidental());

        return new Quadruplet(note1,note2, note3, note4);
    }

    /**
     * Parses the header tokens
     * @param headerTokens the header tokens the Lexer lexed
     */
    public void parseHeader(ArrayList<Token> headerTokens) {
        if (headerTokens.size() < 3) {
            throw new ParserException("Incomplete Header: The header must contain X, T, K at the very minimum");
        }

        // Parse the "X:" token
        Token index = headerTokens.get(0);
        if (index.getType() != Token.Type.INDEX) {
            throw new ParserException("Malformed Header: The header must have X as the first field");
        } else {
            song.setIndex(index.getIntValue());
        }

        //Parse the "T:" token
        Token title = headerTokens.get(1);
        if (title.getType() != Token.Type.TITLE) {
            throw new ParserException("Malformed Header: The header must have T as the second field");
        } else {
            song.setTitle(title.getValue());
        }

        //Parse the rest of the header tokens
        for (int i=2;i<headerTokens.size()-1;i++) {
            Token tok = headerTokens.get(i);      //current token
            switch(tok.getType()){
            case COMPOSER:
                song.setComposer(tok.getValue());
                break;
            case LENGTH:
                song.setDefaultDuration(tok.getRationalValue());
                break;
            case METER:
                song.setMeter(tok.getRationalValue());
                break;
            case TEMPO:
                song.setTempo(tok.getIntValue());
                break;
            case VOICE:
                song.addVoice(new Voice(tok.getValue()));
                break;
            case KEY:
                throw new ParserException("Malformed Header: K must be the last header field");
            case TITLE:
                throw new ParserException("Malformed Header: Duplicate T header field");
            case INDEX:
                throw new ParserException("Malformed Header: Duplicate X header field");
            default:  //add parserexception to default
                throw new ParserException("Invalid field found in header");
            }
        }

        //Parse the "K:" token
        Token key = headerTokens.get(headerTokens.size()-1);
        if (key.getType() != Token.Type.KEY) {
            throw new ParserException("Malformed Header: The header must have K as the last field");
        } else {
            song.setKeySignature(key.getValue());
        }

    }

    /**
     * Parses the voice tokens
     * @param tokens the voice tokens
     */
    public void parseVoice(ArrayList<Token> tokens) {
        int i=0;
        boolean repeatsBalanced = true;

        while(i<tokens.size()) {
            Token tok = tokens.get(i);

            //parse body
            switch(tok.getType()){
            case BAR:
                song.accidentalAssociator.revert(); // restore default accidentals for the piece
                ++i;
                break;
            case CHORD_END:
                ++i;
                break;
            case CHORD_START:
                int offset=findNextType(tokens.subList(i,tokens.size()), Token.Type.CHORD_END); //find CHORD_END
                if(offset<0)
                    throw new ParserException("End of Chord not found");
                song.add(parseChord(tokens.get(i+1).getDuration(), tokens.subList(i+1, i+offset)));     //add chord to current song
                i+=(offset+1);                                                                      //skip until the end of the chord
                break;
            case KEYNOTE:
                song.add(applyAccidental(
                        tok.getValue().charAt(0),
                        tok.getDuration(),
                        tok.getOctave(),
                        tok.getAccidental()
                        ));                                              //apply accidental to token
                ++i;
                break;
            case DUPLET_START:
                try {
                    song.add(parseDuplet(tokens.subList(i+1, i+3)));
                    i+=3;                                                                           //skip to the next usable token
                    break;
                } catch (IndexOutOfBoundsException e) {
                    throw new ParserException("Malformed Duplet");
                }
            case TRIPLET_START:
                try {
                    song.add(parseTriplet(tokens.subList(i+1, i+4)));
                    i+=4;                                                                   //skip to the next usable token
                    break;
                } catch (IndexOutOfBoundsException e) {
                    throw new ParserException("Malformed Triplet");
                }
            case QUAD_START:
                try {
                    song.add(parseQuad(tokens.subList(i+1, i+5)));
                    i+=5;                                                                           //skip to the next usable token
                    break;
                } catch (IndexOutOfBoundsException e) {
                    throw new ParserException("Malformed Quadruplet");
                }
            case REPEAT_START:
                if (!repeatsBalanced) {
                    throw new ParserException("Malformed Body: Repeats are not balanced");
                }
                
                repeatsBalanced = false;
                ++i;                                                                            //do nothing, wait for a REPEAT_END to show up
                break;
            case REPEAT_END:   
                song.accidentalAssociator.revert();
                repeatsBalanced = true;
                
                if (tok.getValue().equals("PASS")) {
                    ++i;
                    break;
                } else {
                    tok.setValue("PASS");
                    int j=i-1;

                    while(!(j == 0 ||
                            tokens.get(j).getType() == Token.Type.REPEAT_START || 
                            tokens.get(j).getType() == Token.Type.DOUBLE_BAR)) {
                        //Look for a repeat_start or a header element in order to start repeating
                        j--;
                    }

                    if (j == 0) {
                        i = 0;
                    } else {
                        i=j;
                    }
                    
                    break;
                }
            case REPEAT_NUMBER:
                if(tok.getValue().equals("[0"))
                    i=tok.getOctave();                                                          //Octave is overloaded, look below in the comments
                else if(tok.getValue().equals("[1")){
                    int k=i+1;
                    while(tokens.get(k).getType()!=Token.Type.BAR            && 
                            tokens.get(k).getType()!=Token.Type.DOUBLE_BAR     && 
                            (tokens.get(k).getType()!=Token.Type.REPEAT_NUMBER ||                   //REPEAT_NUMBER and getValue()=="[2"
                            !(tokens.get(k).getValue().equals("[2")))        &&
                            tokens.get(k).getType()!=Token.Type.REPEAT_END     &&
                            tokens.get(k).getType()!=Token.Type.REPEAT_START)                       //search for one of those Type's that stop a repeat_number
                        k++;
                    tok.setOctave(k);                                                           //octave is overloaded to keep the redirection index
                    tok.setValue("[0");                                                         // "[0" is a "[1" that was passed already
                    ++i;
                }
                else
                    ++i;                                                                        //No need to handle "[2" as it's handled by "[1"
                break;
            case DOUBLE_BAR:
                song.accidentalAssociator.revert();
                ++i;
                break;
            case REST:
                song.add(new Rest(tok.getDuration()));
                ++i;
                break;
            default:
                throw new ParserException("Invalid type found in body");
            }
        }
        
        if (!repeatsBalanced) {
            throw new ParserException("Malformed Body: Repeats are not balanced");
        }       
        
    }

    /**
     * Splits the body tokens into a list of tokens by voice
     * @param tokens the list of body tokens
     * @return the body tokens into a list of tokens by voice
     */
    public HashMap<String, ArrayList<Token>> splitTokensByVoice(ArrayList<Token> tokens) {
        if (tokens.get(0).getType() != Token.Type.VOICE) {
            throw new ParserException("Malformed body: Body starts with an undeclared voice");
        }
        
        // initialize the voice map
        HashMap<String, ArrayList<Token>> voiceMap = new HashMap<String, ArrayList<Token>>();
        for (Voice v: song.getVoices()) {
            voiceMap.put(v.getName(), new ArrayList<Token>(0));
        }
        
        String currentVoice = tokens.get(0).getValue();
        for (int i=1;i<tokens.size();i++) {
            Token tok = tokens.get(i);
            
            //switch the currentVoice and keep going
            if (tok.getType() == Token.Type.VOICE) {
                currentVoice = tok.getValue();
                continue;
            }
            
            if (voiceMap.get(currentVoice) != null) {
                voiceMap.get(currentVoice).add(tok);
            } else {
                throw new ParserException("Malformed body: Undeclared voice " + tok.getValue());
            }
        }
        
        return voiceMap;
    }
    
    /**
     * Parses the list of tokens produced by the lexer to fill the AST for the song.
     * @param headerTokens the header tokens, as produced by the Lexer
     * @param bodyTokens the body tokens, as produced by the Lexer
     */
    public void parse(ArrayList<Token> headerTokens,ArrayList<Token> bodyTokens){
        parseHeader(headerTokens);
        if (song.getVoices().isEmpty()) {
            // no declared voices
            parseVoice(bodyTokens);
        } else {
            HashMap<String, ArrayList<Token>> voicesMap = splitTokensByVoice(bodyTokens);
            for (Voice v : song.getVoices()) {
                song.getVoice(v.getName());
                parseVoice(voicesMap.get(v.getName()));
            }
        }
    }
}
