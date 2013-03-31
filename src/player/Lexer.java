package player;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Lexer{
    
    
    private static StringBuffer patternMaker(){
        StringBuffer tokensBuf = new StringBuffer();
        //1- add COMPOSER
        tokensBuf.append("((?<=C:)[A-Z a-z.-]+(?=\n))");
        tokensBuf.append("|");
        //2- add KEY
        tokensBuf.append("((?<=K:)[A-Za-z]+(?=\n))");
        tokensBuf.append("|");
        //3- add LENGTH
        tokensBuf.append("((?<=L:)[0-9]+/[0-9]+(?=\n))");
        tokensBuf.append("|");
        //4- add METER
        tokensBuf.append("((?<=M:)[0-9]+/[0-9]+(?=\n))");
        tokensBuf.append("|");
        //5- add TEMPO
        tokensBuf.append("((?<=Q:)[0-9]+(?=\n))");
        tokensBuf.append("|");
        //6- add TITLE
        tokensBuf.append("((?<=T:)[A-Z a-z]+(?=\n))");
        tokensBuf.append("|");
        //7- add INDEX
        tokensBuf.append("((?<=X:)[0-9]+(?=\n))");
        tokensBuf.append("|");
        
        //8- add KEYNOTE
        tokensBuf.append("([A-Ga-g](?!:)[,_0-9/^]*)");
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
        tokensBuf.append("((?!:)|(?!:))");
        tokensBuf.append("|");
        //16- add REPEAT_START
        tokensBuf.append("(|:)");
        tokensBuf.append("|");
        //17- add REPEAT_END
        tokensBuf.append("(:|)");
        tokensBuf.append("|");
        //18- add REPEAT_NUMBER
        tokensBuf.append("([[1-2])");
        tokensBuf.append("|");
        //19- add END_LINE
        tokensBuf.append("(\n)");
        tokensBuf.append("|");
        //20- add VOICE
        tokensBuf.append("(V)");
        
        return tokensBuf;
        
    }
    
    public static  ArrayList<Token> Lexer(String input){
        ArrayList <Token> tokens = new ArrayList<Token>();
        
        
        Pattern tokenPatterns = Pattern.compile(new String(patternMaker()));
        
        Matcher matcher = tokenPatterns.matcher(input);
        
        while (matcher.find()) {
            
            System.out.println(matcher.group(3));
            if (matcher.group(map["VOICE"]) != null) {
                Token newToken = new Token(Token.Type.VOICE);
                tokens.add(newToken);
            }
        }

        
        return tokens;
    }
    
    
}