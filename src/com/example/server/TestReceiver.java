package com.example.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
//开机自启服务，BroadcastReceiver，监听手机启动完成的事件ACTION_BOOT_COMPLETED即可
public class TestReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		Intent intent =  new Intent(arg0,  com.example.server.MoniteringServer.class);
		Intent intent1 =  new Intent(arg0,  com.example.server.ServicesServer.class);
		arg0.startService(intent);
		arg0.startService(intent1);
	}
	
	public void isServiceRunning(){
		
	}

}
