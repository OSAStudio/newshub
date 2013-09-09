package com.osastudio.newshub.data.exam;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsBaseTitle;

public class Exam extends NewsBaseTitle {

   public static final String JSON_KEY_ID = "evaluation_id";
   public static final String JSON_KEY_TITLE = "evaluation_title";
   public static final String JSON_KEY_QUESTIONS_LIST = "quess_list";

   protected QuestionList questionsList;

   public Exam() {
      super();
   }

   public Exam(JSONObject jsonObject) {
      super(jsonObject);

      if (isSuccess()) {
         try {
            JSONObject examObject = null;
            if (!jsonObject.isNull(JSON_KEY_LIST)) {
               examObject = jsonObject.getJSONObject(JSON_KEY_LIST);
            }
            if (examObject == null) {
               return;
            }

            if (!examObject.isNull(JSON_KEY_ID)) {
               this.id = examObject.getString(JSON_KEY_ID).trim();
            }
            if (!examObject.isNull(JSON_KEY_TITLE)) {
               this.title = examObject.getString(JSON_KEY_TITLE).trim();
            }
            if (!examObject.isNull(JSON_KEY_QUESTIONS_LIST)) {
               this.questionsList = new QuestionList(examObject);
            }
         } catch (JSONException e) {

         }
      }
   }

   public QuestionList getQuestions() {
      return this.questionsList;
   }

   public void setQuestions(QuestionList questions) {
      this.questionsList = questions;
   }

}
