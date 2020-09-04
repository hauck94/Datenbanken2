package de.hda.fbi.db2.stud.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity Player
 * Created by Marcel Hauck and Kujtesa Beqiri
 * on 03.11.2019.
 */

@Entity
@Table(name = "Player", schema = "praktikum")
public class Player {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;

  @OneToMany(mappedBy = "Player", cascade = CascadeType.PERSIST)
  private List<Game>games = new ArrayList<>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Game> getGames() {
    return games;
  }

  public void setGames(Game games) {
    this.games.add(games);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Player player = (Player) o;
    return name.equals(player.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return "Player{" +
        "name='" + name + '\'' +
        '}';
  }
}
