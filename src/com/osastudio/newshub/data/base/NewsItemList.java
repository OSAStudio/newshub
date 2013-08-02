package com.osastudio.newshub.data.base;

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
      T itemId = null;
      for (int i = 0; i < this.list.size(); i++) {
         itemId = this.list.get(i);
         if (itemId != null) {
            this.itemMap.put(itemId.getId(), i);
         }
      }
   }

   public int getItemIndex(T itemId) {
      if (itemId == null || TextUtils.isEmpty(itemId.getId())
            || !this.itemMap.containsKey(itemId.getId())) {
         return -1;
      }
      return this.itemMap.get(itemId.getId());
   }

}
