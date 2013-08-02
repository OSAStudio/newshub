package com.osastudio.newshub.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

import com.osastudio.newshub.data.DailyReminder;
import com.osastudio.newshub.data.DailyReminderArticle;
import com.osastudio.newshub.data.NewsNoticeList;

public class DailyReminderApi extends NewsBaseApi {

   private static final String TAG = "DailyReminderApi";

   private static final String KEY_DAILY_REMINDER_ID = "dailyReminderID";

   public static NewsNoticeList getDailyReminderList(Context context,
         String userId) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      JSONObject jsonObject = getJsonObject(getDailyReminderListService(),
            params);
      return (jsonObject != null) ? new NewsNoticeList(jsonObject) : null;
   }

   public static DailyReminderArticle getDailyReminderArticle(Context context,
         String userId, DailyReminder reminder) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      params.add(new BasicNameValuePair(KEY_DAILY_REMINDER_ID, reminder.getId()));
      JSONObject jsonObject = getJsonObject(getDailyReminderArticleService(),
            params);
      if (jsonObject == null) {
         return null;
      }

      DailyReminderArticle result = new DailyReminderArticle(jsonObject);
      if (result != null) {
         result.setNewsBaseTitle(reminder);
      }
      return result;
   }

}
