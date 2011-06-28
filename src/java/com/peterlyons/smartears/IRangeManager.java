package com.peterlyons.smartears;


/**
 *This interface manages the set of pitch boundaries 
 *that keep the sounds played within a certain range
 */
public interface IRangeManager {

    /**
   *This accessor method returns an int representing the
   *MIDI note value of the lowest allowable pitch
   */
    public int getLowerBound();

    /**
   *This accessor method sets the
   *MIDI note value of the lowest allowable pitch
   */
    public void setLowerBound( int upperBound );

    /**
   *This accessor method returns an int representing the
   *MIDI note value of the highest allowable pitch
   */
    public int getUpperBound();

    /**
   *This accessor method sets the
   *MIDI note value of the highest allowable pitch
   */
    public void setUpperBound( int top );

    /**
   *Returns true if the supplied note is within the
   *current range limitations and false otherwise
   */
    public boolean isWithinRange( int note );

    /**
   *returns a random base pitch that is within the current range
   *@return a MIDI note value that can be used as the base note
   *of this answer and keep all notes within the range
   *@exception a RangeException if the answer's span is larger than the range
   */
    public int selectBase( IAnswer answer ) throws RangeException;
}