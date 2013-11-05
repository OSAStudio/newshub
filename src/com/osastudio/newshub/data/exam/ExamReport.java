package com.osastudio.newshub.data.exam;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsBaseObject;

/**
 * Class for processing exam report content, including exam score, date, report
 * details etc.
 * 
 * @author Rujin Xue
 * 
 */
public class ExamReport extends NewsBaseObject {

   public static final String JSON_KEY_ID = "result_id";
   public static final String JSON_KEY_SCORE = "result_score";
   public static final String JSON_KEY_TIME = "result_date";
   public static final String JSON_KEY_CONCLUSION = "result_content";

   protected String id;
   protected int score;
   protected String time;
   protected String conclusion;

   public ExamReport() {
      super();
   }

   public ExamReport(JSONObject jsonObject) {
      super(jsonObject);

      try {
         if (!jsonObject.isNull(JSON_KEY_ID)) {
            this.id = jsonObject.getString(JSON_KEY_ID);
         }
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

   public String getId() {
      return this.id;
   }

   public void setId(String id) {
      this.id = id;
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
