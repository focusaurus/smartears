package net.codemusic.smartears.event;


/**
 *Classes implementing this interface will
 *handle registering, unregistering, and propogating 
 *ExcerciseEvents to ExcerciseListeners
 */
public interface IExcerciseEventManager {

    /**
   *Registers an ExcerciseListener to receive all 
   *ExcerciseEvents
   *@param e the listener to be registered.  It will now
   *be notified of all ExcerciseEvents
   *@see ExcerciseListener
   */
    public void addExcerciseListener( ExcerciseListener e );

    /**
   *Unregisters an ExcerciseListener.  
   *@param e the listener to be unregistered.  It will not be notified 
   *of any future ExcerciseEvents
   */
    public void removeExcerciseListener( ExcerciseListener e );

    /**
   *Propogates the supplied event to all registered listeners
   */
    public void processExcerciseChanged( ExcerciseEvent e );
}