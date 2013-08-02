package com.osastudio.newshub.data.base;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsBaseAbstract extends NewsBaseTimestampTitle implements NewsId,
      Parcelable {

   protected String author = "";
   protected int color = DEFAULT_COLOR;

   public NewsBaseAbstract() {
      super();
   }

   public NewsBaseAbstract(Parcel src) {
      super(src);
      this.author = src.readString().trim();
      this.color = src.readInt();
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
      super.writeToParcel(dst, flags);
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
