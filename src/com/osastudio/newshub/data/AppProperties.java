package com.osastudio.newshub.data;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.user.UserStatus;

public class AppProperties extends NewsBaseObject implements UserStatus {

   public static final String JSON_KEY_APK_URL = "android_url";
   public static final String JSON_KEY_MIN_VERSION_CODE = "min_version_code";
   public static final String JSON_KEY_SPLASH_IMAGE_URL = "picture_url";
   public static final String JSON_KEY_RELEASE_NOTES = "update_note";
   public static final String JSON_KEY_USER_IDS = "student_ids";
   public static final String JSON_KEY_USER_STATUS = "user_status";
   public static final String JSON_KEY_VERSION_CODE = "version_code";
   public static final String JSON_KEY_VERSION_NAME = "version_id";
   public static final String JSON_KEY_PROPERTIES = "list";

   private String apkUrl;
   private int minVersionCode;
   private String releaseNotes;
   private String splashImageUrl;
   private List<String> userIds;
   private int userStatus;
   private int versionCode;
   private String versionName;

   public AppProperties() {

   }

   public AppProperties(JSONObject jsonObject) {
      super(jsonObject);

      if (isSuccess()) {
         try {
            JSONObject propertiesObject = null;
            if (!jsonObject.isNull(JSON_KEY_PROPERTIES)) {
               propertiesObject = jsonObject.getJSONObject(JSON_KEY_PROPERTIES);
            }
            if (propertiesObject == null) {
               return;
            }

            if (!propertiesObject.isNull(JSON_KEY_APK_URL)) {
               setApkUrl(propertiesObject.getString(JSON_KEY_APK_URL).trim());
            }
            if (!propertiesObject.isNull(JSON_KEY_MIN_VERSION_CODE)) {
               setMinVersionCode(propertiesObject
                     .getInt(JSON_KEY_MIN_VERSION_CODE));
            }
            if (!propertiesObject.isNull(JSON_KEY_SPLASH_IMAGE_URL)) {
               setSplashImageUrl(propertiesObject.getString(
                     JSON_KEY_SPLASH_IMAGE_URL).trim());
            }
            if (!propertiesObject.isNull(JSON_KEY_RELEASE_NOTES)) {
               setReleaseNotes(propertiesObject.getString(
                     JSON_KEY_RELEASE_NOTES).trim());
            }
            if (!propertiesObject.isNull(JSON_KEY_USER_IDS)) {
               JSONArray jsonArray = propertiesObject
                     .getJSONArray(JSON_KEY_USER_IDS);
               if (jsonArray != null && jsonArray.length() > 0) {
                  ArrayList<String> ids = new ArrayList<String>();
                  for (int i = 0; i < jsonArray.length(); i++) {
                     ids.add(jsonArray.getString(i));
                  }
                  setUserIds(ids);
               }
            }
            if (!propertiesObject.isNull(JSON_KEY_USER_STATUS)) {
               setUserStatus(propertiesObject.getInt(JSON_KEY_USER_STATUS));
            }
            if (!propertiesObject.isNull(JSON_KEY_VERSION_CODE)) {
               setVersionCode(propertiesObject.getInt(JSON_KEY_VERSION_CODE));
            }
            if (!propertiesObject.isNull(JSON_KEY_VERSION_NAME)) {
               setVersionName(propertiesObject.getString(JSON_KEY_VERSION_NAME)
                     .trim());
            }
         } catch (JSONException e) {

         }
      }
   }

   public String getApkUrl() {
      return this.apkUrl;
   }

   public AppProperties setApkUrl(String apkUrl) {
      this.apkUrl = apkUrl;
      return this;
   }

   public int getMinVersionCode() {
      return minVersionCode;
   }

   public AppProperties setMinVersionCode(int minVersionCode) {
      this.minVersionCode = minVersionCode;
      return this;
   }

   public String getReleaseNotes() {
      return this.releaseNotes;
   }

   public AppProperties setReleaseNotes(String releaseNotes) {
      this.releaseNotes = releaseNotes;
      return this;
   }

   public String getSplashImageUrl() {
      return this.splashImageUrl;
   }

   public AppProperties setSplashImageUrl(String imageUrl) {
      this.splashImageUrl = imageUrl;
      return this;
   }

   public List<String> getUserIds() {
      return userIds;
   }

   public AppProperties setUserIds(List<String> userIds) {
      this.userIds = userIds;
      return this;
   }

   public int getUserStatus() {
      return this.userStatus;
   }

   public AppProperties setUserStatus(int userStatus) {
      this.userStatus = userStatus;
      return this;
   }

   public int getVersionCode() {
      return versionCode;
   }

   public AppProperties setVersionCode(int versionCode) {
      this.versionCode = versionCode;
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
