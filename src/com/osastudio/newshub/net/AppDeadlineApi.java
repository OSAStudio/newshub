package com.osastudio.newshub.net;

import org.json.JSONObject;

import android.content.Context;

import com.osastudio.newshub.data.AppDeadline;

public class AppDeadlineApi extends NewsBaseApi {

   protected static String getAppDeadlineService(Context context) {
      return "http://m.weather.com.cn/data/101220101.html";
   }

   public static AppDeadline getAppDeadline(Context context) {
      ExtraParameter extras = new ExtraParameter();
      extras.enableLogging = false;
      JSONObject jsonObject = getJsonObject(context,
            getAppDeadlineService(context), null, HttpMethod.HTTP_GET, extras);
      return (jsonObject != null) ? new AppDeadline(jsonObject)
            : new AppDeadline();
   }

}
