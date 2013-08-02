package com.osastudio.newshub.data;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsBaseTitle extends NewsBaseObject implements NewsId, Parcelable {

//   protected NewsId newsId;
   protected String id = "";
   protected String title = "";

   public NewsBaseTitle() {
//      this.newsId = new NewsId();
   }

   public NewsBaseTitle(Parcel src) {
//      super(src);
      this.id = src.readString().trim();
      this.title = src.readString().trim();
   }

   public String getId() {
//      return (this.newsId != null) ? this.newsId.getId() : null;
      return this.id;
   }

   public NewsBaseTitle setId(String id) {
//      if (this.newsId == null) {
//         this.newsId = new NewsId();
//      }
//      this.newsId.setId(id);
      this.id = id;
      return this;
   }

   public String getTitle() {
      return this.title;
   }

   public NewsBaseTitle setTitle(String title) {
      this.title = (title != null) ? title : "";
      return this;
   }

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dst, int flags) {
//      super.writeToParcel(dst, flags);
      dst.writeString(this.id);
      dst.writeString(this.title);
   }

   public static final Parcelable.Creator<NewsBaseTitle> CREATOR = new Creator<NewsBaseTitle>() {
      @Override
      public NewsBaseTitle createFromParcel(Parcel src) {
         return new NewsBaseTitle(src);
      }

      @Override
      public NewsBaseTitle[] newArray(int size) {
         return new NewsBaseTitle[size];
      }
   };

}
