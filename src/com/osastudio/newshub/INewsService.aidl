package com.osastudio.newshub;

import com.osastudio.newshub.data.AppProperties;

interface INewsService {

   boolean isDownloadingApk();
   
   void downloadApk(String apkUrl);
   
   boolean isCheckingNewVersion();
      
   void checkNewVersion();
   
   boolean hasNewVersion(in AppProperties properties, in boolean notifyIfNoNewVersion);
   
   void checkAppDeadline();
   
   boolean hasExpired();

}
