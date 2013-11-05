package com.osastudio.newshub.data;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.pm.PackageInfo;
import android.os.Parcel;
import android.os.Parcelable;

import com.osastudio.newshub.data.base.NewsBaseObject;
import com.osastudio.newshub.data.user.UserStatus;

/**
 * Application information & kinds of properties, for example splash picture,
 * application release notes, upgrade information, main entry server and backup
 * server configurations etc.
 * 
 * @author Rujin Xue
 * 
 */
public class AppProperties extends NewsBaseObject implements Parcelable,
      UserStatus {

   public static final String EXTRA_APP_PROPERTIES = "app_properties";

   public static final String JSON_KEY_LOGIN_INFO = "list";
   public static final String JSON_KEY_APK_URL = "android_url";
   public static final String JSON_KEY_MIN_VERSION_CODE = "min_version_code";
   public static final String JSON_KEY_SPLASH_IMAGE_URL = "picture_url";
   public static final String JSON_KEY_RELEASE_NOTES = "update_note";
   public static final String JSON_KEY_USER_IDS = "student_ids";
   public static final String JSON_KEY_USER_STATUS = "user_status";
   public static final String JSON_KEY_VERSION_CODE = "version_code";
   public static final String JSON_KEY_VERSION_NAME = "version_id";
   public static final String JSON_KEY_MAIN_SERVER = "server_url";
   public static final String JSON_KEY_BACKUP_SERVER = "back_server_url";
   public static final String JSON_KEY_PROPERTIES = "list";

   private String apkUrl = "";
   private int minVersionCode = 0;
   private String releaseNotes = "";
   private String splashImageUrl = "";
   private List<String> userIds = new ArrayList<String>();
   private int userStatus = 0;
   private int versionCode = 0;
   private String versionName = "";
   private String mainServer = "";
   private String backupServer = "";

   public AppProperties() {

   }

   public AppProperties(Parcel src) {
      this.apkUrl = src.readString();
      this.minVersionCode = src.readInt();
      this.releaseNotes = src.readString();
      this.splashImageUrl = src.readString();
      src.readStringList(this.userIds);
      this.userStatus = src.readInt();
      this.versionCode = src.readInt();
      this.versionName = src.readString();
      this.mainServer = src.readString();
      this.backupServer = src.readString();
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
            if (!propertiesObject.isNull(JSON_KEY_MAIN_SERVER)) {
               setMainServer(propertiesObject.getString(JSON_KEY_MAIN_SERVER)
                     .trim());
            }
            if (!propertiesObject.isNull(JSON_KEY_BACKUP_SERVER)) {
               setBackupServer(propertiesObject.getString(
                     JSON_KEY_BACKUP_SERVER).trim());
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
      return this.minVersionCode;
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
      return this.userIds;
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
      return this.versionCode;
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

   public boolean isUpgradeAvailable(PackageInfo pkgInfo) {
      return pkgInfo.versionCode < this.versionCode;
   }

   public boolean isUpgradeNecessary(PackageInfo pkgInfo) {
      return pkgInfo.versionCode < this.minVersionCode;
   }

   public String getMainServer() {
      return this.mainServer;
   }

   public AppProperties setMainServer(String url) {
      this.mainServer = url;
      return this;
   }

   public String getBackupServer() {
      return this.backupServer;
   }

   public AppProperties setBackupServer(String url) {
      this.backupServer = url;
      return this;
   }

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dst, int flags) {
      dst.writeString(this.apkUrl);
      dst.writeInt(this.minVersionCode);
      dst.writeString(this.releaseNotes);
      dst.writeString(this.splashImageUrl);
      dst.writeStringList(this.userIds);
      dst.writeInt(this.userStatus);
      dst.writeInt(this.versionCode);
      dst.writeString(this.versionName);
      dst.writeString(this.mainServer);
      dst.writeString(this.backupServer);
   }

   public static final Parcelable.Creator<AppProperties> CREATOR = new Creator<AppProperties>() {
      @Override
      public AppProperties createFromParcel(Parcel src) {
         return new AppProperties(src);
      }

      @Override
      public AppProperties[] newArray(int size) {
         return new AppProperties[size];
      }
   };

}
