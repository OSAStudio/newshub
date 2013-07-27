package com.osastudio.newshub.data;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsAbstract extends NewsBaseObject implements Parcelable {

   public static final String JSON_KEY_ARTICLE_ID = "lesson_id";
   public static final String JSON_KEY_COLOR = "lesson_colour";
   public static final String JSON_KEY_PUBLISHED_TIME = "post_date";
   public static final String JSON_KEY_PUBLISHER = "expert_name";
   public static final String JSON_KEY_TITLE = "lesson_title";

   private String articleId = "";
   private String channelId = "";
   private int color = DEFAULT_COLOR;
   private String publishedTime;
   private String publisher = "";
   private String title = "";

   public NewsAbstract() {

   }

   public NewsAbstract(Parcel src) {
      this.articleId = src.readString().trim();
      this.channelId = src.readString().trim();
      this.color = src.readInt();
      this.publishedTime = src.readString().trim();
      this.publisher = src.readString().trim();
      this.title = src.readString().trim();
   }

   public String getArticleId() {
      return this.articleId;
   }

   public NewsAbstract setArticleId(String articleId) {
      this.articleId = (articleId != null) ? articleId : "";
      return this;
   }

   public String getChannelId() {
      return this.channelId;
   }

   public NewsAbstract setChannelId(String channelId) {
      this.channelId = (channelId != null) ? channelId : "";
      return this;
   }

   public int getColor() {
      return this.color;
   }

   public NewsAbstract setColor(int color) {
      this.color = color;
      return this;
   }

   public String getPublishedTime() {
      return this.publishedTime;
   }

   public NewsAbstract setPublishedTime(String publishedTime) {
      this.publishedTime = (publishedTime != null) ? publishedTime : "";
      return this;
   }

   public String getPublisher() {
      return this.publisher;
   }

   public NewsAbstract setPublisher(String publisher) {
      this.publisher = (publisher != null) ? publisher : "";
      return this;
   }

   public String getTitle() {
      return this.title;
   }

   public NewsAbstract setTitle(String title) {
      this.title = (title != null) ? title : "";
      return this;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      }

      if ((obj == null) || !(obj instanceof NewsAbstract)) {
         return false;
      }

      NewsAbstract that = (NewsAbstract) obj;
      return this.channelId.equals(that.getChannelId())
            && this.articleId.equals(that.getArticleId());
   }

   public static NewsAbstract parseJsonObject(JSONObject jsonObject) {
      NewsAbstract result = new NewsAbstract();
      try {
         if (!jsonObject.isNull(JSON_KEY_ARTICLE_ID)) {
            result.setArticleId(jsonObject.getString(JSON_KEY_ARTICLE_ID)
                  .trim());
         }
         if (!jsonObject.isNull(JSON_KEY_COLOR)) {
            result.setColor(NewsAbstract.parseColorValue(jsonObject.getString(
                  JSON_KEY_COLOR).trim()));
         }
         if (!jsonObject.isNull(JSON_KEY_PUBLISHED_TIME)) {
            result.setPublishedTime(jsonObject.getString(
                  JSON_KEY_PUBLISHED_TIME).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_PUBLISHER)) {
            result.setPublisher(jsonObject.getString(JSON_KEY_PUBLISHER).trim());
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
      dst.writeString(this.articleId);
      dst.writeString(this.channelId);
      dst.writeInt(this.color);
      dst.writeString(this.publishedTime);
      dst.writeString(this.publisher);
      dst.writeString(this.title);
   }

   public static final Parcelable.Creator<NewsAbstract> CREATOR = new Creator<NewsAbstract>() {
      @Override
      public NewsAbstract createFromParcel(Parcel src) {
         return new NewsAbstract(src);
      }

      @Override
      public NewsAbstract[] newArray(int size) {
         return new NewsAbstract[size];
      }
   };

}
