package de.hda.fbi.db2.controller;

import de.hda.fbi.db2.api.Lab05Analyse;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import de.hda.fbi.db2.api.Lab01Data;
import de.hda.fbi.db2.api.Lab02EntityManager;
import de.hda.fbi.db2.api.Lab03Game;
import de.hda.fbi.db2.api.Lab04MassData;

/**
 * Controller Created by l.koehler on 05.08.2019.
 */
public class Controller {

  private static Controller controller;

  public static synchronized Controller getInstance() {
    if (controller == null) {
      controller = new Controller();
    }
    return controller;
  }

  private boolean isCsvRead = false;

  private boolean isPersisted = false;

  private static final String IMPL_PACKAGE_NAME = "de.hda.fbi.db2.stud";

  private Lab01Data lab01Data;

  private Lab02EntityManager lab02EntityManager;

  private Lab03Game lab03Game;

  private Lab04MassData lab04MassData;

  private Lab05Analyse lab05Analyse;

  private MenuController menuController;

  private Controller() {
    findImplementations();
  }

  private void findImplementations() {
    try {
      Enumeration<URL> elements = Thread.currentThread().getContextClassLoader()
          .getResources(IMPL_PACKAGE_NAME.replace(".", "/"));
      if (!elements.hasMoreElements()) {
        return;
      }
      String test = elements.nextElement().getFile();
      List<Class> classes = new ArrayList<>(findClasses(new File(test), IMPL_PACKAGE_NAME));
      for (Class clazz : classes) {
        Class interfaces = clazz.getSuperclass();
        if (interfaces != null) {
          if (interfaces.getSimpleName().equals(Lab01Data.class.getSimpleName())) {
            lab01Data = (Lab01Data) clazz.newInstance();
          } else if (interfaces.getSimpleName()
              .equals(Lab02EntityManager.class.getSimpleName())) {
            lab02EntityManager = (Lab02EntityManager) clazz.newInstance();
          } else if (interfaces.getSimpleName()
              .equals(Lab03Game.class.getSimpleName())) {
            lab03Game = (Lab03Game) clazz.newInstance();
          } else if (interfaces.getSimpleName()
              .equals(Lab04MassData.class.getSimpleName())) {
            lab04MassData = (Lab04MassData) clazz.newInstance();
          }
        }
      }

      if (lab01Data == null) {
        return;
      }
      lab01Data.init();

      if (lab02EntityManager == null) {
        return;
      }
      lab02EntityManager.setLab01Data(lab01Data);
      lab02EntityManager.init();

      if (lab03Game == null) {
        return;
      }
      lab03Game.setLab01Data(lab01Data);
      lab03Game.setLab02EntityManager(lab02EntityManager);
      lab03Game.init();

      if (lab04MassData == null) {
        return;
      }
      lab04MassData.setLab01Data(lab01Data);
      lab04MassData.setLab02EntityManager(lab02EntityManager);
      lab04MassData.setLab03Game(lab03Game);
      lab04MassData.init();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private List<Class> findClasses(File directory, String packageName)
      throws ClassNotFoundException {
    List<Class> classes = new ArrayList<>();
    if (!directory.exists()) {
      return classes;
    }
    File[] files = directory.listFiles();
    assert files != null;
    for (File file : files) {
      if (file.isDirectory()) {
        assert !file.getName().contains(".");
        classes.addAll(findClasses(file, packageName + "." + file.getName()));
      } else if (file.getName().endsWith(".class")) {
        classes.add(Class.forName(packageName + '.' + file.getName()
            .substring(0, file.getName().length() - 6)));
      }
    }
    return classes;
  }

  public void readCsv() {
    if (lab01Data == null) {
      System.err.println("Could not find Lab01Data Implementation");
      return;
    }
    try {
      List<String> availableFiles = CsvDataReader.getAvailableFiles();
      for (String availableFile : availableFiles) {
        final List<String[]> additionalCsvLines = CsvDataReader.read(availableFile);
        lab01Data.loadCsvFile(additionalCsvLines);
      }
    } catch (URISyntaxException | IOException e) {
      e.printStackTrace();
    }
    isCsvRead = true;
  }

  public Lab01Data getLab01Data() {
    return lab01Data;
  }

  public Lab02EntityManager getLab02EntityManager() {
    return lab02EntityManager;
  }

  public Lab03Game getLab03Game() {
    return lab03Game;
  }

  public Lab04MassData getLab04MassData() {
    return lab04MassData;
  }

  public Lab05Analyse getLab05Analyse() { return lab05Analyse; }

  public void startMenu() {
    if (lab01Data == null) {
      System.err.println("Could not find Lab01Data Implementation");
      return;
    }

    if (lab02EntityManager == null) {
      System.err.println("Could not find Lab02EntityManager Implementation");
      return;
    }

    if (lab03Game == null) {
      System.err.println("Could not find Lab03Game Implementation");
      return;
    }

    if (menuController == null) {
      menuController = new MenuController(this);
    }
    menuController.showMenu();
  }

  public void persistData() {
    if (lab01Data == null) {
      System.err.println("Could not find Lab01Data Implementation");
      return;
    }

    if (lab02EntityManager == null) {
      System.err.println("Could not find Lab02EntityManager Implementation");
      return;
    }

    lab02EntityManager.persistData();
    isPersisted = true;
  }

  public boolean isCsvRead() {
    return isCsvRead;
  }

  public boolean isPersisted() {
    return isPersisted;
  }

  public void createMassData() {
    if (lab01Data == null) {
      System.err.println("Could not find Lab01Data Implementation");
      return;
    }

    if (lab02EntityManager == null) {
      System.err.println("Could not find Lab02EntityManager Implementation");
      return;
    }

    if (lab03Game == null) {
      System.err.println("Could not find Lab03Game Implementation");
      return;
    }

    if (lab04MassData == null) {
      System.err.println("Could not find Lab04MassData Implementation");
      return;
    }

    lab04MassData.createMassData();
  }
}
