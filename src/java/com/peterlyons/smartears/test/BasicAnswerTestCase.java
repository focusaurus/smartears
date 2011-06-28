package com.peterlyons.smartears.test;

import com.peterlyons.smartears.*;

public class BasicAnswerTestCase extends junit.framework.TestCase {
    public BasicAnswerTestCase( String name ) {
        super( name );
    }

    public void testEquals() {
        int[][] structure = new int[][] {
            { 0, 4, 7 }, { 0, 5, 9 }, { 0, 4, 6 }
        };
        BasicAnswer one = new BasicAnswer(structure);
        BasicAnswer two = new BasicAnswer(structure);
        BasicAnswer three = new BasicAnswer(new int[][] { { 0, 5, 8 } });
        assertTrue( ! one.equals( null ) );
        assertTrue( ! one.equals( new Object() ) );
        assertTrue( ! one.equals( three ) );
    }
}