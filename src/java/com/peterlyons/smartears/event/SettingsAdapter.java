package com.peterlyons.smartears.event;


/**
 *This is a conveniece Adapter that provides no-op
 *implementations of the SettingsListener interface.  
 *It is designed to be subclassed by classes only interested
 *in some of the settings events
 */
public class SettingsAdapter implements SettingsListener {

    /**
   *@see SettingsListener#delayChanged(SettingsEvent)
   */
    public void delayChanged( SettingsEvent e ) {}

    /**
   *@see SettingsListener#durationChanged(SettingsEvent)
   */
    public void durationChanged( SettingsEvent e ) {}

    /**
   *@see SettingsListener#instrumentChanged(SettingsEvent)
   */
    public void instrumentChanged( SettingsEvent e ) {}

    /**
   *@see SettingsListener#playModeChanged(SettingsEvent)
   */
    public void playModeChanged( SettingsEvent e ) {}
}