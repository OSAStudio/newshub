package com.osastudio.newshub;

import java.util.ArrayList;

import com.osastudio.newshub.cache.CacheManager;
import com.osastudio.newshub.data.AppDeadline;
import com.osastudio.newshub.library.PreferenceManager;
import com.osastudio.newshub.net.NewsBaseApi;
import com.osastudio.newshub.utils.Utils;

import android.app.Activity;
import android.app.Application;
import android.util.DisplayMetrics;

public class NewsApp extends Application {

   private ActivityStack mActivityStack;
   private PreferenceManager mPrefsManager;
   private CacheManager mCacheManager;
   private String mCurrentUserId = null;
   private AppDeadline mAppDeadline;

   @Override
   public void onCreate() {
      super.onCreate();
      
      Utils.enableLogging(false);
      NewsBaseApi.enableDebug(false);
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

   public void setCurrentUserId(String userId) {
      if (userId == null || mCurrentUserId == null
            || !userId.equals(mCurrentUserId)) {
         mCurrentUserId = userId;
         getPrefsManager().setUserId(userId);
      }
   }

   public String getCurrentUserId() {
      if (mCurrentUserId == null) {
         mCurrentUserId = getPrefsManager().getUserId();
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
