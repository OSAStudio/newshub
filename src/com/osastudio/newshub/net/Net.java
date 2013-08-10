package com.osastudio.newshub.net;
/**
 * network tools
 */
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.huadi.azker_phone.R;
import com.osastudio.newshub.utils.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

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
	 * true  设备已经连接网络
	 * false 设备未连接网络
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
	 * @param url  网路服务器地址
	 * @return
	 * true   网路服务器开启
	 * false  网络服务器未开启
	 */
	private boolean NetIsOnLine(String path){
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
		HttpConnectionParams.setSoTimeout(httpParams, 10000);
		HttpClient client = new DefaultHttpClient(httpParams);
		HttpGet get = new HttpGet(path);
		HttpResponse response = null;
		try {
		    response = client.execute(get);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
		if(response.getStatusLine().getStatusCode()==200){
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * 判断服务器网络
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
			// TODO Auto-generated method stub
			if(!result.isNetflag()){
			   HandlerMessage(NetTipMessage_show, result.isNetflag());
			} else {
				HandlerMessage(NetIsOK, result.isNetflag());
			}
			super.onPostExecute(result);
		}
      
	}
	
	/**
	 * 传送检测信息
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
