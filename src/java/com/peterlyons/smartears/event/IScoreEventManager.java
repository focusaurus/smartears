package com.peterlyons.smartears.event;


/**
 *This interface represents a class that is responsible for 
 *registering, unregistering, and propogating ScoreEvents to ScoreListeners
 */
public interface IScoreEventManager {

    /**
   *Registers a ScoreListener
   *@param listener the listener to register.  This listener will 
   *be notified of all ScoreEvents
   */
    public void addScoreListener( ScoreListener listener );

    /**
   *Unregisters a ScoreListener
   *@param listener the listener to unregister.  This listener will 
   *no longer be notified of ScoreEvents
   */
    public void removeScoreListener( ScoreListener listener );

    /**
   *Propogates a ScoreEvent to all registered ScoreListeners
   *by calling their correct(ScoreEvent e) method
   */
    public void processCorrectEvent( ScoreEvent e );

    /**
   *Propogates a ScoreEvent to all registered ScoreListeners
   *by calling their incorrect(ScoreEvent e) method
   */
    public void processIncorrectEvent( ScoreEvent e );

    /**
   *Propogates a ScoreEvent to all registered ScoreListeners
   *by calling their reset(ScoreEvent e) method
   */
    public void processResetEvent( ScoreEvent e );

    /**
   *Propogates a ScoreEvent to all registered ScoreListeners
   *by calling their repeat(ScoreEvent e) method
   */
    public void processRepeatEvent( ScoreEvent e );
}