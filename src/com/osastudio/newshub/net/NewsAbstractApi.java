package com.osastudio.newshub.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

import com.osastudio.newshub.data.NewsAbstract;
import com.osastudio.newshub.data.NewsAbstractList;

public class NewsAbstractApi extends NewsBaseApi {

   private static final String KEY_NEWS_CHANNEL_ID = "serviceID";

   protected static String getNewsAbstractListService(Context context) {
      return new StringBuilder(getWebServer(context)).append(
            "lesson!getLessonListByMobile.do").toString();
   }

   public static NewsAbstractList getNewsAbstractList(Context context,
         String userId, String newsChannelId) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      params.add(new BasicNameValuePair(KEY_NEWS_CHANNEL_ID, newsChannelId));
      JSONObject jsonObject = getJsonObject(context,
            getNewsAbstractListService(context), params);
      if (jsonObject == null) {
         return null;
      }

      NewsAbstractList result = new NewsAbstractList(jsonObject);
      if (result.getAbstractList().size() > 0) {
         for (NewsAbstract abs : result.getAbstractList()) {
            if (abs != null) {
               abs.setChannelId(newsChannelId);
            }
         }
         getNewsAbstractCache(context).setNewsAbstractList(result);
         // getNewsBaseAbstractCache(context).setList(result.getList());
      }
      return result;
   }

}
