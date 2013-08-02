package com.osastudio.newshub.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

import com.osastudio.newshub.data.NewsColumnist;
import com.osastudio.newshub.data.NewsColumnistInfo;
import com.osastudio.newshub.data.NewsColumnistList;

public class NewsColumnistApi extends NewsBaseApi {

   private static final String TAG = "NewsColumnistApi";

   private static final String KEY_NEWS_COLUMNIST_ID = "expertID";

   public static NewsColumnistList getNewsColumnistList(Context context,
         String userId) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      JSONObject jsonObject = getJsonObject(getNewsColumnistListService(),
            params);
      return (jsonObject != null) ? new NewsColumnistList(jsonObject) : null;
   }

   public static NewsColumnistInfo getNewsColumnistInfo(Context context,
         String userId, NewsColumnist newsColumnist) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      params.add(new BasicNameValuePair(KEY_NEWS_COLUMNIST_ID, newsColumnist
            .getId()));
      JSONObject jsonObject = getJsonObject(getNewsColumnistInfoService(),
            params);
      if (jsonObject == null) {
         return null;
      }

      NewsColumnistInfo result = new NewsColumnistInfo(jsonObject);
      if (result != null) {
         result.setColumnist(newsColumnist);
      }
      return result;
   }

}