package com.osastudio.newshub.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

import com.osastudio.newshub.NewsApp;
import com.osastudio.newshub.data.NewsResult;
import com.osastudio.newshub.data.RecommendedTopic;
import com.osastudio.newshub.data.RecommendedTopicIntro;
import com.osastudio.newshub.data.RecommendedTopicList;

public class RecommendApi extends NewsBaseApi {

   private static final String TAG = "RecommendedNewsApi";

   private static final String KEY_NEWS_TOPIC_ID = "recommendLssueID";

   public static RecommendedTopicList getRecommendedTopicList(Context context,
         String userId) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      JSONObject jsonObject = getJsonObject(context,
            getRecommendedTopicListService(context), params);
      return (jsonObject != null) ? new RecommendedTopicList(jsonObject) : null;
   }

   public static RecommendedTopicIntro getRecommendedTopicIntro(
         Context context, String userId, RecommendedTopic newsTopic) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      params.add(new BasicNameValuePair(KEY_NEWS_TOPIC_ID, newsTopic.getId()));
      JSONObject jsonObject = getJsonObject(context,
            getRecommendedTopicIntroService(context), params);
      if (jsonObject == null) {
         return null;
      }

      RecommendedTopicIntro result = new RecommendedTopicIntro(jsonObject);
      // result.setNewsBaseTopic(newsTopic);
      result.setId(newsTopic.getId());
      return result;
   }

   @Deprecated
   public static RecommendedTopicIntro getRecommendedTopicIntro(
         Context context, String userId, String topicId) {
      RecommendedTopic topic = new RecommendedTopic();
      topic.setId(topicId);
      return getRecommendedTopicIntro(context, userId, topic);
   }

   public static NewsResult subscribeRecommendedTopic(Context context,
         String userId, String topicId) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      params.add(new BasicNameValuePair(KEY_NEWS_TOPIC_ID, topicId));
      JSONObject jsonObject = getJsonObject(context,
            subscribeRecommendedTopicService(context), params);
      return (jsonObject != null) ? new NewsResult(jsonObject) : null;
   }

}
