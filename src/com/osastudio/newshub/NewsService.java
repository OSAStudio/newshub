package com.osastudio.newshub;

import com.huadi.azker_phone.R;
import com.osastudio.newshub.data.AppDeadline;
import com.osastudio.newshub.data.AppProperties;
import com.osastudio.newshub.data.NewsMessage;
import com.osastudio.newshub.data.NewsMessageList;
import com.osastudio.newshub.data.NewsMessageSchedule;
import com.osastudio.newshub.data.NewsMessageScheduleList;
import com.osastudio.newshub.library.PreferenceManager;
import com.osastudio.newshub.library.UpgradeManager;
import com.osastudio.newshub.net.AppDeadlineApi;
import com.osastudio.newshub.net.AppPropertiesApi;
import com.osastudio.newshub.net.NewsMessageApi;
import com.osastudio.newshub.net.ServerType;
import com.osastudio.newshub.utils.NetworkHelper;
import com.osastudio.newshub.utils.Utils;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.widget.Toast;

public class NewsService extends Service {

   private static final String TAG = "NewsService";

   private static final String ACTION_CHECK_NEWS_MESSAGE_SCHEDULE = "com.osastudio.newshub.action.CHECK_NEWS_MESSAGE_SCHEDULE";
   private static final String ACTION_PULL_NEWS_MESSAGE = "com.osastudio.newshub.action.PULL_NEWS_MESSAGE";
   private static final long RETRY_DELAYED_MILLIS = DateUtils.MINUTE_IN_MILLIS * 10;
   private static final int NEWS_MESSAGE_NOTIFICATION = 1300;
   private static final String SEPARATOR = ",";

   private Handler mHandler = new Handler();
   private UpgradeManager mUpgradeManager;
   private boolean mCheckingNewVersion = false;
   private AppDeadline mAppDeadline;
   private INewsServiceBinder mBinder = new INewsServiceBinder();
   private NewsReceiver mNewsReceiver = new NewsReceiver();

   @Override
   public int onStartCommand(Intent intent, int flags, int startId) {
      super.onStartCommand(intent, flags, startId);
      Utils.logi(TAG, "____________onStartCommand");

      return START_STICKY;
   }

   @Override
   public void onCreate() {
      super.onCreate();
      Utils.logi(TAG, "____________onCreate");

      registerNewsReceiver();
      scheduleCheckingNewsMessageSchedule();
      mBinder.checkNewsMessage();
      heartbeat();
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

   private void heartbeat() {
      new Thread(new Runnable() {
         public void run() {
            while (true) {
               Utils.logi(TAG,
                     "______________I'm alive..." + System.currentTimeMillis());
               try {
                  Thread.sleep(DateUtils.MINUTE_IN_MILLIS);
               } catch (InterruptedException e) {
                  // e.printStackTrace();
               }
            }
         }
      }).start();
   }

   private class INewsServiceBinder extends INewsService.Stub {

      @Override
      public void downloadApk(String apkUrl) {
         if (mUpgradeManager == null) {
            mUpgradeManager = new UpgradeManager(NewsService.this);
            mUpgradeManager.setHanlder(mHandler);
         }
         mUpgradeManager.download(apkUrl);
      }

      @Override
      public boolean isDownloadingApk() {
         return (mUpgradeManager != null) ? mUpgradeManager.isDownloading()
               : false;
      }

      @Override
      public void checkNewVersion() {
         if (!mCheckingNewVersion) {
            mCheckingNewVersion = true;
            new AppPropertiesTask(mHandler, NewsService.this).start();
         }
      }

      public boolean isCheckingNewVersion() {
         return mCheckingNewVersion;
      }

      @Override
      public boolean hasNewVersion(AppProperties properties, boolean notifyIfNot) {
         PackageInfo pkgInfo = Utils.getPackageInfo(NewsService.this);
         if (properties.isUpgradeAvailable(pkgInfo)
               || properties.isUpgradeNecessary(pkgInfo)) {
            Intent intent = new Intent();
            intent.setClass(NewsService.this, UpgradeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                  | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                  | Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.putExtra(AppProperties.EXTRA_APP_PROPERTIES, properties);
            startActivity(intent);
            return true;
         } else {
            if (notifyIfNot) {
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
         new AppDeadlineTask(mHandler, NewsService.this).start();
      }

      @Override
      public void checkNewsMessage() {
         mHandler.removeCallbacks(mCheckNewsMessageRunnable);
         mHandler.postDelayed(mCheckNewsMessageRunnable, 10 * 1000);
      }

   };

   private CheckNewsMessageRunnable mCheckNewsMessageRunnable = new CheckNewsMessageRunnable();

   private class CheckNewsMessageRunnable implements Runnable {

      @Override
      public void run() {
         Utils.logi(TAG, "checkNewsMessage");
         PreferenceManager prefsManager = ((NewsApp) getApplication())
               .getPrefsManager();
         String str = prefsManager.getMessageScheduleUserIds();
         String currUserId = prefsManager.getUserId();
         String[] userIds = str.split(SEPARATOR);
         boolean syncSchedule = false;
         Utils.logi(TAG, "checkNewsMessage: " + "userIds=" + str
               + " currUserId=" + currUserId);
         if (!TextUtils.isEmpty(str) && userIds != null && userIds.length > 0) {
            for (int i = 0; i < userIds.length; i++) {
               String userId = userIds[i];
               if (!TextUtils.isEmpty(userId)) {
                  str = prefsManager.getMessageScheduleByUserId(userId);
                  NewsMessageSchedule schedule = NewsMessageSchedule
                        .parseString(str);
                  if (schedule != null && schedule.isToday()) {
                     analyzeNewsMessageSchedule(userId, schedule);
                  } else {
                     syncSchedule = true;
                     if (TextUtils.isEmpty(currUserId)) {
                        currUserId = userId;
                     }
                  }
               }
            }
         } else {
            syncSchedule = true;
         }

         if (syncSchedule) {
            // if (!TextUtils.isEmpty(currUserId)) {
            requestNewsMessageSchedule(currUserId);
            // }
         }
      }

   }

   private void analyzeNewsMessageSchedule(String userId,
         NewsMessageSchedule schedule) {
      Utils.logi(TAG, "analyzeNewsMessageSchedule");
      if (schedule.allowPulling()) {
         int count = schedule.getCount();
         Utils.logi(TAG, "analyzeNewsMessageSchedule: userId=" + userId
               + " count=" + count);
         if (count == 0) {
            if (schedule.pullNow()) {
               requestNewsMessageList(userId, count + 1, true,
                     RETRY_DELAYED_MILLIS);
            } else {
               schedulePullingNewsMessage(userId, schedule.getScheduleMillis(),
                     count + 1);
            }
         } else if (count == 1) {
            requestNewsMessageList(userId, count + 1, true, 0);
         } else if (count == 2) {
            requestNewsMessageList(userId, count + 1, false, 0,
                  ServerType.BACKUP);
         }
      }
   }

   private void requestNewsMessageSchedule(String userId) {
      if (mRequestNewsMessageScheduleRunnable == null) {
         mRequestNewsMessageScheduleRunnable = new RequestNewsMessageScheduleRunnable(
               userId);
      }
      mHandler.removeCallbacks(mRequestNewsMessageScheduleRunnable);
      mHandler.postDelayed(mRequestNewsMessageScheduleRunnable, 10 * 1000);
   }

   private RequestNewsMessageScheduleRunnable mRequestNewsMessageScheduleRunnable;

   private class RequestNewsMessageScheduleRunnable implements Runnable {

      private String userId;

      public RequestNewsMessageScheduleRunnable(String userId) {
         this.userId = userId;
      }

      public String getUserId() {
         return this.userId;
      }

      @Override
      public void run() {
         Utils.logi(TAG, "requestNewsMessageSchedule: userId=" + this.userId);
         new NewsMessageScheduleTask(mHandler, NewsService.this, this.userId)
               .start();
      }

   }

   private void checkNewsMessageSchedule() {
      Utils.logi(TAG, "checkNewsMessageSchedule: " + System.currentTimeMillis());
      PreferenceManager prefsManager = ((NewsApp) getApplication())
            .getPrefsManager();
      String userId = prefsManager.getUserId();
      Utils.logi(TAG, "checkNewsMessageSchedule: userId=" + userId);
      if (!TextUtils.isEmpty(userId)) {
         String str = prefsManager.getMessageScheduleByUserId(userId);
         Utils.logi(TAG, "checkNewsMessageSchedule: schedule=" + str);
         NewsMessageSchedule schedule = NewsMessageSchedule.parseString(str);
         if (schedule == null || !schedule.isToday()) {
            requestNewsMessageSchedule(userId);
         }
      }
   }

   private void scheduleCheckingNewsMessageSchedule() {
      Utils.logi(TAG, "scheduleCheckingNewsMessageSchedule");
      AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
      Intent intent = new Intent();
      intent.setAction(ACTION_CHECK_NEWS_MESSAGE_SCHEDULE);
      PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent,
            PendingIntent.FLAG_CANCEL_CURRENT);
      alarmManager.cancel(pi);
      alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS * 10, pi);
   }

   private void requestNewsMessageList(String userId, int count,
         boolean retryIfFailed, long retryDelayedMillis, ServerType serverType) {
      if (NetworkHelper.isNetworkAvailable(NewsService.this)) {
         Utils.logi(TAG, "requestNewsMessageList: userId=" + userId + " count="
               + count);
         NewsMessageListTask task = new NewsMessageListTask(mHandler, this,
               userId);
         task.setCount(count);
         task.setRetryIfFailed(retryIfFailed);
         task.setRetryDelayedMillis(retryDelayedMillis);
         task.setServerType(serverType);
         task.start();
      }
   }

   private void requestNewsMessageList(String userId, int count,
         boolean retryIfFailed, long retryDelayedMillis) {
      requestNewsMessageList(userId, count, retryIfFailed, retryDelayedMillis,
            ServerType.AUTOMATIC);
   }

   private void requestNewsMessageList(String userId, int count) {
      requestNewsMessageList(userId, count, false, 0);
   }

   private void schedulePullingNewsMessage(String userId, long scheduleMillis,
         int count) {
      Utils.logi(TAG, "schedulePullingNewsMessage: userId=" + userId
            + " count=" + count + " scheduleMillis=" + scheduleMillis);
      AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
      Intent intent = new Intent();
      intent.setAction(ACTION_PULL_NEWS_MESSAGE);
      intent.putExtra("userId", userId);
      intent.putExtra("count", count);
      PendingIntent pi = PendingIntent.getBroadcast(this, userId.hashCode(),
            intent, PendingIntent.FLAG_UPDATE_CURRENT);
      alarmManager.cancel(pi);
      alarmManager.set(AlarmManager.RTC_WAKEUP, scheduleMillis, pi);
   }

   private void notifyNewsMessage(NewsMessageList messages) {
      for (int i = 0; i < messages.getList().size(); i++) {
         NewsMessage msg = messages.getList().get(i);
         PreferenceManager prefsManager = ((NewsApp) getApplication())
               .getPrefsManager();
         NewsMessageSchedule schedule = NewsMessageSchedule
               .parseString(prefsManager.getMessageScheduleByUserId(msg
                     .getUserId()));
         if (!schedule.hasNotifiedToday()) {
            Notification noti = new Notification(R.drawable.noti,
                  getString(R.string.news_message_prompt_title),
                  System.currentTimeMillis());
            PendingIntent pi = PendingIntent.getActivity(this, msg.hashCode(),
                  getNewsMessageLaunchIntent(msg),
                  PendingIntent.FLAG_UPDATE_CURRENT);
            noti.setLatestEventInfo(this, msg.getContent(),
                  getMsgTitleByType(msg), pi);
            noti.flags |= Notification.FLAG_AUTO_CANCEL;
            if (i == 0) {
               noti.defaults |= Notification.DEFAULT_SOUND;
               noti.defaults |= Notification.DEFAULT_LIGHTS;
               noti.defaults |= Notification.DEFAULT_VIBRATE;
            } else {
               noti.defaults = 0;
            }
            NotificationManager manager = (NotificationManager) getApplicationContext()
                  .getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(msg.hashCode(), noti);
         }
      }
   }

   private Intent getNewsMessageLaunchIntent(NewsMessage msg) {
      Intent intent = new Intent();
      intent.setClass(this, CategoryActivity.class);
      Bundle extras = new Bundle();
      extras.putInt(CategoryActivity.MESSAGE_SEND_TYPE, msg.getType());
      extras.putString(CategoryActivity.MESSAGE_SERVICE_ID, msg.getId());
      extras.putString(CategoryActivity.MESSAGE_USER_ID, msg.getUserId());
      extras.putString(CategoryActivity.MESSAGE_USER_NAME, msg.getUserName());
      intent.putExtras(extras);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      return intent;
   }

   private String getMsgTitleByType(NewsMessage msg) {
      switch (msg.getType()) {
      case NewsMessage.MSG_TYPE_NOTICE:
         return getString(R.string.msg_prompt_title_notice);

      case NewsMessage.MSG_TYPE_NOTICE_FEEDBACK:
         return getString(R.string.msg_prompt_title_notice_feedback);

      case NewsMessage.MSG_TYPE_DAILY_REMINDER:
         return getString(R.string.msg_prompt_title_daily_reminder);

      case NewsMessage.MSG_TYPE_COLUMNIST:
         return getString(R.string.msg_prompt_title_colomnist);

      case NewsMessage.MSG_TYPE_RECOMMEND:
         return getString(R.string.msg_prompt_title_recommend);

      default:
         return "";
      }
   }

   private class NewsReceiver extends BroadcastReceiver {
      @Override
      public void onReceive(Context context, Intent intent) {
         String action = intent.getAction();
         Utils.logi(
               TAG,
               "_______________onReceive: " + action + " "
                     + System.currentTimeMillis());
         if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)
               || ACTION_CHECK_NEWS_MESSAGE_SCHEDULE.equals(action)) {
            if (NetworkHelper.isNetworkAvailable(context)) {
               checkNewsMessageSchedule();
            }
         } else if (ACTION_PULL_NEWS_MESSAGE.equals(action)) {
            String userId = intent.getStringExtra("userId");
            int count = intent.getIntExtra("count", 1);
            Utils.logi(TAG, "_______________onReceive: userId=" + userId
                  + " count=" + count);
            if (count == 1) {
               requestNewsMessageList(userId, count, true, RETRY_DELAYED_MILLIS);
            } else if (count == 2) {
               requestNewsMessageList(userId, count, true, 0);
            } else {
               requestNewsMessageList(userId, count, false, 0,
                     ServerType.BACKUP);
            }
         }
      }
   };

   private void registerNewsReceiver() {
      IntentFilter filter = new IntentFilter();
      filter.addAction(ACTION_CHECK_NEWS_MESSAGE_SCHEDULE);
      filter.addAction(ACTION_PULL_NEWS_MESSAGE);
      filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
      registerReceiver(mNewsReceiver, filter);
   }

   private void unregisterNewsReceiver() {
      unregisterReceiver(mNewsReceiver);
   }

   private class AppPropertiesTask extends NewsTask<AppProperties> {

      public AppPropertiesTask(Handler handler, Context context) {
         super(handler, context);
      }

      @Override
      public AppProperties doInBackground() {
         return AppPropertiesApi.getAppProperties(this.context);
      }

      @Override
      public void onPostExecute(AppProperties result) {
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

   private class AppDeadlineTask extends NewsTask<AppDeadline> {

      public AppDeadlineTask(Handler handler, Context context) {
         super(handler, context);
      }

      @Override
      public AppDeadline doInBackground() {
         return AppDeadlineApi.getAppDeadline(this.context);
      }

      @Override
      public void onPostExecute(AppDeadline result) {
         mAppDeadline = result;
         ((NewsApp) this.context.getApplicationContext())
               .setAppDeadline(result);
      }

   }

   private class NewsMessageScheduleTask extends
         NewsTask<NewsMessageScheduleList> {

      private String userId;

      public NewsMessageScheduleTask(Handler handler, Context context,
            String userId) {
         super(handler, context);
         this.userId = userId;
      }

      @Override
      public NewsMessageScheduleList doInBackground() {
         return NewsMessageApi.getNewsMessageScheduleList(this.context);
      }

      @Override
      public void onPostExecute(NewsMessageScheduleList result) {
         if (result != null && result.getList() != null
               && result.getList().size() > 0) {
            PreferenceManager prefsManager = ((NewsApp) NewsService.this
                  .getApplication()).getPrefsManager();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < result.getList().size(); i++) {
               NewsMessageSchedule schedule = result.getList().get(i);
               schedule.setBaseMillis(System.currentTimeMillis());
               schedule.setCount(0);
               prefsManager.setMessageScheduleByUserId(schedule.getUserId(),
                     schedule.toString());

               analyzeNewsMessageSchedule(schedule.getUserId(), schedule);

               builder.append(schedule.getUserId());
               if (i < result.getList().size() - 1) {
                  builder.append(SEPARATOR);
               }
            }
            prefsManager.setMessageScheduleUserIds(builder.toString());
         }
      }

   }

   private class NewsMessageListTask extends NewsTask<NewsMessageList> {

      private String userId;
      private int count = 0;
      private boolean retryIfFailed = false;
      private long retryDelayedMillis = 0;
      private ServerType serverType = ServerType.AUTOMATIC;

      public NewsMessageListTask(Handler handler, Context context, String userId) {
         super(handler, context);
         this.userId = userId;
      }

      public void setCount(int count) {
         this.count = count;
      }

      public void setRetryIfFailed(boolean retry) {
         this.retryIfFailed = retry;
      }

      public void setRetryDelayedMillis(long millis) {
         this.retryDelayedMillis = millis;
      }

      public void setServerType(ServerType type) {
         this.serverType = type;
      }

      @Override
      public NewsMessageList doInBackground() {
         return NewsMessageApi.getNewsMessageList(this.context, this.userId,
               this.serverType);
      }

      @Override
      public void onPostExecute(NewsMessageList result) {
         PreferenceManager prefsManager = ((NewsApp) NewsService.this
               .getApplication()).getPrefsManager();
         NewsMessageSchedule schedule = NewsMessageSchedule
               .parseString(prefsManager
                     .getMessageScheduleByUserId(this.userId));
         if (result != null && result.isSuccess()) {
            if (schedule != null) {
               schedule.setCount(3);
               prefsManager.setMessageScheduleByUserId(this.userId,
                     schedule.toString());
            }
            notifyNewsMessage(result);
         } else {
            if (schedule != null) {
               schedule.setCount(this.count);
               prefsManager.setMessageScheduleByUserId(this.userId,
                     schedule.toString());
            }
            if (this.retryIfFailed) {
               if (this.retryDelayedMillis > 0) {
                  schedulePullingNewsMessage(this.userId,
                        System.currentTimeMillis() + this.retryDelayedMillis,
                        this.count + 1);
               } else {
                  requestNewsMessageList(this.userId, this.count + 1, false, 0,
                        ServerType.BACKUP);
               }
            }
         }
      }

   }

   public abstract class NewsTask<T> extends Thread {

      protected Context context;
      protected Handler handler;

      public NewsTask(Handler handler) {
         this.handler = handler;
      }

      public NewsTask(Handler handler, Context context) {
         this(handler);
         this.context = context;
      }

      @Override
      public void run() {
         final T result = doInBackground();

         if (this.handler != null) {
            this.handler.post(new Runnable() {
               public void run() {
                  onPostExecute(result);
               }
            });
         }
      }

      public abstract T doInBackground();

      public abstract void onPostExecute(T result);

   }

}
