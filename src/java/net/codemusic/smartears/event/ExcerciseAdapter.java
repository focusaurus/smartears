package net.codemusic.smartears.event;


/**
 *This is a conveniece Adapter that provides no-op
 *implementations of the ExcerciseListener interface.  
 *It is designed to be subclassed by classes only interested
 *in some of the excercise events.  
 *Note that there is only 1 type of ExcerciseEvent, but 
 *more will likely be used in the future, so it will be easier
 *to start using the adapter right away rather than implementing
 *ExcerciseListener
 */
public class ExcerciseAdapter implements ExcerciseListener {

    /**
   *@see ExcerciseListener#excerciseChanged(ExcerciseEvent)
   */
    public void excerciseChanged( ExcerciseEvent e ) {}
}