/*
 * AdvancedChords.java
 *
 * Version 1.0
 *
 * 2001/11/03
 *
 * Copyright (c) 2001 by Peter Lyons, pete@peterlyons.com
 *
 */
package com.peterlyons.smartears;

import com.peterlyons.smartears.event.*;

/**
 *This class is an Exercise that works with 
 *more sophisticated types of 4 and 5 note chords
 *
 *@author Peter Lyons
 */
public class AdvancedChords implements IRecognitionExercise {
    //turning off log4j for now
    //private static org.apache.log4j.Category log4j = org.apache.log4j.Category.getInstance( AdvancedChords.class.getName() );
    private String[] names = {
        "Minor Major Seventh", "Major Seven Sharp Five", "Major Ninth", 
        "Minor Ninth", "Dominant Seventh Natural Ninth", 
        "Dominant Seven Flat Nine", "Dominant Seven Sharp Nine"
    };
    private String[] abbreviations = {
        "min Maj7", "maj7#5", "maj9", "min9", "dom9", "7b9", "7#9"
    };
    private int[][][] structures = {
        { { 0, 3, 7, 11 } }, { { 0, 4, 8, 11 } }, { { 0, 4, 7, 11, 14 } }, 
        { { 0, 3, 7, 10, 14 } }, { { 0, 4, 7, 10, 14 } }, 
        { { 0, 4, 7, 10, 13 } }, { { 0, 4, 7, 10, 15 } }
    };
    private IAnswer[] answers = new IAnswer[names.length];

    /**
     *Creates a new AdvancedChords IExercise that will
     *communicate with the supplied ScoreManager
     */
    public AdvancedChords( IScoreManager sm ) {
        for ( int i = 0; i < names.length; i++ ) {
            answers[i] = new BasicAnswer(structures[i]);
            answers[i].setName( names[i] );
            answers[i].setAbbreviation( abbreviations[i] );
            answers[i].setSpan( structures[i][0][structures[i][0].length - 1] );
        }
    }

    /**
     *@see IExercise#getName()
     */
    public String getName() {
        return "Advanced Chords";
    }

    /**
     *@see IExercise#getDescription()
     */
    public String getDescription() {
        return "More complicated 4 and 5 note chords common in jazz";
    }

    /**
     *@see IAnswerManager#getAnswers()
     */
    public IAnswer[] getAnswers() {
        return answers;
    }
}