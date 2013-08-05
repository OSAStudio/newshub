package com.osastudio.newshub.cache;

import com.osastudio.newshub.data.NewsAbstractList;

import android.content.Context;

public class NewsAbstractCache extends Cache {

   private NewsAbstractList abstracts;
   
   public NewsAbstractCache(Context context) {
      super(context);
   }

   public NewsAbstractList getAbstracts() {
      return this.abstracts;
   }
   
   public void setAbstracts(NewsAbstractList abstracts) {
      this.abstracts = abstracts;
   }
   
   public NewsAbstractList getNewsAbstractList() {
      return getAbstracts();
   }
   
   public void setNewsAbstractList(NewsAbstractList abstracts) {
      setAbstracts(abstracts);
   }
  
   @Override
   public void clean() {
      this.abstracts = null;
   }

}
