package com.osastudio.newshub.data;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsBaseTopicIntro;

/**
 * Class for introduction of recommended topic, including content of
 * recommended topic introduction
 * 
 * @author Rujin Xue
 * 
 */
public class RecommendedTopicIntro extends NewsBaseTopicIntro {

   public static final String JSON_KEY_CONTENT = "recommend_lssue_content";
   public static final String JSON_KEY_ARTICLE = "list";

   public RecommendedTopicIntro(JSONObject jsonObject) {
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
            setNewsBaseTopic(RecommendedTopic.parseJsonObject(articleObject));
         } catch (JSONException e) {

         }
      }
   }

}
