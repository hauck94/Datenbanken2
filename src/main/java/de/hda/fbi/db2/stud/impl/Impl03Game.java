package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.stud.entity.Player;
import de.hda.fbi.db2.stud.entity.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hda.fbi.db2.api.Lab03Game;
import de.hda.fbi.db2.stud.entity.Category;
import de.hda.fbi.db2.stud.entity.Game;
import java.util.Vector;


/**
 * Implementation of {@link Lab03Game}
 * Created by Marcel Hauck and Kujtesa Beqiri
 * on 14.11.2019.
 */


public class Impl03Game extends Lab03Game {



  public static final String ANSI_RESET = "\u001B[0m";
  // public static final String ANSI_BLACK = "\u001B[30m";
   public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  // public static final String ANSI_YELLOW = "\u001B[33m";
  // public static final String ANSI_BLUE = "\u001B[34m";
   public static final String ANSI_PURPLE = "\u001B[35m";
  // public static final String ANSI_CYAN = "\u001B[36m";
  // public static final String ANSI_WHITE = "\u001B[37m";



  private  Player  checkPlayerExist(String name) {
    Vector<Player> playerList =
        (Vector<Player>) lab02EntityManager.getEntityManager().
            createQuery("select p from Player p where p.name = :name")
            .setParameter("name", name).getResultList();
    if (!playerList.isEmpty()){
      return playerList.get(0);
    }
      return null;
  }

  /**
   * This method should create a game. For this you have to do the following things: - Ask for
   * player - Ask for categories - Ask for maximum questions per category and choose questions
   * <p>
   * You do not have to play the game here. This should only create a game
   *
   * @return the game entity object. IMPORTANT: This has to be a entity.
   */

  @Override
  public Object createGame() {

    try {
      lab02EntityManager.getEntityManager().getTransaction().begin();
      Game tmpG = new Game();
      System.out.println("Enter your Name: ");
      BufferedReader reader =
          new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
      String playernName = reader.readLine();
      System.out.println("Welcome " + playernName);
      Player player;
      if (checkPlayerExist(playernName) != null){
         player = checkPlayerExist(playernName);
      } else {
         player = new Player();
      }


      player.setName(playernName);
      tmpG.setPlayer(player);
      int index = 1;
      for (Object c : (lab01Data.getCategories())
      ) {
        System.out.println(ANSI_GREEN + index++ + ANSI_RESET +  " x " + ((Category) c).getName());
      }

      System.out.println("Choose Categories by Number (at least 2, break with 'q'): ");

      String tmpCategoryNumber = "";
      while (true){

        tmpCategoryNumber = reader.readLine();

        if (tmpG.getQuestions().size() >= 2 && tmpCategoryNumber == "q"){
          break;
        }

        if (tmpCategoryNumber != null) {
          if (tmpCategoryNumber.equals("q") && tmpG.getQuestions().size() >= 2){
            break;
          }
          if (tmpCategoryNumber.equals("q") && tmpG.getQuestions().size() < 2){
            System.out.println("you have to pick more Categories");
          } else {
            int catNr = Integer.parseInt(tmpCategoryNumber);

        if (catNr > 0 && catNr < 52) {
          Category tmpC = (Category) lab01Data.getCategories().get(catNr - 1);
          tmpG.addQuestions(tmpC.getQuestionList().get(0));
          System.out.println(ANSI_PURPLE + "Info: " + ANSI_RESET + "added \t \t[" +
              ANSI_GREEN + tmpC.getName() +
              ANSI_RESET + "]\t\t to your game");
          System.out.println(ANSI_PURPLE + "Choose another Category" + ANSI_RESET);
        } else {
          System.out.println(ANSI_RED + "You have to choose a Valid Number" + ANSI_RESET);
        }
          }
        } else {
          System.out.println(ANSI_RED + "You have to choose a Valid Number" + ANSI_RESET);
        }
      }
      System.out.println("Determine the maximum number of questions: ");
      String numberOfQuestionString = reader.readLine();
if (numberOfQuestionString != null) {
  int numberOfQuestions = Integer.parseInt(numberOfQuestionString);

  tmpG.setNumberOfQuestions(numberOfQuestions);
} else {
  System.out.println(ANSI_RED + "You have to choose a Valid Number" + ANSI_RESET);
}
return tmpG;
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Here you have to play the game 'game'.
   *
   * @param game the game that should be played
   */
  @Override
  public void playGame(Object game) {
    Random randomQuestion = new Random();

    BufferedReader reader =
        new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
    System.out.println(ANSI_RED + "The Game starts now" + ANSI_RESET);

    List<Category> listCategory = new ArrayList<>();
    for (Question q : ((Game) game).getQuestions()
    ) {
      listCategory.add(q.getCategory());
    }
      // if there are less questions then the Nmbr of Questions
      // print all questions
    for (Category c : listCategory
    ) {

      if (c.getQuestionList().size() < ((Game) game).getNumberOfQuestions()) {
        for (Question q : c.getQuestionList()
        ) {
          q.setGame((Game) game);
          System.out.println(ANSI_GREEN + q.getTask() + ANSI_RESET);
          System.out.println(ANSI_PURPLE + "1 :" + q.getAnswers().get(0)
              .getAnswerString() + ANSI_RESET);
          System.out.println(ANSI_PURPLE + "2 :" + q.getAnswers().get(1)
              .getAnswerString() + ANSI_RESET);
          System.out.println(ANSI_PURPLE + "3 :" + q.getAnswers().get(2)
              .getAnswerString() + ANSI_RESET);
          System.out.println(ANSI_PURPLE + "4 :" + q.getAnswers().get(3).
              getAnswerString() + ANSI_RESET);

          System.out.println(ANSI_GREEN + "type in the correct number" + ANSI_RESET);

          try {
            String correctNmbr = reader.readLine();
            if (correctNmbr != null) {
              if (q.getSolution() == Integer.parseInt(correctNmbr)) {
                q.setCorrectAnswer(true);
                System.out.println(ANSI_GREEN + "CORRECT" + ANSI_RESET);
              } else {
                q.setCorrectAnswer(false);
                System.out.println(ANSI_RED + "FALSE" + ANSI_RESET);
              }
            }
            ((Game) game).addQuestions(q);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      } else {
        for (int j = 0; j < ((Game) game).getNumberOfQuestions(); j++) {
          int iQuestion = randomQuestion.nextInt(c.getQuestionList().size());

          System.out.println(ANSI_GREEN + c.getQuestionList().
              get(iQuestion).getTask() + ANSI_RESET);
          System.out.println(ANSI_PURPLE + "1 :" + c.getQuestionList().get(iQuestion).
              getAnswers().get(0).getAnswerString() + ANSI_RESET);
          System.out.println(ANSI_PURPLE + "2 :" + c.getQuestionList().get(iQuestion).
              getAnswers().get(1).getAnswerString() + ANSI_RESET);
          System.out.println(ANSI_PURPLE + "3 :" + c.getQuestionList().get(iQuestion).
              getAnswers().get(2).getAnswerString() + ANSI_RESET);
          System.out.println(ANSI_PURPLE + "4 :" + c.getQuestionList().get(iQuestion).
              getAnswers().get(3).getAnswerString() + ANSI_RESET);

          System.out.println(ANSI_GREEN + "type in the correct number" + ANSI_RESET);

          try {
            String correctNmbr = reader.readLine();
            if (correctNmbr != null) {
              if (c.getQuestionList().get(iQuestion).getSolution() == Integer
                  .parseInt(correctNmbr)) {
                c.getQuestionList().get(iQuestion).setCorrectAnswer(true);
                System.out.println(ANSI_GREEN + "CORRECT" + ANSI_RESET);
              } else {
                c.getQuestionList().get(iQuestion).setCorrectAnswer(false);
                System.out.println(ANSI_RED + "FALSE" + ANSI_RESET);
              }
              ((Game) game).addQuestions(c.getQuestionList().get(iQuestion));
              c.getQuestionList().get(iQuestion).setGame(((Game) game));
            }
          } catch (IOException e) {
            e.printStackTrace();
          }

        }
      }
    }
      persistGame(game);
      lab02EntityManager.getEntityManager().getTransaction().commit();
    }

  /**
   * This should create a game with the given arguments. You do not have to read data from console.
   * The game should be create only with the given arguments. You do not have to play the game
   * here.
   *
   * @param playerName name of the player
   * @param questions  chosen questions
   * @return a game object
   */
  @Override
  public Object createGame(String playerName, List<Object> questions) {
    Game game = new Game();
    Player player;
    if (checkPlayerExist(playerName) != null){
      player = checkPlayerExist(playerName);
    } else {
      player = new Player();
      player.setName(playerName);
    }
    game.setPlayer(player);
    game.addQuestionsList(questions);
    player.setGames(game);
    return game;
  }

  /**
   * Simulate a game play. You do not have to read anything from console.
   *
   * @param game given game
   */
  @Override
  public void simulateGame(Object game) {

    Random randomQuestion = new Random();
    Game g = ((Game) game);

    ArrayList<Question> gameQuestions = new ArrayList<Question>();
    for (int i = 0; i < 15; i++) {
      int iQuestion = randomQuestion.nextInt(lab01Data.getQuestions().size() - 1);

      Question q = (Question) lab01Data.getQuestions().get(iQuestion);
      q.setGame(g);
      gameQuestions.add(q);
    }
    g.setNumberOfQuestions(gameQuestions.size());
    for (Question q : gameQuestions
    ) {
      int iAnswer = randomQuestion.nextInt(4) + 1;
      if (q.getSolution() == iAnswer) {
        q.setCorrectAnswer(true);
      } else {
        q.setCorrectAnswer(false);
      }
      g.setQuestions(gameQuestions);
    }


  }

  /**
   * This should return the appropriate player for the given game.
   *
   * @param game given game for returning player
   * @return player for given game
   */
  @Override
  public Object getPlayer(Object game) {
    if (((Game) game).getPlayer() != null){
      return ((Game) game).getPlayer();
    }
    return null;
  }

  /**
   * Return the player entity with given name.
   *
   * @param name name of the player
   * @return the player entity
   */
  @Override
  public Object getPlayer(String name) {
    return null;
  }

  /**
   * return the right answers of a played game.
   *
   * @param game given game
   * @return number of right answers
   */
  @Override
  public int getRightAnswers(Object game) {
    int nmbrCorrectA = 0;
    for (Question q : ((Game) game).getQuestions()
    ) {
      if (q.isCorrectAnswer()){
        nmbrCorrectA++;
      }
    }
    return nmbrCorrectA;
  }

  /**
   * persist the game object in the database.
   *
   * @param game given game
   */
  @Override
  public void persistGame(Object game) {
lab02EntityManager.getEntityManager().persist(game);
  }
}
