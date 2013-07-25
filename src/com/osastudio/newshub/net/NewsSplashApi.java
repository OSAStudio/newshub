package com.osastudio.newshub.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

import com.osastudio.newshub.data.NewsSplash;

public class NewsSplashApi extends NewsBaseApi {
   
   private static final String TAG = "NewsSplashApi";
   
   public static NewsSplash getNewsSpalsh(Context context) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      String jsonString = getString(getNewsSplashServiceUrl(), params);
      if (TextUtils.isEmpty(jsonString)) {
         return null;
      }
      
      JSONObject jsonObject = null;
      try {
         jsonObject = new JSONObject(jsonString);
      } catch (JSONException e) {
//         e.printStackTrace();
         return null;
      }
      if (jsonObject.length() <= 0) {
         return null;
      }
      
      return NewsSplash.parseJsonObject(jsonObject);
   }

}
