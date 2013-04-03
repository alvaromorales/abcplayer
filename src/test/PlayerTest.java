package test;

import java.io.IOException;
import org.junit.Test;
import player.Main;

/**
 * Tests the entire system together (Integration Testing)
 * Main.play uses the Lexer, Parser, DurationVisitor and PlayerVisitor to play a song
 * We wrote the abc files from sheet music found www.gamemusicthemes.com
 * 
 * Testing Strategy
 *  - play pokemon.abc
 *  - play zelda.abc
 *  - play jack.abc
 *  @category no_didit
 */
public class PlayerTest {
    
    /**
     * Plays pokemon.abc, the Pokemon Blue and Red gameboy theme
     * This file tests chords nested in tuples
     */
    @Test
    public void playFurEliseTest() {
        String filename="sample_abc/pokemon.abc";
        try{
            Main.play(filename);
        }
        catch(IOException e){
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }
    
    /**
     * Plays zelda.abc, the Song of Storms from Zelda Ocarina of Time
     * This file tests playing a song with different voices and repeats
     */
    @Test
    public void playInventionTest() {
        String filename="sample_abc/zelda.abc";
        try{
            Main.play(filename);
        }
        catch(IOException e){
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }
    
    /**
     * Plays jack.abc, Hit the Road Jack
     * This file tests numbered repeats
     */
    @Test
    public void playLittleNightMusicTest() {
        String filename="sample_abc/jack.abc";
        try{
            Main.play(filename);
        }
        catch(IOException e){
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }
}
