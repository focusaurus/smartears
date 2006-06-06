package net.codemusic.smartears;

import java.util.*;

import net.codemusic.Strategy;

import net.codemusic.smartears.event.*;

/**
 *Handles the storage and management of a set of possible answers that
 *a user is working with at any given time.
 */
public class AnswerManager implements IAnswerManager {

    /**
   *Stores our active answers
   */
    private Vector answers = new Vector();

    /**
   *Decides how to pick the next answer.  This one just does it randomly.
   */
    private IAnswerSelectionStrategy selectionStrat = new RandomStrategy();
    //BUGBUG decouple this from UI knowledge
    private Strategy alreadyCorrectStrategy = new Strategy() {
        public void execute() {
            net.codemusic.smartears.ui.MainFrame.setStatus( 
                    "Press Play to continue" );
        }
    };

    /**
   *Constructs an AnswerManager that will tie 
   *into the supplied IExampleEventManager's event system
   */
    public AnswerManager( IExampleEventManager eem, IScoreEventManager sem ) {
        eem.addExampleListener( new ExampleAdapter() {
            public void answerEnabled( ExampleEvent e ) {
                add( e.getAnswer() );
            }
            public void answerDisabled( ExampleEvent e ) {
                remove( e.getAnswer() );
            }
            public void examplePlayed( ExampleEvent e ) {
                for ( int i = 0; i < answers.size(); i++ ) {
                    IAnswer answer = (IAnswer)answers.elementAt( i );
                    answer.setGuessStrategy( answer.getDefaultIncorrectStrategy() );
                }
                e.getAnswer().setGuessStrategy( e.getAnswer().getDefaultCorrectStrategy() );
            }
        } );
        sem.addScoreListener( new ScoreAdapter() {
            public void correct( ScoreEvent e ) {
                for ( int i = 0; i < answers.size(); i++ ) {
                    ( (IAnswer)answers.elementAt( i ) ).setGuessStrategy( 
                            alreadyCorrectStrategy );
                }
            }
        } );
    }

    /**
   *@see IAnswerManager#add(IAnswer)
   */
    public void add( IAnswer newAnswer ) {
        if ( ! answers.contains( newAnswer ) ) {
            answers.add( newAnswer );
        }
    }

    /**
   *@see IAnswerManager#remove(IAnswer)
   */
    public void remove( IAnswer answer ) {
        if ( answers.contains( answer ) ) {
            answers.remove( answer );
        }
    }

    /**
   *@see IAnswerManager#removeAll()
   */
    public void removeAll() {
        answers.removeAllElements();
    }

    /**
   *@see IAnswerManager#select()
   */
    public IAnswer select() throws NoPossibleAnswersException {
        if ( answers.isEmpty() ) {
            throw new NoPossibleAnswersException();
        }
        IAnswer correctAnswer = selectionStrat.select( answers );
        correctAnswer.setGuessStrategy( correctAnswer.getDefaultCorrectStrategy() );
        return correctAnswer;
    }

    /**
   *@see IAnswerManager#getAnswers()
   */
    public IAnswer[] getAnswers() {
        //IAnswer[] answerArray = (IAnswer[])answers.toArray();
        //    return answerArray;
        //BUGBUG figure out why this is causing a ClassCastException
        //when I try touse answers.toArry()
        IAnswer[] answerArray = new IAnswer[answers.size()];
        for ( int i = 0; i < answers.size(); i++ ) {
            answerArray[i] = (IAnswer)answers.elementAt( i );
        }
        //Object[] o = answers.toArray();
        //System.out.println("AnswerManager array is "+o+" size:"+o.length+"class: "+o[0].getClass().getName());
        //IAnswer[] funAnswers = (IAnswer[])o;
        return answerArray;
    }

    /**
   *This AnswerManager uses a RandomPolicy by default.
   *@see IAnswerManager#setSelectionStrategy(IAnswerSelectionStrategy)
   */
    public void setSelectionStrategy( IAnswerSelectionStrategy iass ) {
        selectionStrat = iass;
    }

    /*
     *For Testing and Development only
     public static void main(String[] args)
     {
     AnswerManager am = new AnswerManager();
     for(int i=0;i<10;i++)
     {
     int[][] structure = {{0},{1}};
     IAnswer answer = new BasicAnswer(structure);
     answer.setName(new Integer(i).toString());
     am.add(answer);
     }
     for(int i=0;i<20;i++)
     {
     try
     {
     System.out.println(am.select().getName()+" was chosen");
     }
     catch(NoPossibleAnswersException e){}
     }
     }
    */
}