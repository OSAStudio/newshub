package com.osastudio.newshub;

import com.huadi.azker_phone.R;
import com.osastudio.newshub.utils.Utils;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.TextView;



/**
 * Show about content page.
 * 
 * @author pengyue
 *
 */
public class AboutActivity extends NewsBaseActivity {

   private ViewGroup mTextContentLayout;
   private WebView mHtmlContentView;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      setContentView(R.layout.activity_about);

      findViews();
      initViews();
   }

   private void findViews() {
      mTextContentLayout = (ViewGroup) findViewById(R.id.text_content);
      mHtmlContentView = (WebView) findViewById(R.id.html_content);
   }

   private void initViews() {
      TextView titleView = (TextView) findViewById(R.id.about_title);
      titleView.setText(getString(R.string.about_title,
            Utils.getVersionName(this)));

      TextView privacyView = (TextView) findViewById(R.id.privacy);
      privacyView.setOnClickListener(new OnClickListener() {
         public void onClick(View v) {
            mTextContentLayout.setVisibility(View.INVISIBLE);
            mHtmlContentView.setVisibility(View.VISIBLE);
            mHtmlContentView.loadUrl("file:///android_asset/privacy.html");
         }
      });
      TextView announceView = (TextView) findViewById(R.id.announce);
      announceView.setOnClickListener(new OnClickListener() {
         public void onClick(View v) {
            mTextContentLayout.setVisibility(View.INVISIBLE);
            mHtmlContentView.setVisibility(View.VISIBLE);
            mHtmlContentView.loadUrl("file:///android_asset/announce.html");
         }
      });

      mHtmlContentView.setBackgroundColor(0);
      mHtmlContentView.getSettings().setLayoutAlgorithm(
            LayoutAlgorithm.SINGLE_COLUMN);
      mHtmlContentView.getSettings().setDefaultTextEncodingName("utf-8");
      mHtmlContentView
            .setOnLongClickListener(new WebView.OnLongClickListener() {
               public boolean onLongClick(View v) {
                  return true;
               }
            });
      mHtmlContentView.setLongClickable(false);
   }

   @Override
   public void onBackPressed() {
      if (mHtmlContentView.getVisibility() == View.VISIBLE) {
         mHtmlContentView.setVisibility(View.INVISIBLE);
         mTextContentLayout.setVisibility(View.VISIBLE);
         return;
      }
      super.onBackPressed();
   }

}
