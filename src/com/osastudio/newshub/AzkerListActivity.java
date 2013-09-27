package com.osastudio.newshub;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.huadi.azker_phone.R;
import com.huadi.azker_phone.R.color;
import com.osastudio.newshub.NewsApp.TempCacheData;
import com.osastudio.newshub.data.DailyReminder;
import com.osastudio.newshub.data.DailyReminderList;
import com.osastudio.newshub.data.NewsColumnist;
import com.osastudio.newshub.data.NewsColumnistList;
import com.osastudio.newshub.data.NewsNotice;
import com.osastudio.newshub.data.NewsNoticeList;
import com.osastudio.newshub.data.NewsResult;
import com.osastudio.newshub.data.RecommendedTopic;
import com.osastudio.newshub.data.RecommendedTopicList;
import com.osastudio.newshub.data.SubscriptionTopic;
import com.osastudio.newshub.data.SubscriptionTopicList;
import com.osastudio.newshub.net.DailyReminderApi;
import com.osastudio.newshub.net.NewsColumnistApi;
import com.osastudio.newshub.net.NewsNoticeApi;
import com.osastudio.newshub.net.RecommendApi;
import com.osastudio.newshub.net.SubscriptionApi;
import com.osastudio.newshub.utils.NewsResultAsyncTask;
import com.osastudio.newshub.utils.Utils;
import com.osastudio.newshub.utils.Utils.DialogConfirmCallback;
import com.osastudio.newshub.widgets.FileView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AzkerListActivity extends NewsBaseActivity {

	public static final String DIRECT_ENTER="direct_enter";
	final static public String LIST_TYPE = "list type";
	final static public String LIST_TITLE = "list title";
	final static public int RECOMMEND_REQUEST_CODE = 50;
	
	private NewsApp mApp = null;
	private int mListType = -1;
	private String mTitle = null;

	private TextView mTitleView = null;
	private ListView mListView = null;

	private LayoutInflater mInflater = null;

	private List<RecommendedTopic> mRecommendList = null;
	private List<NewsColumnist> mExpertList = null;
	private List<SubscriptionTopic> mUserIssuesList = null;
	private List<NewsNotice> mNoticeList = null;

	private ArrayList<ListData> mListDatas = null;

	private BaseAdapter mAdapter = null;
	private LoadTask mLoadTask = null;
	private LoadBitmapTask mLoadBitmapTask = null;

	private ProgressDialog mDlg = null;
	private boolean mDirectEnter = false;

	private int mTouchSlop;
	private int mInitX, mInitY;
	private int mBaseX, mBaseY;
	private boolean mIsDisplayTop = false;
	private boolean mIsScrollable = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		mApp = (NewsApp)getApplication();
		mInflater = LayoutInflater.from(this);
		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			mDirectEnter = extras.getBoolean(DIRECT_ENTER, false);
			mListType = extras.getInt(LIST_TYPE);
			mTitle = extras.getString(LIST_TITLE);
			findViews();
			mLoadTask = new LoadTask(this);
			mLoadTask.execute();
			mDlg = Utils.showProgressDlg(this, null);
		}
		

		ViewConfiguration configuration = ViewConfiguration.get(this);
		mTouchSlop = configuration.getScaledTouchSlop();
	}
	
	
	@Override
	protected void onNewIntent(Intent intent) {
	   super.onNewIntent(intent);
	   Bundle extras = intent.getExtras();

      if (extras != null) {
         mDirectEnter = extras.getBoolean(DIRECT_ENTER, false);
         mListType = extras.getInt(LIST_TYPE);
         mTitle = extras.getString(LIST_TITLE);
         findViews();
         mLoadTask = new LoadTask(this);
         mLoadTask.execute();
         mDlg = Utils.showProgressDlg(this, null);
      }
	}
	

	@Override
	protected void onDestroy() {
		if (mDlg != null) {
			Utils.closeProgressDlg(mDlg);
			mDlg = null;
		}
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
			mTitleView.setText(Html.fromHtml(mTitle));
		}
		mListView = (ListView) findViewById(R.id.list);
		mListView.setOnItemClickListener(new ItemClickListener());
	}
	
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if (mListView == null) {
			return super.dispatchTouchEvent(event);
		}
		// mGd.onTouchEvent(event);
		int y = (int) event.getRawY();
		int x = (int) event.getRawX();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mInitX = x;
			mInitY = y;
			mIsDisplayTop = false;
			if (mListView.getFirstVisiblePosition() <= 0) {
			   View v = mListView.getChildAt(0);
			   int t = v.getTop();
			   if (t >= 0) {
			      mIsDisplayTop = true;
			   }
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (mIsDisplayTop && y - mBaseY > mTouchSlop
					&& Math.abs(mInitX - x) < Math.abs(mInitY - y)) {
				onBackPressed();
				mIsScrollable = false;
			}
			break;
		case MotionEvent.ACTION_UP:

			break;
		}
		mBaseX = x;
		mBaseY = y;
		if (!mIsScrollable) {
			return true;// super.dispatchTouchEvent(event);
		} else {
			return super.dispatchTouchEvent(event);
		}
	}
	

	private class LoadTask extends NewsResultAsyncTask<Void, Void, NewsResult> {

		public LoadTask(Context context) {
         super(context);
         // TODO Auto-generated constructor stub
      }

      @Override
		protected NewsResult doInBackground(Void... params) {
         NewsResult rtn = null;
			switch (mListType) {
			case Utils.RECOMMEND_LIST_TYPE:
				rtn = getRecommendListData();
				break;
			case Utils.EXPERT_LIST_TYPE:

				rtn = getExpertListData();
				break;
			case Utils.USER_ISSUES_TYPE:
				rtn = getUserIssuesData();
				break;

			case Utils.NOTIFY_LIST_TYPE:
				rtn = getNoticeListData();
				break;

			case Utils.DAILY_REMINDER_TYPE:
				rtn = getDailyReminderData();

				break;
			}
			return rtn;
		}

		@Override
		public void onPostExecute(NewsResult result) {
			mLoadTask = null;
			if (mDlg != null) {
				Utils.closeProgressDlg(mDlg);
				mDlg = null;
			}
			if (result != null && result.isFailure()) {
				if (result.isNetworkError()) {
					Utils.ShowNetworkErrorDialog(AzkerListActivity.this, true);
				} else {
					Utils.ShowResultErrorDialog(AzkerListActivity.this, result.getResultCode(), true);
				}
			}else if (result != null && result.isSuccess() && mListDatas != null && mListDatas.size() > 0) {
				switch (mListType) {
				case Utils.RECOMMEND_LIST_TYPE:
				case Utils.EXPERT_LIST_TYPE:
				case Utils.USER_ISSUES_TYPE:
					mAdapter = new IconTextAdapter();
					mListView.setAdapter(mAdapter);
					mLoadBitmapTask = new LoadBitmapTask();
					mLoadBitmapTask.execute();
					break;

				case Utils.NOTIFY_LIST_TYPE:
				case Utils.DAILY_REMINDER_TYPE:
					mAdapter = new TextAdapter();
					mListView.setAdapter(mAdapter);
					break;
				}

			} else {
			   showEmpthyMessage(mListType);
			}
		}

	}

	private class LoadBitmapTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			if (mListDatas != null) {
				for (int i = 0; i < mListDatas.size(); i++) {
					ListData data = mListDatas.get(i);
					if (data.mIconUrl != null
							&& (data.mBitmap == null || data.mBitmap
									.isRecycled())) {
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

	private NewsResult getRecommendListData() {
		if (mListDatas != null) {
			mListDatas.clear();
			mListDatas = null;
		}
		String userId = ((NewsApp) getApplication()).getCurrentUserId();
		if (userId != null) {
			RecommendedTopicList list = RecommendApi.getRecommendedTopicList(
					this, userId);
			if (list != null) {
				mRecommendList = list.getList();
				if (mRecommendList != null && mRecommendList.size() > 0) {
					if (mListDatas == null) {
						mListDatas = new ArrayList<ListData>();
					}
					ArrayList<TempCacheData> cacheList = new ArrayList<TempCacheData>();
					for (int i = 0; i < mRecommendList.size(); i++) {
						RecommendedTopic topic = mRecommendList.get(i);
						mListDatas.add(new ListData(topic.getId(), topic
								.getTitle(), topic.getIconUrl()));
						cacheList.add(new TempCacheData(topic.getId()));
					}
					mApp.setTempCache(cacheList);
				}
			}
	      return list;
		}
		return null;
	}

	private NewsResult getExpertListData() {
		if (mListDatas != null) {
			mListDatas.clear();
			mListDatas = null;
		}
		String userId = ((NewsApp) getApplication()).getCurrentUserId();
		if (userId != null) {
			NewsColumnistList list = NewsColumnistApi.getNewsColumnistList(
					this, userId);
			if (list != null) {
				mExpertList = list.getList();
				if (mExpertList != null && mExpertList.size() > 0) {
					if (mListDatas == null) {
						mListDatas = new ArrayList<ListData>();
					}
					ArrayList<TempCacheData> cacheList = new ArrayList<TempCacheData>();
					for (int i = 0; i < mExpertList.size(); i++) {
						NewsColumnist expert = mExpertList.get(i);
						mListDatas.add(new ListData(expert.getId(), expert
								.getName(), expert.getIconUrl(), expert
								.getOutline(), expert.getSortOrder()));
						cacheList.add(new TempCacheData(expert.getId()));
					}
					mApp.setTempCache(cacheList);
					
				}
			}
			return list;
		}
		return null;
	}

	private NewsResult getUserIssuesData() {
		if (mListDatas != null) {
			mListDatas.clear();
			mListDatas = null;
		}
		String userId = ((NewsApp) getApplication()).getCurrentUserId();
		if (userId != null) {
			SubscriptionTopicList list = SubscriptionApi
					.getSubscriptionTopicList(this, userId);
			if (list != null) {
				mUserIssuesList = list.getList();
				if (mUserIssuesList != null && mUserIssuesList.size() > 0) {
					if (mListDatas == null) {
						mListDatas = new ArrayList<ListData>();
					}
					for (int i = 0; i < mUserIssuesList.size(); i++) {
						SubscriptionTopic userIssue = mUserIssuesList.get(i);
						mListDatas.add(new ListData(userIssue.getId(),
								userIssue.getTitle(), userIssue.getIconUrl(), 
								userIssue.getType()));
					}
				}

			}
         return list;
		}
		return null;
	}

	private NewsResult getNoticeListData() {
		if (mListDatas != null) {
			mListDatas.clear();
			mListDatas = null;
		}
		String userId = ((NewsApp) getApplication()).getCurrentUserId();
		if (userId != null) {
			NewsNoticeList list = NewsNoticeApi.getNewsNoticeList(this, userId);
			if (list != null) {
				mNoticeList = list.getList();
				if (mNoticeList != null && mNoticeList.size() > 0) {
					if (mListDatas == null) {
						mListDatas = new ArrayList<ListData>();
					}
					ArrayList<TempCacheData> cacheList = new ArrayList<TempCacheData>();
					for (int i = 0; i < mNoticeList.size(); i++) {
						NewsNotice notice = mNoticeList.get(i);
						mListDatas.add(new ListData(notice.getId(), notice
								.getTitle(), null, null, notice
								.getPublishedTime(), null));
						cacheList.add(new TempCacheData(notice.getId()));
					}
					mApp.setTempCache(cacheList);
				}
			}

         return list;
		}
		return null;
	}

	private List<DailyReminder> mDailyReminderList = null;

	private NewsResult getDailyReminderData() {
		if (mListDatas != null) {
			mListDatas.clear();
			mListDatas = null;
		}
		String userId = ((NewsApp) getApplication()).getCurrentUserId();
		if (userId != null) {
			DailyReminderList list = DailyReminderApi.getDailyReminderList(
					this, userId);
			if (list != null) {
				mDailyReminderList = list.getList();
				if (mDailyReminderList != null && mDailyReminderList.size() > 0) {
					if (mListDatas == null) {
						mListDatas = new ArrayList<ListData>();
					}
					for (int i = 0; i < mDailyReminderList.size(); i++) {
						DailyReminder dailyReminder = mDailyReminderList.get(i);
						String id = dailyReminder.getTopicId();
						String title = dailyReminder.getTitle();
						String subtitle = dailyReminder.getContent();
						String data = dailyReminder.getPublishedTime();
						String lastday = dailyReminder.getNumberOfDays();

						mListDatas.add(new ListData(id, title, null, subtitle,
								data, lastday));

						// String id, String title, String iconUrl,
						// String subTitle, String date, String dateNum
					}
				}
			}
         return list;
		}
		return null;
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
				} else {
				   icon.setImageBitmap(null);
				}
				if (data.mTitle != null) {
					title.setText(Html.fromHtml(data.mTitle));
				}
				if (data.mSubTitle != null) {
					subtitle.setText(Html.fromHtml(data.mSubTitle));
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

	private class TextAdapter extends BaseAdapter {

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
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			LinearLayout layout = (LinearLayout) convertView;
			if (layout == null) {
				layout = (LinearLayout) mInflater.inflate(
						R.layout.textdate_item, null);
			}
			ListData data = mListDatas.get(position);
			if (data != null) {
				TextView date = (TextView) layout.findViewById(R.id.date);
				TextView lastDays = (TextView) layout
						.findViewById(R.id.lastdays);
				TextView title = (TextView) layout.findViewById(R.id.title);
				final TextView subTitle = (TextView) layout
						.findViewById(R.id.sub_title);
				final ImageView expendBtn = (ImageView) layout
						.findViewById(R.id.expend_btn);

				expendBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						ListData data = mListDatas.get(position);
						data.mIsShowSubTitle = !data.mIsShowSubTitle;
						// if (subTitle.getVisibility() != View.VISIBLE) {
						// subTitle.setVisibility(View.VISIBLE);
						// expendBtn.setImageResource(R.drawable.angle_up);
						// } else {
						// subTitle.setVisibility(View.GONE);
						// expendBtn.setImageResource(R.drawable.angle_down);
						//
						// }
						mAdapter.notifyDataSetChanged();
					}
				});

				if (data.mDate != null) {
					date.setText(data.mDate);
				}
				if (data.mDayNum != null) {
					lastDays.setText(data.mDayNum);
					lastDays.setVisibility(View.VISIBLE);
				} else {
					lastDays.setVisibility(View.GONE);
				}
				if (data.mTitle != null) {
					title.setText(Html.fromHtml(data.mTitle));
				}
				if (data.mSubTitle != null) {
					subTitle.setText(Html.fromHtml(data.mSubTitle));
					expendBtn.setVisibility(View.VISIBLE);
					expendBtn.setClickable(true);
//					expendBtn.setBackgroundColor(Color.CYAN);
					if (data.mIsShowSubTitle) {
						subTitle.setVisibility(View.VISIBLE);
						expendBtn.setImageResource(R.drawable.angle_up);
					} else {
						subTitle.setVisibility(View.GONE);
						expendBtn.setImageResource(R.drawable.angle_down);
					}
				} else {
					subTitle.setVisibility(View.GONE);
					expendBtn.setVisibility(View.GONE);
				}
//				layout.setOnClickListener(new OnClickListener() {
//               
//               @Override
//                  startNextActivityByClick(position);
//                  
//               }
//            });

				return layout;
			} else {
				return null;
			}
		}

	}
	
	private void startPageActivity(int position, boolean bNeedResult) {
		Intent it = new Intent(this, PageActivity.class);
		it.putExtra(PageActivity.PAGE_TYPE, mListType);
		it.putExtra(PageActivity.START_INDEX, position);
		it.putExtra(PageActivity.CATEGORY_TITLE, mTitle);
		if (bNeedResult) {
			startActivityForResult(it, RECOMMEND_REQUEST_CODE);
		} else {
			startActivity(it);
		}
	}
	
	private void startSummaryActivity(int position) {
		Intent it = new Intent(this, SummaryActivity.class);
		it.putExtra(SummaryActivity.CHANNEL_TYPE, mListType);
		it.putExtra(SummaryActivity.CHANNEL_TITLE, mTitle);
		ListData data = mListDatas.get(position);
		it.putExtra(SummaryActivity.CHANNEL_ID, data.mId);
		it.putExtra(SummaryActivity.TITLE_TYPE, data.mType);
		startActivity(it);
	}
	
	private void showEmpthyMessage(int listType) {
	   int str_id = 0;
	   switch(listType) {
      case Utils.RECOMMEND_LIST_TYPE:
         str_id = R.string.empty_recommend_list;
         break;
      case Utils.NOTIFY_LIST_TYPE:
         str_id = R.string.empty_notice_list;
         break;
      case Utils.USER_ISSUES_TYPE:
         str_id = R.string.empty_user_issue;
         break;
      case Utils.DAILY_REMINDER_TYPE:
         str_id = R.string.empty_daily_reminder;
         break;
	   }
	   if (str_id > 0) {
         Utils.ShowConfirmDialog(this, getString(str_id), new DialogConfirmCallback() {
            
            public void onConfirm(DialogInterface dialog) {
              AzkerListActivity.this.finish();
               
            }
         });
	   }
	}
	
	private class  ItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
		   startNextActivityByClick(position);
//			switch (mListType) {
//			case Utils.RECOMMEND_LIST_TYPE:
//				startPageActivity(position, true);
//				break;
//			case Utils.EXPERT_LIST_TYPE:
//			case Utils.NOTIFY_LIST_TYPE:
//				startPageActivity(position, false);
//				break;
//			case Utils.USER_ISSUES_TYPE:
//			case Utils.DAILY_REMINDER_TYPE:
//				startSummaryActivity(position);
//
//				break;
//			}
			
		}
		
	}
	
	private void startNextActivityByClick(int position) {
	   switch (mListType) {
      case Utils.RECOMMEND_LIST_TYPE:
         startPageActivity(position, true);
         break;
      case Utils.EXPERT_LIST_TYPE:
      case Utils.NOTIFY_LIST_TYPE:
         startPageActivity(position, false);
         break;
      case Utils.USER_ISSUES_TYPE:
      case Utils.DAILY_REMINDER_TYPE:
         startSummaryActivity(position);
         break;
      }
	}
	

	private class ListData {
		public ListData(String id, String title, String iconUrl) {
			mId = id;
			mTitle = title;
			mIconUrl = iconUrl;
		}

		public ListData(String id, String title, String iconUrl, int type) {
		   this(id, title, iconUrl);
		   mType = type;
		}

		public ListData(String id, String title, String iconUrl, String subTitle) {
		   this(id, title, iconUrl);
			mSubTitle = subTitle;
		}

		public ListData(String id, String title, String iconUrl,
				String subTitle, int sortNum) {
		   this(id, title, iconUrl, subTitle);
			mSortNum = mSortNum;
		}

		public ListData(String id, String title, String iconUrl,
				String subTitle, String date, String dateNum) {
			mId = id;
			mTitle = title;
			mIconUrl = iconUrl;
			mSubTitle = subTitle;
			mDate = date;
			mDayNum = dateNum;
		}

		String mId = null; // in DailyReminder is issue id
		String mTitle = null;
		String mIconUrl = null;;
		int mType = 1;
		String mSubTitle = null;
		Bitmap mBitmap = null;
		int mSortNum; // for expert list
		String mDate = null; // for notify and DailyReminder
		String mDayNum = null;// for DailyReminder
		boolean mIsShowSubTitle = false;// for DailyReminder
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == RECOMMEND_REQUEST_CODE) {
				
				Bundle extras =data.getExtras();
				if (extras != null) {
					String pageId = extras.getString(PageActivity.PAGE_ID);
					UserIssueRecommend(pageId);
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void UserIssueRecommend(String pageId) {
		boolean bEmpty = false;
		if (pageId != null) {
			if (mListDatas.size() > 0) {
				for (int i = 0; i < mListDatas.size(); i++) {
					ListData data = mListDatas.get(i);
					if (pageId.equals(data.mId)) {
						mListDatas.remove(data);
						mAdapter.notifyDataSetChanged();
						if (mListDatas.size() <= 0) {
							bEmpty = true;
						}
					}
				}
			} else {
				bEmpty = true;
			}
		}
		if (bEmpty) {
			showEmpthyMessage(Utils.RECOMMEND_LIST_TYPE);
		}
		
	}
}