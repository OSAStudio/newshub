package com.osastudio.newshub.data;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONObject;

import android.text.TextUtils;

public class NewsItemList<T extends NewsId> extends NewsObjectList<T> {

   private HashMap<String, Integer> itemMap;

   public NewsItemList() {
      super();
   }

   public NewsItemList(JSONObject jsonObject) {
      super(jsonObject);
   }

   @Override
   public void setList(List<T> list) {
      this.list = list;
      this.itemMap = new LinkedHashMap<String, Integer>();
      T newsId = null;
      for (int i = 0; i < this.list.size(); i++) {
         newsId = this.list.get(i);
         if (newsId != null) {
            this.itemMap.put(newsId.getId(), i);
         }
      }
   }

   public int getItemIndex(T newId) {
      if (newId == null || TextUtils.isEmpty(newId.getId())
            || !this.itemMap.containsKey(newId.getId())) {
         return -1;
      }
      return this.itemMap.get(newId.getId());
   }

}
