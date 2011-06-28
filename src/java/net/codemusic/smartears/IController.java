package net.codemusic.smartears;

import net.codemusic.smartears.event.*;

/**
 *This is the interface that is used to provide access to the main objects
 *in the system and to connect the GUI classes to the classes that model the sysetm.
 *It contains get accessor methods that are self explainatory.
 */
public interface IController {
    public IAnswerManager getAnswerManager();
    public IExampleEventManager getExampleEventManager();
    public IExampleManager getExampleManager();
    public IExerciseEventManager getExerciseEventManager();
    public IExerciseManager getExerciseManager();
    public IExercise[] getExercises();
    public javax.sound.midi.Instrument[] getInstruments();
    public IScoreEventManager getScoreEventManager();
    public IScoreManager getScoreManager();
    public ISettingsEventManager getSettingsEventManager();
    public ISettingsManager getSettingsManager();
    public IRangeManager getRangeManager();

    /**
   *Called when the user has elected to exit the program.
   *Takes care of system clean up and resource releasing.
   */
    public void shutdown();
}