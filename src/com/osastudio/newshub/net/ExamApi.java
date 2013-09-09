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

import android.content.Context;

public class ExamApi extends NewsBaseApi {

   private static final String KEY_EXAM_ID = "serviceID";
   private static final String KEY_EXAM_ANSWER = "evaluationStr";

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

   public static NewsResult commitExamAnswer(Context context, String userId,
         ExamAnswer examAnswer) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_DEVICE_TYPE, getDeviceType()));
      params.add(new BasicNameValuePair(KEY_USER_ID, userId));
      params.add(new BasicNameValuePair(KEY_EXAM_ID, examAnswer.getExamId()));
      params.add(new BasicNameValuePair(KEY_EXAM_ANSWER, examAnswer
            .toJSONString()));
      JSONObject jsonObject = getJsonObject(context,
            commitExamAnswerService(context), params);
      return (jsonObject != null) ? new NewsResult(jsonObject) : null;
   }

}
