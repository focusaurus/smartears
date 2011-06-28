package net.codemusic.smartears;


/**
 *This interface is responsible for handling exercise 
 *level events for this system, such as changing 
 *to a different exercise
 */
public interface IExerciseManager {

    /**
   *Switches the system to the supplied exercise
   */
    public void exerciseChanged( IExercise e );
}