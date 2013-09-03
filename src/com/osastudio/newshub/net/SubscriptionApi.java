package com.osastudio.newshub.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

import com.osastudio.newshub.data.SubscriptionAbstract;
import com.osastudio.newshub.data.SubscriptionAbstractList;
import com.osastudio.newshub.data.SubscriptionArticle;
import com.osastudio.newshub.data.SubscriptionTopic;
import com.osastudio.newshub.data.SubscriptionTopicList;

public class SubscriptionApi extends NewsBaseApi {

   private static final String TAG = "SubscribedNewsApi";

   private static final String KEY_NEWS_TOPIC_ID = "recommendLssueID";
   private static final String KEY_NEWS_ARTICLE_ID = "expandLessonID";

   public static SubscriptionTopicList getSubscriptionTopicList(
         Context context, String userId) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      JSONObject jsonObject = getJsonObject(context,
            getSubscriptionTopicListService(context), params);
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
      JSONObject jsonObject = getJsonObject(context,
            getSubscriptionAbstractListService(context), params);
      if (jsonObject == null) {
         return null;
      }

      SubscriptionAbstractList result = new SubscriptionAbstractList(jsonObject);
      if (result.getList().size() > 0) {
         for (SubscriptionAbstract abs : result.getList()) {
            if (abs != null) {
               abs.setTopicId(newsTopic.getId());
            }
         }
         getSubscriptionAbstractCache(context).setAbstracts(result);
         // getNewsBaseAbstractCache(context).setList(result.getList());
      }
      return result;
   }

   @Deprecated
   public static SubscriptionAbstractList getSubscriptionAbstractList(
         Context context, String userId, String topicId) {
      SubscriptionTopic topic = new SubscriptionTopic();
      topic.setId(topicId);
      return getSubscriptionAbstractList(context, userId, topic);
   }

   public static SubscriptionArticle getSubscriptionArticle(Context context,
         String userId, SubscriptionAbstract newsAbstract) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      params.add(new BasicNameValuePair(KEY_NEWS_ARTICLE_ID, newsAbstract
            .getId()));
      JSONObject jsonObject = getJsonObject(context,
            getSubscriptionArticleService(context), params);
      if (jsonObject == null) {
         return null;
      }

      SubscriptionArticle result = new SubscriptionArticle(jsonObject);
      result.setNewsBaseAbstract(newsAbstract);
      return result;
   }

}
