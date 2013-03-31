package player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Lexer{
    private String s;
    
    /**
     * Creates a new Lexer object
     * @param string
     */
    public Lexer(String s) {
        this.s = s;
    }
    
    // Create a map, mapping token types to their numbers in order
    private Map<String,Integer> map = new HashMap<String,Integer>();
    
    /**
     * Creates a Map, mapping token types to numbers
     */
    private void createTypeMap() {
        map.put("COMPOSER", 1);
        map.put("KEY", 2);
        map.put("LENGTH", 3);
        map.put("METER", 4);
        map.put("TEMPO", 5);
        map.put("TITLE", 6);
        map.put("INDEX", 7);
        map.put("KEYNOTE", 8);
        map.put("REST", 9);
        map.put("CHORD_START", 10);
        map.put("CHORD_END", 11);
        map.put("DUPLET_START", 12);
        map.put("TRIPLET_START", 13);
        map.put("QUAD_START", 14);
        map.put("BAR", 15);
        map.put("DOUBLE_BAR", 16);
        map.put("REPEAT_START", 17);
        map.put("REPEAT_END", 18);
        map.put("REPEAT_NUMBER", 19);
        map.put("VOICE", 20);
    }

    
    /**
     * Creates a string for pattern 
     * @return StringBuffer
     */
    private StringBuffer patternMaker(){
        StringBuffer tokensBuf = new StringBuffer();
        //1- add COMPOSER
        tokensBuf.append("((?<=C:)[A-Z a-z.-]+(?=(\n)|(%)))");
        tokensBuf.append("|");
        //2- add KEY
        tokensBuf.append("((?<=K:)[A-Za-z]+(?=(\n)|(%)))");
        tokensBuf.append("|");
        //3- add LENGTH
        tokensBuf.append("((?<=L:)[0-9]+/[0-9]+(?=(\n)|(%)))");
        tokensBuf.append("|");
        //4- add METER
        tokensBuf.append("((?<=M:)[0-9]+/[0-9]+(?=(\n)|(%)))");
        tokensBuf.append("|");
        //5- add TEMPO
        tokensBuf.append("((?<=Q:)[0-9]+(?=(\n)|(%)))");
        tokensBuf.append("|");
        //6- add TITLE
        tokensBuf.append("((?<=T:)[A-Z a-z]+(?=(\n)|(%)))");
        tokensBuf.append("|");
        //7- add INDEX
        tokensBuf.append("((?<=X:)[0-9]+(?=(\n)|(%)))");
        tokensBuf.append("|");
        
        //8- add KEYNOTE
        tokensBuf.append("(((^)|(^^)|(_)|(__)|(=))?[A-Ga-g]((,*)|('*)) ([0-9]*/?[0-9]*))");
        tokensBuf.append("|");
        //9- add REST
        tokensBuf.append("(z)");
        tokensBuf.append("|");
        //10- add CHORD_START
        tokensBuf.append("([(?![1-2]))");
        tokensBuf.append("|");
        //11- add CHORD_END
        tokensBuf.append("(])");
        tokensBuf.append("|");
        //12- add DUPLET_START 
        tokensBuf.append("((2)");
        tokensBuf.append("|");
        //13- add TRIPLET_START
        tokensBuf.append("((3)");
        tokensBuf.append("|");
        //14- add QUAD_START
        tokensBuf.append("((4)");
        tokensBuf.append("|");
        //15- add BAR
        tokensBuf.append("(|)");
        tokensBuf.append("|");
        //16- add DOUBLE_BAR
        tokensBuf.append("(||)");
        tokensBuf.append("|");
        //17- add REPEAT_START
        tokensBuf.append("(|:)");
        tokensBuf.append("|");
        //18- add REPEAT_END
        tokensBuf.append("(:|)");
        tokensBuf.append("|");
        //19- add REPEAT_NUMBER
        tokensBuf.append("([[1-2])");
        tokensBuf.append("|");
        //20- add VOICE
        tokensBuf.append("((?<=V:)[A-Z a-z.-]+(?=(\n)|(%)))");
        tokensBuf.append("|");
        //21- add regex for comment, we won't consider it later
        tokensBuf.append("((?<=%)[a-zA-Z_0-9]*(?=\n))");
        tokensBuf.append("|");
        //22- add an extra character, we will use this to detect if there's syntax error or not
        tokensBuf.append(".?");
        return tokensBuf;
        
    }
    
    
    /**
     * Creates a list of tokens from the given abc string
     * @return ArrayList
     */
    public ArrayList<Token> lex(){
        
        // create a map
        createTypeMap();
        
        
        // Create a pattern
        ArrayList <Token> tokens = new ArrayList<Token>();
        
        Pattern tokenPatterns = Pattern.compile(new String(patternMaker()));
        
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
            
            
            
            
            else if (matcher.group(map.get("KEYNOTE")) != null) {
                Token newToken = new Token(Token.Type.KEYNOTE);
                newToken.setValue(matcher.group(map.get("KEYNOTE")));
                newToken.parseValue();
                
                tokens.add(newToken);
                continue;
            }
            
            else if (matcher.group(map.get("INDEX")) != null) {
                Token newToken = new Token(Token.Type.INDEX);
                newToken.setValue(matcher.group(map.get("INDEX")));
                tokens.add(newToken);
                continue;
            }
            
            else if (matcher.group(map.get("INDEX")) != null) {
                Token newToken = new Token(Token.Type.INDEX);
                newToken.setValue(matcher.group(map.get("INDEX")));
                tokens.add(newToken);
                continue;
            }
            
            else if (matcher.group(map.get("INDEX")) != null) {
                Token newToken = new Token(Token.Type.INDEX);
                newToken.setValue(matcher.group(map.get("INDEX")));
                tokens.add(newToken);
                continue;
            }
            
            else if (matcher.group(map.get("INDEX")) != null) {
                Token newToken = new Token(Token.Type.INDEX);
                newToken.setValue(matcher.group(map.get("INDEX")));
                tokens.add(newToken);
                continue;
            }
            
            else if (matcher.group(map.get("INDEX")) != null) {
                Token newToken = new Token(Token.Type.INDEX);
                newToken.setValue(matcher.group(map.get("INDEX")));
                tokens.add(newToken);
                continue;
            }
            
            else if (matcher.group(map.get("INDEX")) != null) {
                Token newToken = new Token(Token.Type.INDEX);
                newToken.setValue(matcher.group(map.get("INDEX")));
                tokens.add(newToken);
                continue;
            }
            
            else if (matcher.group(map.get("INDEX")) != null) {
                Token newToken = new Token(Token.Type.INDEX);
                newToken.setValue(matcher.group(map.get("INDEX")));
                tokens.add(newToken);
                continue;
            }
            
            else if (matcher.group(map.get("INDEX")) != null) {
                Token newToken = new Token(Token.Type.INDEX);
                newToken.setValue(matcher.group(map.get("INDEX")));
                tokens.add(newToken);
                continue;
            }
            
            else if (matcher.group(map.get("INDEX")) != null) {
                Token newToken = new Token(Token.Type.INDEX);
                newToken.setValue(matcher.group(map.get("INDEX")));
                tokens.add(newToken);
                continue;
            }
            
            else if (matcher.group(map.get("INDEX")) != null) {
                Token newToken = new Token(Token.Type.INDEX);
                newToken.setValue(matcher.group(map.get("INDEX")));
                tokens.add(newToken);
                continue;
            }
            
            else if (matcher.group(map.get("INDEX")) != null) {
                Token newToken = new Token(Token.Type.INDEX);
                newToken.setValue(matcher.group(map.get("INDEX")));
                tokens.add(newToken);
                continue;
            }
            
            else if (matcher.group(map.get("INDEX")) != null) {
                Token newToken = new Token(Token.Type.INDEX);
                newToken.setValue(matcher.group(map.get("INDEX")));
                tokens.add(newToken);
                continue;
            }
            
        }

        
        return tokens;
    }
    
    
}