package com.osastudio.newshub.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

import com.osastudio.newshub.data.NewsMessageList;
import com.osastudio.newshub.data.NewsMessageSchedule;

public class NewsMessageApi extends NewsBaseApi {

   private static final String TAG = "NewsMessageApi";

   public static NewsMessageSchedule getNewsMessageSchedule(Context context,
         String userId) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      JSONObject jsonObject = getJsonObject(getNewsMessageScheduleService(),
            params);
      return (jsonObject != null) ? new NewsMessageSchedule(jsonObject) : null;
   }

   public static NewsMessageList getNewsMessageList(Context context,
         String userId) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      JSONObject jsonObject = getJsonObject(getNewsMessageListService(), params);
      return (jsonObject != null) ? new NewsMessageList(jsonObject) : null;
   }

}
