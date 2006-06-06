package net.codemusic.smartears.event;

import net.codemusic.smartears.*;

/**
 *This event occurs when a new Example is played for the user
 *to answer/identify
 */
public class PlayEvent extends ExampleEvent implements java.io.Serializable {

    /**
   *Creates a new PlayEvent
   *@param source the object that created this event
   *@param am The AnswerManager that will select the correct answer
   *@param rm the RangeManager that will select the appropriate base pitch
   */
    public PlayEvent( Object source, IAnswerManager am, IRangeManager rm )
              throws NoPossibleAnswersException, RangeException {
        super( source, am.select() );
        base = rm.selectBase( answer );
    }
}