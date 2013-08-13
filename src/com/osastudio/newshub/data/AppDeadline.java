package com.osastudio.newshub.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.utils.Utils;

import android.text.TextUtils;

public class AppDeadline {

   private static String EXPIRED_TIME = "11,31,2013";
   private Date currentTime;

   public AppDeadline() {
      this.currentTime = new Date(System.currentTimeMillis());
   }

   public AppDeadline(JSONObject jsonObject) {
      this();
      try {
         if (!jsonObject.isNull("weatherinfo")) {
            JSONObject obj = jsonObject.getJSONObject("weatherinfo");
            if (obj != null && !obj.isNull("date_y")) {
               String dateString = obj.getString("date_y");
               if (!TextUtils.isEmpty(dateString)) {
                  this.currentTime = new SimpleDateFormat("yyyy年MM月dd日")
                        .parse(dateString);
               }
            }
         }
      } catch (JSONException e) {
         // e.printStackTrace();
      } catch (ParseException e) {
         // e.printStackTrace();
      }
   }

   public boolean hasExpired() {
      try {
         return this.currentTime.after(new SimpleDateFormat("MM,dd,yyyy")
               .parse(EXPIRED_TIME));
      } catch (ParseException e) {
         // e.printStackTrace();
      }
      return false;
   }

}