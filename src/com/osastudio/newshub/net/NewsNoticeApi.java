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

public class NewsNoticeApi extends NewsBaseApi {

   private static final String KEY_NEWS_NOTICE_ID = "notifyID";

   protected static String getNewsNoticeListService(Context context) {
      return new StringBuilder(getWebServer(context)).append(
            "notify!getNotifyListByMobile.do").toString();
   }

   protected static String getNewsNoticeArticleService(Context context) {
      return new StringBuilder(getWebServer(context)).append(
            "notify!getNotifyContentByMobile.do").toString();
   }

   protected static String feedbackNoticeService(Context context) {
      return new StringBuilder(getWebServer(context)).append(
            "notify!submitNotifyFeedbackByMobile.do").toString();
   }

   /**
    * Get news notice information list by user id
    * 
    * @param context
    *           application context
    * @param userId
    *           user identifier
    * @return news notice list, or null if failed
    */
   public static NewsNoticeList getNewsNoticeList(Context context, String userId) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      JSONObject jsonObject = getJsonObject(context,
            getNewsNoticeListService(context), params);
      return (jsonObject != null) ? new NewsNoticeList(jsonObject) : null;
   }

   /**
    * Get news notice content by news notice information
    * 
    * @param context
    *           application context
    * @param userId
    *           user identifier
    * @param newsNotice
    *           information of an news notice
    * @return news notice content, or null if failed
    */
   public static NewsNoticeArticle getNewsNoticeArticle(Context context,
         String userId, NewsNotice newsNotice) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      params.add(new BasicNameValuePair(KEY_NEWS_NOTICE_ID, newsNotice.getId()));
      JSONObject jsonObject = getJsonObject(context,
            getNewsNoticeArticleService(context), params);
      if (jsonObject == null) {
         return null;
      }

      NewsNoticeArticle result = new NewsNoticeArticle(jsonObject);
      // result.setNewsBaseTitle(newsNotice);
      result.setId(newsNotice.getId());
      return result;
   }

   /**
    * Get news notice content by news notice id
    * 
    * @param context
    *           application context
    * @param userId
    *           user identifier
    * @param noticeId
    *           notice identifier
    * @return news notice content, or null if failed
    */
   public static NewsNoticeArticle getNewsNoticeArticle(Context context,
         String userId, String noticeId) {
      NewsNotice notice = new NewsNotice();
      notice.setId(noticeId);
      return getNewsNoticeArticle(context, userId, notice);
   }

   /**
    * Commit feedback to a notice by notice id
    * 
    * @param context
    *           application context
    * @param noticeId
    *           notice identifier
    * @return notice result including result code & description, or null if
    *         failed
    */
   public static NoticeResult feedbackNotice(Context context, String noticeId) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_NEWS_NOTICE_ID, noticeId));
      JSONObject jsonObject = getJsonObject(context,
            feedbackNoticeService(context), params);
      return (jsonObject != null) ? new NoticeResult(jsonObject) : null;
   }

}
