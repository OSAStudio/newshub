package com.osastudio.newshub.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

import com.osastudio.newshub.data.AppProperties;

public class AppPropertiesApi extends NewsBaseApi {

   private static final String TAG = "AppPropertiesApi";

   public static AppProperties getAppProperties(Context context) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      JSONObject jsonObject = getJsonObject(getAppPropertiesService(),
            params);
      return (jsonObject != null) ? new AppProperties(jsonObject) : null;
   }

}
