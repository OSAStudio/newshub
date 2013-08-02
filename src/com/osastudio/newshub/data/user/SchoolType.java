package com.osastudio.newshub.data.user;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.SingleStringFieldObject;

public class SchoolType extends SingleStringFieldObject {

   public static final String JSON_KEY_SCHOOL_TYPE = "school_class";

   public static SchoolType parseJsonObject(JSONObject jsonObject) {
      SchoolType result = new SchoolType();
      try {
         if (!jsonObject.isNull(JSON_KEY_SCHOOL_TYPE)) {
            result.setValue(jsonObject.getString(JSON_KEY_SCHOOL_TYPE).trim());
         }
      } catch (JSONException e) {

      }
      return result;
   }

}
