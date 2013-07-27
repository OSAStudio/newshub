package com.osastudio.newshub.net;

import java.io.IOException;
import java.io.InputStream;
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
import android.text.TextUtils;

import com.osastudio.newshub.NewsApp;
import com.osastudio.newshub.cache.CacheManager;
import com.osastudio.newshub.cache.NewsAbstractCache;
import com.osastudio.newshub.library.DeviceUuidFactory;
import com.osastudio.newshub.library.Installation;
import com.osastudio.newshub.utils.InputStreamHelper;
import com.osastudio.newshub.utils.Utils;

public class NewsBaseApi {

   private static final String TAG = "NewsBaseApi";

   protected static boolean DEBUG = true;

   protected static final String WEB_SERVER = "";
   protected static final String DEBUG_WEB_SERVER = "http://218.23.42.49:9010/azker/admin/";

   protected static final HttpMethod DEFAULT_HTTP_METHOD = HttpMethod.HTTP_POST;

   protected static final String KEY_DEVICE_ID = "deviceID";
   protected static final String KEY_DEVICE_TYPE = "deviceTYPE";

   protected static String getDeviceId(Context context) {
      String id = null;
      UUID uuid = new DeviceUuidFactory(context).getDeviceUuid();
      if (uuid != null) {
         id = uuid.toString();
      }
      if (TextUtils.isEmpty(id)) {
         id = Installation.id(context);
      }
      return id;
   }

   protected static String getDeviceType() {
      return "android";
   }

   protected static String getWebServer() {
      return DEBUG ? DEBUG_WEB_SERVER : WEB_SERVER;
   }

   protected static String getAppPropertiesService() {
      return new StringBuilder(getWebServer()).append(
            "loginpicture!getLoginPictureByMobile.do").toString();
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
         HttpPost httpRequest = new HttpPost(service);
         if (params != null && params.size() > 0) {
            httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
         }

         HttpResponse httpResponse = new DefaultHttpClient()
               .execute(httpRequest);
         if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String responseString = EntityUtils.toString(
                  httpResponse.getEntity(), HTTP.UTF_8);
            Utils.logi(TAG, "getString() RESPONSE=" + responseString);
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

   public static String getFile(String url, String path) {
      InputStream is = getStream(url);
      if (is != null) {
         InputStreamHelper helper = new InputStreamHelper(is);
         if (helper.writeToFile(path)) {
            Utils.logi(TAG, "getFile() PATH=" + url);
            return path;
         }
      }

      return null;
   }

   protected static CacheManager getCacheManager(Context context) {
      return ((NewsApp) context.getApplicationContext()).getCacheManager();
   }

   protected static NewsAbstractCache getNewsAbstractCache(Context context) {
      return getCacheManager(context).getNewsAbstractCache();
   }

}
