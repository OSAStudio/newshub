package com.osastudio.newshub;

import com.huadi.azker_phone.R;

import android.os.AsyncTask;
import android.os.Bundle;

public class UserInfosActivity extends NewsBaseActivity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_userinfos);
	}
	
	private class LoadTask extends AsyncTask<Void, Void, Void> {

		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
}