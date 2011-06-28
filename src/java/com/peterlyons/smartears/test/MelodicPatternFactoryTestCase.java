package com.peterlyons.smartears.test;

import java.util.*;

import com.peterlyons.smartears.*;

public class MelodicPatternFactoryTestCase extends junit.framework.TestCase {
    protected MelodicPatternFactoryStrategy factory = new MelodicPatternFactoryStrategy();

    public MelodicPatternFactoryTestCase( String name ) {
        super( name );
    }

    public void testCreateStructure() {
        //BUGBUG commented out for now
        //int[][] structure = factory.createStructure();
        //Arrays.sort( structure[0] );
        //assertEquals( 0, structure[0][0] );
    }
}