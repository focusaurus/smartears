package net.codemusic.smartears;

import java.io.*;

import javax.sound.midi.*;

import net.codemusic.smartears.event.*;

/**
 *This class generates sounds based on arrays of ints using java's MIDI capabilities
 *in the javax.sound.midi package
 */
public class MidiSoundEngine implements ISoundEngine,
                                        ISoundEngineVisitor {

    /*  private interface IntArrayIterator
        {
        public boolean hasNext();
        public int next();
        public void setData(int[][] data);
        }
                          
        private class AscendingIterator implements IntArrayIterator
        {
        private int i = 0;
        private int j = 0;
        private int[][] data;
        public int next()
        {
        if(j>=data[i].length)
        {
        j=0;
        i++;
        }
        return data[i][j++];
                          
        }
                          
        public boolean hasNext()
        {
        return i<(data.length-1)||j<data[i].length;
        }
                          
        public void setData(int[][] data)
        {
        this.data=data;
        i=j=0;
        }
        }
                        
        private IntArrayIterator ascendingIterator = new AscendingIterator();
                          
        private IntArrayIterator iterator = ascendingIterator;
    */
    private ISettings settings;
    private Synthesizer synth;
    private Sequencer sequencer;
    private Sequence sequence;
    private ShortMessage programChange = new ShortMessage();
    private static final int NOTE_ON = ShortMessage.NOTE_ON;
    private static final int NOTE_OFF = ShortMessage.NOTE_OFF;
    private int baseNote = 60;
    private int duration = 5;
    private int delay = 10;
    private int velocity = 50;
    private float tempoFactor = 1.0f;
    private boolean closed = false;

    /**
   *Creates a new MidiSoundEngine
   *@param settings this ISettings will be conluted for sound settings
   *@exception MidiUnavailableException if Midi sounds cannot be produced
   */
    public MidiSoundEngine( ISettings settings )
                    throws MidiUnavailableException {
        this.settings = settings;
        sequencer = MidiSystem.getSequencer();
        if ( sequencer == null ) {
            throw new MidiUnavailableException( "null sequencer" );
        }
        sequencer.open();
        synth = MidiSystem.getSynthesizer();
        synth.open();
        //hook the sequencer up to the synthesizer
        sequencer.getTransmitter().setReceiver( synth.getReceiver() );
        //fix for bug in JRE 1.4+ where an extra note is played
        //with the first note until you change instruments at least once
        setInstrument(synth.getAvailableInstruments()[0]);
    }

    /**
   *Plays an answer
   *@param answer the answer that will be played
   *@param base the midi note that will be the base note for the answer
   */
    public void play( IAnswer answer, int base ) {
        if ( closed ) {
            throw new IllegalStateException( "SoundEngine cannot be used after it is closed" );
        }
        //BUGBUG fix this
        answer.acceptSoundEngineVisitor( this, new Integer( base ) );
    }

    /**
   *plays a two dimensional array of ints as midi notes.  Should be called by IAnswers 
   *that are based on two dimensional arrays of ints
   *@param structure the structure of the answer
   *@param param should be an Integer object represnting the base note
   */
    public void playIntMatrix( int[][] structure, Object param ) {
        if ( closed ) {
            throw new IllegalStateException( "SoundEngine cannot be used after it is closed" );
        }
        //BUGBUG figure out optimal resolution,
        //and whether a new object is needed each time
        //    iterator = ascendingIterator;
        //iterator.setData(structure);
        try {
            sequence = new Sequence( Sequence.PPQ, 1 );
            for ( int i = 0; i < structure.length; i++ ) {
                Track track = sequence.createTrack();
                int tick = 0;
                if ( i == 0 ) {
                    track.add( new MidiEvent( programChange, 0 ) );
                }
                int playMode = settings.getPlayMode();
                if ( playMode == ISettings.MIXED ) {
                    playMode = ( Math.random() < .5 )
                                   ? ISettings.ASCENDING : ISettings.DESCENDING;
                }
                switch ( settings.getPlayMode() ) {

                    case ISettings.DESCENDING:
                        for ( int j = structure[i].length - 1; j >= 0; j-- ) {
                            track.add( shortMessage( NOTE_ON, 
                                                     structure[i][j] + ( (Integer)param ).intValue(), 
                                                     tick ) );
                            track.add( shortMessage( NOTE_OFF, 
                                                     structure[i][j] + ( (Integer)param ).intValue(), 
                                                     tick + duration ) );
                            tick += delay;
                        }
                        break;

                    default: //ASCENDING
                        for ( int j = 0; j < structure[i].length; j++ ) {
                            track.add( shortMessage( NOTE_ON, 
                                                     structure[i][j] + ( (Integer)param ).intValue(), 
                                                     tick ) );
                            track.add( shortMessage( NOTE_OFF, 
                                                     structure[i][j] + ( (Integer)param ).intValue(), 
                                                     tick + duration ) );
                            tick += delay;
                        }
                        break;
                }
            }
            sequencer.setSequence( sequence );
            sequencer.setTempoFactor( 4.0f );
            sequencer.start();
        } catch ( InvalidMidiDataException e ) {
            System.err.println( "MidiSoundEngine caught some bad MIDI data" );
            e.printStackTrace();
        }
    }

    /**
   *For this sound engine, the delay is in somewhat abstract midi ticks.
   *Values from 0-50 are about as extreme as you want to get.
   *@see ISoundEngine#setDelay(int)
   */
    public void setDelay( int newDelay ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( "MidiSoundEngine setting delay to " + 
                                newDelay );
        }
        delay = newDelay;
    }

    /**
   *For this sound engine, the duration is in somewhat abstract midi ticks.
   *Values from 0-50 should suit most purposes.
   *@see ISoundEngine#setDuration(int)
   */
    public void setDuration( int newDuration ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( 
                    "MidiSoundEngine setting duration to " + newDuration );
        }
        if ( newDuration == 0 ) {
            newDuration = 1;
        }
        duration = newDuration;
    }

    /**
   *@see ISoundEngine#setInstrument(Instrument)
   */
    public void setInstrument( Instrument i ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( "Swithching to " + i.getName() );
        }
        try {
            programChange.setMessage( ShortMessage.PROGRAM_CHANGE, 
                                      i.getPatch().getProgram(), 
                                      i.getPatch().getBank() );
        } catch ( InvalidMidiDataException e ) {
            System.out.println( "MidiSoundengine.programChange() screwed up" );
            e.printStackTrace();
        }
    }

    /**
   *@see ISoundEngine#getInstruments()
   */
    public Instrument[] getInstruments() {
        return synth.getAvailableInstruments();
    }

    /**
   *a convenience method that creates the ShortMessages that are used
   *to create the sounds
   */
    private MidiEvent shortMessage( int type, int midiNote, int tick ) {
        ShortMessage msg1 = new ShortMessage();
        try {
            msg1.setMessage( type, 0, midiNote, velocity );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return new MidiEvent( msg1, tick );
    }

    /**
   *Called to indicate that this MidiSoundEngine is no longer needed.
   *This method releases the Audio synthesizer resources that it is using.
   */
    public void close() {
        sequencer.close();
        synth.close();
    }

    /**
   *Overridden from Object to make sure the audio resources are
   *released
   */
    public void finalize() throws Throwable {
        sequencer.close();
        super.finalize();
    }
}