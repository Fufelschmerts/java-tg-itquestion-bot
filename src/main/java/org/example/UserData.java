package org.example;

public class UserData {

  private int score;
  private int currentQuestion;

  public int getCurrentQuestion() {
    return currentQuestion;
  }
  public int getScore() {
    return score;
  }
  public int setCurrentQuestion(int currentQuestion) {
    this.currentQuestion = currentQuestion;
    return currentQuestion;
  }
  public int setScore(int score) {
    this.score = score;
    return score;
  }
}
