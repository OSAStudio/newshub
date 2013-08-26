package com.osastudio.newshub.data;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsBaseObject;
import com.osastudio.newshub.data.base.NewsId;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsMessage extends NewsBaseObject implements NewsId, Parcelable {

   private static final String JSON_KEY_CONTENT = "message_content";
   private static final String JSON_KEY_ID = "service_id";
   private static final String JSON_KEY_TYPE = "message_class";

   public static final int MSG_TYPE_NOTICE = 1;
   public static final int MSG_TYPE_NOTICE_FEEDBACK = 2;
   public static final int MSG_TYPE_DAILY_REMINDER = 3;
   public static final int MSG_TYPE_COLUMNIST = 4;
   public static final int MSG_TYPE_RECOMMEND = 5;

   private String content = "";
   private String id = "";
   private int type = 0;
   private String userId = "";
   private String userName = "";

   public NewsMessage() {

   }

   public NewsMessage(Parcel src) {
      this.content = src.readString();
      this.id = src.readString();
      this.type = src.readInt();
   }

   public String getContent() {
      return this.content;
   }

   public NewsMessage setContent(String content) {
      this.content = content;
      return this;
   }

   public String getId() {
      return this.id;
   }

   public NewsMessage setId(String id) {
      this.id = id;
      return this;
   }

   public int getType() {
      return this.type;
   }

   public NewsMessage setType(int type) {
      this.type = type;
      return this;
   }

   public String getUserId() {
      return this.userId;
   }

   public NewsMessage setUserId(String userId) {
      this.userId = userId;
      return this;
   }

   public String getUserName() {
      return this.userName;
   }

   public NewsMessage setUserName(String userName) {
      this.userName = userName;
      return this;
   }

   @Override
   public int describeContents() {
      return 0;
   }

   public static NewsMessage parseJsonObject(JSONObject jsonObject) {
      NewsMessage result = new NewsMessage();
      try {
         if (!jsonObject.isNull(JSON_KEY_CONTENT)) {
            result.setContent(jsonObject.getString(JSON_KEY_CONTENT).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_ID)) {
            result.setId(jsonObject.getString(JSON_KEY_ID).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_TYPE)) {
            result.setType(Integer.valueOf(jsonObject.getString(JSON_KEY_TYPE)
                  .trim()));
         }
         if (!jsonObject.isNull(JSON_KEY_USER_ID)) {
            result.setUserId(jsonObject.getString(JSON_KEY_USER_ID).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_USER_NAME)) {
            result.setUserName(jsonObject.getString(JSON_KEY_USER_NAME).trim());
         }
      } catch (JSONException e) {

      }
      return result;
   }

   @Override
   public void writeToParcel(Parcel dst, int flags) {
      dst.writeString(this.content);
      dst.writeString(this.id);
      dst.writeInt(this.type);
   }

   public static final Parcelable.Creator<NewsMessage> CREATOR = new Creator<NewsMessage>() {
      @Override
      public NewsMessage createFromParcel(Parcel src) {
         return new NewsMessage(src);
      }

      @Override
      public NewsMessage[] newArray(int size) {
         return new NewsMessage[size];
      }
   };

}
