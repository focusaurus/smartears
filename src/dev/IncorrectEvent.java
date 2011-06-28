package com.peterlyons.smartears.event;

import com.peterlyons.smartears.*;

public class IncorrectEvent extends ScoreEvent {
    public IncorrectEvent( Object source, IAnswer answer ) {
        super( source, answer );
    }
}