package com.osastudio.newshub.data.base;

import org.json.JSONObject;

public class NewsBaseArticle extends NewsBaseObject implements NewsId {

   protected NewsBaseAbstract newsAbstract;
   protected String content;

   public NewsBaseArticle() {
      this.newsAbstract = new NewsBaseAbstract();
   }
   
   public NewsBaseArticle(JSONObject jsonObject) {
      super(jsonObject);
   }

   public NewsBaseAbstract getNewsBaseAbstract() {
      return this.newsAbstract;
   }

   public NewsBaseArticle setNewsBaseAbstract(NewsBaseAbstract title) {
      this.newsAbstract = title;
      return this;
   }

   public String getId() {
      return (this.newsAbstract != null) ? this.newsAbstract.getId() : null;
   }

   public NewsBaseArticle setId(String id) {
      if (this.newsAbstract == null) {
         this.newsAbstract = new NewsBaseAbstract();
      }
      this.newsAbstract.setId(id);
      return this;
   }

   public String getTitle() {
      return (this.newsAbstract != null) ? this.newsAbstract.getTitle() : null;
   }

   public NewsBaseArticle setTitle(String title) {
      if (this.newsAbstract == null) {
         this.newsAbstract = new NewsBaseAbstract();
      }
      this.newsAbstract.setTitle(title);
      return this;
   }

   public String getPublishedTime() {
      return (this.newsAbstract != null) ? this.newsAbstract.getPublishedTime()
            : null;
   }

   public NewsBaseArticle setPublishedTime(String time) {
      if (this.newsAbstract == null) {
         this.newsAbstract = new NewsBaseAbstract();
      }
      this.newsAbstract.setPublishedTime(time);
      return this;
   }

   public String getAuthor() {
      return (this.newsAbstract != null) ? this.newsAbstract.getAuthor() : null;
   }

   public NewsBaseArticle setAuthor(String author) {
      if (this.newsAbstract == null) {
         this.newsAbstract = new NewsBaseAbstract();
      }
      this.newsAbstract.setAuthor(author);
      return this;
   }

   public int getColor() {
      return (this.newsAbstract != null) ? this.newsAbstract.getColor() : null;
   }

   public NewsBaseArticle setColor(int color) {
      if (this.newsAbstract == null) {
         this.newsAbstract = new NewsBaseAbstract();
      }
      this.newsAbstract.setColor(color);
      return this;
   }

   public String getContent() {
      return this.content;
   }

   public NewsBaseArticle setContent(String content) {
      this.content = content;
      return this;
   }

}
