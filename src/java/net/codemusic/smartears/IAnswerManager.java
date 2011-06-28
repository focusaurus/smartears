package net.codemusic.smartears;


/**
 *This interface is responsible for managing a user-alterable
 *set of answers.  From this set of answers, it can select the 
 *answer that will be the correct response to the next exercise.
 */
public interface IAnswerManager {

    /**
   *Makes an answer eligible for selection to be played and counted as 
   *a correct response.
   */
    public void add( IAnswer possibleAnswer );

    /**
   *Removes an answer from the selection group so that it wil no longer 
   *be chosen for playing.
   */
    public void remove( IAnswer answerToRemove );

    /**
   *Removes all answers from this answer manager.
   */
    public void removeAll();

    /**
   *Picks the IAnswer that will be the correct response to the next exercise 
   *according to the current IAnswerSelectionPolicy
   */
    public IAnswer select() throws NoPossibleAnswersException;

    /**
   *Returns an array of all the currently enabled answers
   */
    public IAnswer[] getAnswers();

    /**
   *This accessor method can be used to set the strategy
   *used to select the next answer
   */
    public void setSelectionStrategy( IAnswerSelectionStrategy iass );
}