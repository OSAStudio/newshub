package com.osastudio.newshub.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

import com.osastudio.newshub.data.NewsMessageList;
import com.osastudio.newshub.data.NewsMessageScheduleList;

public class NewsMessageApi extends NewsBaseApi {

   protected static String getNewsMessageScheduleService(Context context) {
      return new StringBuilder(getWebServer(context)).append(
            "message!getMessageSendTimeByMobile.do").toString();
   }

   protected static String getNewsMessageListService(Context context) {
      return getNewsMessageListService(context, getWebServer(context));
   }

   protected static String getNewsMessageListService(Context context,
         String server) {
      return new StringBuilder(server).append("message!getMessagesByMobile.do")
            .toString();
   }

   /**
    * Get pushed news message schedule list of an device
    * 
    * @param context
    *           application context
    * @return news message schedule list, or null if failed
    */
   public static NewsMessageScheduleList getNewsMessageScheduleList(
         Context context) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      JSONObject jsonObject = getJsonObject(context,
            getNewsMessageScheduleService(context), params);
      return (jsonObject != null) ? new NewsMessageScheduleList(jsonObject)
            : null;
   }

   public static NewsMessageList getNewsMessageList(Context context,
         String userId) {
      return getNewsMessageList(context, userId, ServerType.AUTOMATIC);
   }

   /**
    * Get pushed news message list by user id, with specified server
    * 
    * @param context
    *           application context
    * @param userId
    *           user identifier
    * @param serverType
    *           specified server, main or backup server
    * @return pushed news message list, or null if failed
    */
   public static NewsMessageList getNewsMessageList(Context context,
         String userId, ServerType serverType) {
      ExtraParameter extras = new ExtraParameter();
      extras.useBackupServerIfFailed = false;
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      JSONObject jsonObject = getJsonObject(
            context,
            getNewsMessageListService(context,
                  getWebServerByType(context, serverType)), params, extras);
      return (jsonObject != null) ? new NewsMessageList(jsonObject) : null;
   }

}
