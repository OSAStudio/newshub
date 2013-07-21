package com.osastudio.newshub.utils;

import android.util.Log;

public class Utils {

   private static final String APP_NAME = "NewsHub";

   private static final boolean DEBUG = true;
   
   
   public static void log(String tag, String info) {
      logi(tag, info);
   }

   public static void logd(String tag, String info) {
      if (DEBUG) {
         Log.d(APP_NAME + ">>>>>>>>>>" + tag, "-------->" + info);
      }
   }

   public static void loge(String tag, String info) {
      if (DEBUG) {
         Log.e(APP_NAME + ">>>>>>>>>>" + tag, "-------->" + info);
      }
   }

   public static void logi(String tag, String info) {
      if (DEBUG) {
         Log.i(APP_NAME + ">>>>>>>>>>" + tag, "-------->" + info);
      }
   }

   public static void logv(String tag, String info) {
      if (DEBUG) {
         Log.v(APP_NAME + ">>>>>>>>>>" + tag, "-------->" + info);
      }
   }

   public static void logw(String tag, String info) {
      if (DEBUG) {
         Log.w(APP_NAME + ">>>>>>>>>>" + tag, "-------->" + info);
      }
   }
   
}
