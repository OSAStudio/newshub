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
import com.osastudio.newshub.data.NoticeResult;
import com.osastudio.newshub.data.SubscribedNewsAbstract;
import com.osastudio.newshub.data.SubscribedNewsAbstractList;
import com.osastudio.newshub.data.SubscribedNewsArticle;
import com.osastudio.newshub.data.SubscribedNewsTopic;
import com.osastudio.newshub.data.SubscribedNewsTopicList;

public class SubscribedNewsApi extends NewsBaseApi {

   private static final String TAG = "SubscribedNewsApi";

   private static final String KEY_NEWS_TOPIC_ID = "recommendLssueID";

   public static SubscribedNewsTopicList getSubscribedNewsTopicList(
         Context context, String userId) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      JSONObject jsonObject = getJsonObject(
            getSubscribedNewsTopicListService(), params);
      return (jsonObject != null) ? new SubscribedNewsTopicList(jsonObject)
            : null;
   }

   public static SubscribedNewsAbstractList getSubscribedNewsaAbstractList(
         Context context, String userId, SubscribedNewsTopic newsTopic) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      params.add(new BasicNameValuePair(KEY_NEWS_TOPIC_ID, newsTopic.getId()));
      JSONObject jsonObject = getJsonObject(
            getSubscribedNewsAbstractListService(), params);
      return (jsonObject != null) ? new SubscribedNewsAbstractList(jsonObject)
            : null;
   }

   public static SubscribedNewsArticle getSubscribedNewsArticle(
         Context context, String userId, SubscribedNewsAbstract newsAbstract) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      params.add(new BasicNameValuePair(KEY_NEWS_TOPIC_ID, newsAbstract
            .getTopicId()));
      JSONObject jsonObject = getJsonObject(getSubscribedNewsArticleService(),
            params);
      if (jsonObject == null) {
         return null;
      }

      SubscribedNewsArticle result = new SubscribedNewsArticle(jsonObject);
      if (result != null) {
         result.setNewsBaseTopicAbstract(newsAbstract);
      }
      return result;
   }

}
