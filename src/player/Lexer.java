package player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Lexer{
	
    private String s;
    private final String regexHeader = 				//One regex for the header
  			 "((?<=C:)\\s*.+$)|" 		+  			//1- add COMPOSER
 "((?<=K:)\\s*[A-Ga-g][#b]?m?)|" 		+			//2- add KEY
   "((?<=L:)\\s*[0-9]+/[0-9]+)|" 		+			//3- add LENGTH
 "((?<=M:)\\s*(?:\\d+\\/\\d+))|" 		+			//4- add METER
 		  "((?<=Q:)\\s*[0-9]+)|"		+			//5- add TEMPO
 			 "((?<=T:)\\s*.+$)|" 		+			//6- add TITLE
 		   "((?<=X:)\\s*\\d+$)|" 		+			//7- add INDEX
 		     "((?<=V:)\\s*.+$)|" 		;			//8- add VOICE

 	private final String regexBody =	   			//One regex for the body
 	   "((?:(?:\\^)|(?:\\^\\^)|" 		+			//1- add KEYNOTE
 	   	   "(?:\\_)|(?:\\_\\_)|" 		+			//1- still KEYNOTE
 "(?:\\=))?[A-Ga-g](?:(?:\\,*)|" 		+			//1- KEEEEEYNOOOOOOTEEEEEEEEEEEE
   "(?:\\'*))(?:[0-9]*/?[0-9]*)" 		+		
					 "(?!\\:))|" 		+			
 		  "(z[0-9 ]*/?[0-9 ]*)|"		+			//2- add REST *
 		  	   "(\\[(?![1-2]))|"		+			//3- add CHORD_START
 		  	    "((?<!\\|)\\])|"		+			//4- add CHORD_END *
 		  			  "(\\(2)|"			+			//5- add DUPLET_START
 		  			  "(\\(3)|"			+			//6- add TRIPLET_START
 		  			  "(\\(4)|"			+			//7- add QUAD_START
 		   "((\\|)(?![\\|:\\]]))|"		+			//8- add BAR
"((?:\\|\\|)|(?:\\[\\|)|(?:\\|\\]))|"	+			//9- add DOUBLE_BAR *
						   "(\\|:)|"	+           //10- add REPEAT_START
					   "(:\\|)|"		+			//11- add REPEAT_END
				   "(\\[[1-2])"			+			//12- add REPEAT_NUMBER
 	 		"((?<=V:)\\s*.+$)|" 		;			//13- add VOICE
    
    // Create a map, mapping token types to their numbers in order
    private static Map<String,Integer> headerMap;
    private static Map<String,Integer> bodyMap;
    
    /**
     * Creates a new Lexer object
     * @param s, the input string to be processed
     */
    public Lexer(String s) {
        this.s = uncomment(s); 						//remove comments from the abc file
        
        //regex group mappings for header
        headerMap = new HashMap<String,Integer>();
        headerMap.put("COMPOSER", 1);
        headerMap.put("KEY", 2);
        headerMap.put("LENGTH", 3);
        headerMap.put("METER", 4);
        headerMap.put("TEMPO", 5);
        headerMap.put("TITLE", 6);
        headerMap.put("INDEX", 7);
        headerMap.put("VOICE", 8);
        
        //regex group mappings for header
        bodyMap.put("KEYNOTE", 1);
        bodyMap.put("REST", 2);
        bodyMap.put("CHORD_START", 3);
        bodyMap.put("CHORD_END", 4);
        bodyMap.put("DUPLET_START", 5);
        bodyMap.put("TRIPLET_START", 6);
        bodyMap.put("QUAD_START", 7);
        bodyMap.put("BAR", 8);
        bodyMap.put("DOUBLE_BAR", 9);
        bodyMap.put("REPEAT_START", 10);
        bodyMap.put("REPEAT_END", 11);
        bodyMap.put("REPEAT_NUMBER", 12);
        bodyMap.put("VOICE", 8);

        
    }
    
    /**
     * Returns the header of the piece
     * @param s, the piece as read from the abc file
     * @return string, the header of the piece
     */
    
    public String getHeader(String s){
    	int splitIndex=s.indexOf("K:");
    	if(splitIndex<0)
    		throw new LexerException("No Key signature K: found in input");
    	
    	try{
    		while(s.charAt(splitIndex)!='\n')
    			splitIndex++;
    	}
    	catch(Exception e){ 						//exception switching in case of EOF
    		throw new LexerException(e.getMessage());
    	}
    	return s.substring(0,splitIndex);
    }
    
    /**
     * Returns the body of the piece
     * @param s, the piece as read from the abc file
     * @return string, the body of the piece
     */
    
    public String getBody(String s){
    	int splitIndex=s.indexOf("K:");
    	if(splitIndex<0)
    		throw new LexerException("No Key signature K: found in input");
    	
    	try{
    		while(s.charAt(splitIndex)!='\n')
    			splitIndex++;
    	}
    	catch(Exception e){ 						//exception switching in case of EOF
    		throw new LexerException(e.getMessage());
    	}
    	return s.substring(splitIndex+1);
    }
    /**
     * Removes comment from a string (comments start with '%' and end with a newline)
     * @param s, the string to un-comment
     * @return String, the string without any comments
     */
    
    public String uncomment(String s){
    	return new String(s.replaceAll("%.*$", ""));
    }
    
    /**
     * Creates a list of tokens from the given abc string
     * @return ArrayList
     */
    public ArrayList<Token> lex(){
        
        // Create a pattern
        ArrayList <Token> tokens = new ArrayList<Token>();
        
        Pattern tokenPatterns = Pattern.compile(regexPattern);
        
        // Create matcher and start matching to groups 
        Matcher matcher = tokenPatterns.matcher(s);
        
        while (matcher.find()) {
            
            if (matcher.group(map.get("COMPOSER")) != null) {
                Token newToken = new Token(Token.Type.COMPOSER);
                newToken.setValue(matcher.group(map.get("COMPOSER")));
                tokens.add(newToken);
                continue;
            }
            
            else if (matcher.group(map.get("KEY")) != null) {
                Token newToken = new Token(Token.Type.KEY);
                newToken.setValue(matcher.group(map.get("KEY")));
                tokens.add(newToken);
                continue;
            }
            
            else if (matcher.group(map.get("LENGTH")) != null) {
                Token newToken = new Token(Token.Type.LENGTH);
                newToken.setValue(matcher.group(map.get("LENGTH")));
                tokens.add(newToken);
                continue;
            }
            
            else if (matcher.group(map.get("METER")) != null) {
                Token newToken = new Token(Token.Type.METER);
                newToken.setValue(matcher.group(map.get("METER")));
                tokens.add(newToken);
                continue;
            }
            
            else if (matcher.group(map.get("TEMPO")) != null) {
                Token newToken = new Token(Token.Type.TEMPO);
                newToken.setValue(matcher.group(map.get("TEMPO")));
                tokens.add(newToken);
                continue;
            }
            
            else if (matcher.group(map.get("TITLE")) != null) {
                Token newToken = new Token(Token.Type.TITLE);
                newToken.setValue(matcher.group(map.get("TITLE")));
                tokens.add(newToken);
                continue;
            }
            
            else if (matcher.group(map.get("INDEX")) != null) {
                Token newToken = new Token(Token.Type.INDEX);
                newToken.setValue(matcher.group(map.get("INDEX")));
                tokens.add(newToken);
                continue;
            }
            
            else if (matcher.group(map.get("VOICE")) != null) {
                Token newToken = new Token(Token.Type.VOICE);
                newToken.setValue(matcher.group(map.get("VOICE")));
                System.out.printf("%s +=",newToken.getValue());
                tokens.add(newToken);
                continue;
            }
            
            else if (matcher.group(map.get("KEYNOTE")) != null) {
                Token newToken = new Token(Token.Type.KEYNOTE);
                newToken.setValue(matcher.group(map.get("KEYNOTE")));
                newToken.parseValue();
                tokens.add(newToken);
                continue;
            }
            
            else if (matcher.group(map.get("REST")) != null) {
                Token newToken = new Token(Token.Type.REST);
                newToken.setValue(matcher.group(map.get("REST")));
                newToken.parseValue();
                tokens.add(newToken);
                continue;
            }
            
            else if (matcher.group(map.get("CHORD_START")) != null) {
                Token newToken = new Token(Token.Type.CHORD_START);
                newToken.setValue(matcher.group(map.get("CHORD_START")));
                tokens.add(newToken);
                continue;
            }
            
            else if (matcher.group(map.get("CHORD_END")) != null) {
                Token newToken = new Token(Token.Type.CHORD_END);
                newToken.setValue(matcher.group(map.get("CHORD_END")));
                tokens.add(newToken);
                continue;
            }
            
            else if (matcher.group(map.get("DUPLET_START")) != null) {
                Token newToken = new Token(Token.Type.DUPLET_START);
                newToken.setValue(matcher.group(map.get("DUPLET_START")));
                tokens.add(newToken);
                continue;
            }
            
            else if (matcher.group(map.get("TRIPLET_START")) != null) {
                Token newToken = new Token(Token.Type.TRIPLET_START);
                newToken.setValue(matcher.group(map.get("TRIPLET_START")));
                tokens.add(newToken);
                continue;
            }
            
            else if (matcher.group(map.get("QUAD_START")) != null) {
                Token newToken = new Token(Token.Type.QUAD_START);
                newToken.setValue(matcher.group(map.get("QUAD_START")));
                tokens.add(newToken);
                continue;
            }
            
            
            else if (matcher.group(map.get("DOUBLE_BAR")) != null) {
                Token newToken = new Token(Token.Type.DOUBLE_BAR);
                newToken.setValue(matcher.group(map.get("DOUBLE_BAR")));
                tokens.add(newToken);
                continue;
            }
            
            else if (matcher.group(map.get("REPEAT_START")) != null) {
                Token newToken = new Token(Token.Type.REPEAT_START);
                newToken.setValue(matcher.group(map.get("REPEAT_START")));
                tokens.add(newToken);
                continue;
            }
            
            else if (matcher.group(map.get("REPEAT_END")) != null) {
                Token newToken = new Token(Token.Type.REPEAT_END);
                newToken.setValue(matcher.group(map.get("REPEAT_END")));
                tokens.add(newToken);
                continue;
            }
            
            else if (matcher.group(map.get("REPEAT_NUMBER")) != null) {
                Token newToken = new Token(Token.Type.REPEAT_NUMBER);
                newToken.setValue(matcher.group(map.get("REPEAT_NUMBER")));
                tokens.add(newToken);
                continue;
            }
            
            else if (matcher.group(map.get("BAR")) != null) {
                Token newToken = new Token(Token.Type.BAR);
                newToken.setValue(matcher.group(map.get("BAR")));
                tokens.add(newToken);
                continue;
            }
        }

        
        return tokens;
    }
    
    
}