package net.codemusic.smartears;


/**
 *This interface represents a class that is responsible
 *for storing the user's current preference settings
 *and creating settings related events
 */
public interface ISettingsManager {

    /**
   *Called when the user has adjusted the note delay.
   *@param newDelay the new delay value
   */
    public void delayChanged( int newDelay );

    /**
   *Called when he user has adjusted the note duration
   *@param newDuration the new duration value
   */
    public void durationChanged( int newDuration );

    /**
   *Called when the user has selected a different instrument sound
   *@param newInstrument the sound the user has selected
   */
    public void instrumentChanged( javax.sound.midi.Instrument newInstrument );

    /**
   *Called when the user has changed the play mode
   *@param playMode a symbolic constand from the ISettings interface that 
   *represents playing the answers ascending or descending
   */
    public void playModeChanged( int playMode );
}