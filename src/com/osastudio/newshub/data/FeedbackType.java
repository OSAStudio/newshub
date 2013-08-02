package com.osastudio.newshub.data;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.PairedStringFieldsObject;

public class FeedbackType extends PairedStringFieldsObject {

   public static final String JSON_KEY_ID = "problem_type_id";
   public static final String JSON_KEY_NAME = "problem_type_name";

   public static FeedbackType parseJsonObject(JSONObject jsonObject) {
      FeedbackType result = new FeedbackType();
      try {
         if (!jsonObject.isNull(JSON_KEY_ID)) {
            result.setId(jsonObject.getString(JSON_KEY_ID).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_NAME)) {
            result.setName(jsonObject.getString(JSON_KEY_NAME).trim());
         }
      } catch (JSONException e) {

      }
      return result;
   }

}
