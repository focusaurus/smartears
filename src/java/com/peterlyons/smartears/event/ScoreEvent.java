package com.peterlyons.smartears.event;

import com.peterlyons.smartears.*;

/**
 *This event happens when the user's score is altered
 */
public class ScoreEvent extends java.util.EventObject
    implements java.io.Serializable {
    private IAnswer answer;
    private IScore score;

    //BUGBUG possibly add base
    //Note that eventually ScoreEvents can be used to
    //collect information about a user's strengths and weaknessed
    //and customize the examples generated to focus on improving
    //the weaknesses

    /**
   *Creates a new ScoreEvent
   *@param source the object that created the event
   *@param answer the IAnswer associated with this event
   *@param score the user's current score
   */
    public ScoreEvent( Object source, IAnswer answer, IScore score ) {
        super( source );
        this.answer = answer;
        this.score = score;
    }

    /**
   *Creates a ScoreEvent that does not have an IAnswer
   *associated with it (such as a reset event).
   *This constructor is equivalent to ScoreEvent(source, null, score).
   */
    public ScoreEvent( Object source, IScore score ) {
        this( source, null, score );
    }

    /**
   *returns the IAnswer associated with this event
   *@return null if there is no IAnswer associated with this event
   */
    public IAnswer getAnswer() {
        return answer;
    }

    /**
   *Returns an IScore representing the user's current score
   */
    public IScore getScore() {
        return score;
    }
}