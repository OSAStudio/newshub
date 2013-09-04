package com.osastudio.newshub.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

import com.osastudio.newshub.data.FeedbackTypeList;
import com.osastudio.newshub.data.NewsResult;

public class FeedbackApi extends NewsBaseApi {

   private static final String TAG = "FeedbackApi";

   private static final String KEY_FEEDBACK_TYPE_ID = "problemTypeID";
   private static final String KEY_FEEDBACK_CONTENT = "feedbackContext";

   public static FeedbackTypeList getFeedbackTypeList(Context context) {
      JSONObject jsonObject = getJsonObject(context,
            getFeedbackTypeListService(context), null);
      return (jsonObject != null) ? new FeedbackTypeList(jsonObject) : null;
   }

   public static NewsResult feedback(Context context, String feedbackTypeId,
         String feedbackContent) {
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair(KEY_DEVICE_ID, getDeviceId(context)));
      params.add(new BasicNameValuePair(KEY_FEEDBACK_TYPE_ID, feedbackTypeId));
      params.add(new BasicNameValuePair(KEY_FEEDBACK_CONTENT, feedbackContent));
      JSONObject jsonObject = getJsonObject(context, feedbackService(context),
            params);
      return (jsonObject != null) ? new NewsResult(jsonObject) : null;
   }

}
