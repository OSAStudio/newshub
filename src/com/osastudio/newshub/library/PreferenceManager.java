package com.osastudio.newshub.library;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class PreferenceManager implements AppSettings {

   private Context mContext = null;
   private HashMap<String, SharedPreferences> mPrefsMap = null;

   public class PreferenceFiles {
      public static final String APP_SETTINGS = "app_settings";
   }

   public class PreferenceItems {
      public static final String FIRST_RUN = "first_run";
      public static final String FONT_SIZE = "font_size";
      public static final String AUTO_LOADING_PICTURE = "auto_loading_picture";
      public static final String USER_ID = "user_id";
      public static final String MESSAGE_SCHEDULE = "message_schedule";
   }

   public PreferenceManager(Context context) {
      mContext = context;
      init();
   }

   private void init() {
      mPrefsMap = new HashMap<String, SharedPreferences>();
      SharedPreferences prefs = mContext.getSharedPreferences(
            PreferenceFiles.APP_SETTINGS, Context.MODE_PRIVATE);
      mPrefsMap.put(PreferenceFiles.APP_SETTINGS, prefs);
   }

   public void cleanup() {
      mPrefsMap.clear();
   }

   public SharedPreferences getPrefs(String fileName) {
      SharedPreferences prefs = null;
      if (!TextUtils.isEmpty(fileName)) {
         prefs = mPrefsMap.get(fileName);
         if (prefs == null) {
            prefs = mContext.getSharedPreferences(fileName,
                  Context.MODE_PRIVATE);
         }
      }
      return prefs;
   }

   private SharedPreferences getAppSettingsPrefs() {
      return getPrefs(PreferenceFiles.APP_SETTINGS);
   }

   public boolean isFirstRun() {
      SharedPreferences prefs = getAppSettingsPrefs();
      if (prefs != null) {
         return prefs.getBoolean(PreferenceItems.FIRST_RUN, true);
      }
      return true;
   }

   public boolean setFirstRun(boolean value) {
      SharedPreferences prefs = getAppSettingsPrefs();
      if (prefs != null) {
         return prefs.edit().putBoolean(PreferenceItems.FIRST_RUN, value)
               .commit();
      }
      return false;
   }

   public int getFontSize() {
      SharedPreferences prefs = getAppSettingsPrefs();
      if (prefs != null) {
         return prefs.getInt(PreferenceItems.FONT_SIZE, DEFAULT_FONT_SIZE);
      }
      return DEFAULT_FONT_SIZE;
   }

   public boolean setFontSize(int fontSize) {
      SharedPreferences prefs = getAppSettingsPrefs();
      if (prefs != null) {
         return prefs.edit().putInt(PreferenceItems.FONT_SIZE, fontSize)
               .commit();
      }
      return false;
   }

   public boolean isAutoLoadingPictureEnabled() {
      SharedPreferences prefs = getAppSettingsPrefs();
      if (prefs != null) {
         return prefs.getBoolean(PreferenceItems.AUTO_LOADING_PICTURE,
               AUTO_LOADING_PICTURE);
      }
      return AUTO_LOADING_PICTURE;
   }

   public boolean enableAutoLoadingPicture(boolean enable) {
      SharedPreferences prefs = getAppSettingsPrefs();
      if (prefs != null) {
         return prefs.edit()
               .putBoolean(PreferenceItems.AUTO_LOADING_PICTURE, enable)
               .commit();
      }
      return false;
   }

   public String getUserId() {
      SharedPreferences prefs = getAppSettingsPrefs();
      if (prefs != null) {
         return prefs.getString(PreferenceItems.USER_ID, "");
      }
      return "";
   }

   public boolean setUserId(String value) {
      SharedPreferences prefs = getAppSettingsPrefs();
      if (prefs != null) {
         return prefs.edit().putString(PreferenceItems.USER_ID, value).commit();
      }
      return false;
   }

   public String getMessageScheduleString() {
      SharedPreferences prefs = getAppSettingsPrefs();
      if (prefs != null) {
         return prefs.getString(PreferenceItems.MESSAGE_SCHEDULE, "");
      }
      return "";
   }

   public boolean setMessageScheduleString(String value) {
      SharedPreferences prefs = getAppSettingsPrefs();
      if (prefs != null) {
         return prefs.edit().putString(PreferenceItems.MESSAGE_SCHEDULE, value)
               .commit();
      }
      return false;
   }

}
