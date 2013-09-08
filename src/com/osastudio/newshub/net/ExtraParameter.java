package com.osastudio.newshub.net;

public class ExtraParameter {
   
   public static final boolean DEFAULT_LOGGING_STATUS = true;
   public static final boolean DEFAULT_CHECK_CONNECTIVITY_STATUS = false;
   public static final boolean DEFAULT_USE_BACKUP_SERVER_STATUS = true;
   public static final int DEFAULT_RETRY_TIMES_ON_ERROR = 3;

   public boolean enableLogging = DEFAULT_LOGGING_STATUS;
   public boolean checkConnectivityOnly = DEFAULT_CHECK_CONNECTIVITY_STATUS;
   public boolean useBackupServerIfFailed = DEFAULT_USE_BACKUP_SERVER_STATUS;
   public int retryTimesOnError = DEFAULT_RETRY_TIMES_ON_ERROR;
   
}
