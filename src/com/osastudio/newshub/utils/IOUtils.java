package com.osastudio.newshub.utils;

import java.io.File;
import java.io.IOException;

import android.os.Environment;
import android.os.StatFs;

public final class IOUtils {
   
	public static final String ROOT_DIRECTORY = ".NewsHub";
	private static final String CACHE_DIRECTORY = "cache";
   
	public static final long RESERVED_STORAGE_SPACE = 1; //MB
	
	public static boolean hasExternalStorage() {
		return Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	public static File getExternalFile(String file) {
		return new File(Environment.getExternalStorageDirectory(), file);
	}

	private static File getRootDir() throws IOException {
		File dir = getExternalFile(ROOT_DIRECTORY);
		if (!dir.exists()) {
			dir.mkdirs();
			new File(dir, ".nomedia").createNewFile();
		}
		return dir;
	}

	public static File getCacheDir() throws IOException {
		File dir = new File(getRootDir(), CACHE_DIRECTORY);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}
   
   public static long gb2Bytes(int gb) {
      return (long) gb * 1024 * 1024 * 1024;
   }
   
   public static long mb2Bytes(int mb) {
      return (long) mb * 1024 * 1024;
   }
   
   public static long kb2Bytes(int kb) {
      return (long) kb * 1024;
   }
   
	public static boolean isInternalStorageAvailable() {
	   return Environment.MEDIA_MOUNTED.equals(
	         Environment.getExternalStorageState());
	}
	
	public static boolean hasEnoughInternalStorageSpace() {
	   return getAvailableInternalStorageSpace()
	         > RESERVED_STORAGE_SPACE * 1024 * 1024;
	}
	
	public static boolean hasInternalStorageSpace() {
	   return getAvailableInternalStorageSpace() > 0;
	}
	
	public static long getAvailableInternalStorageSpace() {
	   return getAvailableStorageSpace(
	         Environment.getExternalStorageDirectory().getPath());
	}
	
	public static boolean hasStorageSpace(String path) {
	   return getAvailableStorageSpace(path) > 0;
	}
	
	public static long getAvailableStorageSpace(String path) {
	   StatFs fs = new StatFs(path);
	   long blockSize = fs.getBlockSize();
	   long blocks = fs.getAvailableBlocks();
	   return (long) blockSize * blocks;
	}
	
}
