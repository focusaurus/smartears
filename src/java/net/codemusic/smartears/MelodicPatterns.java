package net.codemusic.smartears;

import net.codemusic.smartears.event.*;

/**
 *This IExcercise tests identification of melodic patterns
 *(Any series of notes, not necessarily "melodic")
 */
public class MelodicPatterns implements IReproductionExcercise {
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
     *@see IExcercise#getName()
     */
    public String getName() {
        return "Melodic Patterns";
    }

    /**
     *@see IExcercise#getDescription()
     */
    public String getDescription() {
        return "Any series of single notes";
    }

    /**
     *@see IReproductionExcercise#getNextAnswer()
     */
    public IAnswer getNextAnswer() {
        return null;
    }
}