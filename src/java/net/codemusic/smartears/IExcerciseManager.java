package net.codemusic.smartears;


/**
 *This interface is responsible for handling excercise 
 *level events for this system, such as changing 
 *to a different excercise
 */
public interface IExcerciseManager {

    /**
   *Switches the system to the supplied excercise
   */
    public void excerciseChanged( IExcercise e );
}