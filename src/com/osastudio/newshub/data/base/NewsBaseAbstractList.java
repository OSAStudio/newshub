package com.osastudio.newshub.data.base;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class NewsBaseAbstractList<T extends NewsBaseAbstract> extends
      NewsItemList<T> {

   public NewsBaseAbstractList() {
      super();
   }

   public NewsBaseAbstractList(JSONObject jsonObject) {
      super(jsonObject);
   }

   public List<NewsBaseAbstract> asNewsBaseAbstractList() {
      List<NewsBaseAbstract> result = new ArrayList<NewsBaseAbstract>();
      for (int i = 0; i < this.list.size(); i++) {
         result.add(this.list.get(i));
      }
      return result;
   }

}
