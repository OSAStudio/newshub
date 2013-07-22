package com.osastudio.newshub.data;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewsChannelList extends NewsBaseObject {

   public static final String JSON_KEY_CHANNEL_LIST = "list";

   private List<NewsChannel> channelList;

   public NewsChannelList() {
      this.channelList = new ArrayList<NewsChannel>();
   }

   public List<NewsChannel> getChannelList() {
      return channelList;
   }

   public NewsChannelList setChannelList(List<NewsChannel> channelList) {
      this.channelList = channelList;
      return this;
   }

   public static NewsChannelList parseJsonObject(JSONObject jsonObject) {
      NewsChannelList result = null;
      try {
         if (jsonObject.isNull(JSON_KEY_RESULT_CODE)
               || NewsChannelList.isResultFailure(jsonObject
                     .getString(JSON_KEY_RESULT_CODE))) {
            return null;
         }

         result = new NewsChannelList();
         if (!jsonObject.isNull(JSON_KEY_CHANNEL_LIST)) {
            JSONArray jsonArray = jsonObject
                  .getJSONArray(JSON_KEY_CHANNEL_LIST);
            for (int i = 0; i < jsonArray.length(); i++) {
               try {
                  if (!jsonArray.isNull(i)) {
                     result.getChannelList().add(
                           NewsChannel.parseJsonObject(jsonArray
                                 .getJSONObject(i)));
                  }
               } catch (JSONException e) {
                  continue;
               }
            }
         }
      } catch (JSONException e) {

      }

      return result;
   }

}
