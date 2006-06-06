package net.codemusic.smartears;


/**
 *This interface represents a collection of IAnswers
 *that constitute a set of related structures to be learned 
 *together. Recognition Excercises have a fixed number of answers,
 *and should be presentable as multiple choice style excercises.
 */
public interface IRecognitionExcercise extends IExcercise {

    /**
   *Returns all of the IAnswers that constitute this excercise
   */
    public IAnswer[] getAnswers();
}