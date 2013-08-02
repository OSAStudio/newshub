package com.osastudio.newshub.data.user;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsObjectList;

public class UserList extends NewsObjectList<User> {

   public UserList(JSONObject jsonObject) {
      super(jsonObject);

      if (isSuccess()) {
         try {
            if (!jsonObject.isNull(JSON_KEY_LIST)) {
               JSONArray jsonArray = jsonObject.getJSONArray(JSON_KEY_LIST);
               for (int i = 0; i < jsonArray.length(); i++) {
                  try {
                     if (!jsonArray.isNull(i)) {
                        this.list.add(User.parseJsonObject(jsonArray
                              .getJSONObject(i)));
                     }
                  } catch (JSONException e) {
                     continue;
                  }
               }
            }
         } catch (JSONException e) {

         }
      }
   }

}
