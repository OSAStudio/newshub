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
import com.osastudio.newshub.utils.NetworkHelper;
import com.osastudio.newshub.utils.Utils;
import com.osastudio.newshub.widgets.BaseAssistent;
import com.osastudio.newshub.widgets.FileView;
import com.osastudio.newshub.widgets.SlideSwitcher;
import com.osastudio.newshub.widgets.SummaryGrid;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Xml;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FileActivity extends NewsBaseActivity {
	public static final String DIRECT_ENTER = "direct_enter";
	public static final String START_INDEX = "Start_index";
	public static final String CATEGORY_TITLE = "Category_title";
	public static final String PAGE_ID = "page_id";

	public int mCurrentId = 0;
	public String mCategoryTitle = null;
	public String mPageId = null;
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
	private String mArticleId = null;

	private LoadDataTask mTask = null;

	private int mTextSize = 18;
	private boolean mIsWIFI = true;
	private boolean mDirectEnter = false;
	private boolean mIsDisplayTop = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_switcher);
		mIsWIFI = NetworkHelper.isWifiEnabled(this);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			mCurrentId = extras.getInt(START_INDEX);
			mCategoryTitle = extras.getString(CATEGORY_TITLE);
			if (mCategoryTitle == null) {
				mCategoryTitle = getString(R.string.default_file_title);
			}
			if (mCurrentId < 0) {
				mDirectEnter = extras.getBoolean(DIRECT_ENTER, false);
				mPageId = extras.getString(PAGE_ID);
				// RuJin Add to set NewsAbstractCache, only have 1 item
				mCurrentId = 0;
			}
		}

		ViewConfiguration configuration = ViewConfiguration.get(this);
		mTouchSlop = configuration.getScaledTouchSlop();
		mSwitcher = (SlideSwitcher) findViewById(R.id.switcher);

		setupData();

	}
	
	@Override
	protected void onNewIntent(Intent intent) {
	   super.onNewIntent(intent);
	   Bundle extras = getIntent().getExtras();
	   if (extras != null) {
         mCurrentId = extras.getInt(START_INDEX);
         mCategoryTitle = extras.getString(CATEGORY_TITLE);
         if (mCategoryTitle == null) {
            mCategoryTitle = getString(R.string.default_file_title);
         }
         if (mCurrentId < 0) {
            mDirectEnter = extras.getBoolean(DIRECT_ENTER, false);
            mPageId = extras.getString(PAGE_ID);
            // RuJin Add to set NewsAbstractCache, only have 1 item
            mCurrentId = 0;
         }
      }
	   setupData();

	}


	@Override
	protected void onResume() {
		mTextSize = getPrefsManager().getFontSize();
		mIsWIFI = NetworkHelper.isWifiEnabled(this);
		super.onResume();
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
			mIsDisplayTop = false;
			View content = mSwitcher.getCurrentView();
			if (content != null && content instanceof RelativeLayout
					&& ((RelativeLayout) content).getChildCount() > 0) {
				View child = ((RelativeLayout) mSwitcher.getCurrentView())
						.getChildAt(0);
				if (child instanceof FileView) {
					mIsDisplayTop = ((FileView)child).isDisplayTop();
				}
			}
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
			if (mIsDisplayTop && y - mBaseY > mTouchSlop
					&& Math.abs(mInitX - x) < Math.abs(mInitY - y)) {
				onBackPressed();
				mbSwitchAble = false;
			}
			break;
		case MotionEvent.ACTION_UP:

			break;
		}
		mBaseX = x;
		mBaseY = y;
		if (!mbSwitchAble || Math.abs(mBaseX - x) > Math.abs(mBaseY - y)) {
			return true;// super.dispatchTouchEvent(event);
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
					mSummary_data.getId());
			if (mNewsArticle == null) {
				return -1;
			}
			Utils.log("LoadDataTask", "getNewsArticle ok");
			mHtmlCotent = mNewsArticle.getContent();
			mTitle = mNewsArticle.getTitle();
			mArticleId = mNewsArticle.getId();
			
			String channel = mNewsArticle.getChannelName();
			if (channel != null) {
			   mCategoryTitle = channel;
			}
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
			if (convertView instanceof ProgressBar) {
				convertView = null;
			}
			mCurrentId = position;
			Utils.log("getView", "mLastIndex=" + mLastIndex + " mCurrentId="
					+ position + " convertView=" + convertView);
			if (mLastIndex == position && convertView != null) {
				Utils.log("getView", " last data");
				((FileView) convertView).displayTop();
				return convertView;
			} else if (mCurrentId == mCurrentShowId) {
				FileView fileview = (FileView) convertView;
				if (fileview == null) {
					fileview = new FileView(FileActivity.this);
				}
				TextView title = (TextView) fileview.findViewById(R.id.title);
				if (title != null && mCategoryTitle != null) {
					title.setText(mCategoryTitle);
				}
				fileview.setData(Utils.LESSON_LIST_TYPE, mHtmlCotent,
						mTextSize, mArticleId, mIsWIFI);
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
				return createPogress();
			}

		}
	}

	private View createPogress() {
		ProgressBar bar = new ProgressBar(this);
		bar.setMax(100);
		bar.setProgress(50);

		return bar;

	}

	private int mLastIndex = -1;

}
