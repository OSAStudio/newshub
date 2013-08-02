package com.osastudio.newshub;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.huadi.azker_phone.R;
import com.osastudio.newshub.data.RecommendedTopic;
import com.osastudio.newshub.data.RecommendedTopicList;
import com.osastudio.newshub.net.RecommendApi;
import com.osastudio.newshub.utils.Utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class AzkerListActivity extends NewsBaseActivity {

	final static public String LIST_TYPE = "list type";
	final static public String LIST_TITLE = "list title";

	final static public int RECOMMEND_LIST_TYPE = 1;
	final static public int EXPERT_LIST_TYPE = 2;
	final static public int USER_LSSUES_TYPE = 3;

	final static public int NOTIFY_LIST_TYPE = 4;
	final static public int DAILY_REMINDER_TYPE = 5;

	private int mListType = -1;
	private String mTitle = null;

	private TextView mTitleView = null;
	private ListView mListView = null;

	private LayoutInflater mInflater = null;

	private ArrayList<ListData> mListDatas = null;
	
	private BaseAdapter mAdapter = null;
	private LoadTask mLoadTask = null;
	private LoadBitmapTask mLoadBitmapTask = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		mInflater = LayoutInflater.from(this);
		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			mListType = extras.getInt(LIST_TYPE);
			mTitle = extras.getString(LIST_TITLE);
			findViews();
			mLoadTask = new LoadTask();
			mLoadTask.execute();
		}
	}
	
	@Override
	protected void onDestroy() {
		
		if (mLoadTask != null) {
			mLoadTask.cancel(true);
		}
		if (mLoadBitmapTask != null) {
			mLoadBitmapTask.cancel(true);
		}
		if (mListDatas != null) {
			for (int i = 0; i < mListDatas.size(); i++) {
				ListData data = mListDatas.get(i);
				if (data.mBitmap != null && !data.mBitmap.isRecycled()) {
					data.mBitmap.recycle();
					data.mBitmap = null;
				}
			}
			mListDatas.clear();
		}
		super.onDestroy();
	}

	private void findViews() {
		mTitleView = (TextView) findViewById(R.id.title_text);
		if (mTitle != null) {
			mTitleView.setText(mTitle);
		}
		mListView = (ListView) findViewById(R.id.list);
	}

	private class LoadTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			boolean rtn = false;
			switch (mListType) {
			case RECOMMEND_LIST_TYPE:
				rtn = getRecommendListData();
				break;
			case EXPERT_LIST_TYPE:
				break;
			case USER_LSSUES_TYPE:
				break;

			case NOTIFY_LIST_TYPE:
				break;
			case DAILY_REMINDER_TYPE:
				break;
			}
			return rtn;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			mLoadTask = null;
			if (result) {
				switch (mListType) {
				case RECOMMEND_LIST_TYPE:
					mAdapter = new IconTextAdapter();
					mListView.setAdapter(mAdapter);
					mLoadBitmapTask = new LoadBitmapTask();
					mLoadBitmapTask.execute();
					break;
				case EXPERT_LIST_TYPE:
					break;
				case USER_LSSUES_TYPE:
					break;

				case NOTIFY_LIST_TYPE:
					break;
				case DAILY_REMINDER_TYPE:
					break;
				}

			}
			super.onPostExecute(result);
		}

	}
	
	private class LoadBitmapTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			if (mListDatas != null) {
				for (int i = 0; i < mListDatas.size(); i++) {
					ListData data = mListDatas.get(i);
					if (data.mIconUrl != null && 
							(data.mBitmap == null || data.mBitmap.isRecycled())) {
						data.mBitmap = Utils.getBitmapFromUrl(data.mIconUrl);
						if (data.mBitmap != null) {
							publishProgress();
						}
					}
				}
				
			}
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Void... values) {
			mAdapter.notifyDataSetChanged();
			super.onProgressUpdate(values);
		}
		
		@Override
		protected void onPostExecute(Void result) {
			mLoadBitmapTask = null;
			super.onPostExecute(result);
		}
		
	}

	private boolean getRecommendListData() {
		if (mListDatas != null) {
			mListDatas.clear();
			mListDatas = null;
		}
		String userId = ((NewsApp) getApplication()).getCurrentUserId();
		if (userId != null) {
			RecommendedTopicList list = RecommendApi.getRecommendedTopicList(
					this, userId);
			if (list != null) {
				List<RecommendedTopic> recommendList = list.getList();
				if (recommendList != null && recommendList.size() > 0) {
					if (mListDatas == null) {
						mListDatas = new ArrayList<ListData>();
					}
					for (int i = 0; i < recommendList.size(); i++) {
						RecommendedTopic topic = recommendList.get(i);
						mListDatas.add(new ListData(topic.getId(), topic
								.getTitle(), topic.getIconUrl()));
					}
					return true;
				}
			}
		}
		return false;
	}

	private class IconTextAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mListDatas.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mListDatas.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LinearLayout layout = (LinearLayout) convertView;
			if (layout == null) {
				layout = (LinearLayout) mInflater.inflate(
						R.layout.icontext_item, null);
			}
			ListData data = mListDatas.get(position);
			if (data != null) {
				ImageView icon = (ImageView) layout.findViewById(R.id.icon);
				TextView title = (TextView) layout.findViewById(R.id.title);
				TextView subtitle = (TextView) layout
						.findViewById(R.id.sub_title);

				if (data.mBitmap != null && !data.mBitmap.isRecycled()) {
					icon.setImageBitmap(data.mBitmap);
				}
				if (data.mTitle != null) {
					title.setText(data.mTitle);
				}
				if (data.mSubTitle != null) {
					subtitle.setText(data.mSubTitle);
					subtitle.setVisibility(View.VISIBLE);
				} else {
					subtitle.setVisibility(View.GONE);
				}
				return layout;
			} else {
				return null;
			}
		}

	}

	private class ListData {
		public ListData(String id, String title, String iconUrl) {
			mId = id;
			mTitle = title;
			mIconUrl = iconUrl;
		}
		public ListData(String id, String title, String iconUrl, String subTitle) {
			mId = id;
			mTitle = title;
			mIconUrl = iconUrl;
			mSubTitle = subTitle;
		}

		String mId = "";
		String mTitle = "";
		String mIconUrl;
		String mSubTitle = null;
		Bitmap mBitmap = null;
	}
}