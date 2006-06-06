package net.codemusic.smartears.event;


/**
 *An interface that should be implemented by classes
 *needing to be connected to the ExampleEvent system.
 */
public interface ExampleListener {

    /**
   *This method will be called each time the user
   *has requested a new example be played
   */
    public void examplePlayed( ExampleEvent e );

    /**
   *This method will be called when an example is repeated
   */
    public void exampleRepeated( ExampleEvent e );

    /**
   *This method will be called when a particular 
   *answer is enabled
   */
    public void answerEnabled( ExampleEvent e );

    /**
   *This method will be called when a particular 
   *answer is disabled
   */
    public void answerDisabled( ExampleEvent e );
}