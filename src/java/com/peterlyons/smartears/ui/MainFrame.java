package com.peterlyons.smartears.ui;

import java.awt.*;
import java.awt.event.*;

import java.io.*;

import java.util.*;

import javax.sound.midi.Instrument;
import javax.sound.sampled.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import com.peterlyons.smartears.*;
import com.peterlyons.smartears.event.*;

/**
 *This is a utility class that allows a JList to render
 *javax.sound.midi.Instrument objects getting the string
 *from their getName() method instead of calling toString()
 */
class InstrumentRenderer extends DefaultListCellRenderer {
    public Component getListCellRendererComponent( JList list, Object value, 
                                                   int index, 
                                                   boolean isSelected, 
                                                   boolean cellHasFocus ) {
        JLabel label = (JLabel)super.getListCellRendererComponent( list, value, 
                                                                   index, 
                                                                   isSelected, 
                                                                   cellHasFocus );
        label.setText( ( (Instrument)value ).getName().trim() );
        return label;
    }
}

/**
 *This is the Frame that presents the Swing-based GUI
 *for SmartEars
 */
public class MainFrame extends JFrame implements ScoreListener,
                                                 ExerciseListener {
    private Instrument[] instruments;
    private JLabel correctLabel = new JLabel( "0" );
    private JLabel incorrectLabel = new JLabel( "0" );
    private JLabel scoreLabel = new JLabel( "          " );
    private static JLabel statusLabel = new JLabel( "Press Play to begin...", 
                                                    JLabel.CENTER );
    private JLabel examplesPlayedLabel = new JLabel( "0" );
    private JPanel mainPanel = new JPanel( new BorderLayout() );
    private JPanel answerPanel;
    private JMenuBar jMenuBar1 = new JMenuBar();
    private JMenu exerciseMenu = new JMenu( "Exercise" );
    private JMenuItem exerciseMenuExit = new JMenuItem( "Exit" );
    //  private JMenu jMenuHelp = new JMenu("Help");
    // private JMenuItem jMenuHelpAbout = new JMenuItem("About");
    private JSlider delaySlider;
    private JSlider durationSlider;
    JTabbedPane tabbedPane = new JTabbedPane();
    private Hashtable answerButtons = new Hashtable();
    private IController controller;
    private ScoreAdapter autoPlayListener = new ScoreAdapter() {
        public void correct( ScoreEvent e ) {
            controller.getExampleManager().play();
        }
    };
    private Clip correctClip;
    private Clip incorrectClip;
    private AudioInputStream correctAIS;
    private AudioInputStream incorrectAIS;
    private boolean playUISounds = true;
    private int examplesPlayed = 0;
    //plyons turning off Melodic patterns for now
    //private final Staff staff = new Staff();

    /**Construct the frame*/
    public MainFrame( IController ctrl ) {
        controller = ctrl;
        controller.getExampleEventManager().addExampleListener( new ExampleAdapter() {
            public void examplePlayed( ExampleEvent e ) {
                if ( SmartEars.DEBUGGING_ENABLED ) {
                    System.out.println( 
                            "MainFrame's example adapter that enables all possible answers got called" );
                }
                IAnswer[] answers = controller.getAnswerManager().getAnswers();
                //enable all possible answers
                for ( int i = 0; i < answers.length; i++ ) {
                    ( (JButton)answerButtons.get( answers[i] ) ).setEnabled( 
                            true );
                }
                setStatus( "Identify this sound..." );
                examplesPlayedLabel.setText( "" + ( ++examplesPlayed ) );
            }
        } );
        instruments = controller.getInstruments();
        controller.getScoreEventManager().addScoreListener( this );
        controller.getExerciseEventManager().addExerciseListener( this );
        try {
            correctAIS = AudioSystem.getAudioInputStream( getClass().getClassLoader().getResource( 
                                                                  "sounds/correct.wav" ) );
            incorrectAIS = AudioSystem.getAudioInputStream( getClass().getClassLoader().getResource( 
                                                                    "sounds/incorrect.wav" ) );
        } catch ( UnsupportedAudioFileException exception ) {
            //should never happen at run time
        }
         catch ( IOException exception ) {
            System.err.println( 
                    "SmartEars had problems reading the sound files." );
            playUISounds = false;
        }
        try {
            correctClip = (Clip)AudioSystem.getLine( new DataLine.Info( 
                                                             Clip.class, 
                                                             correctAIS.getFormat() ) );
            incorrectClip = (Clip)AudioSystem.getLine( new DataLine.Info( 
                                                               Clip.class, 
                                                               correctAIS.getFormat() ) );
            correctClip.open( correctAIS );
            incorrectClip.open( incorrectAIS );
        } catch ( LineUnavailableException exception ) {
            System.err.println( 
                    "WARNING: Unable to get a sound line to use for playing .wav sounds" );
            playUISounds = false;
        }
         catch ( IOException exception ) {
            System.err.println( 
                    "SmartEars had problems reading the sound files." );
            playUISounds = false;
        }
        enableEvents( AWTEvent.WINDOW_EVENT_MASK );
        //BUGBUG JBuilder did this.  Is it really necessary?
        try {
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        //create all of the GUI components
        guiStart();
        //layout the components
        pack();
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
        getContentPane().setLayout( new BorderLayout() );
        getContentPane().add( createExamplePanel(), BorderLayout.NORTH );
        tabbedPane.add( "Main", 
                        createMainPanel( controller.getExercises()[0] ) );
        tabbedPane.add( "Settings", createSettingsPanel() );
        tabbedPane.getInputMap( JComponent.WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( 
                                                                                 'm' ), 
                                                                         "showMainPanel" );
        tabbedPane.getInputMap( JComponent.WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( 
                                                                                 's' ), 
                                                                         "showSettingsPanel" );
        getContentPane().add( tabbedPane, BorderLayout.CENTER );
        this.setTitle( "SmartEars" );
        exerciseMenuExit.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                exerciseMenuExit_actionPerformed( e );
            }
        } );
        // jMenuHelpAbout.addActionListener(new ActionListener()
        // {
        //   public void actionPerformed(ActionEvent e)
        //{
        //  jMenuHelpAbout_actionPerformed(e);
        //}
        //});
        populateSmartEarsMenu( controller.getExercises() );
        exerciseMenu.add( exerciseMenuExit );
        //  jMenuHelp.add(jMenuHelpAbout);
        jMenuBar1.add( exerciseMenu );
        // jMenuBar1.add(jMenuHelp);
        this.setJMenuBar( jMenuBar1 );
        controller.getScoreEventManager().addScoreListener( new ScoreAdapter() {
            public void correct( ScoreEvent se ) {
                statusLabel.setText( " Correct! :-)" );
                if ( playUISounds ) {
                    correctClip.start();
                    while ( correctClip.isActive() ) {
                        try {
                            Thread.sleep( 200 );
                        } catch ( InterruptedException e ) {
                            e.printStackTrace();
                        }
                    }
                    correctClip.stop();
                }
            }
            public void incorrect( ScoreEvent se ) {
                statusLabel.setText( " Incorrect :-(" );
                if ( playUISounds ) {
                    incorrectClip.start();
                    while ( incorrectClip.isActive() ) {
                        try {
                            Thread.sleep( 200 );
                        } catch ( InterruptedException e ) {
                            e.printStackTrace();
                        }
                        ;
                    }
                    incorrectClip.stop();
                    //disable the button they just clicked so they don't guess wrong twice
                    JButton answerButton = (JButton)answerButtons.get( se.getAnswer() );
                    if ( answerButton != null ) {
                        answerButton.setEnabled( false );
                    }
                }
            }
        } );
    }
    private JPanel createExamplePanel() {
        JButton playBtn = new JButton( "Play" );
        final JButton repeatBtn = new JButton( "Repeat" );
        //NOTE this is the Swing 1.3 way to do it, and it works
        Action playExampleAction = new AbstractAction() {
            public void actionPerformed( ActionEvent e ) {
                controller.getExampleManager().play();
                repeatBtn.setEnabled( true );
            }
        };
        Action repeatExampleAction = new AbstractAction() {
            public void actionPerformed( ActionEvent e ) {
                controller.getExampleManager().repeat();
            }
        };
        playBtn.getInputMap( JComponent.WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( 
                                                                              'p' ), 
                                                                      "playExample" );
        playBtn.getActionMap().put( "playExample", playExampleAction );
        playBtn.addActionListener( playExampleAction );
        playBtn.setMnemonic( 'p' );
        //NOTE, this works properly, but is not the 1.3 way to do it
        // playBtn.registerKeyboardAction(playAction,
        //            "play",
        //            KeyStroke.getKeyStroke('p'),
        //            JComponent.WHEN_IN_FOCUSED_WINDOW);
        repeatBtn.getInputMap( JComponent.WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( 
                                                                                'r' ), 
                                                                        "repeatExample" );
        repeatBtn.getActionMap().put( "repeatExample", repeatExampleAction );
        repeatBtn.addActionListener( repeatExampleAction );
        repeatBtn.setMnemonic( 'r' );
        repeatBtn.setEnabled( false );
        JPanel examplePanel = new JPanel( new BorderLayout() );
        JPanel buttonPanel = new JPanel();
        buttonPanel.add( playBtn );
        buttonPanel.add( repeatBtn );
        examplePanel.add( buttonPanel, BorderLayout.SOUTH );
        examplePanel.add( statusLabel, BorderLayout.NORTH );
        return examplePanel;
    }
    private JPanel createAnswerPanel( final IExercise exercise ) {
        JPanel answerPanel = new JPanel( new BorderLayout() );
        if ( exercise instanceof IRecognitionExercise ) {
            IAnswer[] answers = ( (IRecognitionExercise)exercise ).getAnswers();
            JLabel buttonHint = new JLabel( 
                                        "Right click buttons to enable/disable particular answers" );
            answerPanel.add( buttonHint, BorderLayout.SOUTH );
            JPanel buttonPanel = new JPanel( new GridBagLayout() );
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.BOTH;
            gbc.gridy = 0;
            gbc.gridx = 0;
            // gbc.ipadx=20;
            //gbc.ipady=20;
            gbc.weightx = 1;
            gbc.weighty = 1;
            int row = 0;
            int column = 0;
            JButton[] buttons = createAnswerButtons( answers );
            for ( int i = 0; i < buttons.length; i++ ) {
                gbc.gridx = column++;
                gbc.gridy = row;
                if ( ( i + 1 ) % 3 == 0 ) {
                    row++;
                    column = 0;
                }
                buttonPanel.add( buttons[i], gbc );
            }
            answerPanel.add( buttonPanel, BorderLayout.CENTER );
        } else if ( exercise instanceof IReproductionExercise ) {
            /*JLabel buttonHint = new JLabel( 
                                        "Type your midi values separated by spaces and hit \"Guess\"" );
            answerPanel.add( buttonHint, BorderLayout.SOUTH );
            JPanel buttonPanel = new JPanel();
            final JTextField inputTF = new JTextField( "0 10 11 " );
            buttonPanel.add( inputTF );
            JButton guessButton = new JButton( "Guess" );
            guessButton.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    //inputTF.getText()
                    submitMelodicPatternGuess( staff.getAnswer(), e );
                    
                }
            } );
            buttonPanel.add( guessButton );
            answerPanel.add(staff, BorderLayout.CENTER );
            answerPanel.add( buttonPanel, BorderLayout.NORTH );
            //BUGBUG clean this up - do it elsewhere
            SmartEars.getInstance().getRangeManager().setUpperBound(73);
            SmartEars.getInstance().getRangeManager().setLowerBound(57);
            */
        }
        return answerPanel;
    }
    protected void submitMelodicPatternGuess( BasicAnswer guess, //String guessString, 
                                              ActionEvent e ) {
        /*StringTokenizer st = new StringTokenizer( guessString );
        ArrayList notes = new ArrayList();
        while ( st.hasMoreTokens() ) {
            try {
                notes.add( new Integer( st.nextToken() ) );
            } catch ( NumberFormatException nfe ) {
                //skip it
            }
        }
        int[][] structure = new int[1][notes.size()];
        for ( int i = 0; i < notes.size(); i++ ) {
            structure[0][i] = ( (Integer)notes.get( i ) ).intValue();
        }*/
        guess.setGuessStrategy( new ComparisonStrategy( controller.getExampleManager().getLastPlayedAnswer(), 
                                                        guess, controller ) );
        System.out.print( "You guessed: " );
        printArray( guess.getStructure()[0] );
        System.out.println( "" );
        System.out.print( "Correct is: " );
        printArray( 
                ( (BasicAnswer)controller.getExampleManager().getLastPlayedAnswer() ).getStructure()[0] );
        guess.actionPerformed( e );
    }
                                              
    protected void printArray( int[] value ) {
        for ( int i = 0; i < value.length; i++ ) {
            System.out.print( value[i] + " " );
        }
    }
    
    private JButton[] createAnswerButtons( final IAnswer[] answers ) {
        JButton[] buttons = new JButton[answers.length];
        //Begin big button init loop
        for ( int i = 0; i < answers.length; i++ ) {
            final int index = i;
            final JButton button = new JButton( answers[index].getAbbreviation() );
            button.setToolTipText( answers[index].getName() );
            answerButtons.put( answers[index], button );
            button.addActionListener( answers[index] );
            controller.getExampleEventManager().addExampleListener( new ExampleAdapter() {
                public void answerEnabled( ExampleEvent e ) {
                    if ( e.getAnswer() == answers[index] ) {
                        button.setEnabled( true );
                    }
                }
                public void answerDisabled( ExampleEvent e ) {
                    if ( e.getAnswer() == answers[index] ) {
                        button.setEnabled( false );
                    }
                }
            } );
            button.addMouseListener( new MouseAdapter() {
                public void mouseClicked( MouseEvent e ) {
                    //Note Meta is the right click on Linux and Windows
                    //Command-click on Mac OS X
                    if ( e.isMetaDown() ) {
                        if ( button.isEnabled() ) {
                            controller.getExampleManager().disableAnswer( 
                                    answers[index] );
                        } else {
                            controller.getExampleManager().enableAnswer( 
                                    answers[index] );
                        }
                    }
                }
            } );
            //disable all buttons when the program is first launched
            button.setEnabled( false );
            //button.setToolTipText("Right click to remove or include certain anwers");
            //all buttons are enabled after the first example is played
            controller.getExampleEventManager().addExampleListener( new ExampleAdapter() {
                public void examplePlayed( ExampleEvent e ) {
                    button.setEnabled( true );
                    controller.getExampleEventManager().removeExampleListener( 
                            this );
                }
            } );

            /*    controller.getScoreEventManager().addScoreListener(new ScoreAdapter()
            {
            public void correct(ScoreEvent e)
            {
            answers[index].setGuessStrategy(alreadyCorrectStrategy);
            }
            });*/

            //Assigns the numbers 0-9 to the first 9 buttons
            //BUGBUG figure out how you want to do keybindings
            KeyStroke buttonKeyStroke = KeyStroke.getKeyStroke( new Character( 
                                                                        (char)( index % 10 + 48 ) ), 
                                                                ( index > 9 )
                                                                    ? Event.CTRL_MASK
                                                                    : 0 );
            button.getInputMap( JComponent.WHEN_IN_FOCUSED_WINDOW ).put( 
                    buttonKeyStroke, button );
            button.getActionMap().put( button, 
                                       new AbstractAction() {
                public void actionPerformed( ActionEvent e ) {
                    button.doClick();
                }
            } );
            buttons[index] = button;
        } //end big button init loop
        return buttons;
    }
    private JPanel createMainPanel( final IExercise exercise ) {
        answerPanel = createAnswerPanel( exercise );
        mainPanel.add( answerPanel, BorderLayout.CENTER );
        JPanel scorePanel = new JPanel( new BorderLayout() );
        JPanel labelPanel = new JPanel( new GridLayout( 4, 1, 0, 3 ) );
        labelPanel.add( new JLabel( "Correct answers: " ) );
        labelPanel.add( new JLabel( "Incorrect guesses: " ) );
        labelPanel.add( new JLabel( "Examples played: " ) );
        labelPanel.add( new JLabel( "Score: " ) );
        scorePanel.add( labelPanel, BorderLayout.EAST );
        //BUGBUG these really should use a GridbagLayout
        JPanel valuePanel = new JPanel( new GridLayout( 4, 1, 0, 3 ) );
        valuePanel.add( correctLabel );
        valuePanel.add( incorrectLabel );
        valuePanel.add( examplesPlayedLabel );
        valuePanel.add( scoreLabel );
        scorePanel.add( labelPanel, BorderLayout.CENTER );
        scorePanel.add( valuePanel, BorderLayout.EAST );
        mainPanel.add( scorePanel, BorderLayout.EAST );
        Action showMainPanel = new AbstractAction() {
            public void actionPerformed( ActionEvent e ) {
                tabbedPane.setSelectedIndex( tabbedPane.indexOfComponent( 
                                                     mainPanel ) );
            }
        };
        tabbedPane.getActionMap().put( "showMainPanel", showMainPanel );
        return mainPanel;
    }
    private JPanel createSettingsPanel() {
        final JPanel settingsPanel = new JPanel( new BorderLayout() );
        JPanel instrumentPanel = new JPanel( new BorderLayout() );
        JPanel timingPanel = new JPanel( new GridLayout( 2, 1, 4, 4 ) );
        JPanel centerPanel = new JPanel( new BorderLayout() );
        Action showSettingsPanel = new AbstractAction() {
            public void actionPerformed( ActionEvent e ) {
                tabbedPane.setSelectedIndex( tabbedPane.indexOfComponent( 
                                                     settingsPanel ) );
            }
        };
        tabbedPane.getActionMap().put( "showSettingsPanel", showSettingsPanel );
        if ( instruments.length > 0 ) {
            final JList instrumentList = new JList( instruments );
            instrumentList.setSelectionMode( 
                    ListSelectionModel.SINGLE_SELECTION );
            instrumentList.setCellRenderer( new InstrumentRenderer() );
            instrumentList.addListSelectionListener( new ListSelectionListener() {
                public void valueChanged( ListSelectionEvent e ) {
                    Instrument inst = (Instrument)instrumentList.getSelectedValue();
                    controller.getSettingsManager().instrumentChanged( inst );
                }
            } );
            //have piano initially highlighted
            instrumentList.setSelectedIndex( 0 );
            //    instrumentList.setBorder(new TitledBorder("Instrument Sound:"));
            JScrollPane instrumentSP = new JScrollPane( instrumentList );
            JLabel instrumentLabel = new JLabel( "Instrument Sound:" );
            instrumentPanel.add( instrumentLabel, BorderLayout.NORTH );
            instrumentPanel.add( instrumentSP, BorderLayout.CENTER );
            //    instrumentSP.setBorder(new TitledBorder("Instrument Sound:"));
            //    instrumentSP.setBorder(BorderFactory.createEmptyBorder());
            settingsPanel.add( instrumentPanel, BorderLayout.WEST );
        }
        //    JLabel delayLabel = new JLabel("Note Delay: ", SwingConstants.RIGHT);
        //JLabel durationLabel = new JLabel("Note Duration: ", SwingConstants.RIGHT);
        //JPanel delayPanel = new JPanel();
        delaySlider = new JSlider( 0, 50, 10 );
        delaySlider.setMajorTickSpacing( 10 );
        delaySlider.setMinorTickSpacing( 2 );
        delaySlider.setPaintTicks( true );
        delaySlider.setPaintLabels( true );
        delaySlider.setLabelTable( delaySlider.createStandardLabels( 10 ) );
        delaySlider.addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent e ) {
                controller.getSettingsManager().delayChanged( delaySlider.getValue() );
            }
        } );
        delaySlider.setBorder( BorderFactory.createTitledBorder( "Note Delay:" ) );
        delaySlider.setToolTipText( 
                "Time between each note - set to 0 to hear notes simultaneously" );
        timingPanel.add( delaySlider );
        //JPanel durationPanel = new JPanel();
        durationSlider = new JSlider( 0, 50, 10 );
        durationSlider.setMajorTickSpacing( 10 );
        durationSlider.setMinorTickSpacing( 2 );
        durationSlider.setPaintTicks( true );
        durationSlider.setPaintLabels( true );
        durationSlider.setLabelTable( durationSlider.createStandardLabels( 10 ) );
        durationSlider.addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent e ) {
                controller.getSettingsManager().durationChanged( durationSlider.getValue() );
            }
        } );
        durationSlider.setBorder( BorderFactory.createTitledBorder( 
                                          "Note Duration:" ) );
        timingPanel.add( durationSlider );
        JPanel autoPlayPanel = new JPanel( new GridLayout( 2, 1, 4, 4 ) );
        JRadioButton manualPlayBtn = new JRadioButton( 
                                             "wait for me to push the Play button" );
        JRadioButton autoPlayBtn = new JRadioButton( 
                                           "play a new example automatically" );
        ButtonGroup playBG = new ButtonGroup();
        playBG.add( manualPlayBtn );
        playBG.add( autoPlayBtn );
        manualPlayBtn.setSelected( true );
        autoPlayPanel.add( manualPlayBtn );
        autoPlayPanel.add( autoPlayBtn );
        autoPlayPanel.setBorder( BorderFactory.createTitledBorder( 
                                         "When I guess correctly..." ) );
        centerPanel.add( timingPanel, BorderLayout.CENTER );
        centerPanel.add( autoPlayPanel, BorderLayout.SOUTH );
        settingsPanel.add( centerPanel, BorderLayout.CENTER );
        manualPlayBtn.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                controller.getScoreEventManager().removeScoreListener( 
                        autoPlayListener );
            }
        } );
        autoPlayBtn.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                controller.getScoreEventManager().addScoreListener( 
                        autoPlayListener );
            }
        } );
        // durationPanel.add(durationLabel);
        // durationPanel.add(durationSlider);
        // timingPanel.add(durationPanel);
        Keyboard rangeKb = new Keyboard( 6, 36 ) {
            protected void keyClicked( Keyboard.Key key ) {
                super.keyClicked( key );
                //keep the rangeManager updated on the state of the keyboard
                controller.getRangeManager().setLowerBound( super.lowerBound.getMidiNote() );
                controller.getRangeManager().setUpperBound( super.upperBound.getMidiNote() );
            }
        };
        rangeKb.setBorder( BorderFactory.createTitledBorder( 
                                   "Range Boundaries:" ) );
        settingsPanel.add( rangeKb, BorderLayout.SOUTH );
        // JLabel rangeLabel = new JLabel("Range Boundaries:", JLabel.CENTER);
        //JPanel rangePanel = new JPanel(new BorderLayout());
        //rangePanel.add(rangeLabel, BorderLayout.NORTH);
        //rangePanel.add(rangeKb, BorderLayout.SOUTH);
        // rangePanel.setBorder(BorderFactory.createEtchedBorder());
        //JLabel playModeLabel = new JLabel("Play examples:");
        ButtonGroup playModeBG = new ButtonGroup();
        final JRadioButton ascRB = new JRadioButton( "Ascending" );
        final JRadioButton descRB = new JRadioButton( "Descending" );
        final JRadioButton mixedRB = new JRadioButton( "Mixed" );
        ascRB.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                controller.getSettingsManager().playModeChanged( 
                        ISettings.ASCENDING );
            }
        } );
        descRB.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                controller.getSettingsManager().playModeChanged( 
                        ISettings.DESCENDING );
            }
        } );
        mixedRB.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                controller.getSettingsManager().playModeChanged( 
                        ISettings.MIXED );
            }
        } );

        /*controller.getExampleEventManager().addExampleListener( new ExampleAdapter() {
            public void examplePlayed( ExampleEvent e ) {
                //mixes Ascending and Descending modes about 50/50
                if ( mixedRB.isSelected() ) {
                    controller.getSettingsManager().playModeChanged( 
                            ( ( Math.random() < .5 )
                                  ? ISettings.ASCENDING : ISettings.DESCENDING ) );
                }
            }
        } );*/
        controller.getSettingsEventManager().addSettingsListener( new SettingsAdapter() {
            public void playModeChanged( SettingsEvent e ) {
                int playMode = e.getSettings().getPlayMode();
                switch ( playMode ) {

                    case ISettings.ASCENDING:
                        ascRB.setSelected( true );
                        break;

                    case ISettings.DESCENDING:
                        descRB.setSelected( true );
                        break;

                    case ISettings.MIXED:
                        mixedRB.setSelected( true );
                        break;
                }
            }
        } );
        playModeBG.add( ascRB );
        playModeBG.add( descRB );
        playModeBG.add( mixedRB );
        mixedRB.setSelected( true );
        JPanel playModePanel = new JPanel( new GridLayout( 4, 1, 3, 3 ) );
        // playModePanel.add(playModeLabel);
        playModePanel.add( ascRB );
        playModePanel.add( descRB );
        playModePanel.add( mixedRB );
        playModePanel.setBorder( BorderFactory.createTitledBorder( 
                                         "Play Examples:" ) );
        settingsPanel.add( playModePanel, BorderLayout.EAST );
        return settingsPanel;
    }

    /**
     * Takes all exercises and makes a JRadioButtonMenuItem for each of the,
     * adds them to a ButtonGroup, and puts them on the SmartEars menu
     */
    private void populateSmartEarsMenu( IExercise[] exercises ) {
        ButtonGroup group = new ButtonGroup();
        for ( int i = 0; i < exercises.length; i++ ) {
            final IExercise exercise = exercises[i];
            JRadioButtonMenuItem mi = new JRadioButtonMenuItem( exercise.getName() );
            group.add( mi );
            mi.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    controller.getExerciseManager().exerciseChanged( 
                            exercise );
                }
            } );
            mi.setToolTipText( exercise.getDescription() );
            if ( i == 0 ) {
                mi.setSelected( true );
            }
            exerciseMenu.add( mi );
        }
    }

    /**
     * Sets the status message in the frame
     */
    public static void setStatus( String status ) {
        statusLabel.setText( status );
    }

    /**
   *Keeps the score labels updated
   */
    public void correct( ScoreEvent e ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( "MainFrame.correct() - updating score labels" );
        }
        updateScoreLabels( e );
    }

    /**
   *Keeps the score labels updated
   */
    public void incorrect( ScoreEvent e ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( 
                    "MainFrame.incorrect() - updating score labels" );
        }
        updateScoreLabels( e );
    }
    private void updateScoreLabels( ScoreEvent e ) {
        correctLabel.setText( "" + e.getScore().getCorrectCount() );
        incorrectLabel.setText( "" + e.getScore().getIncorrectCount() );
        setScoreLabel( e.getScore() );
    }

    /**
   *Updates the score label with the user's score
   *as a percentage correct
   */
    private void setScoreLabel( IScore e ) {
        int total = e.getCorrectCount() + e.getIncorrectCount();
        int percent = (int)( ( (float)e.getCorrectCount() / total ) * 100 );
        scoreLabel.setText( e.getScore() + " (" + percent + "%)" );
    }
    public void repeat( ScoreEvent e ) {}

    /**
     * Keeps the score labels updated
     */
    public void reset( ScoreEvent e ) {
        correctLabel.setText( "0" );
        incorrectLabel.setText( "0" );
        examplesPlayedLabel.setText( "0" );
        scoreLabel.setText( "" );
    }

    /**
     *Updates the answer panel to have buttons for the new IExercise
     */
    public void exerciseChanged( ExerciseEvent e ) {
        mainPanel.remove( answerPanel );
        answerPanel = createAnswerPanel( e.getExercise() );
        mainPanel.add( answerPanel, BorderLayout.CENTER );
        //initializes the timings settings for scales nicely
        if ( e.getExercise().getName().toLowerCase().indexOf( "scale" ) > -1 ) {
            delaySlider.setValue( 5 );
            durationSlider.setValue( 5 );
        }
        if ( e.getExercise() instanceof MelodicPatterns ) {
            controller.getAnswerManager().setSelectionStrategy( new MelodicPatternFactoryStrategy() );
        } else {
            controller.getAnswerManager().setSelectionStrategy( new RandomStrategy() );
        }
    }

    /**File | Exit action performed*/
    public void exerciseMenuExit_actionPerformed( ActionEvent e ) {
        controller.shutdown();
    }

    /**Help | About action performed*/
    public void jMenuHelpAbout_actionPerformed( ActionEvent e ) {

        /*BUGBUG fix this JBuilder silliness
          MainFrame_AboutBox dlg = new MainFrame_AboutBox(this);
          Dimension dlgSize = dlg.getPreferredSize();
          Dimension frmSize = getSize();
          Point loc = getLocation();
          dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
          dlg.setModal(true);
          dlg.show();
        */
    }

    /**Overridden so we can exit when window is closed*/
    protected void processWindowEvent( WindowEvent e ) {
        super.processWindowEvent( e );
        if ( e.getID() == WindowEvent.WINDOW_CLOSING ) {
            exerciseMenuExit_actionPerformed( null );
        }
    }
}