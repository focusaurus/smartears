package com.peterlyons.smartears.event;

import com.peterlyons.smartears.*;

public class CorrectEvent extends ScoreEvent {
    public CorrectEvent( Object source, IAnswer answer ) {
        super( source, answer );
    }
}