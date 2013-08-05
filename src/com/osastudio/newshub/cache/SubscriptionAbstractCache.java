package com.osastudio.newshub.cache;

import com.osastudio.newshub.data.SubscriptionAbstractList;

import android.content.Context;

public class SubscriptionAbstractCache extends Cache {

   private SubscriptionAbstractList abstracts;
   
   public SubscriptionAbstractCache(Context context) {
      super(context);
   }

   public SubscriptionAbstractList getAbstracts() {
      return this.abstracts;
   }
   
   public void setAbstracts(SubscriptionAbstractList abstacts) {
      this.abstracts = abstacts;
   }
  
   @Override
   public void clean() {
      this.abstracts = null;
   }

}
