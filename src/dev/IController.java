package com.peterlyons.smartears;

public interface IController {
    public ISoundEngine getSoundEngine();
    public IRangeManager getRangeManager();
    public IAnswerManager getAnswerManager();
    public IScoreKeeper getScoreKeeper();
    public IExercise getExercise();
}