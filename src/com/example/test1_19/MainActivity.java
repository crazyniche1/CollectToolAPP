package com.example.test1_19;

import android.provider.Settings;
import android.widget.CompoundButton.OnCheckedChangeListener;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.example.server.MoniteringServer;
import com.zy.R;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemProperties;
import android.os.storage.OnObbStateChangeListener;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	

	private static final int Gravity = 0;
	private TextView textView1;
	private TextView textView2;
	private TextView textView3;
	private TextView textView4;
	private TextView textView9;
	private TextView textView10;
	private TextView textView11;
	private TextView textView12;
	private TextView textView14;
	private Button button1;
	private Button button2;
	private Button button3;
	private Button button4;
	private Button button5;
	private Button button6;
	private Button button7;
	private Button button8;
	String path = Environment.getExternalStorageDirectory()+ "/"+"App"+ "/";
	private TextView textView13;
	private Button button9;
	private Button button10;
	private Button button11;
	private Button button12;
	private Button button13;
	private Button button14;
	private TextView textView5;
	private TextView textView6;
	private TextView textView17;
	private TextView textView20;
	private Button button15;
	private TextView textView8;
	private Button switch1;
	private CompoundButton switch2;
	
	  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//程序入口启动服务
			startService(new Intent(getApplicationContext(), MoniteringServer.class));
		//today  1521600652
//		String time = String.valueOf(new Date().getTime()/1000);
		Date date = new Date();
		//设置的时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		Date time = null;
		try {
			time = sdf.parse("2018-05-21");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			toast("时间转化异常");
		}
		if(date.before( time )){
			console();
		}	else {
			ToastLong("该软件试用期已过");
			Intent intent = new Intent(getApplicationContext(), PassPage.class); 
			startActivity(intent);
		}	
	}
	public void console(){
		//宏电打开状态栏
//		switchKG();
		view();
		// 判断升级包
		click();
		//升级程序的跳转
		skip();
//		//检测是否安装
		Install();
		//choose file path
		qaz();
//		//mac
//		macAdress();
//		//图标
		xuanze1(1);
//		//wifi
		setWifi();
		SkipPath();
		xuanze1(7);
		mac();
	}
	
	
	public void mac(){
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				setWifi();
				textView1.setText(""+getLocalMacAddress());
			}
		});
		
	}
	
	public void xixi(){
		
		if(isAvilible("com.example.autorun")){
		}else{
		String apkName="AutoRun.apk";
		anzhuang(apkName);
		}
		
		if(isAvilible("org.mightyfrog.android.simplenotepad")){
		}else{
		String apkName1="jsb.apk";
		anzhuang(apkName1);
		}
		
		if(isAvilible("com.estrongs.android.pop")){
		}else{
			String apkName2="ES.apk";
			anzhuang(apkName2);
			
		}
		
		
		if(isAvilible("com.com645")){
		}else{
			
			String apkName3="fxPort.apk";
			anzhuang(apkName3);
		}
		
		if(isAvilible("com.dinotech")){
		}else{
			String apkName4="CityInData10.26fx01x.apk";
			anzhuang(apkName4);		
		}
		
	}
	public void anzhuang(String apkName){
		MainActivity mContext = MainActivity.this;
		Intent mIntent = new Intent(Intent.ACTION_VIEW);  
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
        mIntent.setDataAndType(Uri.parse("file://" + path+ apkName ),   
                "application/vnd.android.package-archive");  
        mContext.startActivity(mIntent);
	}
	//	mac
	private void macAdress(){
		
			textView13.setText(""+getLocalMacAddress());
		//第一个参数是字体大小单位，第二个参数是字号值
		textView13.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40); //单位最好使用SP
	}
	//判断app是否运行,方法有问题
	public  boolean isAppRunning(Context context, String packageName) {  
        boolean isAppRunning = false;  
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);  
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);  
        if (list.size() <= 0) {  
            return isAppRunning; 
        }  
        
        for (ActivityManager.RunningTaskInfo info : list) {  
            if (info.baseActivity.getPackageName().equals(packageName)) {  
            	isAppRunning= true;  
            }  
        }  
        return isAppRunning;  
    }  
	//判断服务是否运行
	public  boolean isServiceRunning(Context context, String serviceName) {  
        
        boolean isRunning = false;  
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);  
        List<RunningServiceInfo> lists = am.getRunningServices(30); 
        for (RunningServiceInfo info : lists) {//判断服务  
        	String infos = info.service.getClassName();
                if(infos.equals(serviceName)){  
                isRunning = true;  
            }  
        }  
        return isRunning; 
    }  
	
	 
	
	 private void CopyAppToSystem( ) throws IOException  
	    {  
	        Process process = Runtime.getRuntime().exec("su");  
	        toast("----su----");
	        DataOutputStream out = new DataOutputStream(process.getOutputStream());  
	        out.writeBytes("mount -o remount,rw -t yaffs2 /dev/block/mtdblock3 /system \n"); 
	        out.writeBytes("cat /system/jsb.apk >  /system/app/ \n");  
//	        copyAssetsFile(getApplicationContext(), "PackageName.txt", Environment.getRootDirectory().getPath() +"/");
	        out.writeBytes("exit\n");  
	        out.flush();  
	        try {  
	            process.waitFor();  
	            toast("root----成功");
	        } catch (InterruptedException e) {  
	            e.printStackTrace(); 
	            toast("root----失败");
	        }  
	    }  
	
	 public void setWifi() {  
		  WifiManager mWm = (WifiManager) MainActivity.this.getSystemService(Context.WIFI_SERVICE);
		 if(!mWm.isWifiEnabled()){
			 mWm.setWifiEnabled(true);  
		 }else{
			 mWm.setWifiEnabled(true); 
		 }
		 
		 
	    }  
	 //mac地址
	public  String getLocalMacAddress() {
		
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
//                toast("wlan0");
           /* String path = "sys/class/net/eth0/address";
            FileInputStream fis_name = new FileInputStream(path);
            byte[] buffer_name = new byte[8192];
            int byteCount_name = fis_name.read(buffer_name);
            if (byteCount_name > 0) {
                mac = new String(buffer_name, 0, byteCount_name, "utf-8");
            }

            toast("eth0");

            if (mac == null) {
                fis_name.close();
                return "";
            }
            fis_name.close();*/
        } catch (Exception io) {
            /*String path = "sys/class/net/wlan0/address";
            FileInputStream fis_name;
            try {
                fis_name = new FileInputStream(path);
                byte[] buffer_name = new byte[8192];
                int byteCount_name = fis_name.read(buffer_name);
                if (byteCount_name > 0) {
                    mac = new String(buffer_name, 0, byteCount_name, "utf-8");
                }
                fis_name.close();
                toast("wlan0");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/
        	toast("mac地址获取异常");
        	setWifi();
        }

        if (mac == null) {
            return "";
        } else {
            return mac.trim();
        }

    }
	
	private void view(){
		textView1 = (TextView)findViewById(R.id.textView1);
		textView2 = (TextView)findViewById(R.id.textView2);
		textView3 = (TextView)findViewById(R.id.textView3);
		textView4 = (TextView)findViewById(R.id.textView4);
		textView5 = (TextView)findViewById(R.id.textView5);
		textView6 = (TextView)findViewById(R.id.textView6);
		textView8 = (TextView)findViewById(R.id.textView8);
		textView9 = (TextView)findViewById(R.id.textView9);
		textView10 = (TextView)findViewById(R.id.textView10);
		textView11 = (TextView)findViewById(R.id.textView11);
		textView12 = (TextView)findViewById(R.id.textView12);
		textView13 = (TextView)findViewById(R.id.textView13);
		textView14 = (TextView)findViewById(R.id.textView14);
		textView17 = (TextView)findViewById(R.id.textView17);
		textView20 = (TextView)findViewById(R.id.textView20);
		button1 = (Button)findViewById(R.id.button1);
		button2 = (Button)findViewById(R.id.button2);
		button3 = (Button)findViewById(R.id.button3);
		button4 = (Button)findViewById(R.id.button4);
		button5 = (Button)findViewById(R.id.button5);
		button6 = (Button)findViewById(R.id.button6);
		button7 = (Button)findViewById(R.id.button7);
		button8 = (Button)findViewById(R.id.button8);
		button9 = (Button)findViewById(R.id.button9);
		button10 = (Button)findViewById(R.id.button10);
		button11 = (Button)findViewById(R.id.button11);
		button12 = (Button)findViewById(R.id.button12);
		button13 = (Button)findViewById(R.id.button13);
		button14 = (Button)findViewById(R.id.button14);
		button15 = (Button)findViewById(R.id.button15);
		
		
//		switch1 = (Switch)findViewById(R.id.switch1);
//		switch2 = (CompoundButton)findViewById(R.id.switch1);
//		switch2 = (ToggleButton)findViewById(R.id.switch1);
		
		
		
	}
	
	//switch,未达到效果
	public void switchKG(){
	 switch2.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
		  	    @Override  
	    public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {  
	        // TODO Auto-generated method stub  
	        if(isChecked){  
	            //选中
	        	toast("xuanz");
	        	
//	        	getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //显示状态栏
	        }else{  
	            //未选中  
	        	toast("weixuanzhogn");
//	        	getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏 
	        } 
	        } 
	    }); 
	}
	
	private void SkipPath(){
		button8.setVisibility(View.VISIBLE);
		button8.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				/*String pkg="com.softwinner.TvdFileManager";
				String cls="com.softwinner.TvdFileManager.MainUI";
				
				softskip(pkg, cls);*/
				
				
				
				
			}
		});
	}
	
    private void installApk(final Context mContext, final String path){  
        Builder mBuilder = new AlertDialog.Builder(mContext)  
        .setIcon(R.drawable.ic_launcher)  
        .setMessage("是否安装");  
        mBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {  
              
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                // TODO Auto-generated method stub  
                Intent mIntent = new Intent(Intent.ACTION_VIEW);  
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
                mIntent.setDataAndType(Uri.parse("file://" + path),   
                        "application/vnd.android.package-archive");  
                mContext.startActivity(mIntent);  
               /* try {         
//                    runApk(mContext, path, className);  
                	toast("yunxignapp");
                } catch (Exception e) {  
                    // TODO: handle exception  
                	toast("chuwentile");
                }  */
            }  
        });  
        mBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {  
              
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                // TODO Auto-generated method stub  
                dialog.dismiss();  
            }  
        });  
        mBuilder.show();  
    } 
	
	private void qaz(){
	
		button9.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				File file = new File(Environment.getExternalStorageDirectory() + "/"+"App" );
//				file.delete();Intent intent = new Intent("android.intent.action.hongdian.statusbar.write");
				Intent intent = new Intent("android.intent.action.hongdian.statusbar.write");
//				intent.putExtra("content", "{"status":"on"}");
				
				/*intent.putExtra("content","{status:off}" );
				sendBroadcast(intent);*/
				toast("执行成功");
				Intent intent2 = new Intent(MainActivity.this, MainActivityNew.class); 
				startActivity(intent2);
			};
		});
		button5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				File sdDir = Environment.getExternalStorageDirectory();
				//创建文件FxApp
				File file = new File(Environment.getExternalStorageDirectory() + "/"+"App" );
					
				toast("创建文件到"+file);
				if(!file.exists()){
					//复制文件
					file.mkdir();
					String path = file+ "/";
					copyAssetsFile(MainActivity.this, "AutoRun.apk", path);
					copyAssetsFile(MainActivity.this, "CityInData10.26fx01x.apk", path);
					copyAssetsFile(MainActivity.this, "ES.apk", path);
					copyAssetsFile(MainActivity.this, "fxPort.apk", path);
					copyAssetsFile(MainActivity.this, "jsb.apk", path);
					toast("	复制文件----完成");
					xixi();
				}else{
					xixi();
					toast("文件已存在");
					
				}
				
			}
		});
	}
	
	/**   
	 *  从assets目录中复制整个文件夹内容   
	 */     
	 private boolean copyAssetsFile(Context context, String fileName, String path) {  
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
	            toast("******开始写入******"+mFile);
	            while((i = mInputStream.read(mbyte)) > 0){  
	                mFileOutputStream.write(mbyte, 0, i);  
	            }  
	            mInputStream.close();  
	            mFileOutputStream.close(); 
	            toast("******写入成功******");
	            return true;  
	        } catch (IOException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace(); 
	            toast("******写入失败******");
	            System.out.println("第一个"+e);
	            return false;  
	        } catch (Exception e) {  
	            // TODO: handle exception  
	        	System.out.println("第二个"+e);
	            return false;  
	        }  
	    }  
	
	public void toast(String text){
		Toast toast = Toast.makeText(getApplicationContext(), text , Toast.LENGTH_SHORT);
		toast.setGravity(Gravity, 0, 0);
		toast.show();
	}
	public void ToastLong(String text){
		Toast toast = Toast.makeText(getApplicationContext(), text , Toast.LENGTH_LONG);
		toast.setGravity(Gravity, 0, 0);
		toast.show();
	}
	/**
	 * 其他版本信息请参考ReferenceText文本
	 */
	private void click(){
	button1.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			/*
			String phoneInfo = ", Product: " + android.os.Build.PRODUCT + "\n";  
			phoneInfo += ", CPU_ABI: " + android.os.Build.CPU_ABI + "\n";  
			phoneInfo += ", TAGS: " + android.os.Build.TAGS + "\n";  
			phoneInfo += ", VERSION_CODES.BASE: "+ android.os.Build.VERSION_CODES.BASE + "\n";  
			phoneInfo += ", MODEL: " + android.os.Build.MODEL + "\n";  
			phoneInfo += ", SDK: " + android.os.Build.VERSION.SDK + "\n";  
			phoneInfo += ", VERSION.RELEASE: " + android.os.Build.VERSION.RELEASE+ "\n";  
			phoneInfo += ", DEVICE: " + android.os.Build.DEVICE + "\n";  
			phoneInfo += ", DISPLAY: " + android.os.Build.DISPLAY + "\n";  
			phoneInfo += ", BRAND: " + android.os.Build.BRAND + "\n";  
			phoneInfo += ", BOARD: " + android.os.Build.BOARD + "\n";  
			phoneInfo += ", FINGERPRINT: " + android.os.Build.FINGERPRINT + "\n";  
			phoneInfo += ", ID: " + android.os.Build.ID + "\n";  
			phoneInfo += ", MANUFACTURER: " + android.os.Build.MANUFACTURER + "\n";  
			phoneInfo += ", USER: " + android.os.Build.USER + "\n";  
			phoneInfo += ", BOOTLOADER: " + android.os.Build.BOOTLOADER + "\n";  
			phoneInfo += ", HARDWARE: " + android.os.Build.HARDWARE + "\n";  
			phoneInfo += ", INCREMENTAL: " + android.os.Build.VERSION.INCREMENTAL+ "\n";  
			phoneInfo += ", CODENAME: " + android.os.Build.VERSION.CODENAME + "\n";  
			phoneInfo += ", SDK版本: " + android.os.Build.VERSION.SDK_INT + "\n";*/
//			String[] strs=phoneInfo2.split("_");
//			
			String phoneInfo2 = SystemProperties.get("gsm.version.baseband") .substring(0, 4)+ "\n";
			String phoneInfo4 = " " + android.os.Build.DISPLAY + "\n";  
//			phoneInfo2 += ", INCREMENTAL: " + android.os.Build.VERSION.INCREMENTAL+ "\n";
//			phoneInfo2 =  "---------下面是版本，上面信息看看就好--------------";
			String phoneInfo3 =android.os.Build.VERSION.INCREMENTAL; 
//			textView1.setText(phoneInfo);
			//根据版本号作对比
//一会需要修改			
			/*if(phoneInfo3.equals(android.os.Build.VERSION.INCREMENTAL)){
				phoneInfo2+="该集中器已升级";
			}else{
				phoneInfo2+="该集中器未升级";
			}*/
			textView6.setText(" " + phoneInfo2);
			textView5.setText(" " + phoneInfo4);
//			phonei = phoneInfo2.substring(0);
//			System.err.println();
//			textView2.setText(", 版本号: " + strs[0].toString());
			String phonei =phoneInfo2.substring(0,4);
			String Info = "记得选择：B301老4G OTA升级包"+ "\n";
			String Info1 = "记得选择：B301新4G OTA升级包"+ "\n";
			String Info2 = "该集中器不支持升级！！！请不要随意升级"+ "\n";
			if(phonei.equals("L710")){
				textView3.setText(Info);
//				textView3.setTextColor(Color.parseColor("FFFFFF")); 
				SkipPath();
				
			}else{
				if(phonei.equals("EC20")){
					textView3.setText(Info1);
					SkipPath();
				}else{
					textView3.setText(Info2);
//					textView3.setTextColor(Color.parseColor("FFFFFF")); 
					SkipPath();
				}
				
			}
			
		}
	});
	
	}
	/*
	 * 读取assets文本
	 */
	public  String readAssetsTxt(String fileName){
	        try {
	            //Return an AssetManager instance for your application's package
	            InputStream is = getAssets().open(fileName);
	            int size = is.available();
	            // Read the entire asset into a local byte buffer.
	            byte[] buffer = new byte[size];
	            is.read(buffer);
	            is.close();
	            // Convert the buffer into a string.
	            String text = new String(buffer, "UTF-8");
	            // Finally stick the string into the text view.
	           return text;
	        } catch (IOException e) {
	            // Should never happen!
//	            throw new RuntimeException(e);
	            e.printStackTrace();
	        }
	        return "读取错误，请检查文件名";
	    }
	//获取已安装的包名
	private String TextAppBackage(){
		//获取文本文件内容
		String string  =  readAssetsTxt("PackageName.txt");
//		textView4.setText(string);
		//截取软件name
		BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(string.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
        StringBuffer strbuf=new StringBuffer();  
        String line;
        
        try {
			while ( (line = br.readLine()) != null ) {    
			    if(!line.trim().equals("")){  
			    	//backage name
			    	int index = line.lastIndexOf(" ");
					line = line.substring(index+1);
					// soft name  index2 统计空格之前的String 个数
//					int index2 = line.indexOf(" ");
//					line2 = line.substring(0,index2);
												
					// line="<br>"+line;每行可以做加工 
			        //附加字符串
					if(isAvilible(line)){
						//system中已安装的software
						
//						toast(line2);
//						textView4.setText("anzhuang"+line2);
					}else{
						//system中未安装的software
//						toast("weianzhuang ");
						strbuf.append(line+ "\r\n");  
//						textView4.setText("weianzhuang");
					}
			    }  
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			toast("遍历字符串每行出错");
		} 
        //罗列需要安装的程序 textView4.setText("需要安装的程序:"+"\r\n"+s);
        //s 表示已安装software的集合
        String s =strbuf.toString();
        
//        textView3.setText(s);
//        textView4.setText(s1);
		return s;
	}
//	/未完成，方法有问题
	private String TextAppName() {
		//获取文本文件内容
		String string  =  readAssetsTxt("PackageName");
		//未安装程序的包名
		String string1  =  TextAppBackage();
		
		//截取软件name
		BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(string.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
        StringBuffer strbuf=new StringBuffer();  
        String line;    
        String line1;    
        try {
			while ( (line = br.readLine()) != null ) {    
			    if(!line.trim().equals("")){  
			    	//name
					int index = line.indexOf(" ");
					String a = line;
					line = line.substring(0,index);
					
					//backagename
					int index1 = a.lastIndexOf(" ");
					line1 = a.substring(index1+1);
					line1=line1+ "\r\n";
//					toast("空值哈哈哈哈"+string1+"*********"+line1);
//					log.i("空值哈哈哈哈"+string1);
					String tag="teshude";
					android.util.Log.i(tag, string1);
//					toast("空值哈哈哈哈"+line1);
			    	if(line1.equals(string1)){
			    		
			    		toast("空值"+line);
			    		toast("空值2"+line1);
			    		//空值
					// line="<br>"+line;每行可以做加工 
			        //换行的作用+ 遍历显示其他行
			    	}else{
			    		toast("line.equals(string1)");
			    		strbuf.append(line);
			    	}
			    }  
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			toast("遍历字符串每行出错");
		} 
        //罗列需要安装的程序 textView4.setText("需要安装的程序:"+"\r\n"+s);
        String s =strbuf.toString();
//        toast(""+s);
        textView3.setText("");	
		return s;
		
	}

	private boolean isAvilible( String packageName){ 
	      final PackageManager packageManager = getPackageManager();//获取packagemanager 
	      List< PackageInfo> pinfo = packageManager.getInstalledPackages(0);//获取所有已安装程序的包信息 
	      List<String> pName = new ArrayList<String>();//用于存储所有已安装程序的包名 
	     //从pinfo中将包名字逐一取出，压入pName list中 
	          if(pinfo != null){ 
	          for(int i = 0; i < pinfo.size(); i++){ 
	              String pn = pinfo.get(i).packageName; 
	              pName.add(pn); 
	          } 
	      } 
	      return pName.contains(packageName);//判断pName中是否有目标程序的包名，有TRUE，没有FALSE 
	} 
	private void xuanze( Button button){
		
		button.setVisibility(View.VISIBLE);
	}
	private void xuanze2( Button button){
		
		button.setVisibility(View.INVISIBLE);
	}
	
	private void xuanze1( int i){
		
		switch(i){
			case 1:
				//图标

				new Thread(new Runnable() { 
					String line ; 
					String s;
			           @Override  
			           public void run() {  
			        	   
				button6.setOnClickListener(new OnClickListener() {
					
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						/*
						String serviceName="com.cityindata.btsensor.UploadService";
						String serviceName1="com.cityindata.btsensor.Collect485Service";
						String serviceName2="com.dinotech";
						Context context = MainActivity.this;
						
						if(isAppRunning(context , serviceName2)){
							toast("采集程序正在运行");
						} else{
							toast("采集程序运行停止");
							String pkg="com.dinotech";
							String cls="com.cityindata.Splash";
							softskip(pkg, cls);
						}
						
						if(isServiceRunning(context , serviceName)){
							toast("上传服务正在运行");
//							textView14.setText("上传服务正在运行");
						} else{
							toast("上传服务运行停止");
//							textView14.setText("上传服务正在停止");
							String pkg="com.dinotech";
							String cls="com.cityindata.Splash";
							softskip(pkg, cls);
						}
						
						if(isServiceRunning(context , serviceName1)){
							toast("采集服务正在运行");
//							textView14.setText("采集服务正在运行");
						} else{
							toast("采集服务运行停止");
//							textView14.setText("采集服务正在停止");
							String pkg="com.dinotech";
							String cls="com.cityindata.Splash";
							softskip(pkg, cls);
						}*/
						//ping
						final Process process;
						try {
							//该ip为百度
							String address="47.92.100.250";
//							String command = Environment.getRootDirectory().getPath()+"/bin/ping -c 4 -s 128 " + address;
//							Process  process = Runtime.getRuntime().exec(command);
							//没有-c 4 -s 128 会闪退
						 process = Runtime.getRuntime().exec("/system/bin/ping -c 4 -s 15 "+ address); //61.135.169.121
//							  process = new ProcessBuilder()
//									       .command("/system/bin/ping"+" -c 4 -s 128 ",address)
//									        .redirectErrorStream(true)
//									       .start();
							InputStreamReader i = new InputStreamReader(process.getInputStream());
							LineNumberReader returndata = new LineNumberReader(i);
							StringBuffer sb =new  StringBuffer();
							String returnMsg="";
							   
							while ((line = returndata.readLine())!= null) {    
//							   System.out.println(line);    
							   returnMsg += line; 
							   sb.append(line+"\r\n");
							   
							}
							s =sb.toString();
//							textView12.setText(s+"\r\n"); 输出为ping 的详细内容
							try {
								//单独开辟线程的原因：避免造成程序阻塞，及强退，同时，优化ping的速度
								//执行阻塞问题解决
								 Thread t=new Thread(new InputStreamRunnable(process.getErrorStream(),"ErrorStream"));  
					              t.start();  
								int status =process.waitFor();
								if(status == 0){
									ToastLong("该集中器可正常联网");
									textView8.setText("\r\n"+"该集中器可以正常上网");
								}else{
									toast("该集中器无法联网");
									textView8.setText("\r\n"+"该集中器无法联网");
								}
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								toast("pingwaitException");
							}
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							toast("pingException");
						}
						finally {
//							final process.destroy();
					}

						
						
					
					}
				});
			           }  
			       }).start();  
				
			
				break;
			case 2:
				//aotu
				button12.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String apkName ="AutoRun.apk" ;
						
//						toast("已安装");
						
						installApk(MainActivity.this, path  + apkName );
						
					}
				});
				break;
			case 3:
				//采集
				button11.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						
						String apkName ="CityInData10.26fx01x.apk" ;
						
//						toast("dao  zheyibule ");
						installApk(MainActivity.this, path  + apkName );
//						button10.setVisibility(View.VISIBLE);
						
						
					}
				});
				
				break;
			case 4:
				//jsb
//				button6.setVisibility(View.VISIBLE);
				button13.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						
						String apkName ="jsb.apk" ;
						
						toast("dao  zheyibule ");
						installApk(MainActivity.this, path  + apkName );
						
						
					}
				});
				break;
			case 5:
//				es
//				button7.setVisibility(View.VISIBLE);
				button14.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						
						String apkName ="ES.apk" ;
						
						toast("dao  zheyibule ");
						installApk(MainActivity.this, path  + apkName );
						
						
					}
				});
				break;
			case 6:
				//串口
//				button8.setVisibility(View.VISIBLE);
				button15.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						
						String apkName ="fxPort.apk" ;
						
						toast("dao  zheyibule ");
						installApk(MainActivity.this, path  + apkName );
						
						
					}
				});
				break;
				case 7:
					button7.setOnClickListener(new OnClickListener(){
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							//copy  图标
							String path = Environment.getExternalStorageDirectory()  +"/";
							String fileName="bootanimation.zip";
							copyAssetsFile(getApplicationContext(), fileName, path);
						}
					});
					break;
		}
	}
	protected static int execRootCmdSilent(String paramString) {
        try {
            Process localProcess = Runtime.getRuntime().exec("su");
            Object localObject = localProcess.getOutputStream();
            DataOutputStream localDataOutputStream = new DataOutputStream(
                    (OutputStream) localObject);
            String str = String.valueOf(paramString);
            localObject = str + "\n";
            localDataOutputStream.writeBytes((String) localObject);
            localDataOutputStream.flush();
            localDataOutputStream.writeBytes("exit\n");
            localDataOutputStream.flush();
            localProcess.waitFor();
            int result = localProcess.exitValue();
            return (Integer) result;
        } catch (Exception localException) {
            localException.printStackTrace();
            return -1;
        }
    }
	protected static boolean haveRoot() {
		 
        int i = execRootCmdSilent("echo test"); // 通过执行测试命令来检测
        if (i != -1) {
            return true;
        }
        return false;
    }
	
	
	private void Install(){
		button4.setOnClickListener(new OnClickListener() {
			String yaz="已安装";
			String waz="未安装";
			@Override
			public void onClick(View v) {
			//	4采集
			if(isAvilible("com.dinotech")){
				textView9.setText(yaz);	
				xuanze2(button11);	
			}else{
				textView9.setText(waz);	
				xuanze(button11);
				xuanze1(3);
			}
				// 9aotu
			if(isAvilible("com.example.autorun")){
				textView11.setText(yaz);	
				xuanze2(button12);
					
				}else{
					textView11.setText(waz);
					xuanze(button12);
					xuanze1(2);
				}
			//10jsb
			if(isAvilible("org.mightyfrog.android.simplenotepad")){
				textView14.setText(yaz);	
				xuanze2(button13);	
				}else{
					textView14.setText(waz);
					xuanze(button13);
					xuanze1(4);
					
				}
			//11文件管理
			if(isAvilible("com.estrongs.android.pop")){
				textView17.setText(yaz);	
				xuanze2(button14);	
				}else{
					textView17.setText(waz);	
					xuanze(button14);
					xuanze1(5);
				}
			//12 风
			if(isAvilible("com.com645")){
				textView20.setText(yaz);	
				xuanze2(button15);	
				}else{
					textView20.setText(waz);
					xuanze(button15);
					xuanze1(6);
				}	
		        
				
				
			}
		});
	}
	private void softskip (String pkg,String cls){
		Intent intent=new Intent();  
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setComponent(new ComponentName(pkg, cls)); 
		startActivity(intent);
	}
	
	private void skip(){
		button3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String pkg="com.softwinner.update";
				String cls="com.softwinner.update.UpdateActivity";
				
				softskip(pkg, cls);
				
			}
		});
		
	}

}  
