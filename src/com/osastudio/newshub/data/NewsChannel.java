package com.osastudio.newshub.data;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsBaseObject;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsChannel extends NewsBaseObject implements Parcelable {

   public static final String JSON_KEY_CHANNEL_ID = "service_id";
   public static final String JSON_KEY_TITLE_ID = "title_id";
   public static final String JSON_KEY_TITLE_TYPE = "title_class";
   public static final String JSON_KEY_TITLE_NAME = "title_name";
   public static final String JSON_KEY_TITLE_COLOR = "title_colour";
   public static final String JSON_KEY_ICON_ID = "icon_id";
   public static final String JSON_KEY_ICON_URL = "icon_url";

   private String channelId = "";
   private String iconId = "";
   private String iconUrl = "";
   private int titleColor = DEFAULT_COLOR;
   private String titleId = "";
   private String titleName = "";
   private String titleType = "";

   public NewsChannel() {

   }

   public NewsChannel(Parcel src) {
      this.channelId = src.readString().trim();
      this.iconId = src.readString().trim();
      this.iconUrl = src.readString().trim();
      this.titleColor = src.readInt();
      this.titleId = src.readString().trim();
      this.titleName = src.readString().trim();
      this.titleType = src.readString().trim();
   }

   public String getChannelId() {
      return this.channelId;
   }

   public NewsChannel setChannelId(String channelId) {
      this.channelId = (channelId != null) ? channelId : "";
      return this;
   }

   public String getIconId() {
      return this.iconId;
   }

   public NewsChannel setIconId(String iconId) {
      this.iconId = (iconId != null) ? iconId : "";
      return this;
   }

   public String getIconUrl() {
      return this.iconUrl;
   }

   public NewsChannel setIconUrl(String iconUrl) {
      this.iconUrl = (iconUrl != null) ? iconUrl : "";
      return this;
   }

   public int getTitleColor() {
      return this.titleColor;
   }

   public NewsChannel setTitleColor(int titleColor) {
      this.titleColor = titleColor;
      return this;
   }

   public String getTitleId() {
      return this.titleId;
   }

   public NewsChannel setTitleId(String titleId) {
      this.titleId = titleId;
      this.titleId = (titleId != null) ? titleId : "";
      return this;
   }

   public String getTitleName() {
      return this.titleName;
   }

   public NewsChannel setTitleName(String titleName) {
      this.titleName = (titleName != null) ? titleName : "";
      return this;
   }

   public String getTitleType() {
      return this.titleType;
   }

   public NewsChannel setTitleType(String titleType) {
      this.titleType = (titleType != null) ? titleType : "";
      return this;
   }

   public static NewsChannel parseJsonObject(JSONObject jsonObject) {
      NewsChannel result = new NewsChannel();
      try {
         if (!jsonObject.isNull(JSON_KEY_CHANNEL_ID)) {
            result.setChannelId(jsonObject.getString(JSON_KEY_CHANNEL_ID)
                  .trim());
         }
         if (!jsonObject.isNull(JSON_KEY_ICON_ID)) {
            result.setIconId(jsonObject.getString(JSON_KEY_ICON_ID));
         }
         if (!jsonObject.isNull(JSON_KEY_ICON_URL)) {
            result.setIconUrl(jsonObject.getString(JSON_KEY_ICON_URL));
         }
         if (!jsonObject.isNull(JSON_KEY_TITLE_COLOR)) {
            result.setTitleColor(NewsChannel.parseColorValue(jsonObject
                  .getString(JSON_KEY_TITLE_COLOR).trim()));
         }
         if (!jsonObject.isNull(JSON_KEY_TITLE_ID)) {
            result.setTitleId(jsonObject.getString(JSON_KEY_TITLE_ID).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_TITLE_NAME)) {
            result.setTitleName(jsonObject.getString(JSON_KEY_TITLE_NAME)
                  .trim());
         }
         if (!jsonObject.isNull(JSON_KEY_TITLE_TYPE)) {
            result.setTitleType(jsonObject.getString(JSON_KEY_TITLE_TYPE)
                  .trim());
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
      dst.writeString(this.channelId);
      dst.writeString(this.iconId);
      dst.writeString(this.iconUrl);
      dst.writeInt(this.titleColor);
      dst.writeString(this.titleId);
      dst.writeString(this.titleName);
      dst.writeString(this.titleType);
   }

   public static final Parcelable.Creator<NewsChannel> CREATOR = new Creator<NewsChannel>() {
      @Override
      public NewsChannel createFromParcel(Parcel src) {
         return new NewsChannel(src);
      }

      @Override
      public NewsChannel[] newArray(int size) {
         return new NewsChannel[size];
      }
   };

}
