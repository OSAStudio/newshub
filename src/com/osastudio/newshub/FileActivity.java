package com.osastudio.newshub;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import com.huadi.azker_phone.R;
import com.osastudio.newshub.cache.NewsAbstractCache;
import com.osastudio.newshub.data.NewsAbstract;
import com.osastudio.newshub.data.NewsAbstractList;
import com.osastudio.newshub.data.NewsArticle;
import com.osastudio.newshub.net.NewsArticleApi;
import com.osastudio.newshub.utils.Utils;
import com.osastudio.newshub.widgets.BaseAssistent;
import com.osastudio.newshub.widgets.FileView;
import com.osastudio.newshub.widgets.SlideSwitcher;
import com.osastudio.newshub.widgets.SummaryGrid;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Xml;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

public class FileActivity extends NewsBaseActivity {
	public static final String START_INDEX = "Start_index";
	public static final String CATEGORY_TITLE = "Category_title";

	public int mCurrentId = 0;
	public String mCategoryTitle = null;
	public int mCurrentShowId = -1;

	private NewsAbstract mSummary_data = null;
	private NewsAbstractList mSummary_list = null;
	private int mFileSize = 1;
	private SlideSwitcher mSwitcher = null;
	// private ArrayList<SummaryData> mSummaries = new ArrayList<SummaryData>();
	private int mTouchSlop;
	private int mDirection = -1; // 0 is preview; 1 is next;
	private int mInitX, mInitY;
	private int mBaseX, mBaseY;
	private boolean mbSwitchAble = true;

	private NewsArticle mNewsArticle = null;
	private String mHtmlCotent = null;
	private String mTitle = null;

	private LoadDataTask mTask = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_switcher);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			mCurrentId = extras.getInt(START_INDEX);
			mCategoryTitle = extras.getString(CATEGORY_TITLE);
		}

		ViewConfiguration configuration = ViewConfiguration.get(this);
		mTouchSlop = configuration.getScaledTouchSlop();
		mSwitcher = (SlideSwitcher) findViewById(R.id.switcher);

		setupData();
	}

	private void setupData() {

		NewsAbstractCache cache = getNewsAbstractCache();
		mSummary_list = cache.getNewsAbstractList();

		SwitchAssistent assistent = new SwitchAssistent();
		mSwitcher.setAssistant(assistent, mCurrentId);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		// mGd.onTouchEvent(event);
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
			if (mbSwitchAble) {
				if (Math.abs(mBaseX - x) > mTouchSlop
						&& Math.abs(mBaseX - x) > Math.abs(mBaseY - y)) {
					if (mInitX > x) {
						mDirection = 1;
					} else {
						mDirection = 0;
					}

					int lastIndex = mSwitcher.getCurrentIndex();
					mSwitcher.SwitcherOnScroll(mDirection);
					mLastIndex = lastIndex;
					Utils.logd("FileActivity", "switch scroll " + mDirection);
					mbSwitchAble = false;
					break;
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			
			break;
		}
		mBaseX = x;
		mBaseY = y;
		if (!mbSwitchAble || Math.abs(mBaseX - x) > Math.abs(mBaseY - y)) {
			return true;//super.dispatchTouchEvent(event);
		} else {
			return super.dispatchTouchEvent(event);
		}
	}

	private class LoadDataTask extends AsyncTask<Integer, Void, Integer> {

		@Override
		protected Integer doInBackground(Integer... params) {
			int index = params[0];
			if (index < 0 || index >= mSummary_list.getAbstractList().size()) {
				return -1;
			}

			mSummary_data = mSummary_list.getAbstractList().get(index);
			mNewsArticle = NewsArticleApi.getNewsArticle(FileActivity.this,
					mSummary_data);
			if (mNewsArticle == null) {
				return -1;
			}
			Utils.log("LoadDataTask", "getNewsArticle ok");
			mHtmlCotent = mNewsArticle.getContent();
			mTitle = mNewsArticle.getTitle();
			AddTitleToHtml();
			return index;
		}

		@Override
		protected void onPostExecute(Integer index) {
			if (index >= 0) {
				mCurrentShowId = index;
				SwitchAssistent assistent = new SwitchAssistent();
				mSwitcher.setAssistant(assistent);
				Utils.log("LoadDataTask", "update switch");
			} else {

			}
			mTask = null;
			super.onPostExecute(index);
		}

	}

	private void AddTitleToHtml() {
		if (mTitle == null) {
			return;
		}
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		String xmlString = null;

		try {
			serializer.setOutput(writer);
			serializer.startTag("", "h2");
			serializer.text(mTitle);
			serializer.endTag("", "h2");

			serializer.startTag("", "hr");
			serializer.endTag("", "hr");
			serializer.flush();
			xmlString = writer.toString();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mHtmlCotent = xmlString + mHtmlCotent;
	}

	private class SwitchAssistent extends BaseAssistent {

		@Override
		public int getCount() {
			return mSummary_list.getAbstractList().size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public View getView(int position, View convertView) {
//			FileView fileview = (FileView) convertView;
//			if (fileview == null) {
//				fileview = new FileView(FileActivity.this);
//			}
//			fileview.setData(mHtmlCotent);
//			return fileview;
			
			mCurrentId = position;
			Utils.log("getView", "mLastIndex="+mLastIndex+" mCurrentId="+position+" convertView="+convertView);
			if (mLastIndex == position && convertView != null) {
				Utils.log("getView", " last data");
				((FileView) convertView).displayTop();
				return convertView;
			}else if (mCurrentId == mCurrentShowId) {
				FileView fileview = (FileView) convertView;
				if (fileview == null) {
					fileview = new FileView(FileActivity.this);
				}
				fileview.setData(mHtmlCotent);
				Utils.log("getView", " real data");
				return fileview;
			} else {
				if (mTask != null) {
					mTask.cancel(true);
					mTask = null;
				}
				mTask = new LoadDataTask();
				mTask.execute(position);
				Utils.log("getView", " no data");
				return null;
			}

		}
	}
	
	private int mLastIndex = -1;

}
