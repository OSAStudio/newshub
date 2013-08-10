package com.osastudio.newshub;

import java.util.ArrayList;

import com.huadi.azker_phone.R;
import com.osastudio.newshub.data.FeedbackType;
import com.osastudio.newshub.data.FeedbackTypeList;
import com.osastudio.newshub.data.NewsResult;
import com.osastudio.newshub.net.FeedbackApi;
import com.osastudio.newshub.utils.Utils;
import com.osastudio.newshub.utils.Utils.DialogConfirmCallback;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class FeedbackActivity extends NewsBaseActivity {
	private Spinner mTypeSpinner = null;
	private EditText mEdit = null;
	private View mButton = null;
	private ArrayList<FeedbackType> mTypeList = null;
	private ProgressDialog mDlg = null;
	private int mTypePosition = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		findViews();
		mDlg = Utils.showProgressDlg(this, getString(R.string.wait));

		new LoadTask().execute();
	}

	private void findViews() {
		mTypeSpinner = (Spinner) findViewById(R.id.type_spinner);
		mEdit = (EditText) findViewById(R.id.content_edit);
		mButton = findViewById(R.id.btn);

		mButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String type = null;
				if (mTypeList != null && mTypeList.size() > mTypePosition) {
					type = mTypeList.get(mTypePosition).getName();
				}
				String cotent = mEdit.getEditableText().toString();
				if (cotent == null || cotent.equals("") || type == null
						|| type.equals("")) {
					Utils.ShowConfirmDialog(FeedbackActivity.this,
							FeedbackActivity.this
									.getString(R.string.empty_alert), null);
				} else {
					if (mTypeList != null && mTypeList.size() > mTypePosition) {
						new CommitTask().execute(mTypeList.get(mTypePosition)
								.getId(), cotent);
					}
				}

			}
		});
	}

	private void setupSpinner() {
		TypeAdapter adapter = new TypeAdapter();
		mTypeSpinner.setAdapter(adapter);
		mTypeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				mTypePosition = position;
			}

			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}

	private class TypeAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mTypeList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mTypeList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View item = convertView;
			if (item == null) {
				item = LayoutInflater.from(FeedbackActivity.this).inflate(
						R.layout.text_item, null);
			}
			TextView tv = (TextView) item.findViewById(R.id.text);
			if (tv != null) {
				tv.setText(mTypeList.get(position).getName());
			}
			return item;

		}

	}

	private class LoadTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			FeedbackTypeList list = FeedbackApi
					.getFeedbackTypeList(getApplicationContext());
			if (list != null) {
				mTypeList = (ArrayList<FeedbackType>) list.getList();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (mTypeList != null) {
				setupSpinner();
			}
			Utils.closeProgressDlg(mDlg);
			mDlg = null;
			super.onPostExecute(result);
		}
	}

	private class CommitTask extends AsyncTask<String, Void, Boolean> {
		NewsResult mResult = null;

		@Override
		protected Boolean doInBackground(String... params) {
			String feedbackTypeId = params[0];
			String feedbackContent = params[1];
			mResult = FeedbackApi.feedback(getApplicationContext(),
					feedbackTypeId, feedbackContent);
			return mResult.isSuccess();
		}

		@Override
		protected void onPostExecute(Boolean bSuccess) {
			if (!bSuccess) {
				int code = mResult.getResultCode();
				String msg = Utils.getErrorResultMsg(FeedbackActivity.this,
						code);
				if (msg != null) {
					Utils.ShowConfirmDialog(FeedbackActivity.this, msg, null);
				}
			} else {
				Utils.ShowConfirmDialog(FeedbackActivity.this,
						FeedbackActivity.this
								.getString(R.string.feedback_success),
						new DialogConfirmCallback() {
							public void onConfirm(DialogInterface dialog) {
								FeedbackActivity.this.finish();

							}
						});
			}
			super.onPostExecute(bSuccess);
		}
	}
}