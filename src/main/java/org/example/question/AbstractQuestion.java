package org.example.question;

public abstract class AbstractQuestion {
  private final String question;

  // конструктор вопроса для question
  public AbstractQuestion(String question) {
    this.question = question;
  }

  // геттер для question
  public String getQuestion() {
    return question;
  }
  public abstract boolean checkAnswer(String answer);
}
