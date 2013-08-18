package com.osastudio.newshub;

import com.osastudio.newshub.cache.CacheManager;
import com.osastudio.newshub.cache.NewsAbstractCache;
import com.osastudio.newshub.cache.NewsBaseAbstractCache;
import com.osastudio.newshub.cache.SubscriptionAbstractCache;
import com.osastudio.newshub.data.AppDeadline;
import com.osastudio.newshub.library.PreferenceManager;
import com.osastudio.newshub.utils.UIUtils;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;

public class NewsBaseActivity extends Activity {

   protected INewsService mNewsService;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      if (getActivityStack() == null) {
         ((NewsApp) getApplication()).prepareEnvironment();
         startNewsService();
      }

      getActivityStack().push(this);
   }

   protected void onPause() {
      super.onPause();
      MobclickAgent.onPause(this);
   }

   @Override
   protected void onResume() {
      super.onResume();
      MobclickAgent.onResume(this);
   }

   @Override
   protected void onDestroy() {
      super.onDestroy();

      getActivityStack().remove(this);

      if (getActivityStack().getCount() <= 0) {
         ((NewsApp) getApplication()).cleanupEnvironment();
      }
   }

   protected INewsService getNewsService() {
      return mNewsService;
   }

   protected INewsService setNewsService(INewsService service) {
      mNewsService = service;
      return service;
   }

   protected ComponentName startNewsService() {
      return startService(new Intent(getApplicationContext(), NewsService.class));
   }

   protected boolean stopNewsService() {
      return stopService(new Intent(getApplicationContext(), NewsService.class));
   }

   protected boolean bindNewsService(ServiceConnection conn) {
      return bindService(new Intent(this, NewsService.class), conn,
            Context.BIND_AUTO_CREATE);
   }

   protected void unbindNewService(ServiceConnection conn) {
      if (conn != null) {
         unbindService(conn);
      }
   }

   protected void showToast(Context context, String msg) {
      UIUtils.showToast(context, msg);
   }

   protected ActivityStack getActivityStack() {
      return ((NewsApp) getApplication()).getActivityStack();
   }

   protected PreferenceManager getPrefsManager() {
      return ((NewsApp) getApplication()).getPrefsManager();
   }

   protected CacheManager getCacheManager() {
      return ((NewsApp) getApplication()).getCacheManager();
   }

   protected NewsBaseAbstractCache getNewsBaseAbstractCache() {
      return getCacheManager().getNewsBaseAbstractCache();
   }

   protected NewsAbstractCache getNewsAbstractCache() {
      return getCacheManager().getNewsAbstractCache();
   }

   protected SubscriptionAbstractCache getSubscriptionAbstractCache() {
      return getCacheManager().getSubscriptionAbstractCache();
   }

}
