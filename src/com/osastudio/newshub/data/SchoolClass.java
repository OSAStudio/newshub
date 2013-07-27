package com.osastudio.newshub.data;

import org.json.JSONException;
import org.json.JSONObject;

public class SchoolClass extends PairedStringFieldsObject {

   public static final String JSON_KEY_ID = "class_id";
   public static final String JSON_KEY_NAME = "class_name";

   public static SchoolClass parseJsonObject(JSONObject jsonObject) {
      SchoolClass result = new SchoolClass();
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
