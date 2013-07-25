package com.osastudio.newshub.data;

import android.text.TextUtils;

public class NewsBaseObject implements NewsResult {

   protected static final String JSON_KEY_RESULT_DESC = "msg";
   protected static final String JSON_KEY_RESULT_CODE = "stat";
   
   protected static final int DEFAULT_COLOR = 0xAA000000;
   

   protected static boolean isResultSuccess(final String resultCode) {
      try {
         if (!TextUtils.isEmpty(resultCode) 
               && Integer.parseInt(resultCode) == RESULT_OK) {
            return true;
         }
      } catch (NumberFormatException e) {
         
      }
      return false;
   }

   protected static boolean isResultFailure(final String resultCode) {
      return !isResultSuccess(resultCode);
   }

   public static int parseColorValue(final String hexString) {
      if (!TextUtils.isEmpty(hexString)) {
         try {
            int result = hexString.startsWith("#") ? Integer.decode(hexString)
                  : Integer.decode("#".concat(hexString));
            return (result & 0xFFFFFF) | 0xAA000000;
         } catch (NumberFormatException e) {
             e.printStackTrace();
         }
      }
      return getDefaultColor();
   }
   
   protected static int getDefaultColor() {
      return 0xAA000000;
   }

}
