package net.codemusic.smartears;


/**
 *This interface represents an excercise in which an
 *example is played and the student is expected to reproduce
 *it's structure.  Reproduction excercises do not have 
 *a fixed number of possible answers
 */
public interface IReproductionExcercise extends IExcercise {

    /**
     *Returns the next answer that shoud be played
     */
    public IAnswer getNextAnswer();
}