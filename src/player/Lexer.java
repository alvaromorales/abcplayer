package player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Lexer{
	
    private String head,body;
    
    private final String regexHeader = 				//One regex for the header
  			 "((?<=C:)\\s*.+)|" 		+  			//1- add COMPOSER
 "((?<=K:)\\s*[A-Ga-g][#b]?m?)|" 		+			//2- add KEY
   "((?<=L:)\\s*[0-9]+/[0-9]+)|" 		+			//3- add LENGTH
 "((?<=M:)\\s*(?:(?:\\d+\\/\\d+)|" 		+			//4- add METER
 			   	  "(?:C\\|?)))|" 		+			//4- still METER
 		  "((?<=Q:)\\s*[0-9]+)|"		+			//5- add TEMPO
 			 "((?<=T:)\\s*.+)|" 		+			//6- add TITLE
 		   "((?<=X:)\\s*\\d+)|" 		+			//7- add INDEX
 		     "((?<=V:)\\s*.+\\n)" 		;			//8- add VOICE

 	private final String regexBody =	   			//One regex for the body
 		   "((?:(?:\\^){1,2}|" 		+			//1- add KEYNOTE
 	   	   		 "(?:\\_){1,2}|" 		+			
 	   	   		 	 "(?:\\=))?"		+
 	   	   	 		  "[A-Ga-g]"		+
 	   	 "(?:(?:\\,*)|(?:\\'*))" 		+			
 	   	  "(?:[0-9]*/?[0-9]*))|" 		+		
		    "(z[0-9]*/?[0-9]*)|"		+			//2- add REST
 		  	   "(\\[(?![1-2]))|"		+			//3- add CHORD_START
 		  	    "((?<!\\|)\\])|"		+			//4- add CHORD_END *
 		  			   "(\\(2)|"		+			//5- add DUPLET_START
 		  			   "(\\(3)|"		+			//6- add TRIPLET_START
 		  			   "(\\(4)|"		+			//7- add QUAD_START
 		   "((?:\\|)(?![\\|:\\]]))|"		+			//8- add BAR
"((?:\\|{2})|(?:\\[\\|)|(?:\\|\\]))|"	+			//9- add DOUBLE_BAR *
						   "(\\|:)|"	+           //10- add REPEAT_START
					   "(:\\|)|"		+			//11- add REPEAT_END
				   "(\\[[1-2])|"		+			//12- add REPEAT_NUMBER
			   "((?<=V:)\\s*.+(?=\\n))"		;			//13- add VOICE
    
    // Create a headMap, headMapping token types to their numbers in order
    private static Map<String,Integer> headMap;
    private static Map<String,Integer> bodyMap;
    
    
    /**
     * Creates a new Lexer object
     * @param s, the input string to be processed
     */
    public Lexer(String s) {
    	
        this.head=uncomment(makeHeader(s)); //head of the piece, no comments 
        this.body=uncomment(makeBody(s)); 	//body of the piece, no comments
        
        System.out.println(this.body);
        
        headMap=new HashMap<String,Integer>();
        bodyMap=new HashMap<String,Integer>();

        //regex group headMappings for header
        headMap.put("COMPOSER", 1);
        headMap.put("KEY", 2);
        headMap.put("LENGTH", 3);
        headMap.put("METER", 4);
        headMap.put("TEMPO", 5);
        headMap.put("TITLE", 6);
        headMap.put("INDEX", 7);
        headMap.put("VOICE", 8);
        
        //regex group bodyMappings for body
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
        bodyMap.put("VOICE", 13);
        
    }
    
    /**
     * Returns the header of the piece
     * @param s, the piece as read from the abc file
     * @return string, the header of the piece
     */
    
    public String makeHeader(String s){
    	int splitIndex=s.indexOf("K:");
    	if(splitIndex<0)
    		throw new LexerException("No Key signature K: found in input");
    	
    	try{
    		while(s.charAt(splitIndex)!='\n')
    			splitIndex++;
    	}
    	catch(StringIndexOutOfBoundsException e){ 						//exception switching in case of EOF
    		throw new LexerException("Reached end of file, no body after header?");
    	}
    	return s.substring(0,splitIndex);
    }
    
    /**
     * Returns the body of the piece
     * @param s, the piece as read from the abc file
     * @return string, the body of the piece
     */
    
    public String makeBody(String s){
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
    	return new String(s.replaceAll("%.*", ""));
    }
    
    /**
     * Creates a list of tokens from the given abc string
     * @return ArrayList
     */
    public ArrayList<Token> lexHead(){
        
        // Create patterns
        ArrayList <Token> tokens = new ArrayList<Token>(0);
        
        Pattern headPattern = Pattern.compile(this.regexHeader);
        
        // Create headMatchers and start matching to groups 
        Matcher headMatcher = headPattern.matcher(this.head);
        
        while (headMatcher.find()) {
            
            if (headMatcher.group(headMap.get("COMPOSER")) != null) {
                Token newToken = new Token(Token.Type.COMPOSER);
                newToken.setValue(headMatcher.group(headMap.get("COMPOSER")));
                tokens.add(newToken);
                continue;
            }
            
            else if (headMatcher.group(headMap.get("KEY")) != null) {
                Token newToken = new Token(Token.Type.KEY);
                newToken.setValue(headMatcher.group(headMap.get("KEY")));
                tokens.add(newToken);
                continue;
            }
            
            else if (headMatcher.group(headMap.get("LENGTH")) != null) {
                Token newToken = new Token(Token.Type.LENGTH);
                newToken.setValue(headMatcher.group(headMap.get("LENGTH")));
                tokens.add(newToken);
                continue;
            }
            
            else if (headMatcher.group(headMap.get("METER")) != null) {
                Token newToken = new Token(Token.Type.METER);
                newToken.setValue(headMatcher.group(headMap.get("METER")));
                tokens.add(newToken);
                continue;
            }
            
            else if (headMatcher.group(headMap.get("TEMPO")) != null) {
                Token newToken = new Token(Token.Type.TEMPO);
                newToken.setValue(headMatcher.group(headMap.get("TEMPO")));
                tokens.add(newToken);
                continue;
            }
            
            else if (headMatcher.group(headMap.get("TITLE")) != null) {
                Token newToken = new Token(Token.Type.TITLE);
                newToken.setValue(headMatcher.group(headMap.get("TITLE")));
                tokens.add(newToken);
                continue;
            }
            
            else if (headMatcher.group(headMap.get("INDEX")) != null) {
                Token newToken = new Token(Token.Type.INDEX);
                newToken.setValue(headMatcher.group(headMap.get("INDEX")));
                tokens.add(newToken);
                continue;
            }
            
            else if (headMatcher.group(headMap.get("VOICE")) != null) {
                Token newToken = new Token(Token.Type.VOICE);
                newToken.setValue(headMatcher.group(headMap.get("VOICE")));
                tokens.add(newToken);
                continue;
            }
        }

        System.out.println(tokens.toString());
        return tokens;
    }
    
    /**
     * Creates a list of body tokens from the given abc string
     * @return ArrayList, list of body Tokens
     */
    public ArrayList<Token> lexBody(){
        
        // Create patterns
        ArrayList <Token> tokens = new ArrayList<Token>(0);
        
        Pattern bodyPattern = Pattern.compile(this.regexBody);

        // Create Matcher and start matching to groups 
        Matcher bodyMatcher = bodyPattern.matcher(this.body);
        
        //start lexing the body
        while (bodyMatcher.find()) {
        	for(int i=1;i<=bodyMatcher.groupCount();++i)
        		if(bodyMatcher.group(i) != null)
        			System.out.printf("Matched: %s, group_num=%d\n",bodyMatcher.group(i), i);
        	
            if (bodyMatcher.group(bodyMap.get("KEYNOTE")) != null) {
                Token newToken = new Token(Token.Type.KEYNOTE);
                newToken.setValue(bodyMatcher.group(bodyMap.get("KEYNOTE")));
                newToken.parseValue();
                tokens.add(newToken);
                System.out.println(newToken.toString());
                continue;
            }
            
            else if (bodyMatcher.group(bodyMap.get("REST")) != null) {
                Token newToken = new Token(Token.Type.REST);
                newToken.setValue(bodyMatcher.group(bodyMap.get("REST")));
                newToken.parseValue();
                tokens.add(newToken);
                continue;
            }
            
            else if (bodyMatcher.group(bodyMap.get("CHORD_START")) != null) {
                Token newToken = new Token(Token.Type.CHORD_START);
                newToken.setValue(bodyMatcher.group(bodyMap.get("CHORD_START")));
                tokens.add(newToken);
                continue;
            }
            
            else if (bodyMatcher.group(bodyMap.get("CHORD_END")) != null) {
                Token newToken = new Token(Token.Type.CHORD_END);
                newToken.setValue(bodyMatcher.group(bodyMap.get("CHORD_END")));
                tokens.add(newToken);
                continue;
            }
            
            else if (bodyMatcher.group(bodyMap.get("DUPLET_START")) != null) {
                Token newToken = new Token(Token.Type.DUPLET_START);
                newToken.setValue(bodyMatcher.group(bodyMap.get("DUPLET_START")));
                tokens.add(newToken);
                continue;
            }
            
            else if (bodyMatcher.group(bodyMap.get("TRIPLET_START")) != null) {
                Token newToken = new Token(Token.Type.TRIPLET_START);
                newToken.setValue(bodyMatcher.group(bodyMap.get("TRIPLET_START")));
                tokens.add(newToken);
                continue;
            }
            
            else if (bodyMatcher.group(bodyMap.get("QUAD_START")) != null) {
                Token newToken = new Token(Token.Type.QUAD_START);
                newToken.setValue(bodyMatcher.group(bodyMap.get("QUAD_START")));
                tokens.add(newToken);
                continue;
            }
            
            
            else if (bodyMatcher.group(bodyMap.get("DOUBLE_BAR")) != null) {
                Token newToken = new Token(Token.Type.DOUBLE_BAR);
                newToken.setValue(bodyMatcher.group(bodyMap.get("DOUBLE_BAR")));
                tokens.add(newToken);
                continue;
            }
            
            else if (bodyMatcher.group(bodyMap.get("REPEAT_START")) != null) {
                Token newToken = new Token(Token.Type.REPEAT_START);
                newToken.setValue(bodyMatcher.group(bodyMap.get("REPEAT_START")));
                tokens.add(newToken);
                continue;
            }
            
            else if (bodyMatcher.group(bodyMap.get("REPEAT_END")) != null) {
                Token newToken = new Token(Token.Type.REPEAT_END);
                newToken.setValue(bodyMatcher.group(bodyMap.get("REPEAT_END")));
                tokens.add(newToken);
                continue;
            }
            
            else if (bodyMatcher.group(bodyMap.get("REPEAT_NUMBER")) != null) {
                Token newToken = new Token(Token.Type.REPEAT_NUMBER);
                newToken.setValue(bodyMatcher.group(bodyMap.get("REPEAT_NUMBER")));
                tokens.add(newToken);
                continue;
            }
            
            else if (bodyMatcher.group(bodyMap.get("BAR")) != null) {
                Token newToken = new Token(Token.Type.BAR);
                newToken.setValue(bodyMatcher.group(bodyMap.get("BAR")));
                tokens.add(newToken);
                continue;
            } 
            else if (bodyMatcher.group(bodyMap.get("VOICE")) != null) {
            	
                Token newToken = new Token(Token.Type.VOICE);
                newToken.setValue(bodyMatcher.group(bodyMap.get("VOICE")));
                tokens.add(newToken);
                System.out.println(newToken);
                continue;
            }
        }

        return tokens;
    }
    
}