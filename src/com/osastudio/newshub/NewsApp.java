package com.osastudio.newshub;

import java.util.ArrayList;

import com.osastudio.newshub.cache.CacheManager;

import android.app.Activity;
import android.app.Application;
import android.util.DisplayMetrics;

public class NewsApp extends Application {
   final public static boolean IS_DEBUG = false;
   private ActivityStack mActivityStack;
   private CacheManager mCacheManager;
   private String mCurrentUserId = null;
   
   @Override
   public void onCreate() {
      super.onCreate();
   }
   
   public ActivityStack getActivityStack() {
      return mActivityStack;
   }
   
   public CacheManager getCacheManager() {
      return mCacheManager;
   }
   
   public void prepareEnvironment() {
      if (mActivityStack == null) {
         mActivityStack = new ActivityStack();
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
      
      if (mCacheManager != null) {
         mCacheManager.cleanup();
         mCacheManager = null;
      }
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
	   mCurrentUserId = userid;
   }
   
   public String getCurrentUserId() {
	   if (IS_DEBUG) {
		   mCurrentUserId = "2";
	   }
	   return mCurrentUserId;
   }
   
   
   //////////////////pp add for cache
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
