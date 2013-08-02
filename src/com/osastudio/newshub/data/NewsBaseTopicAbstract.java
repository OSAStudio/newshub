package com.osastudio.newshub.data;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsBaseTopicAbstract extends NewsBaseObject implements NewsId, Parcelable {

   protected NewsBaseAbstract newsAbstract;
   protected String topicId;

   public NewsBaseTopicAbstract() {
      this.newsAbstract = new NewsBaseAbstract();
   }

   public NewsBaseTopicAbstract(Parcel src) {
      this.newsAbstract = src.readParcelable(NewsBaseAbstract.class
            .getClassLoader());
      this.topicId = src.readString();
   }

   public NewsBaseAbstract getNewsBaseAbstract() {
      return this.newsAbstract;
   }

   public NewsBaseTopicAbstract setNewsBaseAbstract(NewsBaseAbstract abs) {
      this.newsAbstract = abs;
      return this;
   }

   public String getId() {
      return (this.newsAbstract != null) ? this.newsAbstract.getId() : null;
   }

   public NewsBaseTopicAbstract setId(String id) {
      if (this.newsAbstract == null) {
         this.newsAbstract = new NewsBaseAbstract();
      }
      this.newsAbstract.setId(id);
      return this;
   }

   public String getTitle() {
      return (this.newsAbstract != null) ? this.newsAbstract.getTitle() : null;
   }

   public NewsBaseTopicAbstract setTitle(String title) {
      if (this.newsAbstract == null) {
         this.newsAbstract = new NewsBaseAbstract();
      }
      this.newsAbstract.setTitle(title);
      return this;
   }

   public String getPublishedTime() {
      return (this.newsAbstract != null) ? this.newsAbstract.getPublishedTime()
            : null;
   }

   public NewsBaseTopicAbstract setPublishedTime(String time) {
      if (this.newsAbstract == null) {
         this.newsAbstract = new NewsBaseAbstract();
      }
      this.newsAbstract.setPublishedTime(time);
      return this;
   }

   public String getAuthor() {
      return (this.newsAbstract != null) ? this.newsAbstract.getAuthor() : null;
   }

   public NewsBaseTopicAbstract setAuthor(String author) {
      if (this.newsAbstract == null) {
         this.newsAbstract = new NewsBaseAbstract();
      }
      this.newsAbstract.setAuthor(author);
      return this;
   }

   public int getColor() {
      return (this.newsAbstract != null) ? this.newsAbstract.getColor() : null;
   }

   public NewsBaseTopicAbstract setColor(int color) {
      if (this.newsAbstract == null) {
         this.newsAbstract = new NewsBaseAbstract();
      }
      this.newsAbstract.setColor(color);
      return this;
   }

   public String getTopicId() {
      return this.topicId;
   }

   public NewsBaseTopicAbstract setTopicId(String id) {
      this.topicId = id;
      return this;
   }

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dst, int flags) {
      dst.writeParcelable(this.newsAbstract, flags);
      dst.writeString(this.topicId);
   }

   public static final Parcelable.Creator<NewsBaseTopicAbstract> CREATOR = new Creator<NewsBaseTopicAbstract>() {
      @Override
      public NewsBaseTopicAbstract createFromParcel(Parcel src) {
         return new NewsBaseTopicAbstract(src);
      }

      @Override
      public NewsBaseTopicAbstract[] newArray(int size) {
         return new NewsBaseTopicAbstract[size];
      }
   };

}
