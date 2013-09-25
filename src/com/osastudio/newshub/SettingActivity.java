package com.osastudio.newshub;

import com.huadi.azker_phone.R;
import com.osastudio.newshub.data.NewsResult;
import com.osastudio.newshub.library.AppSettings;
import com.osastudio.newshub.library.PreferenceManager;
import com.osastudio.newshub.net.UserApi;
import com.osastudio.newshub.utils.NewsResultAsyncTask;
import com.osastudio.newshub.utils.Utils;
import com.osastudio.newshub.widgets.RegisterView;
import com.osastudio.newshub.widgets.RegisterView.USER_TYPE;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class SettingActivity extends NewsBaseActivity implements AppSettings {
   private View mAccountManager = null;
   private View mAddAcountManager = null;
   private View mIncreaseBtn = null;
   private View mDecreaseBtn = null;
   private int mFontSize = DEFAULT_FONT_SIZE;
   private View mDownloadClose = null;
   private View mDownloadOpen = null;
   private boolean mAutoLoadingPicture = AUTO_LOADING_PICTURE;
   private View mHelp = null;
   private ViewGroup mCheckUpdate = null;
   private View mAbout = null;
   private ViewGroup mFontSizeGroup, mAutoDownloadGroup;
   private TextView mFontSizePrompt;
   private ProgressDialog mPDlg = null;
   private CheckTask mCheckTask = null;
   private TextView mVersionName;
   private TextView mSystemInfo;

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

      setContentView(R.layout.activity_setting);
      findViews();
      initViews();
   }

   private void findViews() {
      mAccountManager = findViewById(R.id.account_manager);
      mAddAcountManager = findViewById(R.id.add_account);
      mIncreaseBtn = findViewById(R.id.increase_btn);
      mDecreaseBtn = findViewById(R.id.decrease_btn);
      mFontSizePrompt = (TextView) findViewById(R.id.font_size_prompt);
      mDownloadClose = findViewById(R.id.close_btn);
      mDownloadOpen = findViewById(R.id.open_btn);
      mHelp = findViewById(R.id.help);
      mCheckUpdate = (ViewGroup) findViewById(R.id.check_update);
      mAbout = findViewById(R.id.about);
      mFontSizeGroup = (ViewGroup) findViewById(R.id.font_size_group);
      mAutoDownloadGroup = (ViewGroup) findViewById(R.id.auto_download_group);
      mVersionName = (TextView) findViewById(R.id.version_name);
      mSystemInfo = (TextView) findViewById(R.id.system_info);
   }

   private void initViews() {
      PreferenceManager prefsManager = getPrefsManager();
      mFontSize = prefsManager.getFontSize();
      mFontSizePrompt.setText(getString(R.string.text_size_sub, mFontSize));
      View.OnClickListener listener = new View.OnClickListener() {
         public void onClick(View v) {
            if (v.getId() == R.id.increase_btn) {
               mFontSize += 2;
            } else if (v.getId() == R.id.decrease_btn) {
               mFontSize -= 2;
            }
            
            if (mFontSize < MIN_FONT_SIZE) {
               mFontSize = MIN_FONT_SIZE;
            } else if (mFontSize > MAX_FONT_SIZE) {
               mFontSize = MAX_FONT_SIZE;
            }
		      mFontSizePrompt.setText(getString(R.string.text_size_sub, mFontSize));
         }
      };
      mIncreaseBtn.setOnClickListener(listener);
      mDecreaseBtn.setOnClickListener(listener);

      mAutoLoadingPicture = prefsManager.isAutoLoadingPictureEnabled();
      mDownloadOpen.setSelected(!mAutoLoadingPicture);
      mDownloadClose.setSelected(mAutoLoadingPicture);
      listener = new View.OnClickListener() {
         public void onClick(View v) {
            if (v.getId() == R.id.open_btn) {
               mAutoLoadingPicture = false;
               mDownloadOpen.setSelected(true);
               mDownloadClose.setSelected(false);
            } else if (v.getId() == R.id.close_btn) {
               mAutoLoadingPicture = true;
               mDownloadOpen.setSelected(false);
               mDownloadClose.setSelected(true);
            }
         }
      };
      mDownloadOpen.setOnClickListener(listener);
      mDownloadClose.setOnClickListener(listener);

      mAccountManager.setOnClickListener(new OnClickListener() {
         public void onClick(View v) {
            Intent it = new Intent(SettingActivity.this,
                  UserManagerActivity.class);
            startActivity(it);
         }
      });

      mAddAcountManager.setOnClickListener(new OnClickListener() {

         public void onClick(View v) {
            mPDlg = Utils.showProgressDlg(SettingActivity.this, null);
            mCheckTask = new CheckTask(SettingActivity.this);
            mCheckTask.execute();
         }
      });

      DisplayMetrics dm = new DisplayMetrics();
      getWindowManager().getDefaultDisplay().getMetrics(dm);
      mSystemInfo.setText(getString(R.string.system_sub, dm.heightPixels,
            dm.widthPixels, dm.densityDpi, dm.density));

      mCheckUpdate.setOnClickListener(new OnClickListener() {
         public void onClick(View v) {
            try {
               getNewsService().checkNewVersion();
            } catch (Exception e) {
               // e.printStackTrace();
            }
         }
      });
      mVersionName.setText(getString(R.string.check_update_sub,
            Utils.getVersionName(this)));

      mAbout.setOnClickListener(new OnClickListener() {
         public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(SettingActivity.this, AboutActivity.class);
            startActivity(intent);
         }
      });
   }

   @Override
   protected void onPause() {
      super.onPause();
      
      PreferenceManager prefsManager = getPrefsManager();
      prefsManager.setFontSize(mFontSize);
      prefsManager.enableAutoLoadingPicture(mAutoLoadingPicture);
   }

   @Override
   protected void onDestroy() {
      super.onDestroy();

      unbindNewService(mNewsServiceConn);
   }

   private void showRegisterView(int w, int h) {
      RegisterView registerDlg = new RegisterView(this,
            R.style.Theme_PageDialog, w, h, USER_TYPE.ADD);
      registerDlg.show();
   }

   private class CheckTask extends NewsResultAsyncTask<Void, Void, NewsResult> {

      public CheckTask(Context context) {
         super(context);
      }

      protected NewsResult doInBackground(Void... params) {
         return UserApi.getValidateStatus(SettingActivity.this);

      }

      @Override
      public void onPostExecute(NewsResult result) {
         if (mPDlg != null) {
            Utils.closeProgressDlg(mPDlg);
            mPDlg = null;
         }

         super.onPostExecute(result);

         if (result != null && result.isSuccess()) {
            WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();

            int screenWidth = display.getWidth();
            screenWidth = screenWidth > 0 ? screenWidth : 0;
            int screenHeight = display.getHeight();
            screenHeight = screenHeight > 0 ? screenHeight : 0;
            showRegisterView(screenWidth, screenHeight);
         }

      }

   }

}