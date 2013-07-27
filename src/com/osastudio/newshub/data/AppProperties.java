package com.osastudio.newshub.data;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.user.UserStatus;

public class AppProperties extends NewsBaseObject implements UserStatus {

   public static final String JSON_KEY_APK_URL = "android_url";
   public static final String JSON_KEY_SPLASH_IMAGE_URL = "picture_url";
   public static final String JSON_KEY_RELEASE_NOTES = "update_note";
   public static final String JSON_KEY_USER_STATUS = "user_status";
   public static final String JSON_KEY_VERSION_NAME = "version_id";

   private String apkUrl;
   private String splashImageUrl;
   private String releaseNotes;
   private int userStatus;
   private String versionName;

   public AppProperties() {

   }

   public AppProperties(JSONObject jsonObject) {
      super(jsonObject);

      if (isSuccess()) {
         try {
            if (!jsonObject.isNull(JSON_KEY_APK_URL)) {
               setApkUrl(jsonObject.getString(JSON_KEY_APK_URL).trim());
            }
            if (!jsonObject.isNull(JSON_KEY_SPLASH_IMAGE_URL)) {
               setSplashImageUrl(jsonObject
                     .getString(JSON_KEY_SPLASH_IMAGE_URL).trim());
            }
            if (!jsonObject.isNull(JSON_KEY_RELEASE_NOTES)) {
               setReleaseNotes(jsonObject.getString(JSON_KEY_RELEASE_NOTES)
                     .trim());
            }
            if (!jsonObject.isNull(JSON_KEY_USER_STATUS)) {
               setUserStatus(jsonObject.getInt(JSON_KEY_USER_STATUS));
            }
            if (!jsonObject.isNull(JSON_KEY_VERSION_NAME)) {
               setVersionName(jsonObject.getString(JSON_KEY_VERSION_NAME)
                     .trim());
            }
         } catch (JSONException e) {

         }
      }
   }

   public String getSplashImageUrl() {
      return this.splashImageUrl;
   }

   public AppProperties setSplashImageUrl(String imageUrl) {
      this.splashImageUrl = imageUrl;
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

}
