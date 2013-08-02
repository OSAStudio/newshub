package com.osastudio.newshub.data.user;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.PairedStringFieldsObject;

public class CityDistrict extends PairedStringFieldsObject {

   public static final String JSON_KEY_ID = "area_id";
   public static final String JSON_KEY_NAME = "area_name";

   public static CityDistrict parseJsonObject(JSONObject jsonObject) {
      CityDistrict result = new CityDistrict();
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
