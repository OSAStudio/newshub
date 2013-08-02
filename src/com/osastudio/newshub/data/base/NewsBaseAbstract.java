package com.osastudio.newshub.data.base;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsBaseAbstract extends NewsBaseObject implements NewsId, Parcelable {

   protected NewsBaseTimestampTitle newsTitle;
   protected String author = "";
   protected int color = DEFAULT_COLOR;

   public NewsBaseAbstract() {
      this.newsTitle = new NewsBaseTimestampTitle();
   }

   public NewsBaseAbstract(Parcel src) {
      this.newsTitle = src.readParcelable(NewsBaseTimestampTitle.class
            .getClassLoader());
      this.author = src.readString().trim();
      this.color = src.readInt();
   }

   public String getId() {
      return (this.newsTitle != null) ? this.newsTitle.getId() : null;
   }

   public NewsBaseAbstract setId(String id) {
      if (this.newsTitle == null) {
         this.newsTitle = new NewsBaseTimestampTitle();
      }
      this.newsTitle.setId(id);
      return this;
   }

   public String getTitle() {
      return (this.newsTitle != null) ? this.newsTitle.getTitle() : null;
   }

   public NewsBaseAbstract setTitle(String title) {
      if (this.newsTitle == null) {
         this.newsTitle = new NewsBaseTimestampTitle();
      }
      this.newsTitle.setTitle(title);
      return this;
   }

   public String getPublishedTime() {
      return (this.newsTitle != null) ? this.newsTitle.getPublishedTime()
            : null;
   }

   public NewsBaseAbstract setPublishedTime(String time) {
      if (this.newsTitle == null) {
         this.newsTitle = new NewsBaseTimestampTitle();
      }
      this.newsTitle.setPublishedTime(time);
      return this;
   }

   public String getAuthor() {
      return this.author;
   }

   public NewsBaseAbstract setAuthor(String author) {
      this.author = (author != null) ? author : "";
      return this;
   }

   public int getColor() {
      return this.color;
   }

   public NewsBaseAbstract setColor(int color) {
      this.color = color;
      return this;
   }

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dst, int flags) {
      dst.writeParcelable(this.newsTitle, flags);
      dst.writeString(this.author);
      dst.writeInt(this.color);
   }

   public static final Parcelable.Creator<NewsBaseAbstract> CREATOR = new Creator<NewsBaseAbstract>() {
      @Override
      public NewsBaseAbstract createFromParcel(Parcel src) {
         return new NewsBaseAbstract(src);
      }

      @Override
      public NewsBaseAbstract[] newArray(int size) {
         return new NewsBaseAbstract[size];
      }
   };

}
