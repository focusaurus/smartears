package com.peterlyons.smartears;

import com.peterlyons.smartears.event.*;

/**
 *This class is responsible for storing the user's
 *score statistics and creating Score related events
 */
public class ScoreManager implements IScoreManager,
                                     IScore {
    private int score = 0;
    private int correctCount = 0;
    private int incorrectCount = 0;
    private int repeatCount = 0;
    private IScoreEventManager scoreEventManager;

    /**
   *Creates a new ScoreManager that will communicate 
   *with the supplied event manangers
   */
    public ScoreManager( IScoreEventManager sem, IExampleEventManager eem ) {
        scoreEventManager = sem;
        //keeps a record of repeats
        eem.addExampleListener( new ExampleAdapter() {
            public void repeat( ExampleEvent e ) {
                ScoreManager.this.repeat( e.getAnswer() );
            }
        } );
    }

    /**
   *@see IScoreManager#correct(IAnswer)
   */
    public void correct( IAnswer answer ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( "ScoreManager.correct()" );
        }
        correctCount++;
        score += answer.getValue();
        scoreEventManager.processCorrectEvent( new ScoreEvent( this, answer, 
                                                               this ) );
    }

    /**
   *@see IScoreManager#incorrect(IAnswer)
   */
    public void incorrect( IAnswer answer ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( "ScoreManager.incorrect()" );
        }
        incorrectCount++;
        score -= answer.getValue();
        scoreEventManager.processIncorrectEvent( new ScoreEvent( this, answer, 
                                                                 this ) );
    }

    /**
   *@see IScoreManager#repeat(IAnswer)
   */
    public void repeat( IAnswer answer ) {
        repeatCount++;
        scoreEventManager.processRepeatEvent( new ScoreEvent( this, answer, 
                                                              this ) );
    }

    /**
   *@see IScoreManager#reset()
   */
    public void reset() {
        score = correctCount = incorrectCount = repeatCount = 0;
        scoreEventManager.processResetEvent( new ScoreEvent( this, this ) );
    }

    /**
   *@see IScoreManager#getCorrectCount()
   */
    public int getCorrectCount() {
        return correctCount;
    }

    /**
   *@see IScoreManager#getIncorrectCount()
   */
    public int getIncorrectCount() {
        return incorrectCount;
    }

    /**
   *@see IScoreManager#getScore()
   */
    public int getScore() {
        return score;
    }

    /**
   *@see IScoreManager#getRepeatCount()
   */
    public int getRepeatCount() {
        return repeatCount;
    }
}