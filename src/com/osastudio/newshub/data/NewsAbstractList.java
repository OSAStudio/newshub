package com.osastudio.newshub.data;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

public class NewsAbstractList extends NewsObjectList<NewsAbstract> {

   private HashMap<String, Integer> abstractMap;

   public NewsAbstractList(JSONObject jsonObject) {
      super(jsonObject);

      if (isSuccess()) {
         try {
            if (!jsonObject.isNull(JSON_KEY_LIST)) {
               JSONArray jsonArray = jsonObject.getJSONArray(JSON_KEY_LIST);
               NewsAbstract abs = null;
               for (int i = 0; i < jsonArray.length(); i++) {
                  try {
                     if (!jsonArray.isNull(i)) {
                        abs = NewsAbstract.parseJsonObject(jsonArray
                              .getJSONObject(i));
                        if (abs != null) {
                           list.add(abs);
                        }
                     }
                  } catch (JSONException e) {
                     continue;
                  }
               }
               setAbstractList(list);
            }
         } catch (JSONException e) {

         }
      }
   }

   public List<NewsAbstract> getAbstractList() {
      return this.list;
   }

   public NewsAbstractList setAbstractList(List<NewsAbstract> list) {
      setList(list);
      return this;
   }
   
   public void setList(List<NewsAbstract> list) {
      this.list = list;
      this.abstractMap = new LinkedHashMap<String, Integer>();
      NewsAbstract abs = null;
      for (int i = 0; i < this.list.size(); i++) {
         abs = this.list.get(i);
         if (abs != null) {
            this.abstractMap.put(abs.getArticleId(), i);
         }
      }
   }

   public int getNewsAbstractIndex(NewsAbstract abs) {
      if (abs == null || TextUtils.isEmpty(abs.getArticleId())
            || !this.abstractMap.containsKey(abs.getArticleId())) {
         return -1;
      }
      return this.abstractMap.get(abs.getArticleId());
   }

}
