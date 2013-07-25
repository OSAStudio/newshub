package com.osastudio.newshub.data;

public class NewsResult implements ResultCode {
   
   private int resultCode = RESULT_UNKNOWN_ERROR;

   public NewsResult() {
      
   }
   
   public NewsResult(String resultString) {
      try {
         this.resultCode = Integer.parseInt(resultString);
      } catch (NumberFormatException e) {
         
      }
   }
   
   public NewsResult(int resultCode) {
      this.resultCode = resultCode;
   }

   public int getResultCode() {
      return this.resultCode;
   }

   public void setResultCode(int resultCode) {
      this.resultCode = resultCode;
   }
   
   public boolean isSuccess() {
      return this.resultCode == RESULT_OK;
   }
   
   public boolean isFailure() {
      return !isSuccess();
   }
   
}
