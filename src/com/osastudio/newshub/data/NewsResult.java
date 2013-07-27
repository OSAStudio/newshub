package com.osastudio.newshub.data;

import org.json.JSONException;
import org.json.JSONObject;

public class NewsResult implements ResultCode {

   protected static final String JSON_KEY_RESULT_DESCRIPTION = "msg";
   protected static final String JSON_KEY_RESULT_CODE = "stat";

   private int resultCode = RESULT_NONE;
   private String resultDesc;

   public NewsResult() {
      
   }

   public NewsResult(JSONObject jsonObject) {
      try {
         if (!jsonObject.isNull(JSON_KEY_RESULT_CODE)) {
            setResultCode(jsonObject.getString(JSON_KEY_RESULT_CODE));
         }
         if (!jsonObject.isNull(JSON_KEY_RESULT_DESCRIPTION)) {
            setResultDescription(jsonObject
                  .getString(JSON_KEY_RESULT_DESCRIPTION));
         }
      } catch (JSONException e) {

      }
   }

   public NewsResult(String resultString, String resultDesc) {
      try {
         this.resultCode = Integer.parseInt(resultString);
      } catch (NumberFormatException e) {

      }
      this.resultDesc = resultDesc;
   }

   public NewsResult(int resultCode, String resultDesc) {
      this.resultCode = resultCode;
      this.resultDesc = resultDesc;
   }

   public int getResultCode() {
      return this.resultCode;
   }

   public void setResultCode(String resultString) {
      try {
         this.resultCode = Integer.parseInt(resultString);
      } catch (NumberFormatException e) {

      }
   }

   public void setResultCode(int resultCode) {
      this.resultCode = resultCode;
   }

   public String getResultDescription() {
      return this.resultDesc;
   }

   public NewsResult setResultDescription(String resultDescription) {
      this.resultDesc = resultDescription;
      return this;
   }
   
   public boolean isAlert() {
      return false;
   }

   public boolean isSuccess() {
      return this.resultCode == RESULT_OK;
   }

   public boolean isFailure() {
      return !isSuccess();
   }

}
