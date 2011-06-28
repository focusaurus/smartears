package net.codemusic.smartears.event;


/**
 *This is a conveniece Adapter that provides no-op
 *implementations of the ExerciseListener interface.  
 *It is designed to be subclassed by classes only interested
 *in some of the exercise events.  
 *Note that there is only 1 type of ExerciseEvent, but 
 *more will likely be used in the future, so it will be easier
 *to start using the adapter right away rather than implementing
 *ExerciseListener
 */
public class ExerciseAdapter implements ExerciseListener {

    /**
   *@see ExerciseListener#exerciseChanged(ExerciseEvent)
   */
    public void exerciseChanged( ExerciseEvent e ) {}
}