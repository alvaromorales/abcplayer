package ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import player.RationalNumber;
import player.AccidentalAssociationMaker;

/**
 * Class representing a song
 */
public class Song implements NoteElement {
    private Map<String,Voice> voices = new HashMap<String,Voice>();
    private Voice currentVoice=null;
    private String composer;
    private String keySignature;
    private RationalNumber defaultNoteLength;
    private RationalNumber meter;
    private int tempo;
    private String title;
    private int index;
    public AccidentalAssociationMaker accidentalAssociator=new AccidentalAssociationMaker();
    
    /**
     * Creates a Song object
     */
    public Song() {
        this.meter = new RationalNumber(4,4);
        this.defaultNoteLength = new RationalNumber(1,8);
        this.tempo = 100;
    }
    
    /**
     * Returns list of voices in song
     * @return list of voices in song
     */
    public List<Voice> getVoices(){
        return new ArrayList<Voice>(voices.values());
    }
    
    /**
     * Adds a new note to the song.
     * The new note is added to the current Voice.
     * If no voice exists, create one with voice.name=null
     * @param e, the NoteElement to add
     */
    public void add(NoteElement e){
        if(currentVoice == null){
            this.addVoice(new Voice(null));
            this.currentVoice= voices.get(null);
        }
        currentVoice.addNote(e);
    }
    
    /**
     * Adds a Voice to the song
     * @param v the Voice to add
     */
    public void addVoice(Voice v) {
        voices.put(v.getName(),v);                   //adds a voice object to the HashMap
    }

    /**
     * If voice exists, changes to that voice.
     * @param name, the name of the Voice to fetch
     */
    public void getVoice(String name) {         //handles Voice tokens both in header and body
        if(voices.containsKey(name)) {          //if voice exists
            currentVoice=voices.get(name); //fetch that voice
        }
    }
    
    /**
     * Accepts a visitor
     */
    @Override
    public <E> E accept(Visitor<E> v) {
        return v.visit(this);
    }
    

    /**
     * Gets the RationalNumber duration of all the Voices in the Song
     * @return the RationalNumber duration of all the Voices in the Song
     */
    @Override
    public RationalNumber getDuration() {
        RationalNumber result = new RationalNumber(0, 1);
        for (Voice v: this.voices.values()) {
            result = result.add(v.getDuration());
        }
        return result;
    }
    
    /**
     * Gets the name of the composer of the song.
     * The composer header field is labeled "C:".
     * @return the name of the composer of the song.
     */
    public String getComposer() {
        return composer;
    }

    /**
     * Sets the name of the composer of the song.
     * The composer header field is labeled "C:".
     * @param composer the name of the composer of the song.
     */
    public void setComposer(String composer){
        this.composer = composer;
    }

    /**
     * Gets the key signature of the song.
     * The key signature header field is labeled "K:".
     * @return the key signature of the song.
     */
    public String getKeySignature() {
        return keySignature;
    }

    /**
     * Sets the key signature of the song.
     * The key signature header field is labeled "K:".
     * @param keySignature the key signature of the song.
     */
    public void setKeySignature(String keySignature) {
        this.keySignature = keySignature;
        this.accidentalAssociator=new player.AccidentalAssociationMaker(keySignature);
    }

    /**
     * Gets the default note length of the song.
     * The default note length header field is labeled "L:".
     * @return the default note length of the song.
     */
    public RationalNumber getDefaultNoteLength() {
        return defaultNoteLength;
    }

    /**
     * Sets the default note length of the song
     * The default note length header field is labeled "L:".
     * @param defaultNoteLength the default note length of the song.
     */
    public void setDefaultDuration(RationalNumber defaultNoteLength) {
        this.defaultNoteLength = defaultNoteLength;
    }

    /**
     * Gets the meter of the song.
     * The meter header field is labeled "M:".
     * @return the meter of the song.
     */
    public RationalNumber getMeter() {
        return meter;
    }

    /**
     * Sets the meter of the song
     * The meter header field is labeled "M:".
     * @param meter the meter of the song
     */
    public void setMeter(RationalNumber meter) {
        this.meter = meter;
    }

    /**
     * Gets the tempo of the song
     * The tempo header field is labeled "Q:".
     * @return the tempo of the song
     */
    public int getTempo() {
        return tempo;
    }

    /**
     * Sets the tempo of the song
     * The tempo header field is labeled "Q:".
     * @param tempo the tempo of the song.
     */
    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    /**
     * Gets the title of the song
     * The title header field is labeled "T:".
     * @return the title of the song
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the song
     * The title header field is labeled "T:".
     * @param title the title of the song
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the index of the song.
     * The index header field is labeled "X:".
     * @return the index of the song.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Sets the index of the song.
     * The index header field is labeled "X:".
     * @param index the index of the song.
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Checks if a Song is equal to another Song
     * Auto-generated by eclipse
     * @param o the Object to compare to
     * @return true if equal, else false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Song other = (Song) obj;
        if (composer == null) {
            if (other.composer != null)
                return false;
        } else if (!composer.equals(other.composer))
            return false;
        if (defaultNoteLength == null) {
            if (other.defaultNoteLength != null)
                return false;
        } else if (!defaultNoteLength.equals(other.defaultNoteLength))
            return false;
        if (index != other.index)
            return false;
        if (keySignature == null) {
            if (other.keySignature != null)
                return false;
        } else if (!keySignature.equals(other.keySignature))
            return false;
        if (meter == null) {
            if (other.meter != null)
                return false;
        } else if (!meter.equals(other.meter))
            return false;
        if (tempo != other.tempo)
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (voices == null) {
            if (other.voices != null)
                return false;
        } else if (!voices.equals(other.voices))
            return false;
        return true;
    }

    /**
     * Gets the string representation of a Song
     * @return the string representation of a Song
     */
    @Override
    public String toString() {
        return "Song [composer=" + composer + ", keySignature=" + keySignature + ", defaultNoteLength="
                + defaultNoteLength + ", meter=" + meter + ", tempo="
                + tempo + ", title=" + title + ", index=" + index + "\nvoices=" + voices + "]";
    }
    
}