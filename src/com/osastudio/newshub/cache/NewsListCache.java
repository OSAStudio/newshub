package com.osastudio.newshub.cache;

import java.util.List;

import com.osastudio.newshub.data.base.NewsBaseObject;

import android.content.Context;

public abstract class NewsListCache<T extends NewsBaseObject> extends Cache {

   private List<T> list;
   
   public NewsListCache(Context context) {
      super(context);
   }
   
   public List<T> getList() {
      return this.list;
   }
   
   public void setList(List<T> list) {
      this.list = list;
   }

   @Override
   public void clean() {
      
   }

}
