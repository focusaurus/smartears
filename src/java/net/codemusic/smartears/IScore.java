package net.codemusic.smartears;


/**
 *This interface represents the user's 
 *Score
 */
public interface IScore {

    /**
   *Return the total number of correct responses
   *the user has made since the score was last reset
   */
    public int getCorrectCount();

    /**
   *Returns the total number of incorrect responses the user has 
   *made since the score was last reset.
   */
    public int getIncorrectCount();

    /**
   *Gets the users score, which is based on a certain number
   *of positive points for a correct answer (answers may be weighted differently)
   *and negative points for an incorrect response.
   */
    public int getScore();

    /**
   *Returns the number of times the user has requested additional 
   *hearings of examples
   */
    public int getRepeatCount();
}