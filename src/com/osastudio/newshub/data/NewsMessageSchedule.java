package com.osastudio.newshub.data;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.osastudio.newshub.data.base.NewsBaseObject;

public class NewsMessageSchedule extends NewsBaseObject {

   public static final String JSON_KEY_SCHEDULE = "list";
   private static final String JSON_KEY_OFFSET_MILLIS = "sendTime";

   private static final String SEPARATOR = ",";
   private static final int MAX_PULLING_COUNT = 3;
   private static final int PULLING_TIME_TOLERANCE = 10; // millisecond

   private long baseMillis = 0;
   private int count = 0;
   private long offsetMillis = 0;

   public NewsMessageSchedule() {

   }

   public NewsMessageSchedule(JSONObject jsonObject) {
      super(jsonObject);

      if (isSuccess()) {
         try {
            JSONObject bodyObject = null;
            if (!jsonObject.isNull(JSON_KEY_SCHEDULE)) {
               bodyObject = jsonObject.getJSONObject(JSON_KEY_SCHEDULE);
            }
            if (bodyObject == null) {
               return;
            }

            if (!bodyObject.isNull(JSON_KEY_OFFSET_MILLIS)) {
               this.offsetMillis = bodyObject.getLong(JSON_KEY_OFFSET_MILLIS);
            }
         } catch (JSONException e) {

         }
      }
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

   public long getRemainingMillis() {
      return this.baseMillis + this.offsetMillis - System.currentTimeMillis();
   }
   
   public long getScheduleMillis() {
      return this.baseMillis + this.offsetMillis;
   }
   
   public boolean pullNow() {
      return Math.abs(getRemainingMillis()) <= PULLING_TIME_TOLERANCE;
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
      return getRemainingMillis() + PULLING_TIME_TOLERANCE < 0;
   }
   
   public boolean hasPullingCountExceeded() {
      return this.count >= MAX_PULLING_COUNT;
   }

   public boolean allowPulling() {
      return !hasPullingCountExceeded() && isToday() && !isPullingLate();
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
               result.setBaseMillis(Long.valueOf(arr[0]));
            }
            if (!TextUtils.isEmpty(arr[1])) {
               result.setOffsetMillis(Long.valueOf(arr[1]));
            }
            if (!TextUtils.isEmpty(arr[2])) {
               result.setCount(Integer.valueOf(arr[2]));
            }
         } catch (NumberFormatException e) {

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
