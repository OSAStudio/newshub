package com.osastudio.newshub.data;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsBaseContent;

public class DailyReminderArticle extends NewsBaseContent {

   public static final String JSON_KEY_ARTICLE = "list";
   public static final String JSON_KEY_CONTENT = "daily_reminder_content";

   public DailyReminderArticle(JSONObject jsonObject) {
      super(jsonObject);

      if (isSuccess()) {
         try {
            JSONObject articleObject = null;
            if (!jsonObject.isNull(JSON_KEY_ARTICLE)) {
               articleObject = jsonObject.getJSONObject(JSON_KEY_ARTICLE);
            }
            if (articleObject == null) {
               return;
            }

            if (!articleObject.isNull(JSON_KEY_CONTENT)) {
               setContent(articleObject.getString(JSON_KEY_CONTENT).trim());
            }
            setNewsBaseTitle(DailyReminder.parseJsonObject(articleObject));
         } catch (JSONException e) {

         }
      }
   }

}
