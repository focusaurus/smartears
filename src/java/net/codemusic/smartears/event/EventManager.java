package net.codemusic.smartears.event;

import java.util.*;

import net.codemusic.smartears.*;

/**
 *This class handles registering, unregistering, and notifying all of the
 *model-related event listeners in the SmartEars system.  The Managers should begin
 *event propogation by calling the appropriate processXXX() method in this class.  
 *The methods are self-explainatory.
 */
public class EventManager implements IExampleEventManager,
                                     IScoreEventManager,
                                     ISettingsEventManager,
                                     IExerciseEventManager {
    private Vector exampleListeners = new Vector( 25 );
    private Vector scoreListeners = new Vector( 3 );
    private Vector settingsListeners = new Vector( 3 );
    private Vector exerciseListeners = new Vector( 3 );

    public void addExampleListener( ExampleListener e ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( 
                    "EventManager.addExampleListener()-" + 
                    e.getClass().getName() );
        }
        exampleListeners.add( e );
    }
    public void removeExampleListener( ExampleListener e ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( "EventManager.removeExampleListener()" );
        }
        exampleListeners.remove( e );
    }
    public void addExerciseListener( ExerciseListener e ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( "EventManager.addExerciseListener()" );
        }
        exerciseListeners.add( e );
    }
    public void removeExerciseListener( ExerciseListener e ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( "EventManager.removeExerciseListener()" );
        }
        exerciseListeners.remove( e );
    }
    public void addScoreListener( ScoreListener e ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( "EventManager.addScoreListener()" );
        }
        scoreListeners.add( e );
    }
    public void removeScoreListener( ScoreListener e ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( "EventManager.removeScoreListener()" );
        }
        scoreListeners.remove( e );
    }
    public void addSettingsListener( SettingsListener e ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( "EventManager.addSettingsListener()" );
        }
        settingsListeners.add( e );
    }
    public void removeSettingsListener( SettingsListener e ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( "EventManager.removeSettingsListener()" );
        }
        settingsListeners.remove( e );
    }
    public void processExamplePlayed( ExampleEvent e ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( "EventManager.processExamplePlayed()" );
        }
        for ( int i = 0; i < exampleListeners.size(); i++ ) {
            ( (ExampleListener)exampleListeners.elementAt( i ) ).examplePlayed( 
                    e );
        }
    }
    public void processExampleRepeated( ExampleEvent e ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( "EventManager.processExampleRepeated()" );
        }
        for ( int i = 0; i < exampleListeners.size(); i++ ) {
            ( (ExampleListener)exampleListeners.elementAt( i ) ).exampleRepeated( 
                    e );
        }
    }
    public void processAnswerEnabled( ExampleEvent e ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( "EventManager.processAnswerEnabled()" );
        }
        for ( int i = 0; i < exampleListeners.size(); i++ ) {
            ( (ExampleListener)exampleListeners.elementAt( i ) ).answerEnabled( 
                    e );
        }
    }
    public void processAnswerDisabled( ExampleEvent e ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( "EventManager.processAnswerDisabled()" );
        }
        for ( int i = 0; i < exampleListeners.size(); i++ ) {
            ( (ExampleListener)exampleListeners.elementAt( i ) ).answerDisabled( 
                    e );
        }
    }
    public void processCorrectEvent( ScoreEvent e ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( "EventManager.processCorrectEvent()" );
        }
        for ( int i = 0; i < scoreListeners.size(); i++ ) {
            ( (ScoreListener)scoreListeners.elementAt( i ) ).correct( e );
        }
    }
    public void processExerciseChanged( ExerciseEvent e ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( "EventManager.processExerciseEvent()" );
        }
        for ( int i = 0; i < exerciseListeners.size(); i++ ) {
            ( (ExerciseListener)exerciseListeners.elementAt( i ) ).exerciseChanged( 
                    e );
        }
    }
    public void processIncorrectEvent( ScoreEvent e ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( "EventManager.processIncorrectEvent()" );
        }
        for ( int i = 0; i < scoreListeners.size(); i++ ) {
            ( (ScoreListener)scoreListeners.elementAt( i ) ).incorrect( e );
        }
    }
    public void processResetEvent( ScoreEvent e ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( "EventManager.processResetEvent()" );
        }
        for ( int i = 0; i < scoreListeners.size(); i++ ) {
            ( (ScoreListener)scoreListeners.elementAt( i ) ).reset( e );
        }
    }
    public void processRepeatEvent( ScoreEvent e ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( "EventManager.processRepeatEvent()" );
        }
        for ( int i = 0; i < scoreListeners.size(); i++ ) {
            ( (ScoreListener)scoreListeners.elementAt( i ) ).repeat( e );
        }
    }
    public void processDelayChanged( SettingsEvent e ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( "EventManager.processDelayChanged()" );
        }
        for ( int i = 0; i < settingsListeners.size(); i++ ) {
            ( (SettingsListener)settingsListeners.elementAt( i ) ).delayChanged( 
                    e );
        }
    }
    public void processDurationChanged( SettingsEvent e ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( "EventManager.processDurationChanged()" );
        }
        for ( int i = 0; i < settingsListeners.size(); i++ ) {
            ( (SettingsListener)settingsListeners.elementAt( i ) ).durationChanged( 
                    e );
        }
    }
    public void processInstrumentChanged( SettingsEvent e ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( "EventManager.processInstrumentChanged()" );
        }
        for ( int i = 0; i < settingsListeners.size(); i++ ) {
            ( (SettingsListener)settingsListeners.elementAt( i ) ).instrumentChanged( 
                    e );
        }
    }
    public void processPlayModeChanged( SettingsEvent e ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( "EventManager.processPlayModeChanged()" );
        }
        for ( int i = 0; i < settingsListeners.size(); i++ ) {
            ( (SettingsListener)settingsListeners.elementAt( i ) ).playModeChanged( 
                    e );
        }
    }
}