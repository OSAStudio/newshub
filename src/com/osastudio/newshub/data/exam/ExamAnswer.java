package com.osastudio.newshub.data.exam;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.osastudio.library.json.JSONHelper;

public class ExamAnswer extends JSONHelper {

   public static final String JSON_KEY_EXAM_ID = "evaluation_id";
   public static final String JSON_KEY_QUESTIONS = "result_list";

   protected String examId;
   protected List<QuestionAnswer> questionAnswers;

   public ExamAnswer() {
      this.questionAnswers = new ArrayList<QuestionAnswer>();
   }

   public String getExamId() {
      return this.examId;
   }

   public void setExamId(String examId) {
      this.examId = examId;
   }

   public void addQuestionAnswer(QuestionAnswer questionResult) {
      this.questionAnswers.add(questionResult);
   }

   public List<QuestionAnswer> getQuestionAnswers() {
      return this.questionAnswers;
   }

   public void setQuestionAnswers(List<QuestionAnswer> questionResults) {
      this.questionAnswers = questionResults;
   }

   @Override
   public JSONObject toJSONObject() {
      JSONObject result = null;
      if (!TextUtils.isEmpty(this.examId)) {
         result = new JSONObject();
         try {
            result.put(JSON_KEY_EXAM_ID, this.examId);
            JSONArray array = new JSONArray();
            for (int i = 0; i < this.questionAnswers.size(); i++) {
               JSONObject obj = this.questionAnswers.get(i).toJSONObject();
               if (obj != null) {
                  array.put(obj);
               }
            }
         } catch (JSONException e) {

         }
      }
      return result;
   }

}
