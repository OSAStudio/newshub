package com.osastudio.newshub.data.base;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsBaseTopic extends NewsBaseObject implements NewsId, Parcelable {

   protected NewsBaseTitle newsTitle;
   protected String iconUrl;

   public NewsBaseTopic() {
      this.newsTitle = new NewsBaseTitle();
   }

   public NewsBaseTopic(Parcel src) {
      this.newsTitle = src.readParcelable(NewsBaseTitle.class.getClassLoader());
      this.iconUrl = src.readString();
   }

   public NewsBaseTitle getNewsBaseTitle() {
      return this.newsTitle;
   }

   public NewsBaseTopic setNewsBaseTitle(NewsBaseTitle title) {
      this.newsTitle = title;
      return this;
   }

   public String getId() {
      return (this.newsTitle != null) ? this.newsTitle.getId() : null;
   }

   public NewsBaseTopic setId(String id) {
      if (this.newsTitle == null) {
         this.newsTitle = new NewsBaseTitle();
      }
      this.newsTitle.setId(id);
      return this;
   }

   public String getTitle() {
      return (this.newsTitle != null) ? this.newsTitle.getTitle() : null;
   }

   public NewsBaseTopic setTitle(String title) {
      if (this.newsTitle == null) {
         this.newsTitle = new NewsBaseTitle();
      }
      this.newsTitle.setTitle(title);
      return this;
   }

   public String getIconUrl() {
      return this.iconUrl;
   }

   public NewsBaseTopic setIconUrl(String url) {
      this.iconUrl = url;
      return this;
   }

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dst, int flags) {
      dst.writeParcelable(this.newsTitle, flags);
      dst.writeString(this.iconUrl);
   }

   public static final Parcelable.Creator<NewsBaseTopic> CREATOR = new Creator<NewsBaseTopic>() {
      @Override
      public NewsBaseTopic createFromParcel(Parcel src) {
         return new NewsBaseTopic(src);
      }

      @Override
      public NewsBaseTopic[] newArray(int size) {
         return new NewsBaseTopic[size];
      }
   };

}
