package de.hda.fbi.db2.stud.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hda.fbi.db2.api.Lab01Data;
import de.hda.fbi.db2.stud.entity.Answer;
import de.hda.fbi.db2.stud.entity.Category;
import de.hda.fbi.db2.stud.entity.Question;


/**
 * Implementation of {@link Lab01Data}
 * Created by Marcel Hauck and Kujtesa Beqiri
 * on 03.11.2019.
 */


public class Impl01Data extends Lab01Data {

  private Map<String, Category> categoryMap = new HashMap<>();

  @java.lang.Override
  public List<Object> getQuestions() {
    List<Object> questionList = new ArrayList<>();
    for (Category values : categoryMap.values()){
      for (Question q: values.getQuestionList()) {
        questionList.add(q);
      }
    }
    return questionList;
  }

  @java.lang.Override
  public List<Object> getCategories() {
    List<Object> list = new ArrayList<>();
    for (Map.Entry<String, Category> item : categoryMap.entrySet()) {
      list.add(item.getValue());
    }
    return list;
  }

  @java.lang.Override
  public void loadCsvFile(List<String[]> additionalCsvLines) {

    //Counter für Category
    int categoryCounter = 1;

    try {
      /*Auslesen aller Kategorien aus Strings
       falls noch nicht vorhanden, zur Hashmap neu hinzufügen */
      for (int i = 1; i < additionalCsvLines.size(); i++) {

        Category tmpC = new Category();
        tmpC.setName(additionalCsvLines.get(i)[7]);

        // falls Category nicht vorhanden, hinzufügen
        if (!categoryMap.containsKey(additionalCsvLines.get(i)[7])) {
          /* Question-Objekt
              und Zuweisung aller Attribute */
          Question tmpQ = new Question();
          tmpQ.setId(Integer.parseInt(additionalCsvLines.get(i)[0]));
          tmpQ.setTask(additionalCsvLines.get(i)[1]);
          tmpQ.setCategory(tmpC);


          /* alle 4 Antworten Question-Class hinzufügen */
          List<Answer> tmpA = new ArrayList<>();
          for (int j = 2; j < 6; j++) {
            Answer tmpAnsw = new Answer();
            tmpAnsw.setAnswerString(additionalCsvLines.get(i)[j]);
            //tmpAnsw.setQuestion(tmpQ);
            tmpA.add(tmpAnsw);
          }
          //antworten setzen und liste leeren
          tmpQ.setAnswers(tmpA);

          //korrekte Antwort
          tmpQ.setSolution(Integer.parseInt(additionalCsvLines.get(i)[6]));

          tmpC.addQuestionList(tmpQ);
          categoryMap.put(tmpC.getName(), tmpC);
        } else {
          Category c1 = categoryMap.get(tmpC.getName());
           /* Question-Objekt
              und Zuweisung aller Attribute */
          Question tmpQ = new Question();
          tmpQ.setId(Integer.parseInt(additionalCsvLines.get(i)[0]));
          tmpQ.setTask(additionalCsvLines.get(i)[1]);
          tmpQ.setCategory(c1);


          /* alle 4 Antworten Question-Class hinzufügen */
          List<Answer> tmpA = new ArrayList<>();
          for (int j = 2; j < 6; j++) {
            Answer tmpAnsw = new Answer();
            tmpAnsw.setAnswerString(additionalCsvLines.get(i)[j]);
//            tmpAnsw.setQuestion(tmpQ);
            tmpA.add(tmpAnsw);
          }
          //antworten setzen und liste leeren
          tmpQ.setAnswers(tmpA);

          //korrekte Antwort
          tmpQ.setSolution(Integer.parseInt(additionalCsvLines.get(i)[6]));

          tmpC.addQuestionList(tmpQ);

          categoryMap.get(c1.getName()).addQuestionList(tmpQ);
        }

      }


    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
  }
}
