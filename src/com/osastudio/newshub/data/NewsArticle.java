package com.osastudio.newshub.data;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsBaseTopicArticle;

public class NewsArticle extends NewsBaseTopicArticle {

   public static final String JSON_KEY_CONTENT = "lesson_content";

   public NewsArticle(JSONObject jsonObject) {
      super(jsonObject);

      if (isSuccess()) {
         try {
            if (!jsonObject.isNull(JSON_KEY_CONTENT)) {
               setContent(jsonObject.getString(JSON_KEY_CONTENT).trim());
            }
            setNewsBaseAbstract(NewsAbstract.parseJsonObject(jsonObject));
         } catch (JSONException e) {

         }
      }
   }

   public String getChannelId() {
      return (this.newsAbstract != null) ? ((NewsAbstract) this.newsAbstract)
            .getChannelId() : getTopicId();
   }

   public NewsArticle setChannelId(String channelId) {
      setTopicId(channelId);
      if (this.newsAbstract == null) {
         this.newsAbstract = new NewsAbstract();
      }
      ((NewsAbstract) this.newsAbstract).setChannelId(channelId);
      return this;
   }

}
