package com.osastudio.newshub.data;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsBaseObject;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsColumnist extends NewsBaseObject implements Parcelable {

   public static final String JSON_KEY_ICON_URL = "expert_icon";
   public static final String JSON_KEY_ID = "expert_id";
   public static final String JSON_KEY_NAME = "expert_name";
   public static final String JSON_KEY_OUTLINE = "expert_outline";
   public static final String JSON_KEY_SORT_ORDER = "expert_xh";

   private String iconUrl = "";
   private String id = "";
   private String name = "";
   private String outline;
   private int sortOrder = 0;

   public NewsColumnist() {

   }

   public NewsColumnist(Parcel src) {
      this.iconUrl = src.readString().trim();
      this.id = src.readString().trim();
      this.name = src.readString().trim();
      this.outline = src.readString().trim();
      this.sortOrder = src.readInt();
   }

   public String getIconUrl() {
      return this.iconUrl;
   }

   public NewsColumnist setIconUrl(String url) {
      this.iconUrl = (url != null) ? url : "";
      return this;
   }

   public String getId() {
      return this.id;
   }

   public NewsColumnist setId(String id) {
      this.id = (id != null) ? id : "";
      return this;
   }

   public String getName() {
      return this.name;
   }

   public NewsColumnist setName(String name) {
      this.name = (name != null) ? name : "";
      return this;
   }

   public String getOutline() {
      return this.outline;
   }

   public NewsColumnist setOutline(String outline) {
      this.outline = (outline != null) ? outline : "";
      return this;
   }

   public int getSortOrder() {
      return this.sortOrder;
   }

   public NewsColumnist setSortOrder(int order) {
      this.sortOrder = order;
      return this;
   }

   public static NewsColumnist parseJsonObject(JSONObject jsonObject) {
      NewsColumnist result = new NewsColumnist();
      try {
         if (!jsonObject.isNull(JSON_KEY_ICON_URL)) {
            result.setIconUrl(jsonObject.getString(JSON_KEY_ICON_URL).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_ID)) {
            result.setId(jsonObject.getString(JSON_KEY_ID).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_NAME)) {
            result.setName(jsonObject.getString(JSON_KEY_NAME).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_SORT_ORDER)) {
            result.setSortOrder(NewsColumnist.parseColorValue(jsonObject
                  .getString(JSON_KEY_SORT_ORDER).trim()));
         }
         if (!jsonObject.isNull(JSON_KEY_OUTLINE)) {
            result.setOutline(jsonObject.getString(JSON_KEY_OUTLINE).trim());
         }
      } catch (JSONException e) {

      }
      return result;
   }

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dst, int flags) {
      dst.writeString(this.iconUrl);
      dst.writeString(this.id);
      dst.writeString(this.name);
      dst.writeString(this.outline);
      dst.writeInt(this.sortOrder);
   }

   public static final Parcelable.Creator<NewsColumnist> CREATOR = new Creator<NewsColumnist>() {
      @Override
      public NewsColumnist createFromParcel(Parcel src) {
         return new NewsColumnist(src);
      }

      @Override
      public NewsColumnist[] newArray(int size) {
         return new NewsColumnist[size];
      }
   };

}
