package com.osastudio.newshub.data;

import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public interface NewsId  {

   public abstract String getId();
   public abstract NewsId setId(String id);
   
   /*
   protected String id = "";

   public NewsId() {

   }
   
   public NewsId(JSONObject jsonObject) {
      super(jsonObject);
   }

   public NewsId(Parcel src) {
      this.id = src.readString().trim();
   }

   public String getId() {
      return this.id;
   }

   public NewsId setId(String id) {
      this.id = (id != null) ? id : "";
      return this;
   }

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dst, int flags) {
      dst.writeString(this.id);
   }

   public static final Parcelable.Creator<NewsId> CREATOR = new Creator<NewsId>() {
      @Override
      public NewsId createFromParcel(Parcel src) {
         return new NewsId(src);
      }

      @Override
      public NewsId[] newArray(int size) {
         return new NewsId[size];
      }
   };
   */
}
