package com.osastudio.newshub;

import com.osastudio.newshub.data.NewsAbstract;
import com.osastudio.newshub.data.NewsArticle;
import com.osastudio.newshub.data.NewsChannel;
import com.osastudio.newshub.net.NewsArticleApi;
import com.osastudio.newshub.widgets.BaseAssistent;
import com.osastudio.newshub.widgets.FileView;
import com.osastudio.newshub.widgets.SlideSwitcher;
import com.osastudio.newshub.widgets.SummaryGrid;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

public class FileActivity extends Activity {
	public static final String SUMMARY_DATA = "Summary_data";
	
	public static final String FILE_SIZE = "File_size";
	private NewsAbstract mSummary_data = null;
	private NewsChannel mCategoryData = null;
	private int mFileSize = 1;
	private SlideSwitcher mSwitcher = null;
//	private ArrayList<SummaryData> mSummaries = new ArrayList<SummaryData>();
	private int mTouchSlop;
	private int mDirection = -1; // 0 is preview; 1 is next;
	private int mInitX, mInitY;
	private boolean mbSwitchAble = true;
	
	private NewsArticle mNewsArticle = null;
	private String mHtmlCotent = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_switcher);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
//			mSummary_data =  extras.getParcelable(SUMMARY_DATA);
//			mFileSize = extras.getInt(FILE_SIZE);
//			mCategoryData = extras.getParcelable(SummaryActivity.CATEGORY_DATA);
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
//			NewsAbstractList summary_list = NewsAbstractApi.getNewsAbstractList(getApplicationContext(), mCategoryData.getChannelId());
//			mSummaries = (ArrayList<NewsAbstract>)summary_list.getAbstractList();
			mNewsArticle = NewsArticleApi.getNewsArticle(FileActivity.this, "1");//mSummary_data.getArticleId());
			mHtmlCotent = mNewsArticle.getContent();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			SwitchAssistent assistent = new SwitchAssistent();
			mSwitcher.setAssistant(assistent);
			super.onPostExecute(result);
		}

	}


	private class SwitchAssistent extends BaseAssistent {

		@Override
		public int getCount() {
			return 1;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public View getView(int position, View convertView) {
			FileView fileview = (FileView) convertView;
			if (fileview == null) {
				fileview = new FileView(FileActivity.this);
			}
			fileview.setData(mHtmlCotent);
			return fileview;

		}

	}



}
