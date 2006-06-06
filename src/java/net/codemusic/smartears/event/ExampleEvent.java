package net.codemusic.smartears.event;

import net.codemusic.smartears.*;

/**
 *This Event encapsulates information about events
 *dealing with examples
 */
public class ExampleEvent extends java.util.EventObject
    implements java.io.Serializable {
    protected IAnswer answer;
    protected int base = -1;

    /**
   *Returns the IAnswer involved in the event
   */
    public IAnswer getAnswer() {
        return answer;
    }

    /**
   *Returns an int representing the Midi note that the IAnswer
   *in this event should be built up from.  In ExampleEvents where
   *a IRangeManager has not explicitly set this value,
   *it will default to -1
   */
    public int getBase() {
        return base;
    }

    /**
   *Creates a new ExampleEvent
   *@param source the object that created this event
   *@param answer the IAnswer associated with this event
   */
    public ExampleEvent( Object source, IAnswer answer ) {
        super( source );
        this.answer = answer;
    }
}