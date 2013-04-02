package player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Lexer{
	
    private String s;
    private final String regexPattern = 
  			 "((?<=C:)\\s*.+$)|" 		+  			//1- add COMPOSER
 "((?<=K:)\\s*[A-Ga-g][#b]?m?)|" 		+			//2- add KEY
   "((?<=L:)\\s*[0-9]+/[0-9]+)|" 		+			//3- add LENGTH
 "((?<=M:)\\s*(?:\\d+\\/\\d+))|" 		+			//4- add METER
 		  "((?<=Q:)\\s*[0-9]+)|"		+			//5- add TEMPO
 			 "((?<=T:)\\s*.+$)|" 		+			//6- add TITLE
 		   "((?<=X:)\\s*\\d+$)|" 		+			//7- add INDEX
 		     "((?<=V:)\\s*.+$)|"		+			//8- add VOICE
"((?:(?:\\^)|(?:\\^\\^)|(?:\\_)|(?:\\_\\_)|(?:\\=))?[A-Ga-g](?:(?:\\,*)|(?:\\'*))(?:[0-9]*/?[0-9]*)(?!\\:))|" + //9- add KEYNOTE *
 		  "(z[0-9 ]*/?[0-9 ]*)|"		+			//10- add REST *
 		  	   "(\\[(?![1-2]))|"		+			//11- add CHORD_START
 		  	    "((?<!\\|)\\])|"		+			//12- add CHORD_END *
 		  			  "(\\(2)|"			+			//13- add DUPLET_START
 		  			  "(\\(3)|"			+			//14- add TRIPLET_START
 		  			  "(\\(4)|"			+			//15- add QUAD_START
 		   "((\\|)(?![\\|:\\]]))|"		+			//16- add BAR
"((?:\\|\\|)|(?:\\[\\|)|(?:\\|\\]))|"	+			//17- add DOUBLE_BAR *
						   "(\\|:)|"	+           //18- add REPEAT_START
					   "(:\\|)|"		+			//19- add REPEAT_END
				   "(\\[[1-2])";					//20- add REPEAT_NUMBER
    
    // Create a map, mapping token types to their numbers in order
    private static Map<String,Integer> map;
    
    /**
     * Creates a new Lexer object
     * @param s, the input string to be processed
     */
    public Lexer(String s) {
        this.s = uncomment(s); 						//remove comments from the abc file
        map = new HashMap<String,Integer>();
        map.put("COMPOSER", 1);
        map.put("KEY", 2);
        map.put("LENGTH", 3);
        map.put("METER", 4);
        map.put("TEMPO", 5);
        map.put("TITLE", 6);
        map.put("INDEX", 7);
        map.put("VOICE", 8);
        map.put("KEYNOTE", 9);
        map.put("REST", 10);
        map.put("CHORD_START", 11);
        map.put("CHORD_END", 12);
        map.put("DUPLET_START", 13);
        map.put("TRIPLET_START", 14);
        map.put("QUAD_START", 15);
        map.put("BAR", 16);
        map.put("DOUBLE_BAR", 17);
        map.put("REPEAT_START", 18);
        map.put("REPEAT_END", 19);
        map.put("REPEAT_NUMBER", 20);
        
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