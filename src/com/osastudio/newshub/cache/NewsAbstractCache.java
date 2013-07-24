package com.osastudio.newshub.cache;

import com.osastudio.newshub.data.NewsAbstractList;

import android.content.Context;

public class NewsAbstractCache extends Cache {

   private NewsAbstractList abstractList;
   
   public NewsAbstractCache(Context context) {
      super(context);
   }

   public NewsAbstractList getNewsAbstractList() {
      return this.abstractList;
   }
   
   public void setNewsAbstractList(NewsAbstractList list) {
      this.abstractList = list;
   }
  
   @Override
   public void clean() {
      this.abstractList = null;
   }

}
