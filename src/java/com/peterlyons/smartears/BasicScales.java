package com.peterlyons.smartears;

import com.peterlyons.smartears.event.*;

/**
 *This IExercise tests recognition of Basic Scales, including
 *all of the modes of the major scale and the two additional forms
 *of the minor scale
 */
public class BasicScales implements IRecognitionExercise {
    private String[] names = {
        "Ionian", "Dorian", "Phrygian", "Lydian", "Mixolydian", "Aeolian", 
        "Locrian", "Harmonic Minor", "Melodic Minor"
    };
    private int[][][] structures = {
        { { 0, 2, 4, 5, 7, 9, 11, 12 } }, { { 0, 2, 3, 5, 7, 9, 10, 12 } }, 
        { { 0, 1, 3, 5, 7, 8, 10, 12 } }, { { 0, 2, 4, 6, 7, 9, 11, 12 } }, 
        { { 0, 2, 4, 5, 7, 9, 10, 12 } }, { { 0, 2, 3, 5, 7, 8, 10, 12 } }, 
        { { 0, 1, 3, 5, 6, 8, 10, 12 } }, { { 0, 2, 3, 5, 7, 8, 11, 12 } }, 
        { { 0, 2, 3, 5, 7, 9, 11, 12 } }
    };
    private IAnswer[] answers = new IAnswer[structures.length];

    /**
   *Creates a new BasicScales object that will communicate with
   *the supplied IScoreManager
   */
    public BasicScales() {
        for ( int i = 0; i < names.length; i++ ) {
            answers[i] = new BasicAnswer(structures[i]);
            answers[i].setSpan( 12 );
            answers[i].setName( names[i] );
            answers[i].setAbbreviation( names[i] );
        }
    }

    /**
   *@see IExercise#getName()
   */
    public String getName() {
        return "Basic Scales";
    }

    /**
   *@see IExercise#getDescription()
   */
    public String getDescription() {
        return "Modes of the Major Scale plus 3 forms of minor";
    }

    /**
   *@see IAnswerManager#getAnswers()
   */
    public IAnswer[] getAnswers() {
        return answers;
    }
}