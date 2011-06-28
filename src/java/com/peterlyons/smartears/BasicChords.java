package com.peterlyons.smartears;

import com.peterlyons.smartears.event.*;

/**
 * This class is an Exercise that works with the 4 basic triads and the 5 most
 * common 7th chords
 *
 *@author    plyons
 */
public class BasicChords implements IRecognitionExercise {
    
    private String[] names = {
        "Major Triad", "Minor Triad", "Diminished Triad", "Augmented Triad",
        "Major Seventh", "Dominant Seventh", "Minor Seventh",
        "Half Diminished Seventh", "Diminished Seventh"
        };

    private String[] abbreviations = {
        "Maj", "Min", "Dim", "Aug", "Maj7", "Dom7", "Min7", "HalfDim7", "dim7"
        };

    private int[][][] structures = {
        {{0, 4, 7}}, {{0, 3, 7}}, {{0, 3, 6}}, {{0, 4, 8}},
        {{0, 4, 7, 11}}, {{0, 4, 7, 10}}, {{0, 3, 7, 10}},
        {{0, 3, 6, 10}}, {{0, 3, 6, 9}}
        };

    private IAnswer[] answers = new IAnswer[names.length];

    /**
     * Creates a new BasicChords IExercise that will communicate with the
     * supplied ScoreManager
     */
    public BasicChords() {
        for (int i = 0; i < names.length; i++) {
            answers[i] = new BasicAnswer(structures[i]);
            answers[i].setName(names[i]);
            answers[i].setAbbreviation(abbreviations[i]);
            answers[i].setSpan(structures[i][0][structures[i][0].length - 1]);
        }
    }

    /**
     *@return   The name value
     *@see      IExercise#getName()
     */
    public String getName() {
        return "Basic Chords";
    }

    /**
     *@return   The description value
     *@see      IExercise#getDescription()
     */
    public String getDescription() {
        return "Triads and common seventh chords";
    }

    /**
     *@return   The answers value
     *@see      IAnswerManager#getAnswers()
     */
    public IAnswer[] getAnswers() {
        return answers;
    }
}
