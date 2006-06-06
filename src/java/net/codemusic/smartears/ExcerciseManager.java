package net.codemusic.smartears;

import net.codemusic.smartears.event.*;

/**
 *this class is responsible for handling excercise related
 *events in the system such as changing to a different excercise
 */
public class ExcerciseManager implements IExcerciseManager {
    private IExcerciseEventManager excerciseEventManager;

    public ExcerciseManager( IExcerciseEventManager e ) {
        excerciseEventManager = e;
    }

    /**
   *Switches to the supplied excercise
   */
    public void excerciseChanged( IExcercise e ) {
        excerciseEventManager.processExcerciseChanged( new ExcerciseEvent( 
                                                               this, e ) );
    }
}