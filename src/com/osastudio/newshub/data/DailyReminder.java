package com.osastudio.newshub.data;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsBaseTimestampContent;

public class DailyReminder extends NewsBaseTimestampContent {

   public static final String JSON_KEY_ID = "daily_reminder_id";
   public static final String JSON_KEY_TITLE = "daily_reminder_title";
   public static final String JSON_KEY_CONTENT = "daily_reminder_content";
   public static final String JSON_KEY_PUBLISHED_TIME = "post_date";
   public static final String JSON_KEY_TOPIC_ID = "lssue_id";
   public static final String JSON_KEY_NUMBER_OF_DAYS = "day_number";

   private String topicId;
   private String numberOfDays;

   public String getTopicId() {
      return this.topicId;
   }

   public DailyReminder setTopicId(String topicId) {
      this.topicId = topicId;
      return this;
   }

   public String getNumberOfDays() {
      return this.numberOfDays;
   }

   public DailyReminder setNumberOfDays(String numberOfDays) {
      this.numberOfDays = numberOfDays;
      return this;
   }

   public static DailyReminder parseJsonObject(JSONObject jsonObject) {
      DailyReminder result = new DailyReminder();
      try {
         if (!jsonObject.isNull(JSON_KEY_ID)) {
            result.setId(jsonObject.getString(JSON_KEY_ID).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_TITLE)) {
            result.setTitle(jsonObject.getString(JSON_KEY_TITLE).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_CONTENT)) {
            result.setContent(jsonObject.getString(JSON_KEY_CONTENT).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_PUBLISHED_TIME)) {
            result.setPublishedTime(jsonObject.getString(
                  JSON_KEY_PUBLISHED_TIME).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_TOPIC_ID)) {
            result.setContent(jsonObject.getString(JSON_KEY_TOPIC_ID).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_NUMBER_OF_DAYS)) {
            result.setContent(jsonObject.getString(JSON_KEY_NUMBER_OF_DAYS)
                  .trim());
         }
      } catch (JSONException e) {

      }
      return result;
   }

}
