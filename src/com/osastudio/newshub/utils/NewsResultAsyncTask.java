package com.osastudio.newshub.utils;

import com.osastudio.newshub.data.NewsResult;
import com.osastudio.newshub.data.ResultCode;

import android.content.Context;
import android.os.AsyncTask;

public abstract class NewsResultAsyncTask<Params, Progress, Result extends NewsResult>
      extends AsyncTask<Params, Progress, Result> {
   Context mContext = null;
   public NewsResultAsyncTask(Context context) {
      mContext = context;
   }
   
   @Override
   public void onPostExecute(Result result) {
      if (result != null && result.isFailure() && result.getResultCode() != 114) {
         String msg = Utils.getErrorResultMsg(mContext, result.getResultCode());
         if (msg != null) {
            Utils.ShowConfirmDialog(mContext, msg, null);
         }
      }
   }

}
