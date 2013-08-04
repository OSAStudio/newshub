package com.osastudio.newshub;

import com.osastudio.newshub.library.UpgradeManager;
import com.osastudio.newshub.utils.Utils;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

public class NewsService extends Service {

   private static final String TAG = "NewsService";

   private final IBinder mBinder = new NewsBinder();
   private Handler mHandler = new Handler();
   private UpgradeManager mUpgradeManager;
   
   @Override
   public int onStartCommand(Intent intent, int flags, int startId) {
      Utils.logi(TAG, "____________onStartCommand: "
            + intent.getComponent().getClassName());
      return super.onStartCommand(intent, flags, startId);
   }

   @Override
   public void onCreate() {
      super.onCreate();
      Utils.logi(TAG, "____________onCreate");
   }

   @Override
   public IBinder onBind(Intent intent) {
      Utils.logi(TAG, "____________onBind: "
            + intent.getComponent().getClassName());
      return mBinder;
   }

   @Override
   public boolean onUnbind(Intent intent) {
      Utils.logi(TAG, "____________onUnbind: "
            + intent.getComponent().getClassName());
      return super.onUnbind(intent);
   }

   @Override
   public void onDestroy() {
      super.onDestroy();
      Utils.logi(TAG, "____________onDestroy");
   }

   public boolean isUpgrading() {
      return (mUpgradeManager != null) ? mUpgradeManager.isUpgrading() : false;
   }
   
   public void upgradeApk(String apkUrl) {
      if (mUpgradeManager == null) {
         mUpgradeManager = new UpgradeManager(this);
         mUpgradeManager.setHanlder(mHandler);
      }
      mUpgradeManager.upgrade(apkUrl);
   }
   
   public class NewsBinder extends Binder {

      public NewsService getService() {
         return NewsService.this;
      }

   }

}
