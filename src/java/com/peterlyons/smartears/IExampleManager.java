package com.peterlyons.smartears;


/**
 *The IExampleManager is responsible for controlling the 
 *example related operations of the system
 */
public interface IExampleManager {

    /**
   *Plays a new example for the 
   *user to answer/identify
   */
    public void play();

    /**
   *Repeats the previously played answer
   */
    public void repeat();

    /**
   *Enables an answer as part of this exercise and eligible for
   *playing
   */
    public void enableAnswer( IAnswer a );

    /**
   *Disables an answer so that it will not be eligible for playing 
   */
    public void disableAnswer( IAnswer a );

    /**
     *Returns the last answer that was played and therefore
     *is the current correct answer
     */
    public IAnswer getLastPlayedAnswer();
}