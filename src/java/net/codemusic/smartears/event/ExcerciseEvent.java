package net.codemusic.smartears.event;

import net.codemusic.smartears.*;

/**
 *This class represents an event dealing with an IExcercise
 */
public class ExcerciseEvent extends java.util.EventObject
    implements java.io.Serializable {
    protected IExcercise excercise;

    /**
   *Creates a new ExcerciseEvent
   *@param source the object htat created this event
   *@param excercise the IExcercise associatod with this event
   */
    public ExcerciseEvent( Object source, IExcercise excercise ) {
        super( source );
        this.excercise = excercise;
    }

    /**
   *Returns the IExcercise associated with this event
   */
    public IExcercise getExcercise() {
        return excercise;
    }
}