package com.osastudio.newshub.data.exam;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsItemList;

public class ExamIntro extends NewsItemList<Paragraph> {

   protected static final String JSON_KEY_PARAGRAPH_TITLE = "description_title";
   protected static final String JSON_KEY_PARAGRAPH_CONTENT = "description_content";
   private static final String JSON_KEY_P1_TITLE = "description1_title";
   private static final String JSON_KEY_P1_CONTENT = "description1_content";
   private static final String JSON_KEY_P2_TITLE = "description2_title";
   private static final String JSON_KEY_P2_CONTENT = "description2_content";
   private static final String JSON_KEY_P3_TITLE = "description3_title";
   private static final String JSON_KEY_P3_CONTENT = "description3_content";
   
   public ExamIntro() {
      super();
   }
   
   public ExamIntro(JSONObject jsonObject) {
      super(jsonObject);
      
      try {
         Paragraph p = new Paragraph();
         if (!jsonObject.isNull(JSON_KEY_P1_TITLE)) {
            p.setTitle(jsonObject.getString(JSON_KEY_P1_TITLE).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_P1_CONTENT)) {
            p.setContent(jsonObject.getString(JSON_KEY_P1_CONTENT).trim());
         }
         this.list.add(p);
         p = new Paragraph();
         if (!jsonObject.isNull(JSON_KEY_P2_TITLE)) {
            p.setTitle(jsonObject.getString(JSON_KEY_P2_TITLE).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_P2_CONTENT)) {
            p.setContent(jsonObject.getString(JSON_KEY_P2_CONTENT).trim());
         }
         this.list.add(p);
         p = new Paragraph();
         if (!jsonObject.isNull(JSON_KEY_P3_TITLE)) {
            p.setTitle(jsonObject.getString(JSON_KEY_P3_TITLE).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_P3_CONTENT)) {
            p.setContent(jsonObject.getString(JSON_KEY_P3_CONTENT).trim());
         }
         this.list.add(p);
      } catch (JSONException e) {

      }
   }
   
}
