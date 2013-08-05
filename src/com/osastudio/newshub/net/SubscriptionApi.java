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
import com.osastudio.newshub.data.SubscriptionAbstract;
import com.osastudio.newshub.data.SubscriptionAbstractList;
import com.osastudio.newshub.data.SubscriptionArticle;
import com.osastudio.newshub.data.SubscriptionTopic;
import com.osastudio.newshub.data.SubscriptionTopicList;

public class SubscriptionApi extends NewsBaseApi {

   private static final String TAG = "SubscribedNewsApi";

   private static final String KEY_NEWS_TOPIC_ID = "recommendLssueID";

   public static SubscriptionTopicList getSubscriptionTopicList(
         Context context, String userId) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      JSONObject jsonObject = getJsonObject(getSubscriptionTopicListService(),
            params);
      return (jsonObject != null) ? new SubscriptionTopicList(jsonObject)
            : null;
   }

   public static SubscriptionAbstractList getSubscriptionAbstractList(
         Context context, String userId, SubscriptionTopic newsTopic) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      params.add(new BasicNameValuePair(KEY_NEWS_TOPIC_ID, newsTopic.getId()));
      JSONObject jsonObject = getJsonObject(
            getSubscriptionAbstractListService(), params);
      return (jsonObject != null) ? new SubscriptionAbstractList(jsonObject)
            : null;
   }
   
   public static SubscriptionAbstractList getSubscriptionAbstractList(
	         Context context, String userId, String issueid) {
	      List<NameValuePair> params = new ArrayList<NameValuePair>();
	      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
	      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
	      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
	      params.add(new BasicNameValuePair(KEY_NEWS_TOPIC_ID, issueid));
	      JSONObject jsonObject = getJsonObject(
	            getSubscriptionAbstractListService(), params);
	      return (jsonObject != null) ? new SubscriptionAbstractList(jsonObject)
	            : null;
	   }

   public static SubscriptionArticle getSubscriptionArticle(Context context,
         String userId, SubscriptionAbstract newsAbstract) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      params.add(new BasicNameValuePair(KEY_NEWS_TOPIC_ID, newsAbstract
            .getTopicId()));
      JSONObject jsonObject = getJsonObject(getSubscriptionArticleService(),
            params);
      if (jsonObject == null) {
         return null;
      }

      SubscriptionArticle result = new SubscriptionArticle(jsonObject);
      if (result != null) {
         result.setNewsBaseTopicAbstract(newsAbstract);
      }
      return result;
   }

}
