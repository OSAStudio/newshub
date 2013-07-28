package com.osastudio.newshub.net;

public interface FileInterface {

   public interface FileDownloadListener {
      public abstract void onPreDownload(String url);
      public abstract void onDownloadProgressUpdate(String url, int progress);
      public abstract void onPostDownload(String url);
   }
   
   public interface FileUploadListener {
      public abstract void onPreUpload(String path);
      public abstract void onUploadProgressUpdate(String path, int progress);
      public abstract void onPostUpload(String path);
   }
   
}
