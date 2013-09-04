package com.osastudio.newshub.library;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class PreferenceManager implements AppSettings {

   private Context mContext = null;

   public class PreferenceFiles {
      public static final String APP_SETTINGS = "app_settings";
   }

   public class PreferenceItems {
      public static final String FIRST_RUN = "first_run";
      public static final String FONT_SIZE = "font_size";
      public static final String AUTO_LOADING_PICTURE = "auto_loading_picture";
      public static final String USER_ID = "user_id";
      public static final String MESSAGE_USER_IDS = "message_user_ids";
      public static final String MAIN_SERVER = "main_server";
      public static final String BACKUP_SERVER = "backup_server";
   }

   public PreferenceManager(Context context) {
      mContext = context;
      init();
   }

   private void init() {
      SharedPreferences prefs = mContext.getSharedPreferences(
            PreferenceFiles.APP_SETTINGS, Context.MODE_PRIVATE);
   }

   public void cleanup() {

   }

   public SharedPreferences getPrefs(String fileName) {
      SharedPreferences prefs = null;
      if (!TextUtils.isEmpty(fileName)) {
         prefs = mContext.getSharedPreferences(fileName, Context.MODE_PRIVATE);
      }
      return prefs;
   }

   public String getStringValue(SharedPreferences prefs, String key,
         String defaultValue) {
      if (prefs != null && !TextUtils.isEmpty(key)) {
         return prefs.getString(key, defaultValue);
      }
      return defaultValue;
   }

   public boolean setStringValue(SharedPreferences prefs, String key,
         String value) {
      if (prefs != null && !TextUtils.isEmpty(key)) {
         return prefs.edit().putString(key, value).commit();
      }
      return false;
   }

   public boolean getBooleanValue(SharedPreferences prefs, String key,
         boolean defaultValue) {
      if (prefs != null && !TextUtils.isEmpty(key)) {
         return prefs.getBoolean(key, defaultValue);
      }
      return defaultValue;
   }

   public boolean setBooleanValue(SharedPreferences prefs, String key,
         boolean value) {
      if (prefs != null && !TextUtils.isEmpty(key)) {
         return prefs.edit().putBoolean(key, value).commit();
      }
      return false;
   }

   public int getIntegerValue(SharedPreferences prefs, String key,
         int defaultValue) {
      if (prefs != null && !TextUtils.isEmpty(key)) {
         return prefs.getInt(key, defaultValue);
      }
      return defaultValue;
   }

   public boolean setIntegerValue(SharedPreferences prefs, String key, int value) {
      if (prefs != null && !TextUtils.isEmpty(key)) {
         return prefs.edit().putInt(key, value).commit();
      }
      return false;
   }

   public long getLongValue(SharedPreferences prefs, String key,
         long defaultValue) {
      if (prefs != null && !TextUtils.isEmpty(key)) {
         return prefs.getLong(key, defaultValue);
      }
      return defaultValue;
   }

   public boolean setLongValue(SharedPreferences prefs, String key, long value) {
      if (prefs != null && !TextUtils.isEmpty(key)) {
         return prefs.edit().putLong(key, value).commit();
      }
      return false;
   }

   private SharedPreferences getAppSettingsPrefs() {
      return getPrefs(PreferenceFiles.APP_SETTINGS);
   }

   public boolean isFirstRun() {
      return getBooleanValue(getAppSettingsPrefs(), PreferenceItems.FIRST_RUN,
            true);
   }

   public boolean setFirstRun(boolean value) {
      return setBooleanValue(getAppSettingsPrefs(), PreferenceItems.FIRST_RUN,
            value);
   }

   public int getFontSize() {
      return getIntegerValue(getAppSettingsPrefs(), PreferenceItems.FONT_SIZE,
            DEFAULT_FONT_SIZE);
   }

   public boolean setFontSize(int fontSize) {
      return setIntegerValue(getAppSettingsPrefs(), PreferenceItems.FONT_SIZE,
            fontSize);
   }

   public boolean isAutoLoadingPictureEnabled() {
      return getBooleanValue(getAppSettingsPrefs(),
            PreferenceItems.AUTO_LOADING_PICTURE, AUTO_LOADING_PICTURE);
   }

   public boolean enableAutoLoadingPicture(boolean enable) {
      return setBooleanValue(getAppSettingsPrefs(),
            PreferenceItems.AUTO_LOADING_PICTURE, enable);
   }

   public String getUserId() {
      return getStringValue(getAppSettingsPrefs(), PreferenceItems.USER_ID, "");
   }

   public boolean setUserId(String value) {
      return setStringValue(getAppSettingsPrefs(), PreferenceItems.USER_ID,
            value);
   }

   public String getMessageScheduleUserIds() {
      return getStringValue(getAppSettingsPrefs(),
            PreferenceItems.MESSAGE_USER_IDS, "");
   }

   public boolean setMessageScheduleUserIds(String value) {
      return setStringValue(getAppSettingsPrefs(),
            PreferenceItems.MESSAGE_USER_IDS, value);
   }

   public String getMessageScheduleByUserId(String userId) {
      return getStringValue(getAppSettingsPrefs(), userId, "");
   }

   public boolean setMessageScheduleByUserId(String userId, String value) {
      return setStringValue(getAppSettingsPrefs(), userId, value);
   }

   public String getMainServer() {
      return getStringValue(getAppSettingsPrefs(), PreferenceItems.MAIN_SERVER,
            "");
   }

   public boolean setMainServer(String value) {
      return setStringValue(getAppSettingsPrefs(), PreferenceItems.MAIN_SERVER,
            value);
   }

   public String getBackupServer() {
      return getStringValue(getAppSettingsPrefs(),
            PreferenceItems.BACKUP_SERVER, "");
   }

   public boolean setBackupServer(String value) {
      return setStringValue(getAppSettingsPrefs(),
            PreferenceItems.BACKUP_SERVER, value);
   }

}
