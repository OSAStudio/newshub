package com.osastudio.newshub.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

import com.osastudio.newshub.data.DailyReminderList;

public class DailyReminderApi extends NewsBaseApi {

   private static final String TAG = "DailyReminderApi";

   public static DailyReminderList getDailyReminderList(Context context,
         String userId) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      JSONObject jsonObject = getJsonObject(context,
            getDailyReminderListService(context), params);
      return (jsonObject != null) ? new DailyReminderList(jsonObject) : null;
   }

}
