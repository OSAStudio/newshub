package com.osastudio.newshub.data;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.NewsResult;

public class RegisterResult extends NewsResult {

   private String userId = "";

   public RegisterResult(JSONObject jsonObject) {
      super(jsonObject);

      if (isSuccess()) {
         try {
            JSONObject idObject = null;
            if (!jsonObject.isNull(JSON_KEY_LIST)) {
               idObject = jsonObject.getJSONObject(JSON_KEY_LIST);
            }
            if (idObject == null) {
               return;
            }

            if (!idObject.isNull(JSON_KEY_USER_ID)) {
               this.userId = idObject.getString(JSON_KEY_USER_ID).trim();
            }
         } catch (JSONException e) {

         }
      }
   }

   public String getUserId() {
      return this.userId;
   }

   public RegisterResult setUserId(String userId) {
      this.userId = userId;
      return this;
   }

}
