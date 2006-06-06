package net.codemusic.smartears.event;


/**
 *This interface represents a class that is responsble
 *for registering, unregistering, and propogating SettingsEvents to
 *SettingsListeners
 */
public interface ISettingsEventManager {

    /**
   *Registers a SettingsListener
   *@param listener the SettingsListener to register.
   *It will now be notified of all SettingsEvents
   */
    public void addSettingsListener( SettingsListener listener );

    /**
   *Unregisters a SettingsListener
   *@param listener the SettingsListener to unregister.
   *It willno longer be notified of any SettingsEvents
   */
    public void removeSettingsListener( SettingsListener listener );

    /**
   *Notifies all registered SettingsListeners of this event
   *by calling their delayChanged(SettingsEvent e) method
   *@param e the SettingsEvent associated with this change
   */
    public void processDelayChanged( SettingsEvent e );

    /**
   *Notifies all registered SettingsListeners of this event
   *by calling their durationChanged(SettingsEvent e) method
   *@param e the SettingsEvent associated with this change
   */
    public void processDurationChanged( SettingsEvent e );

    /**
   *Notifies all registered SettingsListeners of this event
   *by calling their instrumentChanged(SettingsEvent e) method
   *@param e the SettingsEvent associated with this change
   */
    public void processInstrumentChanged( SettingsEvent e );

    /**
   *Notifies all registered SettingsListeners of this event
   *by calling their playModeChanged(SettingsEvent e) method
   *@param e the SettingsEvent associated with this change
   */
    public void processPlayModeChanged( SettingsEvent e );
}