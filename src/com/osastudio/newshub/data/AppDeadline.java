package com.osastudio.newshub.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppDeadline {

   public static boolean isExpired() {
      try {
         return new Date(System.currentTimeMillis())
               .after(new SimpleDateFormat("yyyy-MM-dd").parse("2013-11-31"));
      } catch (ParseException e) {
         // e.printStackTrace();
      }
      return false;
   }

}
