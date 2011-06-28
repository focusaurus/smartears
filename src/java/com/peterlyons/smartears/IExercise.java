package com.peterlyons.smartears;


/**
 *This interface represents a collection of IAnswers
 *that constitute a set of related structures to be learned 
 *together
 */
public interface IExercise {

    /**
     *Returns the name of this exercise, e.g. Intervals, Basic Chords, etc
     */
    public String getName();

    /**
     *Returns a brief description of what kind of sounds
     *are in the exercise
     */
    public String getDescription();
}