package com.osastudio.newshub.data.exam;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsBaseTitle;

public class ExamInfo extends NewsBaseTitle {
   
   public static final int INTRODUCTION = 1; 
   public static final int REPORT = 2;
   
   public static final String JSON_KEY_ID = "evaluation_id";
   public static final String JSON_KEY_TITLE = "evaluation_title";
   public static final String JSON_KEY_TYPE = "evaluation_type";
   public static final String JSON_KEY_ALLOW_ANSWER = "show_button";
   
   protected int type;
   protected ExamIntro introduction;
   protected ExamReport report;
   protected boolean allowAnswer;
   
   public ExamInfo() {
      super();
   }

   public ExamInfo(JSONObject jsonObject) {
      super(jsonObject);
      
      if (isSuccess()) {
         try {
            JSONObject infoObject = null;
            if (!jsonObject.isNull(JSON_KEY_LIST)) {
               infoObject = jsonObject.getJSONObject(JSON_KEY_LIST);
            }
            if (infoObject == null) {
               return;
            }
            
            if (!infoObject.isNull(JSON_KEY_ID)) {
               this.id = infoObject.getString(JSON_KEY_ID).trim();
            }
            if (!infoObject.isNull(JSON_KEY_TITLE)) {
               this.title = infoObject.getString(JSON_KEY_TITLE).trim();
            }
            if (!infoObject.isNull(JSON_KEY_TYPE)) {
               this.type = infoObject.getInt(JSON_KEY_TYPE);
            }
            this.introduction = new ExamIntro(infoObject);
            this.report = new ExamReport(infoObject);
         } catch (JSONException e) {
   
         }
      }
   }

   public int getType() {
      return type;
   }

   public void setType(int type) {
      this.type = type;
   }
   
   public boolean hasExamined() {
      return this.type == REPORT;
   }

   public ExamIntro getIntroduction() {
      return this.introduction;
   }

   public void setIntroduction(ExamIntro introduction) {
      this.introduction = introduction;
   }

   public ExamReport getReport() {
      return this.report;
   }

   public void setReport(ExamReport report) {
      this.report = report;
   }
   
   public boolean allowAnswer() {
      return this.allowAnswer;
   }
   
   public void setAllowAnswer(boolean allow) {
      this.allowAnswer = allow;
   }

}
