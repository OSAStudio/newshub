package com.osastudio.newshub.library;

import android.content.Context;
import android.os.Handler;

public abstract class NewsTask<T> extends Thread {

   protected Context context;
   protected Handler handler;

   public NewsTask(Handler handler) {
      this.handler = handler;
   }

   public NewsTask(Handler handler, Context context) {
      this(handler);
      this.context = context;
   }

   @Override
   public void run() {
      final T result = doInBackground();

      if (this.handler != null) {
         this.handler.post(new Runnable() {
            public void run() {
               onPostExecute(result);
            }
         });
      }
   }

   public abstract T doInBackground();

   public abstract void onPostExecute(T result);

}
