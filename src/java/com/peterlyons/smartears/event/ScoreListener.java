package com.peterlyons.smartears.event;


/**
 *This interface should be implemented my classes
 *wishing to tie in to the ScoreEvent subsystem
 */
public interface ScoreListener {

    /**
   *Will be called when the user correctly answers/identifies an example
   *@param e the ScoreEvent associated with this action
   */
    public void correct( ScoreEvent e );

    /**
   *Will be called when the user incorrectly answers/identifies an example
   *@param e the ScoreEvent associated with this action
   */
    public void incorrect( ScoreEvent e );

    /**
   *Will be called when the user requests an additional hearing of an example
   *@param e the ScoreEvent associated with this action
   */
    public void repeat( ScoreEvent e );

    /**
   *Will be called when the user's score has been re-initialized
   *@param e the ScoreEvent associated with this action
   */
    public void reset( ScoreEvent e );
}