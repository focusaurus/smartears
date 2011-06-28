package com.peterlyons.smartears;

import javax.sound.midi.*;

import com.peterlyons.smartears.event.*;

public class Controller {
    private static EventManager eventManager = new EventManager();
    private static ISoundEngine soundEngine;
    private static IScoreKeeper scoreKeeper = new ScoreKeeper();
    private static IRangeManager rangeManager = new RangeManager();
    private static IAnswerManager answerManager = new AnswerManager( 
                                                          eventManager );
    private static IExerciseManager exerciseManager = new ExerciseManager();
    private static IExampleManager exampleManager = new ExampleManager( 
                                                            eventManager );
    private static IExercise exercise = new Intervals();
    //starts the Singleton pattern at class load time
    private static Controller controller = new Controller();
    public static final boolean DEBUGGING_ENABLED = true;
    private MainFrame frame;

    private Controller() {
        try {
            soundEngine = new MidiSoundEngine();
        } catch ( MidiUnavailableException e ) {
            System.err.println( 
                    "Unable to access your system's MIDI functionaltiy. Exiting" );
            System.exit( 1 );
        }
        IAnswer[] answers = exercise.getAnswers();
        for ( int i = 0; i < answers.length; i++ ) {
            answerManager.add( answers[i] );
        }
        System.out.println( 
                "Answer Manager should now have " + answers.length + 
                " answers" );
        eventManager.addExampleListener( new AnswerStrategySetter() );
        eventManager.addExampleListener( new ExampleAdapter() {
            public void play( ExampleEvent e ) {
                soundEngine.play( e.getAnswer(), e.getBase() );
            }
            public void repeat( ExampleEvent e ) {
                soundEngine.play( e.getAnswer(), e.getBase() );
            }
        } );
        frame = new MainFrame();
        ;
        eventManager.addScoreListener( new ScoreAdapter() {
            public void correct( ScoreEvent se ) {
                System.out.println( "correct" );
                frame.setStatus( "Correct! :-)" );
            }
            public void incorrect( ScoreEvent se ) {
                System.out.println( "incorrect" );
                frame.setStatus( "Incorrect :-(" );
            }
        } );
    }

    public static void main( String[] args ) {
        //new Controller();
    }
    public static ISoundEngine getSoundEngine() {
        return soundEngine;
    }
    public static IRangeManager getRangeManager() {
        return rangeManager;
    }
    public static IAnswerManager getAnswerManager() {
        return answerManager;
    }
    public static IScoreKeeper getScoreKeeper() {
        return scoreKeeper;
    }
    public static IExercise getExercise() {
        return exercise;
    }
    public static IExampleEventManager getExampleEventManager() {
        return eventManager;
    }
    public static IScoreEventManager getScoreEventManager() {
        return eventManager;
    }
    public static IExerciseManager getExerciseManager() {
        return exerciseManager;
    }
    public static IExampleManager getExampleManager() {
        return exampleManager;
    }
}