package com.osastudio.newshub.library;

import java.io.File;
import java.io.IOException;

import com.huadi.azker_phone.R;
import com.osastudio.newshub.net.FileInterface.FileDownloadListener;
import com.osastudio.newshub.net.NewsBaseApi;
import com.osastudio.newshub.utils.IOUtils;
import com.osastudio.newshub.utils.Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.RemoteViews;
import android.widget.Toast;

public class UpgradeManager {

   private static final String TAG = "UpgradeManager";

   private static final int NOTIFICATION_ID = 1001;
   private Notification mNotification;
   private Bitmap mIcon;
   private int mProgress = 0;

   private Context mContext;
   private Handler mHandler;
   private boolean mDownloading = false;
   private FileDownloadListener mDownloadListener = new FileDownloadListener() {
      @Override
      public void onPreDownload(String url) {
         showNotification(url);
      }

      @Override
      public void onPostDownload(String url) {
         hideNotification();
      }

      @Override
      public void onDownloadProgressUpdate(String url, int progress) {
         updateNotification(progress);
      }
   };

   public UpgradeManager(Context context) {
      mContext = context;
   }

   public void setHanlder(Handler handler) {
      mHandler = handler;
   }

   public boolean isDownloading() {
      return mDownloading;
   }

   public void download(String url) {
      Utils.logi(TAG, "_______________upgrade");
      mDownloading = true;
      new UpgradeTask().execute(url);
   }

   public void install(String path) {
      mDownloading = false;
      Utils.logi(TAG, "_______________install");
   }

   private void showNotification(String url) {
      if (mNotification == null) {
         mIcon = BitmapFactory.decodeResource(mContext.getResources(),
               R.drawable.download);
         mNotification = new Notification(R.drawable.download, "",
               System.currentTimeMillis());
         PendingIntent pendingintent = PendingIntent.getActivity(mContext, 0,
               new Intent(), PendingIntent.FLAG_CANCEL_CURRENT);
         mNotification.setLatestEventInfo(mContext, "TITLE", "MESSAGE",
               pendingintent);
         mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
      }
      mNotification.contentView = new RemoteViews(mContext.getPackageName(),
            R.layout.download_bar);
      mNotification.icon = R.drawable.download;
      // mNotification.largeIcon = mIcon;
      mNotification.contentView.setProgressBar(R.id.download_progress_bar, 100,
            0, false);
      String fileName = url.substring(url.lastIndexOf('/') + 1);
      mNotification.contentView.setTextViewText(R.id.download_title,
            "Downloading...");
      NotificationManager manager = (NotificationManager) mContext
            .getApplicationContext().getSystemService(
                  Context.NOTIFICATION_SERVICE);
      manager.notify(NOTIFICATION_ID, mNotification);

   }

   private void hideNotification() {
      NotificationManager manager = (NotificationManager) mContext
            .getApplicationContext().getSystemService(
                  Context.NOTIFICATION_SERVICE);
      manager.cancel(NOTIFICATION_ID);
   }

   private void updateNotification(int progress) {
      if (progress <= mProgress) {
         return;
      }
      mProgress = progress;
      mNotification.contentView.setProgressBar(R.id.download_progress_bar, 100,
            mProgress, false);
      NotificationManager manager = (NotificationManager) mContext
            .getApplicationContext().getSystemService(
                  Context.NOTIFICATION_SERVICE);
      manager.notify(NOTIFICATION_ID, mNotification);
   }

   private void notifyCompletion() {
      hideNotification();
      Toast.makeText(mContext, "Download successfully!", Toast.LENGTH_LONG)
            .show();
   }

   private void notifyCancelled() {
      hideNotification();
      Toast.makeText(mContext, "Download cancelled!", Toast.LENGTH_LONG).show();
   }

   private void notifyFailure() {
      hideNotification();
      Toast.makeText(mContext, "Download failed!", Toast.LENGTH_LONG).show();
   }

   public static String getApkPath(String url) {
      File folder = null;
      try {
         folder = IOUtils.getDownloadDir();
      } catch (IOException e) {
         folder = Environment.getExternalStorageDirectory();
      }
      return new File(folder, IOUtils.getFileNameFromUrl(url))
            .getAbsolutePath();
   }

   private class UpgradeTask extends AsyncTask<String, Integer, String> {

      @Override
      protected String doInBackground(String... params) {
         return NewsBaseApi.getFile(mContext, params[0], getApkPath(params[0]),
               mHandler, mDownloadListener, 1, this);
      }

      @Override
      protected void onPostExecute(String result) {
         if (!TextUtils.isEmpty(result)) {
            install(result);
            notifyCompletion();
         } else {
            notifyFailure();
         }
      }

   }

}
