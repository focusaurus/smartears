package net.codemusic.smartears;


/**
 *This interface represents a class that is responsible
 *for storing the user's current preference settings
  */
public interface ISettings {

    /**
   *Indicates that answers should be played from low to high
   */
    public final static int ASCENDING = 0;

    /**
   *Indicates that answers should be played from high to low
   */
    public final static int DESCENDING = 1;

    /**
   *Indicates that ASCDENDING and DESCENDING should be randomly mixed
   */
    public final static int MIXED = 2;

    /**
   *Returns an int representing the number of ticks between the start of
   *the notes of an IAnswer
   */
    public int getDelay();

    /**
   *Returns an int that represents the number of ticks each note lasts
   */
    public int getDuration();

    /**
   *Returns the current MIDI instrument that is used to create the timbre
   */
    public javax.sound.midi.Instrument getInstrument();

    /**
   *Returns the current play mode - either
   *ASCENDING or DESCENDING
   */
    public int getPlayMode();
}