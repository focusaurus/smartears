package net.codemusic.smartears.dev;

import java.io.*;


/**
 * Title:        smartears
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      codemusic.net
 * @author Peter Lyons
 * @version 1.0
 */
import javax.sound.midi.*;

public class MidiTest1 {

    /*public static void main(String[] args) {
    try
    {
    PrintStream o = System.out;
      MidiDevice.Info[] mdi = MidiSystem.getMidiDeviceInfo();
      //mdi = null;
      for(int i=0; i<mdi.length;i++)
        {
    o.println("Device "+i);
    o.println("\t"+mdi[i].getName());
    o.println("\t"+mdi[i].getDescription());
    o.println("\t"+mdi[i].getVendor());
    o.println("\t"+mdi[i].getVersion());
        }
      MidiDevice.Info info = MidiSystem.getMidiDeviceInfo()[0];
    MidiDevice dev = MidiSystem.getMidiDevice(info);
    Receiver rec = dev.getReceiver();
    dev.open();
    ShortMessage msg1 = new ShortMessage();
    msg1.setMessage(ShortMessage.NOTE_ON, 0, 60, 70);
    rec.send(msg1, -1);
    rec.close();
    dev.close();
    System.exit(0);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    
    }*/
    public static void main( String[] args ) {
        try {
            PrintStream o = System.out;
            MidiDevice.Info[] mdi = MidiSystem.getMidiDeviceInfo();
            for ( int i = 0; i < mdi.length; i++ ) {
                o.println( "Device " + i );
                o.println( "\t" + mdi[i].getName() );
                o.println( "\t" + mdi[i].getDescription() );
                o.println( "\t" + mdi[i].getVendor() );
                o.println( "\t" + mdi[i].getVersion() );
            }
            Synthesizer synth = MidiSystem.getSynthesizer();
            Instrument[] insts = synth.getAvailableInstruments();
            MidiChannel[] channels = synth.getChannels();
            o.println( "Synth info:\n\t" + synth.getMaxReceivers() + 
                       " max receivers\n\t" + ( insts.length + 1 ) + 
                       " total instruments available\n\t" + 
                       ( channels.length + 1 ) + "total channels available" );
            ShortMessage msg1 = new ShortMessage();
            msg1.setMessage( ShortMessage.NOTE_ON, 0, 72, 90 );
            Receiver rec = synth.getReceiver();
            synth.open();
            channels[0].programChange( 121 );
            rec.send( msg1, -1 );
            for ( int i = 0; i < insts.length; i++ ) {
                o.println( i + ": " + insts[i].getName() );
            }
            o.println( "Hit Enter to stop the sound" );
            System.in.read();
            Sequence sequence = new Sequence( Sequence.PPQ, 1 );
            Track track = sequence.createTrack();
            track.add( createEvent( 60, 0 ) );
            track.add( createEvent( 64, 1 ) );
            track.add( createEvent( 67, 2 ) );
            track.add( createEvent( 72, 3 ) );
            ShortMessage msg2 = new ShortMessage();
            msg1.setMessage( ShortMessage.NOTE_OFF, 0, 72, 90 );
            MidiEvent event1 = new MidiEvent( msg2, 4 );
            track.add( event1 );
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setSequence( sequence );
            sequencer.getTransmitter().setReceiver( synth.getReceiver() );
            System.out.println( "about to start the sequencer" );
            sequencer.start();
            o.println( "Hit Enter to stop the sound" );
            System.in.read();
            //   rec.close();
            synth.close();
            sequencer.close();
            System.exit( 0 );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }
    public static MidiEvent createEvent( int midiNote, int position ) {
        ShortMessage msg1 = new ShortMessage();
        try {
            msg1.setMessage( ShortMessage.NOTE_ON, 0, midiNote, 90 );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return new MidiEvent( msg1, position );
    }
}