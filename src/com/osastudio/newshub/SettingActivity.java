package com.osastudio.newshub;

import com.huadi.azker_phone.R;
import com.osastudio.newshub.library.AppSettings;
import com.osastudio.newshub.library.PreferenceManager;
import com.osastudio.newshub.utils.Utils;

import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
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
   private View mCheckUpdate = null;
   private View mAbout = null;
   private ViewGroup mFontSizeGroup, mAutoDownloadGroup;
   private TextView mFontSizePrompt;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
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
      mCheckUpdate = findViewById(R.id.check_update);
      mAbout = findViewById(R.id.about);
      mFontSizeGroup = (ViewGroup) findViewById(R.id.font_size_group);
      mAutoDownloadGroup = (ViewGroup) findViewById(R.id.auto_download_group);
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
               prefsManager.enableAutoLoadingPicture(true);
               mDownloadOpen.setSelected(true);
               mDownloadClose.setSelected(false);
            } else if (v.getId() == R.id.close_btn) {
               prefsManager.enableAutoLoadingPicture(false);
               mDownloadOpen.setSelected(false);
               mDownloadClose.setSelected(true);
            }
         }
      };
      if (autoLoading) {
         mDownloadOpen.setSelected(true);
         mDownloadClose.setSelected(false);
      } else {
         mDownloadOpen.setSelected(false);
         mDownloadClose.setSelected(true);
      }
      mDownloadOpen.setOnClickListener(listener);
      mDownloadClose.setOnClickListener(listener);
   }

}