package com.osastudio.newshub.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsItemList;

/**
 * Daily reminder list for processing recommended content list for different
 * user pushed by server
 * 
 * @author Rujin Xue
 * 
 */
public class DailyReminderList extends NewsItemList<DailyReminder> {

   public DailyReminderList(JSONObject jsonObject) {
      super(jsonObject);

      if (isSuccess()) {
         try {
            if (!jsonObject.isNull(JSON_KEY_LIST)) {
               JSONArray jsonArray = jsonObject.getJSONArray(JSON_KEY_LIST);
               DailyReminder topic = null;
               for (int i = 0; i < jsonArray.length(); i++) {
                  try {
                     if (!jsonArray.isNull(i)) {
                        topic = DailyReminder.parseJsonObject(jsonArray
                              .getJSONObject(i));
                        if (topic != null) {
                           this.list.add(topic);
                        }
                     }
                  } catch (JSONException e) {
                     continue;
                  }
               }
               setList(this.list);
            }
         } catch (JSONException e) {

         }
      }
   }

}
