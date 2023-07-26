package org.example;

import org.example.question.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.concurrent.ConcurrentHashMap;

public class Bot extends TelegramLongPollingBot {

  private final AbstractQuestion[] questions;
  private final ConcurrentHashMap<Long, UserData> users;

  public Bot() {
    questions = new AbstractQuestion[4];
    questions[0] = new JavaQuestion();
    questions[1] = new SQLQuestion();
    questions[2] = new HttpQuestion();
    questions[3] = new GitQuestion();
    users = new ConcurrentHashMap<>();
  }

  @Override
  public String getBotUsername() {
    return "YOUR_BOT_NAME";
  }

  @Override
  public String getBotToken() {
    return "YOUR_TOKEN";
  }

  public void sendText(Long who, String what) {
    SendMessage sm = SendMessage.builder()
            .chatId(who.toString()) //Who are we sending a message to
            .text(what).build();    //Message content
    try {
      execute(sm);                        //Actually sending the message
    } catch (TelegramApiException e) {
      throw new RuntimeException(e);      //Any error will be printed here
    }
  }

  @Override
  public void onUpdateReceived(Update update) {
    var message = update.getMessage();
    var text = message.getText();
    var userId = message.getFrom().getId();
    if (text.equals("/start")) {
      UserData data = new UserData();
      users.put(userId, data);
      sendText(userId, questions[0].getQuestion());
      return;
    }
    UserData data = users.get(userId);
    int currentQuestion = data.getCurrentQuestion();

    if (currentQuestion == questions.length) {
      sendText(userId, "Вы завершили тестирование! Спасибо за участие! Перезапустите бота командой /start, чтобы пройти тестирование еще раз.");
      return;
    }

    boolean result = questions[currentQuestion].checkAnswer(text);
    data.setScore(result ? data.getScore() + 1 : data.getScore());
    data.setCurrentQuestion(currentQuestion + 1);
    if (currentQuestion == questions.length - 1) {
      String rating = "Ваш рейтинг: " + data.getScore() + " из " + questions.length + " очков";
      sendText(userId, rating + " Спасибо за участие! Перезапустите бота командой /start, чтобы пройти тестирование еще раз.");
      System.out.println(message.getPhoto() + message.getFrom().getFirstName() + " " + message.getFrom().getLastName() + " " + rating);
    } else {
      sendText(userId, questions[data.getCurrentQuestion()].getQuestion());
    }
  }
}
