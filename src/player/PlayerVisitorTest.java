package player;
import java.io.IOException;
import org.junit.Test;

/**
 * Tests the PlayerVisitor class
 * Testing Strategy
 *  - play fur_elise.abc
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
}
