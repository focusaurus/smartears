package net.codemusic.smartears.event;

import net.codemusic.smartears.*;

public class CorrectEvent extends ScoreEvent {
    public CorrectEvent( Object source, IAnswer answer ) {
        super( source, answer );
    }
}