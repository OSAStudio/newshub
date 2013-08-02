package com.osastudio.newshub.data;

import org.json.JSONException;
import org.json.JSONObject;

public class SubscribedNewsArticle extends NewsBaseTopicArticle {

   public static final String JSON_KEY_CONTENT = "expand_lesson_content";
   public static final String JSON_KEY_ARTICLE = "list";

   public SubscribedNewsArticle(JSONObject jsonObject) {
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
            setNewsBaseTopicAbstract(SubscribedNewsAbstract
                  .parseJsonObject(articleObject));
         } catch (JSONException e) {

         }
      }
   }

}
