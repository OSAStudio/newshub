package com.osastudio.newshub;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLEngineResult.Status;

import com.huadi.azker_phone.R;
import com.osastudio.newshub.NewsApp.TempCacheData;
import com.osastudio.newshub.data.AppProperties;
import com.osastudio.newshub.data.NewsChannel;
import com.osastudio.newshub.data.NewsChannelList;
import com.osastudio.newshub.data.RecommendedTopic;
import com.osastudio.newshub.data.user.ValidateResult;
import com.osastudio.newshub.net.AppPropertiesApi;
import com.osastudio.newshub.net.NewsChannelApi;
import com.osastudio.newshub.net.UserApi;
import com.osastudio.newshub.utils.InputStreamHelper;
import com.osastudio.newshub.utils.Utils;
import com.osastudio.newshub.widgets.AzkerGridLayout;
import com.osastudio.newshub.widgets.AzkerGridLayout.OnGridItemClickListener;
import com.osastudio.newshub.widgets.BaseAssistent;
import com.osastudio.newshub.widgets.RegisterView;
import com.osastudio.newshub.widgets.SlideSwitcher;

import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

@SuppressLint("NewApi")
public class CategoryActivity extends NewsBaseActivity {
	private static final String DEFAULT_BACKGROUND_FILE = "file:///android_asset/1.jpg";

	public static final String TYPE_NOTIFY_LIST = "1";
	public static final String TYPE_EXPERT_LIST = "3";
	public static final String TYPE_USETLSSUES_MOBILE = "5";
	public static final String TYPE_LESSON_LIST = "6";

	public static final int REQUEST_NOTIFY_LIST = 1;
	public static final int REQUEST_EXPERT_LIST = 3;
	public static final int REQUEST_USETLSSUES_MOBILE = 5;
	public static final int REQUEST_LESSON_LIST = 6;
	public static final int REQUEST_USER_INFO = 7;

	private AppProperties mAppProperties = null;
	private Bitmap mReceiveBmp = null;
	private Bitmap mCoverBmp = null;
	private RelativeLayout mRoot = null;
	private ImageView mCover = null;
	private SlideSwitcher mSwitcher = null;
	private View mActivateLayout = null;
	private EditText mActivateEdit = null;
	private View mActivateBtn = null;
	private View mAccount_btn = null;
	private View mRecommend_btn = null;
	private View mExpertlist_btn = null;
	private View mFeedback_btn = null;
	private View mSetting_btn = null;
	private TextView mPage = null;

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
	private int mScreenHeight = 0;
	private float mdp = 1;
	private int mUserStatus = 3;
	private boolean mIsSplashShow = true;

	private LoadDataTask mTask = null;
	private ProgressDialog mDlg = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Rect frame = new Rect();  
		getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);  
		int statusBarHeight = frame.top; 
		


		
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		
		mScreenWidth = display.getWidth();
		mScreenWidth = mScreenWidth > 0 ? mScreenWidth : 0;
		mScreenHeight = display.getHeight();
		mScreenHeight = mScreenHeight > 0 ? mScreenHeight : 0;

		DisplayMetrics dm = new DisplayMetrics();
		display.getMetrics(dm);
		mdp = dm.density;

		setContentView(R.layout.category_activity);
		findViews();

		ViewConfiguration configuration = ViewConfiguration.get(this);
		mTouchSlop = configuration.getScaledTouchSlop();

		mInflater = LayoutInflater.from(this);

		setupData();
		mDlg = Utils.showProgressDlg(this, null);
	}
	
	public static int getStatusHeight(Activity activity){
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight){
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

	private void findViews() {
		int top = getStatusHeight(this);
		int margin = (int) ((mScreenHeight - mScreenWidth * 4 / 3 - top) / 2 - 5 * mdp);
		mRoot = (RelativeLayout) findViewById(R.id.root);
		Bitmap bg = getImageFromAssetsFile("1.jpg");
		if (bg != null) {
			mRoot.setBackgroundDrawable(new BitmapDrawable(bg));
		}
		mCover = (ImageView) findViewById(R.id.cover);

		mCoverBmp = getImageFromAssetsFile("0.jpg");
		if (mCoverBmp != null) {
			mCover.setImageBitmap(mCoverBmp);
		}

		mSwitcher = (SlideSwitcher) findViewById(R.id.switcher);
		mSwitcher.setVisibility(View.INVISIBLE);

		mActivateLayout = findViewById(R.id.activite);
		mActivateLayout.setVisibility(View.INVISIBLE);
		mActivateEdit = (EditText) findViewById(R.id.activite_edit);
		mActivateBtn = findViewById(R.id.activite_btn);
		mActivateBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String activate_str = mActivateEdit.getEditableText()
						.toString();
				if (activate_str != null && !activate_str.equals("")) {
					new ActivateTask().execute(activate_str);
				}

			}
		});

		mAccount_btn = findViewById(R.id.account_btn);
		RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) mAccount_btn
				.getLayoutParams();
		rlp.topMargin = margin;
		mAccount_btn.setLayoutParams(rlp);
		mAccount_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				startUserInfosActivity();

			}
		});

		mRecommend_btn = findViewById(R.id.recommend);
		mRecommend_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				startListActivity(Utils.RECOMMEND_LIST_TYPE, null);

			}
		});

		mExpertlist_btn = findViewById(R.id.expertlist);
		mExpertlist_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				startListActivity(Utils.EXPERT_LIST_TYPE, null);

			}
		});

		mFeedback_btn = findViewById(R.id.feedback);
		mFeedback_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startFeedbackActivity();
			}
		});

		mSetting_btn = findViewById(R.id.settings);
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mSetting_btn
				.getLayoutParams();
		lp.bottomMargin = margin;
		mSetting_btn.setLayoutParams(lp);
		mSetting_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startSettingActivity();

			}
		});

		mPage = (TextView) findViewById(R.id.page);
		rlp = (RelativeLayout.LayoutParams)mPage.getLayoutParams();
		rlp.height = margin;
		mPage.setLayoutParams(rlp);
	}

	private void setPageText(int current) {
		int total;
		if (mCategoryList.size() % 8 == 0) {
			total = mCategoryList.size() / 8;
		} else {
			total = mCategoryList.size() / 8 + 1;
		}
		String page = String.valueOf(current + 1) + "/" + String.valueOf(total);
		mPage.setText(page);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mTask != null
				&& !mTask.getStatus().equals(AsyncTask.Status.FINISHED)) {
			mTask.cancel(true);
			mTask = null;
		}

		if (mLoadBitmapTask != null
				&& !mLoadBitmapTask.getStatus().equals(
						AsyncTask.Status.FINISHED)) {
			mLoadBitmapTask.cancel(true);
			mLoadBitmapTask = null;
		}

		if (mIconList != null) {
			for (int i = 0; i < mIconList.size(); i++) {
				if (mIconList.get(i) != null && mIconList.get(i).mBmp != null
						&& !mIconList.get(i).mBmp.isRecycled()) {
					mIconList.get(i).mBmp.recycle();
					mIconList.get(i).mBmp = null;
				}
			}
		}
	}

	private Bitmap getImageFromAssetsFile(String fileName) {
		Bitmap bmp = null;
		AssetManager am = getResources().getAssets();
		try {
			InputStream is = am.open(fileName);
			bmp = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bmp;

	}

	private void hideCover() {
		View cover = findViewById(R.id.cover_layout);
		if (cover.getVisibility() == View.VISIBLE && mUserStatus == 3) {
			mSwitcher.setVisibility(View.VISIBLE);
			Animation anim = AnimationUtils.loadAnimation(this,
					R.anim.pull_out_to_top);
			cover.setVisibility(View.GONE);
			cover.setAnimation(anim);
		}
	}

	private void showCover() {
		View cover = findViewById(R.id.cover_layout);
		if (cover.getVisibility() != View.VISIBLE) {
			Animation anim = AnimationUtils.loadAnimation(this,
					R.anim.pull_in_from_top);
			anim.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					mSwitcher.setVisibility(View.INVISIBLE);

				}
			});
			cover.setAnimation(anim);
			cover.setVisibility(View.VISIBLE);
		}

	}

	private RegisterView mRegisterView = null;

	private void showRegisterView() {
		// mSwitcher.setVisibility(View.GONE);

		// mRegisterView = (RegisterView)findViewById(R.id.register);
		// mRegisterView.setVisibility(View.VISIBLE);
		RegisterView registerDlg = new RegisterView(this,
				R.style.Theme_PageDialog, mScreenWidth, mScreenHeight);
		registerDlg.show();
	}

	private void setupData() {
		mTask = new LoadDataTask();
		mTask.execute();
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
					setPageText(mSwitcher.getCurrentIndex());

					break;
				}
			}
			if (y - mInitY > mTouchSlop
					&& Math.abs(mInitX - x) < Math.abs(mInitY - y)) {
				showCover();
			} else if (mInitY - y > mTouchSlop
					&& Math.abs(mInitX - x) < Math.abs(mInitY - y)) {
				hideCover();
			}
			break;
		}
		return super.dispatchTouchEvent(event);
	}

	private class ActivateTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			ValidateResult result = UserApi.validate(getApplicationContext(),
					params[0]);
			return result.isValidated();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (result) {
				mActivateLayout.setVisibility(View.INVISIBLE);
				showRegisterView();
			} else {
				mActivateEdit.setText(null);
			}
			super.onPostExecute(result);
		}
	}

	private class BMP_Item {
		String mPath;
		Bitmap mBitmap;
	}

	// private class LoadImageTask extends AsyncTask<Void, BMP_Item, Void> {
	//
	// @Override
	// protected Void doInBackground(Void... params) {
	// BMP_Item item = new BMP_Item();
	// item.mPath = DEFAULT_SPLASH_FILE;
	// item.mBitmap = Utils.loadBitmap(DEFAULT_SPLASH_FILE, mScreenWidth,
	// 0, 0);
	// if (item.mBitmap != null) {
	// publishProgress(item);
	// }
	// item = new BMP_Item();
	// item.mPath = DEFAULT_BACKGROUND_FILE;
	// item.mBitmap = Utils.loadBitmap(DEFAULT_SPLASH_FILE, mScreenWidth,
	// 0, 0);
	// if (item.mBitmap != null) {
	// publishProgress(item);
	// }
	// return null;
	// }
	//
	// @Override
	// protected void onProgressUpdate(BMP_Item... values) {
	// BMP_Item item = values[0];
	// super.onProgressUpdate(values);
	// }
	//
	// }

	private class LoadDataTask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			mAppProperties = AppPropertiesApi
					.getAppProperties(CategoryActivity.this);
			Utils.logd("LoadDataTask", "mAppProperties=" + mAppProperties);
			if (mAppProperties != null) {
				publishProgress(1);
				mReceiveBmp = Utils.getBitmapFromUrl(mAppProperties
						.getSplashImageUrl());

				Utils.logd("LoadDataTask", "get cover bmp=" + mCoverBmp + "// "
						+ mAppProperties.getSplashImageUrl());
				publishProgress(2);
			}

			NewsChannelList channel_list = NewsChannelApi.getNewsChannelList(
					getApplicationContext(),
					((NewsApp) getApplication()).getCurrentUserId());
			if (channel_list != null) {
				mCategoryList = (ArrayList<NewsChannel>) channel_list
						.getChannelList();
			}

			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			int status = values[0];
			switch (status) {
			case 1:
				mUserStatus = mAppProperties.getUserStatus();
				if (mUserStatus == 1) {
					mActivateLayout.setVisibility(View.VISIBLE);
				} else {
					mActivateLayout.setVisibility(View.INVISIBLE);
					if (mUserStatus == 2) {
						showRegisterView();
					} else if (mUserStatus == 3) {
						List<String> userIds = mAppProperties.getUserIds();
						if (userIds != null && userIds.size() > 0) {
							((NewsApp) getApplication())
									.setCurrentUserId(userIds.get(0));
						}
					}
				}
				break;
			case 2:
				if (mCover != null && mReceiveBmp != null) {
					mCover.setImageBitmap(mReceiveBmp);
					if (mCoverBmp != null && !mCoverBmp.isRecycled()) {
						mCoverBmp.recycle();
					}
					mCoverBmp = mReceiveBmp;
				}
				break;
			case 3:
				break;
			}

			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Void result) {
			if (mDlg != null) {
				Utils.closeProgressDlg(mDlg);
				mDlg = null;
			}
			if (mCategoryList != null && mCategoryList.size() > 0) {
				SwitchAssistent assistent = new SwitchAssistent();
				mSwitcher.setAssistant(assistent);
				
				setPageText(mSwitcher.getCurrentIndex());

				mLoadBitmapTask = new LoadBitmapTask();
				mLoadBitmapTask.execute();
			}
			mTask = null;
			super.onPostExecute(result);
		}

		@Override
		protected void onCancelled() {
			if (mDlg != null) {
				Utils.closeProgressDlg(mDlg);
				mDlg = null;
			}
			mTask = null;
			super.onCancelled();
		}

	}

	private ArrayList<IconData> mIconList = null;
	private LoadBitmapTask mLoadBitmapTask = null;

	private class IconData {
		String mIconUrl = null;
		Bitmap mBmp = null;
	}

	private class LoadBitmapTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			if (mIconList == null) {
				mIconList = new ArrayList<IconData>();
			}
			for (int i = 0; i < mCategoryList.size(); i++) {
				boolean bNeedDecode = true;
				NewsChannel channel = mCategoryList.get(i);
				for (int j = 0; i < mIconList.size(); i++) {
					IconData data = mIconList.get(j);
					if (channel.getIconUrl().equals(data.mIconUrl)) {
						bNeedDecode = false;
						break;
					}
				}
				if (bNeedDecode) {
					Bitmap bmp = Utils.getBitmapFromUrl(channel.getIconUrl(),
							mScreenWidth / 8);

					Utils.logd("LoadBitmapTask",
							"decode icon " + channel.getIconUrl() + " " + bmp);
					if (bmp != null) {
						IconData iconData = new IconData();
						iconData.mIconUrl = channel.getIconUrl();
						iconData.mBmp = bmp;
						mIconList.add(iconData);
						publishProgress();
					}
				}

			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			SwitchAssistent assistent = new SwitchAssistent();
			mSwitcher.setAssistant(assistent);

			Utils.logd("LoadBitmapTask", "update icon ui");
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Void result) {
			mLoadBitmapTask = null;
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
			// AzkerGridLayout grid_layout = (AzkerGridLayout) convertView;
			// if (grid_layout == null) {
			// grid_layout = new AzkerGridLayout(CategoryActivity.this);
			// }
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.category_view, null);
			}
			AzkerGridLayout grid_layout = (AzkerGridLayout) convertView
					.findViewById(R.id.grid);
			android.view.ViewGroup.LayoutParams lp = grid_layout
					.getLayoutParams();
			if (lp != null) {
				lp.width = (int) (mScreenWidth * 2 / 3 + 10 * mdp);
				lp.height = lp.width * 2;
				grid_layout.setLayoutParams(lp);
			}

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
				NewsChannel data = mCategoryList.get(index);
				if (data.getTitleType() > 0) {
					startNextActivity(index);
				}
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

				View base = category.findViewById(R.id.base);
				ImageView iv = (ImageView) category.findViewById(R.id.image);
				TextView tv = (TextView) category.findViewById(R.id.name);

				NewsChannel data = mCategoryList.get(index);
				base.setBackgroundColor(data.getTitleColor());
				tv.setText(data.getTitleName());
				if (mIconList != null) {
					for (int i = 0; i < mIconList.size(); i++) {
						IconData icondata = mIconList.get(i);
						if (icondata != null
								&& icondata.mIconUrl.equals(data.getIconUrl())) {
							if (icondata.mBmp != null
									&& !icondata.mBmp.isRecycled()) {
								iv.setImageBitmap(icondata.mBmp);
							}
						}
					}
				}
				return category;
			} else {
				return null;
			}
		}
	}

	private void startNextActivity(int index) {
		if (index < mCategoryList.size()) {
			NewsChannel data = mCategoryList.get(index);
			if (data.getTitleType() > 0) {
				int type = data.getTitleType();
				switch (type) {
				case Utils.IMPORT_NOTIFY_TYPE:
				case Utils.IMPORT_EXPERT_TYPE:
					ArrayList<TempCacheData> cacheList = new ArrayList<TempCacheData>();
					cacheList.add(new TempCacheData(data.getChannelId()));
					((NewsApp) getApplication()).setTempCache(cacheList);

					startPageActivity(type, data.getTitleName());
					break;
				case Utils.USER_ISSUES_TYPE:
				case Utils.DAILY_REMINDER_TYPE:
				case Utils.EXPERT_LIST_TYPE:
				case Utils.NOTIFY_LIST_TYPE:
				case Utils.RECOMMEND_LIST_TYPE:
					startListActivity(type, data.getTitleName());
					break;
				case Utils.LESSON_LIST_TYPE:
					startSummaryActivity(mCategoryList.get(index));
					break;
				}
			}
		}

	}

	private void startSummaryActivity(NewsChannel data) {
		int requestCode = REQUEST_LESSON_LIST;

		Intent it = new Intent(this, SummaryActivity.class);
		it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		it.putExtra(SummaryActivity.CHANNEL_TYPE, Utils.LESSON_LIST_TYPE);
		it.putExtra(SummaryActivity.CHANNEL_ID, data.getChannelId());
		it.putExtra(SummaryActivity.CHANNEL_TITLE, data.getTitleName());
		startActivityForResult(it, requestCode);
	}

	private void startUserInfosActivity() {
		Intent it = new Intent(this, UserInfosActivity.class);
		startActivityForResult(it, REQUEST_USER_INFO);
	}

	private void startPageActivity(int listType, String title) {
		Intent it = new Intent(this, PageActivity.class);
		it.putExtra(PageActivity.PAGE_TYPE, listType);
		it.putExtra(PageActivity.START_INDEX, 0);
		it.putExtra(PageActivity.CATEGORY_TITLE, title);
		startActivity(it);
	}

	private void startListActivity(int listtype, String title) {
		int title_resid = 0;
		if (title == null) {
			switch (listtype) {
			case Utils.RECOMMEND_LIST_TYPE:
				title_resid = R.string.recommend;
				break;
			case Utils.EXPERT_LIST_TYPE:
				title_resid = R.string.expertlist;
				break;
			case Utils.USER_ISSUES_TYPE:
				title_resid = R.string.user_issues_list;
				break;

			case Utils.NOTIFY_LIST_TYPE:
				break;
			case Utils.DAILY_REMINDER_TYPE:
				break;
			}
			if (title_resid > 0) {
				title = getString(title_resid);
			}
		}
		Intent it = new Intent(this, AzkerListActivity.class);
		it.putExtra(AzkerListActivity.LIST_TYPE, listtype);
		it.putExtra(AzkerListActivity.LIST_TITLE, title);
		startActivity(it);
	}

	private void startFeedbackActivity() {

		Intent it = new Intent(this, FeedbackActivity.class);
		startActivity(it);
	}

	private void startSettingActivity() {

		Intent it = new Intent(this, SettingActivity.class);
		startActivity(it);
	}

}
