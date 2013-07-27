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
import com.osastudio.newshub.data.NewsArticle;

public class NewsArticleApi extends NewsBaseApi {
   
   private static final String TAG = "NewsArticleApi";
   
   private static final String KEY_NEWS_ARTICLE_ID = "serviceID";
   
   @Deprecated
   public static NewsArticle getNewsArticle(Context context, String newsArticleId) {
      return getNewsArticle(context, new NewsAbstract().setArticleId(newsArticleId));
   }
   
   public static NewsArticle getNewsArticle(Context context, NewsAbstract newsAbstract) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      params.add(new BasicNameValuePair(KEY_NEWS_ARTICLE_ID, newsAbstract.getArticleId()));
      String jsonString = getString(getNewsArticleServiceUrl(), params);
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
      
      NewsArticle result = new NewsArticle(jsonObject);
      if (result != null) {
         result.setAbstract(newsAbstract);
      }
      return result;
   }

}
