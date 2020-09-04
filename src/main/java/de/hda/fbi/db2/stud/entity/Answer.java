package de.hda.fbi.db2.stud.entity;


import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Answer
 * Created by Marcel Hauck and Kujtesa Beqiri
 * on 03.11.2019.
 */
@Embeddable
public class Answer {

  private String answerString;

  public Answer() {
  }

  public String getAnswerString() {
    return answerString;
  }

  public void setAnswerString(String answerString) {
    this.answerString = answerString;
  }

  @Override
  public String toString() {
    return "Answer{" +
        "answerString='" + answerString + '\'' +
      ", question=" +
      '}';
}

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Answer answer = (Answer) o;
    return Objects.equals(answerString, answer.answerString);
  }

  @Override
  public int hashCode() {
    return Objects.hash(answerString);
  }
}
