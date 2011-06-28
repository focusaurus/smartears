package com.peterlyons.smartears;

public class AnswerAction extends javax.swing.AbstractAction
{
  private IScoreManager scoreManager;

  private Strategy incorrectStrat = new Strategy()
  {
    public void execute()
    {
      if(SmartEars.DEBUGGING_ENABLED) {
        System.out.println("BasicAnswer executing the incorrect strategy");
      }
      scoreManager.incorrect(BasicAnswer.this);
    }
  };

  private Strategy correctStrat = new Strategy()
  {
    public void execute()
    {
      scoreManager.correct(BasicAnswer.this);
      setGuessStrategy(getDefaultIncorrectStrategy());
    }
  };

  /**
   *The strategy that we use when we are guessed
   */
  private Strategy guessStrat = incorrectStrat;

  public AnswerAction(String text, Icon icon, IScoreManager sm)
  {
    super(text, icon);
    scoreManager = sm;
  }

  public void actionPerformed(ActionEvent e)
  {

  }
}
