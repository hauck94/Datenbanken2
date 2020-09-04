package de.hda.fbi.db2.stud.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity Game
 * Created by Marcel Hauck and Kujtesa Beqiri
 * on 03.11.2019.
 */
@Entity

@NamedQueries({
    @NamedQuery(name = "CountPlayedGames",
        query = "select g.player.name ,count(g) from Game g group by g.player.name "
            + "order by count(g) desc"),
    @NamedQuery(name = "PopularCategories",
        query = "select count(q.category), q.category.name from Game g join g.questions q group by"
            + " q.category.name order by count(q.category) desc"),
    @NamedQuery(name = "Game.getCount",
        query = "select count(g.id) from Game g"),
    @NamedQuery(name = "Game.playerGameHistory",
        query = "select g from Game g where g.player = :name"),

    @NamedQuery(name = "JPQL1",
        query = "select g.player  from Game g where g.gameStart > :gameStart and "
            + "g.gameEnd < :gameEnd group by g.player"),
    @NamedQuery(name = "JPQL2",
        query = "select g from Game g where g.player.name = :name")
})


@Table(name = "Game", schema = "praktikum")
public class Game {
  @Id @GeneratedValue
private int id;
  private Timestamp gameStart;
  private  Timestamp gameEnd;
  @ManyToOne(cascade = CascadeType.PERSIST)
  private Player player;
  @OneToMany(mappedBy = "game")
  private List<Question> questions = new ArrayList<>();
  private int numberOfQuestions;


  public Game() {
    long offset = Timestamp.valueOf("2020-01-01 00:00:00").getTime();
    long end = Timestamp.valueOf("2020-01-15 00:00:00").getTime();
    long diff = end - offset + 1;
    Timestamp rand = new Timestamp(offset + (long) (Math.random() * diff));

    Long milliseconds = Long.valueOf(120000);

    gameStart = rand;
    gameEnd = new Timestamp(gameStart.getTime() + milliseconds);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }


  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public List<Question> getQuestions() {
    return questions;
  }

  public void setQuestions(List<Question> questions) {
    this.questions = questions;
  }

  public int getNumberOfQuestions() {
    return numberOfQuestions;
  }

  public void setNumberOfQuestions(int numberOfQuestions) {
    this.numberOfQuestions = numberOfQuestions;
  }

  public void addQuestions(Question q) {
    this.questions.add(q);
  }

  public void addQuestionsList(List<Object> questions) {
    for (Object q : questions
    ) {
      this.questions.add(((Question) q));
    }
  }

  public Timestamp getGameStart() {
    return new Timestamp(this.gameStart.getTime());
  }

  public void setGameStart(Timestamp gameStart) {
    this.gameStart = gameStart != null ? new Timestamp(gameStart.getTime()) : null;;
  }

  public Timestamp getGameEnd() {
    return new Timestamp(this.gameEnd.getTime());
  }

  public void setGameEnd(Timestamp gameEnd) {
    this.gameEnd = gameEnd != null ? new Timestamp(gameEnd.getTime()) : null;
  }

  public void output() {
    System.out.println(id + "\t" + player.getName() + "\t"
        + getGameStart() + "\t  " + getGameEnd() + "\t");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Game game = (Game) o;
    return id == game.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Game{" +
        "id=" + id +
        '}';
  }
}
