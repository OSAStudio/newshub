package com.osastudio.newshub.data.user;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.PairedStringFieldsObject;

public class School extends PairedStringFieldsObject {

   public static final String JSON_KEY_ID = "school_id";
   public static final String JSON_KEY_NAME = "school_name";

   public static School parseJsonObject(JSONObject jsonObject) {
      School result = new School();
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
