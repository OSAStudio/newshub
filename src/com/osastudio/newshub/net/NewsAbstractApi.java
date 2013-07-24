package com.osastudio.newshub.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

import com.osastudio.newshub.data.NewsAbstract;
import com.osastudio.newshub.data.NewsAbstractList;
import com.osastudio.newshub.data.NewsChannelList;

public class NewsAbstractApi extends NewsBaseApi {
   
   private static final String TAG = "NewsAbstractApi";
   
   private static final String KEY_NEWS_CHANNEL_ID = "serviceID";
   
   public static NewsAbstractList getNewsAbstractList(Context context, String newsChannelId) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, "1234567890"));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, DEVICE_TYPE));
      params.add(new BasicNameValuePair(KEY_NEWS_CHANNEL_ID, newsChannelId));
      String jsonString = getString(getNewsAbstractListServiceUrl(), params);
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
      
      NewsAbstractList result = NewsAbstractList.parseJsonObject(jsonObject);
      if (result != null && result.getAbstractList().size() > 0) {
         for (NewsAbstract abs : result.getAbstractList()) {
            if (abs != null) {
               abs.setChannelId(newsChannelId);
            }
         }
         getNewsAbstractCache(context).setNewsAbstractList(result);
      }
      
      return result;
   }

}
