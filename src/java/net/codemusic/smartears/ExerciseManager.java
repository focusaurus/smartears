package net.codemusic.smartears;

import net.codemusic.smartears.event.*;

/**
 *this class is responsible for handling exercise related
 *events in the system such as changing to a different exercise
 */
public class ExerciseManager implements IExerciseManager {
    private IExerciseEventManager exerciseEventManager;

    public ExerciseManager( IExerciseEventManager e ) {
        exerciseEventManager = e;
    }

    /**
   *Switches to the supplied exercise
   */
    public void exerciseChanged( IExercise e ) {
        exerciseEventManager.processExerciseChanged( new ExerciseEvent( 
                                                               this, e ) );
    }
}