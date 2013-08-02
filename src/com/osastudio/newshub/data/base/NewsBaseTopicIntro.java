package com.osastudio.newshub.data.base;

import org.json.JSONObject;

public class NewsBaseTopicIntro extends NewsBaseObject implements NewsId {

   protected NewsBaseTopic newsTopic;
   protected String content;

   public NewsBaseTopicIntro() {
      this.newsTopic = new NewsBaseTopic();
   }

   public NewsBaseTopicIntro(JSONObject jsonObject) {
      super(jsonObject);
   }

   public NewsBaseTopic getNewsBaseTopic() {
      return this.newsTopic;
   }

   public NewsBaseTopicIntro setNewsBaseTopic(NewsBaseTopic topic) {
      this.newsTopic = topic;
      return this;
   }

   public String getId() {
      return (this.newsTopic != null) ? this.newsTopic.getId() : null;
   }

   public NewsBaseTopicIntro setId(String id) {
      if (this.newsTopic == null) {
         this.newsTopic = new NewsBaseTopic();
      }
      this.newsTopic.setId(id);
      return this;
   }

   public String getTitle() {
      return (this.newsTopic != null) ? this.newsTopic.getTitle() : null;
   }

   public NewsBaseTopicIntro setTitle(String title) {
      if (this.newsTopic == null) {
         this.newsTopic = new NewsBaseTopic();
      }
      this.newsTopic.setTitle(title);
      return this;
   }

   public String getIconUrl() {
      return (this.newsTopic != null) ? this.newsTopic.getIconUrl() : null;
   }

   public NewsBaseTopicIntro setIconUrl(String url) {
      if (this.newsTopic == null) {
         this.newsTopic = new NewsBaseTopic();
      }
      this.newsTopic.setIconUrl(url);
      return this;
   }

   public String getContent() {
      return this.content;
   }

   public NewsBaseTopicIntro setContent(String introduction) {
      this.content = introduction;
      return this;
   }

}
