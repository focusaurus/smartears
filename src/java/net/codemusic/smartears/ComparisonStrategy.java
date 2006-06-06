package net.codemusic.smartears;

import net.codemusic.*;

/**
 *This startegy generates new melodic pattern answers
 */
public class ComparisonStrategy implements Strategy {
    protected IAnswer correct;
    protected IAnswer guess;
    protected IController controller;

    public ComparisonStrategy( IAnswer correct, IAnswer guess, 
                               IController controller ) {
        this.correct = correct;
        this.guess = guess;
        this.controller = controller;
    }

    public void execute() {
        if ( correct.equals( guess ) ) {
            System.out.println( "Correct!" );
            controller.getScoreManager().correct( guess );
        } else {
            System.out.println( "Incorrect!" );
            controller.getScoreManager().incorrect( guess );
        }
    }
}