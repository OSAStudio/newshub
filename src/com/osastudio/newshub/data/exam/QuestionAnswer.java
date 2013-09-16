package com.osastudio.newshub.data.exam;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.osastudio.library.json.JSONHelper;

public class QuestionAnswer extends JSONHelper {

   public static final String JSON_KEY_QUESTION_ID = "quess_id";
   public static final String JSON_KEY_OPTIONS = "option_list";
   public static final String JSON_KEY_OPTION_ID = "option_id";

   protected String questionId;
   protected List<String> optionIds;

   public QuestionAnswer() {
      this.optionIds = new ArrayList<String>();
   }

   public String getQuestionId() {
      return this.questionId;
   }

   public void setQuestionId(String questionId) {
      this.questionId = questionId;
   }

   public List<String> getOptionIds() {
      return this.optionIds;
   }

   public void addOptionId(String optionId) {
      if (!this.optionIds.contains(optionId)) {
         this.optionIds.add(optionId);
      }
   }
   
   public void removeOptionId(String optionId) {
      this.optionIds.remove(optionId);
   }
   
   public void clearAllOptionIds() {
      this.optionIds.clear();
   }

   public void setOptionIds(List<String> optionIds) {
      this.optionIds = optionIds;
   }

   @Override
   public JSONObject toJSONObject() {
      JSONObject result = null;
      if (!TextUtils.isEmpty(this.questionId)) {
         result = new JSONObject();
         try {
            result.put(JSON_KEY_QUESTION_ID, this.questionId);
            JSONArray array = new JSONArray();
            for (int i = 0; i < this.optionIds.size(); i++) {
               JSONObject item = new JSONObject();
               item.put(JSON_KEY_OPTION_ID, this.optionIds.get(i));
               array.put(item);
            }
            result.put(JSON_KEY_OPTIONS, array);
         } catch (JSONException e) {
            
         }
      }
      return result;
   }

}
