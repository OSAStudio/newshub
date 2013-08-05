package com.osastudio.newshub;

import com.huadi.azker_phone.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class SettingActivity extends Activity {
	private View mAccountManager = null;
	private View mAddAcountManager = null;
	private View mTextBig = null;
	private View mTextNormal = null;
	private View mTextSmall = null;
	private View mDownloadClose = null;
	private View mDownloadOpen = null;
	private View mHelp = null;
	private View mCheckUpdate = null;
	private View mAbout = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		findviews();
	}
	
	private void findviews() {
		mAccountManager = findViewById(R.id.account_manager);
		mAddAcountManager =  findViewById(R.id.add_account);
		mTextBig =  findViewById(R.id.big_btn);
		mTextNormal =  findViewById(R.id.normal_btn);
		mTextSmall =  findViewById(R.id.small_btn);
		mDownloadClose =  findViewById(R.id.close_btn);
		mDownloadOpen =  findViewById(R.id.open_btn);
		mHelp =  findViewById(R.id.help);
		mCheckUpdate =  findViewById(R.id.check_update);
		mAbout =  findViewById(R.id.about);
	}
}