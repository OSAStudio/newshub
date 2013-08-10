package com.osastudio.newshub.widgets;

import com.huadi.azker_phone.R;
import com.osastudio.newshub.NewsApp;
import com.osastudio.newshub.data.NewsResult;
import com.osastudio.newshub.data.NoticeResult;
import com.osastudio.newshub.net.NewsArticleApi;
import com.osastudio.newshub.net.NewsNoticeApi;
import com.osastudio.newshub.utils.Utils;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.LinearLayout;
import android.widget.Toast;

public class FileView extends LinearLayout {
   
	final String jsLoadImageMethods = "<script type='text/javascript'>"
			+ "imageSrc = new Array();"
         + ""
			+ "function getRemoteImage(index)"
			+ "{"
			+ "var images = document.getElementsByTagName('img');"
			+ "images[index].src = imageSrc[index];"
			+ "images[index].onclick = undefined;"
			+ "}"
			+ ""
			+ "function useDefaultImage()"
			+ "{"
			+ "var images = document.getElementsByTagName('img');"
			+ "for (var i = 0; i < images.length; i++) {"
			+ "imageSrc.push(images[i].src);"
			+ "images[i].src = 'file:///android_asset/web_default_image.png';"
			+ "images[i].onclick = new Function('getRemoteImage(' + i + ')')"
			+ "}"
			+ "}"
			+ ""
			+ "window.onload = useDefaultImage;"
			+ "</script>"; 

	final String MIMETYPE = "text/html";

	final String ENCODING = "utf-8";

	private Context mContext = null;
	private WebView mWebView = null;
	private View mPraiseBtn = null;
	private String mAarticleId = null;
	private String mHtml = null;
	private int mFileType;

	public FileView(Context context) {
		this(context, null);
	}

	public FileView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;

		LayoutInflater inflater = LayoutInflater.from(mContext);
		inflater.inflate(R.layout.file_view, this);
		mPraiseBtn = findViewById(R.id.praise_btn);
		mPraiseBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				new PraiseTask().execute();
			}
		});
		setupWebView();
	}

	private void setupWebView() {
		mWebView = (WebView) findViewById(R.id.web_view);
		// mWebView.addJavascriptInterface(new Js2JavaInterface(),
		// HtmlParser.Js2JavaInterfaceName);
		WebSettings ws = mWebView.getSettings();

		TextSize size = ws.getTextSize();
		Utils.logd("getTextSize", "size=" + size);

		mWebView.setBackgroundColor(0);
		mWebView.getSettings()
				.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setOnLongClickListener(new WebView.OnLongClickListener() {

			public boolean onLongClick(View v) {
				return true;
			}
		});
		// mWebView.getSettings().setBlockNetworkImage(true);
	}

	public void setData(int fileType, String html, int size, String articleId) {
		mFileType = fileType;
		mHtml = html;
		mAarticleId = articleId;
		if (mAarticleId != null
				&& (fileType == Utils.LESSON_LIST_TYPE
						|| fileType == Utils.IMPORT_NOTIFY_TYPE || fileType == Utils.NOTIFY_LIST_TYPE)) {
			findViewById(R.id.praise_layout).setVisibility(View.VISIBLE);
		} else {
			findViewById(R.id.praise_layout).setVisibility(View.GONE);

		}
      
		boolean enable = ((NewsApp) mContext.getApplicationContext())
		      .getPrefsManager().isAutoLoadingPictureEnabled();

		mHtml = "<html> \n" + "<head> \n";
      if (!enable) {
         mHtml += jsLoadImageMethods;
      }
		mHtml = mHtml + "<style type=\"text/css\"> \n"
				+ "h2 {text-align:justify; font-size: " + (size + 4)
				+ "px; line-height: " + (size + 10) + "px}\n"
				+ "body {text-align:justify; font-size: " + size
				+ "px; line-height: " + (size + size) + "px}\n" + "</style> \n"
				+ "</head> \n" + "<body>" + html + "</body> \n </html>";
		mWebView.loadDataWithBaseURL("about:blank", mHtml, MIMETYPE, ENCODING,
				"");
	}

	public void displayTop() {

		mWebView.scrollTo(getScrollX(), 0);
	}

	private class PraiseTask extends AsyncTask<Void, Void, NewsResult> {

		@Override
		protected NewsResult doInBackground(Void... params) {
			NewsResult result=null;
			if (!((Activity) mContext).isFinishing()) {
				
				switch (mFileType) {
				case Utils.LESSON_LIST_TYPE:
					result = NewsArticleApi.likeArticle(
							mContext.getApplicationContext(), mAarticleId);
					break;
				case Utils.IMPORT_NOTIFY_TYPE:
				case Utils.NOTIFY_LIST_TYPE:
					result = NewsNoticeApi.feedbackNotice(mContext.getApplicationContext(), mAarticleId);
					break;
				}
			}
			return result;
		}
		
		@Override
		protected void onPostExecute(NewsResult result) {
			int msgId = -1;
			if (!((Activity) mContext).isFinishing()) {
				if (result instanceof NoticeResult) {
					if (((NoticeResult)result).alreadyFeedback()) {
						msgId = R.string.notice_already_feedback;
					} else if (result.isSuccess()) {
						msgId = R.string.notice_feedback;
					}
				} else if (result.isSuccess()){
					msgId = R.string.praise;
				}
				if (msgId > 0) {
					Toast.makeText(mContext, msgId, Toast.LENGTH_LONG).show();
				}
			}
		}

	}

}