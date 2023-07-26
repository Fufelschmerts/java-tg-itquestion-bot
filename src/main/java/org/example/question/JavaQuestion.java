package org.example.question;

public class JavaQuestion extends AbstractQuestion {
  public JavaQuestion() {
    super("Сколько в языке программирования Java примитивных типов данных?");
  }
  @Override
  public boolean checkAnswer(String answer) {
    return answer.equals("8");
  }
}
