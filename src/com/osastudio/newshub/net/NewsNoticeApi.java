package com.osastudio.newshub.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

import com.osastudio.newshub.data.NewsNotice;
import com.osastudio.newshub.data.NewsNoticeArticle;
import com.osastudio.newshub.data.NewsNoticeList;

public class NewsNoticeApi extends NewsBaseApi {

   private static final String TAG = "NewsNoticeApi";

   private static final String KEY_NEWS_NOTICE_ID = "notifyID";

   public static NewsNoticeList getNewsNoticeList(Context context, String userId) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      JSONObject jsonObject = getJsonObject(getNewsNoticeListService(), params);
      return (jsonObject != null) ? new NewsNoticeList(jsonObject) : null;
   }

   public static NewsNoticeArticle getNewsNoticeArticle(Context context,
         String userId, NewsNotice newsNotice) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      params.add(new BasicNameValuePair(KEY_NEWS_NOTICE_ID, newsNotice
            .getNoticeId()));
      JSONObject jsonObject = getJsonObject(getNewsNoticeArticleService(), params);
      if (jsonObject == null) {
         return null;
      }

      NewsNoticeArticle result = new NewsNoticeArticle(jsonObject);
      if (result != null) {
         result.setNotice(newsNotice);
      }
      return result;
   }

}