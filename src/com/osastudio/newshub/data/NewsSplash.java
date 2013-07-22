package com.osastudio.newshub.data;

import org.json.JSONException;
import org.json.JSONObject;

public class NewsSplash extends NewsBaseObject {
   
   public static final String JSON_KEY_IMAGE_URL = "picture_url";

   private String imageUrl;
   
   public NewsSplash() {
      
   }
   
   public String getImageUrl() {
      return this.imageUrl;
   }

   public NewsSplash setImageUrl(String imageUrl) {
      this.imageUrl = imageUrl;
      return this;
   }

   public static NewsSplash parseJsonObject(JSONObject jsonObject) {
      NewsSplash result = new NewsSplash();
      try {
         if (jsonObject.isNull(JSON_KEY_RESULT_CODE)
               || NewsSplash.isResultFailure(jsonObject
                     .getString(JSON_KEY_RESULT_CODE))) {
            return null;
         }

         if (!jsonObject.isNull(JSON_KEY_IMAGE_URL)) {
            result.setImageUrl(jsonObject.getString(JSON_KEY_IMAGE_URL).trim());
         }
      } catch (JSONException e) {
         
      }
      return result;
   }
   
}
