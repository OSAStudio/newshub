package com.osastudio.newshub;

import com.osastudio.newshub.cache.CacheManager;
import com.osastudio.newshub.cache.NewsAbstractCache;
import com.osastudio.newshub.cache.SubscriptionAbstractCache;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

public class NewsBaseActivity extends Activity {

   private NewsService mNewsService;

   private ServiceConnection mNewsServiceConn = new ServiceConnection() {
      public void onServiceDisconnected(ComponentName name) {

      }

      @Override
      public void onServiceConnected(ComponentName name, IBinder service) {
         mNewsService = ((NewsService.NewsBinder) service).getService();
      }
   };

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
   }

   @Override
   protected void onResume() {
      super.onResume();
   }

   @Override
   protected void onDestroy() {
      super.onDestroy();

      getActivityStack().remove(this);

      if (getActivityStack().getCount() <= 0) {
         ((NewsApp) getApplication()).cleanupEnvironment();
      }
   }

   protected NewsService getNewsService() {
      return mNewsService;
   }
   
   protected ComponentName startNewsService() {
      return startService(new Intent(getApplicationContext(), NewsService.class));
   }

   protected boolean stopNewsService() {
      return stopService(new Intent(getApplicationContext(), NewsService.class));
   }

   protected boolean bindNewsService() {
      return bindService(new Intent(this, NewsService.class), mNewsServiceConn,
            Context.BIND_AUTO_CREATE);
   }

   protected void unbindNewService() {
      if (mNewsServiceConn != null) {
         unbindService(mNewsServiceConn);
      }
   }
   
   protected void upgradeApk(String apkUrl) {
      if (mNewsService != null) {
         mNewsService.upgradeApk(apkUrl);
      }
   }

   protected void showToast(Context context, String msg) {
      Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
   }

   protected ActivityStack getActivityStack() {
      return ((NewsApp) getApplication()).getActivityStack();
   }

   protected CacheManager getCacheManager() {
      return ((NewsApp) getApplication()).getCacheManager();
   }

   protected NewsAbstractCache getNewsAbstractCache() {
      return getCacheManager().getNewsAbstractCache();
   }

   protected SubscriptionAbstractCache getSubscriptionAbstractCache() {
      return getCacheManager().getSubscriptionAbstractCache();
   }

}
