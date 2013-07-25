package com.osastudio.newshub.data;

import org.json.JSONException;
import org.json.JSONObject;

public class AppProperties extends NewsBaseObject {
   
   public static final String JSON_KEY_APK_URL = "android_url";
   public static final String JSON_KEY_IMAGE_URL = "picture_url";
   public static final String JSON_KEY_RELEASE_NOTES = "update_note";
   public static final String JSON_KEY_USER_STATUS = "user_status";
   public static final String JSON_KEY_VERSION_NAME = "version_id";

   private String apkUrl;
   private String imageUrl;
   private String releaseNotes;
   private int userStatus;
   private String versionName;
   
   public AppProperties() {
      
   }
   
   public String getImageUrl() {
      return this.imageUrl;
   }

   public AppProperties setImageUrl(String imageUrl) {
      this.imageUrl = imageUrl;
      return this;
   }

   public String getApkUrl() {
      return this.apkUrl;
   }

   public AppProperties setApkUrl(String apkUrl) {
      this.apkUrl = apkUrl;
      return this;
   }

   public String getReleaseNotes() {
      return this.releaseNotes;
   }

   public AppProperties setReleaseNotes(String releaseNotes) {
      this.releaseNotes = releaseNotes;
      return this;
   }

   public int getUserStatus() {
      return this.userStatus;
   }

   public AppProperties setUserStatus(int userStatus) {
      this.userStatus = userStatus;
      return this;
   }

   public String getVersionName() {
      return this.versionName;
   }

   public AppProperties setVersionName(String versionName) {
      this.versionName = versionName;
      return this;
   }

   public static AppProperties parseJsonObject(JSONObject jsonObject) {
      AppProperties result = new AppProperties();
      try {
         if (jsonObject.isNull(JSON_KEY_RESULT_CODE)
               || AppProperties.isResultFailure(jsonObject
                     .getString(JSON_KEY_RESULT_CODE))) {
            return null;
         }

         if (!jsonObject.isNull(JSON_KEY_APK_URL)) {
            result.setApkUrl(jsonObject.getString(JSON_KEY_APK_URL).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_IMAGE_URL)) {
            result.setImageUrl(jsonObject.getString(JSON_KEY_IMAGE_URL).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_RELEASE_NOTES)) {
            result.setReleaseNotes(jsonObject.getString(JSON_KEY_RELEASE_NOTES).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_USER_STATUS)) {
            result.setUserStatus(jsonObject.getInt(JSON_KEY_USER_STATUS));
         }
         if (!jsonObject.isNull(JSON_KEY_VERSION_NAME)) {
            result.setVersionName(jsonObject.getString(JSON_KEY_VERSION_NAME).trim());
         }
      } catch (JSONException e) {
         
      }
      return result;
   }
   
}
