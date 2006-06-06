package net.codemusic.smartears.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class StaffLine extends JPanel {
	protected Staff staff;
	protected int midiValue;

	public StaffLine( final Staff aStaff, final int aMidiValue ) {
		staff = aStaff;
		midiValue = aMidiValue;
		addMouseListener( new MouseAdapter() {

            public void mouseReleased(MouseEvent event) {
                staff.setSelectedNote(null);
            }
		});
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
	}

	public void update( Graphics g ) {
		paint( g );
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		switch ( midiValue ) {
			case 64:
			case 67:
			case 71:
			case 74:
			case 77:
			g.setColor(Color.BLACK);
			g.fillRect(0, (int)getHeight()/5*2, getWidth(), (int)getHeight()/5);
			break;
		}
	}
    
    public int getMIDIValue() {
        return midiValue;   
    }
    
    public String toString() {
        return "StaffLine " + getMIDIValue();   
    }
}