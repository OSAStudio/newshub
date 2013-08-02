package com.osastudio.newshub.data;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class DailyReminder extends NewsBaseTitle implements Parcelable {

   public static final String JSON_KEY_ID = "daily_reminder_id";
   public static final String JSON_KEY_TITLE = "daily_reminder_title";

   public DailyReminder() {
      super();
   }

   public DailyReminder(Parcel src) {
      super(src);
   }

   public static DailyReminder parseJsonObject(JSONObject jsonObject) {
      DailyReminder result = new DailyReminder();
      try {
         if (!jsonObject.isNull(JSON_KEY_ID)) {
            result.setId(jsonObject.getString(JSON_KEY_ID).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_TITLE)) {
            result.setTitle(jsonObject.getString(JSON_KEY_TITLE).trim());
         }
      } catch (JSONException e) {

      }
      return result;
   }

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dst, int flags) {
      super.writeToParcel(dst, flags);
   }

   public static final Parcelable.Creator<DailyReminder> CREATOR = new Creator<DailyReminder>() {
      @Override
      public DailyReminder createFromParcel(Parcel src) {
         return new DailyReminder(src);
      }

      @Override
      public DailyReminder[] newArray(int size) {
         return new DailyReminder[size];
      }
   };

}
