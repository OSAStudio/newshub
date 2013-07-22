package com.osastudio.newshub.widgets;

import android.view.View;

public abstract class BaseAssistent {
	public abstract int getCount();

	public abstract Object getItem(int position);

	public abstract View getView(int position, View convertView);
}