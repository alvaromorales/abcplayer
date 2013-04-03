package test;

import player.AccidentalAssociationMaker;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests the AccidentalAssociationMaker (AAM) class
 * Testing Strategy:
 *  - test initialization with a major key
 *  - test getting a note with an accidental
 *  - test setting a local accidental
 *  - test revert
 */
public class AccidentalAssociationMakerTest {

    /**
     * Tests getting a note with an accidental
     */
    @Test
    public void getAccidentalTest() {
        AccidentalAssociationMaker associator = new AccidentalAssociationMaker("E");
        assertEquals(1, associator.getAccidental('F'));
    }

    /**
     * Tests that an AAM (initialized with key C) sets the correct defaults
     */
    @Test
    public void initTest() {
        AccidentalAssociationMaker associator = new AccidentalAssociationMaker("C");
        assertTrue(
                associator.getAccidental('A') == 0 && 
                associator.getAccidental('B') == 0 && 
                associator.getAccidental('C') == 0 && 
                associator.getAccidental('D') == 0 && 
                associator.getAccidental('E') == 0 && 
                associator.getAccidental('F') == 0 && 
                associator.getAccidental('G') == 0);
    }

    /**
     * Test setting a local accidental
     */
    @Test
    public void setAccidentalTest() {
        AccidentalAssociationMaker associator = new AccidentalAssociationMaker("B");
        // the accidental for 'A' is normally 0, we set it to 1
        associator.setAccidental('A', 1);
        assertEquals(1, associator.getAccidental('A'));
    }

    /**
     * Test reverting an AAM
     */
    @Test
    public void revertTest() {
        AccidentalAssociationMaker associator = new AccidentalAssociationMaker("Ab");

        //some local changes
        associator.setAccidental('B', 0);
        associator.setAccidental('A', 0);
        associator.setAccidental('G', 1);

        associator.revert();

        assertEquals(new AccidentalAssociationMaker("Ab"), associator);
    }

}
