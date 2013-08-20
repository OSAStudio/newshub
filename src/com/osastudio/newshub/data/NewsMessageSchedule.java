package com.osastudio.newshub.data;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.osastudio.newshub.data.base.NewsBaseObject;
import com.osastudio.newshub.utils.Utils;

public class NewsMessageSchedule extends NewsBaseObject {

   public static final String JSON_KEY_SCHEDULE = "list";
   private static final String JSON_KEY_OFFSET_MILLIS = "sendTime";

   private static final String SEPARATOR = ",";
   private static final int MAX_COUNT = 3;

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
      Calendar today = GregorianCalendar.getInstance();
      long now = today.getTimeInMillis();
      today.set(Calendar.HOUR_OF_DAY, 0);
      today.set(Calendar.MINUTE, 0);
      today.set(Calendar.SECOND, 0);
      today.set(Calendar.MILLISECOND, 0);
      this.baseMillis = today.getTimeInMillis();
      return this.baseMillis + this.offsetMillis - now;
   }
   
   public long getScheduleMillis() {
      return this.baseMillis + this.offsetMillis;
   }
   
   public boolean pullNow() {
      return getRemainingMillis() == 0;
   }
   
   public boolean isToday() {
      if (this.baseMillis > 0) {
         Calendar today = GregorianCalendar.getInstance();
         today.set(Calendar.HOUR_OF_DAY, 0);
         today.set(Calendar.MINUTE, 0);
         today.set(Calendar.SECOND, 0);
         today.set(Calendar.MILLISECOND, 0);
         Calendar date = GregorianCalendar.getInstance();
         date.setTimeInMillis(this.baseMillis);
         return date.compareTo(today) == 0;
      }
      return false;
   }
   
   public boolean isPullingLate() {
      return System.currentTimeMillis() <= this.baseMillis + this.offsetMillis;
   }
   
   public boolean isPullingCountExceeded() {
      return this.count >= MAX_COUNT;
   }

   public boolean isPullingAllowed() {
      return !isPullingCountExceeded() && isToday() && !isPullingLate();
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
      if (arr != null && arr.length == MAX_COUNT) {
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
