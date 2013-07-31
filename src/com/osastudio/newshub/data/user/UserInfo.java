package com.osastudio.newshub.data.user;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.NewsBaseObject;

public class UserInfo extends NewsBaseObject {

   public static final String FEMALE_NAME = "Å®";
   public static final String MALE_NAME = "ÄÐ";

   public static final String JSON_KEY_BIRTHDAY = "birthday";
   public static final String JSON_KEY_CLASS_NAME = "class_name";
   public static final String JSON_KEY_GENDER = "sex";
   public static final String JSON_KEY_SCHOOL_NAME = "school_name";
   public static final String JSON_KEY_SERVICE_EXPIRED_TIME = "end_date";
   public static final String JSON_KEY_SERVICE_TYPE = "custom_type";
   public static final String JSON_KEY_SERVICE_VALIDATED_TIME = "active_date";
   public static final String JSON_KEY_USER_NAME = "student_name";
   public static final String JSON_KEY_YEAR_NAME = "year_name";

   private String birthday;
   private String className;
   private String gender;
   private String schoolName;
   private String serviceExpiredTime;
   private String serviceType;
   private String serviceValidatedTime;
   private String userName;
   private String yearName;

   public UserInfo() {

   }

   public String getBirthday() {
      return this.birthday;
   }

   public UserInfo setBirthday(String birthday) {
      this.birthday = birthday;
      return this;
   }

   public String getClassName() {
      return this.className;
   }

   public UserInfo setClassName(String className) {
      this.className = className;
      return this;
   }

   public String getGender() {
      return this.gender;
   }

   public UserInfo setGender(String gender) {
      this.gender = gender;
      return this;
   }

   public boolean isFemale() {
      return FEMALE_NAME.equals(this.gender);
   }

   public boolean isMale() {
      return MALE_NAME.equals(this.gender);
   }

   public String getSchoolName() {
      return this.schoolName;
   }

   public UserInfo setSchoolName(String schoolName) {
      this.schoolName = schoolName;
      return this;
   }

   public String getServiceExpiredTime() {
      return this.serviceExpiredTime;
   }

   public UserInfo setServiceExpiredTime(String serviceExpiredTime) {
      this.serviceExpiredTime = serviceExpiredTime;
      return this;
   }

   public String getServiceType() {
      return this.serviceType;
   }

   public UserInfo setServiceType(String serviceType) {
      this.serviceType = serviceType;
      return this;
   }

   public String getServiceValidatedTime() {
      return this.serviceValidatedTime;
   }

   public UserInfo setServiceValidatedTime(String serviceValidatedTime) {
      this.serviceValidatedTime = serviceValidatedTime;
      return this;
   }

   public String getUserName() {
      return this.userName;
   }

   public UserInfo setUserName(String userName) {
      this.userName = userName;
      return this;
   }

   public String getYearName() {
      return this.yearName;
   }

   public UserInfo setYearName(String yearName) {
      this.yearName = yearName;
      return this;
   }

   public static UserInfo parseJsonObject(JSONObject jsonObject) {
      UserInfo result = new UserInfo();
      try {
         if (!jsonObject.isNull(JSON_KEY_BIRTHDAY)) {
            result.setBirthday(jsonObject.getString(JSON_KEY_BIRTHDAY).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_CLASS_NAME)) {
            result.setClassName(jsonObject.getString(JSON_KEY_CLASS_NAME)
                  .trim());
         }
         if (!jsonObject.isNull(JSON_KEY_GENDER)) {
            result.setGender(jsonObject.getString(JSON_KEY_GENDER).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_SCHOOL_NAME)) {
            result.setSchoolName(jsonObject.getString(JSON_KEY_SCHOOL_NAME)
                  .trim());
         }
         if (!jsonObject.isNull(JSON_KEY_SERVICE_EXPIRED_TIME)) {
            result.setServiceExpiredTime(jsonObject.getString(
                  JSON_KEY_SERVICE_EXPIRED_TIME).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_SERVICE_TYPE)) {
            result.setServiceType(jsonObject.getString(JSON_KEY_SERVICE_TYPE)
                  .trim());
         }
         if (!jsonObject.isNull(JSON_KEY_SERVICE_VALIDATED_TIME)) {
            result.setServiceValidatedTime(jsonObject.getString(
                  JSON_KEY_SERVICE_VALIDATED_TIME).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_USER_NAME)) {
            result.setUserName(jsonObject.getString(JSON_KEY_USER_NAME).trim());
         }
         if (!jsonObject.isNull(JSON_KEY_YEAR_NAME)) {
            result.setYearName(jsonObject.getString(JSON_KEY_YEAR_NAME).trim());
         }
      } catch (JSONException e) {

      }
      return result;
   }

}
