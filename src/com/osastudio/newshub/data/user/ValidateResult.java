package com.osastudio.newshub.data.user;

import org.json.JSONObject;

import com.osastudio.newshub.data.NewsResult;

public class ValidateResult extends NewsResult {

   public ValidateResult(JSONObject jsonObject) {
      super(jsonObject);
   }
   
   public boolean isValidated() {
      if (this.resultCode == RESULT_OK) {
         return true;
      }
      return false;
   }
   
}
