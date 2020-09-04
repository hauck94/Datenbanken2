package de.hda.fbi.db2.stud.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Entity Question
 * Created by Marcel Hauck and Kujtesa Beqiri
 * on 03.11.2019.
 */
@Entity
@Table(name = "Question", schema = "praktikum")
public class Question {
  @Id
private int id;
private String task;

private int solution;

@ElementCollection
private List<Answer> answers = new ArrayList<>();
@ManyToOne(cascade = CascadeType.PERSIST)
private Category category;
private boolean correctAnswer;
@ManyToOne(cascade = CascadeType.PERSIST)
private Game game;


  public Question() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTask() {
    return task;
  }

  public void setTask(String task) {
    this.task = task;
  }

  public List<Answer> getAnswers() {
    return answers;
  }

  public void setAnswers(List<Answer> answers) {
    this.answers = answers;
  }

  public int getSolution() {
    return solution;
  }

  public void setSolution(int solution) {
    this.solution = solution;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public boolean isCorrectAnswer() {
    return correctAnswer;
  }

  public void setCorrectAnswer(boolean correctAnswer) {
    this.correctAnswer = correctAnswer;
  }

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Question question = (Question) o;
    return id == question.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Question{" +
        "id=" + id +
        ", task='" + task + '\'' +
        '}';
  }
}
