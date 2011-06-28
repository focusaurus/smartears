package com.peterlyons.smartears;

/**
 *This class represets the users score and statistics
 */
public class Score implements IScore
{
  private int score = 0;
  private int correctCount = 0;
  private int incorrectCount = 0;
  private int repeatCount = 0;

  public Score(int correctCount, 
	       int incorrectCount, 
	       int repeatCount, 
	       int score)
  {
    this.correctCount = correctCount;
    this.incorrectCount = incorrectCount;
    this.repeatCount = repeatCount;
    this.score = score;
  }

  public int getCorrectCount()
  {
    return correctCount;
  }
  
  public int getIncorrectCount()
  {
    return incorrectCount;
  }
  
  public int getScore()
  {
    return score;
  }

  public int getRepeatCount()
  {
    return repeatCount;
  }
}





