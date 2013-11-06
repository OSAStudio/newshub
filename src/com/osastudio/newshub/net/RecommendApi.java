package com.osastudio.newshub.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

import com.osastudio.newshub.data.NewsResult;
import com.osastudio.newshub.data.RecommendedTopic;
import com.osastudio.newshub.data.RecommendedTopicIntro;
import com.osastudio.newshub.data.RecommendedTopicList;

public class RecommendApi extends NewsBaseApi {

   private static final String KEY_NEWS_TOPIC_ID = "recommendLssueID";

   protected static String getRecommendedTopicListService(Context context) {
      return new StringBuilder(getWebServer(context)).append(
            "expandlssue!getRecommendLssueListByMobile.do").toString();
   }

   protected static String getRecommendedTopicIntroService(Context context) {
      return new StringBuilder(getWebServer(context)).append(
            "expandlssue!getRecommendLssueContentByMobile.do").toString();
   }

   protected static String subscribeRecommendedTopicService(Context context) {
      return new StringBuilder(getWebServer(context)).append(
            "expandlssue!submitExpandLssueByMobile.do").toString();
   }

   /**
    * Get recommended topic list by user id
    * 
    * @param context
    *           application context
    * @param userId
    *           user identifier
    * @return recommended topic list, or null if failed
    */
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

   /**
    * Get recommended topic introduction by topic & user id
    * 
    * @param context
    *           application context
    * @param userId
    *           user identifier
    * @param newsTopic
    *           recommended news topic
    * @return recommended topic introduction, or null if failed
    */
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

   /**
    * Subscribe recommended topic by topic id
    * 
    * @param context
    *           application context
    * @param userId
    *           user identifier
    * @param topicId
    *           recommended topic id
    * @return news result including result code & description, or null if failed
    */
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
