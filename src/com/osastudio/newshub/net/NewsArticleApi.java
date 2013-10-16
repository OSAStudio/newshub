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

   private static final String KEY_NEWS_ARTICLE_ID = "serviceID";

   protected static String getNewsArticleService(Context context) {
      return new StringBuilder(getWebServer(context)).append(
            "lesson!getLessonContentByMobile.do").toString();
   }

   protected static String likeArticleService(Context context) {
      return new StringBuilder(getWebServer(context)).append(
            "lesson!submitLessonUPByMobile.do").toString();
   }

   public static NewsArticle getNewsArticle(Context context, String userId,
         String articleId) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      params.add(new BasicNameValuePair(KEY_NEWS_ARTICLE_ID, articleId));
      JSONObject jsonObject = getJsonObject(context,
            getNewsArticleService(context), params);
      if (jsonObject == null) {
         return null;
      }

      NewsArticle result = new NewsArticle(jsonObject);
      result.setId(articleId);
      return result;
   }

   public static NewsArticle getNewsArticle(Context context, String userId,
         NewsAbstract newsAbstract) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      params.add(new BasicNameValuePair(KEY_NEWS_ARTICLE_ID, newsAbstract
            .getId()));
      JSONObject jsonObject = getJsonObject(context,
            getNewsArticleService(context), params);
      if (jsonObject == null) {
         return null;
      }

      NewsArticle result = new NewsArticle(jsonObject);
      result.setNewsBaseAbstract(newsAbstract);
      return result;
   }

   public static NewsResult likeArticle(Context context, String articleId) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair("lessonID", articleId));
      JSONObject jsonObject = getJsonObject(context,
            likeArticleService(context), params);
      return (jsonObject != null) ? new NewsResult(jsonObject) : null;
   }

}
