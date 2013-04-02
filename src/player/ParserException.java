package player;

public class ParserException extends RuntimeException {

	private static final long serialVersionUID = 8591096048830833647L;

	public ParserException() { super(); }
	public ParserException(String message) { super(message); }
	public ParserException(String message, Throwable cause) { super(message, cause); }
	public ParserException(Throwable cause) { super(cause); }
}