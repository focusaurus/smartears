package com.peterlyons.smartears;

import javax.sound.midi.*;

/**
 *This interface provides the service of actually playing 
 *sounds 
 */
public interface ISoundEngine {

    /**
   *Plays the supplied IAnswer at the supplied base note
   *@param answer the answer to play
   *@param base the bottom note of the answer
   */
    public void play( IAnswer answer, int base );

    /**
   *Call this method when you are done with a ISoundEngine object.
   *It releases any resources that the sound engine may be using. Calling any
   *methods after calling close() will generate an IllegalStateException
   */
    public void close();

    /**
   *Determines the amount of time between the beginning of each
   *note in an IAnswer
   */
    public void setDelay( int newDelay );

    /**
   *Sets the amount of time in abstract ticks that each 
   *note sounds
   */
    public void setDuration( int newDuration );

    /**
   *Returns an array of the instruments that this engine
   *is capable of emulating
   */
    public Instrument[] getInstruments();

    /**
   *Changes the instrument sound the engine uses
   *@param newInstrument the sound to emulate
   */
    public void setInstrument( Instrument newInstrument );
}