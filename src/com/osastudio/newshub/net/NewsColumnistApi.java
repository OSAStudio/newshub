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

   private static final String KEY_NEWS_COLUMNIST_ID = "expertID";

   protected static String getNewsColumnistListService(Context context) {
      return new StringBuilder(getWebServer(context)).append(
            "expert!getExpertListByMobile.do").toString();
   }

   protected static String getNewsColumnistInfoService(Context context) {
      return new StringBuilder(getWebServer(context)).append(
            "expert!getExpertContentByMobile.do").toString();
   }

   /**
    * Get news columnist brief list by user id
    * 
    * @param context
    *           application context
    * @param userId
    *           user identifier
    * @return news columnist, or null if failed
    */
   public static NewsColumnistList getNewsColumnistList(Context context,
         String userId) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      JSONObject jsonObject = getJsonObject(context,
            getNewsColumnistListService(context), params);
      return (jsonObject != null) ? new NewsColumnistList(jsonObject) : null;
   }

   /**
    * Get news columnist detail information by specific columnist brief
    * 
    * @param context
    *           application context
    * @param userId
    *           user identifier
    * @param newsColumnist
    *           brief of news columnist
    * @return news columnist detail information, or null if failed
    */
   public static NewsColumnistInfo getNewsColumnistInfo(Context context,
         String userId, NewsColumnist newsColumnist) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      params.add(new BasicNameValuePair(KEY_NEWS_COLUMNIST_ID, newsColumnist
            .getId()));
      JSONObject jsonObject = getJsonObject(context,
            getNewsColumnistInfoService(context), params);
      if (jsonObject == null) {
         return null;
      }

      NewsColumnistInfo result = new NewsColumnistInfo(jsonObject);
      // result.setColumnist(newsColumnist);
      result.setId(newsColumnist.getId());
      return result;
   }

   public static NewsColumnistInfo getNewsColumnistInfo(Context context,
         String userId, String columnistId) {
      NewsColumnist columnist = new NewsColumnist();
      columnist.setId(columnistId);
      return getNewsColumnistInfo(context, userId, columnist);
   }

}
