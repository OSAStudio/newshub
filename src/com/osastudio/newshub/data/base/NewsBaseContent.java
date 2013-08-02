package com.osastudio.newshub.data.base;

import org.json.JSONObject;

public class NewsBaseContent extends NewsBaseObject implements NewsId {

   protected NewsBaseTitle newsTitle;
   protected String content;

   public NewsBaseContent() {
      this.newsTitle = new NewsBaseTitle();
   }

   public NewsBaseContent(JSONObject jsonObject) {
      super(jsonObject);
   }

   public NewsBaseTitle getNewsBaseTitle() {
      return this.newsTitle;
   }

   public NewsBaseContent setNewsBaseTitle(NewsBaseTitle title) {
      this.newsTitle = title;
      return this;
   }

   public String getId() {
      return (this.newsTitle != null) ? this.newsTitle.getId() : null;
   }

   public NewsBaseContent setId(String id) {
      if (this.newsTitle == null) {
         this.newsTitle = new NewsBaseTitle();
      }
      this.newsTitle.setId(id);
      return this;
   }

   public String getTitle() {
      return (this.newsTitle != null) ? this.newsTitle.getTitle() : null;
   }

   public NewsBaseContent setTitle(String title) {
      if (this.newsTitle == null) {
         this.newsTitle = new NewsBaseTitle();
      }
      this.newsTitle.setTitle(title);
      return this;
   }

   public String getContent() {
      return this.content;
   }

   public NewsBaseContent setContent(String content) {
      this.content = content;
      return this;
   }

}
