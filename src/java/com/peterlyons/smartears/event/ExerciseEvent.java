package com.peterlyons.smartears.event;

import com.peterlyons.smartears.*;

/**
 *This class represents an event dealing with an IExercise
 */
public class ExerciseEvent extends java.util.EventObject
    implements java.io.Serializable {
    protected IExercise exercise;

    /**
   *Creates a new ExerciseEvent
   *@param source the object htat created this event
   *@param exercise the IExercise associatod with this event
   */
    public ExerciseEvent( Object source, IExercise exercise ) {
        super( source );
        this.exercise = exercise;
    }

    /**
   *Returns the IExercise associated with this event
   */
    public IExercise getExercise() {
        return exercise;
    }
}