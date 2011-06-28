package com.peterlyons.smartears;


/**
 *This interface represents an exercise in which an
 *example is played and the student is expected to reproduce
 *it's structure.  Reproduction exercises do not have 
 *a fixed number of possible answers
 */
public interface IReproductionExercise extends IExercise {

    /**
     *Returns the next answer that shoud be played
     */
    public IAnswer getNextAnswer();
}