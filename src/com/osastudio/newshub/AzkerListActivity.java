package com.osastudio.newshub;

import com.huadi.azker_phone.R;

import android.os.Bundle;

public class AzkerListActivity extends NewsBaseActivity {
	
	final static public String LIST_TYPE = "list type";
	
	final static public int RECOMMEND_LIST_TYPE = 1;
	final static public int EXPERT_LIST_TYPE = 2;
	final static public int USER_LSSUES_TYPE = 3;
	
	final static public int NOTIFY_LIST_TYPE = 4;
	final static public int DAILY_REMINDER_TYPE = 5;
	
	private int mListType = -1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			mListType = extras.getInt(LIST_TYPE);
		}
	}
}