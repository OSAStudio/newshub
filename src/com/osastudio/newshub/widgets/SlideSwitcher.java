package com.osastudio.newshub.widgets;


import com.osastudio.newshub.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ViewSwitcher;

public class SlideSwitcher extends ViewSwitcher implements ViewSwitcher.ViewFactory {
	Context mContext = null;
	int mSize = 0;
	int mCurrentIndex = 0;
	BaseAssistent mAssistant = null;
	boolean mbLoop = false;
	
	public SlideSwitcher(Context context) {
		this(context, null);

	}
	
	public SlideSwitcher(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		setFactory(this);
	}

	public View makeView() {
		RelativeLayout layout = new RelativeLayout(mContext);
		layout.setLayoutParams(new ViewSwitcher.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		layout.setPadding(1, 0, 1, 0);
		return layout;
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
	}
	
	
	public void setAssistant(BaseAssistent assistant) {
		mAssistant = assistant;
		mSize = assistant.getCount();
		if (mSize > 0) {
			RelativeLayout layout = (RelativeLayout)getCurrentView();
			setupLayout(mCurrentIndex, layout);
		}
		invalidate();
	}
	
	public void setRepeat(boolean bLoop) {
		mbLoop = bLoop;
	}
	
	int count = 0;
	public void SwitcherOnScroll(int direction) {
		
		if (direction == 0) {
			switchToPrevious(
					new AnimItem(R.anim.push_right_in, R.anim.push_left_out));
		} else if (direction == 1) {
			AnimItem anim;
			anim = new AnimItem(R.anim.push_left_in, R.anim.push_right_out);
			switchToNext(anim);
		}
	}
	
	public boolean switchToNext(AnimItem anim) {
		if (anim == null) {
			anim = new AnimItem(R.anim.push_left_in, R.anim.push_right_out);
		}
		int nextIndex = mCurrentIndex;
		if (mSize <= 0) {
			return false;
		}
		if (mCurrentIndex >= mSize - 1) {
			if (mbLoop) {
				nextIndex = 0;
			} else {
				return false;
			}
		} else {
			nextIndex++;
		}
		RelativeLayout layout = (RelativeLayout) getNextView();
		setupLayout(nextIndex, layout);
		setInAnimation(mContext, anim.mInAnim);//R.anim.push_left_in);
		setOutAnimation(mContext, anim.mOutAnit);
		showNext();
		mCurrentIndex = nextIndex;
		return true;
	}
	
	public boolean switchToPrevious(AnimItem anim) {
		if (anim == null) {
			anim = new AnimItem(R.anim.push_right_in, R.anim.push_left_out);
		}
		int preIndex = mCurrentIndex;
		if (mSize <= 0) {
			return false;
		}
		if (mCurrentIndex <= 0) {
			if (mbLoop) {
				preIndex = mSize - 1;
			} else {
				return false;
			}
		} else {
			preIndex--;
		}
		RelativeLayout layout = (RelativeLayout) getNextView();
		setupLayout(preIndex, layout);
		setInAnimation(mContext, anim.mInAnim);
		setOutAnimation(mContext, anim.mOutAnit);
		showPrevious();
		mCurrentIndex = preIndex;
		return true;
	}
	
	private boolean setupLayout(int childIndex, RelativeLayout layout) {
		View view = null;
		if (childIndex >= mSize) {
			return false;
		}
		if (layout != null) {
			view = mAssistant.getView(childIndex, layout.getChildAt(0));
		}
		if (view != null) {
			if (layout.getChildCount() > 0) {
				layout.removeAllViews();
			}
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			layout.addView(view, lp);
			layout.invalidate();
		}
		return true;
		
	}
	
	public int getCurrentIndex() {
		return mCurrentIndex;
	}
	
	private class AnimItem {
		public int mInAnim;
		public int mOutAnit;
		public AnimItem(int inAnim, int outAnim) {
			mInAnim = inAnim;
			mOutAnit = outAnim;
		}
	}
	
	
	
}