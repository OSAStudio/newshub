package com.osastudio.newshub.data.base;

import org.json.JSONObject;

public class NewsBaseTopicIntroduction extends NewsBaseObject implements NewsId {

   protected NewsBaseTopic newsTopic;
   protected String content;

   public NewsBaseTopicIntroduction() {
      this.newsTopic = new NewsBaseTopic();
   }

   public NewsBaseTopicIntroduction(JSONObject jsonObject) {
      super(jsonObject);
   }

   public NewsBaseTopic getNewsBaseTopic() {
      return this.newsTopic;
   }

   public NewsBaseTopicIntroduction setNewsBaseTopic(NewsBaseTopic topic) {
      this.newsTopic = topic;
      return this;
   }

   public String getId() {
      return (this.newsTopic != null) ? this.newsTopic.getId() : null;
   }

   public NewsBaseTopicIntroduction setId(String id) {
      if (this.newsTopic == null) {
         this.newsTopic = new NewsBaseTopic();
      }
      this.newsTopic.setId(id);
      return this;
   }

   public String getTitle() {
      return (this.newsTopic != null) ? this.newsTopic.getTitle() : null;
   }

   public NewsBaseTopicIntroduction setTitle(String title) {
      if (this.newsTopic == null) {
         this.newsTopic = new NewsBaseTopic();
      }
      this.newsTopic.setTitle(title);
      return this;
   }

   public String getIconUrl() {
      return (this.newsTopic != null) ? this.newsTopic.getIconUrl() : null;
   }

   public NewsBaseTopicIntroduction setIconUrl(String url) {
      if (this.newsTopic == null) {
         this.newsTopic = new NewsBaseTopic();
      }
      this.newsTopic.setIconUrl(url);
      return this;
   }

   public String getContent() {
      return this.content;
   }

   public NewsBaseTopicIntroduction setContent(String introduction) {
      this.content = introduction;
      return this;
   }

}
