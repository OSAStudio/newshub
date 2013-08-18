package com.osastudio.newshub;

import com.osastudio.newshub.utils.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

   @Override
   public void onReceive(Context context, Intent intent) {
      Utils.logi("BootReceiver", "______________" + intent.getAction());
      Intent serviceIntent = new Intent();
      serviceIntent.setClass(context, NewsService.class);
      context.startService(serviceIntent);
   }

}
