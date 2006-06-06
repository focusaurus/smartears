package net.codemusic.smartears;

import java.io.*;

import javax.sound.midi.*;

import net.codemusic.Strategy;

public class Demo1 {
    private IAnswer[] answers;
    private ISoundEngine sndEng;
    private IAnswerManager ansMan;
    private PrintStream out = System.out;
    private PrintStream err = System.err;
    private RangeManager rangeMan = new RangeManager();
    //used to read user input from the command line
    private BufferedReader console;

    class IncorrectStrategy implements Strategy {
        public void execute() {
            out.println( "incorrect :-(" );
        }
    }

    public static void main( String[] args ) {
        Demo1 demo1 = new Demo1();
        demo1.start();
        System.exit( 0 );
    }

    private IncorrectStrategy icStrat = new IncorrectStrategy();

    public Demo1() {
        try {
            sndEng = new MidiSoundEngine();
        } catch ( MidiUnavailableException e ) {
            err.println( 
                    "Sorry, I can't figure out how to do Midi on this machine.. Bye" );
            e.printStackTrace();
            System.exit( 0 );
        }
        console = new BufferedReader( new InputStreamReader( System.in ) );
        IExcercise intervals = new Intervals();
        answers = intervals.getAnswers();
        ansMan = new AnswerManager();
        for ( int i = 0; i < answers.length; i++ ) {
            answers[i].setGuessStrategy( icStrat );
            //int[][] struct = ((BasicAnswer)answers[i]).getStructure();
            //int[][] newStruct = {{struct[0][0]},{struct[0][1]}};
            //((BasicAnswer)answers[i]).setStructure(newStruct);
            ansMan.add( answers[i] );
        }
    }

    public void start() {
        out.println( "Let's identify some intervals!\ntype q to quit" );
        String command = "";
        while ( ! "q".equals( command ) ) {
            playExcercise();
            try {
                command = console.readLine();
                if ( ! "q".equals( command ) ) {
                    answers[Integer.parseInt( command )].handleGuess();
                }
            } catch ( IOException e ) {
                e.printStackTrace();
                break;
            }
             catch ( NumberFormatException e ) {
                err.println( "invalid response.  Use anly numbers 0-12" );
            }
        }
    }
    private void playExcercise() {
        out.println( "How many half steps is this?" );
        try {
            final IAnswer ans = ansMan.select();
            ans.setGuessStrategy( new Strategy() {
                public void execute() {
                    out.println( "correct!" );
                    ans.setGuessStrategy( icStrat );
                }
            } );
            sndEng.play( ans, rangeMan.selectBase( ans ) );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }
}