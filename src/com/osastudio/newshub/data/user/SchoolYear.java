package com.osastudio.newshub.data.user;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.PairedStringFieldsObject;

public class SchoolYear extends PairedStringFieldsObject {

   public static final String JSON_KEY_ID = "year_id";
   public static final String JSON_KEY_NAME = "year_name";

   public static SchoolYear parseJsonObject(JSONObject jsonObject) {
      SchoolYear result = new SchoolYear();
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
