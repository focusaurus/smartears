package com.peterlyons.smartears;

import javax.sound.midi.Instrument;

import com.peterlyons.smartears.event.*;

/**
 *This class is repsonible for storing the user's current
 *preference settings
 *@see ISettingsManager
 *@see ISettings
 */
public class SettingsManager implements ISettingsManager,
                                        ISettings {
    private int delay = 10;
    private int duration = 5;
    private Instrument instrument;
    private int playMode = ISettings.ASCENDING;
    private ISettingsEventManager settingsEventManager;

    /**
   *Creates a new SettingsManager that will communicate 
   *with the supplied event manager
   */
    public SettingsManager( ISettingsEventManager sem ) {
        settingsEventManager = sem;
    }

    /**
   *@see ISettingsManager#delayChanged(int)
   */
    public void delayChanged( int newDelay ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( "SettingsManager.delayChange()" );
        }
        delay = newDelay;
        settingsEventManager.processDelayChanged( new SettingsEvent( this, 
                                                                     this ) );
    }

    /**
   *@see ISettingsManager#durationChanged(int)
   */
    public void durationChanged( int newDuration ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( "SettingsManager.durationChange()" );
        }
        duration = newDuration;
        settingsEventManager.processDurationChanged( new SettingsEvent( this, 
                                                                        this ) );
    }

    /**
   *@see ISettingsManager#instrumentChanged(Instrument)
   */
    public void instrumentChanged( Instrument newInstrument ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( "SettingsManager.instrumentChange()" );
        }
        instrument = newInstrument;
        settingsEventManager.processInstrumentChanged( new SettingsEvent( this, 
                                                                          this ) );
    }

    /**
   *@see ISettingsManager#playModeChanged(int)
   */
    public void playModeChanged( int playMode ) {
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( "SettingsManager.setPlayMode()" );
        }
        switch ( playMode ) {

            case ISettings.ASCENDING:
            case ISettings.DESCENDING:
                this.playMode = playMode;
                settingsEventManager.processPlayModeChanged( new SettingsEvent( 
                                                                     this, 
                                                                     this ) );
                break;
        }
    }

    /**
   *@see ISettings#getDelay()
   */
    public int getDelay() {
        return delay;
    }

    /**
   *@see ISettings#getDuration()
   */
    public int getDuration() {
        return duration;
    }

    /**
   *@see ISettings#getInstrument()
   */
    public Instrument getInstrument() {
        return instrument;
    }

    /**
   *@see ISettings#getPlayMode()
   */
    public int getPlayMode() {
        return playMode;
    }
}