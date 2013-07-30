package com.osastudio.newshub.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


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
   
   public synchronized static Bitmap loadBitmap(String pathName, int outputW, int outputH, int rotate) {
	      BitmapFactory.Options options = new BitmapFactory.Options();
	      options.inJustDecodeBounds = true;
	      options.inScaled = false;
	      options.inPreferredConfig = Bitmap.Config.RGB_565;
	      options.inDither = false;
	      int width, height;
	      Bitmap bmp = null;
	      
	      if(pathName == null || pathName.equals(""))
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
	      
	      if (outputW != 0 && outputH == 0){
	         outputH = height * outputW / width;
	      } else if (outputW == 0 && outputH != 0) {
	         outputW = width * outputH / height;
	      }

	      if (outputW != 0 && outputH != 0) {
	         int ratio1 = 0;
	         float ratioSrc = (float)width / (float)height;
	         float ratioDes = (float)outputW / (float)outputH;
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

//	      int orientation =  utils.getPicOrientation(pathName);
	      ExifInterface exif = null;
	      int orientation = 0;
	      try {
	         exif = new ExifInterface(pathName);
	      } catch (IOException e1) {
	         // TODO Auto-generated catch block
	         e1.printStackTrace();
	      }
	      if (exif != null) {
	         orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
	         switch(orientation) {
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
//	      if(rotate != 0 && bmp.getWidth() > bmp.getHeight()) {
//	         rotate = 90;
//	      } else
//	         rotate = 0;
	      orientation += rotate;
	      orientation %= 360;
	      
	      if (orientation != 0) {
	         Matrix matrix = new Matrix();
	         matrix.setRotate(orientation);

	         Bitmap bmp2 = null;
	         try {
	            bmp2 = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, false);
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
	            sbmp = Bitmap.createBitmap(outputW, outputH, Bitmap.Config.RGB_565);
	         } catch (OutOfMemoryError e) {
	            Utils.logd("DecodeCache", "createStretchBitmap, OutOfMemoryError!");
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
