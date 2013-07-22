package com.osastudio.newshub;

import java.util.ArrayList;

import com.osastudio.newshub.widgets.AzkerGridLayout;
import com.osastudio.newshub.widgets.AzkerGridLayout.OnGridItemClickListener;
import com.osastudio.newshub.widgets.BaseAssistent;
import com.osastudio.newshub.widgets.SlideSwitcher;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CategoryActivity extends Activity {
	private SlideSwitcher mSwitcher = null;
	private ArrayList<CategoryData> mCategories = new ArrayList<CategoryData>();
	private int mTouchSlop;
	private int mBaseX, mBaseY;
	private int mDirection = -1; // 0 is preview; 1 is next;
	private int mInitX, mInitY;
	private boolean mbSwitchAble = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_switcher);

		ViewConfiguration configuration = ViewConfiguration.get(this);
		mTouchSlop = configuration.getScaledTouchSlop();
		mSwitcher = (SlideSwitcher) findViewById(R.id.switcher);
		setupData();
	}

	private void setupData() {
		new LoadDataTask().execute();
	}
	
	
	public boolean dispatchTouchEvent(MotionEvent event) {
//		mGd.onTouchEvent(event);
		int y = (int) event.getRawY();
		int x = (int) event.getRawX();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mInitX = x;
			mInitY = y;
			mBaseX = x;
			mBaseY = y;
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
			for (int i = 0; i < 20; i++) {
				CategoryData category = new CategoryData();
				category.title_id = i;
				category.icon_id = i;
				if (i == 0) {
					category.title_class = "֪ͨ";
					category.title_color = 0xFFEE7942;
					category.title_name = "���û���һ����";
				} else if (i == 1) {

					category.title_class = "��";
					category.title_color = 0xFFFAF0E6;
					category.title_name = "��һ��";
				} else {
					category.title_class = "����";
					category.title_color = 0xFFC6E2FF;
					category.title_name = "���� " + i;

				}
				category.icon_url = null;
				category.service_id = 1;
				mCategories.add(category);
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

	private void setupGridLayout(AzkerGridLayout grid_layout, int page) {
		grid_layout.setAssistant(new GridLayoutAssistent(page));
	}

	private class SwitchAssistent extends BaseAssistent {

		@Override
		public int getCount() {
			if (mCategories.size() % 8 == 0) {
				return mCategories.size() / 8;
			} else {
				return mCategories.size() / 8 + 1;
			}
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public View getView(int position, View convertView) {
			AzkerGridLayout grid_layout = (AzkerGridLayout) convertView;
			if (grid_layout == null) {
				grid_layout = new AzkerGridLayout(CategoryActivity.this);
			}
			
			setupGridLayout(grid_layout, position);

			grid_layout.setGridItemClickListener(new GridItemClickListener());
			return grid_layout;

		}

	}
	
	
	private class GridItemClickListener implements OnGridItemClickListener {

		@Override
		public void onClick(int position, View v) {
			startSummaryActivity();
			
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
			if ((mPage+1) * 8 <= mCategories.size()) {
				count = 8;
			} else {
				count = 8 - ((mPage+1) * 8 - mCategories.size());
			}
			return count;
		}

		@Override
		public Object getItem(int position) {
			int index = mPage * 8 + position;
			if (index < mCategories.size()) {
				return mCategories.get(index);
			} else {
				return null;
			}
		}

		@Override
		public View getView(int position, View convertView) {
			int index = mPage * 8 + position;
			if (index < mCategories.size()) {
				RelativeLayout category = (RelativeLayout)convertView;
				if (category == null) {
					LayoutInflater inflater = LayoutInflater.from(CategoryActivity.this);
					category = (RelativeLayout)inflater.inflate(R.layout.category_item, null);
				}
				CategoryData data = mCategories.get(index);
				View base = category.findViewById(R.id.base);
				base.setBackgroundColor(data.title_color);
				ImageView iv = (ImageView)category.findViewById(R.id.image);
				TextView tv = (TextView)category.findViewById(R.id.name);
				tv.setText(data.title_name);
				return category;
			} else {
				return null;
			}
		}

	}
	
	private void startSummaryActivity() {
		 Intent it = new Intent(this, SummaryActivity.class);
	        it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  //Add for PresenterWidget. dwy.
	        
	        startActivityForResult(it,1);
	}

	public class CategoryData {
		int title_id;
		String title_class;
		String title_name;
		int title_color;
		int icon_id;
		String icon_url;
		int service_id;
	}

}
