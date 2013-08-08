package com.osastudio.newshub.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.huadi.azker_phone.R;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.util.Log;

public class Utils {

	private static final String APP_NAME = "NewsHub";

	private static final boolean DEBUG = true;

	final static public int NOTIFY_LIST_TYPE = 1;
	final static public int IMPORT_NOTIFY_TYPE = 2;
	final static public int EXPERT_LIST_TYPE = 3;
	final static public int IMPORT_EXPERT_TYPE = 4;
	final static public int USER_ISSUES_TYPE = 5;
	final static public int LESSON_LIST_TYPE = 6;
	final static public int DAILY_REMINDER_TYPE = 7;
	final static public int RECOMMEND_LIST_TYPE = 8;

	public synchronized static Bitmap loadBitmap(String pathName, int outputW,
			int outputH, int rotate) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		options.inScaled = false;
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		options.inDither = false;
		int width, height;
		Bitmap bmp = null;

		if (pathName == null || pathName.equals(""))
			return null;

		File file = new File(pathName);
		if (!file.exists())
			return null;

		options.inJustDecodeBounds = true;
		try {
			BitmapFactory.decodeFile(pathName, options);
		} catch (OutOfMemoryError e) {
			return null;

		}
		width = options.outWidth;
		height = options.outHeight;

		if (width == 0 || height == 0)
			return null;

		options.inDither = true;
		options.inJustDecodeBounds = false;

		if (outputW != 0 && outputH == 0) {
			outputH = height * outputW / width;
		} else if (outputW == 0 && outputH != 0) {
			outputW = width * outputH / height;
		}

		if (outputW != 0 && outputH != 0) {
			int ratio1 = 0;
			float ratioSrc = (float) width / (float) height;
			float ratioDes = (float) outputW / (float) outputH;
			if (ratioSrc < ratioDes && height > outputH) {
				outputW = outputH * width / height;
			}
			ratio1 = width / outputW;
			options.inSampleSize = ratio1;
		} else {
			options.inSampleSize = 1;
		}

		try {
			bmp = BitmapFactory.decodeFile(pathName, options);
		} catch (OutOfMemoryError e) {
			return null;
		}
		if (bmp == null) {
			return null;
		}

		// int orientation = utils.getPicOrientation(pathName);
		ExifInterface exif = null;
		int orientation = 0;
		try {
			exif = new ExifInterface(pathName);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (exif != null) {
			orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				orientation = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				orientation = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				orientation = 270;
				break;
			default:
				orientation = 0;
			}
		}
		// if(rotate != 0 && bmp.getWidth() > bmp.getHeight()) {
		// rotate = 90;
		// } else
		// rotate = 0;
		orientation += rotate;
		orientation %= 360;

		if (orientation != 0) {
			Matrix matrix = new Matrix();
			matrix.setRotate(orientation);

			Bitmap bmp2 = null;
			try {
				bmp2 = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(),
						bmp.getHeight(), matrix, false);
			} catch (OutOfMemoryError e) {
				// Log.e("ZiiPhoto","can not create bitmap, out of memory!");
			}
			if (bmp2 != null) {
				bmp.recycle();
				bmp = bmp2;
			}
		}

		int bh = bmp.getHeight();
		if (outputH > 0 && bh > outputH) {
			outputW = outputH * bmp.getWidth() / bmp.getHeight();
			Bitmap sbmp = null;
			try {
				sbmp = Bitmap.createBitmap(outputW, outputH,
						Bitmap.Config.ARGB_8888);
			} catch (OutOfMemoryError e) {
				Utils.logd("DecodeCache",
						"createStretchBitmap, OutOfMemoryError!");
			}
			if (sbmp != null) {
				Canvas canvas = new Canvas(sbmp);
				RectF dst = new RectF(0, 0, outputW, outputH);
				canvas.drawBitmap(bmp, null, dst, null);
				bmp.recycle();
				bmp = sbmp;
				canvas = null;
			}
		}

		return bmp;

	}

	public static Bitmap getBitmapFromUrl(String url) {
		Bitmap bmp = null;
		if (url != null) {
			URL newurl;
			try {
				newurl = new URL(convertUrl(url));

				try {
					bmp = BitmapFactory.decodeStream(newurl.openConnection()
							.getInputStream());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return bmp;

	}

	public static Bitmap getBitmapFromUrl(String url, int largeSize) {
		Bitmap bmp = null;
		if (url != null) {
			URL newurl;
			try {
				newurl = new URL(convertUrl(url));

				try {
					bmp = BitmapFactory.decodeStream(newurl.openConnection()
							.getInputStream());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (bmp != null && (bmp.getWidth() > largeSize || bmp.getHeight()>largeSize)) {
			int ow = bmp.getWidth();
			int oh = bmp.getHeight();

			if (ow > oh) {
				ow = largeSize;
				oh = ow * bmp.getWidth() / bmp.getHeight();
			} else {
				oh = largeSize;
				ow = oh * bmp.getHeight() / bmp.getWidth();
			}
			Bitmap sbmp = null;
			try {
				sbmp = Bitmap.createBitmap(ow, oh, Bitmap.Config.ARGB_8888);
			} catch (OutOfMemoryError e) {
				Utils.logd("DecodeCache",
						"createStretchBitmap, OutOfMemoryError!");
			}
			if (sbmp != null) {
				Canvas canvas = new Canvas(sbmp);
				RectF dst = new RectF(0, 0, ow, oh);
				canvas.drawBitmap(bmp, null, dst, null);
				bmp.recycle();
				bmp = sbmp;
				canvas = null;
			}
		}

		return bmp;

	}

	public static String convertUrl(String title) {
		title = title.replace("%", "%25");
		title = title.replace(" ", "%20");
		title = title.replace("!", "%21");
		// title = title.replace("\"", "%22");
		title = title.replace("#", "%23");
		title = title.replace("$", "%24");

		title = title.replace("&", "%26");
		title = title.replace(";", "%3b");
		title = title.replace("[", "%5b");
		title = title.replace("]", "%5d");
		return title;
	}

	public static ProgressDialog showProgressDlg(Context context, String msg) {
		ProgressDialog dlg = null;
		if (msg == null) {
			msg = context.getString(R.string.wait);
		}
		try {
			dlg = new ProgressDialog(context);
			dlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dlg.setMax(100);
			dlg.setMessage(msg);
			dlg.setCancelable(false);
			dlg.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dlg;
	}

	public static void closeProgressDlg(ProgressDialog dlg) {
		if (dlg != null) {
			dlg.dismiss();
		}
	}

	public static void ShowConfirmDialog(Context context, String msg,
			final DialogConfirmCallback cb) {
		new AlertDialog.Builder(context)
				.setMessage(msg)
				.setPositiveButton(R.string.confirm,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								if (cb != null) {
									cb.onConfirm(dialog);
								}

							}
						}).setCancelable(false).show();
	}

	public interface DialogConfirmCallback {
		void onConfirm(DialogInterface dialog);
	}

	public static String getErrorResultMsg(Context context, int recult_code) {
		String msg = null;
		int msgId = -1;
		switch (recult_code) {
		case 102:
		case 104:
		case 105:
			msgId = R.string.msg_key_error;
			break;
		case 103:
			msgId = R.string.msg_activate_error;
			break;
		case 106:
			msgId = R.string.msg_register_error;
			break;
		case 107:
			msgId = R.string.msg_error_deviceid;
			break;
		case 108:
			msgId = R.string.msg_no_pay;
			break;
		case 109:
			msgId = R.string.msg_add_account_error;
			break;
		case 110:
			msgId = R.string.msg_feedback_error;
			break;
		case 111:
			msgId = R.string.msg_no_authority;
			break;
		case 112:
			msgId = R.string.msg_accout_full;
			break;

		}
		if (msgId > 0) {
			msg = context.getString(msgId);
		}
		return msg;
	}
   
	public static PackageInfo getPackageInfo(Context context) {
	   try {
         PackageManager pm = context.getPackageManager();
         return pm.getPackageInfo(context.getPackageName(), 0);
      } catch (NameNotFoundException e) {
//         e.printStackTrace();
      }
      return null;
	}

	public static int getVersionCode(Context context) {
      return getPackageInfo(context).versionCode;
	}
   
	public static String getVersionName(Context context) {
      return getPackageInfo(context).versionName;
	}
   
	public static void log(String tag, String info) {
		logi(tag, info);
	}

	public static void logd(String tag, String info) {
		if (DEBUG) {
			Log.d(APP_NAME + ">>>>>>>>>>" + tag, "-------->" + info);
		}
	}

	public static void loge(String tag, String info) {
		if (DEBUG) {
			Log.e(APP_NAME + ">>>>>>>>>>" + tag, "-------->" + info);
		}
	}

	public static void logi(String tag, String info) {
		if (DEBUG) {
			Log.i(APP_NAME + ">>>>>>>>>>" + tag, "-------->" + info);
		}
	}

	public static void logv(String tag, String info) {
		if (DEBUG) {
			Log.v(APP_NAME + ">>>>>>>>>>" + tag, "-------->" + info);
		}
	}

	public static void logw(String tag, String info) {
		if (DEBUG) {
			Log.w(APP_NAME + ">>>>>>>>>>" + tag, "-------->" + info);
		}
	}

}
