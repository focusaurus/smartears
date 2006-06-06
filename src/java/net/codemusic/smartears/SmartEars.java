/**
 * Title:        SmartEars
 * Description:  An ear Training program.
 * Copyright:    Copyright (c) 2001
 * Company:      codemusic.net
 * @author Peter Lyons
 * @version 1.0
 */
package net.codemusic.smartears;

import javax.sound.midi.*;

import net.codemusic.smartears.event.*;
import net.codemusic.smartears.ui.*;

/**
 *This class serves as a Controller for this application in that it
 *connects the model classes with the view classes.  It creates and connects the
 *model classes and allows the GUI to access the necessary model elements.
 */
public class SmartEars implements IController {

    /**
     *Symbolic constant flag used to enable or complie-out debugging code
     *(when false, the compiler should completely omit the debugging 
     *statements from the bytecode)
     */
    public final static boolean DEBUGGING_ENABLED = false;

    /*
     *A central event manager that handles all of the system's events
     */
    private EventManager eventManager = new EventManager();
    private IAnswerManager answerManager;
    private IExampleManager exampleManager;
    private IExcerciseManager excerciseManager;
    private IExcercise[] excercises;
    private Instrument[] instruments;
    private IRangeManager rangeManager = new RangeManager();
    private IScoreManager scoreManager;
    private ISettingsManager settingsManager;
    private ISoundEngine soundEngine;
    protected static SmartEars instance;

    /**
     *Starts up the SmartEars program.
     */
    public static void main( String[] args ) {
        instance = new SmartEars( args );
        instance.start(args);
    }
    
    public static SmartEars getInstance() {
        return instance;
    }

    /**
     * A Singleton pattern private constructor
     */
    private SmartEars( String[] args ) {
        
    }
    

    private void start(String[] args) {
        settingsManager = new SettingsManager( eventManager );
        try {
            soundEngine = new MidiSoundEngine( (ISettings)settingsManager );
        } catch ( MidiUnavailableException e ) {
            System.err.println( 
                    "Unable to access your system's MIDI functionaltiy. Exiting" );
            System.exit( 1 );
        }
        //the answerStrategySetter tells the chosen answer that it is correct
        //    eventManager.addExampleListener(new AnswerStrategySetter());
        excerciseManager = new ExcerciseManager( eventManager );
        answerManager = new AnswerManager( eventManager, eventManager );
        scoreManager = new ScoreManager( eventManager, eventManager );
        //Create all of the Excercises
        IExcercise[] allExcercises = {
            new Intervals(), new BasicScales(), 
            //new MelodicPatterns( scoreManager ), 
            new BasicChords(), new Trichords(), 
            new AdvancedChords( scoreManager )
        };
        excercises = allExcercises;
        IAnswer[] answers = ( (IRecognitionExcercise)excercises[0] ).getAnswers();
        instruments = soundEngine.getInstruments();
        exampleManager = new ExampleManager( eventManager, answerManager, 
                                             rangeManager );
        //tell the AnswerManager about all of the answers to the first excercise
        for ( int i = 0; i < answers.length; i++ ) {
            answerManager.add( answers[i] );
        }
	System.setProperty("com.apple.macos.useScreenMenuBar","true");
        //Launch the GUI
        MainFrame frame = new MainFrame( this );
        //this listener takes care of having the sound engine play the sounds
        eventManager.addExampleListener( new ExampleAdapter() {
            public void examplePlayed( ExampleEvent e ) {
                soundEngine.play( e.getAnswer(), e.getBase() );
            }
            public void exampleRepeated( ExampleEvent e ) {
                soundEngine.play( e.getAnswer(), e.getBase() );
            }
        } );
        //keeps the sound engine informed of settings changes
        eventManager.addSettingsListener( new SettingsAdapter() {
            public void delayChanged( SettingsEvent e ) {
                soundEngine.setDelay( e.getSettings().getDelay() );
            }
            public void durationChanged( SettingsEvent e ) {
                soundEngine.setDuration( e.getSettings().getDuration() );
            }
            public void instrumentChanged( SettingsEvent e ) {
                //  System.out.println("SmartEars dispatching to soundEngine");
                soundEngine.setInstrument( e.getSettings().getInstrument() );
            }
        } );
        //keeps the answer manager's answers in sync with the current excercise
        eventManager.addExcerciseListener( new ExcerciseListener() {
            public void excerciseChanged( ExcerciseEvent e ) {
                IExcercise excercise = e.getExcercise();
                if ( excercise instanceof IRecognitionExcercise ) {
                    answerManager.removeAll();
                    IAnswer[] answers = ( (IRecognitionExcercise)excercise ).getAnswers();
                    for ( int i = 0; i < answers.length; i++ ) {
                        answerManager.add( answers[i] );
                    }
                }
            }
        } );
        //Use Ascending order only for ReproductionExcercises
        eventManager.addExcerciseListener( new ExcerciseListener() {
            public void excerciseChanged( ExcerciseEvent e ) {
                IExcercise excercise = e.getExcercise();
                if ( excercise instanceof IReproductionExcercise ) {
                    System.out.println( 
                            "Excercise changed to " + excercise.getName() );
                    getSettingsManager().playModeChanged( ISettings.ASCENDING );
                }
            }
        } );
    }

    /**
     *@see IController
     */
    public IAnswerManager getAnswerManager() {
        return answerManager;
    }

    /**
     *@see IController
     */
    public IExampleEventManager getExampleEventManager() {
        return eventManager;
    }

    /**
     *@see IController
     */
    public IExampleManager getExampleManager() {
        return exampleManager;
    }

    /**
     *@see IController
     */
    public IExcerciseManager getExcerciseManager() {
        return excerciseManager;
    }

    /**
     *@see IController
     */
    public IExcerciseEventManager getExcerciseEventManager() {
        return eventManager;
    }

    /**
     *@see IController
     */
    public IExcercise[] getExcercises() {
        return excercises;
    }

    /**
     *@see IController
     */
    public Instrument[] getInstruments() {
        return instruments;
    }

    /**
     *@see IController
     */
    public IScoreEventManager getScoreEventManager() {
        return eventManager;
    }

    /**
     *@see IController
     */
    public IScoreManager getScoreManager() {
        return scoreManager;
    }

    /**
     *@see IController
     */
    public ISettingsEventManager getSettingsEventManager() {
        return eventManager;
    }

    /**
     *@see IController
     */
    public ISettingsManager getSettingsManager() {
        return settingsManager;
    }

    /**
     *@see IController
     */
    public IRangeManager getRangeManager() {
        return rangeManager;
    }

    /**
     *Shuts down the system, cleaning up any necessary 
     *resources before calling System.exit(0);
     *@see IController
     */
    public void shutdown() {
        soundEngine.close();
        System.exit( 0 );
    }
}
