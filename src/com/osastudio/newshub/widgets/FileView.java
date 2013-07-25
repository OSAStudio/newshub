package com.osastudio.newshub.widgets;

import com.osastudio.newshub.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.LinearLayout;

public class FileView extends LinearLayout {

	final String MIMETYPE = "text/html";

	final String ENCODING = "utf-8";
	
	private Context mContext = null;
	private WebView mWebView = null;
	private String mHtml = null;
	
	public FileView(Context context) {
		this(context, null);
	}

	public FileView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;

		LayoutInflater inflater = LayoutInflater.from(mContext);
		inflater.inflate(R.layout.file_view, this);
		setupWebView();
	}
	
	private void setupWebView() {
		mWebView = (WebView)findViewById(R.id.web_view);
//		mWebView.addJavascriptInterface(new Js2JavaInterface(),
//				HtmlParser.Js2JavaInterfaceName);
		mWebView.setBackgroundColor(0);
		mWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		mWebView.getSettings().setJavaScriptEnabled(true);

	}
	
	public void setData(String html) {
		mHtml = html;
		mWebView.loadDataWithBaseURL("about:blank", html, MIMETYPE, ENCODING, "");
	}
	
}