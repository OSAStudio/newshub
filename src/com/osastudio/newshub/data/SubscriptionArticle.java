package com.osastudio.newshub.data;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsBaseTopicArticle;

/**
 * Class for detail content of subscription article
 * 
 * @author Rujin Xue
 * 
 */
public class SubscriptionArticle extends NewsBaseTopicArticle {

   public static final String JSON_KEY_CONTENT = "expand_lesson_content";
   public static final String JSON_KEY_ARTICLE = "list";

   public SubscriptionArticle(JSONObject jsonObject) {
      super(jsonObject);

      if (isSuccess()) {
         try {
            JSONObject articleObject = null;
            if (!jsonObject.isNull(JSON_KEY_ARTICLE)) {
               articleObject = jsonObject.getJSONObject(JSON_KEY_ARTICLE);
            }
            if (articleObject == null) {
               return;
            }

            if (!articleObject.isNull(JSON_KEY_CONTENT)) {
               setContent(articleObject.getString(JSON_KEY_CONTENT).trim());
            }
            setNewsBaseAbstract(SubscriptionAbstract
                  .parseJsonObject(articleObject));
         } catch (JSONException e) {

         }
      }
   }

   @Override
   public String getTopicId() {
      return (this.newsAbstract != null) ? ((SubscriptionAbstract) this.newsAbstract)
            .getTopicId() : super.getTopicId();
   }

   @Override
   public NewsBaseTopicArticle setTopicId(String id) {
      super.setTopicId(id);
      if (this.newsAbstract == null) {
         this.newsAbstract = new SubscriptionAbstract();
      }
      ((SubscriptionAbstract) this.newsAbstract).setTopicId(id);
      return this;
   }

}
