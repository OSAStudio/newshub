package com.osastudio.newshub.data;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsBaseObject;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsNotice extends NewsBaseObject implements Parcelable {

   public static final String JSON_KEY_NOTICE_ID = "notify_id";
   public static final String JSON_KEY_PUBLISHED_TIME = "send_date";
   public static final String JSON_KEY_TITLE = "notify_title";

   private String noticeId = "";
   private String publishedTime;
   private String title = "";

   public NewsNotice() {

   }

   public NewsNotice(Parcel src) {
      this.noticeId = src.readString().trim();
      this.publishedTime = src.readString().trim();
      this.title = src.readString().trim();
   }

   public String getNoticeId() {
      return this.noticeId;
   }

   public NewsNotice setNoticeId(String noticeId) {
      this.noticeId = (noticeId != null) ? noticeId : "";
      return this;
   }

   public String getPublishedTime() {
      return this.publishedTime;
   }

   public NewsNotice setPublishedTime(String publishedTime) {
      this.publishedTime = (publishedTime != null) ? publishedTime : "";
      return this;
   }

   public String getTitle() {
      return this.title;
   }

   public NewsNotice setTitle(String title) {
      this.title = (title != null) ? title : "";
      return this;
   }

   public static NewsNotice parseJsonObject(JSONObject jsonObject) {
      NewsNotice result = new NewsNotice();
      try {
         if (!jsonObject.isNull(JSON_KEY_NOTICE_ID)) {
            result.setNoticeId(jsonObject.getString(JSON_KEY_NOTICE_ID).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_PUBLISHED_TIME)) {
            result.setPublishedTime(jsonObject.getString(
                  JSON_KEY_PUBLISHED_TIME).trim());
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
      dst.writeString(this.noticeId);
      dst.writeString(this.publishedTime);
      dst.writeString(this.title);
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
