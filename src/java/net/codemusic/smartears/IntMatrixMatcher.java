package net.codemusic.smartears;

import java.util.Arrays;

public class IntMatrixMatcher {
    public static boolean equals( int[][] one, int[][] two ) {
        if ( one == null && two == null ) {
            return true;
        }
        if ( ( one == null && two != null ) || 
             ( one != null && two == null ) ) {
            return false;
        }
        if ( one.length == 0 && two.length == 0 ) {
            return true;
        }
        if ( one.length != two.length ) {
            return false;
        }
        for ( int i = 0; i < one.length; i++ ) {
            if ( ! Arrays.equals( one[i], two[i] ) ) {
                return false;
            }
        }
        return true;
    }
}