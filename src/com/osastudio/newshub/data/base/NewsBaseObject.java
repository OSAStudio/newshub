package com.osastudio.newshub.data.base;

import org.json.JSONObject;

import com.osastudio.newshub.data.NewsResult;
import com.osastudio.newshub.data.ResultCode;

import android.text.TextUtils;

public class NewsBaseObject extends NewsResult implements ResultCode {

   protected static final int DEFAULT_ALPHA = 0xAA;
   protected static final int DEFAULT_COLOR = 0x000000 | (DEFAULT_ALPHA << 24);

   public NewsBaseObject() {
      super();
   }

   public NewsBaseObject(JSONObject jsonObject) {
      super(jsonObject);
   }

   public static int parseColorValue(final String hexString) {
      if (!TextUtils.isEmpty(hexString)) {
         try {
            int result = hexString.startsWith("#") ? Integer.decode(hexString)
                  : Integer.decode("#".concat(hexString));
            return (result & 0xFFFFFF) | (DEFAULT_ALPHA << 24);
         } catch (NumberFormatException e) {
            e.printStackTrace();
         }
      }
      return getDefaultColor();
   }

   protected static int getDefaultColor() {
      return DEFAULT_COLOR;
   }

}
