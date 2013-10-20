package com.osastudio.newshub.library;

import java.io.File;
import java.io.IOException;

import com.huadi.azker_phone.R;
import com.osastudio.newshub.net.FileInterface.FileDownloadListener;
import com.osastudio.newshub.net.NewsBaseApi;
import com.osastudio.newshub.utils.FileHelper;
import com.osastudio.newshub.utils.IOUtils;
import com.osastudio.newshub.utils.Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.RemoteViews;
import android.widget.Toast;

public class UpgradeManager {

   private static final String TAG = "UpgradeManager";

   private static final int NOTIFICATION = 1024;
   private Notification mNotification;
   private int mProgress = 0;

   private Context mContext;
   private Handler mHandler;
   private boolean mDownloading = false;
   private FileDownloadListener mDownloadListener = new FileDownloadListener() {
      @Override
      public void onPreDownload(String url) {
         showDownloadNotification(url);
      }

      @Override
      public void onPostDownload(String url) {
         hideNotification(NOTIFICATION);
      }

      @Override
      public void onDownloadProgressUpdate(String url, int progress) {
         updateDownloadNotification(progress);
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
      if (!mDownloading) {
         cleanup();
         mDownloading = true;
         new UpgradeTask(mHandler, mContext, url).start();
      }
   }
   
   private void cleanup() {
      mDownloading = false;
      mNotification = null;
      mProgress = 0;
   }

   public void install(String filePath) {
      mDownloading = false;
      mContext.startActivity(getInstallIntent(filePath));
   }

   public Intent getInstallIntent(String filePath) {
      Intent intent = new Intent(Intent.ACTION_VIEW);
      intent.setDataAndType(Uri.fromFile(new File(filePath)),
            "application/vnd.android.package-archive");
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      return intent;
   }

   private void showDownloadNotification(String url) {
      NotificationManager manager = (NotificationManager) mContext
            .getSystemService(Context.NOTIFICATION_SERVICE);
      String fileName = IOUtils.getFileNameFromUrl(url);
      if (mNotification == null) {
         PendingIntent pi = PendingIntent.getActivity(mContext, 0,
               new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
         RemoteViews views = new RemoteViews(mContext.getPackageName(),
               R.layout.download_notification);
         Notification noti = new Notification();
         noti.icon = R.drawable.noti;
         noti.contentView = views;
         noti.when = System.currentTimeMillis();
         noti.contentIntent = pi;
         noti.tickerText = mContext
               .getString(R.string.apk_download_ticker_text);
         noti.flags |= Notification.FLAG_AUTO_CANCEL;
         mNotification = noti;
      }
      mNotification.contentView.setTextViewText(R.id.download_title,
            mContext.getString(R.string.apk_downloading, fileName));
      mNotification.contentView.setProgressBar(R.id.download_progress, 100, 0,
            false);
      manager.notify(NOTIFICATION, mNotification);
   }

   private void showInstallNotification(String filePath) {
      NotificationManager manager = (NotificationManager) mContext
            .getSystemService(Context.NOTIFICATION_SERVICE);
      PendingIntent pi = PendingIntent.getActivity(mContext, 0,
            getInstallIntent(filePath), PendingIntent.FLAG_UPDATE_CURRENT);
      RemoteViews views = new RemoteViews(mContext.getPackageName(),
            R.layout.msg_notification);
      Notification noti = new Notification();
      noti.icon = R.drawable.noti;
      noti.contentView = views;
      noti.when = System.currentTimeMillis();
      noti.contentIntent = pi;
      noti.tickerText = mContext.getString(R.string.apk_downloaded);
      noti.flags |= Notification.FLAG_AUTO_CANCEL;
      noti.contentView.setTextViewText(R.id.noti_title,
            mContext.getString(R.string.apk_install_prompt));
      mNotification = noti;
      manager.notify(NOTIFICATION, mNotification);
   }

   private void hideNotification(int notificationId) {
      NotificationManager manager = (NotificationManager) mContext
            .getApplicationContext().getSystemService(
                  Context.NOTIFICATION_SERVICE);
      manager.cancel(notificationId);
   }

   private void updateDownloadNotification(int progress) {
      if (progress <= mProgress) {
         return;
      }
      mProgress = progress;
      mNotification.contentView.setProgressBar(R.id.download_progress, 100,
            mProgress, false);
      NotificationManager manager = (NotificationManager) mContext
            .getApplicationContext().getSystemService(
                  Context.NOTIFICATION_SERVICE);
      manager.notify(NOTIFICATION, mNotification);
   }

   private void notifyDownloadCompletion() {
      hideNotification(NOTIFICATION);
      cleanup();
      Toast.makeText(mContext, R.string.apk_downloaded, Toast.LENGTH_LONG)
            .show();
   }

   private void notifyDownloadCancelled() {
      hideNotification(NOTIFICATION);
      cleanup();
      Toast.makeText(mContext, R.string.apk_download_cancelled,
            Toast.LENGTH_LONG).show();
   }

   private void notifyDownloadFailure() {
      hideNotification(NOTIFICATION);
      cleanup();
      Toast.makeText(mContext, R.string.apk_download_failed, Toast.LENGTH_LONG)
            .show();
   }

   public static File getApkFile(String url) {
      File folder = null;
      try {
         folder = IOUtils.getDownloadDir();
      } catch (IOException e) {
         folder = Environment.getExternalStorageDirectory();
      }
      String fileName = IOUtils.getFileNameFromUrl(url);
      return TextUtils.isEmpty(fileName) ? null : new File(folder, fileName);
   }

   public static String getApkPath(String url) {
      File file = getApkFile(url);
      return (file != null) ? file.getAbsolutePath() : null;
   }

   private class UpgradeTask extends NewsTask<String> {

      private String url;

      public UpgradeTask(Handler handler, Context context, String url) {
         super(handler, context);
         this.url = url;
      }

      @Override
      public String doInBackground() {
         String filePath = getApkPath(this.url);
         if (new File(filePath).exists()) {
            FileHelper.deleteFile(filePath);
         }
         return NewsBaseApi.getFile(this.context, this.url, filePath,
               this.handler, mDownloadListener, 1);
      }

      @Override
      public void onPostExecute(String result) {
         if (!TextUtils.isEmpty(result)) {
            cleanup();
            showInstallNotification(result);
            install(result);
         } else {
            notifyDownloadFailure();
         }
      }

   }

}
