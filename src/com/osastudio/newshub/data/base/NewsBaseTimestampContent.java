package com.osastudio.newshub.data.base;

import org.json.JSONObject;

public class NewsBaseTimestampContent extends NewsBaseContent implements NewsId {

   protected String publishedTime;

   public NewsBaseTimestampContent() {
      super();
   }

   public NewsBaseTimestampContent(JSONObject jsonObject) {
      super(jsonObject);
   }

   public String getPublishedTime() {
      return this.publishedTime;
   }

   public NewsBaseTimestampContent setPublishedTime(String time) {
      this.publishedTime = time;
      return this;
   }

}
