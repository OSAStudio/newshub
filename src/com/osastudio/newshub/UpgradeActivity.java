package com.osastudio.newshub;

import com.huadi.azker_phone.R;
import com.osastudio.newshub.data.AppProperties;
import com.osastudio.newshub.utils.UIUtils;
import com.osastudio.newshub.utils.Utils;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;

public class UpgradeActivity extends NewsBaseActivity {

   private AppProperties mAppProperties;
   
   protected ServiceConnection mNewsServiceConn = new ServiceConnection() {
      @Override
      public void onServiceDisconnected(ComponentName name) {

      }

      @Override
      public void onServiceConnected(ComponentName name, IBinder service) {
         setNewsService(INewsService.Stub.asInterface(service));
      }
   };


   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      
      bindNewsService(mNewsServiceConn);

      setContentView(R.layout.upgrade_activity);

      checkUpgrade();
   }

   @Override
   protected void onNewIntent(Intent intent) {
      super.onNewIntent(intent);

      setIntent(intent);

      checkUpgrade();
   }

   @Override
   protected void onDestroy() {
      super.onDestroy();

      unbindNewService(mNewsServiceConn);
   }

   private void checkUpgrade() {
      Intent intent = getIntent();
      mAppProperties = intent
            .getParcelableExtra(AppProperties.EXTRA_APP_PROPERTIES);
      if (mAppProperties != null) {
         showUpgradeDialog();
      } else {
         finish();
      }
   }

   public void showUpgradeDialog() {
      PackageInfo pkgInfo = Utils.getPackageInfo(this);
      StringBuilder sb = new StringBuilder(getString(R.string.upgrade_prompt,
            mAppProperties.getVersionName()));
      sb.append("\n").append(mAppProperties.getReleaseNotes());

      boolean neccessary = mAppProperties.isUpgradeNecessary(pkgInfo);
      if (neccessary) {
         sb.append("\n\n").append(getString(R.string.upgrade_neccessary));
      }

      AlertDialog.Builder builder = UIUtils.getAlertDialogBuilder(this);
      builder.setIcon(R.drawable.icon).setTitle(R.string.upgrade)
            .setMessage(sb.toString());

      if (neccessary) {
         builder.setPositiveButton(R.string.upgrade_now,
               new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int which) {
                     ((NewsApp) getApplication()).getActivityStack()
                           .finishAll();
                     downloadApk(mAppProperties.getApkUrl());
                  }
               }).setNegativeButton(R.string.exit,
               new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int which) {
                     ((NewsApp) getApplication()).getActivityStack()
                           .finishAll();
                  }
               });
      } else {
         builder.setPositiveButton(R.string.upgrade_now,
               new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int which) {
                     finish();
                     downloadApk(mAppProperties.getApkUrl());
                  }
               }).setNegativeButton(R.string.upgrade_later,
               new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int which) {
                     finish();
                  }
               });
      }
      builder.setCancelable(false).show();
   }

   private void downloadApk(String url) {
      try {
         getNewsService().downloadApk(url);
      } catch (Exception e) {
         
      }
//      Uri uri = Uri.parse(url);
//      Intent intent = new Intent(Intent.ACTION_VIEW);
//      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//      intent.setData(uri);
//      try {
//         getApplicationContext().startActivity(intent);
//      } catch (ActivityNotFoundException e) {
//
//      }
   }

}
