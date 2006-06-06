package net.codemusic.smartears.event;


/**
 *An interface that should be implemented by classes
 *wishing to tie into the Settings Event subsystem
 */
public interface SettingsListener {

    /**
   *Will be called when the delay value is modified
   *@param e the SettingsEvent associated with this change
   */
    public void delayChanged( SettingsEvent e );

    /**
   *Will be called when the duration value is modified
   *@param e the SettingsEvent associated with this change
   */
    public void durationChanged( SettingsEvent e );

    /**
   *Will be called when the istrument sound is changed
   *@param e the SettingsEvent associated with this change
   */
    public void instrumentChanged( SettingsEvent e );

    /**
   *Will be called when the play mode is changed
   *@param e the SettingsEvent associated with this change
   */
    public void playModeChanged( SettingsEvent e );
}