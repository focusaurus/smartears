package net.codemusic.smartears;


/**
 *This class manages the user-defined range of pitches 
 *that sounds should be played in
 */
public class RangeManager implements IRangeManager {

    /**
   *The lower bound of the range.  Defaults to middle C
   */
    private int lowerBound = 60;

    /**
   *The upper bound of the range.  Defaults to 
   *two octaves above middle C
   */
    private int upperBound = 84;

    /**
   *lowerBound must be between 21 and 107 (piano keyboard range)
   *@see IRangeManager#setLowerBound(int)
   */
    public void setLowerBound( int lowerBound ) {
        //restrict lowerBound to piano keyboard range
        if ( lowerBound < 21 || lowerBound > 107 ) {
            throw new IllegalArgumentException( "Lower Bound too low" );
        }
        this.lowerBound = lowerBound;
    }

    /**
   *@see IRangeManager#getLowerBound()
   */
    public int getLowerBound() {
        return lowerBound;
    }

    /**
   *@see IRangeManager#getUpperBound()
   */
    public int getUpperBound() {
        return upperBound;
    }

    /**
   *upperBound must be between 22 and 108
   *@see IRangeManager#setUpperBound(int)
   */
    public void setUpperBound( int upperBound ) {
        //restrict upperBound to piano keyboard range
        if ( lowerBound < 22 || lowerBound > 108 ) {
            throw new IllegalArgumentException( "Upper Bound too high" );
        }
        this.upperBound = upperBound;
    }

    /**
   *Returns a boolean indicating whether the supplied
   *note is between the upper and lower bounds (inclusive)
   */
    public boolean isWithinRange( int note ) {
        return note >= lowerBound && note <= upperBound;
    }

    /**
   *Using the supplied answer's span, selects a random
   *base note that will ensure that the answer will fit within
   *the current range boundaries.
   *@exception RangeException is thrown if the answer's span is too big for the
   *current range
   */
    public int selectBase( IAnswer answer ) throws RangeException {
        int span = answer.getSpan();
        if ( span > ( upperBound - lowerBound ) ) {
            throw new RangeException( "Answer will not fit in current range" );
        }
        int base = (int)( Math.random() * ( upperBound - lowerBound ) ) + 
                   lowerBound;
        if ( ( base + span ) > upperBound ) {
            base -= ( ( base + span ) - upperBound );
        }
        if ( SmartEars.DEBUGGING_ENABLED ) {
            System.out.println( "Base is " + base );
        }
        return base;
    }
}