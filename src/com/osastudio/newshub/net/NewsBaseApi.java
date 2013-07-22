package com.osastudio.newshub.net;

import java.io.IOException;
import java.io.InputStream;
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
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.osastudio.newshub.utils.Utils;

public class NewsBaseApi {

   private static final String TAG = "NewsBaseApi";

   protected static boolean DEBUG = true;

   protected static final String WEB_SERVER = "";
   protected static final String DEBUG_WEB_SERVER = "http://218.23.42.49:9010/azker/admin/";
   
   protected static final HttpMethod DEFAULT_HTTP_METHOD = HttpMethod.HTTP_POST;
   
   protected static final String KEY_DEVICE_ID = "deviceID";
   protected static final String KEY_DEVICE_TYPE = "deviceTYPE";
   
   protected static final String DEVICE_TYPE = "android";
   

   protected static String getWebServer() {
      return DEBUG ? DEBUG_WEB_SERVER : WEB_SERVER;
   }

   protected static String getNewsSplashServiceUrl() {
      return new StringBuilder(getWebServer()).append(
            "loginpicture!getLoginPictureByMobile.do").toString();
   }

   protected static String getNewsChannelListServiceUrl() {
      return new StringBuilder(getWebServer()).append(
            "titleshow!getTitleListByMobile.do").toString();
   }

   protected static String getNewsAbstractListServiceUrl() {
      return new StringBuilder(getWebServer()).append(
            "lesson!getLessonListByMobile.do").toString();
   }

   protected static String getNewsArticleServiceUrl() {
      return new StringBuilder(getWebServer()).append(
            "lesson!getLessonContentByMobile.do").toString();
   }

   protected static String getString(String serviceUrl,
         List<NameValuePair> params) {
      return getString(serviceUrl, params, DEFAULT_HTTP_METHOD);
   }
   
   protected static String getString(String serviceUrl,
         List<NameValuePair> params, HttpMethod method) {
      if (method == HttpMethod.HTTP_GET) {
         return getStringByHttpGet(serviceUrl, params);
      } else {
         return getStringByHttpPost(serviceUrl, params);
      }
   }

   protected static String getStringByHttpGet(String serviceUrl,
         List<NameValuePair> params) {
      return null;
   }

   protected static String getStringByHttpPost(String serviceUrl,
         List<NameValuePair> params) {
      try {
         HttpPost httpRequest = new HttpPost(serviceUrl);
         httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

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
            Utils.logi(TAG, "getFile() URL=" + url 
                  + " length=" + httpResponse.getEntity().getContentLength());
            if (is != null) {
               return is;
            }
         }
      } catch (ClientProtocolException e) {
//         e.printStackTrace();
      } catch (IllegalArgumentException e) {
//         e.printStackTrace();
      } catch (IllegalStateException e) {
//         e.printStackTrace();
      } catch (IOException e) {
//         e.printStackTrace();
      }
      
      return null;
   }

}
