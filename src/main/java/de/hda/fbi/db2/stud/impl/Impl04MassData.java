package de.hda.fbi.db2.stud.impl;

import java.util.UUID;
import de.hda.fbi.db2.api.Lab03Game;
import de.hda.fbi.db2.api.Lab04MassData;
import de.hda.fbi.db2.stud.entity.Game;
import de.hda.fbi.db2.stud.entity.Player;


/**
 * Implementation of {@link Lab04MassData} Created by Marcel Hauck and Kujtesa Beqiri on
 * 15.12.2019.
 */
public class Impl04MassData extends Lab04MassData {

  @Override
  public void createMassData() {

    lab02EntityManager.getEntityManager().getTransaction().begin();
    int c = 0;
    for (int i = 0; i < 10; i++) {
      String uuid = UUID.randomUUID().toString();
      for (int j = 0; j < 10; j++) {
        c++;
        float result = (float) c / 1000000;
        Game g = (Game) lab03Game.createGame(uuid, lab01Data.getQuestions());
        lab03Game.simulateGame(g);
        lab02EntityManager.getEntityManager().persist(g);
        System.out.println("Entry: " + c + " || Progress: [" + result * 100 + "]%");

        if ((c > 0) && c % 100 == 0) {
          // Flush in batches of 1000 to keep caches from bogging.


          lab02EntityManager.getEntityManager().flush();
          lab02EntityManager.getEntityManager().clear();
          lab02EntityManager.getEntityManager().getTransaction().commit();

          lab02EntityManager.getEntityManager().getTransaction().begin();
        }
      }


    }
if (lab02EntityManager.getEntityManager().isOpen()){
  lab02EntityManager.getEntityManager().close();
}
  }
}
