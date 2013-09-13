package com.osastudio.newshub.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

import com.osastudio.newshub.data.AppProperties;

public class AppPropertiesApi extends NewsBaseApi {

   protected static String getAppPropertiesService(Context context) {
      return getAppPropertiesService(context, getWebServer(context));
   }

   protected static String getAppPropertiesService(Context context,
         String server) {
      return new StringBuilder(server).append(
            "loginpicture!checkLoginInfoByMobile.do").toString();
   }

   public static AppProperties getAppProperties(Context context) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      JSONObject jsonObject = getJsonObject(context,
            getAppPropertiesService(context, getDefaultWebServer(context)),
            params);
      AppProperties result = null;
      if (jsonObject != null) {
         result = new AppProperties(jsonObject);
         if (!TextUtils.isEmpty(result.getMainServer())) {
            getPrefsManager(context).setMainServer(result.getMainServer());
            setWebServer(result.getMainServer());
         }
         if (!TextUtils.isEmpty(result.getBackupServer())) {
            getPrefsManager(context).setBackupServer(result.getBackupServer());
         }
      }
      return result;
   }

}
