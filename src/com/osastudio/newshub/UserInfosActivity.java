package com.osastudio.newshub;

import java.util.ArrayList;

import com.huadi.azker_phone.R;
import com.osastudio.newshub.data.user.User;
import com.osastudio.newshub.data.user.UserList;
import com.osastudio.newshub.net.UserApi;
import com.osastudio.newshub.utils.Utils;
import com.osastudio.newshub.widgets.UserInfoView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class UserInfosActivity extends NewsBaseActivity {

	private static final int[] USER_VIEW_ID = { R.id.user0, R.id.user1,
			R.id.user2, R.id.user3 };

	private ArrayList<User> mUserList = null;

	private UserInfoView mUserInfoView = null;
	private TextView mUser0 = null;
	private TextView mUser1 = null;
	private TextView mUser2 = null;
	private TextView mUser3 = null;
	private View mConfirmBtn = null;

	private ProgressDialog mPDlg = null;
	private LoadTask mTask = null;

	private int mCurrentUserIndex = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_userinfos);
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
		mUserInfoView = (UserInfoView) findViewById(R.id.userInfo);
		mUser0 = (TextView) findViewById(R.id.user0);
		mUser0.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setUserInfo(0);
				
			}
		});
		mUser1 = (TextView) findViewById(R.id.user1);
		mUser1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setUserInfo(1);
				
			}
		});
		mUser2 = (TextView) findViewById(R.id.user2);
		mUser2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setUserInfo(2);
				
			}
		});
		mUser3 = (TextView) findViewById(R.id.user3);
		mUser3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setUserInfo(3);
				
			}
		});
	}

	private void setupViews() {
		String userId = ((NewsApp) getApplication()).getCurrentUserId();
		for (int i = 0; i < mUserList.size(); i++) {
			User user = mUserList.get(i);
			if (user.getUserId() != null && user.getUserId().equals(userId)) {
				mCurrentUserIndex = i;
			}
			((TextView)findViewById(USER_VIEW_ID[i])).setText(user.getUserName());
		}
		setUserInfo(mCurrentUserIndex);
	}

	private void setUserInfo(int index) {
		if (index >= mUserList.size()) {
			return;
		}
		mCurrentUserIndex = index;
		if (mCurrentUserIndex >= 4 || mCurrentUserIndex < 0) {
			mCurrentUserIndex = 0;
		}
		for (int i = 0; i < 4; i++) {
			if (i == mCurrentUserIndex) {
				findViewById(USER_VIEW_ID[i]).setBackgroundColor(
						getResources().getColor(R.color.holo_blue));
			} else {
				findViewById(USER_VIEW_ID[i]).setBackgroundColor(
						getResources().getColor(R.color.bg_blue));
			}
		}
		User user = mUserList.get(mCurrentUserIndex);
		mUserInfoView.setUserInfo(user.getUserName(), user.getGender(),
				user.getBirthday(), user.getSchoolName(), user.getYearName(),
				user.getClassName(), user.getServiceExpiredTime());

	}

	private class LoadTask extends AsyncTask<Void, Void, Void> {

		protected Void doInBackground(Void... params) {
			UserList userList = UserApi.getUserList(UserInfosActivity.this);
			mUserList = (ArrayList<User>) userList.getList();
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