package com.peterlyons.smartears.event;

import com.peterlyons.smartears.*;

/**
 *This event represents a change in the user's preferences 
 */
public class SettingsEvent extends java.util.EventObject
    implements java.io.Serializable {
    private ISettings settings;

    /**
   *Creates a new SettingsEvent
   *@param source the object that created the event
   * @param settings the user's current settings  
   */
    public SettingsEvent( Object source, ISettings settings ) {
        super( source );
        this.settings = settings;
    }

    /**
   *Returns an Isettings object representing the user's 
   *current settings
   */
    public ISettings getSettings() {
        return settings;
    }
}