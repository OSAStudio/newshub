package com.osastudio.newshub.widgets;

import java.util.ArrayList;

import com.huadi.azker_phone.R;



import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SummaryGrid extends LinearLayout implements
		View.OnClickListener, View.OnLongClickListener {
	private final static int CONTINER_ID[] = { R.id.container0,
			R.id.container1, R.id.container2, R.id.container3, R.id.container4,
			R.id.container5 };

	private ArrayList<RelativeLayout> mContiners = new ArrayList<RelativeLayout>();

	private Context mContext = null;
	private BaseAssistent mAssistant = null;
	private OnGridItemClickListener mClickListener = null;

	public SummaryGrid(Context context) {
		this(context, null);
	}

	public SummaryGrid(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		LayoutInflater inflater = LayoutInflater.from(mContext);
		inflater.inflate(R.layout.summary_grid, this);
		findViews();
	}

	private void findViews() {
		for (int i = 0; i < CONTINER_ID.length; i++) {
			RelativeLayout continer = (RelativeLayout) findViewById(CONTINER_ID[i]);
			if (i != 0) {
				continer.setOnClickListener(this);
			}
			continer.setOnLongClickListener(this);
			mContiners.add(continer);
		}
	}
	
	public void setAssistant(BaseAssistent assistant) {
		mAssistant = assistant;
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

	@Override
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

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		return true;
	}

}