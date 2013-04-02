package player;

public class LexerException extends RuntimeException {
	
	private static final long serialVersionUID = 5100538752207706718L;
	
	public LexerException() { super(); }
	public LexerException(String message) { super(message); }
	public LexerException(String message, Throwable cause) { super(message, cause); }
	public LexerException(Throwable cause) { super(cause); }

}
