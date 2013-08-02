package com.osastudio.newshub.data;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.base.NewsItemList;

public class NewsAbstractList extends NewsItemList<NewsAbstract> {

   public NewsAbstractList(JSONObject jsonObject) {
      super(jsonObject);

      if (isSuccess()) {
         try {
            if (!jsonObject.isNull(JSON_KEY_LIST)) {
               JSONArray jsonArray = jsonObject.getJSONArray(JSON_KEY_LIST);
               NewsAbstract abs = null;
               List<NewsAbstract> abstracts = new ArrayList<NewsAbstract>();
               for (int i = 0; i < jsonArray.length(); i++) {
                  try {
                     if (!jsonArray.isNull(i)) {
                        abs = NewsAbstract.parseJsonObject(jsonArray
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

   public List<NewsAbstract> getAbstractList() {
      return this.list;
   }

}
