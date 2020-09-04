package de.hda.fbi.db2.stud.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import de.hda.fbi.db2.api.Lab02EntityManager;

/**
 * Implementation of {@link Lab02EntityManager}
 * Created by Marcel Hauck and Kujtesa Beqiri
 * on 14.11.2019.
 */

public class Impl02EntityManager extends Lab02EntityManager {

  // Die EntityManagerFactory erstellen
  private static EntityManagerFactory emf =
      Persistence.createEntityManagerFactory("postgresPU");

  // Neuen EntityManager erstellen
  private static EntityManager em = emf.createEntityManager();;

  @Override
  public void persistData() {
    try {
      Query cc = em.createQuery("select c from Category c");
      List categories = cc.getResultList();
      int countC = categories.size();

      Query cq = em.createQuery("select c from Question c");
      List questions = cq.getResultList();
      int countQ = questions.size();
      if (countC == 51 && countQ == 200) { // nur persistieren wenn nicht alle fragen
        //oder antwroten in der Datenbank vorhanden sind
        // DO NOTHING
      } else {
        for (Object c : lab01Data.getCategories()
        ) {
          em.getTransaction().begin();
          em.persist(c);
          em.getTransaction().commit();
        }
      }

      } catch (Exception e){
        e.printStackTrace();
      }

  }
  @Override
  public javax.persistence.EntityManager getEntityManager() {
    return em;
  }
}
