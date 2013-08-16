package com.osastudio.newshub.widgets;

import java.util.ArrayList;

import com.huadi.azker_phone.R;
import com.osastudio.newshub.utils.Utils;



import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.LayoutInflater;
import android.view.GestureDetector.OnGestureListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class AzkerGridLayout extends LinearLayout /*implements View.OnClickListener, View.OnLongClickListener*/ {
	private final static int RAW_ID[] = { R.id.row0, R.id.row1, R.id.row2,
			R.id.row3 };
	private final static int CONTINER_ID[] = { R.id.container0,
			R.id.container1, R.id.container2, R.id.container3, R.id.container4,
			R.id.container5, R.id.container6, R.id.container7 };

	private ArrayList<RelativeLayout> mContiners = new ArrayList<RelativeLayout>();

	private Context mContext = null;
	private int mSize = 0;
	private BaseAssistent mAssistant = null;
	private OnGridItemClickListener mClickListener = null;
	private GestureDetector gd = null;

	public AzkerGridLayout(Context context) {
		this(context, null);
	}

	public AzkerGridLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		gd = new GestureDetector(context, new MyGestureListener());
		mContext = context;
		LayoutInflater inflater = LayoutInflater.from(mContext);
		inflater.inflate(R.layout.azker_grid_layout, this);
		findViews();
	}

	private void findViews() {
		for (int i = 0; i < CONTINER_ID.length; i++) {
			RelativeLayout continer = (RelativeLayout) findViewById(CONTINER_ID[i]);
//			continer.setOnClickListener(this);
//			continer.setOnLongClickListener(this);
			mContiners.add(continer);
		}
	}
	
	public void setAssistant(BaseAssistent assistant) {
		mAssistant = assistant;
		mSize = assistant.getCount();
		for (int i = 0; i < mContiners.size(); i++) {
			RelativeLayout continer = mContiners.get(i);
			setupLayout(i, continer);
		}
		invalidate();
	}
	
	private boolean setupLayout(int childIndex, RelativeLayout layout) {
		View view = null;
		if (childIndex >= mContiners.size()) {
			return false;
		}
		if (layout != null) {
			view = mAssistant.getView(childIndex, layout.getChildAt(0));
		}

		if (layout.getChildCount() > 0) {
			layout.removeAllViews();
		}
		if (view != null) {
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			layout.addView(view, lp);
			layout.invalidate();
		}
		return true;
		
	}

	public void onClick(View v) {
		if (mClickListener != null) {
			int id = v.getId();
			for (int i = 0; i < CONTINER_ID.length; i++) {
				if (id == CONTINER_ID[i]) {
					mClickListener.onClick(i, v);
					break;
				}
			}
		}

	}

	public void setGridItemClickListener(OnGridItemClickListener listener) {
		mClickListener = listener;
	}

	public interface OnGridItemClickListener {
		void onClick(int position, View v);
	}

	
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
	   gd.onTouchEvent(ev);
	   return true;
	}
	
	public class MyGestureListener implements OnGestureListener {

      @Override
      public boolean onDown(MotionEvent e) {
         // TODO Auto-generated method stub
         return false;
      }

      @Override
      public void onShowPress(MotionEvent e) {
         // TODO Auto-generated method stub
         
      }

      @Override
      public boolean onSingleTapUp(MotionEvent e) {
         // TODO Auto-generated method stub
         return false;
      }

      @Override
      public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
            float distanceY) {
         // TODO Auto-generated method stub
         return false;
      }

      @Override
      public void onLongPress(MotionEvent e) {
         // TODO Auto-generated method stub
         
      }

      @Override
      public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
            float velocityY) {
         // TODO Auto-generated method stub
         return false;
      }
	   
	}

}