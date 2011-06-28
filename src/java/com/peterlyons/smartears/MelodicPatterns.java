package com.peterlyons.smartears;

import com.peterlyons.smartears.event.*;

/**
 *This IExercise tests identification of melodic patterns
 *(Any series of notes, not necessarily "melodic")
 */
public class MelodicPatterns implements IReproductionExercise {
    protected IAnswer[] answers = new IAnswer[0];
    protected IScoreManager scoreManager;

    /**
     *Creates a new MelodicPatterns object that will communicate with
     *the supplied IScoreManager
     */
    public MelodicPatterns( IScoreManager sm ) {
        scoreManager = sm;
    }

    /**
     *@see IExercise#getName()
     */
    public String getName() {
        return "Melodic Patterns";
    }

    /**
     *@see IExercise#getDescription()
     */
    public String getDescription() {
        return "Any series of single notes";
    }

    /**
     *@see IReproductionExercise#getNextAnswer()
     */
    public IAnswer getNextAnswer() {
        return null;
    }
}