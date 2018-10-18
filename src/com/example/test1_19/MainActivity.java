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
		
		//���������������
			startService(new Intent(getApplicationContext(), MoniteringServer.class));
		//today  1521600652
//		String time = String.valueOf(new Date().getTime()/1000);
		Date date = new Date();
		//���õ�ʱ��
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		Date time = null;
		try {
			time = sdf.parse("2018-05-21");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			toast("ʱ��ת���쳣");
		}
		if(date.before( time )){
			console();
		}	else {
			ToastLong("������������ѹ�");
			Intent intent = new Intent(getApplicationContext(), PassPage.class); 
			startActivity(intent);
		}	
	}
	public void console(){
		//����״̬��
//		switchKG();
		view();
		// �ж�������
		click();
		//�����������ת
		skip();
//		//����Ƿ�װ
		Install();
		//choose file path
		qaz();
//		//mac
//		macAdress();
//		//ͼ��
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
		//��һ�������������С��λ���ڶ����������ֺ�ֵ
		textView13.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40); //��λ���ʹ��SP
	}
	//�ж�app�Ƿ�����,����������
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
	//�жϷ����Ƿ�����
	public  boolean isServiceRunning(Context context, String serviceName) {  
        
        boolean isRunning = false;  
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);  
        List<RunningServiceInfo> lists = am.getRunningServices(30); 
        for (RunningServiceInfo info : lists) {//�жϷ���  
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
	            toast("root----�ɹ�");
	        } catch (InterruptedException e) {  
	            e.printStackTrace(); 
	            toast("root----ʧ��");
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
	 //mac��ַ
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
        	toast("mac��ַ��ȡ�쳣");
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
	
	//switch,δ�ﵽЧ��
	public void switchKG(){
	 switch2.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
		  	    @Override  
	    public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {  
	        // TODO Auto-generated method stub  
	        if(isChecked){  
	            //ѡ��
	        	toast("xuanz");
	        	
//	        	getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //��ʾ״̬��
	        }else{  
	            //δѡ��  
	        	toast("weixuanzhogn");
//	        	getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //����״̬�� 
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
        .setMessage("�Ƿ�װ");  
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
				toast("ִ�гɹ�");
				Intent intent2 = new Intent(MainActivity.this, MainActivityNew.class); 
				startActivity(intent2);
			};
		});
		button5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				File sdDir = Environment.getExternalStorageDirectory();
				//�����ļ�FxApp
				File file = new File(Environment.getExternalStorageDirectory() + "/"+"App" );
					
				toast("�����ļ���"+file);
				if(!file.exists()){
					//�����ļ�
					file.mkdir();
					String path = file+ "/";
					copyAssetsFile(MainActivity.this, "AutoRun.apk", path);
					copyAssetsFile(MainActivity.this, "CityInData10.26fx01x.apk", path);
					copyAssetsFile(MainActivity.this, "ES.apk", path);
					copyAssetsFile(MainActivity.this, "fxPort.apk", path);
					copyAssetsFile(MainActivity.this, "jsb.apk", path);
					toast("	�����ļ�----���");
					xixi();
				}else{
					xixi();
					toast("�ļ��Ѵ���");
					
				}
				
			}
		});
	}
	
	/**   
	 *  ��assetsĿ¼�и��������ļ�������   
	 */     
	 private boolean copyAssetsFile(Context context, String fileName, String path) {  
	        // TODO Auto-generated method stub  
	  
	        try {  
	        	//getAssets��·��Ϊ��assets/
	        	
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
	            toast("******��ʼд��******"+mFile);
	            while((i = mInputStream.read(mbyte)) > 0){  
	                mFileOutputStream.write(mbyte, 0, i);  
	            }  
	            mInputStream.close();  
	            mFileOutputStream.close(); 
	            toast("******д��ɹ�******");
	            return true;  
	        } catch (IOException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace(); 
	            toast("******д��ʧ��******");
	            System.out.println("��һ��"+e);
	            return false;  
	        } catch (Exception e) {  
	            // TODO: handle exception  
	        	System.out.println("�ڶ���"+e);
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
	 * �����汾��Ϣ��ο�ReferenceText�ı�
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
			phoneInfo += ", SDK�汾: " + android.os.Build.VERSION.SDK_INT + "\n";*/
//			String[] strs=phoneInfo2.split("_");
//			
			String phoneInfo2 = SystemProperties.get("gsm.version.baseband") .substring(0, 4)+ "\n";
			String phoneInfo4 = " " + android.os.Build.DISPLAY + "\n";  
//			phoneInfo2 += ", INCREMENTAL: " + android.os.Build.VERSION.INCREMENTAL+ "\n";
//			phoneInfo2 =  "---------�����ǰ汾��������Ϣ�����ͺ�--------------";
			String phoneInfo3 =android.os.Build.VERSION.INCREMENTAL; 
//			textView1.setText(phoneInfo);
			//���ݰ汾�����Ա�
//һ����Ҫ�޸�			
			/*if(phoneInfo3.equals(android.os.Build.VERSION.INCREMENTAL)){
				phoneInfo2+="�ü�����������";
			}else{
				phoneInfo2+="�ü�����δ����";
			}*/
			textView6.setText(" " + phoneInfo2);
			textView5.setText(" " + phoneInfo4);
//			phonei = phoneInfo2.substring(0);
//			System.err.println();
//			textView2.setText(", �汾��: " + strs[0].toString());
			String phonei =phoneInfo2.substring(0,4);
			String Info = "�ǵ�ѡ��B301��4G OTA������"+ "\n";
			String Info1 = "�ǵ�ѡ��B301��4G OTA������"+ "\n";
			String Info2 = "�ü�������֧�������������벻Ҫ��������"+ "\n";
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
	 * ��ȡassets�ı�
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
	        return "��ȡ���������ļ���";
	    }
	//��ȡ�Ѱ�װ�İ���
	private String TextAppBackage(){
		//��ȡ�ı��ļ�����
		String string  =  readAssetsTxt("PackageName.txt");
//		textView4.setText(string);
		//��ȡ���name
		BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(string.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
        StringBuffer strbuf=new StringBuffer();  
        String line;
        
        try {
			while ( (line = br.readLine()) != null ) {    
			    if(!line.trim().equals("")){  
			    	//backage name
			    	int index = line.lastIndexOf(" ");
					line = line.substring(index+1);
					// soft name  index2 ͳ�ƿո�֮ǰ��String ����
//					int index2 = line.indexOf(" ");
//					line2 = line.substring(0,index2);
												
					// line="<br>"+line;ÿ�п������ӹ� 
			        //�����ַ���
					if(isAvilible(line)){
						//system���Ѱ�װ��software
						
//						toast(line2);
//						textView4.setText("anzhuang"+line2);
					}else{
						//system��δ��װ��software
//						toast("weianzhuang ");
						strbuf.append(line+ "\r\n");  
//						textView4.setText("weianzhuang");
					}
			    }  
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			toast("�����ַ���ÿ�г���");
		} 
        //������Ҫ��װ�ĳ��� textView4.setText("��Ҫ��װ�ĳ���:"+"\r\n"+s);
        //s ��ʾ�Ѱ�װsoftware�ļ���
        String s =strbuf.toString();
        
//        textView3.setText(s);
//        textView4.setText(s1);
		return s;
	}
//	/δ��ɣ�����������
	private String TextAppName() {
		//��ȡ�ı��ļ�����
		String string  =  readAssetsTxt("PackageName");
		//δ��װ����İ���
		String string1  =  TextAppBackage();
		
		//��ȡ���name
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
//					toast("��ֵ��������"+string1+"*********"+line1);
//					log.i("��ֵ��������"+string1);
					String tag="teshude";
					android.util.Log.i(tag, string1);
//					toast("��ֵ��������"+line1);
			    	if(line1.equals(string1)){
			    		
			    		toast("��ֵ"+line);
			    		toast("��ֵ2"+line1);
			    		//��ֵ
					// line="<br>"+line;ÿ�п������ӹ� 
			        //���е�����+ ������ʾ������
			    	}else{
			    		toast("line.equals(string1)");
			    		strbuf.append(line);
			    	}
			    }  
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			toast("�����ַ���ÿ�г���");
		} 
        //������Ҫ��װ�ĳ��� textView4.setText("��Ҫ��װ�ĳ���:"+"\r\n"+s);
        String s =strbuf.toString();
//        toast(""+s);
        textView3.setText("");	
		return s;
		
	}

	private boolean isAvilible( String packageName){ 
	      final PackageManager packageManager = getPackageManager();//��ȡpackagemanager 
	      List< PackageInfo> pinfo = packageManager.getInstalledPackages(0);//��ȡ�����Ѱ�װ����İ���Ϣ 
	      List<String> pName = new ArrayList<String>();//���ڴ洢�����Ѱ�װ����İ��� 
	     //��pinfo�н���������һȡ����ѹ��pName list�� 
	          if(pinfo != null){ 
	          for(int i = 0; i < pinfo.size(); i++){ 
	              String pn = pinfo.get(i).packageName; 
	              pName.add(pn); 
	          } 
	      } 
	      return pName.contains(packageName);//�ж�pName���Ƿ���Ŀ�����İ�������TRUE��û��FALSE 
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
				//ͼ��

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
							toast("�ɼ�������������");
						} else{
							toast("�ɼ���������ֹͣ");
							String pkg="com.dinotech";
							String cls="com.cityindata.Splash";
							softskip(pkg, cls);
						}
						
						if(isServiceRunning(context , serviceName)){
							toast("�ϴ�������������");
//							textView14.setText("�ϴ�������������");
						} else{
							toast("�ϴ���������ֹͣ");
//							textView14.setText("�ϴ���������ֹͣ");
							String pkg="com.dinotech";
							String cls="com.cityindata.Splash";
							softskip(pkg, cls);
						}
						
						if(isServiceRunning(context , serviceName1)){
							toast("�ɼ�������������");
//							textView14.setText("�ɼ�������������");
						} else{
							toast("�ɼ���������ֹͣ");
//							textView14.setText("�ɼ���������ֹͣ");
							String pkg="com.dinotech";
							String cls="com.cityindata.Splash";
							softskip(pkg, cls);
						}*/
						//ping
						final Process process;
						try {
							//��ipΪ�ٶ�
							String address="47.92.100.250";
//							String command = Environment.getRootDirectory().getPath()+"/bin/ping -c 4 -s 128 " + address;
//							Process  process = Runtime.getRuntime().exec(command);
							//û��-c 4 -s 128 ������
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
//							textView12.setText(s+"\r\n"); ���Ϊping ����ϸ����
							try {
								//���������̵߳�ԭ�򣺱�����ɳ�����������ǿ�ˣ�ͬʱ���Ż�ping���ٶ�
								//ִ������������
								 Thread t=new Thread(new InputStreamRunnable(process.getErrorStream(),"ErrorStream"));  
					              t.start();  
								int status =process.waitFor();
								if(status == 0){
									ToastLong("�ü���������������");
									textView8.setText("\r\n"+"�ü�����������������");
								}else{
									toast("�ü������޷�����");
									textView8.setText("\r\n"+"�ü������޷�����");
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
						
//						toast("�Ѱ�װ");
						
						installApk(MainActivity.this, path  + apkName );
						
					}
				});
				break;
			case 3:
				//�ɼ�
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
				//����
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
							//copy  ͼ��
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
		 
        int i = execRootCmdSilent("echo test"); // ͨ��ִ�в������������
        if (i != -1) {
            return true;
        }
        return false;
    }
	
	
	private void Install(){
		button4.setOnClickListener(new OnClickListener() {
			String yaz="�Ѱ�װ";
			String waz="δ��װ";
			@Override
			public void onClick(View v) {
			//	4�ɼ�
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
			//11�ļ�����
			if(isAvilible("com.estrongs.android.pop")){
				textView17.setText(yaz);	
				xuanze2(button14);	
				}else{
					textView17.setText(waz);	
					xuanze(button14);
					xuanze1(5);
				}
			//12 ��
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
