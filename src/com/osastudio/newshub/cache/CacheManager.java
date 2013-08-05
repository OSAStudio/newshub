package com.osastudio.newshub.cache;

import com.osastudio.newshub.data.base.NewsBaseAbstract;

import android.content.Context;

public class CacheManager {

   private Context mContext = null;
   private NewsBaseAbstractCache mNewsBaseAbstractCache;
   private NewsAbstractCache mNewsAbstractCache;
   private SubscriptionAbstractCache mSubscriptionAbstractCache;

   public CacheManager(Context context) {
      mContext = context;

      mNewsBaseAbstractCache = new NewsBaseAbstractCache<NewsBaseAbstract>(
            context);
      mNewsAbstractCache = new NewsAbstractCache(context);
      mSubscriptionAbstractCache = new SubscriptionAbstractCache(context);
   }
   
   public NewsBaseAbstractCache getNewsBaseAbstractCache() {
      return mNewsBaseAbstractCache;
   }

   public NewsAbstractCache getNewsAbstractCache() {
      return mNewsAbstractCache;
   }

   public SubscriptionAbstractCache getSubscriptionAbstractCache() {
      return mSubscriptionAbstractCache;
   }

   public void cleanup() {
      if (mNewsBaseAbstractCache != null) {
         mNewsBaseAbstractCache.clean();
         mNewsBaseAbstractCache = null;
      }
      
      if (mNewsAbstractCache != null) {
         mNewsAbstractCache.clean();
         mNewsAbstractCache = null;
      }

      if (mSubscriptionAbstractCache != null) {
         mSubscriptionAbstractCache.clean();
         mSubscriptionAbstractCache = null;
      }

      // sInstance = null;
   }

}
