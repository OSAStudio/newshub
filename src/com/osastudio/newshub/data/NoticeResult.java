package com.osastudio.newshub.data;

import org.json.JSONObject;

import com.osastudio.newshub.data.NewsResult;

/**
 * Notice result for processing notice associated events
 * 
 * @author Rujin Xue
 * 
 */
public class NoticeResult extends NewsResult {

   public NoticeResult(JSONObject jsonObject) {
      super(jsonObject);
   }

   public boolean alreadyFeedback() {
      return this.resultCode == RESULT_NOTICE_ALREADY_FEEDBACK;
   }

}
