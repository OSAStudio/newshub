package com.osastudio.newshub.data;

import android.text.TextUtils;

public class NewsBaseObject {

   protected static final String JSON_KEY_RESULT_DESC = "msg";
   protected static final String JSON_KEY_RESULT_CODE = "stat";

   protected static final String RESULT_OK = "1";

   public static boolean isResultSuccess(final String resultCode) {
      return !TextUtils.isEmpty(resultCode) && RESULT_OK.equals(resultCode);
   }

   public static boolean isResultFailure(final String resultCode) {
      return !isResultSuccess(resultCode);
   }

   public static int parseColorValue(final String hexString) {
      if (!TextUtils.isEmpty(hexString)) {
         try {
            return hexString.startsWith("#") ? Integer.decode(hexString)
                  : Integer.decode("#FF".concat(hexString));
         } catch (NumberFormatException e) {
            // e.printStackTrace();
         }
      }
      return getDefaultColor();
   }
   
   public static int getDefaultColor() {
      return 0x00000000;
   }

}
