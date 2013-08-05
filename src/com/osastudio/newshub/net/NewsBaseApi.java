package com.osastudio.newshub.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;

import com.osastudio.newshub.NewsApp;
import com.osastudio.newshub.cache.CacheManager;
import com.osastudio.newshub.cache.NewsAbstractCache;
import com.osastudio.newshub.cache.NewsBaseAbstractCache;
import com.osastudio.newshub.cache.SubscriptionAbstractCache;
import com.osastudio.newshub.data.base.NewsBaseAbstract;
import com.osastudio.newshub.library.DeviceUuidFactory;
import com.osastudio.newshub.library.Installation;
import com.osastudio.newshub.utils.FileHelper;
import com.osastudio.newshub.utils.InputStreamHelper;
import com.osastudio.newshub.utils.Utils;

public class NewsBaseApi {

   private static final String TAG = "NewsBaseApi";

   protected static boolean DEBUG = true;

   protected static final String DEFAULT_WEB_SERVER = "";
   protected static final String DEFAULT_DEBUG_WEB_SERVER = "218.23.42.49";
   protected static String WEB_SERVER = DEFAULT_WEB_SERVER;
   protected static String DEBUG_WEB_SERVER = DEFAULT_DEBUG_WEB_SERVER;

   protected static final HttpMethod DEFAULT_HTTP_METHOD = HttpMethod.HTTP_POST;

   protected static final String KEY_DEVICE_ID = "deviceID";
   protected static final String KEY_DEVICE_TYPE = "deviceTYPE";

   protected static final String KEY_USER_ID = "studentID";

   protected static String getDeviceId(Context context) {
      String id = new DeviceUuidFactory(context).getDeviceId();
      // UUID uuid = new DeviceUuidFactory(context).getDeviceUuid();
      // if (uuid != null) {
      // id = uuid.toString();
      // }
      // if (TextUtils.isEmpty(id)) {
      // id = Installation.id(context);
      // }

      if (NewsApp.IS_DEBUG) {
         id = "667ebbbf9fcd9229344681aebf4ec67316645186";
      }
      return id;
   }

   public static void enableDebug(boolean debug) {
      DEBUG = debug;
   }

   public static void setWebServer(String addr) {
      if (DEBUG) {
         DEBUG_WEB_SERVER = addr;
      } else {
         WEB_SERVER = addr;
      }
   }

   protected static String getDeviceType() {
      return "android";
   }

   protected static String getWebServer() {
      return "http://" + (DEBUG ? DEBUG_WEB_SERVER : WEB_SERVER)
            + ":9010/azker/admin/";
   }

   protected static String getAppPropertiesService() {
      return new StringBuilder(getWebServer()).append(
            "loginpicture!checkLoginInfoByMobile.do").toString();
   }

   protected static String getNewsChannelListService() {
      return new StringBuilder(getWebServer()).append(
            "titleshow!getTitleListByMobile.do").toString();
   }

   protected static String getNewsAbstractListService() {
      return new StringBuilder(getWebServer()).append(
            "lesson!getLessonListByMobile.do").toString();
   }

   protected static String getNewsArticleService() {
      return new StringBuilder(getWebServer()).append(
            "lesson!getLessonContentByMobile.do").toString();
   }

   protected static String likeArticleService() {
      return new StringBuilder(getWebServer()).append(
            "lesson!submitLessonUPByMobile.do").toString();
   }

   protected static String getUserListService() {
      return new StringBuilder(getWebServer()).append(
            "custom!getUserInfosByMobile.do").toString();
   }

   protected static String getUserInfoListService() {
      return new StringBuilder(getWebServer()).append(
            "custom!getUserInfoAndServiceInfoByMobile.do").toString();
   }

   protected static String validateService() {
      return new StringBuilder(getWebServer()).append(
            "serial!checkSerialByMobile.do").toString();
   }

   protected static String getValidateStatusService() {
      return new StringBuilder(getWebServer()).append(
            "custom!checkActiveUserByMobile.do").toString();
   }

   protected static String registerService() {
      return new StringBuilder(getWebServer()).append(
            "custom!submitUserInfoByMobile.do").toString();
   }

   protected static String addUserService() {
      return new StringBuilder(getWebServer()).append(
            "custom!addAnotherUserByMobile.do").toString();
   }

   protected static String getCityListService() {
      return new StringBuilder(getWebServer()).append(
            "custom!getCitysByMobile.do").toString();
   }

   protected static String getCityDistrictListService() {
      return new StringBuilder(getWebServer()).append(
            "custom!getAreasByMobile.do").toString();
   }

   protected static String getSchoolTypeListService() {
      return new StringBuilder(getWebServer()).append(
            "custom!getSchoolClassByMobile.do").toString();
   }

   protected static String getSchoolListService() {
      return new StringBuilder(getWebServer()).append(
            "custom!getSchoolsByMobile.do").toString();
   }

   protected static String getSchoolYearListService() {
      return new StringBuilder(getWebServer()).append(
            "custom!getYearsByMobile.do").toString();
   }

   protected static String getSchoolClassListService() {
      return new StringBuilder(getWebServer()).append(
            "custom!getClassesByMobile.do").toString();
   }

   protected static String getQualificationListService() {
      return new StringBuilder(getWebServer()).append(
            "custom!getXueliByMobile.do").toString();
   }

   protected static String getFeedbackTypeListService() {
      return new StringBuilder(getWebServer()).append(
            "problemfeedback!getProblemTypeByMobile.do").toString();
   }

   protected static String feedbackService() {
      return new StringBuilder(getWebServer()).append(
            "problemfeedback!submitProblemFeedbackByMobile.do").toString();
   }

   protected static String getNewsNoticeListService() {
      return new StringBuilder(getWebServer()).append(
            "notify!getNotifyListByMobile.do").toString();
   }

   protected static String getNewsNoticeArticleService() {
      return new StringBuilder(getWebServer()).append(
            "notify!getNotifyContentByMobile.do").toString();
   }

   protected static String feedbackNoticeService() {
      return new StringBuilder(getWebServer()).append(
            "notify!submitNotifyFeedbackByMobile.do").toString();
   }

   protected static String getDailyReminderListService() {
      return new StringBuilder(getWebServer()).append(
            "dailyreminder!getDailyReminderListByMobile.do").toString();
   }

   protected static String getSubscriptionTopicListService() {
      return new StringBuilder(getWebServer()).append(
            "expandlssue!getUserLssuesByMobile.do").toString();
   }

   protected static String getSubscriptionAbstractListService() {
      return new StringBuilder(getWebServer()).append(
            "expandlesson!getExpandLessonsByMobile.do").toString();
   }

   protected static String getSubscriptionArticleService() {
      return new StringBuilder(getWebServer()).append(
            "expandlesson!getExpandLessonContentByMobile.do").toString();
   }

   protected static String getRecommendedTopicListService() {
      return new StringBuilder(getWebServer()).append(
            "expandlssue!getRecommendLssueListByMobile.do").toString();
   }

   protected static String getRecommendedTopicIntroService() {
      return new StringBuilder(getWebServer()).append(
            "expandlssue!getRecommendLssueContentByMobile.do").toString();
   }

   protected static String getNewsColumnistListService() {
      return new StringBuilder(getWebServer()).append(
            "expert!getExpertListByMobile.do").toString();
   }

   protected static String getNewsColumnistInfoService() {
      return new StringBuilder(getWebServer()).append(
            "expert!getExpertContentByMobile.do").toString();
   }

   protected static JSONObject getJsonObject(String service,
         List<NameValuePair> params) {
      String jsonString = getString(service, params);
      if (TextUtils.isEmpty(jsonString)) {
         return null;
      }
      JSONObject jsonObject = null;
      try {
         jsonObject = new JSONObject(jsonString);
      } catch (JSONException e) {
         // e.printStackTrace();
         return null;
      }
      if (jsonObject.length() <= 0) {
         return null;
      }
      return jsonObject;
   }

   protected static String getString(String service, List<NameValuePair> params) {
      return getString(service, params, DEFAULT_HTTP_METHOD);
   }

   protected static String getString(String service,
         List<NameValuePair> params, HttpMethod method) {
      if (method == HttpMethod.HTTP_GET) {
         return getStringByHttpGet(service, params);
      } else {
         return getStringByHttpPost(service, params);
      }
   }

   protected static String getStringByHttpGet(String service,
         List<NameValuePair> params) {
      return null;
   }

   protected static String getStringByHttpPost(String service,
         List<NameValuePair> params) {
      try {
         Utils.logi(TAG, "getString() [SERVICE] " + service);
         HttpPost httpRequest = new HttpPost(service);
         if (params != null && params.size() > 0) {
            httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            Utils.logi(
                  TAG,
                  "getString() [PARAMS] "
                        + EntityUtils.toString(httpRequest.getEntity(),
                              HTTP.UTF_8));
         }

         HttpResponse httpResponse = new DefaultHttpClient()
               .execute(httpRequest);
         if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String responseString = EntityUtils.toString(
                  httpResponse.getEntity(), HTTP.UTF_8);
            Utils.logi(TAG, "getString() [RESPONSE] " + responseString);
            return responseString;
         }
      } catch (ClientProtocolException e) {
         // e.printStackTrace();
      } catch (IllegalArgumentException e) {
         // e.printStackTrace();
      } catch (IOException e) {
         // e.printStackTrace();
      } catch (ParseException e) {
         // e.printStackTrace();
      }

      return null;
   }

   public static InputStream getStream(String url) {
      try {
         HttpClient httpClient = new DefaultHttpClient();
         HttpGet httpGet = new HttpGet(url);

         HttpResponse httpResponse = httpClient.execute(httpGet);
         if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            InputStream is = httpResponse.getEntity().getContent();
            Utils.logi(TAG, "getFile() URL=" + url + " length="
                  + httpResponse.getEntity().getContentLength());
            if (is != null) {
               return is;
            }
         }
      } catch (ClientProtocolException e) {
         // e.printStackTrace();
      } catch (IllegalArgumentException e) {
         // e.printStackTrace();
      } catch (IllegalStateException e) {
         // e.printStackTrace();
      } catch (IOException e) {
         // e.printStackTrace();
      }

      return null;
   }

   public static long getFileLength(String url) {
      long length = -1;
      try {
         HttpClient httpClient = new DefaultHttpClient();
         HttpGet httpGet = new HttpGet(url);

         HttpResponse httpResponse = httpClient.execute(httpGet);
         if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            length = httpResponse.getEntity().getContentLength();
            Utils.logi(TAG, "getFileLength() URL=" + url + " length=" + length);
         }
      } catch (ClientProtocolException e) {
         // e.printStackTrace();
      } catch (IllegalArgumentException e) {
         // e.printStackTrace();
      } catch (IllegalStateException e) {
         // e.printStackTrace();
      } catch (IOException e) {
         // e.printStackTrace();
      }
      return length;
   }

   public static String getFile(String url, String path) {
      String result = null;
      InputStream is = getStream(url);
      if (is != null) {
         InputStreamHelper helper = new InputStreamHelper(is);
         if (helper.writeToFile(path)) {
            Utils.logi(TAG, "getFile() PATH=" + url);
            result = path;
         }
         helper.close();
      }

      return result;
   }

   /**
    * Download file from server
    * 
    * @param context
    *           context of caller
    * @param urlString
    *           url of file to be downloaded
    * @param filePath
    *           local path to which file will be saved
    * @param handler
    *           handler of UI thread
    * @param listener
    *           file download listener
    * @param progressUpdateInterval
    *           downloading percentage interval setup to get notified
    * @param task
    *           task in which file downloading will run
    * @return
    */
   public static String getFile(Context context, final String urlString,
         final String filePath, Handler handler,
         final FileInterface.FileDownloadListener listener,
         final int progressUpdateInterval, AsyncTask task) {
      if (TextUtils.isEmpty(urlString) || TextUtils.isEmpty(filePath)) {
         return null;
      }

      File localFile = new File(filePath);
      if (localFile.exists()) {
         long length = getFileLength(urlString);
         if (length > 0 && length == localFile.length()) {
            return filePath;
         }
         FileHelper.deleteFile(localFile);
      }

      /* OnFileDownloadListener */
      if (handler != null && listener != null) {
         handler.post(new Runnable() {
            public void run() {
               listener.onPreDownload(urlString);
            }
         });
      }

      int fileSize = 0;
      BufferedInputStream bis = null;
      BufferedOutputStream bos = null;
      try {
         URL url = new URL(urlString);
         URLConnection conn = url.openConnection();
         fileSize = conn.getContentLength();

         if ((fileSize <= 0) || !(new File(filePath).createNewFile())) {
            return null;
         }

         conn.setConnectTimeout(60 * 1000);
         conn.setReadTimeout(60 * 1000);
         // conn.setRequestProperty("Range", "bytes=" + start + "-" + end);
         bis = new BufferedInputStream(conn.getInputStream());
         bos = new BufferedOutputStream(new FileOutputStream(filePath));
      } catch (IOException e) {
         // e.printStackTrace();
         return null;
      }

      byte[] buffer = new byte[1024];
      int length = 0, downloadSize = 0;
      int progress = 0, prevProgress = 0;
      boolean hasError = false;

      int interval = (progressUpdateInterval > 0) ? (progressUpdateInterval - 1)
            : 0;
      while (true) {
         if (task != null && task.isCancelled()) {
            Utils.logi(TAG, "getFile() Task cancelled: " + urlString);
            break;
         }

         try {
            if ((length = bis.read(buffer)) == -1) {
               break;
            }

            downloadSize += length;
            prevProgress = progress;
            progress = downloadSize * 100 / fileSize;

            /* OnFileDownloadListener */
            if ((progress > prevProgress + interval) && handler != null
                  && listener != null) {
               final int percentage = progress;
               handler.post(new Runnable() {
                  public void run() {
                     listener.onDownloadProgressUpdate(urlString, percentage);
                  }
               });
            }

            bos.write(buffer, 0, length);
         } catch (IOException e) {
            Utils.loge(TAG, "getFile() IOException occurs (" + downloadSize
                  + ":" + fileSize + ")");
            hasError = true;
            break;
         }
      }

      try {
         bos.flush();
         bos.close();
         bis.close();
      } catch (IOException e) {
         e.printStackTrace();
         FileHelper.deleteFile(filePath);
         return null;
      }

      if (hasError || (downloadSize != fileSize)) {
         FileHelper.deleteFile(filePath);
         return null;
      }

      /* OnFileDownloadListener */
      if (handler != null && listener != null) {
         handler.post(new Runnable() {
            public void run() {
               listener.onPostDownload(urlString);
            }
         });
      }

      Utils.logi(TAG, "getFile() " + filePath + " (" + downloadSize + ":"
            + fileSize + ")");
      File file = new File(filePath);
      if (file.exists()) {
         Utils.logi(TAG, "length=" + file.length());
      }
      return filePath;
   }

   protected static CacheManager getCacheManager(Context context) {
      return ((NewsApp) context.getApplicationContext()).getCacheManager();
   }

   protected static NewsBaseAbstractCache getNewsBaseAbstractCache(
         Context context) {
      return getCacheManager(context).getNewsBaseAbstractCache();
   }

   protected static NewsAbstractCache getNewsAbstractCache(Context context) {
      return getCacheManager(context).getNewsAbstractCache();
   }

   protected static SubscriptionAbstractCache getSubscriptionAbstractCache(
         Context context) {
      return getCacheManager(context).getSubscriptionAbstractCache();
   }

}
