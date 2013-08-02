package com.osastudio.newshub.data;

import org.json.JSONException;
import org.json.JSONObject;

public class RecommendedNewsTopicIntroduction extends NewsBaseTopicIntroduction {

   public static final String JSON_KEY_CONTENT = "recommand_lssue_content";
   public static final String JSON_KEY_ARTICLE = "list";

   public RecommendedNewsTopicIntroduction(JSONObject jsonObject) {
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
            setNewsBaseTopic(RecommendedNewsTopic
                  .parseJsonObject(articleObject));
         } catch (JSONException e) {

         }
      }
   }

}
