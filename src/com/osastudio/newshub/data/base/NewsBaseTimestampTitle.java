package com.osastudio.newshub.data.base;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsBaseTimestampTitle extends NewsBaseTitle implements NewsId,
      Parcelable {

   protected String publishedTime;

   public NewsBaseTimestampTitle() {
      super();
   }

   public NewsBaseTimestampTitle(Parcel src) {
      super(src);
      this.publishedTime = src.readString();
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
      super.writeToParcel(dst, flags);
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
