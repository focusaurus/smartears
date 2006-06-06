package net.codemusic.smartears;

import java.awt.event.*;

import net.codemusic.Strategy;

/**
 *This interface represents an answer that can be played and which the user 
 *must identify or otherwise answer
 */
public interface IAnswer extends ActionListener {

    /**
   *This accessor method returns the full technical name for
   *this answer
   */
    public String getName();

    /**
   *This accessor method sets the full technical name for
   *this answer
   */
    public void setName( String name );

    /**
   *This accessor method returns the abbreviated name for
   *this answer
   */
    public String getAbbreviation();

    /**
   *This accessor method sets the abbreviated name for
   *this answer
   */
    public void setAbbreviation( String abbreviation );

    /**
   *Returns an int specifying how many points this answer is worth.
   */
    public int getValue();

    /**
   *Called when the user has guessed this answer.  This method
   *executes the guess strategy that can be set using the setGuessStrategy method.
   *@see #setGuessStrategy(Strategy)
   */
    public void actionPerformed( ActionEvent e );

    /**
   *This accessor method sets the strategy that this answer 
   *will use when its actionPerformed method is called.
   *@see #actionPerformed(ActionEvent)
   */
    public void setGuessStrategy( Strategy s );

    /**
   *This accessor method returns a basic strategy for
   *correctly identified answers
   */
    public Strategy getDefaultCorrectStrategy();

    /**
   *This accessor method returns a basic strategy for
   *what to do when an answer is chosen after the 
   *example has been correctly identified
   */
    public Strategy getDefaultAlreadyCorrectStrategy();

    /**
   *This accessor method returns a basic strategy for 
   *incorrectly identified answers
   */
    public Strategy getDefaultIncorrectStrategy();

    /**
   *A Visitor Design Pattern method that accepts a Visitor from
   *a Sound Engine to negotiate playback for this IAnswer
   */
    public void acceptSoundEngineVisitor( ISoundEngineVisitor v, Object param );

    /**
   *Returns the number of half steps from the lowest note to 
   *the highest note in this answer.
   */
    public int getSpan();

    /**
   *Sets the number of half steps from the lowest note to 
   *the highest note in this answer. Nota bene that the programmer
   *is responsible for making sure this number is accurate
   */
    public void setSpan( int span );
}