package com.osastudio.newshub.cache;

import android.content.Context;

public class CacheManager {

//   private static CacheManager sInstance = null;
   
   private Context mContext = null;
   private NewsAbstractCache mNewsAbstractCache;
   
//   public static CacheManager getInstance(Context context) {
//      if (sInstance == null) {
//         synchronized (CacheManager.class) {
//            if (sInstance == null) {
//               sInstance = new CacheManager(context);
//            }
//         }
//      }
//      return sInstance;
//   }
   
   public CacheManager(Context context) {
      mContext = context;
      
      mNewsAbstractCache = new NewsAbstractCache(context);
   }
   
   public NewsAbstractCache getNewsAbstractCache() {
      return this.mNewsAbstractCache;
   }
   
   public void cleanup() {
      if (mNewsAbstractCache != null) {
         mNewsAbstractCache.clean();
         mNewsAbstractCache = null;
      }
      
//      sInstance = null;
   }
   
}
