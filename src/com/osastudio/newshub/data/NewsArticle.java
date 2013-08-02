package com.osastudio.newshub.data;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsBaseObject;

public class NewsArticle extends NewsBaseObject {

   public static final String JSON_KEY_CONTENT = "lesson_content";

   private NewsAbstract newsAbstract;
   private String content;

   public NewsArticle() {

   }

   public NewsArticle(JSONObject jsonObject) {
      super(jsonObject);

      if (isSuccess()) {
         try {
            if (!jsonObject.isNull(JSON_KEY_CONTENT)) {
               setContent(jsonObject.getString(JSON_KEY_CONTENT).trim());
            }
            setAbstract(NewsAbstract.parseJsonObject(jsonObject));
         } catch (JSONException e) {

         }
      }
   }

   public NewsAbstract getAbstract() {
      return newsAbstract;
   }

   public NewsArticle setAbstract(NewsAbstract abs) {
      this.newsAbstract = abs;
      return this;
   }

   public String getArticleId() {
      return (this.newsAbstract != null) ? this.newsAbstract.getArticleId()
            : null;
   }

   public NewsArticle setArticleId(String articleId) {
      if (this.newsAbstract == null) {
         this.newsAbstract = new NewsAbstract();
      }
      this.newsAbstract.setArticleId(articleId);
      return this;
   }

   public String getChannelId() {
      return (this.newsAbstract != null) ? this.newsAbstract.getChannelId()
            : null;
   }

   public NewsArticle setChannelId(String channelId) {
      if (this.newsAbstract == null) {
         this.newsAbstract = new NewsAbstract();
      }
      this.newsAbstract.setChannelId(channelId);
      return this;
   }

   public int getColor() {
      return (this.newsAbstract != null) ? this.newsAbstract.getColor()
            : getDefaultColor();
   }

   public NewsArticle setColor(int color) {
      if (this.newsAbstract == null) {
         this.newsAbstract = new NewsAbstract();
      }
      this.newsAbstract.setColor(color);
      return this;
   }

   public String getPublishedTime() {
      return (this.newsAbstract != null) ? this.newsAbstract.getPublishedTime()
            : null;
   }

   public NewsArticle setPublishedTime(String publishedTime) {
      if (this.newsAbstract == null) {
         this.newsAbstract = new NewsAbstract();
      }
      this.newsAbstract.setPublishedTime(publishedTime);
      return this;
   }

   public String getAuthor() {
      return (this.newsAbstract != null) ? this.newsAbstract.getAuthor()
            : null;
   }

   public NewsArticle setAuthor(String author) {
      if (this.newsAbstract == null) {
         this.newsAbstract = new NewsAbstract();
      }
      this.newsAbstract.setAuthor(author);
      return this;
   }

   public String getTitle() {
      return (this.newsAbstract != null) ? this.newsAbstract.getTitle() : null;
   }

   public NewsArticle setTitle(String title) {
      if (this.newsAbstract == null) {
         this.newsAbstract = new NewsAbstract();
      }
      this.newsAbstract.setTitle(title);
      return this;
   }

   public String getContent() {
      return this.content;
   }

   public NewsArticle setContent(String content) {
      this.content = content;
      return this;
   }

}
