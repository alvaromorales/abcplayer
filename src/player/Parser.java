package player;

import java.util.ArrayList;
import java.util.List;

import player.AST.Chord;
import player.Lexer;
import player.AST;
import player.Token.Type;

public class Parser {

	private Lexer lexer;

	/*
	 * Constructor, attaches lexer to parser, assuming lexer is initialized
	 * @param lexer, an initialized lexer instance
	 */
	public Parser(Lexer lexer){
		this.lexer=lexer;
	}

	/*
	 * Returns the index of the next token with a specific type
	 * @param inp, the input list to search
	 * @param type, the type we are looking for
	 * @return the index of the first occurrence of that type. Returns -1 if not found
	 */
	public int findNextType(List<Token> inp, Token.Type type){
		for(int i=0;i<inp.size();++i)
			if(inp.get(i).getType() == type){
				return i;
				break;
			}
		return -1;
	}
	
	/*
	 * Parses a list of keyNotes into a Chord
	 * @param inp, list of tokens to parse into a Chord
	 */
	public AST.Chord parseChord(RationalNumber duration, List<Token> chordTokens){
		ArrayList<NoteElement> buffer = new ArrayList<NoteElement>(0);
		for(Token j: chordTokens){
			if(j.getType() != Token.Type.KEYNOTE)
				throw new ParserException("Found non-single note inside a chord");
			
			buffer.add(new AST.SingleNote(j.getValue().charAt(0), j.getDuration(), j.getOctave(), j.getAccidential()));
		}
		return new AST.Chord(duration,buffer);
	}
	
	public AST.Duplet parseDuplet(List<Token> inp){
		try{
			AST.SingleNote note1=new AST.SingleNote(inp.get(0).getValue().charAt(0), 
													inp.get(0).getDuration(), 
													inp.get(0).getOctave(), 
													inp.get(0).getAccidential());
		
			AST.SingleNote note2=new AST.SingleNote(inp.get(1).getValue().charAt(0), 
													inp.get(1).getDuration(), 
													inp.get(1).getOctave(), 
													inp.get(1).getAccidential());
		
			return new AST.Duplet(note1 , note2);
			
		}
		catch(ArrayIndexOutOfBoundsException e){ // in case i+1 or i+2 fails
			throw new ParserException(e.getMessage()); //throw ParserException
		}
	}
	
	/*
	 * Parses the list of tokens produced by the lexer to fill the AST for the song.
	 * @param inp, the list of tokens produced by the lexer
	 */
	public AST.Song parse(List<Token> inp){

		AST.Song song=new AST.Song();
		int i=0;
		while(i<inp.size()){
			Token it=inp.get(i); //iterator object from index 
			
			if(it.inHeader()){ //We are parsing the header
				switch(it.getType()){
				case COMPOSER:
					song.setComposer(it.getValue());
				case KEY:
					song.setKeySignature(it.getValue());
				case LENGTH:
					song.setDefaultDuration(it.getRationalValue());
				case METER:
					song.setMeter(it.getRationalValue());
				case TEMPO:
					song.setTempo(it.getIntValue());
				case TITLE:
					song.setTitle(it.getValue());
				case INDEX:
					song.setIndex(it.getIntValue());
				case VOICE:
					song.getVoice(it.getValue());
				default:
					throw new ParserException("Invalid type found in header");		
				}
			}
			else{ //We are parsing the body
				switch(it.getType()){
				case BAR:
					break;
				case CHORD_END:
					throw new ParserException("Unexpected Chord End");
				case CHORD_START:
					int offset=this.findNextType(inp.subList(i,inp.size()-1), Token.Type.CHORD_END); //find CHORD_END
					if(offset<0)
						throw new ParserException("End of Chord not found");
			
					song.add(this.parseChord(it.getDuration(), inp.subList(i+1, i+offset-1))); //add chord to current song
					
					i+=offset; //skip until the end of the chord
				case DUPLET_START:
					song.add(this.parseDuplet(inp.subList(i+1, i+3)));
					i+=2; //skip to the next usuable token
				case END_LINE:
					break;
				case KEYNOTE:
					song.add(new AST.SingleNote(it.getValue().charAt(0), it.getDuration(), it.getOctave(), it.getAccidential()));	
				case QUAD_START:
					break;
				case REPEAT_END:
					break;
				case REPEAT_NUMBER:
					break;
				case REPEAT_START:
					break;
				case REST:
					break;
				case TIME:
					break;
				case TRIPLET_START:
					
					i+=3;
				default:
					throw new ParserException("Invalid type found in body");

				}
			}
			++i;
		}

	}
	
}
