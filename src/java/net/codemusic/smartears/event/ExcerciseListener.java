package net.codemusic.smartears.event;


/**
 *This interface should be implemented by classes intreseted
 *in being connected to the excercise event subsystem
 */
public interface ExcerciseListener {

    /**
   *This method will be called when the user has switched to
   *a different IExcercise
   *@param e the ExcerciseEvent that encapsulates this change
   *@see ExcerciseEvent
   */
    public void excerciseChanged( ExcerciseEvent e );
}