package net.codemusic.smartears;


/**
 *This interface represents a collection of IAnswers
 *that constitute a set of related structures to be learned 
 *together
 */
public interface IExcercise {

    /**
     *Returns the name of this excercise, e.g. Intervals, Basic Chords, etc
     */
    public String getName();

    /**
     *Returns a brief description of what kind of sounds
     *are in the excercise
     */
    public String getDescription();
}