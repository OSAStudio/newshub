package com.osastudio.newshub.data;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.osastudio.newshub.data.base.NewsBaseObject;
import com.osastudio.newshub.utils.Utils;

public class NewsMessageSchedule extends NewsBaseObject {

   private static final String TAG = "NewsMessageSchedule";

   protected static final String JSON_KEY_OFFSET_MILLIS = "sendTime";

   protected static final String SEPARATOR = ",";
   public static final int MAX_PULLING_COUNT = 3;
   protected static final int PULLING_TIME_TOLERANCE = 10; // millisecond

   private long baseMillis = 0;
   private int count = 0;
   private long offsetMillis = 0;
   private String userId;

   public NewsMessageSchedule() {
      super();
   }

   public static NewsMessageSchedule parseJsonObject(JSONObject jsonObject) {
      NewsMessageSchedule result = new NewsMessageSchedule();
      try {
         if (!jsonObject.isNull(JSON_KEY_OFFSET_MILLIS)) {
            result.setOffsetMillis(jsonObject.getLong(JSON_KEY_OFFSET_MILLIS));
         }
         if (!jsonObject.isNull(JSON_KEY_USER_ID)) {
            result.setUserId(jsonObject.getString(JSON_KEY_USER_ID).trim());
         }
      } catch (JSONException e) {

      }
      return result;
   }

   public long getBaseMillis() {
      return this.baseMillis;
   }

   public NewsMessageSchedule setBaseMillis(long millis) {
      this.baseMillis = toBaseMillis(millis);
      return this;
   }

   public int getCount() {
      return this.count;
   }

   public NewsMessageSchedule setCount(int count) {
      this.count = count;
      return this;
   }

   public long getOffsetMillis() {
      return this.offsetMillis;
   }

   public NewsMessageSchedule setOffsetMillis(long millis) {
      this.offsetMillis = millis;
      return this;
   }

   public String getUserId() {
      return this.userId;
   }

   public NewsMessageSchedule setUserId(String userId) {
      this.userId = userId;
      return this;
   }

   public long getRemainingMillis() {
      return this.baseMillis + this.offsetMillis - System.currentTimeMillis();
   }

   public long getScheduleMillis() {
      return this.baseMillis + this.offsetMillis;
   }

   public boolean pullNow() {
      // return Math.abs(getRemainingMillis()) <= PULLING_TIME_TOLERANCE;
      return getRemainingMillis() <= PULLING_TIME_TOLERANCE;
   }

   public boolean isToday() {
      if (this.baseMillis > 0) {
         Calendar today = GregorianCalendar.getInstance();
         today.set(Calendar.HOUR_OF_DAY, 0);
         today.set(Calendar.MINUTE, 0);
         today.set(Calendar.SECOND, 0);
         today.set(Calendar.MILLISECOND, 0);
         Calendar scheduleDate = GregorianCalendar.getInstance();
         scheduleDate.setTimeInMillis(this.baseMillis);
         return scheduleDate.compareTo(today) == 0;
      }
      return false;
   }

   public boolean isPullingLate() {
//      return getRemainingMillis() + PULLING_TIME_TOLERANCE < 0;
      return false;
   }

   public boolean hasPullingCountExceeded() {
      return this.count >= MAX_PULLING_COUNT;
   }

   public boolean allowPulling() {
      return isToday() && !hasPullingCountExceeded() && !isPullingLate();
   }

   public boolean hasNotifiedToday() {
      return isToday() && hasPullingCountExceeded();
   }

   @Override
   public String toString() {
      return new StringBuilder().append(this.baseMillis).append(SEPARATOR)
            .append(this.offsetMillis).append(SEPARATOR).append(this.count)
            .toString();
   }

   public static NewsMessageSchedule parseString(String str) {
      NewsMessageSchedule result = null;
      String[] arr = str.split(SEPARATOR);
      if (arr != null && arr.length == 3) {
         result = new NewsMessageSchedule();
         try {
            if (!TextUtils.isEmpty(arr[0])) {
               Utils.log(TAG, "parseString: baseMillis=" + arr[0]);
               result.setBaseMillis(Long.valueOf(arr[0]));
            }
            if (!TextUtils.isEmpty(arr[1])) {
               Utils.log(TAG, "parseString: offsetMillis=" + arr[1]);
               result.setOffsetMillis(Long.valueOf(arr[1]));
            }
            if (!TextUtils.isEmpty(arr[2])) {
               Utils.log(TAG, "parseString: count=" + arr[2]);
               result.setCount(Integer.valueOf(arr[2]));
            }
         } catch (NumberFormatException e) {
            e.printStackTrace();
         }
      }
      return result;
   }

   public static long toBaseMillis(long millis) {
      Calendar date = GregorianCalendar.getInstance();
      date.setTimeInMillis(millis);
      date.set(Calendar.HOUR_OF_DAY, 0);
      date.set(Calendar.MINUTE, 0);
      date.set(Calendar.SECOND, 0);
      date.set(Calendar.MILLISECOND, 0);
      return date.getTimeInMillis();
   }

}
