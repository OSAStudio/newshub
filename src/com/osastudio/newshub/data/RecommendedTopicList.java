package com.osastudio.newshub.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsItemList;

/**
 * List for recommended topic
 * 
 * @author Rujin Xue
 *
 */
public class RecommendedTopicList extends
      NewsItemList<RecommendedTopic> {

   public RecommendedTopicList(JSONObject jsonObject) {
      super(jsonObject);

      if (isSuccess()) {
         try {
            if (!jsonObject.isNull(JSON_KEY_LIST)) {
               JSONArray jsonArray = jsonObject.getJSONArray(JSON_KEY_LIST);
               RecommendedTopic topic = null;
               ArrayList<RecommendedTopic> topics = new ArrayList<RecommendedTopic>();
               for (int i = 0; i < jsonArray.length(); i++) {
                  try {
                     if (!jsonArray.isNull(i)) {
                        topic = RecommendedTopic.parseJsonObject(jsonArray
                              .getJSONObject(i));
                        if (topic != null) {
                           topics.add(topic);
                        }
                     }
                  } catch (JSONException e) {
                     continue;
                  }
               }
               setList(topics);
            }
         } catch (JSONException e) {

         }
      }
   }

}
