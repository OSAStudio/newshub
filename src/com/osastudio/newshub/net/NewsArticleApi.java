package com.osastudio.newshub.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

import com.osastudio.newshub.data.NewsArticle;
import com.osastudio.newshub.data.NewsChannelList;

public class NewsArticleApi extends NewsBaseApi {
   
   private static final String TAG = "NewsArticleApi";
   
   private static final String KEY_NEWS_ARTICLE_ID = "serviceID";
   
   public static NewsArticle getNewsArticle(Context context, String newsArticleId) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, "1234567890"));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, DEVICE_TYPE));
      params.add(new BasicNameValuePair(KEY_NEWS_ARTICLE_ID, newsArticleId));
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
      
      return NewsArticle.parseJsonObject(jsonObject);
   }

}
