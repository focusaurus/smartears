package com.peterlyons.smartears;


/**
 *This interface represents the class that is
 *responible for keeping score and creating score
 *related events
 */
public interface IScoreManager {

    /**
   *Called when you user has chosen the correct answer
   *@param answer the correct answer just chosen
   */
    public void correct( IAnswer answer );

    /**
   *Called when the user has chosen an incorrect answer
   *@param answer the incorrect answer just chosen
   */
    public void incorrect( IAnswer answer );

    /**
   *Called when the user has requested an additional
   *hearing of an example
   *@param answer the answer to be repeated
   */
    public void repeat( IAnswer answer );

    /**
   *Resets all scores and statistics to their initial values
   */
    public void reset();

    /**
   *Returns the total number of times the user has correctly identified
   *an answer
   */
    public int getCorrectCount();

    /**
   *Returns the total number of times the user has guessed incorrectly
   */
    public int getIncorrectCount();

    /**
   *Returns the total number of times the user has 
   *had a question played over
   */
    public int getRepeatCount();

    /**
   *Returns the users score in "points" based on the weights
   *of particular answers
   */
    public int getScore();
}