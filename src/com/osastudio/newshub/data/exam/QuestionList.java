package com.osastudio.newshub.data.exam;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsItemList;

public class QuestionList extends NewsItemList<Question> {

   public static final String JSON_KEY_LIST = "quess_list";

   public QuestionList(JSONObject jsonObject) {
      try {
         if (!jsonObject.isNull(JSON_KEY_LIST)) {
            JSONArray jsonArray = jsonObject.getJSONArray(JSON_KEY_LIST);
            Question q = null;
            for (int i = 0; i < jsonArray.length(); i++) {
               try {
                  if (!jsonArray.isNull(i)) {
                     q = new Question(jsonArray.getJSONObject(i));
                     q.setOrder(i + 1);
                     q.setTotalCount(jsonArray.length());
                     this.list.add(q);
                  }
               } catch (JSONException e) {
                  continue;
               }
            }
         }
      } catch (JSONException e) {

      }
   }

}
