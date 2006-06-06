package net.codemusic.smartears.event;

public interface IEventManager {
    public void addExampleListener( ExampleEventListener e );
    public void addScoreListener( ScoreEventListener e );
    public void processPlayEvent( ExampleEvent e );
    public void processRepeatEvent( ExampleEvent e );
    public void processCorrectEvent( ScoreEvent e );
    public void processIncorrectEvent( ScoreEvent e );
}