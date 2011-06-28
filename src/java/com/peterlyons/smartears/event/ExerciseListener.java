package com.peterlyons.smartears.event;


/**
 *This interface should be implemented by classes intreseted
 *in being connected to the exercise event subsystem
 */
public interface ExerciseListener {

    /**
   *This method will be called when the user has switched to
   *a different IExercise
   *@param e the ExerciseEvent that encapsulates this change
   *@see ExerciseEvent
   */
    public void exerciseChanged( ExerciseEvent e );
}