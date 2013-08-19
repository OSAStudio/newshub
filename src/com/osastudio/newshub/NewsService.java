package com.osastudio.newshub;

import com.huadi.azker_phone.R;
import com.osastudio.newshub.data.AppDeadline;
import com.osastudio.newshub.data.AppProperties;
import com.osastudio.newshub.data.NewsMessageList;
import com.osastudio.newshub.data.NewsMessageScheduler;
import com.osastudio.newshub.library.PreferenceManager;
import com.osastudio.newshub.library.UpgradeManager;
import com.osastudio.newshub.net.AppDeadlineApi;
import com.osastudio.newshub.net.AppPropertiesApi;
import com.osastudio.newshub.net.NewsMessageApi;
import com.osastudio.newshub.utils.Utils;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

public class NewsService extends Service {

   private static final String TAG = "NewsService";

   private static final String ACTION_PULL_NEWS_MESSAGE = "com.osastudio.newshub.action.PULL_NEWS_MESSAGE";

   private Handler mHandler = new Handler();
   private UpgradeManager mUpgradeManager;
   private boolean mCheckingNewVersion = false;
   private AppDeadline mAppDeadline;
   private NewsReceiver mNewsReceiver;

   @Override
   public int onStartCommand(Intent intent, int flags, int startId) {
      Utils.logi(TAG, "____________onStartCommand");
      return START_STICKY;
   }

   @Override
   public void onCreate() {
      super.onCreate();
      Utils.logi(TAG, "____________onCreate");

      registerNewsReceiver();

      mHandler.postDelayed(new Runnable() {
         public void run() {
            mHandler.postDelayed(this, 10000);
            Utils.logi(TAG,
                  "______________I am alive..." + System.currentTimeMillis());
         }
      }, 10000);
   }

   @Override
   public IBinder onBind(Intent intent) {
      Utils.logi(TAG, "____________onBind");
      return mBinder;
   }

   @Override
   public boolean onUnbind(Intent intent) {
      Utils.logi(TAG, "____________onUnbind");
      return super.onUnbind(intent);
   }

   @Override
   public void onDestroy() {
      super.onDestroy();
      Utils.logi(TAG, "____________onDestroy");

      unregisterNewsReceiver();

      Intent selfIntent = new Intent();
      selfIntent.setClass(this, NewsService.class);
      startService(selfIntent);
   }

   private final INewsService.Stub mBinder = new INewsService.Stub() {

      @Override
      public boolean isDownloadingApk() {
         return (mUpgradeManager != null) ? mUpgradeManager.isDownloading()
               : false;
      }

      @Override
      public void downloadApk(String apkUrl) {
         if (mUpgradeManager == null) {
            mUpgradeManager = new UpgradeManager(NewsService.this);
            mUpgradeManager.setHanlder(mHandler);
         }
         mUpgradeManager.download(apkUrl);
      }

      public boolean isCheckingNewVersion() {
         return mCheckingNewVersion;
      }

      @Override
      public void checkNewVersion() {
         if (!mCheckingNewVersion) {
            mCheckingNewVersion = true;
            new AppPropertiesTask().execute(NewsService.this);
         }
      }

      @Override
      public boolean hasNewVersion(AppProperties properties,
            boolean notifyIfNoNewVersion) {
         PackageInfo pkgInfo = Utils.getPackageInfo(NewsService.this);
         if (properties.isUpgradeAvailable(pkgInfo)
               || properties.isUpgradeNecessary(pkgInfo)) {
            Intent intent = new Intent();
            intent.setClass(NewsService.this, UpgradeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                  | Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
            intent.putExtra(AppProperties.EXTRA_APP_PROPERTIES, properties);
            startActivity(intent);
            return true;
         } else {
            if (notifyIfNoNewVersion) {
               Toast.makeText(NewsService.this,
                     getString(R.string.no_new_version), Toast.LENGTH_LONG)
                     .show();
            }
            return false;
         }
      }

      @Override
      public boolean hasExpired() {
         return (mAppDeadline != null) ? mAppDeadline.hasExpired()
               : new AppDeadline().hasExpired();
      }

      @Override
      public void checkAppDeadline() {
         new AppDeadlineTask().execute(NewsService.this);
      }

      void checkNewsMessageScheduler(String userId) {
         String str = ((NewsApp) NewsService.this.getApplication())
               .getPrefsManager().getMessageSchedulerString();
         NewsMessageScheduler scheduler = NewsMessageScheduler
               .parseFormattedString(str);
         if (scheduler != null) {
            if (scheduler.isToday() && scheduler.isPullingAllowed()) {
               int count = scheduler.getCount();
               if (count == 0) {
                  
               } else if (count == 1) {
                  
               } else if (count == 3) {
                  
               }
            }
         } else {
            requestNewsMessageScheduler(userId);
         }
      }
      
      void requestNewsMessageScheduler(String userId) {
         new NewsMessageSchedulerTask(userId).execute(NewsService.this);
      }

      void requestNewsMessageList(String userId) {
         new NewsMessageListTask(userId).execute(NewsService.this);
      }

   };

   private class NewsReceiver extends BroadcastReceiver {

      @Override
      public void onReceive(Context context, Intent intent) {
         String action = intent.getAction();
         Utils.logi(TAG, "_______________onReceive: " + action);
         if (ACTION_PULL_NEWS_MESSAGE.equals(action)) {

         }
      }

   };

   private void registerNewsReceiver() {
      if (mNewsReceiver == null) {
         mNewsReceiver = new NewsReceiver();
      }
      IntentFilter filter = new IntentFilter();
      filter.addAction(ACTION_PULL_NEWS_MESSAGE);
      registerReceiver(mNewsReceiver, filter);
   }

   private void unregisterNewsReceiver() {
      if (mNewsReceiver != null) {
         unregisterReceiver(mNewsReceiver);
         mNewsReceiver = null;
      }
   }

   private class AppPropertiesTask extends
         AsyncTask<Context, Integer, AppProperties> {

      @Override
      protected AppProperties doInBackground(Context... params) {
         return AppPropertiesApi.getAppProperties(params[0]);
      }

      @Override
      protected void onPostExecute(AppProperties result) {
         mCheckingNewVersion = false;
         if (result != null) {
            try {
               mBinder.hasNewVersion(result, true);
            } catch (Exception e) {
               // e.printStackTrace();
            }
         }
      }

   }

   private class AppDeadlineTask extends
         AsyncTask<Context, Integer, AppDeadline> {

      private Context context;

      @Override
      protected AppDeadline doInBackground(Context... params) {
         this.context = params[0];
         return AppDeadlineApi.getAppDeadline(params[0]);
      }

      @Override
      protected void onPostExecute(AppDeadline result) {
         mAppDeadline = result;
         ((NewsApp) this.context.getApplicationContext())
               .setAppDeadline(result);
      }
   }

   private class NewsMessageSchedulerTask extends
         AsyncTask<Context, Integer, NewsMessageScheduler> {

      private Context context;
      private String userId;

      public NewsMessageSchedulerTask(String userId) {
         this.userId = userId;
      }

      @Override
      protected NewsMessageScheduler doInBackground(Context... params) {
         this.context = params[0];
         return NewsMessageApi.getNewsMessageScheduler(params[0], this.userId);
      }

      @Override
      protected void onPostExecute(NewsMessageScheduler result) {
         if (result != null) {

         }
      }
   }

   private class NewsMessageListTask extends
         AsyncTask<Context, Integer, NewsMessageList> {

      private Context context;
      private String userId;

      public NewsMessageListTask(String userId) {
         this.userId = userId;
      }

      @Override
      protected NewsMessageList doInBackground(Context... params) {
         this.context = params[0];
         return NewsMessageApi.getNewsMessageList(params[0], this.userId);
      }

      @Override
      protected void onPostExecute(NewsMessageList result) {

      }
   }

}
