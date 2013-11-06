package com.osastudio.newshub.data;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsBaseTopic;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class for recommended topic, including id, title, icon.
 * 
 * @author Rujin Xue
 * 
 */
public class RecommendedTopic extends NewsBaseTopic implements Parcelable {

   public static final String JSON_KEY_ID = "recommend_lssue_id";
   public static final String JSON_KEY_TITLE = "recommend_lssue_title";
   public static final String JSON_KEY_ICON_URL = "recommend_lssue_icon";

   public RecommendedTopic() {
      super();
   }

   public RecommendedTopic(Parcel src) {
      super(src);
   }

   public static RecommendedTopic parseJsonObject(JSONObject jsonObject) {
      RecommendedTopic result = new RecommendedTopic();
      try {
         if (!jsonObject.isNull(JSON_KEY_ID)) {
            result.setId(jsonObject.getString(JSON_KEY_ID).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_TITLE)) {
            result.setTitle(jsonObject.getString(JSON_KEY_TITLE).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_ICON_URL)) {
            result.setIconUrl(jsonObject.getString(JSON_KEY_ICON_URL).trim());
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

   public static final Parcelable.Creator<RecommendedTopic> CREATOR = new Creator<RecommendedTopic>() {
      @Override
      public RecommendedTopic createFromParcel(Parcel src) {
         return new RecommendedTopic(src);
      }

      @Override
      public RecommendedTopic[] newArray(int size) {
         return new RecommendedTopic[size];
      }
   };

}
