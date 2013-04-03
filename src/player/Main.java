package player;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import javax.sound.midi.MidiUnavailableException;
import sound.SequencePlayer;

/**
 * Main entry point of your application.
 */
public class Main {

	/**
	 * Reads file from filename to a single String
	 * @param path, the path to file. If file not found, IOException is thrown.
	 * @return String, the contents of the file in the default charset.
	 */
	
	private static String readFile(String path) throws IOException {
		
		  FileInputStream stream = new FileInputStream(new File(path));
		  try {
		    FileChannel fc = stream.getChannel();
		    MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
		    /* Instead of using default, pass in a decoder. */
		    return Charset.defaultCharset().decode(bb).toString();
		  }
		  finally {
		    stream.close();
		  }
		}
    /**
     * Plays the input file using Java MIDI API and displays
     * header information to the standard output stream.
     * 
     * (Your code should not exit the application abnormally using
     * System.exit().)
     * 
     * @param file the name of input abc file
     */
    public static void play(String file) throws IOException {
    	String input_string=readFile(file);

    	Lexer lexer = new Lexer(input_string);
    	Parser parser = new Parser();
    	parser.parse(lexer.lexHead(),lexer.lexBody());
            	
    	DurationVisitor durationV = new DurationVisitor();
        durationV.visit(parser.getSong());
        
        PlayerVisitor visitor = new PlayerVisitor(durationV.getTicksPerQuarter(),parser.getSong().getTempo(),parser.getSong().getDefaultNoteLength());
        visitor.visit(parser.getSong());
        SequencePlayer player = visitor.getPlayer();
        
        try {
            player.play();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    		
    }
    
    public static void main(String[] args){
        String filename="sample_abc/fur_elise.abc";
        try{
        	play(filename);
        }
        catch(IOException e){
        	throw new RuntimeException("Error reading file");
        }
       
    }
}
