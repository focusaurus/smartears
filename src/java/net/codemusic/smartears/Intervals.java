package net.codemusic.smartears;

import net.codemusic.smartears.event.*;

/**
 *This IExercise tests the basic 2-note intervals up to 
 *one octave
 */
public class Intervals implements IRecognitionExercise {
    private String[] names = {
        "Perfect Unison", "Minor Second", "Major Second", "Minor Third", 
        "Major Third", "Perfect Fourth", "Augmented Fourth", "Perfect Fifth", 
        "Minor Sixth", "Major Sixth", "Minor Seventh", "Major Seventh", 
        "Perfect Octave"
    };
    private String[] abbreviations = {
        "P0", "m2", "M2", "m3", "M3", "P4", "A4", "P5", "m6", "M6", "m7", "M7", 
        "P8"
    };
    private IAnswer[] answers = new IAnswer[names.length];

    /**
   *Creates a new Intervals object that will communicate with
   *the supplied IScoreManager
   */
    public Intervals() {
        for ( int i = 0; i < names.length; i++ ) {
            int[][] structure = { { 0, i } };
            answers[i] = new BasicAnswer(structure);
            answers[i].setSpan( structure[0][1] );
            answers[i].setName( names[i] );
            answers[i].setAbbreviation( abbreviations[i] );
        }
    }

    /**
   *@see IExercise#getName()
   */
    public String getName() {
        return "Intervals";
    }

    /**
   *@see IExercise#getDescription()
   */
    public String getDescription() {
        return "All intervals up to one octave";
    }

    /**
   *@see IExercise#getAnswers()
   */
    public IAnswer[] getAnswers() {
        return answers;
    }
}