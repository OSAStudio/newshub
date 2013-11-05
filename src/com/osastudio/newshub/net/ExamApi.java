package com.osastudio.newshub.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.osastudio.newshub.data.NewsResult;
import com.osastudio.newshub.data.exam.Exam;
import com.osastudio.newshub.data.exam.ExamAnswer;
import com.osastudio.newshub.data.exam.ExamInfo;
import com.osastudio.newshub.data.exam.ExamReport;
import com.osastudio.newshub.utils.Utils;

import android.content.Context;
import android.text.TextUtils;

public class ExamApi extends NewsBaseApi {

   private static final String KEY_EXAM_ID = "serviceID";
   private static final String KEY_EXAM_REPORT_ID = "resultID";
   private static final String KEY_EXAM_ANSWER = "evaluationStr";

   protected static String getExamInfoService(Context context) {
      return new StringBuilder(getWebServer(context)).append(
            "evaluation!getEvaluationHomePageByMobile.do").toString();
   }

   protected static String getExamService(Context context) {
      return new StringBuilder(getWebServer(context)).append(
            "evaluation!getEvaluationContentByMobile.do").toString();
   }

   protected static String commitExamAnswerService(Context context) {
      return new StringBuilder(getWebServer(context)).append(
            "evaluation!submitEvaluationResultByMobile.do").toString();
   }

   /**
    * Get exam information of specified exam id & user id
    * 
    * @param context
    *           application context
    * @param userId
    *           user identifier
    * @param examId
    *           exam identifier
    * @return exam information, or null if failed
    */
   public static ExamInfo getExamInfo(Context context, String userId,
         String examId) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      params.add(new BasicNameValuePair(KEY_EXAM_ID, examId));
      JSONObject jsonObject = getJsonObject(context,
            getExamInfoService(context), params);
      return (jsonObject != null) ? new ExamInfo(jsonObject) : null;
   }

   /**
    * Get exam content of specified exam id & user id
    * 
    * @param context
    *           application context
    * @param userId
    *           user identifier
    * @param examId
    *           exam identifier
    * @return exam content, or null if failed
    */
   public static Exam getExam(Context context, String userId, String examId) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      params.add(new BasicNameValuePair(KEY_EXAM_ID, examId));
      JSONObject jsonObject = getJsonObject(context, getExamService(context),
            params);
      return (jsonObject != null) ? new Exam(jsonObject) : null;
   }

   public static ExamReport commitExamAnswer(Context context, String userId,
         ExamAnswer examAnswer) {
      return commitExamAnswer(context, userId, examAnswer, null);
   }

   /**
    * Commit exam answer to server, exam report will be returned
    * 
    * @param context
    *           application context
    * @param userId
    *           user identifier
    * @param examAnswer
    *           answers of an exam, including answers to all questions
    * @param reportId
    *           report identifier if user has committed before
    * @return exam report according to user answers, or null if failed
    */
   public static ExamReport commitExamAnswer(Context context, String userId,
         ExamAnswer examAnswer, String reportId) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      params.add(new BasicNameValuePair(KEY_EXAM_ID, examAnswer.getExamId()));
      if (!TextUtils.isEmpty(reportId)) {
         params.add(new BasicNameValuePair(KEY_EXAM_REPORT_ID, reportId));
      }
      params.add(new BasicNameValuePair(KEY_EXAM_ANSWER, examAnswer
            .toJSONString()));
      JSONObject jsonObject = getJsonObject(context,
            commitExamAnswerService(context), params);
      return (jsonObject != null) ? new ExamReport(jsonObject) : null;
   }

}
