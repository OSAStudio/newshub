package com.osastudio.newshub.data.base;

import org.json.JSONObject;

public class NewsBaseTopicArticle extends NewsBaseObject implements NewsId {

   protected NewsBaseTopicAbstract newsAbstract;
   protected String content;

   public NewsBaseTopicArticle() {
      this.newsAbstract = new NewsBaseTopicAbstract();
   }
   
   public NewsBaseTopicArticle(JSONObject jsonObject) {
      super(jsonObject);
   }

   public NewsBaseTopicAbstract getNewsBaseTopicAbstract() {
      return this.newsAbstract;
   }

   public NewsBaseTopicArticle setNewsBaseTopicAbstract(
         NewsBaseTopicAbstract title) {
      this.newsAbstract = title;
      return this;
   }

   public String getId() {
      return (this.newsAbstract != null) ? this.newsAbstract.getId() : null;
   }

   public NewsBaseTopicArticle setId(String id) {
      if (this.newsAbstract == null) {
         this.newsAbstract = new NewsBaseTopicAbstract();
      }
      this.newsAbstract.setId(id);
      return this;
   }

   public String getTitle() {
      return (this.newsAbstract != null) ? this.newsAbstract.getTitle() : null;
   }

   public NewsBaseTopicArticle setTitle(String title) {
      if (this.newsAbstract == null) {
         this.newsAbstract = new NewsBaseTopicAbstract();
      }
      this.newsAbstract.setTitle(title);
      return this;
   }

   public String getPublishedTime() {
      return (this.newsAbstract != null) ? this.newsAbstract.getPublishedTime()
            : null;
   }

   public NewsBaseTopicArticle setPublishedTime(String time) {
      if (this.newsAbstract == null) {
         this.newsAbstract = new NewsBaseTopicAbstract();
      }
      this.newsAbstract.setPublishedTime(time);
      return this;
   }

   public String getAuthor() {
      return (this.newsAbstract != null) ? this.newsAbstract.getAuthor() : null;
   }

   public NewsBaseTopicArticle setAuthor(String author) {
      if (this.newsAbstract == null) {
         this.newsAbstract = new NewsBaseTopicAbstract();
      }
      this.newsAbstract.setAuthor(author);
      return this;
   }

   public int getColor() {
      return (this.newsAbstract != null) ? this.newsAbstract.getColor() : null;
   }

   public NewsBaseTopicArticle setColor(int color) {
      if (this.newsAbstract == null) {
         this.newsAbstract = new NewsBaseTopicAbstract();
      }
      this.newsAbstract.setColor(color);
      return this;
   }

   public String getTopicId() {
      return (this.newsAbstract != null) ? this.newsAbstract.getTopicId()
            : null;
   }

   public NewsBaseTopicArticle setTopicId(String id) {
      if (this.newsAbstract == null) {
         this.newsAbstract = new NewsBaseTopicAbstract();
      }
      this.newsAbstract.setTopicId(id);
      return this;
   }

   public String getContent() {
      return this.content;
   }

   public NewsBaseTopicArticle setContent(String content) {
      this.content = content;
      return this;
   }

}
