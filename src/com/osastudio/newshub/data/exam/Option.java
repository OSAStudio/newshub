package com.osastudio.newshub.data.exam;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsBaseTitle;

public class Option extends NewsBaseTitle {

   public static final String JSON_KEY_ID = "option_id";
   public static final String JSON_KEY_TITLE = "option_title";
   public static final String JSON_KEY_NAME = "option_number";
   
   protected String name;
   protected int order = 0;
   protected int totalCount = 0;
   protected boolean selected = false;
   
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
         if (!jsonObject.isNull(JSON_KEY_NAME)) {
            this.name = jsonObject.getString(JSON_KEY_NAME).trim();
         }
      } catch (JSONException e) {
   
      }
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }
   
   public int getOrder() {
      return this.order;
   }

   public void setOrder(int order) {
      this.order = order;
   }

   public int getTotalCount() {
      return this.totalCount;
   }

   public void setTotalCount(int totalCount) {
      this.totalCount = totalCount;
   }
   
   public boolean isSelected() {
      return this.selected;
   }
   
   public void setSelected(boolean selected) {
      this.selected = selected;
   }

}
