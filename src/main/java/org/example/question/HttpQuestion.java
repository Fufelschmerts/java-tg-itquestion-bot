package org.example.question;

public class HttpQuestion extends AbstractQuestion {

  private final int minimalCount = 4;
  private final String[] HttpMethods = {"GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS", "TRACE", "CONNECT", "PATCH"};
  public HttpQuestion() {
    super("Перечислите, пожалуйста, все известные вам методы HTTP-запросов.");
  }

  @Override
  public boolean checkAnswer(String answer) {
    int count = 0;
    for (String method : HttpMethods) {
      if (answer.toUpperCase().contains(method)) {
        count++;
      }
    }
    return count >= minimalCount;
  }
}
