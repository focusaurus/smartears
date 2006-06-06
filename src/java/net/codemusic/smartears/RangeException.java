package net.codemusic.smartears;


/**
 *This class of Exception indicates a problem with the range of something
 */
public class RangeException extends Exception {
    public RangeException( String msg ) {
        super( msg );
    }
    public RangeException() {
        super();
    }
}