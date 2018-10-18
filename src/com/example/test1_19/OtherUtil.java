package com.example.test1_19;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.List;

import com.example.util.MyLog;

import android.app.AlarmManager;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.SystemProperties;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android_serialport_api.SerialPort;

public class OtherUtil {

	private static final int NETWORKCONNECTED = 0;
	private static Context context;
	public  String getLocalMacAddress(Context con) {
		// TODO Auto-generated method stub
		this.context = con;
        String mac = null;
        try {
        	String path = "sys/class/net/wlan0/address";
            FileInputStream fis_name;
                fis_name = new FileInputStream(path);
                byte[] buffer_name = new byte[8192];
                int byteCount_name = fis_name.read(buffer_name);
                if (byteCount_name > 0) {
                    mac = new String(buffer_name, 0, byteCount_name, "utf-8");
                }
                fis_name.close();
        } catch (Exception io) {
        	 WifiManager mWm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    		 if(!mWm.isWifiEnabled()){
    			 mWm.setWifiEnabled(true);  
    		 }else{
    			 mWm.setWifiEnabled(true); 
    		 }
        }

        return mac.trim();
    
	}
	
	
	public  void OpenserialPort(SerialPort mSerialPort,InputStream mInputStream,OutputStream mOutputStream){
		/** 
         * 打开串口 
         *@param device 串口设备文件 
         *@param baudrate 波特率，一般是9600 
         *@param parity 奇偶校验，0 None,1 Even , 2 Odd, 
         *@param dataBits 数据位，5 - 8 
         *@param stopBit 停止位，1 或 2 
         */  
			String prot  ="ttyS2";
			// TODO Auto-generated method stub
			try {
				 mSerialPort = new SerialPort(new File("/dev/" + prot), 9600,0, 8, 1);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	        mInputStream = mSerialPort.getInputStream();
	        mOutputStream = mSerialPort.getOutputStream();
	        Log.i("testzy", "打开串口");
		}
	
	public  void closeserial(SerialPort mSerialPort,InputStream mInputStream,OutputStream mOutputStream){
			
			if (mSerialPort != null) {
	            mSerialPort.close();
	        }
	        if (mInputStream != null) {
	            try {
	                mInputStream.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        if (mOutputStream != null) {
	            try {
	                mOutputStream.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
		}
		
	public  void receiveThread(InputStream mInputStream,StringBuilder sb,Handler handler ){
                    int size;
                    
                    try {
                        byte[] buffer = new byte[1024];
                        if (mInputStream == null)
                        	
                        	return;
                        size = mInputStream.read(buffer);
                        if (size > 0) {
                        	byte[] buffer1 = new byte[1024];
                        	for(int w = 0; w < size; w++)
                        	{
                        		if((buffer[w] >> 4 & 0x0F) < 10)
                        			buffer1[w*3] = (byte) ((buffer[w] >> 4 & 0x0F) + 0x30);
                        		else
                        			buffer1[w*3] = (byte) ((buffer[w] >> 4 & 0x0F) - 10 + 0x41);
                        		
                        		if((buffer[w] & 0x0F) < 10)
                        			buffer1[w*3 + 1] = (byte) ((buffer[w] & 0x0F) + 0x30);
                        		else
                        			buffer1[w*3 + 1] = (byte) ((buffer[w] & 0x0F) - 10 + 0x41);
                        		buffer1[w*3 + 2] = ',';
                        	}
                        	
                        	size = size * 3;
                        	
                             String recinfo = new String(buffer1, 0,
                                    size);
                             Log.i("zy", recinfo+"recinfo");
                            sb.append(recinfo+"\n");
                            handler.sendEmptyMessage(1);
                            
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
	}
	
	public  void synchronizationtime(Context con){
		this.context = con;
		  try {
			URL url = new URL("http://120.77.223.237:80");
			URLConnection uc = url.openConnection();
			long id = uc.getDate();
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(id);
			long millis = calendar.getTimeInMillis();
			AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
			am.setTime(millis);
			MyLog.write485("更改："+millis);
		} catch (Exception e) {
			Log.i("zy_url_exception:", e.toString());
		} 
	}
	
	public   void toast(String text,Context con){
		this.context = con;
		/*Toast toast = Toast.makeText(context, text , Toast.LENGTH_SHORT);
		toast.setGravity(0, 0, 0);
		toast.show();*/
		
		Object toast = Toast.makeText(con, text, 25);
		TextView v = (TextView) ((Toast) toast).getView().findViewById(android.R.id.message);
	   	v.setTextSize(25);
	   	((Toast) toast).setGravity(0, 0, 0);
	   	((Toast) toast).show();
	}
	
	public  void install(String apkName,Context con,String path){
		this.context = con;
		Intent mIntent = new Intent(Intent.ACTION_VIEW);  
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
        mIntent.setDataAndType(Uri.parse("file://" + path + apkName ),   
                "application/vnd.android.package-archive");  
        context.startActivity(mIntent);
}
	
	public  void showVersion( TextView textview, TextView textview1){
			
			String phoneInfo2 = SystemProperties.get("gsm.version.baseband") + "\n";
			String phoneInfo4 = " " + android.os.Build.DISPLAY + "\n";  
	//		String phoneInfo3 =android.os.Build.VERSION.INCREMENTAL; 
			textview.setText(" " + phoneInfo2);
			textview1.setText(" " + phoneInfo4);
		}
	/** 
     * 检测 响应某个意图的Activity 是否存在 
     * @param context 
     * @param intent 
     * @return 
     */  
	 public  boolean isIntentAvailable(Context context, Intent intent) {  
	        final PackageManager packageManager = context.getPackageManager();  
	        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,  
	                PackageManager.GET_ACTIVITIES);  
	        return list.size() > 0;  
	    }  
	 public  Intent openstatus(){
		 Intent intent = new Intent("android.intent.action.hongdian.statusbar.write");
		 intent.putExtra("content","{status:off}" );
//		 sendBroadcast(intent);
		 return intent;
	 }
/*	public Intent checkIntentAvailable(){
		 Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);  
	     intent.putExtra(SearchManager.QUERY, context.getTitle());  
		return intent;
		
	}*/
	 
	 public  boolean isDateTimeAuto(){
		 try {
			 return android.provider.Settings.Global.getInt(context.getContentResolver(),
             android.provider.Settings.Global.AUTO_TIME)> 0;
		 	} catch(SettingNotFoundException e){
		 		e.printStackTrace(); 
		 		return false;
		 	}
		 
	 }
	 
	 public  void setDateTimeAuto(Context context){
		 this.context= context;
		//自动确定时区
		 android.provider.Settings.Global.putInt(context.getContentResolver(),
         android.provider.Settings.Global.AUTO_TIME_ZONE,1);
		 //自动确定时间
		 android.provider.Settings.Global.putInt(context.getContentResolver(),
         android.provider.Settings.Global.AUTO_TIME,1);
	 }
//	 重启
	 public static int reboot() {  
	        int r = 0;  
	        try {  
	            Process process = Runtime.getRuntime().exec("su -c reboot");  
	            r = process.waitFor();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	            r = -1;  
	        } catch (InterruptedException e) {  
	            e.printStackTrace();  
	            r = -1;  
	        }  
	        return r;  
	    }  
//	 关机
	 public static int shutdown() {  
	        int r = 0;  
	        try {  
	            Process process = Runtime.getRuntime().exec(new String[]{"su" , "-c" ,"reboot -p"});  
	            r = process.waitFor();  
	            java.lang.System.out.println("r:" + r );  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	            r = -1;  
	        } catch (InterruptedException e) {  
	            e.printStackTrace();  
	            r = -1;  
	        }  
	        return r;  
	    }  
	
	 public  boolean copyAssetsFile(Context context, String fileName, String path) {  
	        // TODO Auto-generated method stub  
	  
		 try {  
	        	//getAssets的路径为：assets/
	        	
	            InputStream mInputStream = context.getAssets().open("app/"+fileName);  
	            File file = new File(path);    
	            if (!file.exists()) {    
	                file.mkdir();    
	            }
				File mFile = new File(path + fileName);  
	            if(!mFile.exists())  
	            mFile.createNewFile();  
	            FileOutputStream mFileOutputStream = new FileOutputStream(mFile);  
	            
	            byte[] mbyte = new byte[1024];  
	            
	            int i = 0;  
	            MyLog.write485("******开始写入******"+mFile);
	            while((i = mInputStream.read(mbyte)) > 0){  
	                mFileOutputStream.write(mbyte, 0, i);  
	            }  
	            mInputStream.close();  
	            mFileOutputStream.close(); 
	            MyLog.write485("******写入成功******");
	            return true;  
	        } catch (IOException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace(); 
	            MyLog.write485("******写入失败******");
	            return false;  
	        } catch (Exception e) {  
	            // TODO: handle exception  
	            return false;  
	        }  
	 }
	public void Dialog (){
		ProgressDialog pd = new ProgressDialog(context);
		pd.setTitle("提示：");
		pd.setMessage("正在查询网络状态-----");
		pd.setProgressStyle(NETWORKCONNECTED);
		pd.setCancelable(false);
	}
	

}
