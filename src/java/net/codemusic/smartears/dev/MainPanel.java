package net.codemusic.smartears;

import java.awt.*;

import javax.swing.*;

public class MainPanel extends JPanel {
    private JButton playBtn = new JButton( "Play" );
    private JTabbedPane tabbedPane = new JTabbedPane( JTabbedPane.TOP );
    private JPanel mainPanel = new JPanel( new BorderLayout() );
    private JPanel settingsPanel = new JPanel( new BorderLayout() );
    private IController controller;

    public MainPanel( IController controller ) {
        this.controller = controller;
        setLayout( new BorderLayout() );
        add( playBtn, BorderLayout.NORTH );
        tabbedPane.add( "Main", mainPanel );
        tabbedPane.add( "Settings", settingsPanel );
        add( tabbedPane, BorderLayout.CENTER );
    }
}