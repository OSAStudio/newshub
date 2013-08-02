package com.osastudio.newshub.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

import com.osastudio.newshub.data.NewsAbstract;
import com.osastudio.newshub.data.NewsArticle;
import com.osastudio.newshub.data.NewsResult;

public class NewsArticleApi extends NewsBaseApi {

   private static final String TAG = "NewsArticleApi";

   private static final String KEY_NEWS_ARTICLE_ID = "serviceID";

   public static NewsArticle getNewsArticle(Context context,
         NewsAbstract newsAbstract) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      params.add(new BasicNameValuePair(KEY_NEWS_ARTICLE_ID, newsAbstract
            .getId()));
      JSONObject jsonObject = getJsonObject(getNewsArticleService(), params);
      if (jsonObject == null) {
         return null;
      }

      NewsArticle result = new NewsArticle(jsonObject);
      if (result != null) {
         result.setNewsBaseAbstract(newsAbstract);
      }
      return result;
   }

   public static NewsResult likeArticle(Context context, String articleId) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_NEWS_ARTICLE_ID, articleId));
      JSONObject jsonObject = getJsonObject(likeArticleService(), params);
      return (jsonObject != null) ? new NewsResult(jsonObject) : null;
   }

}
