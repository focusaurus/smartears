package com.peterlyons.smartears;

import com.peterlyons.smartears.event.*;

/**
 *The example manager controls the portion of the system
 *that handles all example-related activities such as
 *playing, repeating, enabling, and disabling answers.
 */
public class ExampleManager implements IExampleManager {

    /**
   *Each play event is stored so it can be repeated
   */
    private PlayEvent lastPlayEvent;

    /**
   *Handles propogation of exampleEvents to the listeners
   */
    private IExampleEventManager exampleEventManager;

    /**
   *Responsible for managing the list of active answers and picking
   *which answer to play next
   */
    private IAnswerManager answerManager;

    /**
   *Picks a base note for the answer and ensures
   *the answer lies within the user's desired range
   *of pitches
   */
    private IRangeManager rangeManager;

    /**
   *Constructs a new examplemanager that will communicate with the supplied object
   */
    public ExampleManager( IExampleEventManager eem, IAnswerManager am, 
                           IRangeManager rm ) {
        exampleEventManager = eem;
        answerManager = am;
        rangeManager = rm;
    }

    /**
   *Plays an example that the user must answer/identify
   */
    public void play() {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( "ExamlpeManager.play()" );
        }
        try {
            exampleEventManager.processExamplePlayed( 
                    lastPlayEvent = new PlayEvent( this, answerManager, 
                                                   rangeManager ) );
        } catch ( NoPossibleAnswersException e ) {
            com.peterlyons.smartears.ui.MainFrame.setStatus( 
                    "Please select at least one answer to work on" );
            //   System.out.println("Please select at least one answer to work on");
            //BUGBUG send out a new SettingsEvent or something
        }
         catch ( RangeException e ) {
            System.out.println( 
                    "Your working range is too small for some of your answers." );
            //BUGBUG send out a new SettingsEvent or something
        }
    }

    /**
   *Plays the previous example again
   */
    public void repeat() {
        exampleEventManager.processExampleRepeated( new RepeatEvent( this, 
                                                                     lastPlayEvent ) );
    }

    /**
   *Adds an answer to the collection of possible answers
   */
    public void enableAnswer( IAnswer a ) {
        exampleEventManager.processAnswerEnabled( new ExampleEvent( this, a ) );
    }

    /**
   *Removes an answer from the collection of possible answers
   */
    public void disableAnswer( IAnswer a ) {
        exampleEventManager.processAnswerDisabled( new ExampleEvent( this, a ) );
    }

    /**
     *@see IExampleManager#getLastPlayedAnswer()
     */
    public IAnswer getLastPlayedAnswer() {
        return lastPlayEvent.getAnswer();
    }
}