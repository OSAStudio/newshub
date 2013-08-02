package com.osastudio.newshub.data;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsBaseTimestampTitle extends NewsBaseObject implements
      NewsId, Parcelable {

   protected NewsBaseTitle newsTitle;
   private String publishedTime;

   public NewsBaseTimestampTitle() {
      this.newsTitle = new NewsBaseTitle();
   }

   public NewsBaseTimestampTitle(Parcel src) {
      this.newsTitle = src.readParcelable(NewsBaseTitle.class.getClassLoader());
      this.publishedTime = src.readString();
   }

   public NewsBaseTitle getNewsBaseTitle() {
      return this.newsTitle;
   }

   public NewsBaseTimestampTitle setNewsBaseTitle(NewsBaseTitle title) {
      this.newsTitle = title;
      return this;
   }

   public String getId() {
      return (this.newsTitle != null) ? this.newsTitle.getId() : null;
   }

   public NewsBaseTimestampTitle setId(String id) {
      if (this.newsTitle == null) {
         this.newsTitle = new NewsBaseTitle();
      }
      this.newsTitle.setId(id);
      return this;
   }

   public String getTitle() {
      return (this.newsTitle != null) ? this.newsTitle.getTitle() : null;
   }

   public NewsBaseTimestampTitle setTitle(String title) {
      if (this.newsTitle == null) {
         this.newsTitle = new NewsBaseTitle();
      }
      this.newsTitle.setTitle(title);
      return this;
   }

   public String getPublishedTime() {
      return this.publishedTime;
   }

   public NewsBaseTimestampTitle setPublishedTime(String time) {
      this.publishedTime = time;
      return this;
   }

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dst, int flags) {
      dst.writeParcelable(this.newsTitle, flags);
      dst.writeString(this.publishedTime);
   }

   public static final Parcelable.Creator<NewsBaseTimestampTitle> CREATOR = new Creator<NewsBaseTimestampTitle>() {
      @Override
      public NewsBaseTimestampTitle createFromParcel(Parcel src) {
         return new NewsBaseTimestampTitle(src);
      }

      @Override
      public NewsBaseTimestampTitle[] newArray(int size) {
         return new NewsBaseTimestampTitle[size];
      }
   };

}
