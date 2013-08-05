package com.osastudio.newshub.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

import com.osastudio.newshub.data.NewsChannelList;

public class NewsChannelApi extends NewsBaseApi {

   private static final String TAG = "NewsChannelApi";

   public static NewsChannelList getNewsChannelList(Context context, String userId) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      JSONObject jsonObject = getJsonObject(getNewsChannelListService(),
            params);
      return (jsonObject != null) ? new NewsChannelList(jsonObject) : null;
   }

}
