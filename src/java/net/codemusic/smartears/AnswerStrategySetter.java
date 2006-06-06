/*
 * AnswerStrategySetter.java
 *
 * Version 1.0
 *
 * 2001/11/03
 *
 * Copyright (c) 2001 by Peter Lyons, pete@codemusic.net
 *
 */
package net.codemusic.smartears;

import net.codemusic.Strategy;

import net.codemusic.smartears.event.*;

/**
 * This class handles changing the IAnswer's guessStrategy
 * when it is sellected so that it knows that it is the
 * correct answer
 */
public class AnswerStrategySetter extends ExampleAdapter {

    /*
     * When an IAnswer is played, it is the correct answer,
     * so this class sets the IAnswer's guessStrategy to
     * be the default correct strategy.
     */
    public void examplePlayed( ExampleEvent e ) {
        e.getAnswer().setGuessStrategy( e.getAnswer().getDefaultCorrectStrategy() );
    }
}