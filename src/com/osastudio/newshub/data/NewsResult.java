package com.osastudio.newshub.data;

import org.json.JSONException;
import org.json.JSONObject;

public class NewsResult implements ResultCode {

   protected static final String JSON_KEY_RESULT_DESCRIPTION = "msg";
   protected static final String JSON_KEY_RESULT_CODE = "stat";

   protected int resultCode = RESULT_NONE;
   protected String resultDesc;

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
   
   public boolean needAlert() {
      if (this.resultCode == RESULT_INVALID_VALIDATE_CODE
            || this.resultCode == RESULT_VALIDATE_FAILURE
            || this.resultCode == RESULT_ILLEGAL_VALIDATE_CODE_STATUS
            || this.resultCode == RESULT_VALIDATE_CODE_ALREADY_EXISTS
            || this.resultCode == RESULT_REGISTER_FAILURE
            || this.resultCode == RESULT_NO_DEVICE_IDENTIFIER
            || this.resultCode == RESULT_PAYMENT_NEEDED
            || this.resultCode == RESULT_ADD_ACCOUNT_FAILURE) {
         return true;
      }
      return false;
   }

   public boolean isSuccess() {
      return this.resultCode == RESULT_OK;
   }

   public boolean isFailure() {
      return !isSuccess();
   }

}
