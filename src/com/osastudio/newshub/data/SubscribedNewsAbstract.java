package com.osastudio.newshub.data;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsBaseTopicAbstract;

import android.os.Parcel;
import android.os.Parcelable;

public class SubscribedNewsAbstract extends NewsBaseTopicAbstract implements
      Parcelable {

   public static final String JSON_KEY_ID = "expand_lesson_id";
   public static final String JSON_KEY_COLOR = "expand_lesson_colour";
   public static final String JSON_KEY_PUBLISHED_TIME = "post_date";
   public static final String JSON_KEY_AUTHOR = "expert_name";
   public static final String JSON_KEY_TITLE = "expand_lesson_title";

   public SubscribedNewsAbstract() {
      super();
   }

   public SubscribedNewsAbstract(Parcel src) {
      super(src);
   }

   public static SubscribedNewsAbstract parseJsonObject(JSONObject jsonObject) {
      SubscribedNewsAbstract result = new SubscribedNewsAbstract();
      try {
         if (!jsonObject.isNull(JSON_KEY_ID)) {
            result.setId(jsonObject.getString(JSON_KEY_ID).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_COLOR)) {
            result.setColor(SubscribedNewsAbstract.parseColorValue(jsonObject
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

   public static final Parcelable.Creator<SubscribedNewsAbstract> CREATOR = new Creator<SubscribedNewsAbstract>() {
      @Override
      public SubscribedNewsAbstract createFromParcel(Parcel src) {
         return new SubscribedNewsAbstract(src);
      }

      @Override
      public SubscribedNewsAbstract[] newArray(int size) {
         return new SubscribedNewsAbstract[size];
      }
   };

}
