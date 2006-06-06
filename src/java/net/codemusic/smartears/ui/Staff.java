package net.codemusic.smartears.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import net.codemusic.smartears.*;
import net.codemusic.smartears.event.*;

public class Staff extends JLayeredPane {
	
    protected StaffLine[] staffLines = new StaffLine[17];
    
    protected Note selectedNote;
    
    protected java.util.List notes = new ArrayList();
    
    protected ExampleEvent lastExampleEvent;
    
    protected JPanel notePanel = new JPanel((LayoutManager)null);

	public Staff() {
		setLayout(new OverlayLayout(this));
		JPanel staffPanel = new JPanel();
		staffPanel.setLayout(new GridLayout( 17, 1, 0, 0 ));
		setBackground( Color.WHITE );
		staffLines[0] = new StaffLine( this, 73 );
		staffLines[1] = new StaffLine( this, 72 );
		staffLines[2] = new StaffLine( this, 71 );
		staffLines[3] = new StaffLine( this, 70 );
		staffLines[4] = new StaffLine( this, 69 );
		staffLines[5] = new StaffLine( this, 68 );
		staffLines[6] = new StaffLine( this, 67 );
		staffLines[7] = new StaffLine( this, 66 );
		staffLines[8] = new StaffLine( this, 65 );
		staffLines[9] = new StaffLine( this, 64 );
		staffLines[10] = new StaffLine( this, 63 );
		staffLines[11] = new StaffLine( this, 62 );
		staffLines[12] = new StaffLine( this, 61 );
		staffLines[13] = new StaffLine( this, 60 );
		staffLines[14] = new StaffLine( this, 59 );
		staffLines[15] = new StaffLine( this, 58 );
		staffLines[16] = new StaffLine( this, 57 );
		for(int i=0;i < staffLines.length; i++ ) {
			staffPanel.add(staffLines[i]);	
		}
		notePanel.setOpaque(false);

		add(staffPanel, DEFAULT_LAYER);
		add(notePanel, PALETTE_LAYER);
		setBackground( Color.WHITE );
        SmartEars.getInstance().getExampleEventManager().addExampleListener(new ExampleAdapter() {
           public void examplePlayed(ExampleEvent event) {
               resetNotes(event);
           }
        });
	}
    
    public void setSelectedNote(Note value) {
        if(value == null) {
            if (selectedNote == null) {
                //they just clicked - ignore
                printPattern();
                return;   
            } else {
                //they dropped a note, set it's value
                Point location = selectedNote.getLocation();
                for(int i=0; i < staffLines.length; i++) {
                    StaffLine line = staffLines[i];
                    int x = (int)(location.getX() - line.getLocation().getX());
                    int y = (int)(location.getY() - line.getLocation().getY());
                    if (line.contains(x, y)) {
                        selectedNote.setMIDIValue(line.getMIDIValue());
                        MainFrame.setStatus("Picked: " + line.getMIDIValue());
                    }
                }
            }
        }
        selectedNote = value;    
    }
    
    protected void resetNotes(ExampleEvent event) {
        lastExampleEvent = event;
        Iterator it = notes.iterator();
        while (it.hasNext()) {
            notePanel.remove((Component)it.next());
        }
        notes.clear();
        int[][] structure = ((BasicAnswer)event.getAnswer()).getStructure();
        int base = event.getBase();
        int firstMIDI = structure[0][0] + base;
        System.out.println("First: " + firstMIDI);
        StaffLine line = staffLines[73-firstMIDI];
        for (int i = 0; i < structure[0].length; i++) {
            Note note = new Note(this, (i==0));
            note.setMIDIValue(firstMIDI);
            notes.add(note);
            notePanel.add(note);
            note.setBounds(new Rectangle(20+20*i, line.getY(), 30, 30));
        }
        repaint();
    }
    
    protected void printPattern() {
           Iterator it = notes.iterator();
           StringBuffer buf = new StringBuffer();
           while (it.hasNext()) {
                Note note = (Note)it.next();
                buf.append(note.getMIDIValue());
                if (it.hasNext()) {
                   buf.append(", "); 
                }
           }
           MainFrame.setStatus(buf.toString());
    }
    
    public BasicAnswer getAnswer() {
        int base = lastExampleEvent.getBase();
        int[][] structure = new int[1][notes.size()];
        Iterator it = notes.iterator();
        while (it.hasNext()) {
            Note note = (Note)it.next();
            structure[0][notes.indexOf(note)] = note.getMIDIValue()- base;
        }
        return new BasicAnswer(structure);
    }
}