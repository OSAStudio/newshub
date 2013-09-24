package com.osastudio.newshub.data;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

public class NewsResult implements ResultCode {

   public static final String JSON_KEY_RESULT_DESCRIPTION = "msg";
   public static final String JSON_KEY_RESULT_CODE = "stat";
   
   protected static final String JSON_KEY_LIST = "list";
   protected static final String JSON_KEY_USER_ID = "student_id";
   protected static final String JSON_KEY_USER_NAME = "student_name";

   protected int resultCode = RESULT_NONE;
   protected String resultDesc = "";

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
   
   public boolean isSuccess() {
      return this.resultCode == RESULT_OK;
   }

   public boolean isFailure() {
      return !isSuccess();
   }
   
   public boolean isNetworkError() {
      return this.resultCode >= RESULT_NETWORK_ERROR_BASE;
   }
   
   public static int httpCode2ResultCode(int httpCode) {
      return httpCode + RESULT_HTTP_BASE;
   }
   
   public static int resultCode2HttpCode(int resultCode) {
      return resultCode - RESULT_HTTP_BASE;
   }
   
   public static JSONObject toJsonObject(String jsonString) {
      if (!TextUtils.isEmpty(jsonString)) {
         try {
            JSONObject jsonObject = new JSONObject(jsonString);
            if (jsonObject.length() > 0) {
               return jsonObject;
            }
         } catch (JSONException e) {
            
         }
      }
      return null;
   }
   
}
