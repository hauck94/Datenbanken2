package de.hda.fbi.db2.stud.entity;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity Category
 * Created by Marcel Hauck and Kujtesa Beqiri
 * on 03.11.2019.
 */
@Entity
@Table(name = "Category", schema = "praktikum")
public class Category {
  @Id @GeneratedValue
  private int id;
 @Column(unique = true)
  private String name;
  @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST)
  private List<Question> questionList = new ArrayList<>();

  public Category() {
  }

  public Category(String name, List<Question> questionList) {
    this.name = name;
    this.questionList = questionList;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Question> getQuestionList() {
    return questionList;
  }

  public void setQuestionList(List<Question> questionList) {
    this.questionList = questionList;
  }

  public void addQuestionList(Question q) {
    this.questionList.add(q);
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Category category = (Category) o;
    return id == category.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Category{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
