package com.peterlyons.smartears;


/**
 *This interface represents a collection of IAnswers
 *that constitute a set of related structures to be learned 
 *together. Recognition Exercises have a fixed number of answers,
 *and should be presentable as multiple choice style exercises.
 */
public interface IRecognitionExercise extends IExercise {

    /**
   *Returns all of the IAnswers that constitute this exercise
   */
    public IAnswer[] getAnswers();
}