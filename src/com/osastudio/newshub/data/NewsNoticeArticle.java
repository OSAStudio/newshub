package com.osastudio.newshub.data;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsBaseContent;

public class NewsNoticeArticle extends NewsBaseContent {

   public static final String JSON_KEY_CONTENT = "notify_content";
   public static final String JSON_KEY_FEEDBACK_REQUIRED = "need_back";
   public static final String JSON_KEY_NOTICE_ARTICLE = "list";

   private boolean isFeedbackRequired = false;

   public NewsNoticeArticle(JSONObject jsonObject) {
      super(jsonObject);

      if (isSuccess()) {
         try {
            JSONObject articleObject = null;
            if (!jsonObject.isNull(JSON_KEY_NOTICE_ARTICLE)) {
               articleObject = jsonObject
                     .getJSONObject(JSON_KEY_NOTICE_ARTICLE);
            }
            if (articleObject == null) {
               return;
            }

            if (!articleObject.isNull(JSON_KEY_CONTENT)) {
               setContent(articleObject.getString(JSON_KEY_CONTENT).trim());
            }
            if (!articleObject.isNull(JSON_KEY_FEEDBACK_REQUIRED)) {
               setFeedbackRequired((articleObject
                     .getInt(JSON_KEY_FEEDBACK_REQUIRED) != 0) ? true : false);
            }
            setNewsBaseTitle(NewsNotice.parseJsonObject(articleObject));
         } catch (JSONException e) {

         }
      }
   }

   public boolean isFeedbackRequired() {
      return this.isFeedbackRequired;
   }

   public NewsNoticeArticle setFeedbackRequired(boolean isFeedbackRequired) {
      this.isFeedbackRequired = isFeedbackRequired;
      return this;
   }

}
