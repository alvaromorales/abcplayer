package player;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import player.AST.*;
import player.NoteElement.Visitor;

public class DurationVisitor implements Visitor<Void> {
    private Set<Integer> denominators = new HashSet<Integer>();

    /**
     * Adds the denominator of a SingleNote to the environment HashSet of denominators
     * @param s the SingleNote to visit
     */
    @Override
    public Void visit(SingleNote s) {
        denominators.add(s.getDuration().getDenominator());
        return null;
    }

    /**
     * Adds the denominator of a Rest to the environment HashSet of denominators
     * @param r the Rest to visit
     */
    @Override
    public Void visit(Rest r) {
        denominators.add(r.getDuration().getDenominator());
        return null;
    }

    /**
     * Adds the denominator of a Chord to the environment HashSet of denominators
     * @param c the Chord to visit
     */
    @Override
    public Void visit(Chord c) {
        denominators.add(c.getDuration().getDenominator());
        return null;
    }

    /**
     * Adds the denominator of a Duplet to the environment HashSet of denominators
     * @param d the Duplet to visit
     */
    @Override
    public Void visit(Duplet d) {
        denominators.add(d.getDuration().getDenominator());
        return null;
    }

    /**
     * Adds the denominator of a Triplet to the environment HashSet of denominators
     * @param t the Triplet to visit
     */
    @Override
    public Void visit(Triplet t) {
        denominators.add(t.getDuration().getDenominator());
        return null;
    }

    /**
     * Adds the denominator of a Triplet to the environment HashSet of denominators
     * @param t the Triplet to visit
     */
    @Override
    public Void visit(Quadruplet q) {
        denominators.add(q.getDuration().getDenominator());
        return null;
    }

    /**
     * Adds all the denominators of the notes in a voice to the environment HashSet of denominators
     * @param v the Voice to visit
     */
    @Override
    public Void visit(Voice v) {
        for (NoteElement n : v.getNotes()) {
            n.accept(this);
        }
        return null;
    }

    /**
     * Adds all the denominators of the notes in a song to the environment HashSet of denominators
     * @param s the Song to visit
     */
    @Override
    public Void visit(Song s) {
        for (Voice v : s.getVoices()) {
            v.accept(this);
        }
        return null;
    }
   
    /**
     * Find the ticksPerQuarter for a NoteElement
     * Iteratively compute the LCM of the denominators
     * Code adapted from http://stackoverflow.com/questions/4201860/how-to-find-gcf-lcm-on-a-set-of-numbers
     * @return the ticksPerQuarter of a NoteElement
     */
    public int findTicksPerQuarter() {
        List<Integer> denominators = new ArrayList<Integer>(this.denominators);
        if (denominators.size() > 0) {
            int result = denominators.get(0);
            
            if (denominators.size() < 2) {
                return result;
            }
            
            for (int i = 1; i<denominators.size();i++) {
                result = RationalNumber.lcm(result, denominators.get(i));
            }
            return result;
        } else {
            return 0;
        }

    }
    
}
