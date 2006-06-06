package net.codemusic.smartears.event;

import net.codemusic.smartears.*;

public class IncorrectEvent extends ScoreEvent {
    public IncorrectEvent( Object source, IAnswer answer ) {
        super( source, answer );
    }
}