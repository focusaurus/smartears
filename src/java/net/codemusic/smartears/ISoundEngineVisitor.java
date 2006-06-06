package net.codemusic.smartears;


/**
 *This Visitor allows the answer to call a method of the 
 *ISoundengine appropriate for its internal structure.  
 *Currently, all answers are modeled with two dimensional arrays of integers,
 *but more sophisticated answer that contain timing information, etc
 *are planned
 */
public interface ISoundEngineVisitor {

    /*
     *has this visitor play an answer that can be represented 
     *as a two dimensional array of ints
     */
    public void playIntMatrix( int[][] structureMatrix, Object param );
}