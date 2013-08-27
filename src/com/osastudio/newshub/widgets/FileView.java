package com.osastudio.newshub.widgets;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.huadi.azker_phone.R;
import com.osastudio.newshub.NewsApp;
import com.osastudio.newshub.data.AppDeadline;
import com.osastudio.newshub.data.NewsResult;
import com.osastudio.newshub.data.NoticeResult;
import com.osastudio.newshub.data.base.NewsBaseObject;
import com.osastudio.newshub.net.NewsArticleApi;
import com.osastudio.newshub.net.NewsNoticeApi;
import com.osastudio.newshub.utils.NewsResultAsyncTask;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class FileView extends LinearLayout {
   
	final String jsLoadImageMethods = "<script type='text/javascript'>"
			+ "function getRemoteImage(index)"
			+ "{"
			+ "var images = document.getElementsByTagName('img');"
			+ "images[index].src = imageSrc[index];"
			+ "images[index].onclick = undefined;"
//			+ "var orisrc = imagex[index].getAttribute(\"orisrc\");"
//         + "images[index].setAttribute(\"src\",orisrc);"
//         + "images[index].setAttribute(\"onclick\", undefined);"
			+ "}"
			+ ""
			+ "function useDefaultImage()"
			+ "{"
			+ "var images = document.getElementsByTagName('img');"
			+ "for (var i = 0; i < images.length; i++) {"
			+ "imageSrc.push(images[i].src);"
			+ "images[i].src = 'file:///android_asset/web_default_image.png';"
			+ "images[i].onclick = new Function('getRemoteImage(' + i + ')');"
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
				
				new PraiseTask(mContext).execute();
			}
		});
		setupWebView();
	}

	public class JSInterface {
      
      private WebView webView;
	   public JSInterface(WebView webView) {
	      this.webView = webView;
	   }

		public void getRemoteImage(String imgUrl) {
         Utils.logi("", "______________________getRemoteImage: " + imgUrl);
			this.webView.loadUrl("javascript:(function(){"
					+ "var objs = document.getElementsByTagName(\"img\"); "
					+ "for(var i=0;i<objs.length;i++)  "
					+ "{"
					+ "    var imgOriSrc = objs[i].getAttribute(\"orisrc\"); "
					+ " if(imgOriSrc == \"" + imgUrl + "\"){ "
					+ "    objs[i].setAttribute(\"src\",imgOriSrc);"
					+ "    objs[i].setAttribute(\"onclick\", undefined);}" 
					+ "}"
					+ "})()");
		}

	}

	private void setupWebView() {
		mWebView = (WebView) findViewById(R.id.web_view);
		 mWebView.addJavascriptInterface(new JSInterface(mWebView),
		 "jsif");
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
      mWebView.setLongClickable(false);
		// mWebView.getSettings().setBlockNetworkImage(true);
	}
   
	private String processContent(String content, int size, boolean autoLoad) {
      AppDeadline deadline = ((NewsApp) mContext.getApplicationContext())
            .getAppDeadline();
      boolean hasExpired = (deadline != null) ? deadline.hasExpired()
            : new AppDeadline().hasExpired();
	   Document document = null;
	   document = Jsoup.parse(content);
      if (document != null) {
         if (hasExpired || !autoLoad) {
            Elements elements = document.getElementsByTag("img");
            if (elements != null && elements.size() > 0) {
               for (Element element : elements) {
                  String imgUrl = element.attr("src");
                  element.attr("src", "file:///android_asset/web_default_image.png");
                  element.attr("orisrc", imgUrl);
                  if (!hasExpired) {
                  element.attr("onclick", "window.jsif.getRemoteImage('"
                        + imgUrl + "')");
                  }
               }
            }
         }
         
	      String style = "<style type=\"text/css\"> \n"
			+ "h2 {text-align:justify; font-size: " + (size + 4)
			+ "px; line-height: " + (size + 10) + "px}\n"
			+ "body {text-align:justify; font-size: " + size
			+ "px; line-height: " + (size + size) + "px}\n" + "</style> \n";
         document.head().append(style).toString();
         
//         Utils.logi("", "___________processContent: " + document.toString());
         return document.toString();
      }
      return content;
	}

	public void setData(int fileType, String html, int size, String articleId, boolean bWIFI) {
		mFileType = fileType;
		mHtml = html;
		mAarticleId = articleId;
		if (mAarticleId != null
				&& (fileType == Utils.LESSON_LIST_TYPE
						|| fileType == Utils.IMPORT_NOTIFY_TYPE || fileType == Utils.NOTIFY_LIST_TYPE)) {
			if (fileType != Utils.LESSON_LIST_TYPE) {
				ImageView image = (ImageView)findViewById(R.id.praise_btn);
				if (image != null) {
					image.setImageResource(R.drawable.need_back);
				} else {
					image.setImageResource(R.drawable.praise);
				}
			}
			findViewById(R.id.praise_layout).setVisibility(View.VISIBLE);
			
		} else {
			findViewById(R.id.praise_layout).setVisibility(View.GONE);

		}
      
		boolean autoLoad = (bWIFI || ((NewsApp) mContext.getApplicationContext())
		      .getPrefsManager().isAutoLoadingPictureEnabled());

      mHtml = processContent(html, size, autoLoad);
		mWebView.loadDataWithBaseURL("about:blank", mHtml, MIMETYPE, ENCODING,
				"");
	}

	public void displayTop() {

		mWebView.scrollTo(getScrollX(), 0);
	}
	
	public boolean isDisplayTop () {
		if (mWebView.getScrollY() <= 0) {
			return true;
		} else {
			return false;
		}
	}

	
	private class PraiseTask extends NewsResultAsyncTask<Void, Void, NewsResult> {

		public PraiseTask(Context context) {
         super(context);
         // TODO Auto-generated constructor stub
      }

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
		public void onPostExecute(NewsResult result) {
			int msgId = -1;
			if (!((Activity) mContext).isFinishing()) {
				if (result.isSuccess()){
					msgId = R.string.praise;
				}
				if (msgId > 0) {
					Toast.makeText(mContext, msgId, Toast.LENGTH_SHORT).show();
				}
			}
		}

	}

}