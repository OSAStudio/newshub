package com.osastudio.newshub.data.exam;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsBaseObject;

/**
 * Class for processing introduction of an exam, may including several
 * paragraphs with title and description
 * 
 * @author Rujin Xue
 * 
 */
public class ExamIntro extends NewsBaseObject {

   private static final String JSON_KEY_INTRO_TITLE = "description1_title";
   private static final String JSON_KEY_INTRO_CONTENT = "description1_content";
   private static final String JSON_KEY_HOW_TITLE = "description2_title";
   private static final String JSON_KEY_HOW_CONTENT = "description2_content";
   private static final String JSON_KEY_TIME_TITLE = "description3_title";
   private static final String JSON_KEY_TIME_CONTENT = "description3_content";

   private String introTitle;
   private String introContent;
   private String howTitle;
   private String howContent;
   private String timeTitle;
   private String timeContent;

   public ExamIntro() {
      super();
   }

   public ExamIntro(JSONObject jsonObject) {
      super(jsonObject);

      try {
         if (!jsonObject.isNull(JSON_KEY_INTRO_TITLE)) {
            this.introTitle = jsonObject.getString(JSON_KEY_INTRO_TITLE).trim();
         }
         if (!jsonObject.isNull(JSON_KEY_INTRO_CONTENT)) {
            this.introContent = jsonObject.getString(JSON_KEY_INTRO_CONTENT)
                  .trim();
         }
         if (!jsonObject.isNull(JSON_KEY_HOW_TITLE)) {
            this.howTitle = jsonObject.getString(JSON_KEY_HOW_TITLE).trim();
         }
         if (!jsonObject.isNull(JSON_KEY_HOW_CONTENT)) {
            this.howContent = jsonObject.getString(JSON_KEY_HOW_CONTENT).trim();
         }
         if (!jsonObject.isNull(JSON_KEY_TIME_TITLE)) {
            this.timeTitle = jsonObject.getString(JSON_KEY_TIME_TITLE).trim();
         }
         if (!jsonObject.isNull(JSON_KEY_TIME_CONTENT)) {
            this.timeContent = jsonObject.getString(JSON_KEY_TIME_CONTENT)
                  .trim();
         }
      } catch (JSONException e) {

      }
   }

   public String getIntroTitle() {
      return this.introTitle;
   }

   public void setIntroTitle(String introTitle) {
      this.introTitle = introTitle;
   }

   public String getIntroContent() {
      return this.introContent;
   }

   public void setIntroContent(String introContent) {
      this.introContent = introContent;
   }

   public String getHowTitle() {
      return this.howTitle;
   }

   public void setHowTitle(String howTitle) {
      this.howTitle = howTitle;
   }

   public String getHowContent() {
      return this.howContent;
   }

   public void setHowContent(String howContent) {
      this.howContent = howContent;
   }

   public String getTimeTitle() {
      return this.timeTitle;
   }

   public void setTimeTitle(String timeTitle) {
      this.timeTitle = timeTitle;
   }

   public String getTimeContent() {
      return timeContent;
   }

   public void setTimeContent(String timeContent) {
      this.timeContent = timeContent;
   }

}
