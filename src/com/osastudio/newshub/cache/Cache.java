package com.osastudio.newshub.cache;

import android.content.Context;

public abstract class Cache {

   protected Context context;
   
   public Cache(Context context) {
      this.context = context;
   }
   
   public Context getContext() {
      return this.context;
   }
   
   public abstract void clean();
   
}
