package net.codemusic.smartears.event;


/**
 *Classes implementing this interface will
 *handle registering, unregistering, and propogating 
 *ExerciseEvents to ExerciseListeners
 */
public interface IExerciseEventManager {

    /**
   *Registers an ExerciseListener to receive all 
   *ExerciseEvents
   *@param e the listener to be registered.  It will now
   *be notified of all ExerciseEvents
   *@see ExerciseListener
   */
    public void addExerciseListener( ExerciseListener e );

    /**
   *Unregisters an ExerciseListener.  
   *@param e the listener to be unregistered.  It will not be notified 
   *of any future ExerciseEvents
   */
    public void removeExerciseListener( ExerciseListener e );

    /**
   *Propogates the supplied event to all registered listeners
   */
    public void processExerciseChanged( ExerciseEvent e );
}