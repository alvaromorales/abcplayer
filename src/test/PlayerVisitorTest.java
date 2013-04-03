package test;

import java.io.IOException;
import org.junit.Test;
import player.Main;

/**
 * Tests the PlayerVisitor class by testing the entire system together (Integration Testing)
 * Main.play uses the Lexer, Parser, DurationVisitor and PlayerVisitor to play a song
 * 
 * Testing Strategy
 *  - play fur_elise.abc
 *  - play invention.abc
 *  - play little_night_music.abc
 *  @category no_didit
 */
public class PlayerVisitorTest {
    
    /**
     * Plays fur_elise.abc
     */
    @Test
    public void playFurEliseTest() {
        String filename="sample_abc/fur_elise.abc";
        try{
            Main.play(filename);
        }
        catch(IOException e){
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }
    
    /**
     * Plays invention.abc
     */
    @Test
    public void playInventionTest() {
        String filename="sample_abc/invention.abc";
        try{
            Main.play(filename);
        }
        catch(IOException e){
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }
    
    /**
     * Plays little_night_music.abc
     */
    @Test
    public void playLittleNightMusicTest() {
        String filename="sample_abc/little_night_music.abc";
        try{
            Main.play(filename);
        }
        catch(IOException e){
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }
}
