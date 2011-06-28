package net.codemusic.smartears;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class ExercisePanel extends JPanel {
    private JButton playBtn = new JButton( "Play" );
    private JButton repeatBtn = new JButton( "Repeat" );
    private IController controller;

    public ExercisePanel( IController controller ) {
        this.controller = controller;
        playBtn.addActionListener( new ActionListener() {
            public void actionPerformer( ActionEvent e ) {
                Controller.getExampleManager().play();
            }
        } );
        repeatBtn.addActionListener( new ActionListener() {
            public void actionPerformer( ActionEvent e ) {
                Controller.getExampleManager().repeat();
            }
        } );
        add( playBtn );
        add( repeatBtn );
    }
}