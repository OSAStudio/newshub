package com.osastudio.newshub;

import com.osastudio.newshub.cache.CacheManager;
import com.osastudio.newshub.cache.NewsAbstractCache;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

public class NewsBaseActivity extends Activity {

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      
      if (getActivityStack() == null) {
         ((NewsApp) getApplication()).prepareEnvironment();
      }
      
      getActivityStack().push(this);
   }
   
	protected void onPause() {
	   super.onPause();
	}
	
	@Override
	protected void onResume() {
	   super.onResume();
	}
   
	@Override
	protected void onDestroy() {
      super.onDestroy();
      
      getActivityStack().remove(this);
      
      if (getActivityStack().getCount() <= 0) {
         ((NewsApp) getApplication()).cleanupEnvironment();
      }
	}
   
   public void showToast(Context context, String msg) {
      Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
   }
   
   protected ActivityStack getActivityStack() {
      return ((NewsApp) getApplication()).getActivityStack();
   }
   
   protected CacheManager getCacheManager() {
      return ((NewsApp) getApplication()).getCacheManager();
   }
   
   protected NewsAbstractCache getNewsAbstractCache() {
      return getCacheManager().getNewsAbstractCache();
   }
   
}
