package com.peterlyons.smartears.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Note extends JComponent {

    protected int yOff;

    protected int midiValue;

    private Font arial10 = new Font("SansSerif", Font.PLAIN, 10);

    public Note(final Staff staff, boolean first) {
        addMouseListener( new MouseAdapter() {
            public void mousePressed( MouseEvent e ) {
                yOff = e.getY();
                staff.setSelectedNote(Note.this);
            }

            public void mouseReleased(MouseEvent event) {
                staff.setSelectedNote(null);
            }
        } );
        if (!first) {
            addMouseMotionListener( new MouseMotionAdapter() {
                public void mouseDragged( MouseEvent e ) {
                    //e.getX() + getX() - xOff (for dragging)
                    int newY = e.getY() + getY() - yOff;
                    if ( newY + getHeight() > getParent().getHeight() ) {
                        newY = getParent().getHeight() - getHeight();
                    }
                    setLocation( getX(), Math.max( 0, newY ) );
                    if (isSharp()) {
                        setForeground(Color.RED);
                    } else {
                        setForeground(Color.BLACK);  
                    }
                }
            } );
        }
    }

    public void paintComponent( Graphics g ) {
        super.paintComponent(g);
        g.setFont(arial10);
        g.setColor(getForeground());
        int half = getWidth()/2;
        g.fillOval( half, 0, half, half );
    }

    public boolean isSharp() {
            switch (getMIDIValue()) {
            case 58:
            case 61:
            case 63:
            case 66:
            case 68:
            case 70:
            case 73:
            return true;
            default:
            return false;
        }
    }
    
    public void update( Graphics g ) {
        paint( g );
    }

    public int getMIDIValue() {
        return midiValue;
    }

    public void setMIDIValue(int value) {
        midiValue = value;
        repaint();
    }
}