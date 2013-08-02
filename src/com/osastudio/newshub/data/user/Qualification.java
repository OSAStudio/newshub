package com.osastudio.newshub.data.user;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.SingleStringFieldObject;

public class Qualification extends SingleStringFieldObject {

   public static final String JSON_KEY_QUALIFICATION = "xueli";

   public static Qualification parseJsonObject(JSONObject jsonObject) {
      Qualification result = new Qualification();
      try {
         if (!jsonObject.isNull(JSON_KEY_QUALIFICATION)) {
            result.setValue(jsonObject.getString(JSON_KEY_QUALIFICATION).trim());
         }
      } catch (JSONException e) {

      }
      return result;
   }

}
