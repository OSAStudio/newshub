package com.osastudio.newshub.library;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.UUID;

public class DeviceUuidFactory {

   protected static final String PREFS_FILE = "device_id";
   protected static final String PREFS_DEVICE_ID = "device_id";

   protected volatile static UUID uuid = null;
   protected volatile static String id = null;

   public DeviceUuidFactory(Context context) {
      if (uuid == null) {
         synchronized (DeviceUuidFactory.class) {
            if (uuid == null) {
               final SharedPreferences prefs = context.getSharedPreferences(
                     PREFS_FILE, 0);
               final String prefsId = prefs.getString(PREFS_DEVICE_ID, null);

               if (prefsId != null) {
                  // Use the ids previously computed and stored in the prefs
                  // file
                  id = prefsId;
//                  uuid = UUID.fromString(prefsId);
               } else {
                  String serialNumber = null;
                  try {
                     Class<?> cls = Class
                           .forName("android.os.SystemProperties");
                     Method get = cls.getMethod("get", String.class,
                           String.class);
                     serialNumber = (String) (get
                           .invoke(cls, "ro.serialno", ""));
                  } catch (Exception e) {
//                     e.printStackTrace();
                  }

                  if (!TextUtils.isEmpty(serialNumber)) {
                     try {
                        id = serialNumber;
                        uuid = UUID.nameUUIDFromBytes(serialNumber
                              .getBytes("utf8"));
                     } catch (UnsupportedEncodingException e) {
                        // e.printStackTrace();
                     }
                  } else {
                  /*
                  final String androidId = Secure.getString(
                        context.getContentResolver(), Secure.ANDROID_ID);

                  // Use the Android ID unless it's broken, in which case
                  // fallback on deviceId, unless it's not available, then
                  // fallback on a random number which we store to a prefs file
                  */
                  try {
//                     if (!"9774d56d682e549c".equals(androidId)) {
//                        uuid = UUID.nameUUIDFromBytes(androidId
//                              .getBytes("utf8"));
//                     } else {
                        final String deviceId = ((TelephonyManager) context
                              .getSystemService(Context.TELEPHONY_SERVICE))
                              .getDeviceId();
                        if (!TextUtils.isEmpty(deviceId)) {
                           id = deviceId;
                           uuid = UUID.nameUUIDFromBytes(deviceId
                                 .getBytes("utf8"));
                        }
//                        uuid = deviceId != null ? UUID
//                              .nameUUIDFromBytes(deviceId.getBytes("utf8"))
//                              : UUID.randomUUID();
//                     }
                  } catch (UnsupportedEncodingException e) {
                     // throw new RuntimeException(e);
                  }
                  }
                  // Write the value out to the prefs file
                  prefs.edit().putString(PREFS_DEVICE_ID, id).commit();
               }
            }
         }
      }
   }

   /**
    * Returns a unique UUID for the current android device. As with all UUIDs,
    * this unique ID is "very highly likely" to be unique across all Android
    * devices. Much more so than ANDROID_ID is.
    * 
    * The UUID is generated by using ANDROID_ID as the base key if appropriate,
    * falling back on TelephonyManager.getDeviceID() if ANDROID_ID is known to
    * be incorrect, and finally falling back on a random UUID that's persisted
    * to SharedPreferences if getDeviceID() does not return a usable value.
    * 
    * In some rare circumstances, this ID may change. In particular, if the
    * device is factory reset a new device ID may be generated. In addition, if
    * a user upgrades their phone from certain buggy implementations of Android
    * 2.2 to a newer, non-buggy version of Android, the device ID may change.
    * Or, if a user uninstalls your app on a device that has neither a proper
    * Android ID nor a Device ID, this ID may change on reinstallation.
    * 
    * Note that if the code falls back on using TelephonyManager.getDeviceId(),
    * the resulting ID will NOT change after a factory reset. Something to be
    * aware of.
    * 
    * Works around a bug in Android 2.2 for many devices when using ANDROID_ID
    * directly.
    * 
    * @see http://code.google.com/p/android/issues/detail?id=10603
    * 
    * @return a UUID that may be used to uniquely identify your device for most
    *         purposes.
    */
   public UUID getDeviceUuid() {
      try {
         if ((uuid == null) && !TextUtils.isEmpty(id)) {
            uuid = UUID.nameUUIDFromBytes(id.getBytes("utf8"));
         }
      } catch (UnsupportedEncodingException e) {
//         e.printStackTrace();
      }
      return uuid;
   }

   public String getDeviceId() {
      return id;
   }

}