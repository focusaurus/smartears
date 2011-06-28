package net.codemusic.smartears;

import java.awt.event.*;
import java.util.*;
import net.codemusic.Strategy;
import net.codemusic.smartears.*;
import net.codemusic.smartears.event.*;

/**
 *The BasicAnswer class models any answer that can be viewed 
 *as a two dimensional array of integers (MIDI values).  This would include things
 *like intervals, melodies (of equal rhythmic duration to each note), 
 *scales, chords, and chord progressions.
 */
public class BasicAnswer implements IAnswer {

    /*  class DefaultCorrectStrategy implements Strategy
        {
        public void execute()
        {
        Controller.getEventManager().processCorrectEvent(new CorrectEvent(BasicAnswer.this));
        setGuessStrategy(new DefaultIncorrectStrategy());
        }
        }
                          
        class DefaultIncorrectStrategy implements Strategy
        {
        public void execute()
        {
        if(Controller.DEBUGGING_ENABLED)
        {
        System.out.println("BasicAnswer.DefaultIncorrectStrategy.execute()");
        }
        Controller.getEventManager().processIncorrectEvent(new IncorrectEvent(BasicAnswer.this));
        }
        }*/

    /**
   *The technical term for this answer, eg Perfect Fourth, Mixolydian, 
   *Dominant Seventh, or Plagal Cadence.
   */
    private String name = "";

    /**
   *An abbreviated name useful in tight GUI layouts, eg P4, mix, 7, or Plagal
   */
    private String abbreviation = "";

    /**
   *Defines what we do when we are guessed and are incorrect.
   *Dispatches to the Smartears.instance().getScoreManager().
   */
    private Strategy incorrectStrat = new Strategy() {
        public void execute() {
            if ( SmartEars.DEBUGGING_ENABLED ) {
                System.out.println( 
                        "BasicAnswer executing the incorrect strategy" );
            }
            SmartEars.getInstance().getScoreManager().incorrect( BasicAnswer.this );
        }
    };

    /**
   *Defines what we do when we are guessed and are correct.
   *Dispatches to the Smartears.instance().getScoreManager().
   */
    private Strategy correctStrat = new Strategy() {
        public void execute() {
            SmartEars.getInstance().getScoreManager().correct( BasicAnswer.this );
        }
    };

    /**
    *Defines what we do when we are guessed, but this 
    *exercise has been answered already.
    */
    private Strategy alreadyCorrectStrat = new Strategy() {
        public void execute() {
            //BUGBUG decouple this from ui knowledge
            net.codemusic.smartears.ui.MainFrame.setStatus( 
            "Press Play to continue" );
        }
    };

    /**
   *The strategy that we use when we are guessed
   */
    private Strategy guessStrat = incorrectStrat;

    /**
   *This array of ints models the structure of the answer,
   *which is thought of as a two-dimensional array of MIDI note numbers
   */
    private int[][] structure;

    /**
   *Number of half steps from lowest note to highest note of this answer
   */
    private int span = 0;

    /**
   *@see IAnswer#getValue()
   */
    private int value = 1;


    /**
   *Creates a Basic Answer with the supplied array as its structure
   */
    public BasicAnswer(int[][] structure) {
        this.structure = structure;
    }

    /**
   *@see IAnswer#getName()
   */
    public String getName() {
        return name;
    }

    /**
   *@see IAnswer#setName(String)
   */
    public void setName( String name ) {
        this.name = name;
    }

    /**
   *@see IAnswer#getAbbreviation()
   */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
   *@see IAnswer#setAbbreviation(String)
   */
    public void setAbbreviation( String abbreviation ) {
        this.abbreviation = abbreviation;
    }

    /**
   *@see IAnswer#actionPerformed(ActionEvent)
   */
    public void actionPerformed( ActionEvent e ) {
        //NOTE - I may want a more specific type of Strategy whose execute method takes
        //an IAnswer
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( "BasicAnswer.actionPerformed()" );
        }
        guessStrat.execute();
        // System.out.println(guessStrat.toString());
    }

    /**
   *@see IAnswer#setGuessStrategy(Strategy)
   */
    public void setGuessStrategy( Strategy s ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( 
                    "BasicAnswer.setGuessStrategy(): " + 
                    s.getClass().getName() );
        }
        guessStrat = s;
    }

    /**
   *@see IAnswer#getDefaultCorrectStrategy()
   */
    public Strategy getDefaultCorrectStrategy() {
        return correctStrat;
    }

    /**
   *@see IAnswer#getDefaultIncorrectStrategy()
   */
    public Strategy getDefaultIncorrectStrategy() {
        return incorrectStrat;
    }

    /**
   *@see IAnswer#getDefaultAlreadyCorrectStrategy()
   */
    public Strategy getDefaultAlreadyCorrectStrategy() {
        return alreadyCorrectStrat;
    }

    /**
   *This accessor method returns the answer's structure
   */
    public int[][] getStructure() {
        return structure;
    }

    /**
   *This method accepts the visitor and calls its playIntMatrix
   *method, passing it the structure of this BasicAnswer
   */
    public void acceptSoundEngineVisitor( ISoundEngineVisitor v, Object param ) {
        v.playIntMatrix( structure, param );
    }

    /**
   *@see IAnswer#getSpan()
   */
    public int getSpan() {
        return span;
    }

    /**
   *@see IAnswer#setSpan(int)
   */
    public void setSpan( int span ) {
        this.span = span;
    }

    /**
   *@see IAnswer#getValue()
   */
    public int getValue() {
        return value;
    }
    public boolean equals( Object o ) {
        if ( ! ( o instanceof BasicAnswer ) ) {
            return super.equals( o );
        } else {
            return IntMatrixMatcher.equals( getStructure(), 
                                            ( (BasicAnswer)o ).getStructure() );
        }
    }
}