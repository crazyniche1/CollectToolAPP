package com.example.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.example.test1_19.InputStreamRunnable;
import com.example.util.MyLog;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MoniteringServer extends Service {

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
	
	//判断服务是否启动
	public boolean isServiceRunning(final String className){
		ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> info =  activityManager.getRunningServices(Integer.MAX_VALUE);
		if(info ==null || info.size()==0)
		return false;
		for(ActivityManager.RunningServiceInfo aInfo : info){
			if(className.equals(aInfo.service.getClassName()))
				return true;
		}
		return false;
	}


	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	

	int i= 0;
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Calendar collectTime = Calendar.getInstance();
		int minute = collectTime.get(Calendar.MINUTE);
		int seconds = minute * 60+ collectTime.get(Calendar.SECOND);   
		try {
			Thread.sleep(1000);
			startService(new Intent(getApplicationContext(), MoniteringServer.class)); 
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(seconds % 120 == 0 ){
//			MyLog.write485("2分钟："+2*60);
			String className="com.cityindata.btsensor.UploadService";
	//		String className1="com.example.server.ServicesServer";
//			MyLog.write485("该服务启动"+i+"次");
			if(!isServiceRunning(className) && i==0){
				new services().run();
//				MyLog.write485("该服务启动"+i+"次");
			}
		
		}
		//启动dingding
		/*if(seconds % 60 == 0 ){
			new dingding().run();
		}*/
		
		 return START_REDELIVER_INTENT;
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		MyLog.write485("onDestroy");
		super.onDestroy();
	}
class services extends Thread{
	@Override
	public void run() {
//		// TODO Auto-generated method stub
//		super.run();
		String pkg="com.dinotech";
		String cls="com.cityindata.Splash";
		openSoftware(pkg, cls);
		
	}
}
private void openSoftware(String pkg, String cls){
	Intent intent = new Intent();
	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	intent.setComponent(new ComponentName(pkg, cls));
	startActivity(intent);
}
	
class dingding extends Thread{
	@Override
	public void run() {
			SimpleDateFormat format  = new SimpleDateFormat("HH:mm:ss");
			Calendar time = Calendar.getInstance();
			String today = format.format(time.getTime());
			if(today.equals("12:10:00") || today.equals("18:02:00")){
				openDingDing();
			}
			MyLog.write485("dingding_kill");
			if(today.equals("12:15:00") || today.equals("18:05:00") || today.equals("09:53:01")){
				killprocess();
				
			}
			
	}
}
private void openDingDing(){
	String pkg="com.alibaba.android.rimet";
	String cls="com.alibaba.android.rimet.biz.SplashActivity";
	
	Intent intent=new Intent();  
	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	intent.setComponent(new ComponentName(pkg, cls));
	startActivity(intent);
}
//kill 自身
private void killprocess(){
	int pid = android.os.Process.myPid();
	String common = "kill -9 " +pid;
	try {
		Runtime.getRuntime().exec(common);
		MyLog.write485("执行common");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		MyLog.write485("kill process exception");
	}
}
}
