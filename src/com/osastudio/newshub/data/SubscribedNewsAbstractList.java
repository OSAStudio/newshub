package com.osastudio.newshub.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsItemList;

public class SubscribedNewsAbstractList extends
      NewsItemList<SubscribedNewsAbstract> {

   public SubscribedNewsAbstractList(JSONObject jsonObject) {
      super(jsonObject);

      if (isSuccess()) {
         try {
            if (!jsonObject.isNull(JSON_KEY_LIST)) {
               JSONArray jsonArray = jsonObject.getJSONArray(JSON_KEY_LIST);
               SubscribedNewsAbstract abs = null;
               ArrayList<SubscribedNewsAbstract> abstracts = new ArrayList<SubscribedNewsAbstract>();
               for (int i = 0; i < jsonArray.length(); i++) {
                  try {
                     if (!jsonArray.isNull(i)) {
                        abs = SubscribedNewsAbstract.parseJsonObject(jsonArray
                              .getJSONObject(i));
                        if (abs != null) {
                           abstracts.add(abs);
                        }
                     }
                  } catch (JSONException e) {
                     continue;
                  }
               }
               setList(abstracts);
            }
         } catch (JSONException e) {

         }
      }
   }

}
