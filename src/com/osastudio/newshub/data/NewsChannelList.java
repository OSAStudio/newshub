package com.osastudio.newshub.data;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsChannelList extends NewsBaseObject implements Parcelable {

   public static final String JSON_KEY_CHANNEL_LIST = "list";

   private List<NewsChannel> channelList;

   public NewsChannelList() {
      this.channelList = new ArrayList<NewsChannel>();
   }

   public NewsChannelList(Parcel src) {
      this.channelList = src.createTypedArrayList(NewsChannel.CREATOR);
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

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dst, int flags) {
      dst.writeTypedList(this.channelList);
   }

   public static final Parcelable.Creator<NewsChannelList> CREATOR = new Creator<NewsChannelList>() {
      @Override
      public NewsChannelList createFromParcel(Parcel src) {
         return new NewsChannelList(src);
      }

      @Override
      public NewsChannelList[] newArray(int size) {
         return new NewsChannelList[size];
      }
   };

}
