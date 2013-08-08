package com.osastudio.newshub.data.base;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsBaseTopicAbstract extends NewsBaseAbstract implements NewsId,
      Parcelable {

   protected String topicId = "";

   public NewsBaseTopicAbstract() {
      super();
   }

   public NewsBaseTopicAbstract(Parcel src) {
      super(src);
      this.topicId = src.readString();
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
      super.writeToParcel(dst, flags);
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
