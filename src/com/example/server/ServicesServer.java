package com.example.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.test1_19.InputStreamRunnable;
import com.example.test1_19.OtherUtil;
import com.example.util.MyLog;

import android.app.AlarmManager;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

public class ServicesServer extends Service {
	SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final int Gravity = 0;
	public void toast(String text){
		Toast toast = Toast.makeText(getApplicationContext(), text , Toast.LENGTH_SHORT);
		toast.setGravity(Gravity, 0, 0);
		toast.show();
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	


	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	

	int i= 0;
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Calendar collectTime = Calendar.getInstance();
		int minute = collectTime.get(Calendar.MINUTE);
		int seconds = minute * 60+ collectTime.get(Calendar.SECOND);  
		OtherUtil ou = new OtherUtil();
		try {
			Thread.sleep(1000);
			startService(new Intent(getApplicationContext(), ServicesServer.class)); 
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if( seconds==2460 ){
			try {
				Thread.sleep(30*1000);
				new PingTast().execute();
			} catch (InterruptedException e) {
			}
		}
		
		 return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	 class PingTast extends AsyncTask<Void, Void, Boolean>{
			String line ; 
			String s;
		    Process process;
		    int status;
		    
		    @Override
			protected Boolean doInBackground(Void... arg0) {
			try {
			//没有-c 4 -s 15会闪退
			 process = Runtime.getRuntime().exec("/system/bin/ping -c 4 -s 15 "+"47.92.100.250"); 
			InputStreamReader i = new InputStreamReader(process.getInputStream());
			LineNumberReader returndata = new LineNumberReader(i);
			StringBuffer sb =new  StringBuffer();
			String returnMsg="";
			while ((line = returndata.readLine())!= null) {    
			   returnMsg += line; 
			   sb.append(line+"\r\n");
			}
			s =sb.toString();
			try {
				//单独开辟线程的原因：避免造成程序阻塞，及强退，同时，优化ping的速度
				Thread t=new Thread(new InputStreamRunnable(process.getErrorStream(),"ErrorStream"));  
		        t.start();  
				 status =process.waitFor();

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
			}
			finally {
				 process.destroy();
			}
			
			if(status ==0){
				 return true;
			 }else{
				 return false;
			 }
			}
		    
		    @Override
		    protected void onPostExecute(Boolean result) {
		    	// TODO Auto-generated method stub
		    	super.onPostExecute(result);
		    	if(result==false){
		    		MyLog.write485("reboot:");
		    		new OtherUtil().reboot();
		    	}
		    		
		    }
		}
	
}
