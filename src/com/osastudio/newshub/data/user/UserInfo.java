package com.osastudio.newshub.data.user;

import org.json.JSONException;
import org.json.JSONObject;

import com.osastudio.newshub.data.NewsBaseObject;

public class UserInfo extends NewsBaseObject {

   public static final String JSON_KEY_SERVICE_TYPE = "custom_type";
   public static final String JSON_KEY_SERVICE_VALIDATED_TIME = "active_date";

   private User user;
   private String serviceType;
   private String serviceValidatedTime;

   public UserInfo() {

   }

   public User getUser() {
      return this.user;
   }

   public UserInfo setUser(User user) {
      this.user = user;
      return this;
   }

   public String getBirthday() {
      return (this.user != null) ? this.user.getBirthday() : null;
   }

   public UserInfo setBirthday(String birthday) {
      if (this.user == null) {
         this.user = new User();
      }
      this.user.setBirthday(birthday);
      return this;
   }

   public String getClassName() {
      return (this.user != null) ? this.user.getClassName() : null;
   }

   public UserInfo setClassName(String className) {
      if (this.user == null) {
         this.user = new User();
      }
      this.user.setClassName(className);
      return this;
   }

   public String getGender() {
      return (this.user != null) ? this.user.getGender() : null;
   }

   public UserInfo setGender(String gender) {
      if (this.user == null) {
         this.user = new User();
      }
      this.user.setGender(gender);
      return this;
   }

   public boolean isFemale() {
      return (this.user != null) ? this.user.isFemale() : false;
   }

   public boolean isMale() {
      return (this.user != null) ? this.user.isMale() : false;
   }

   public String getSchoolName() {
      return (this.user != null) ? this.user.getSchoolName() : null;
   }

   public UserInfo setSchoolName(String schoolName) {
      if (this.user == null) {
         this.user = new User();
      }
      this.user.setSchoolName(schoolName);
      return this;
   }

   public String getServiceExpiredTime() {
      return (this.user != null) ? this.user.getServiceExpiredTime() : null;
   }

   public UserInfo setServiceExpiredTime(String serviceExpiredTime) {
      if (this.user == null) {
         this.user = new User();
      }
      this.user.setServiceExpiredTime(serviceExpiredTime);
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
      return (this.user != null) ? this.user.getUserName() : null;
   }

   public UserInfo setUserName(String userName) {
      if (this.user == null) {
         this.user = new User();
      }
      this.user.setUserName(userName);
      return this;
   }

   public String getYearName() {
      return (this.user != null) ? this.user.getYearName() : null;
   }

   public UserInfo setYearName(String yearName) {
      if (this.user == null) {
         this.user = new User();
      }
      this.user.setYearName(yearName);
      return this;
   }

   public static UserInfo parseJsonObject(JSONObject jsonObject) {
      UserInfo result = new UserInfo();
      try {
         if (!jsonObject.isNull(JSON_KEY_SERVICE_TYPE)) {
            result.setServiceType(jsonObject.getString(JSON_KEY_SERVICE_TYPE)
                  .trim());
         }
         if (!jsonObject.isNull(JSON_KEY_SERVICE_VALIDATED_TIME)) {
            result.setServiceValidatedTime(jsonObject.getString(
                  JSON_KEY_SERVICE_VALIDATED_TIME).trim());
         }
         result.setUser(User.parseJsonObject(jsonObject));
      } catch (JSONException e) {

      }
      return result;
   }

}
