package com.peterlyons.smartears.event;


/**
 *Occurs when the user requests another hearing of a previously played Example
 */
public class RepeatEvent extends ExampleEvent implements java.io.Serializable {

    /**
   *Creates a new RepeatEvent
   *@param source the object that created this RepeatEvent
   *@param eventToRepeat the event that should be played again
   */
    public RepeatEvent( Object source, PlayEvent eventToRepeat ) {
        super( source, eventToRepeat.getAnswer() );
        answer = eventToRepeat.getAnswer();
        base = eventToRepeat.getBase();
    }
}