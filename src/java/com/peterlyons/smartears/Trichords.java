package com.peterlyons.smartears;

import com.peterlyons.smartears.event.*;

/**
 *This IExercise tests recognition of Trichords, 
 *which are three note groupings distinguished by their intervalic content.
 *Trichords are used often in analyzing certain types of serial music
 */
public class Trichords implements IRecognitionExercise {
    private String[] names = {
        "{0,1,2}", "{0,1,3}", "{0,1,4}", "{0,1,5}", "{0,1,6}", "{0,2,4}", 
        "{0,2,5}", "{0,2,6}", "{0,2,7}", "{0,3,6}", "{0,3,7}", "{0,4,7}", 
        "{0,4,8}"
    };
    private String[] abbreviations = {
        "0,1,2", "0,1,3", "0,1,4", "0,1,5", "0,1,6", "0,2,4", "0,2,5", "0,2,6", 
        "0,2,7", "0,3,6", "0,3,7", "0,4,7", "0,4,8"
    };
    private int[][][] structures = {
        { { 0, 1, 2 } }, { { 0, 1, 3 } }, { { 0, 1, 4 } }, { { 0, 1, 5 } }, 
        { { 0, 1, 6 } }, { { 0, 2, 4 } }, { { 0, 2, 5 } }, { { 0, 2, 6 } }, 
        { { 0, 2, 7 } }, { { 0, 3, 6 } }, { { 0, 3, 7 } }, { { 0, 4, 7 } }, 
        { { 0, 4, 8 } }
    };
    private IAnswer[] answers = new IAnswer[structures.length];

    /**
   *Creates a new Trichords object that will communicate with
   *the supplied IScoreManager
   */
    public Trichords() {
        for ( int i = 0; i < names.length; i++ ) {
            answers[i] = new BasicAnswer( structures[i]);
            answers[i].setSpan( structures[i][0][2] );
            answers[i].setName( names[i] );
            answers[i].setAbbreviation( abbreviations[i] );
        }
    }

    /**
   *@see IExercise#getName()
   */
    public String getName() {
        return "Trichords";
    }

    /**
   *@see IExercise#getDescription()
   */
    public String getDescription() {
        return "Three note groupings";
    }

    /**
   *@see IAnswerManager#getAnswers()
   */
    public IAnswer[] getAnswers() {
        return answers;
    }
}