package com.osastudio.newshub;

import java.util.ArrayList;
import java.util.List;

import com.huadi.azker_phone.R;
import com.osastudio.newshub.data.NewsAbstract;
import com.osastudio.newshub.data.NewsAbstractList;
import com.osastudio.newshub.data.NewsChannel;
import com.osastudio.newshub.data.NewsChannelList;
import com.osastudio.newshub.data.SubscriptionTopic;
import com.osastudio.newshub.data.base.NewsBaseAbstract;
import com.osastudio.newshub.data.base.NewsItemList;
import com.osastudio.newshub.net.NewsAbstractApi;
import com.osastudio.newshub.net.NewsChannelApi;
import com.osastudio.newshub.widgets.BaseAssistent;
import com.osastudio.newshub.widgets.SlideSwitcher;
import com.osastudio.newshub.widgets.SummaryGrid;
import com.osastudio.newshub.widgets.SummaryGrid.OnGridItemClickListener;

import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class OldSummaryActivity extends NewsBaseActivity {
	public static final String CHANNEL_ID = "Channel_id";
	public static final String CHANNEL_TITLE = "Channel_title";
	
	private String mChannelId = null;
	private String mChannelTitle = null;
	private SlideSwitcher mSwitcher = null;
	private ArrayList<NewsAbstract> mSummaries = new ArrayList<NewsAbstract>();
	private int mTouchSlop;
	private int mDirection = -1; // 0 is preview; 1 is next;
	private int mInitX, mInitY;
	private boolean mbSwitchAble = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_switcher);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			mChannelId = extras.getString(CHANNEL_ID);
			mChannelTitle = extras.getString(CHANNEL_TITLE);
		}

		ViewConfiguration configuration = ViewConfiguration.get(this);
		mTouchSlop = configuration.getScaledTouchSlop();
		mSwitcher = (SlideSwitcher) findViewById(R.id.switcher);
		setupData();
	}

	private void setupData() {
		new LoadDataTask().execute();
	}
	
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
//		mGd.onTouchEvent(event);
		int y = (int) event.getRawY();
		int x = (int) event.getRawX();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mInitX = x;
			mInitY = y;
			mDirection = -1;
			mbSwitchAble = true;
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			if (mbSwitchAble) {
				if (Math.abs(mInitX - x) > mTouchSlop
						&& Math.abs(mInitX - x) > Math.abs(mInitY - y)) {
					if (mInitX > x) {
						mDirection = 1;
					} else {
						mDirection = 0;
					}
					mSwitcher.SwitcherOnScroll(mDirection);
					break;
				}
			}
			break;
		}
		return super.dispatchTouchEvent(event);
	}

	private class LoadDataTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			
			NewsAbstractList summary_list = NewsAbstractApi.getNewsAbstractList(getApplicationContext(), mChannelId);
			mSummaries = (ArrayList<NewsAbstract>)summary_list.getAbstractList();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			SwitchAssistent assistent = new SwitchAssistent();
			mSwitcher.setAssistant(assistent);
			super.onPostExecute(result);
		}

	}

	private void setupGridLayout(SummaryGrid grid_layout, int page) {
		grid_layout.setAssistant(new GridLayoutAssistent(page));
	}
	
	private void startFileActivity(int index) {
		 Intent it = new Intent(this, FileActivity.class);
         it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
         it.putExtra(FileActivity.START_INDEX, index);
         it.putExtra(FileActivity.CATEGORY_TITLE, mChannelTitle);
         startActivityForResult(it,1);
	}

	private class SummaryItemClickListener implements OnGridItemClickListener {
		@Override
		public void onClick(int position, View v) {
			int page = mSwitcher.getCurrentIndex();
			int index = page * 6 + position;
			if (index < mSummaries.size()) {
				startFileActivity(index);
			}
			
		}
		
	}
	
	private class SwitchAssistent extends BaseAssistent {

		@Override
		public int getCount() {
			if (mSummaries.size() % 6 == 0) {
				return mSummaries.size() / 6;
			} else {
				return mSummaries.size() / 6 + 1;
			}
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public View getView(int position, View convertView) {
			SummaryGrid grid_layout = (SummaryGrid) convertView;
			if (grid_layout == null) {
				grid_layout = new SummaryGrid(OldSummaryActivity.this);
			}
			setupGridLayout(grid_layout, position);
			grid_layout.setGridItemClickListener(new SummaryItemClickListener());
			return grid_layout;

		}

	}

	private class GridLayoutAssistent extends BaseAssistent {
		int mPage = 0;
		public GridLayoutAssistent(int thispage) {
			mPage = thispage;
		}
		@Override
		public int getCount() {
			int count = 0;
			if ((mPage+1) * 6 <= mSummaries.size()) {
				count = 6;
			} else {
				count = 6 - ((mPage+1) * 6 - mSummaries.size());
			}
			return count;
		}

		@Override
		public Object getItem(int position) {
			int index = mPage * 6 + position;
			if (index < mSummaries.size()) {
				return mSummaries.get(index);
			} else {
				return null;
			}
		}

		@Override
		public View getView(int position, View convertView) {
			int index = mPage * 6 + position;
			if (index < mSummaries.size()) {
				View summary = convertView;
				if (summary == null) {
					LayoutInflater inflater = LayoutInflater.from(OldSummaryActivity.this);
					summary = inflater.inflate(R.layout.summary_item, null);
				}
				NewsAbstract data = mSummaries.get(index);
//				View base = summary.findViewById(R.id.base);
//				base.setBackgroundColor(data.title_color);
				TextView tv = (TextView)summary.findViewById(R.id.title);
				tv.setText(data.getTitle());

				TextView name = (TextView)summary.findViewById(R.id.expert_name);
				name.setText(data.getAuthor());
				summary.setBackgroundColor(data.getColor());
				return summary;
			} else {
				return null;
			}
		}

	}
	



}
