package com.osastudio.newshub.net;
/**
 * network tools
 */
import org.json.JSONObject;

import com.osastudio.newshub.data.NewsResult;
import com.osastudio.newshub.utils.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class Net {
	private String httpPath ;
	private Context context = null;
	private static ConnectivityManager conn_Manager = null;
	public final static int NetIsOK = 1;
	public final static int NetTipMessage_show = 2;
	private Handler handler = null;
	public Net(Context context, Handler handler){
		this.context = context;
		this.handler = handler;
		conn_Manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
	}
	/**
	 * is network ok?
	 * true  �豸�Ѿ���������
	 * false �豸δ��������
	 * @return
	 */
	public  boolean PhoneIsOnLine(){
		if (conn_Manager != null) {
	         NetworkInfo networkInfo = conn_Manager.getActiveNetworkInfo();
	         if (networkInfo != null) {
	        	 Utils.log("PhoneIsOnLine networkInfo", "available="+networkInfo.isAvailable()+" Connected="+networkInfo.isConnected());
	             return networkInfo.isAvailable() && networkInfo.isConnected();
	         }
	      }
		return false;
	}
	/**
	 * is server ok
	 * @param url  ��·��������ַ
	 * @return
	 * true   ��·����������
	 * false  ���������δ����
	 */
   private boolean NetIsOnLine(String path) {
      JSONObject jsonObject = NewsBaseApi.getJsonObject(path, null,
            HttpMethod.HTTP_GET);
      return (jsonObject != null) ? new NewsResult(jsonObject).isSuccess()
            : false;
   }
	
	
	/**
	 * �жϷ���������
	 * @param HttpPath
	 */
	public void ExecutNetTask(String HttpPath){
		this.httpPath = HttpPath;
		new NetTask().execute();
	}
	
	static class NetResult {
		private boolean netflag ;

		public boolean isNetflag() {
			return netflag;
		}
	
		public void setNetflag(boolean netflag) {
			this.netflag = netflag;
		} 
	   
	}
	
	class NetTask extends AsyncTask<Void, Void, NetResult>{

		@Override
		protected NetResult doInBackground(Void... params) {
			boolean flag = NetIsOnLine(httpPath);
			NetResult result = new NetResult();
			result.setNetflag(flag);
			return result;
		}

		@Override
		protected void onPostExecute(NetResult result) {
			if(!result.isNetflag()){
			   HandlerMessage(NetTipMessage_show, result.isNetflag());
			} else {
				HandlerMessage(NetIsOK, result.isNetflag());
			}
			super.onPostExecute(result);
		}
      
	}
	
	/**
	 * ���ͼ����Ϣ
	 * @param index
	 * @param flag
	 */
	private void HandlerMessage(int index,boolean flag){
		Message message = new Message();
		message.what = index;
		message.obj = flag;
		handler.sendMessage(message);
	}
}
