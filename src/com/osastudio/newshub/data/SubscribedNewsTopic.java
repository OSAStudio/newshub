package com.osastudio.newshub.data;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class SubscribedNewsTopic extends NewsBaseTopic implements Parcelable {

   public static final String JSON_KEY_ICON_URL = "recommend_lssue_icon";
   public static final String JSON_KEY_TITLE = "recommend_lssue_title";
   public static final String JSON_KEY_ID = "recommend_lssue_id";

   public SubscribedNewsTopic() {
      super();
   }

   public SubscribedNewsTopic(Parcel src) {
      super(src);
   }

   public static SubscribedNewsTopic parseJsonObject(JSONObject jsonObject) {
      SubscribedNewsTopic result = new SubscribedNewsTopic();
      try {
         if (!jsonObject.isNull(JSON_KEY_ICON_URL)) {
            result.setIconUrl(jsonObject.getString(JSON_KEY_ICON_URL).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_TITLE)) {
            result.setTitle(jsonObject.getString(JSON_KEY_TITLE).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_ID)) {
            result.setId(jsonObject.getString(JSON_KEY_ID).trim());
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
      ;
   }

   public static final Parcelable.Creator<SubscribedNewsTopic> CREATOR = new Creator<SubscribedNewsTopic>() {
      @Override
      public SubscribedNewsTopic createFromParcel(Parcel src) {
         return new SubscribedNewsTopic(src);
      }

      @Override
      public SubscribedNewsTopic[] newArray(int size) {
         return new SubscribedNewsTopic[size];
      }
   };

}
