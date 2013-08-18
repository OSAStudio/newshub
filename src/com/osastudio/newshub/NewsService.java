package com.osastudio.newshub;

import com.huadi.azker_phone.R;
import com.osastudio.newshub.data.AppDeadline;
import com.osastudio.newshub.data.AppProperties;
import com.osastudio.newshub.library.UpgradeManager;
import com.osastudio.newshub.net.AppDeadlineApi;
import com.osastudio.newshub.net.AppPropertiesApi;
import com.osastudio.newshub.utils.Utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

public class NewsService extends Service {

   private static final String TAG = "NewsService";

   private Handler mHandler = new Handler();
   private UpgradeManager mUpgradeManager;
   private boolean mCheckingNewVersion = false;
   private AppDeadline mAppDeadline;

   @Override
   public int onStartCommand(Intent intent, int flags, int startId) {
      Utils.logi(TAG, "____________onStartCommand");
      return START_STICKY;
   }

   @Override
   public void onCreate() {
      super.onCreate();
      Utils.logi(TAG, "____________onCreate");
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

   };

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

}
