package player;

import java.util.ArrayList;

/**
 * Main entry point of your application.
 */
public class Main {

    /**
     * Plays the input file using Java MIDI API and displays
     * header information to the standard output stream.
     * 
     * (Your code should not exit the application abnormally using
     * System.exit().)
     * 
     * @param file the name of input abc file
     */
    public static void play(String file) {
        // YOUR CODE HERE
    }

//    public static void main(String[] args) {
        // CALL play() HERE
//    }
    
    public static void main(String[] args){
        String input = "| V B C D V: D D D B ||";

        ArrayList <Token> tokens = Lexer.Lexer(input);
        for (Token token : tokens)
            System.out.println(token.getValue());
    }

}
