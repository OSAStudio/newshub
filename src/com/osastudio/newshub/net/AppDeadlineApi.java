package com.osastudio.newshub.net;

import org.json.JSONObject;

import android.content.Context;

import com.osastudio.newshub.data.AppDeadline;

public class AppDeadlineApi extends NewsBaseApi {

   public static AppDeadline getAppDeadline(Context context) {
      JSONObject jsonObject = getJsonObject(getAppDeadlineService(), null,
            HttpMethod.HTTP_GET);
      return (jsonObject != null) ? new AppDeadline(jsonObject)
            : new AppDeadline();
   }

}
