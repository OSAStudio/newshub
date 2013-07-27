package com.osastudio.newshub.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FeedbackTypeList extends NewsObjectList<FeedbackType> {

   public FeedbackTypeList(JSONObject jsonObject) {
      super(jsonObject);

      if (isSuccess()) {
         try {
            if (!jsonObject.isNull(JSON_KEY_LIST)) {
               JSONArray jsonArray = jsonObject.getJSONArray(JSON_KEY_LIST);
               this.list = new ArrayList<FeedbackType>();
               for (int i = 0; i < jsonArray.length(); i++) {
                  try {
                     if (!jsonArray.isNull(i)) {
                        this.list.add(FeedbackType.parseJsonObject(jsonArray
                              .getJSONObject(i)));
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

}