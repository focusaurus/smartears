package net.codemusic.smartears;

import java.util.*;

/**
 *This interface represents a class that is a particular
 *way of choosing the next answer from a set of possible
 *answers.  Possible implements might include purely random
 *selection as well as intelligent selection that gives more
 *weight to particular answers that a student misses frequently.
 */
public interface IAnswerSelectionStrategy {

    /**
   *Selects and returns the next correct answer.
   */
    public IAnswer select( Vector IAnswers );
}