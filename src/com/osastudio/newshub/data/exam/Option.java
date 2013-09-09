package com.osastudio.newshub.data.exam;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsBaseTitle;

public class Option extends NewsBaseTitle {

   public static final String JSON_KEY_ID = "option_id";
   public static final String JSON_KEY_TITLE = "option_title";
   public static final String JSON_KEY_NUMBER = "option_number";
   
   protected String number;
   
   public Option() {
      super();
   }
   
   public Option(JSONObject jsonObject) {
      super(jsonObject);
      
      try {
         if (!jsonObject.isNull(JSON_KEY_ID)) {
            this.id = jsonObject.getString(JSON_KEY_ID).trim();
         }
         if (!jsonObject.isNull(JSON_KEY_TITLE)) {
            this.title = jsonObject.getString(JSON_KEY_TITLE).trim();
         }
         if (!jsonObject.isNull(JSON_KEY_NUMBER)) {
            this.number = jsonObject.getString(JSON_KEY_NUMBER).trim();
         }
      } catch (JSONException e) {
   
      }
   }

   public String getNumber() {
      return this.number;
   }

   public void setNumber(String number) {
      this.number = number;
   }
   
}
