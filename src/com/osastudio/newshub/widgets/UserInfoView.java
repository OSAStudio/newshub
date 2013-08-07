package com.osastudio.newshub.widgets;

import com.huadi.azker_phone.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserInfoView extends LinearLayout {
	private Context mContext = null;
	private TextView mName = null;
	private TextView mSex = null;
	private TextView mBirth = null;
	private TextView mSchool = null;
	private TextView mGrade = null;
	private TextView mClass = null;
	private View mExpirydate_layout = null;
	private TextView mExpirydate = null;

	public UserInfoView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public UserInfoView(Context context, AttributeSet attrs) {
		super(context, attrs);

		mContext = context;
		LayoutInflater inflater = LayoutInflater.from(mContext);
		inflater.inflate(R.layout.userinfoview, this);
		findViews();
	}

	private void findViews() {
		mName = (TextView) findViewById(R.id.name_text);
		mSex = (TextView) findViewById(R.id.sex_text);
		mBirth = (TextView) findViewById(R.id.birth_text);
		mSchool = (TextView) findViewById(R.id.school_text);
		mGrade = (TextView) findViewById(R.id.grade_text);
		mClass = (TextView) findViewById(R.id.class_text);
		mExpirydate = (TextView) findViewById(R.id.expiry_date_text);
		mExpirydate_layout = findViewById(R.id.expiry_date);
	}

	public void setUserInfo(String name, String sex, String birth,
			String school, String grade, String clas, String expirty_date) {
		mName.setText(name);
		mSex.setText(name);
		mBirth.setText(name);
		mSchool.setText(name);
		mGrade.setText(name);
		mClass.setText(name);
		if (expirty_date != null) {
			mExpirydate_layout.setVisibility(View.VISIBLE);
			mExpirydate.setText(name);
		} else {
			mExpirydate_layout.setVisibility(View.GONE);
		}
	}

}