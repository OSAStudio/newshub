package com.osastudio.newshub;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;

import com.osastudio.newshub.data.NewsChannel;
import com.osastudio.newshub.data.NewsChannelList;
import com.osastudio.newshub.net.NewsChannelApi;
import com.osastudio.newshub.utils.Utils;
import com.osastudio.newshub.widgets.AzkerGridLayout;
import com.osastudio.newshub.widgets.AzkerGridLayout.OnGridItemClickListener;
import com.osastudio.newshub.widgets.BaseAssistent;
import com.osastudio.newshub.widgets.SlideSwitcher;

import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CategoryActivity extends NewsBaseActivity {
	private static final String DEFAULT_SPLASH_FILE = "file:///android_asset/0.jpg";
	private static final String DEFAULT_BACKGROUND_FILE = "file:///android_asset/1.jpg";
	
	private SlideSwitcher mSwitcher = null;
	// private ArrayList<CategoryData> mCategories = new
	// ArrayList<CategoryData>();
	private ArrayList<NewsChannel> mCategoryList = null;
	private int mTouchSlop;
	private int mBaseX, mBaseY;
	private int mDirection = -1; // 0 is preview; 1 is next;
	private int mInitX, mInitY;
	private boolean mbSwitchAble = true;
	private LayoutInflater mInflater = null;
	private int mScreenWidth = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_activity);

//		String serialnum = null;
//
//		try {
//
//			Class<?> c = Class.forName("android.os.SystemProperties");
//
//			Method get = c.getMethod("get", String.class, String.class);
//
//			serialnum = (String) (get.invoke(c, "ro.serialno", "unknown"));
//			Utils.log("pp", serialnum);
//
//			String androidId = Settings.Secure.getString(getContentResolver(),
//
//			Settings.Secure.ANDROID_ID);
//			Utils.log("pp", androidId);
//		}
//
//		catch (Exception ignored)
//
//		{
//
//		}

		ViewConfiguration configuration = ViewConfiguration.get(this);
		mTouchSlop = configuration.getScaledTouchSlop();
		mSwitcher = (SlideSwitcher) findViewById(R.id.switcher);

		mInflater = LayoutInflater.from(this);
		
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		mScreenWidth = display.getWidth();
		mScreenWidth = mScreenWidth > 0 ? mScreenWidth : 0;
		setupData();
	}

	private void setupData() {
		new LoadDataTask().execute();
//        InputStream myInput = null;
//        try {
//			myInput = getAssets().open("0.jpg");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	public boolean dispatchTouchEvent(MotionEvent event) {
		// mGd.onTouchEvent(event);
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
	
	private class BMP_Item {
		String mPath;
		Bitmap mBitmap;
	}
	private class LoadImageTask extends AsyncTask<Void, BMP_Item, Void> {
		

		@Override
		protected Void doInBackground(Void... params) {
			BMP_Item item = new BMP_Item();
			item.mPath = DEFAULT_SPLASH_FILE;
			item.mBitmap = Utils.loadBitmap(DEFAULT_SPLASH_FILE, mScreenWidth, 0, 0);
			if (item.mBitmap != null) {
				publishProgress(item);
			}
			item = new BMP_Item();
			item.mPath = DEFAULT_BACKGROUND_FILE;
			item.mBitmap = Utils.loadBitmap(DEFAULT_SPLASH_FILE, mScreenWidth, 0, 0);
			if (item.mBitmap != null) {
				publishProgress(item);
			}
			return null;
		}
		
		@Override
		protected void onProgressUpdate(BMP_Item... values) {
			BMP_Item item = values[0];
			super.onProgressUpdate(values);
		}


	}

	private class LoadDataTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			NewsChannelList channel_list = NewsChannelApi
					.getNewsChannelList(getApplicationContext());
			if (channel_list != null) {
				mCategoryList = (ArrayList<NewsChannel>) channel_list
						.getChannelList();
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
			if (mCategoryList.size() % 8 == 0) {
				return mCategoryList.size() / 8;
			} else {
				return mCategoryList.size() / 8 + 1;
			}
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public View getView(int position, View convertView) {
//			AzkerGridLayout grid_layout = (AzkerGridLayout) convertView;
//			if (grid_layout == null) {
//				grid_layout = new AzkerGridLayout(CategoryActivity.this);
//			}
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.category_view, null);
			}
			AzkerGridLayout grid_layout = (AzkerGridLayout)convertView.findViewById(R.id.grid);

			setupGridLayout(grid_layout, position);

			grid_layout.setGridItemClickListener(new GridItemClickListener());
			return convertView;

		}

	}

	private class GridItemClickListener implements OnGridItemClickListener {

		@Override
		public void onClick(int position, View v) {
			int page = mSwitcher.getCurrentIndex();
			int index = page * 8 + position;
			if (index < mCategoryList.size()) {
				startSummaryActivity(mCategoryList.get(index));
			}

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
			// if ((mPage+1) * 8 <= mCategories.size()) {
			// count = 8;
			// } else {
			// count = 8 - ((mPage+1) * 8 - mCategories.size());
			// }
			if ((mPage + 1) * 8 <= mCategoryList.size()) {
				count = 8;
			} else {
				count = 8 - ((mPage + 1) * 8 - mCategoryList.size());
			}
			return count;
		}

		@Override
		public Object getItem(int position) {
			int index = mPage * 8 + position;
			// if (index < mCategories.size()) {
			// return mCategories.get(index);
			// } else {
			// return null;
			// }
			if (index < mCategoryList.size()) {
				return mCategoryList.get(index);
			} else {
				return null;
			}
		}

		@Override
		public View getView(int position, View convertView) {
			int index = mPage * 8 + position;
			// if (index < mCategories.size()) {
			if (index < mCategoryList.size()) {
				RelativeLayout category = (RelativeLayout) convertView;
				if (category == null) {
					LayoutInflater inflater = LayoutInflater
							.from(CategoryActivity.this);
					category = (RelativeLayout) inflater.inflate(
							R.layout.category_item, null);
				}
				// CategoryData data = mCategories.get(index);
				// View base = category.findViewById(R.id.base);
				// base.setBackgroundColor(data.title_color);
				// ImageView iv = (ImageView)category.findViewById(R.id.image);
				// TextView tv = (TextView)category.findViewById(R.id.name);
				// tv.setText(data.title_name);

				View base = category.findViewById(R.id.base);
				ImageView iv = (ImageView) category.findViewById(R.id.image);
				TextView tv = (TextView) category.findViewById(R.id.name);

				NewsChannel data = mCategoryList.get(index);
				base.setBackgroundColor(data.getTitleColor());
				tv.setText(data.getTitleName());
				return category;
			} else {
				return null;
			}
		}

	}

	private void startSummaryActivity(NewsChannel data) {
		Intent it = new Intent(this, SummaryActivity.class);
		it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		it.putExtra(SummaryActivity.CATEGORY_DATA, data);
		startActivityForResult(it, 1);
	}

}
