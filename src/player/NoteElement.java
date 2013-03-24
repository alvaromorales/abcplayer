package player;
import player.AST.*;

public interface NoteElement {
    
    /**
     * Interface to implement the visitor pattern on the abcplayer
     */
    public interface Visitor<E> {
        public E visit(SingleNote s);
        public E visit(Rest r);
        public E visit(Chord c);
        public E visit(Duplet d);
        public E visit(Triplet t);
        public E visit(Quadruplet q);
        public E visit(Voice v);
        public E visit(Song s);
    }
    
    public <E> E accept(Visitor<E> v);
}
