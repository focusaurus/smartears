package net.codemusic.smartears;

import java.awt.*;

import javax.swing.*;

public class MainPanel extends JPanel {
    private JButton playBtn = new JButton( "Play" );
    private JButton repeatBtn = new JButton( "Repeat" );

    public GamePanel() {
        playBtn.addActionListener( new ActionListener() {
            public void actionPerformer( ActionEvent e ) {
                play();
            }
        } );
        repeatBtn.addActionListener( new ActionListener() {
            public void actionPerformer( ActionEvent e ) {
                repeat();
            }
        } );
        add( playBtn );
        add( repeatBtn );
    }

    private void play() {
        Controller.singleton().getGameController.play();
    }
    private void repeat() {
        Controller.singleton().getGameController.repeat();
    }
}