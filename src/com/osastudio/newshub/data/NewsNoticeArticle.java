package com.osastudio.newshub.data;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsBaseObject;

public class NewsNoticeArticle extends NewsBaseObject {

   public static final String JSON_KEY_CONTENT = "notify_content";
   public static final String JSON_KEY_FEEDBACK_REQUIRED = "need_back";
   public static final String JSON_KEY_NOTICE_ARTICLE = "list";

   private NewsNotice newsNotice;
   private String content;
   private boolean isFeedbackRequired = false;

   public NewsNoticeArticle() {

   }

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
            setNotice(NewsNotice.parseJsonObject(articleObject));
         } catch (JSONException e) {

         }
      }
   }

   public NewsNotice getNotice() {
      return newsNotice;
   }

   public NewsNoticeArticle setNotice(NewsNotice notice) {
      this.newsNotice = notice;
      return this;
   }

   public String getNoticeId() {
      return (this.newsNotice != null) ? this.newsNotice.getNoticeId() : null;
   }

   public NewsNoticeArticle setNoticeId(String noticeId) {
      if (this.newsNotice == null) {
         this.newsNotice = new NewsNotice();
      }
      this.newsNotice.setNoticeId(noticeId);
      return this;
   }

   public String getPublishedTime() {
      return (this.newsNotice != null) ? this.newsNotice.getPublishedTime()
            : null;
   }

   public NewsNoticeArticle setPublishedTime(String publishedTime) {
      if (this.newsNotice == null) {
         this.newsNotice = new NewsNotice();
      }
      this.newsNotice.setPublishedTime(publishedTime);
      return this;
   }

   public String getTitle() {
      return (this.newsNotice != null) ? this.newsNotice.getTitle() : null;
   }

   public NewsNoticeArticle setTitle(String title) {
      if (this.newsNotice == null) {
         this.newsNotice = new NewsNotice();
      }
      this.newsNotice.setTitle(title);
      return this;
   }

   public String getContent() {
      return this.content;
   }

   public NewsNoticeArticle setContent(String content) {
      this.content = content;
      return this;
   }

   public boolean isFeedbackRequired() {
      return this.isFeedbackRequired;
   }

   public NewsNoticeArticle setFeedbackRequired(boolean isFeedbackRequired) {
      this.isFeedbackRequired = isFeedbackRequired;
      return this;
   }

}
