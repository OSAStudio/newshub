package com.osastudio.newshub.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

import com.osastudio.newshub.data.RecommendedNewsTopic;
import com.osastudio.newshub.data.RecommendedNewsTopicIntroduction;
import com.osastudio.newshub.data.RecommendedNewsTopicList;

public class RecommendedNewsApi extends NewsBaseApi {

   private static final String TAG = "RecommendedNewsApi";

   private static final String KEY_NEWS_TOPIC_ID = "recommendLssueID";

   public static RecommendedNewsTopicList getRecommendedNewsTopicList(
         Context context, String userId) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      JSONObject jsonObject = getJsonObject(
            getRecommendedNewsTopicListService(), params);
      return (jsonObject != null) ? new RecommendedNewsTopicList(jsonObject)
            : null;
   }

   public static RecommendedNewsTopicIntroduction getNewsNoticeArticle(
         Context context, String userId, RecommendedNewsTopic newsTopic) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      params.add(new BasicNameValuePair(KEY_NEWS_TOPIC_ID, newsTopic.getId()));
      JSONObject jsonObject = getJsonObject(getRecommendedNewsArticleService(),
            params);
      if (jsonObject == null) {
         return null;
      }

      RecommendedNewsTopicIntroduction result = new RecommendedNewsTopicIntroduction(
            jsonObject);
      if (result != null) {
         result.setNewsBaseTopic(newsTopic);
      }
      return result;
   }

}
