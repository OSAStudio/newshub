package com.osastudio.newshub.data;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class NewsObjectList<T> extends NewsBaseObject {

   public static final String JSON_KEY_LIST = "list";

   protected List<T> list;

   public NewsObjectList() {
      super();
      this.list = new ArrayList<T>();
   }
   
   public NewsObjectList(JSONObject jsonObject) {
      super(jsonObject);
   }

   public List<T> getList() {
      return this.list;
   }

   public void setList(List<T> list) {
      this.list = list;
   }

}
