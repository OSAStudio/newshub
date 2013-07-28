package com.osastudio.newshub.library;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;

public class ChecksumHelper {

   public static String toMD5(String value) {
      try {
         MessageDigest md = MessageDigest.getInstance("MD5");
         md.reset();
         md.update(value.getBytes());
         return toHexString(md.digest());
      } catch (NoSuchAlgorithmException e) {
      }
      return null;
   }

   public static String toSHA1(String value) {
      try {
         MessageDigest md = MessageDigest.getInstance("SHA1");
         md.reset();
         md.update(value.getBytes());
         return toHexString(md.digest());
      } catch (NoSuchAlgorithmException e) {
      }
      return null;
   }

   public static long toCRC32(String value) {
      CRC32 crc = new CRC32();
      crc.reset();
      crc.update(value.getBytes());
      return crc.getValue();
   }

   private static String toHexString(byte[] bytes) {
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < bytes.length; i++) {
         sb.append(Integer.toHexString(bytes[i] >> 4 & 0x0f)).append(
               Integer.toHexString(bytes[i] & 0x0f));
      }
      return sb.toString();
   }

}
