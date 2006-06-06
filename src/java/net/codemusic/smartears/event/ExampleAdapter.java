package net.codemusic.smartears.event;


/**
 *This is a conveniece Adapter that provides no-op
 *implementations of the ExampleListener interface.  
 *It is designed to be subclassed by classes only interested
 *in some of the example events
 */
public class ExampleAdapter implements ExampleListener {
    public void examplePlayed( ExampleEvent e ) {}
    public void exampleRepeated( ExampleEvent e ) {}
    public void answerEnabled( ExampleEvent e ) {}
    public void answerDisabled( ExampleEvent e ) {}
}