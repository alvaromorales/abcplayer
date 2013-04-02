package player;

import java.util.ArrayList;
import java.util.List;
import player.Lexer;
import player.AST.*;

public class Parser {

    private Lexer lexer;
    private Song song;

    /**
     * Constructor, attaches lexer to parser, assuming lexer is initialized
     * @param lexer, an initialized lexer instance
     */
    public Parser(Lexer lexer){
        this.lexer = lexer;
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
     * Apply accidental association table to a token to get a SingleNote
     * @param tok, the token to parse into a KeyNote. 
     * Token.Type must be KEYNOTE. Not modified by this method.
     * @return the new SingleNote with the correct accidentals applied
     */
    public SingleNote applyAccidental(Token tok){
        // no accidental, fetch it from the Associator
        if(tok.getAccidental() == Integer.MAX_VALUE) {
            return new AST.SingleNote(
                    tok.getValue().charAt(0), 
                    tok.getDuration(), 
                    tok.getOctave(), 
                    song.accidentalAssociator.getAccidental(tok.getValue().charAt(0))); //fetch default from table
        }

        song.accidentalAssociator.setAccidental(tok.getValue().charAt(0), tok.getAccidental());
        return new SingleNote(tok.getValue().charAt(0), tok.getDuration(), tok.getOctave(), tok.getAccidental());
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
            return new AST.SingleNote(pitch, 
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
        System.out.println(tokens.toString());
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
     * Parses the list of tokens produced by the lexer to fill the AST for the song.
     */
    public void parse(){
        ArrayList<Token> tokens = lexer.lex();

        //initialize Song
        song=new Song(); 
        int i=0;

        while(i<tokens.size()) {
            Token it=tokens.get(i);      //iterator object from index 
            System.out.println(it.toString());
            if(it.inHeader()) {           //We are parsing the header
                ++i;
                switch(it.getType()){
                case COMPOSER:
                    if(song.getHeaderCount()<0)
                        throw new ParserException("Header field found after KEY");
                    song.setComposer(it.getValue());
                    break;
                case KEY:
                    if(song.getHeaderCount()<0)
                        throw new ParserException("Duplicate KEY found in header");
                    song.minimizeHeaderCount();
                    song.setKeySignature(it.getValue());
                    break;
                case LENGTH:
                    if(song.getHeaderCount()<0)
                        throw new ParserException("LENGTH Header field found after KEY");
                    song.setDefaultDuration(it.getRationalValue());
                    break;
                case METER:
                    if(song.getHeaderCount()<0)
                        throw new ParserException("METER Header field found after KEY");
                    song.setMeter(it.getRationalValue());
                    break;
                case TEMPO:
                    if(song.getHeaderCount()<0)
                        throw new ParserException("TEMPO Header field found after KEY");
                    song.setTempo(it.getIntValue());
                    break;
                case TITLE:
                    if(song.getHeaderCount()!=1)
                        throw new ParserException("Second header field is not the title");
                    song.setTitle(it.getValue());
                    break;
                case INDEX:
                    if(song.getHeaderCount()!=0)
                        throw new ParserException("First header field is not the index");
                    song.setIndex(it.getIntValue());
                    break;
                case VOICE:
                    song.getVoice(it.getValue());
                    break;
                default:  //add parserexception to default
                    throw new ParserException("Invalid type found in body");
                }
            }
            else { 																					//We are parsing the body
                switch(it.getType()){
                case BAR:
                    song.accidentalAssociator.revert(); 											// restore default accidentals for the piece
                    ++i;
                    break;
                case CHORD_END:
                    ++i;
                    break;
                case CHORD_START:
                    int offset=findNextType(tokens.subList(i,tokens.size()), Token.Type.CHORD_END); //find CHORD_END
                    if(offset<0)
                        throw new ParserException("End of Chord not found");
                    song.add(parseChord(tokens.get(i+1).getDuration(), tokens.subList(i+1, i+offset)));  	//add chord to current song
                    i+=(offset+1); 																		//skip until the end of the chord
                    break;
                case KEYNOTE:
                    song.add(applyAccidental(it));                                              //apply accidental to token
                    ++i;
                    break;
                case DUPLET_START:
                    try {
                        song.add(parseDuplet(tokens.subList(i+1, i+3)));
                        i+=3; 																			//skip to the next usable token
                        break;
                    } catch (IndexOutOfBoundsException e) {
                        throw new ParserException("Malformed Duplet");
                    }
                case TRIPLET_START:
                    try {
                        song.add(parseTriplet(tokens.subList(i+1, i+4)));
                        i+=4;																	//skip to the next usable token
                        break;
                    } catch (IndexOutOfBoundsException e) {
                        throw new ParserException("Malformed Triplet");
                    }
                case QUAD_START:
                    try {
                        System.out.println(it.toString());
                        song.add(parseQuad(tokens.subList(i+1, i+5)));
                        i+=5; 																			//skip to the next usable token
                        break;
                    } catch (IndexOutOfBoundsException e) {
                        throw new ParserException("Malformed Quadruplet");
                    }
                case REPEAT_START:
                    ++i;																			//do nothing, wait for a REPEAT_END to show up
                    break;
                case REPEAT_END:
                    int j=i-1;
                    while(!((tokens.get(j).getType() == Token.Type.REPEAT_START && tokens.get(j).getValue()!="PASS") || 
                            tokens.get(j).inHeader()==true ||
                            tokens.get(j).getType()==Token.Type.DOUBLE_BAR))							//Look for a repeat_start or a header element in order to start repeating
                        j--;
                    if(tokens.get(j).getType() == Token.Type.REPEAT_START)
                        tokens.get(j).setValue("PASS");
                    i=j+1;
                    break;
                case REPEAT_NUMBER:
                    if(it.getValue().contains("0"))
                        i=it.getOctave();															//Octave is overloaded, look below in the comments
                    else if(it.getValue().contains("1")){
                        int k=i+1;
                        while(tokens.get(k).getType()!=Token.Type.BAR            && 
                                tokens.get(k).getType()!=Token.Type.DOUBLE_BAR     && 
                                (tokens.get(k).getType()!=Token.Type.REPEAT_NUMBER ||					//REPEAT_NUMBER and getValue()=="[2"
                                !(tokens.get(k).getValue().contains("2")))        &&
                                tokens.get(k).getType()!=Token.Type.REPEAT_END     &&
                                tokens.get(k).getType()!=Token.Type.REPEAT_START) 						//search for one of those Type's that stop a repeat_number
                            k++;
                        it.setOctave(k);															//octave is overloaded to keep the redirection index
                        it.setValue("[0");															// "[0" is a "[1" that was passed already
                        ++i;
                    }
                    else
                        ++i;																		//No need to handle "[2" as it's handled by "[1"
                    break;
                case DOUBLE_BAR:
                    song.accidentalAssociator.revert();
                    ++i;
                    break;
                case REST:
                    song.add(new Rest(it.getDuration()));
                    ++i;
                    break;
                default:
                    throw new ParserException("Invalid type found in body");
                }
            }

        }

    }

}
