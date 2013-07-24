package com.osastudio.newshub.data;

import android.text.TextUtils;

public class NewsBaseObject {

   protected static final String JSON_KEY_RESULT_DESC = "msg";
   protected static final String JSON_KEY_RESULT_CODE = "stat";

   protected static final String RESULT_OK = "1";

   protected static boolean isResultSuccess(final String resultCode) {
      return !TextUtils.isEmpty(resultCode) && RESULT_OK.equals(resultCode);
   }

   protected static boolean isResultFailure(final String resultCode) {
      return !isResultSuccess(resultCode);
   }

   public static int parseColorValue(final String hexString) {
      if (!TextUtils.isEmpty(hexString)) {
         try {
            int result = hexString.startsWith("#") ? Integer.decode(hexString)
                  : Integer.decode("#".concat(hexString));
            return (result & 0xFFFFFF) | 0x80000000;
         } catch (NumberFormatException e) {
             e.printStackTrace();
         }
      }
      return getDefaultColor();
   }
   
   protected static int getDefaultColor() {
      return 0x80000000;
   }

}
