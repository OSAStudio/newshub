package com.osastudio.newshub;

import java.util.ArrayList;

import com.huadi.azker_phone.R;
import com.osastudio.newshub.data.user.UserInfo;
import com.osastudio.newshub.data.user.UserInfoList;
import com.osastudio.newshub.data.user.ValidateResult;
import com.osastudio.newshub.library.AppSettings;
import com.osastudio.newshub.library.PreferenceManager;
import com.osastudio.newshub.net.UserApi;
import com.osastudio.newshub.utils.Utils;
import com.osastudio.newshub.widgets.RegisterView;
import com.osastudio.newshub.widgets.RegisterView.USER_TYPE;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.Display;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SettingActivity extends NewsBaseActivity implements AppSettings {
   private View mAccountManager = null;
   private View mAddAcountManager = null;
   private Button mTextBig = null;
   private Button mTextNormal = null;
   private Button mTextSmall = null;
   private Button mDownloadClose = null;
   private Button mDownloadOpen = null;
   private View mHelp = null;
   private ViewGroup mCheckUpdate = null;
   private View mAbout = null;
   private ViewGroup mFontSizeGroup, mAutoDownloadGroup;
   private TextView mFontSizePrompt;
   private ProgressDialog mPDlg = null;
   private CheckTask mCheckTask = null;
   private TextView mVersionName;

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
      mTextBig = (Button) findViewById(R.id.big_btn);
      mTextNormal = (Button) findViewById(R.id.normal_btn);
      mTextSmall = (Button) findViewById(R.id.small_btn);
      mFontSizePrompt = (TextView) findViewById(R.id.font_size_prompt);
      mDownloadClose = (Button) findViewById(R.id.close_btn);
      mDownloadOpen = (Button) findViewById(R.id.open_btn);
      mHelp = findViewById(R.id.help);
      mCheckUpdate = (ViewGroup) findViewById(R.id.check_update);
      mAbout = findViewById(R.id.about);
      mFontSizeGroup = (ViewGroup) findViewById(R.id.font_size_group);
      mAutoDownloadGroup = (ViewGroup) findViewById(R.id.auto_download_group);
      mVersionName = (TextView) findViewById(R.id.version_name);
   }

   private void initViews() {
      PreferenceManager prefsManager = getPrefsManager();
      int fontSize = prefsManager.getFontSize();
      boolean autoLoading = prefsManager.isAutoLoadingPictureEnabled();

      int resId = 0;
      if (fontSize == FONT_SIZE_BIG) {
         mTextBig.setSelected(true);
         mTextNormal.setSelected(false);
         mTextSmall.setSelected(false);
         resId = R.string.big;
      } else if (fontSize == FONT_SIZE_NORMAL) {
         mTextBig.setSelected(false);
         mTextNormal.setSelected(true);
         mTextSmall.setSelected(false);
         resId = R.string.normal;
      } else if (fontSize == FONT_SIZE_SMALL) {
         mTextBig.setSelected(false);
         mTextNormal.setSelected(false);
         mTextSmall.setSelected(true);
         resId = R.string.small;
      }
      if (resId > 0) {
         mFontSizePrompt.setText(getString(R.string.text_size_sub,
               getString(resId)));
      }

      View.OnClickListener listener = new View.OnClickListener() {
         public void onClick(View v) {
            int fontSize = 0;
            int resId = 0;
            if (v.getId() == R.id.big_btn) {
               mTextBig.setSelected(true);
               mTextNormal.setSelected(false);
               mTextSmall.setSelected(false);
               fontSize = FONT_SIZE_BIG;
               resId = R.string.big;
            } else if (v.getId() == R.id.normal_btn) {
               mTextBig.setSelected(false);
               mTextNormal.setSelected(true);
               mTextSmall.setSelected(false);
               fontSize = FONT_SIZE_NORMAL;
               resId = R.string.normal;
            } else if (v.getId() == R.id.small_btn) {
               mTextBig.setSelected(false);
               mTextNormal.setSelected(false);
               mTextSmall.setSelected(true);
               fontSize = FONT_SIZE_SMALL;
               resId = R.string.small;
            }
            if (fontSize > 0) {
               getPrefsManager().setFontSize(fontSize);
            }
            if (resId > 0) {
               mFontSizePrompt.setText(getString(R.string.text_size_sub,
                     getString(resId)));
            }
         }
      };
      mTextBig.setOnClickListener(listener);
      mTextNormal.setOnClickListener(listener);
      mTextSmall.setOnClickListener(listener);

      listener = new View.OnClickListener() {
         public void onClick(View v) {
            PreferenceManager prefsManager = getPrefsManager();
            if (v.getId() == R.id.open_btn) {
               prefsManager.enableAutoLoadingPicture(false);
               mDownloadOpen.setSelected(true);
               mDownloadClose.setSelected(false);
            } else if (v.getId() == R.id.close_btn) {
               prefsManager.enableAutoLoadingPicture(true);
               mDownloadOpen.setSelected(false);
               mDownloadClose.setSelected(true);
            }
         }
      };
      if (autoLoading) {
         mDownloadOpen.setSelected(false);
         mDownloadClose.setSelected(true);
      } else {
         mDownloadOpen.setSelected(true);
         mDownloadClose.setSelected(false);
      }
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
            mCheckTask = new CheckTask();
            mCheckTask.execute();
         }
      });

      mCheckUpdate.setOnClickListener(new OnClickListener() {
         public void onClick(View v) {
            try {
               getNewsService().checkNewVersion();
            } catch (Exception e) {
//               e.printStackTrace();
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
   protected void onDestroy() {
      super.onDestroy();

      unbindNewService(mNewsServiceConn);
   }

   private void showRegisterView(int w, int h) {
      RegisterView registerDlg = new RegisterView(this,
            R.style.Theme_PageDialog, w, h, USER_TYPE.ADD);
      registerDlg.show();
   }

   private class CheckTask extends AsyncTask<Void, Void, Boolean> {

      protected Boolean doInBackground(Void... params) {
         ValidateResult result = UserApi
               .getValidateStatus(SettingActivity.this);
         if (result != null) {
        	 return result.isSuccess();
         } else {
        	 return false;
         }
      }

      @Override
      protected void onPostExecute(Boolean result) {
         if (mPDlg != null) {
            Utils.closeProgressDlg(mPDlg);
            mPDlg = null;
         }

         if (result) {
            WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();

            int screenWidth = display.getWidth();
            screenWidth = screenWidth > 0 ? screenWidth : 0;
            int screenHeight = display.getHeight();
            screenHeight = screenHeight > 0 ? screenHeight : 0;
            showRegisterView(screenWidth, screenHeight);
         } else {
            Utils.ShowConfirmDialog(SettingActivity.this,
                  SettingActivity.this.getString(R.string.cant_add_user_msg),
                  null);
         }

         super.onPostExecute(result);
      }

   }

}