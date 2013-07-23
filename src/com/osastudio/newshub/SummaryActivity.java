package com.osastudio.newshub;

import java.util.ArrayList;

import com.osastudio.newshub.data.NewsChannel;
import com.osastudio.newshub.widgets.BaseAssistent;
import com.osastudio.newshub.widgets.SlideSwitcher;
import com.osastudio.newshub.widgets.SummaryGrid;

import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SummaryActivity extends Activity {
	public static final String CATEGORY_DATA = "Category_data";
	private NewsChannel mCategoryData = null;
	private SlideSwitcher mSwitcher = null;
	private ArrayList<SummaryData> mSummaries = new ArrayList<SummaryData>();
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
			mCategoryData =  extras.getParcelable(CATEGORY_DATA);
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
		return false;
	}

	private class LoadDataTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			for (int i = 0; i < 20; i++) {
				SummaryData summary = new SummaryData();
				summary.lesson_id = i;

				summary.lesson_title = "title "+i;
				summary.lesson_colour = 0xFFFAF0E6;
				summary.post_date = "07月15日";
				summary.expert_name = "孔教授";
				mSummaries.add(summary);
			}
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
				grid_layout = new SummaryGrid(SummaryActivity.this);
			}
			setupGridLayout(grid_layout, position);

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
					LayoutInflater inflater = LayoutInflater.from(SummaryActivity.this);
					summary = inflater.inflate(R.layout.summary_item, null);
				}
				SummaryData data = mSummaries.get(index);
//				View base = summary.findViewById(R.id.base);
//				base.setBackgroundColor(data.title_color);
				TextView tv = (TextView)summary.findViewById(R.id.title);
				tv.setText(data.lesson_title);

				TextView name = (TextView)summary.findViewById(R.id.expert_name);
				name.setText(data.expert_name);
				return summary;
			} else {
				return null;
			}
		}

	}
	
	public class SummaryData {
		int lesson_id;//�γ�ID��Ψһ����񰴴�ID������ʾ��
		String lesson_title;//�γ̱���
		int lesson_colour;//��ɫ
		String post_date;//��������
		String expert_name;//����ר��
	}


}
