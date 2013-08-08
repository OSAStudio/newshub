package com.osastudio.newshub;

import java.util.ArrayList;

import com.huadi.azker_phone.R;
import com.osastudio.newshub.data.user.User;
import com.osastudio.newshub.data.user.UserInfo;
import com.osastudio.newshub.data.user.UserInfoList;
import com.osastudio.newshub.data.user.UserList;
import com.osastudio.newshub.net.UserApi;
import com.osastudio.newshub.utils.Utils;
import com.osastudio.newshub.widgets.UserInfoView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class UserManagerActivity extends NewsBaseActivity {
	private LinearLayout mLayout = null;

	private ArrayList<UserInfo> mUserInfos = null;
	private ProgressDialog mPDlg = null;
	private LoadTask mTask = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usermanager);
		findViews();
		mPDlg = Utils.showProgressDlg(this, null);
		mTask = new LoadTask();
		mTask.execute();
	}

	@Override
	protected void onDestroy() {
		if (mTask != null) {
			mTask.cancel(true);
		}
		if (mPDlg != null) {
			Utils.closeProgressDlg(mPDlg);
			mPDlg = null;
		}
		super.onDestroy();
	}

	private void findViews() {
		mLayout = (LinearLayout) findViewById(R.id.layout);
	}

	private void setupViews() {
		if (mUserInfos == null || mUserInfos.size() <= 0) {
			finish();
		}

		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		llp.topMargin = 10;
		llp.bottomMargin = 10;
		
		for (int i = 0; i < mUserInfos.size(); i++) {
			UserInfo user = mUserInfos.get(i);
			UserInfoView infoView = new UserInfoView(this);
			infoView.setUserInfo(user.getUserName(), user.getGender(),
					user.getBirthday(), user.getSchoolName(), user.getYearName(),
					user.getClassName(), user.getServiceExpiredTime());
			mLayout.addView(infoView, llp);
		}
		
	}

	private class LoadTask extends AsyncTask<Void, Void, Void> {

		protected Void doInBackground(Void... params) {
			UserInfoList list = UserApi
					.getUserInfoList(UserManagerActivity.this);

			mUserInfos = (ArrayList<UserInfo>) list.getList();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (mPDlg != null) {
				Utils.closeProgressDlg(mPDlg);
				mPDlg = null;
			}
			setupViews();

			super.onPostExecute(result);
		}

	}
}