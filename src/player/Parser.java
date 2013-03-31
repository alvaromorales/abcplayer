package player;

import java.util.ArrayList;
import java.util.List;
import player.Lexer;
import player.AST.*;
import player.Token.Type;

public class Parser {

	private Lexer lexer;

	/**
	 * Constructor, attaches lexer to parser, assuming lexer is initialized
	 * @param lexer, an initialized lexer instance
	 */
	public Parser(Lexer lexer){
		this.lexer=lexer;
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
	 * Parses a list of keyNotes into a Chord
	 * @param inp, list of tokens to parse into a Chord
	 */
	public Chord parseChord(RationalNumber duration, List<Token> chordTokens){
		ArrayList<NoteElement> buffer = new ArrayList<NoteElement>(0);
		for(Token j: chordTokens){
			if(j.getType() != Token.Type.KEYNOTE)
				throw new ParserException("Found non-single note inside a chord");
			
			buffer.add(new SingleNote(j.getValue().charAt(0), j.getDuration(), j.getOctave(), j.getAccidential()));
		}
		return new Chord(duration,buffer);
	}
	
	/**
	 * Create a duplet from a list of Tokens
	 * @param inp, a list of tokens, inp.size()==2
	 * @return duplet, a new duplet 
	 */
	public Duplet parseDuplet(List<Token> inp){
		try{
			SingleNote note1=new SingleNote(inp.get(0).getValue().charAt(0), 
													inp.get(0).getDuration().mul(new RationalNumber(3, 2)), 
													inp.get(0).getOctave(), 
													inp.get(0).getAccidential());
		
			SingleNote note2=new SingleNote(inp.get(1).getValue().charAt(0), 
													inp.get(1).getDuration().mul(new RationalNumber(3, 2)), 
													inp.get(1).getOctave(), 
													inp.get(1).getAccidential());
		
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
			SingleNote note1=new SingleNote(inp.get(0).getValue().charAt(0), 
													inp.get(0).getDuration().mul(new RationalNumber(2, 3)), 
													inp.get(0).getOctave(), 
													inp.get(0).getAccidential());
		
			SingleNote note2=new SingleNote(inp.get(1).getValue().charAt(0), 
													inp.get(1).getDuration().mul(new RationalNumber(2, 3)), 
													inp.get(1).getOctave(), 
													inp.get(1).getAccidential());
			
			SingleNote note3=new SingleNote(inp.get(1).getValue().charAt(0), 
													inp.get(2).getDuration().mul(new RationalNumber(2, 3)), 
													inp.get(2).getOctave(), 
													inp.get(2).getAccidential());
		
			return new Triplet(note1 , note2, note3);
			
		}
		catch(ArrayIndexOutOfBoundsException e){ // in case 0, 1 or 2 fails
			throw new ParserException(e.getMessage()); //throw ParserException
		}
	}
	
	/**
	 * Create a quad from a list of Tokens
	 * @param inp, a list of tokens, inp.size()==4
	 * @return quad, a new quad 
	 */
	public Quadruplet parseQuad(List<Token> inp){
		try{
			SingleNote note1=new SingleNote(inp.get(0).getValue().charAt(0), 
													inp.get(0).getDuration().mul(new RationalNumber(3, 4)), 
													inp.get(0).getOctave(), 
													inp.get(0).getAccidential());
		
			SingleNote note2=new SingleNote(inp.get(1).getValue().charAt(0), 
													inp.get(1).getDuration().mul(new RationalNumber(3, 4)), 
													inp.get(1).getOctave(), 
													inp.get(1).getAccidential());
			
			SingleNote note3=new SingleNote(inp.get(2).getValue().charAt(0), 
													inp.get(2).getDuration().mul(new RationalNumber(3, 4)), 
													inp.get(2).getOctave(), 
													inp.get(2).getAccidential());
			
			SingleNote note4=new SingleNote(inp.get(3).getValue().charAt(0), 
													inp.get(3).getDuration().mul(new RationalNumber(3, 4)), 
													inp.get(3).getOctave(), 
													inp.get(3).getAccidential());
		
			return new Quadruplet(note1 , note2, note3, note4);
			
		}
		catch(ArrayIndexOutOfBoundsException e){ // in case 0, 1 or 2 fails
			throw new ParserException(e.getMessage()); //throw ParserException
		}
	}
	/**
	 * Parses the list of tokens produced by the lexer to fill the AST for the song.
	 * @param inp, the list of tokens produced by the lexer
	 */
	public Song parse(List<Token> inp){

		Song song=new Song();
		int i=0;
		while(i<inp.size()){
			Token it=inp.get(i); //iterator object from index 
			
			if(it.inHeader()){ //We are parsing the header
				switch(it.getType()){
				case COMPOSER:
					if(song.getHeaderCount()<0)
						throw new ParserException("Header field found after KEY");
					song.setComposer(it.getValue());
				case KEY:
					if(song.getHeaderCount()<0)
						throw new ParserException("Duplicate KEY found in header");
					song.minimizeHeaderCount();
					song.setKeySignature(it.getValue());
				case LENGTH:
					if(song.getHeaderCount()<0)
						throw new ParserException("LENGTH Header field found after KEY");
					song.setDefaultDuration(it.getRationalValue());
				case METER:
					if(song.getHeaderCount()<0)
						throw new ParserException("METER Header field found after KEY");
					song.setMeter(it.getRationalValue());
				case TEMPO:
					if(song.getHeaderCount()<0)
						throw new ParserException("TEMPO Header field found after KEY");
					song.setTempo(it.getIntValue());
				case TITLE:
					if(song.getHeaderCount()!=1)
						throw new ParserException("Second header field is not the title");
					song.setTitle(it.getValue());
				case INDEX:
					if(song.getHeaderCount()!=0)
						throw new ParserException("First header field is not the index");
					song.setIndex(it.getIntValue());
				case VOICE:
					song.getVoice(it.getValue());
				default:
					throw new ParserException("Invalid type found in header");		
				}
			}
			else { 										//We are parsing the body
				switch(it.getType()){
				case BAR:
					song.accidentalAssociator.revert(); // restore default accidentals for the piece
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
				case KEYNOTE:
					song.add(new SingleNote(it.getValue().charAt(0), it.getDuration(), it.getOctave(), it.getAccidential()));	
				case QUAD_START:
					song.add(this.parseQuad(inp.subList(i+1, i+5)));
					i+=4; //skip to the next usuable token
				case REPEAT_END:
					break;
				case REPEAT_NUMBER:
					break;
				case REPEAT_START:
					break;
				case REST:
					song.add(new Rest(it.getDuration()));
				case TRIPLET_START:
					song.add(this.parseTriplet(inp.subList(i+1, i+4)));
					i+=3; //skip to the next usuable token
				default:
					throw new ParserException("Invalid type found in body");

				}
			}
			++i;
		}

		return song;
	}
	
}
