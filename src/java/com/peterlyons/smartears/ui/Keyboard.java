package com.peterlyons.smartears.ui;

import java.awt.*;
import java.awt.event.*;

import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

/**
 *This class is on onscreen mockup of a piano keyboard
 */

//BUGBUG this class is currently tied to it's immediate use as a range delimiter
//it can be made more generic and reusable, but later, of course
public class Keyboard extends JPanel {

    /**
   *This class represents one Key on the keyboard
   */
    class Key extends JPanel {
        private boolean down = false;
        private int midiNote = -1;
        private Color upColor;
        private Color downColor;
        private Border upBorder = BorderFactory.createBevelBorder( 
                                          BevelBorder.RAISED, Color.white, 
                                          Color.gray );
        private Border downBorder = BorderFactory.createBevelBorder( 
                                            BevelBorder.LOWERED, Color.white, 
                                            Color.gray );

        /**
     *Constructs a new Key
     *@param midiNote the MIDI note that this key will represent
     *@param upColor the color this key should be when it is not depresed
     *@param downColor the color this key should be when it is depressed
     */
        public Key( int midiNote, Color upColor, Color downColor ) {
            this.midiNote = midiNote;
            this.upColor = upColor;
            this.downColor = downColor;
            addMouseListener( new MouseAdapter() {
                public void mouseClicked( MouseEvent e ) {
                    keyClicked( Key.this );
                }
            } );
            setBackground( upColor );
            setBorder( upBorder );
        }

        /**
     *Returns a boolean indicating whether this key is
     *currently down
     */
        public boolean isDown() {
            return down;
        }

        /**
     *This accessor method returns the MIDI note that this key represnts
     */
        public int getMidiNote() {
            return midiNote;
        }

        /**
     *Pushes the key down
     */
        public void depress() {
            down = true;
            setBackground( downColor );
            setBorder( downBorder );
        }

        /**
     *Lets the key come back up
     */
        public void release() {
            down = false;
            setBackground( upColor );
            setBorder( upBorder );
        }
    } //End class Key

    private Color downColor = new Color( 204, 204, 255 );
    protected Key lowerBound = new Key( Integer.MIN_VALUE, Color.white, 
                                        downColor );
    protected Key upperBound = new Key( Integer.MAX_VALUE, Color.white, 
                                        downColor );
    private int whiteKeyWidth = 16;
    private int whiteKeyHeight = 80;
    private int blackKeyWidth = 12;
    private int blackKeyHeight = 50;
    private int octaves = 5;

    /**
   *Constructs a new Keyboard with the default values
   *of five octaves and a base note of 36
   */
    public Keyboard() {
        this( 5, 36 );
    }

    /**
   *Constructs a new Keyboard
   *@param numOctaves how many octaves should be on the keyboard
   *@param baseNote the MIDI note that the lowest key should have. 
   *FYI middle C is MIDI note 60
   */
    public Keyboard( int numOctaves, int baseNote ) {
        octaves = numOctaves;
        JLayeredPane jlp = new JLayeredPane();
        int[] whiteIDs = { 0, 2, 4, 5, 7, 9, 11 };
        int[] blackIDs = { 1, 3, 6, 8, 10 };
        int[] blackKeyOffsets = { 0, 1, 3, 4, 5 };
        for ( int octave = 0; octave < numOctaves; octave++ ) {
            //insert the white keys into the layer 1
            for ( int i = 0; i < whiteIDs.length; i++ ) {
                int midiNote = baseNote + whiteIDs[i] + ( 12 * octave );
                Key whiteKey = new Key( midiNote, Color.white, downColor );
                jlp.add( whiteKey, new Integer( 1 ) );
                whiteKey.setBounds( ( octave * 7 + i ) * whiteKeyWidth, 0, 
                                    whiteKeyWidth, whiteKeyHeight );
                //BUGBUG note that the default ranges are
                //currently hardcoded from the RangeManager and
                //should be made dynamic
                if ( midiNote == 60 ) {
                    lowerBound = whiteKey;
                    lowerBound.depress();
                }
                if ( midiNote == 84 ) {
                    upperBound = whiteKey;
                    upperBound.depress();
                }
            }
            for ( int i = 0; i < blackIDs.length; i++ ) {
                //insert the black keys into layer 2
                Key blackKey = new Key( baseNote + blackIDs[i] + 
                                        ( 12 * octave ), Color.black, 
                                        downColor );
                jlp.add( blackKey, new Integer( 2 ) );
                //NOTE that the 6 pixel offset here is hard coded.
                //May want to make it a percent of the white key width
                blackKey.setBounds( 
                        ( octave * 7 + blackKeyOffsets[i] ) * whiteKeyWidth + 6, 
                        0, blackKeyWidth, blackKeyHeight );
            }
        }
        add( jlp );
        jlp.setPreferredSize( new Dimension( numOctaves * 7 * whiteKeyWidth, 
                                             whiteKeyHeight ) );
        setPreferredSize( jlp.getPreferredSize() );
    }

    /**
   *Called to indicate that one of the keys has been pressed
   *Currently, this method keeps 2 keys and only 2 keys down at all times
   *Clicks in between the 2 keys affect the depressed key that is closet to
   *the key just clicked.  Ties go to the lower bound
   */
    protected void keyClicked( Key key ) {
        int note = key.getMidiNote();
        if ( note <= lowerBound.getMidiNote() ) {
            //make the lower bound lower
            lowerBound.release();
            lowerBound = key;
            lowerBound.depress();
        } else if ( note >= upperBound.getMidiNote() ) {
            //make the upper bound higher
            upperBound.release();
            upperBound = key;
            upperBound.depress();
        } else {
            //it's in the middle
            //change the bound that it is closer to
            int lowerDist = note - lowerBound.getMidiNote();
            int upperDist = upperBound.getMidiNote() - note;
            if ( lowerDist <= upperDist ) {
                lowerBound.release();
                lowerBound = key;
                lowerBound.depress();
            } else {
                upperBound.release();
                upperBound = key;
                upperBound.depress();
            }
        }
    }
    public Dimension getPreferredSize() {
        return new Dimension( ( octaves * 7 * whiteKeyWidth ) + 10, 
                              whiteKeyHeight + 30 );
    }
    //For Development only

    /*  public static void main(String[] args)
        {
        JFrame frame = new JFrame()
        { 
                      
        protected void processWindowEvent(WindowEvent e)
        {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING)
        {
        System.exit(0);
        }
        }
        }; 
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(new Keyboard(5,36));
        frame.pack();
        frame.setVisible(true);
        frame.show();
        }*/
}