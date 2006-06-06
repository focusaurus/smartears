package net.codemusic.smartears;

import java.util.*;

/**
 *  This strategy generates new melodic pattern answers
 *
 *@author     plyons
 *@created    August 17, 2002
 */
public class MelodicPatternFactoryStrategy implements IAnswerSelectionStrategy {

    public static final int MIN_NOTES = 3;
    
    public static final int MAX_NOTES = 4;
   
   /**
     *  Generates a new BasicAnswer structured as a melodic pattern
     *
     *@param  answers  is ignored
     *@return  Description of the Return Value
     */
    public IAnswer select( Vector answers ) {
        int max = 0;
        int length = randomLength();
        int[][] structure = new int[1][length];
        for ( int i = 0; i < length; i++ ) {
            int note = (int)( Math.random() * 12 );
            structure[0][i] = note;
            max = Math.max(max, note);
        }
        //the structure must have 0 as the lowest element somewhere
        structure[0][(int)( Math.random() * (length - 1) )] = 0;
        BasicAnswer answer = new BasicAnswer(structure);
        answer.setSpan(max);
        return answer;    
    }

    protected int randomLength() {
        double length = (Math.random() * (MAX_NOTES - MIN_NOTES)) + MIN_NOTES;
        return Math.round((float)length);   
    }
}