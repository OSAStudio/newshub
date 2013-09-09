package com.osastudio.newshub.data.exam;

import org.json.JSONException;
import org.json.JSONObject;

public class ExamReport {

   public static final String JSON_KEY_SCORE = "evaluation_id";
   public static final String JSON_KEY_TIME = "evaluation_title";
   public static final String JSON_KEY_CONCLUSION = "evaluation_type";

   protected int score;
   protected String time;
   protected String conclusion;

   public ExamReport() {
      
   }
   
   public ExamReport(JSONObject jsonObject) {
      try {
         if (!jsonObject.isNull(JSON_KEY_SCORE)) {
            this.score = jsonObject.getInt(JSON_KEY_SCORE);
         }
         if (!jsonObject.isNull(JSON_KEY_TIME)) {
            this.time = jsonObject.getString(JSON_KEY_TIME).trim();
         }
         if (!jsonObject.isNull(JSON_KEY_CONCLUSION)) {
            this.conclusion = jsonObject.getString(JSON_KEY_CONCLUSION).trim();
         }
      } catch (JSONException e) {

      }
   }

   public int getScore() {
      return this.score;
   }

   public void setScore(int score) {
      this.score = score;
   }

   public String getTime() {
      return this.time;
   }

   public void setTime(String time) {
      this.time = time;
   }

   public String getConclusion() {
      return this.conclusion;
   }

   public void setConclusion(String conclusion) {
      this.conclusion = conclusion;
   }

}
