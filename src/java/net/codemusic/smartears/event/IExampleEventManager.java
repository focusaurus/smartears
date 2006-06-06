package net.codemusic.smartears.event;


/**
 *Handles the registering, unregistering, and notifying 
 *of ExampleListeners
 */
public interface IExampleEventManager {

    /**
   *Registers an ExampleListener to be notified about all
   *ExampleEvents
   *@see ExampleListener
   */
    public void addExampleListener( ExampleListener e );

    /**
   *Unregisters an ExampleListener - it will no longer be
   *notified about any ExampleEvents.  If the listener was
   *not registered to begin with, this method has no effect
   *@see ExampleListener
   */
    public void removeExampleListener( ExampleListener e );

    /**
   *Propogates the supplied ExampleEvent to all registered listeners
   *by calling their examplePlayed(ExampleEvent e) method
   *@see ExampleEvent
   */
    public void processExamplePlayed( ExampleEvent e );

    /**
   *Propogates the supplied ExampleEvent to all registered listeners
   *by calling their exampleRepeated(ExampleEvent e) method
   *@see ExampleEvent
   */
    public void processExampleRepeated( ExampleEvent e );

    /**
   *Propogates the supplied ExampleEvent to all registered listeners
   *by calling their answerDisabled(ExampleEvent e) method
   *@see ExampleEvent
   */
    public void processAnswerDisabled( ExampleEvent e );

    /**
   *Propogates the supplied ExampleEvent to all registered listeners
   *by calling their answerEnabled(ExampleEvent e) method
   *@see ExampleEvent
   */
    public void processAnswerEnabled( ExampleEvent e );
}