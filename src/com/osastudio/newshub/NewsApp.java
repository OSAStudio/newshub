package com.osastudio.newshub;

import java.util.ArrayList;

import com.osastudio.newshub.cache.CacheManager;
import com.osastudio.newshub.data.AppDeadline;
import com.osastudio.newshub.library.PreferenceManager;
import com.osastudio.newshub.library.PreferenceManager.PreferenceFiles;
import com.osastudio.newshub.library.PreferenceManager.PreferenceItems;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;

public class NewsApp extends Application {

   final public static boolean IS_DEBUG = false;

   private ActivityStack mActivityStack;
   private PreferenceManager mPrefsManager;
   private CacheManager mCacheManager;
   private String mCurrentUserId = null;
   private AppDeadline mAppDeadline;

   @Override
   public void onCreate() {
      super.onCreate();
   }

   public ActivityStack getActivityStack() {
      if (mActivityStack == null) {
         mActivityStack = new ActivityStack();
      }
      return mActivityStack;
   }

   public PreferenceManager getPrefsManager() {
      if (mPrefsManager == null) {
         mPrefsManager = new PreferenceManager(this);
      }
      return mPrefsManager;
   }

   public CacheManager getCacheManager() {
      if (mCacheManager == null) {
         mCacheManager = new CacheManager(this);
      }
      return mCacheManager;
   }

   public void prepareEnvironment() {
      if (mActivityStack == null) {
         mActivityStack = new ActivityStack();
      }

      if (mPrefsManager == null) {
         mPrefsManager = new PreferenceManager(this);
      }

      if (mCacheManager == null) {
         mCacheManager = new CacheManager(this);
      }
   }

   public void cleanupEnvironment() {
      if (mActivityStack != null) {
         mActivityStack.cleanup();
         mActivityStack = null;
      }

      if (mPrefsManager != null) {
         mPrefsManager.cleanup();
         mPrefsManager = null;
      }

      if (mCacheManager != null) {
         mCacheManager.cleanup();
         mCacheManager = null;
      }
   }

   public AppDeadline getAppDeadline() {
      return mAppDeadline;
   }

   public void setAppDeadline(AppDeadline deadline) {
      mAppDeadline = deadline;
   }

   @Override
   public void onTerminate() {

   }

   public static int getScreenWidth(Activity context) {
      return context.getWindowManager().getDefaultDisplay().getWidth();
   }

   public static int getScreenHeight(Activity context) {
      return context.getWindowManager().getDefaultDisplay().getHeight();
   }

   public static float getScreenDensity(Activity context) {
      DisplayMetrics dm = new DisplayMetrics();
      context.getWindowManager().getDefaultDisplay().getMetrics(dm);
      return dm.density;
   }

   public static DisplayMetrics getDisplayMetrics(Activity context) {
      DisplayMetrics dm = new DisplayMetrics();
      context.getWindowManager().getDefaultDisplay().getMetrics(dm);
      return dm;
   }

   public void setCurrentUserId(String userid) {
      if (userid == null || mCurrentUserId == null
            || !userid.equals(mCurrentUserId)) {
         mCurrentUserId = userid;
         SharedPreferences prefs = getSharedPreferences(
               PreferenceFiles.APP_SETTINGS, Context.MODE_PRIVATE);
         if (prefs != null) {
            prefs.edit().putString(PreferenceItems.USER_ID, userid).commit();
         }
      }
   }

   public String getCurrentUserId() {
      if (mCurrentUserId == null) {
         SharedPreferences prefs = getSharedPreferences(
               PreferenceFiles.APP_SETTINGS, Context.MODE_PRIVATE);
         if (prefs != null) {
            mCurrentUserId = prefs.getString(PreferenceItems.USER_ID, null);
         }
      }
      return mCurrentUserId;
   }

   // ////////////////pp add for cache
   public static class TempCacheData {
      public TempCacheData(String id) {
         mId = id;
      }

      String mId;
   }

   private ArrayList<TempCacheData> mTempCache = null;

   public void setTempCache(ArrayList<TempCacheData> cache) {
      mTempCache = cache;
   }

   public ArrayList<TempCacheData> getTempCache() {
      return mTempCache;
   }

}
