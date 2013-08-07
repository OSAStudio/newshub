package com.osastudio.newshub.widgets;

import com.huadi.azker_phone.R;
import com.osastudio.newshub.utils.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
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
		WebSettings ws = mWebView.getSettings();
		
		TextSize size = ws.getTextSize();
		Utils.logd("getTextSize", "size="+size);
		
		mWebView.setBackgroundColor(0);
		mWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setOnLongClickListener(new WebView.OnLongClickListener() {
            
            public boolean onLongClick(View v) {
                return true;
            }
        });
//		mWebView.getSettings().setBlockNetworkImage(true);
	}
	
	public void setData(String html, int size) {
		mHtml = html;

		mHtml= "<html> \n" +
	              "<head> \n" +
	              "<style type=\"text/css\"> \n" +
	              "h2 {text-align:justify; font-size: "+(size+4)+"px; line-height: "+(size+10)+"px}\n" +
	              "body {text-align:justify; font-size: "+size+"px; line-height: "+(size+6)+"px}\n" +
	              "</style> \n" +
	              "</head> \n" +
	              "<body>"+html+"</body> \n </html>";
		mWebView.loadDataWithBaseURL("about:blank", mHtml, MIMETYPE, ENCODING, "");
	}
	
	public void displayTop() {

		mWebView.scrollTo(getScrollX(), 0);
	}
	
}