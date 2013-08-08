package com.osastudio.newshub.data.base;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsBaseTopic extends NewsBaseTitle implements NewsId, Parcelable {

   protected String iconUrl = "";

   public NewsBaseTopic() {
      super();
   }

   public NewsBaseTopic(Parcel src) {
      super(src);
      this.iconUrl = src.readString();
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
      super.writeToParcel(dst, flags);
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
