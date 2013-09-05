package com.osastudio.newshub.net;

public enum ServerType {
   
   DEFAULT,
   MAIN,
   BACKUP,
   AUTOMATIC;
   
   public static boolean isDefaultServer(ServerType type) {
      return DEFAULT.compareTo(type) == 0;
   }

   public static boolean isMainServer(ServerType type) {
      return MAIN.compareTo(type) == 0;
   }
   
   public static boolean isBackupServer(ServerType type) {
      return BACKUP.compareTo(type) == 0;
   }
   
   public static boolean isAutomatic(ServerType type) {
      return AUTOMATIC.compareTo(type) == 0;
   }
   
}
