package com.osastudio.newshub;

import com.osastudio.newshub.data.AppProperties;

interface INewsService {

   void downloadApk(in String apkUrl);
   
   boolean isDownloadingApk();
   
   void checkNewVersion();
   
   boolean isCheckingNewVersion();
   
   boolean hasNewVersion(in AppProperties properties, in boolean notifyIfNot);
   
   void checkAppDeadline();

   boolean hasExpired();
   
   void checkNewsMessage();
      
}
