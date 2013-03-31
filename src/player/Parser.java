package player;

import java.util.List;
import player.Lexer;
import player.AST;

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
	 * Parses the list of tokens produced by the lexer to fill the AST for the song.
	 * @param inp, the list of tokens produced by the lexer
	 */
	public AST.Song parse(List<Token> inp){
		
		AST.Song song=new AST.Song();
		for(Token it: inp){
			if(it.inHeader()){
				
			}
		}
		
		
	}
}
