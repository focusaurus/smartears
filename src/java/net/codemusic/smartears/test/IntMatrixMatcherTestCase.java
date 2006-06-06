package net.codemusic.smartears.test;

import net.codemusic.smartears.*;

public class IntMatrixMatcherTestCase extends junit.framework.TestCase {
    public IntMatrixMatcherTestCase( String name ) {
        super( name );
    }

    public static void main( String[] args ) {
        new IntMatrixMatcherTestCase( "hi" ).testEquals();
    }
    public void testEquals() {
        assertTrue( "Two nulls are equal", 
                    IntMatrixMatcher.equals( null, null ) );
        assertTrue( ! IntMatrixMatcher.equals( null, new int[0][0] ) );
        assertTrue( ! IntMatrixMatcher.equals( new int[0][0], null ) );
        assertTrue( IntMatrixMatcher.equals( new int[0][0], new int[0][0] ) );
        assertTrue( IntMatrixMatcher.equals( new int[7][8], new int[7][8] ) );
        assertTrue( ! IntMatrixMatcher.equals( new int[1][0], new int[0][0] ) );
        assertTrue( 
                "Arrays with first dimension of zero length are equal even if second dimesion lengths are unequal", 
                IntMatrixMatcher.equals( new int[0][1], new int[0][0] ) );
        assertTrue( ! IntMatrixMatcher.equals( new int[0][0], new int[1][0] ) );
        assertTrue( IntMatrixMatcher.equals( new int[0][0], new int[0][1] ) );
        int[][] a = { { 0 }, { 0, 1 }, { 0, 1, 2 } };
        int[][] b = { { 0 }, { 0, 1 }, { 0, 1, 2 } };
        int[][] c = { { 0 }, { 0, 1 }, { 0, 1, 9 } };
        int[][] d = { { 0, 9 }, { 0, 1 }, { 0, 1, 2 } };
        assertTrue( IntMatrixMatcher.equals( a, b ) );
        assertTrue( ! IntMatrixMatcher.equals( b, c ) );
        assertTrue( ! IntMatrixMatcher.equals( c, d ) );
    }
}