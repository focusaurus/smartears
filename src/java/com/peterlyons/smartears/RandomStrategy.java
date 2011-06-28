package com.peterlyons.smartears;

import java.util.*;

/**
 *This startegy chooses randomly amongst the set of answers.
 */
public class RandomStrategy implements IAnswerSelectionStrategy {

    /**
   *Selects a random answer from the supplied Vector of answers
   */
    public IAnswer select( Vector answers ) {
        return (IAnswer)answers.elementAt( 
                       (int)( Math.random() * answers.size() ) );
    }
}