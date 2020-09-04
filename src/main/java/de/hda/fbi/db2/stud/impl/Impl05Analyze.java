package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab05Analyse;
import de.hda.fbi.db2.stud.entity.Game;
import de.hda.fbi.db2.stud.entity.Player;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 */

public class Impl05Analyze extends Lab05Analyse {

  // Die EntityManagerFactory erstellen
  private static EntityManagerFactory emf =
      Persistence.createEntityManagerFactory("postgresPU");

  // Neuen EntityManager erstellen
  private static EntityManager em = emf.createEntityManager();;


  private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

  //3) JPQL-Querie: Ausgabe aller Spieler mit Anzahl der gespielten Spiele,
  // nach Anzahl absteigend geordnet.
  public static void countPlayedGames() {
    System.out.println("------------------------------------------------------------------------");
    System.out.println("=== 3) JPQL-Querie: Ausgabe aller Spieler mit Anzahl"
        + " der gespielten Spiele, nach Anzahl absteigend geordnet ===");
    List playerGameCount = em.createNamedQuery("CountPlayedGames").getResultList();
    for (Iterator i = playerGameCount.iterator(); i.hasNext();) {
      Object[] playerGame = (Object[]) i.next();
      System.out.print("Spieler " + playerGame[0] + " - " + playerGame[1] + " gespielte Spiele \n");
    }//END-FOR
  }


  //4) JPQL-Querie: Ausgabe der am meisten gefragten Kategorie.
  // (Oder wahlweise Beliebtheit der Kategorien: nach Anzahl der Auswahl absteigend sortiert.)
  public static void popularCategories() {
    System.out.println("-------------------------------------------------------------------------");
    System.out.println("=== 4) JPQL-Querie: Ausgabe der am meisten gefragten Kategorie."
        + " (Oder wahlweise Beliebtheit der Kategorien: nach Anzahl der"
        + " Auswahl absteigend sortiert.) ===");
    List popularCategories = em.createNamedQuery("PopularCategories").getResultList();
    for (Iterator i = popularCategories.iterator(); i.hasNext();) {
      Object[] catCount = (Object[]) i.next();
      Object cName = (Object) catCount[1];
      System.out.print("Kategorie: " + cName + " - Häufigkeit: " + catCount[0] + "\n");
    }//END-FOR
  }


  static void bestimmterSpieler() {
    System.out.println("------------------------------------------------------------------------");
    System.out.println("=== 2) xxxxxxxxxxxxxxxxxxxxxxxxxxxx");
    List popularCategories = em.createNamedQuery("JPQL2").setParameter("name",
        "5496f73a-3d7b-4493-b7e5-a0dbe5e75ce4").getResultList();
    for (Iterator i = popularCategories.iterator(); i.hasNext();) {
      Game g = (Game) i.next();
      g.output();
    }//END-FOR
  }

  static void spielerBestimmtenZeitraums() {
    System.out.println("------------------------------------------------------------------------");
    System.out.println("=== 1) JPQL-Querie: xxxxxxxxxxxxxxxxxxxx");
    java.sql.Timestamp startT = java.sql.Timestamp.valueOf("2020-01-05 00:00:01");
    java.sql.Timestamp endT = java.sql.Timestamp.valueOf("2020-01-10 00:00:01");

    List popularCategories = em.createNamedQuery("JPQL1").
        setParameter("gameStart", startT).
        setParameter("gameEnd", endT).
        getResultList();

    for (Iterator i = popularCategories.iterator(); i.hasNext();) {
      Player p = (Player) i.next();
      System.out.println(p.getName());
    }//END-FOR
  }

  public void run() {
    Date startDate;
    Date endeDate;
    String playerName;


    System.out.println("\n\nBitte wählen Sie:");
    System.out.println("\t1. Ausgabe aller Spieler für einen Zeitraum");
    System.out.println("\t2. Informationen zu einem bestimmten Spieler");
    System.out.println("\t3. Ausgabe aller Spieler nach gespielten Spielen");
    System.out.println("\t4. Ausgabe der am meisten gefragten Kategorien");
    System.out.println("\t5. Zurück zum Hauptmenü");


    final Scanner scanner = new Scanner(System.in, "utf-8");
    final int choice = scanner.nextInt();

    switch (choice) {
      case 1:
        spielerBestimmtenZeitraums();
        break;
      case 2:
        bestimmterSpieler();
        break;
      case 3:
        countPlayedGames();
        break;
      case 4:
        popularCategories();
        break;
      case 5:
        return;
      default:
        System.out.println("Fehlerhafte Eingabe! Bitte wählen Sie"
            + " erneut einen existierenden Menü-Punkt aus!");
        break;
    }

  }

  @Override
  public void analyze() {
    run();
  }
}
