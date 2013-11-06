package com.osastudio.newshub;

import com.osastudio.newshub.data.AppProperties;

interface INewsService {

	// Download apk file from remote server with given url 
   void downloadApk(in String apkUrl);
   
   // Whether downloading apk is in progress
   boolean isDownloadingApk();
   
   // Check if application upgrade is available
   void checkNewVersion();
   
   // Whether checking application upgrade is in progress
   boolean isCheckingNewVersion();
   
   // Check if application upgrade is available according to current application properties
   boolean hasNewVersion(in AppProperties properties, in boolean notifyIfNot);
   
   void checkAppDeadline();

   boolean hasExpired();
   
   // Check and pull news message from remote server if necessary
   void checkNewsMessage();
      
}
