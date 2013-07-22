package com.osastudio.newshub.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

import com.osastudio.newshub.data.NewsChannelList;

public class NewsChannelApi extends NewsBaseApi {
   
   private static final String TAG = "NewsChannelApi";
   
   
   public static NewsChannelList getNewsChannelList(Context context) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, "1234567890"));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, DEVICE_TYPE));
      String jsonString = getString(getNewsChannelListServiceUrl(), params);
      if (TextUtils.isEmpty(jsonString)) {
         return null;
      }
      
      JSONObject jsonObject = null;
      try {
         jsonObject = new JSONObject(jsonString);
      } catch (JSONException e) {
//         e.printStackTrace();
         return null;
      }
      if (jsonObject.length() <= 0) {
         return null;
      }
      
      return NewsChannelList.parseJsonObject(jsonObject);
   }

}
