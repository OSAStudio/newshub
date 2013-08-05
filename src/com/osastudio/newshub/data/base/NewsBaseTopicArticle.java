package com.osastudio.newshub.data.base;

import org.json.JSONObject;

public class NewsBaseTopicArticle extends NewsBaseArticle implements NewsId {

   protected String topicId = "";

   public NewsBaseTopicArticle() {
      super();
   }
   
   public NewsBaseTopicArticle(JSONObject jsonObject) {
      super(jsonObject);
   }

   public String getTopicId() {
      return this.topicId;
   }

   public NewsBaseTopicArticle setTopicId(String id) {
      this.topicId = id;
      return this;
   }

}
