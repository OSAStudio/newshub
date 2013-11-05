package com.osastudio.newshub.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

import com.osastudio.newshub.data.NewsResult;
import com.osastudio.newshub.data.RegisterResult;
import com.osastudio.newshub.data.user.CityDistrictList;
import com.osastudio.newshub.data.user.CityList;
import com.osastudio.newshub.data.user.QualificationList;
import com.osastudio.newshub.data.user.RegisterParameters;
import com.osastudio.newshub.data.user.SchoolClasslist;
import com.osastudio.newshub.data.user.SchoolList;
import com.osastudio.newshub.data.user.SchoolTypeList;
import com.osastudio.newshub.data.user.SchoolYearlist;
import com.osastudio.newshub.data.user.UserInfoList;
import com.osastudio.newshub.data.user.UserList;
import com.osastudio.newshub.data.user.ValidateResult;
import com.osastudio.newshub.library.ChecksumHelper;

public class UserApi extends NewsBaseApi {

   private static final String KEY_BIRTHDAY = "birthday";
   private static final String KEY_CITY_ID = "cityID";
   private static final String KEY_CLASS_ID = "classID";
   private static final String KEY_DISTRICT_ID = "areaID";
   private static final String KEY_GENDER = "sex";
   private static final String KEY_QUALIFICATION = "xueli";
   private static final String KEY_SCHOOL_ID = "schoolID";
   private static final String KEY_SCHOOL_TYPE = "school_class";
   private static final String KEY_USER_NAME = "studentName";
   private static final String KEY_VALIDATE_CODE = "serial";
   private static final String KEY_PASSWORD = "pwd";
   private static final String KEY_YEAR_ID = "yearID";
   private static final String KEY_PHONE_NUMBER = "mobile";

   protected static String getUserListService(Context context) {
      return new StringBuilder(getWebServer(context)).append(
            "custom!getUserInfosByMobile.do").toString();
   }

   protected static String getUserInfoListService(Context context) {
      return new StringBuilder(getWebServer(context)).append(
            "custom!getUserInfoAndServiceInfoByMobile.do").toString();
   }

   protected static String validateService(Context context) {
      return new StringBuilder(getWebServer(context)).append(
            "serial!checkSerialByMobile.do").toString();
   }

   protected static String getValidateStatusService(Context context) {
      return new StringBuilder(getWebServer(context)).append(
            "custom!checkActiveUserByMobile.do").toString();
   }

   protected static String registerService(Context context) {
      return new StringBuilder(getWebServer(context)).append(
            "custom!submitUserInfoByMobile.do").toString();
   }

   protected static String addUserService(Context context) {
      return new StringBuilder(getWebServer(context)).append(
            "custom!addAnotherUserByMobile.do").toString();
   }

   protected static String getCityListService(Context context) {
      return new StringBuilder(getWebServer(context)).append(
            "custom!getCitysByMobile.do").toString();
   }

   protected static String getCityDistrictListService(Context context) {
      return new StringBuilder(getWebServer(context)).append(
            "custom!getAreasByMobile.do").toString();
   }

   protected static String getSchoolTypeListService(Context context) {
      return new StringBuilder(getWebServer(context)).append(
            "custom!getSchoolClassByMobile.do").toString();
   }

   protected static String getSchoolListService(Context context) {
      return new StringBuilder(getWebServer(context)).append(
            "custom!getSchoolsByMobile.do").toString();
   }

   protected static String getSchoolYearListService(Context context) {
      return new StringBuilder(getWebServer(context)).append(
            "custom!getYearsByMobile.do").toString();
   }

   protected static String getSchoolClassListService(Context context) {
      return new StringBuilder(getWebServer(context)).append(
            "custom!getClassesByMobile.do").toString();
   }

   protected static String getQualificationListService(Context context) {
      return new StringBuilder(getWebServer(context)).append(
            "custom!getXueliByMobile.do").toString();
   }

   /**
    * Get validate status of an device
    * 
    * @param context
    *           application context
    * @return validate result, including result code & description, or null if
    *         failed
    */
   public static ValidateResult getValidateStatus(Context context) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      JSONObject jsonObject = getJsonObject(context,
            getValidateStatusService(context), params);
      return (jsonObject != null) ? new ValidateResult(jsonObject) : null;
   }

   public static ValidateResult validate(Context context, String validateCode) {
      return validate(context, validateCode, "");
   }

   /**
    * Validate an device via released code from service provider
    * 
    * @param context
    *           application context
    * @param validateCode
    *           released validate code
    * @param password
    *           password to verify device
    * @return validate result, including result code & description, or null if
    *         failed
    */
   public static ValidateResult validate(Context context, String validateCode,
         String password) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      params.add(new BasicNameValuePair(KEY_PHONE_NUMBER, validateCode));
      params.add(new BasicNameValuePair(KEY_PASSWORD, password));
      JSONObject jsonObject = getJsonObject(context, validateService(context),
            params);
      return (jsonObject != null) ? new ValidateResult(jsonObject) : null;
   }

   /**
    * Get user account list bound to device
    * 
    * @param context
    *           application context
    * @return user list, or null if failed
    */
   public static UserList getUserList(Context context) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      JSONObject jsonObject = getJsonObject(context,
            getUserListService(context), params);
      return (jsonObject != null) ? new UserList(jsonObject) : null;
   }

   /**
    * Get user information list bound to device
    * 
    * @param context
    *           application context
    * @return user information list, or null if failed
    */
   public static UserInfoList getUserInfoList(Context context) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      JSONObject jsonObject = getJsonObject(context,
            getUserInfoListService(context), params);
      return (jsonObject != null) ? new UserInfoList(jsonObject) : null;
   }

   /**
    * Register user on remote server
    * 
    * @param context
    *           application context
    * @param register
    *           user's register information to be committed
    * @return register result, including result code & description, or null if
    *         failed
    */
   public static RegisterResult registerUser(Context context,
         RegisterParameters register) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_SCHOOL_ID, register.schoolId));
      params.add(new BasicNameValuePair(KEY_YEAR_ID, register.yearId));
      params.add(new BasicNameValuePair(KEY_CLASS_ID, register.classId));
      params.add(new BasicNameValuePair(KEY_USER_NAME, register.userName));
      params.add(new BasicNameValuePair(KEY_GENDER, register.gender));
      params.add(new BasicNameValuePair(KEY_BIRTHDAY, register.birthday));
      params.add(new BasicNameValuePair(KEY_QUALIFICATION,
            register.qualification));
      JSONObject jsonObject = getJsonObject(context, registerService(context),
            params);
      return (jsonObject != null) ? new RegisterResult(jsonObject) : null;
   }

   /**
    * Add multiple user bound to device
    * 
    * @param context
    *           application context
    * @param register
    *           user's register information to be committed
    * @return
    */
   public static NewsResult addUser(Context context, RegisterParameters register) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_SCHOOL_ID, register.schoolId));
      params.add(new BasicNameValuePair(KEY_YEAR_ID, register.yearId));
      params.add(new BasicNameValuePair(KEY_CLASS_ID, register.classId));
      params.add(new BasicNameValuePair(KEY_USER_NAME, register.userName));
      params.add(new BasicNameValuePair(KEY_GENDER, register.gender));
      params.add(new BasicNameValuePair(KEY_BIRTHDAY, register.birthday));
      JSONObject jsonObject = getJsonObject(context, addUserService(context),
            params);
      return (jsonObject != null) ? new NewsResult(jsonObject) : null;
   }

   /**
    * Get city list
    * 
    * @param context
    *           application context
    * @return city list, or null if failed
    */
   public static CityList getCityList(Context context) {
      JSONObject jsonObject = getJsonObject(context,
            getCityListService(context), null);
      return (jsonObject != null) ? new CityList(jsonObject) : null;
   }

   /**
    * Get district list of city by city id
    * 
    * @param context
    *           application context
    * @param cityId
    *           city identifier
    * @return district list of city, or null if failed
    */
   public static CityDistrictList getCityDistrictList(Context context,
         String cityId) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_CITY_ID, cityId));
      JSONObject jsonObject = getJsonObject(context,
            getCityDistrictListService(context), params);
      return (jsonObject != null) ? new CityDistrictList(jsonObject) : null;
   }

   /**
    * Get school types list
    * 
    * @param context
    *           application context
    * @return school types list, or null if failed
    */
   public static SchoolTypeList getSchoolTypeList(Context context) {
      JSONObject jsonObject = getJsonObject(context,
            getSchoolTypeListService(context), null);
      return (jsonObject != null) ? new SchoolTypeList(jsonObject) : null;
   }

   /**
    * Get school list of specified district by school type
    * 
    * @param context
    *           application context
    * @param districtId
    *           district identifier of city
    * @param schoolType
    *           school type
    * @return school list, or null if failed
    */
   public static SchoolList getSchoolList(Context context, String districtId,
         String schoolType) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DISTRICT_ID, districtId));
      params.add(new BasicNameValuePair(KEY_SCHOOL_TYPE, schoolType));
      JSONObject jsonObject = getJsonObject(context,
            getSchoolListService(context), params);
      return (jsonObject != null) ? new SchoolList(jsonObject) : null;
   }

   /**
    * Get enrollment year list of class by school id
    * 
    * @param context
    *           application context
    * @param schoolId
    *           school identifier
    * @return enrollment year list, or null if failed
    */
   public static SchoolYearlist getSchoolYearList(Context context,
         String schoolId) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_SCHOOL_ID, schoolId));
      JSONObject jsonObject = getJsonObject(context,
            getSchoolYearListService(context), params);
      return (jsonObject != null) ? new SchoolYearlist(jsonObject) : null;
   }

   /**
    * Get class list of specified school by enrollment year
    * 
    * @param context
    *           application context
    * @param yearId
    *           specified enrollment year of class
    * @return class list, or null if failed
    */
   public static SchoolClasslist getSchoolClassList(Context context,
         String yearId) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_YEAR_ID, yearId));
      JSONObject jsonObject = getJsonObject(context,
            getSchoolClassListService(context), params);
      return (jsonObject != null) ? new SchoolClasslist(jsonObject) : null;
   }

   /**
    * Get qualification list
    * 
    * @param context
    *           application context
    * @return qualification list, or null if failed
    */
   public static QualificationList getQualificationList(Context context) {
      JSONObject jsonObject = getJsonObject(context,
            getQualificationListService(context), null);
      return (jsonObject != null) ? new QualificationList(jsonObject) : null;
   }

}
