package com.osastudio.newshub.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
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
import com.osastudio.newshub.data.NewsResult;
import com.osastudio.newshub.library.DeviceUuidFactory;
import com.osastudio.newshub.library.PreferenceManager;
import com.osastudio.newshub.utils.FileHelper;
import com.osastudio.newshub.utils.InputStreamHelper;
import com.osastudio.newshub.utils.Utils;

public class NewsBaseApi {

   private static final String TAG = "NewsBaseApi";

   protected static final String DEFAULT_WEB_SERVER = "http://www.azker.com:9010/azker";
   protected static final String DEFAULT_DEBUG_WEB_SERVER = "http://218.23.42.49:9010/azker";
   protected static String WEB_SERVER = DEFAULT_WEB_SERVER;
   protected static String DEBUG_WEB_SERVER = DEFAULT_DEBUG_WEB_SERVER;

   protected static final HttpMethod DEFAULT_HTTP_METHOD = HttpMethod.HTTP_POST;

   protected static final String KEY_DEVICE_ID = "deviceID";
   protected static final String KEY_DEVICE_TYPE = "deviceTYPE";

   protected static final String KEY_USER_ID = "studentID";

   protected static boolean DEBUG = false;

   public static String getDeviceId(Context context) {
      return new DeviceUuidFactory(context).getDeviceId();
//      return "99e9ff07-bbb0-3625-a087-e9d3ea2086aa";
   }

   protected static String getDeviceType() {
      return "android";
   }

   public static void enableDebug(boolean debug) {
      DEBUG = debug;
   }

   protected static void setWebServer(String addr) {
      if (DEBUG) {
         DEBUG_WEB_SERVER = addr;
      } else {
         WEB_SERVER = addr;
      }
   }

   public static String getDefaultWebServer(Context context) {
      return generateWebServer(context, DEBUG ? DEFAULT_DEBUG_WEB_SERVER
            : DEFAULT_WEB_SERVER);
   }

   public static String getWebServer(Context context) {
      return generateWebServer(context, DEBUG ? DEBUG_WEB_SERVER : WEB_SERVER);
   }

   public static String getWebServerByType(Context context, ServerType type) {
      String server = null;
      if (ServerType.isMainServer(type)) {
         server = getPrefsManager(context).getMainServer();
      } else if (ServerType.isBackupServer(type)) {
         server = getPrefsManager(context).getBackupServer();
      } else if (ServerType.isAutomatic(type)) {
         server = getWebServer(context);
      }

      if (TextUtils.isEmpty(server)) {
         server = getDefaultWebServer(context);
      }
      return server;
   }

   protected static String generateWebServer(Context context, String server) {
      return new StringBuilder(server).append("/admin/").toString();
   }

   protected static JSONObject getJsonObject(Context context, String service,
         List<NameValuePair> params) {
      return getJsonObject(context, service, params, new ExtraParameter());
   }

   protected static JSONObject getJsonObject(Context context, String service,
         List<NameValuePair> params, ExtraParameter extras) {
      return getJsonObject(context, service, params, DEFAULT_HTTP_METHOD,
            extras);
   }

   protected static JSONObject getJsonObject(Context context, String service,
         List<NameValuePair> params, HttpMethod method) {
      return getJsonObject(context, service, params, method,
            new ExtraParameter());
   }

   protected static JSONObject getJsonObject(Context context, String service,
         List<NameValuePair> params, HttpMethod method, ExtraParameter extras) {
      String jsonString = getString(context, service, params, method, extras);
      return NewsResult.toJsonObject(jsonString);
   }

   protected static String getString(Context context, String service,
         List<NameValuePair> params) {
      return getString(context, service, params, new ExtraParameter());
   }

   protected static String getString(Context context, String service,
         List<NameValuePair> params, ExtraParameter extras) {
      return getString(context, service, params, DEFAULT_HTTP_METHOD, extras);
   }

   protected static String getString(Context context, String service,
         List<NameValuePair> params, HttpMethod method) {
      return getString(context, service, params, method, new ExtraParameter());
   }

   protected static String getString(Context context, String service,
         List<NameValuePair> params, HttpMethod method, ExtraParameter extras) {
      if (method == HttpMethod.HTTP_GET) {
         return getStringByHttpGet(context, service, params, extras);
      } else {
         return getStringByHttpPost(context, service, params, extras);
      }
   }

   protected static String getStringByHttpGet(Context context, String service,
         List<NameValuePair> params) {
      return getStringByHttpGet(context, service, params, new ExtraParameter());
   }

   protected static String getStringByHttpGet(Context context, String service,
         List<NameValuePair> params, ExtraParameter extras) {
      boolean logging = true;
      if (extras != null) {
         logging = extras.enableLogging;
      }

      try {
         if (logging) {
            Utils.log(TAG, "getString() [SERVICE] " + service);
         }
         return getString(context, new HttpGet(service), extras);
      } catch (IllegalArgumentException e) {
         e.printStackTrace();
      }

      return null;
   }

   protected static String getStringByHttpPost(Context context, String service,
         List<NameValuePair> params) {
      return getStringByHttpPost(context, service, params, new ExtraParameter());
   }

   protected static String getStringByHttpPost(Context context, String service,
         List<NameValuePair> params, ExtraParameter extras) {
      boolean logging = true;
      if (extras != null) {
         logging = extras.enableLogging;
      }

      try {
         if (logging) {
            Utils.log(TAG, "getString() [SERVICE] " + service);
         }
         HttpPost httpRequest = new HttpPost(service);
         if (params != null && params.size() > 0) {
            httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            if (logging) {
               Utils.log(
                     TAG,
                     "getString() [PARAMS] "
                           + EntityUtils.toString(httpRequest.getEntity(),
                                 HTTP.UTF_8));
            }
         }
         return getString(context, httpRequest, extras);
      } catch (IllegalArgumentException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (ParseException e) {
         e.printStackTrace();
      }

      return null;
   }

   protected static String getString(Context context,
         HttpRequestBase httpRequest) {
      return getString(context, httpRequest, new ExtraParameter());
   }

   protected static String getString(Context context,
         HttpRequestBase httpRequest, ExtraParameter extras) {
      boolean logging = ExtraParameter.DEFAULT_LOGGING_STATUS;
      boolean checkConnectivityOnly = ExtraParameter.DEFAULT_CHECK_CONNECTIVITY_STATUS;
      boolean useBackupServerIfFailed = ExtraParameter.DEFAULT_USE_BACKUP_SERVER_STATUS;
      int retries = ExtraParameter.DEFAULT_RETRY_TIMES_ON_ERROR;
      if (extras != null) {
         logging = extras.enableLogging;
         checkConnectivityOnly = extras.checkConnectivityOnly;
         useBackupServerIfFailed = extras.useBackupServerIfFailed;
         retries = extras.retryTimesOnError;
      }

      int errorCode = 0;
      String errorDesc = null;
      final int INTERVAL = 10 * 1000;
      int timeout = 10 * 1000;
      while (retries > 0) {
         if (logging) {
            Utils.log(TAG, "getString() [RETRIES] " + retries);
         }
         try {
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
            HttpConnectionParams.setSoTimeout(httpParams, timeout);
            HttpResponse httpResponse = new DefaultHttpClient(httpParams)
                  .execute(httpRequest);
            int status = httpResponse.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
               if (checkConnectivityOnly) {
                  try {
                     JSONObject jsonObject = new JSONObject();
                     jsonObject.put(NewsResult.JSON_KEY_RESULT_CODE,
                           NewsResult.RESULT_OK);
                     jsonObject.put(NewsResult.JSON_KEY_RESULT_DESCRIPTION,
                           httpResponse.getStatusLine().getReasonPhrase());
                     return jsonObject.toString();
                  } catch (JSONException e) {
                     e.printStackTrace();
                  }
               }

               String response = EntityUtils.toString(httpResponse.getEntity(),
                     HTTP.UTF_8);
               if (logging) {
                  Utils.log(TAG, "getString() [RESPONSE] " + response);
               }
               return response;
            } else {
               if (logging) {
                  Utils.log(TAG, "getString() [ERROR] " + status);
               }
               errorCode = NewsResult.httpCode2ResultCode(status);
               errorDesc = httpResponse.getStatusLine().getReasonPhrase();
            }
         } catch (ConnectTimeoutException e) {
            e.printStackTrace();
            errorCode = NewsResult.RESULT_CONNECT_TIMEOUT_EXCEPTION;
            errorDesc = "Connect Timeout Exception";
         } catch (ClientProtocolException e) {
            e.printStackTrace();
            errorCode = NewsResult.RESULT_CLIENT_PROTOCOL_EXCEPTION;
            errorDesc = "Client Protocol Exception";
         } catch (IOException e) {
            e.printStackTrace();
            errorCode = NewsResult.RESULT_IO_EXCEPTION;
            errorDesc = "IO Exception";
         } catch (ParseException e) {
            e.printStackTrace();
            errorCode = NewsResult.RESULT_PARSE_EXCEPTION;
            errorDesc = "Parse Exception";
         }

         retries--;
         timeout += INTERVAL;

         if (retries == 1) {
            if (useBackupServerIfFailed) {
               PreferenceManager prefsManager = getPrefsManager(context);
               String mainServer = prefsManager.getMainServer();
               String backServer = prefsManager.getBackupServer();
               String service = httpRequest.getURI().toString();
               String newService = service.replaceFirst(mainServer, backServer);
               if (logging) {
                  Utils.log(TAG, "getString() [NEW SERVICE] " + newService);
               }
               httpRequest.setURI(URI.create(newService));
            }
         } else if (retries <= 0) {
            try {
               JSONObject jsonObject = new JSONObject();
               jsonObject.put(NewsResult.JSON_KEY_RESULT_CODE, errorCode);
               jsonObject
                     .put(NewsResult.JSON_KEY_RESULT_DESCRIPTION, errorDesc);
               if (logging) {
                  Utils.log(TAG,
                        "getString() [RESULT] " + jsonObject.toString());
               }
               return jsonObject.toString();
            } catch (JSONException e) {
               e.printStackTrace();
            }
         }
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
            Utils.log(TAG, "getFile() URL=" + url + " length="
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
            Utils.log(TAG, "getFileLength() URL=" + url + " length=" + length);
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
            Utils.log(TAG, "getFile() PATH=" + url);
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
            Utils.log(TAG, "getFile() Task cancelled: " + urlString);
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
            Utils.log(TAG, "getFile() IOException occurs (" + downloadSize
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

      Utils.log(TAG, "getFile() " + filePath + " (" + downloadSize + ":"
            + fileSize + ")");
      File file = new File(filePath);
      if (file.exists()) {
         Utils.log(TAG, "length=" + file.length());
      }
      return filePath;
   }

   protected static PreferenceManager getPrefsManager(Context context) {
      return ((NewsApp) context.getApplicationContext()).getPrefsManager();
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
