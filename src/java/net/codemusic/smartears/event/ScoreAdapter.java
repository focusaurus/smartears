package net.codemusic.smartears.event;


/**
 *The ScoreAdapter is a convenience class 
 *that provides no-op implementations of the
 *ScoreListener interface.  It is designed to be
 *subclassed by listeners that are only interested in some
 *types of ScoreEvents
 *@see ScoreListener
 */
public class ScoreAdapter implements ScoreListener {

    /**
   *@see ScoreListener#correct(ScoreEvent)
   */
    public void correct( ScoreEvent e ) {}

    /**
   *@see ScoreListener#incorrect(ScoreEvent)
   */
    public void incorrect( ScoreEvent e ) {}

    /**
   *@see ScoreListener#repeat(ScoreEvent)
   */
    public void repeat( ScoreEvent e ) {}

    /**
   *@see ScoreListener#reset(ScoreEvent)
   */
    public void reset( ScoreEvent e ) {}
}