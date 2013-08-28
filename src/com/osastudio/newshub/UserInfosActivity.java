package com.osastudio.newshub;

import java.util.ArrayList;

import com.huadi.azker_phone.R;
import com.osastudio.newshub.data.NewsResult;
import com.osastudio.newshub.data.user.User;
import com.osastudio.newshub.data.user.UserList;
import com.osastudio.newshub.library.PreferenceManager.PreferenceFiles;
import com.osastudio.newshub.library.PreferenceManager.PreferenceItems;
import com.osastudio.newshub.net.UserApi;
import com.osastudio.newshub.utils.NewsResultAsyncTask;
import com.osastudio.newshub.utils.Utils;
import com.osastudio.newshub.utils.Utils.DialogConfirmCallback;
import com.osastudio.newshub.widgets.UserInfoView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
		mTask = new LoadTask(this);
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

		mConfirmBtn = findViewById(R.id.confirm_btn);
		mConfirmBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (mUserList != null && mUserList.size() > 0) {
					if (mCurrentUserIndex >= 4 || mCurrentUserIndex < 0 || mCurrentUserIndex >= mUserList.size()) {
						mCurrentUserIndex = 0;
					}
					User user = mUserList.get(mCurrentUserIndex);
					if (user.getServiceExpiredTime() != null) {
			               String msg = UserInfosActivity.this.getString(R.string.msg_userid_no_authority);
			               Utils.ShowConfirmDialog(UserInfosActivity.this, msg, null);
			            
					} else { 
						SharedPreferences prefs = UserInfosActivity.this
								.getSharedPreferences(PreferenceFiles.APP_SETTINGS,
										Context.MODE_PRIVATE);
						if (prefs != null) {
							prefs.edit().putString(
								PreferenceItems.USER_ID,
								mUserList.get(mCurrentUserIndex)
										.getUserId()).commit();
	
						}
	
						UserInfosActivity.this.setResult(RESULT_OK);
						UserInfosActivity.this.finish();
					}
				}
			}
		});
	}

	private void setupViews() {
		if (mUserList == null || mUserList.size() <= 0) {
			return;
		}
		String userId = ((NewsApp) getApplication()).getCurrentUserId();
		for (int i = 0; i < mUserList.size(); i++) {
			if (i >= 4) {
				break;
			}
			User user = mUserList.get(i);
			if (user.getUserId() != null && user.getUserId().equals(userId)) {
				mCurrentUserIndex = i;
			}
			((TextView) findViewById(USER_VIEW_ID[i])).setText(user
					.getUserName());
		}
		setUserInfo(mCurrentUserIndex);
	}

	private void setUserInfo(int index) {
		if (mUserList == null || mUserList.size() <= 0
				|| index >= mUserList.size()) {
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

	private class LoadTask extends NewsResultAsyncTask<Void, Void, NewsResult> {

		public LoadTask(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		protected NewsResult doInBackground(Void... params) {
			UserList userList = UserApi.getUserList(UserInfosActivity.this);
			if (userList != null) {
				mUserList = (ArrayList<User>) userList.getList();
			}
			return userList;
		}

		@Override
		public void onPostExecute(NewsResult result) {
			super.onPostExecute(result);
			if (mPDlg != null) {
				Utils.closeProgressDlg(mPDlg);
				mPDlg = null;
			}
			setupViews();
		}

	}

}