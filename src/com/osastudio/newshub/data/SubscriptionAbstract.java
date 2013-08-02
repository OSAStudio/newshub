package com.osastudio.newshub.data;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsBaseTopicAbstract;

import android.os.Parcel;
import android.os.Parcelable;

public class SubscriptionAbstract extends NewsBaseTopicAbstract implements
      Parcelable {

   public static final String JSON_KEY_ID = "expand_lesson_id";
   public static final String JSON_KEY_COLOR = "expand_lesson_colour";
   public static final String JSON_KEY_PUBLISHED_TIME = "post_date";
   public static final String JSON_KEY_AUTHOR = "expert_name";
   public static final String JSON_KEY_TITLE = "expand_lesson_title";

   public SubscriptionAbstract() {
      super();
   }

   public SubscriptionAbstract(Parcel src) {
      super(src);
   }

   public static SubscriptionAbstract parseJsonObject(JSONObject jsonObject) {
      SubscriptionAbstract result = new SubscriptionAbstract();
      try {
         if (!jsonObject.isNull(JSON_KEY_ID)) {
            result.setId(jsonObject.getString(JSON_KEY_ID).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_COLOR)) {
            result.setColor(SubscriptionAbstract.parseColorValue(jsonObject
                  .getString(JSON_KEY_COLOR).trim()));
         }
         if (!jsonObject.isNull(JSON_KEY_PUBLISHED_TIME)) {
            result.setPublishedTime(jsonObject.getString(
                  JSON_KEY_PUBLISHED_TIME).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_AUTHOR)) {
            result.setAuthor(jsonObject.getString(JSON_KEY_AUTHOR).trim());
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

   public static final Parcelable.Creator<SubscriptionAbstract> CREATOR = new Creator<SubscriptionAbstract>() {
      @Override
      public SubscriptionAbstract createFromParcel(Parcel src) {
         return new SubscriptionAbstract(src);
      }

      @Override
      public SubscriptionAbstract[] newArray(int size) {
         return new SubscriptionAbstract[size];
      }
   };

}
