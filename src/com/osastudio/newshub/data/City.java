package com.osastudio.newshub.data;

import org.json.JSONException;
import org.json.JSONObject;

public class City extends PairedStringFieldsObject {

   public static final String JSON_KEY_ID = "city_id";
   public static final String JSON_KEY_NAME = "city_name";

   public static City parseJsonObject(JSONObject jsonObject) {
      City result = new City();
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
