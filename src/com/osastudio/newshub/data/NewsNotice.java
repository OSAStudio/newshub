package com.osastudio.newshub.data;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsBaseTimestampTitle;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * News notice information including id, title, publish time etc.
 * 
 * @author Rujin Xue
 * 
 */
public class NewsNotice extends NewsBaseTimestampTitle implements Parcelable {

   public static final String JSON_KEY_ID = "notify_id";
   public static final String JSON_KEY_TITLE = "notify_title";
   public static final String JSON_KEY_PUBLISHED_TIME = "send_date";

   public NewsNotice() {
      super();
   }

   public NewsNotice(Parcel src) {
      super(src);
   }

   public static NewsNotice parseJsonObject(JSONObject jsonObject) {
      NewsNotice result = new NewsNotice();
      try {
         if (!jsonObject.isNull(JSON_KEY_ID)) {
            result.setId(jsonObject.getString(JSON_KEY_ID).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_TITLE)) {
            result.setTitle(jsonObject.getString(JSON_KEY_TITLE).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_PUBLISHED_TIME)) {
            result.setPublishedTime(jsonObject.getString(
                  JSON_KEY_PUBLISHED_TIME).trim());
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

   public static final Parcelable.Creator<NewsNotice> CREATOR = new Creator<NewsNotice>() {
      @Override
      public NewsNotice createFromParcel(Parcel src) {
         return new NewsNotice(src);
      }

      @Override
      public NewsNotice[] newArray(int size) {
         return new NewsNotice[size];
      }
   };

}
