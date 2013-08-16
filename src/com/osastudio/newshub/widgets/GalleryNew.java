package com.osastudio.newshub.widgets;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Gallery;

public class GalleryNew extends Gallery {
	public static enum GalleryTouchDisableType {
		ZOOM, PAINT, VIDEO_PLAYING
	}

	private Context mContext = null;

	private int isEnable = 0; // 0, enable gallery touch
	private boolean bPointDown = false;
	private int index = -1;

	public GalleryNew(Context context) {
		this(context, null);
	}

	public GalleryNew(Context context, AttributeSet attrs) {
		super(context, attrs);

		mContext = context;
		setSoundEffectsEnabled(false);
	}

	public void setTouchEnable(boolean bEnable, GalleryTouchDisableType type) {
		int id = type.ordinal();
		int flag = 1 << id;
		if (bEnable) {
			flag = ~flag;
			isEnable &= flag;
		} else {
			isEnable |= flag;
		}
	}

	// public void setTouchEnable(int bEnable) {
	// this.isEnable = bEnable;
	// }

	/**
	 * check if scrolling left or right
	 */
	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
		return e2.getX() > e1.getX();
	}

	// @Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (isEnable != 0) {
			return true;
		}
		if (e1 == null && e2 == null) {
			super.onFling(e1, e2, velocityX, velocityY);
		} else {

			int kEvent;
			if (isScrollingLeft(e1, e2)) {
				kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
			} else {
				kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
			}
			onKeyDown(kEvent, null);
			if (mIsPlaying) {
				startPlaying();
			}

		}
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_POINTER_DOWN:
			// ((PPTActivity)mContext).zoomPage();
			// bEnable = false;
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_UP:
			break;
		case MotionEvent.ACTION_POINTER_UP:
			break;
		}
		return super.onTouchEvent(event);
	}

	// @Override
	// public boolean onInterceptTouchEvent(MotionEvent ev) {
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_POINTER_DOWN:
			// bEnable = false;
			setTouchEnable(false, GalleryTouchDisableType.ZOOM);
			bPointDown = true;
			index = getSelectedItemPosition();
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_UP:

			if ((isEnable != 0) && bPointDown) {
				onTouchEvent(ev);
				bPointDown = false;
			}
			break;
		case MotionEvent.ACTION_POINTER_UP:
			break;
		}
		if (isEnable == 0) {
			onTouchEvent(ev);
		}
		return super.dispatchTouchEvent(ev);
		// if(bEnable) {
		// return onTouchEvent(ev);
		// } else {
		// return super.dispatchTouchEvent(ev);//onInterceptTouchEvent(ev);
		// }
	}

	private boolean mIsPlaying = false;
	private Timer mPlayingTimer = null;
	private TimerTask mPlayingTask = null;
	private AutoSLideListener mPlayingListener = null;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {

			}
		};
	};

	public void startAutoSlide(AutoSLideListener listener) {
		mIsPlaying = true;
		mPlayingListener = listener;
		startPlaying();
	}

	public void stopAutoSLide() {
		mIsPlaying = false;
		if (mPlayingTimer != null) {
			mPlayingTimer.cancel();
			mPlayingTimer = null;
		}
	}

	public boolean isAutoSlidePlaying() {
		return mIsPlaying;
	}
	
	private void startPlaying() {
		final int count = getCount();
		int cur = getSelectedItemPosition();
		if (cur < count) {
			setSpacing(-1);
			mIsPlaying = true;
			if (mPlayingTimer != null) {
				mPlayingTimer.cancel();
			}
			mPlayingTimer = new Timer();
			
			
			mPlayingTimer.schedule(new TimerTask() {
				public void run() {
					GalleryNew.this.post(new Runnable() {
						public void run() {
							int current = getSelectedItemPosition();
							if (current < count-1) {
//								setSelection(current + 1);
//								scrollBy(-25, 0);
//								KeyEvent ev = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_RIGHT);
//								GalleryNew.this.dispatchKeyEvent(ev);
//								View v1 = getChildAt(current);
//								View v2 = getChildAt(current+1);
//								AppUtil.Debug("startPlaying", "child1="+v1+" child2="+v2);
//								
								onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
								
								current++;
							}
							if (mPlayingListener != null) {
								mPlayingListener.onSlide(GalleryNew.this,
										current);
							}

						}
					});
				}
			}, 3000, 3000);
		}
	}

	public interface AutoSLideListener {
		void onSlide(GalleryNew gallery, int currentPosition);
	}

}