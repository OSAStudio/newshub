package com.osastudio.newshub.data.exam;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsBaseTitle;

public class Question extends NewsBaseTitle {

   public static final String JSON_KEY_ID = "quess_id";
   public static final String JSON_KEY_TITLE = "quess_title";
   public static final String JSON_KEY_TYPE = "quess_class";
   public static final String JSON_KEY_MAX_CHOICES = "quess_opnion";
   public static final String JSON_KEY_OPTIONS_LIST = "option_list";

   protected int type;
   protected int maxChoices;
   protected OptionList optionsList;
   protected int order = 0;
   protected int totalCount = 0;

   public Question() {
      super();
   }

   public Question(JSONObject jsonObject) {
      super(jsonObject);

      try {
         if (!jsonObject.isNull(JSON_KEY_ID)) {
            this.id = jsonObject.getString(JSON_KEY_ID).trim();
         }
         if (!jsonObject.isNull(JSON_KEY_TITLE)) {
            this.title = jsonObject.getString(JSON_KEY_TITLE).trim();
         }
         if (!jsonObject.isNull(JSON_KEY_TYPE)) {
            this.type = jsonObject.getInt(JSON_KEY_TYPE);
         }
         if (!jsonObject.isNull(JSON_KEY_MAX_CHOICES)) {
            this.maxChoices = jsonObject.getInt(JSON_KEY_MAX_CHOICES);
         }
         if (!jsonObject.isNull(JSON_KEY_OPTIONS_LIST)) {
            this.optionsList = new OptionList(jsonObject);
         }
      } catch (JSONException e) {

      }
   }

   public int getType() {
      return this.type;
   }

   public void setType(int type) {
      this.type = type;
   }

   public int getMaxChoices() {
      return this.maxChoices;
   }

   public void setMaxChoices(int maxChoices) {
      this.maxChoices = maxChoices;
   }

   public boolean isSingleChoice() {
      return this.maxChoices == 1;
   }

   public boolean isMultipleChoice() {
      return this.maxChoices > 1;
   }

   public OptionList getOptions() {
      return this.optionsList;
   }

   public void setOptions(OptionList options) {
      this.optionsList = options;
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

}
