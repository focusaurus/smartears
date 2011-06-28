package com.peterlyons.smartears;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.peterlyons.smartears.event.*;

/**
 * Title:        SmartEars
 * Description:  An ear Training program.
 * Copyright:    Copyright (c) 2001
 * Company:      peterlyons.com
 * @author Peter Lyons
 * @version 1.0
 */
public class MainFrame extends JFrame {
    private JPanel contentPane;
    private JMenuBar jMenuBar1 = new JMenuBar();
    private JMenu jMenuFile = new JMenu( "File" );
    private JMenuItem jMenuFileExit = new JMenuItem( "Exit" );
    private JMenu jMenuHelp = new JMenu( "Help" );
    private JMenuItem jMenuHelpAbout = new JMenuItem( "About" );
    private JLabel statusBar = new JLabel();
    private IExampleManager exampleManager = Controller.getExampleManager();
    private IExampleEventManager exampleEventManager = Controller.getExampleEventManager();

    /**Construct the frame*/
    public MainFrame() {
        enableEvents( AWTEvent.WINDOW_EVENT_MASK );
        //BUGBUG JBuilder did this.  Is it really necessary?
        try {
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        boolean packFrame = false;
        guiStart();
        //BUGBUG probably don't need this crap
        //Validate frames that have preset sizes
        //Pack frames that have useful preferred size info, e.g. from their layout
        if ( packFrame ) {
            pack();
        } else {
            validate();
        }
        //Center the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        if ( frameSize.height > screenSize.height ) {
            frameSize.height = screenSize.height;
        }
        if ( frameSize.width > screenSize.width ) {
            frameSize.width = screenSize.width;
        }
        setLocation( ( screenSize.width - frameSize.width ) / 2, 
                     ( screenSize.height - frameSize.height ) / 2 );
        setVisible( true );
    }

    /**Component initialization*/
    private void guiStart() {
        //setIconImage(Toolkit.getDefaultToolkit().createImage(MainFrame.class.getResource("[Your Icon]")));
        contentPane = (JPanel)this.getContentPane();
        contentPane.setLayout( new BorderLayout() );
        JButton playBtn = new JButton( "Play" );
        final JButton repeatBtn = new JButton( "Repeat" );
        playBtn.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                exampleManager.play();
                repeatBtn.setEnabled( true );
            }
        } );
        repeatBtn.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                exampleManager.repeat();
            }
        } );
        repeatBtn.addMouseListener( new MouseAdapter() {
            public void mouseClicked( MouseEvent e ) {
                System.out.println( "shift: " + e.isShiftDown() );
                System.out.println( "control:" + e.isControlDown() );
                System.out.println( "alt: " + e.isAltDown() );
                System.out.println( "meta: " + e.isMetaDown() );
                System.out.println( "popup: " + e.isPopupTrigger() );
            }
        } );
        repeatBtn.setEnabled( false );
        JPanel examplePanel = new JPanel();
        examplePanel.add( playBtn );
        examplePanel.add( repeatBtn );
        contentPane.add( examplePanel, BorderLayout.NORTH );
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add( "Main", 
                        createMainPanel( Controller.getExercise().getAnswers() ) );
        tabbedPane.add( "Settings", createSettingsPanel() );
        contentPane.add( tabbedPane, BorderLayout.CENTER );
        this.setSize( new Dimension( 400, 300 ) );
        this.setTitle( "SmartEars" );
        jMenuFileExit.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                jMenuFileExit_actionPerformed( e );
            }
        } );
        jMenuHelpAbout.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                jMenuHelpAbout_actionPerformed( e );
            }
        } );
        jMenuFile.add( jMenuFileExit );
        jMenuHelp.add( jMenuHelpAbout );
        jMenuBar1.add( jMenuFile );
        jMenuBar1.add( jMenuHelp );
        this.setJMenuBar( jMenuBar1 );
        //contentPane.add(new ExercisePanel(), BorderLayout.CENTER);
        contentPane.add( statusBar, BorderLayout.SOUTH );
    }
    private JPanel createMainPanel( final IAnswer[] answers ) {
        JPanel mainPanel = new JPanel( new BorderLayout() );
        JPanel answerPanel = new JPanel( new GridLayout( 
                                                 (int)answers.length / 2, 2, 3, 
                                                 3 ) );
        for ( int i = 0; i < answers.length; i++ ) {
            final JButton button = new JButton( answers[i].getAbbreviation() );
            final int index = i;
            button.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    answers[index].handleGuess();
                }
            } );
            exampleEventManager.addExampleListener( new ExampleAdapter() {
                public void enable( ExampleEvent e ) {
                    if ( e.getAnswer().equals( answers[index] ) ) {
                        button.setEnabled( true );
                    }
                }
                public void disable( ExampleEvent e ) {
                    if ( e.getAnswer().equals( answers[index] ) ) {
                        button.setEnabled( false );
                    }
                }
            } );
            button.addMouseListener( new MouseAdapter() {
                public void mouseClicked( MouseEvent e ) {
                    //Note Meta is the right click on Linux
                    //BUGBUG need to test on other platforms
                    if ( e.isMetaDown() ) {
                        ExampleEvent event = new ExampleEvent( button, 
                                                               answers[index] );
                        if ( button.isEnabled() ) {
                            exampleEventManager.processAnswerDisabled( event );
                        } else {
                            exampleEventManager.processAnswerEnabled( event );
                        }
                        //toggle the button
                        button.setEnabled( ! button.isEnabled() );
                    }
                }
            } );
            button.setEnabled( false );
            button.setToolTipText( 
                    "Right click to remove or include certain anwers" );
            exampleEventManager.addExampleListener( new ExampleAdapter() {
                public void play( ExampleEvent e ) {
                    button.setEnabled( true );
                    exampleEventManager.removeExampleListener( this );
                }
            } );
            answerPanel.add( button );
        }
        mainPanel.add( answerPanel, BorderLayout.CENTER );
        JPanel scorePanel = new JPanel( new BorderLayout() );
        JPanel labelPanel = new JPanel( new GridLayout( 4, 1, 0, 3 ) );
        labelPanel.add( new JLabel( "Correct Answers:" ) );
        labelPanel.add( new JLabel( "Incorrect guesses:" ) );
        labelPanel.add( new JLabel( "Total Examples:" ) );
        labelPanel.add( new JLabel( "Score:" ) );
        scorePanel.add( labelPanel, BorderLayout.CENTER );
        //BUGBUG these really should use a GridbagLayout
        JPanel valuePanel = new JPanel( new GridLayout( 4, 1, 0, 3 ) );
        valuePanel.add( new JLabel( "0" ) );
        valuePanel.add( new JLabel( "0" ) );
        valuePanel.add( new JLabel( "0" ) );
        valuePanel.add( new JLabel( "0 %" ) );
        scorePanel.add( labelPanel, BorderLayout.CENTER );
        scorePanel.add( valuePanel, BorderLayout.EAST );
        mainPanel.add( scorePanel, BorderLayout.EAST );
        return mainPanel;
    }
    private JPanel createSettingsPanel() {
        JPanel settings = new JPanel();
        return settings;
    }
    public void setStatus( String status ) {
        statusBar.setText( status );
    }

    /**File | Exit action performed*/
    public void jMenuFileExit_actionPerformed( ActionEvent e ) {
        System.exit( 0 );
    }

    /**Help | About action performed*/
    public void jMenuHelpAbout_actionPerformed( ActionEvent e ) {
        MainFrame_AboutBox dlg = new MainFrame_AboutBox( this );
        Dimension dlgSize = dlg.getPreferredSize();
        Dimension frmSize = getSize();
        Point loc = getLocation();
        dlg.setLocation( ( frmSize.width - dlgSize.width ) / 2 + loc.x, 
                         ( frmSize.height - dlgSize.height ) / 2 + loc.y );
        dlg.setModal( true );
        dlg.show();
    }

    /**Overridden so we can exit when window is closed*/
    protected void processWindowEvent( WindowEvent e ) {
        super.processWindowEvent( e );
        if ( e.getID() == WindowEvent.WINDOW_CLOSING ) {
            jMenuFileExit_actionPerformed( null );
        }
    }
}