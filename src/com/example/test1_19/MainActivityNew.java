package com.example.test1_19;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.collect.Dl645;
import com.example.collect.Modbus;
import com.example.common.CurrentString;
import com.example.server.MoniteringServer;
import com.example.server.ServicesServer;
import com.example.util.MyLog;
import com.zy.R;




import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.os.SystemProperties;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android_serialport_api.SerialPort;



@SuppressLint({ "InflateParams", "HandlerLeak" })
public class MainActivityNew extends Activity implements  android.view.View.OnClickListener {
	
	private ViewPager mViewPager;
	private LinearLayout mtab_hongdian;
	private LinearLayout mtab_huise;
	private LinearLayout mtab_heise;
	private LinearLayout mtab_mater;
	private LinearLayout mtab_about;
	private ImageButton mImg1;
	private ImageButton mImg2;
	private ImageButton mImg3;
	private PagerAdapter mPagerAdapter;
	private TextView mac_text;
	 View tab01;
	 View tab02;
	 View tab03;
	 View tab04;
	 View tab05;
	private TextView baseband_version;
	private TextView system_version;
	private String mainpath = Environment.getExternalStorageDirectory()+"/"+"CollectToolApp"+ "/"+"App"+ "/";
	Uri uri = Uri.parse("content://telephony/carriers/preferapn");
	OtherUtil ou = new OtherUtil();
	private Button openserial;
	private TextView receivecontent;
	private EditText meterid;
	private Button sendserial;
	private Button cleanserial;
	private EditText parity_bit;
	private Button heise_install;
	
	protected SerialPort mSerialPort;
    protected InputStream mInputStream;
    protected OutputStream mOutputStream;
	
	private StringBuilder sb = new StringBuilder();
	private StringBuilder sb2 = new StringBuilder();
	private CurrentString CurrentString = new CurrentString();
	
	private  ProgressDialog pd;
	private TextView about_version;
	
    Handler handler = new Handler(  ) {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
            	String[] data=sb2.toString().split(",,");
            	String data1 = "";
            	for(int h=0;h<data.length;h++){
            		data1=data1+data[h]+"   ";
            	}
            	receivecontent.setText(data1+"\n");
            }
        }
    };
	

	
	@SuppressLint("InflateParams")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//隐藏系统自带标题栏,该方法与setcontentview 方法不能调换位置否则，抛出异常，且 页面闪退Caused by: Android.util.AndroidRuntimeException: requestFeature() must be called before adding content
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.new_activity_main);
		//默认不弹出键盘
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		//解决同步时间networkonmainthreadexception
		if (android.os.Build.VERSION.SDK_INT > 9) {  
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();  
            StrictMode.setThreadPolicy(policy);  
        }  
		//程序入口启动服务
		service();
		//主入口
		console();
		
		
	}
	private void service(){
		startService(new Intent(getApplicationContext(), MoniteringServer.class));
		startService(new Intent(getApplicationContext(),ServicesServer.class));
	}
	private void console(){
		//设置的时间
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		Date time = null;
		try {
			time = sdf.parse(CurrentString.updatetime);
		} catch (ParseException e) {
			toast(CurrentString.a);
		}
		if(date.before( time )){
			initView();  
			initViewPage();  
			initEvent(); 
			mac();
			version();
			ou.showVersion(baseband_version,system_version);
			
			if(ou.isDateTimeAuto()){
				toast(CurrentString.p);
			}else{
				ou.setDateTimeAuto(getApplicationContext());
			}
		}	else {
			toast(CurrentString.b);
			Intent intent = new Intent(getApplicationContext(), PassPage.class); 
			startActivity(intent);
		}
	}
	
	private void setAirplaneMode(boolean setAirPlane) { 
		Settings.System.putInt(getApplicationContext().getContentResolver(), 
		Settings.System.AIRPLANE_MODE_ON, setAirPlane ? 1 : 0); 
		Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED); 
		intent.putExtra("state", setAirPlane); 
		getApplicationContext().sendBroadcast(intent); 
		} 
	
	private  void toast(String text){
		ou.toast(text, getApplicationContext());
	}
	
	private void mac(){
		
		 String mac = ou.getLocalMacAddress(this);
	     mac_text.setText(mac.toString());
	}
	private void version(){
		try {
			about_version.setText("软件版本："+this.getPackageManager()
							.getPackageInfo(this.getPackageName(), 0)
							.versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}	
	}
	private void initEvent() {
		
		mtab_hongdian.setOnClickListener((OnClickListener) this); 
		mtab_heise.setOnClickListener((OnClickListener) this); 
		mtab_huise.setOnClickListener((OnClickListener) this); 
		mtab_mater.setOnClickListener((OnClickListener) this); 
		mtab_about.setOnClickListener((OnClickListener) this); 
		
		openserial.setOnClickListener((OnClickListener) this); 
 		sendserial.setOnClickListener((OnClickListener) this); 
 		cleanserial.setOnClickListener((OnClickListener) this); 
		
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
//			ViewPage左右滑动时
			@Override
			public void onPageSelected(int arg0) {
				int currentItem = mViewPager.getCurrentItem();
				switch (currentItem) {  
                case 0:  
//                     mtab_hongdian.setBackgroundResource(R.drawable.logo);  
                    break;  
                case 1:  
                    // resetImg();  
                    break;  
                case 2:  
                    break;  
                default:  
                    break;  
                }  
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	private void initViewPage() {
		// TODO Auto-generated method stub
		// 初妈化3个布局 
	      LayoutInflater mLayoutInflater = LayoutInflater.from(this);  
	         tab01 = mLayoutInflater.inflate(R.layout.heise_page, null);  
	         tab02 = mLayoutInflater.inflate(R.layout.hongdian_page, null);  
	         tab03 = mLayoutInflater.inflate(R.layout.huise_page, null);  
	         tab04 = mLayoutInflater.inflate(R.layout.chaobiao_page, null);  
	         tab05 = mLayoutInflater.inflate(R.layout.about_page, null);  
	         
	       //view
	        receivecontent = (TextView)tab04.findViewById(R.id.receivecontent);
	 		meterid = (EditText)tab04.findViewById(R.id.meterid);
	 		openserial = (Button)tab04.findViewById(R.id.openserial);
	 		sendserial = (Button)tab04.findViewById(R.id.sendserial);
	 		cleanserial = (Button)tab04.findViewById(R.id.cleanserial);
	 		parity_bit = (EditText)tab04.findViewById(R.id.parity_bit);
	 		
	 		heise_install = (Button)tab01.findViewById(R.id.heise_install);
	 		about_version = (TextView)tab05.findViewById(R.id.aboutversion);
	 		//存放布局
	        final List<View> mViews = new ArrayList<View>();
	        mViews.add(tab04);
	        mViews.add(tab02);
	        mViews.add(tab03);
	        mViews.add(tab01);
	        mViews.add(tab05);
	        
	     // 适配器初始化并设置 ,用来放置界面切换  
	       mPagerAdapter = new PagerAdapter() {
				
	        	 @Override  
	             public void destroyItem(ViewGroup container, int position,  
	                     Object object) {  
	                 container.removeView(mViews.get(position));  
	   
	             }  
	   
	             @Override  
	             public Object instantiateItem(ViewGroup container, int position) {  
	                 View view = mViews.get(position);  
	                 container.addView(view);  
	                 return view;  
	             }  
	   
	             @Override  
	             public boolean isViewFromObject(View arg0, Object arg1) {  
	   
	                 return arg0 == arg1;  
	             }  
	   
	             @Override  
	             public int getCount() {  
	   
	                 return mViews.size();  
	             }  
			};
			 mViewPager.setAdapter(mPagerAdapter); 	
	}
	public void onTabClicked(View view) {
		switch (view.getId()) {
		case R.id.id_tab_hongdiana:
				toast(CurrentString.h);
				File file = new File(Environment.getExternalStorageDirectory() + "/"+"CollectToolApp"+ "/"+"App" );
				String path = file+ "/";
				String s[] = {"AutoRun.apk","CityInData10.26fx01x.apk","ES.apk","fxPort.apk","jsb.apk"};
				for (String string : s) {
					ou.copyAssetsFile(getApplicationContext(), string, path);
				}
				MyLog.write485("over");
				for (String string2 : s) {
					ou.install(string2, getApplicationContext(), mainpath);
				}
				break; 
			 case R.id.id_tab_huisea:
					toast(CurrentString.g);
					
					Intent intent = ou.openstatus();
					if(ou.isIntentAvailable(getApplicationContext(), intent)){
						toast(CurrentString.o);
					}else{
						sendBroadcast(intent);
					}
					break;	
	        case R.id.id_tab_heisea:
	        	toast(CurrentString.f);
				ou.synchronizationtime(getApplicationContext());
				break;
	        case R.id.id_tab_timea:
	        	pd = new ProgressDialog(this);
	    		pd.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    		pd.setTitle("提示：");
	    		pd.setMessage("正在查询网络状态-----");
	    		pd.setCancelable(false);
	    		new PingTast().execute();
	    		break;
		}
	}
	
	/** 
     * 判断哪个要显示，及设置按钮图片 
     */ 
	 @SuppressWarnings("deprecation")
	public void onClick(View arg0) {  
	       
			switch (arg0.getId()) {  
			case R.id.heise_install:
				toast(CurrentString.h);
				String str = "CityInData10.26fx02j.apk";
				ou.copyAssetsFile(getApplicationContext(),str , mainpath);
				ou.install(str, getApplicationContext(), mainpath);
				break;
			
	        case R.id.cleanserial:
				sb2=sb2.delete(0, sb2.length());
				receivecontent.setText(sb2.toString());
				ou.closeserial(mSerialPort, mInputStream, mOutputStream);
				toast(CurrentString.e);
				break;  
	        case R.id.sendserial:
				String hex = meterid.getText().toString().trim();
				if(TextUtils.isEmpty(hex)){
					toast(CurrentString.d);
				}else{
				int num = hex.length();
				int idd = Integer.parseInt(hex);
				 Log.i("zy", num+"num");
					if(num >4){
						 new Dl645().SendDl645(idd,mOutputStream); 
						 ou.receiveThread(mInputStream, sb2, handler);
					}else{
						try{
							
							new Modbus().Modbus(idd, mOutputStream);
						}catch(Exception e){
							MyLog.write485( e.toString());
						}
						
	                   	ou.receiveThread(mInputStream, sb2, handler);
					 }
				}
				break;  
	        case R.id.openserial:  
//				ou.OpenserialPort(mSerialPort, mInputStream, mOutputStream);
				String prot  = "ttyS2";
				String hex1 = parity_bit.getText().toString().trim();
				if(TextUtils.isEmpty(hex1)){
					toast(CurrentString.c);
				}else{
				int check_bit = Integer.parseInt(parity_bit.getText().toString().trim());
				
				try {
					 mSerialPort = new SerialPort(new File("/dev/" + prot), 9600,check_bit, 8, 1);
					 toast(CurrentString.q);
				} catch (Exception e) {
					 toast(CurrentString.r);
				} 
		        mInputStream = mSerialPort.getInputStream();
		        mOutputStream = mSerialPort.getOutputStream();
				}
				break;  
	        case R.id.id_tab_mater:  
	            mViewPager.setCurrentItem(0);  
	            break;  
	        case R.id.id_tab_heise:  
	            mViewPager.setCurrentItem(1);  
	            break;  
	        case R.id.id_tab_huise:  
	            mViewPager.setCurrentItem(2);  
	            break; 
	        case R.id.id_tab_hongdian:  
	            mViewPager.setCurrentItem(3);  
	            break; 
	        case R.id.id_tab_about:  
	            mViewPager.setCurrentItem(4);  
	            break; 
	        
	        default:  
	            break;  
	        }  
	    }  
	
	private void initView() {
		// 初始化3个LinearLayout 
		mViewPager = (ViewPager) findViewById(R.id.id_viewpage);  
		mtab_hongdian = (LinearLayout) findViewById(R.id.id_tab_hongdian); 
		mtab_huise = (LinearLayout) findViewById(R.id.id_tab_huise);
		mtab_heise = (LinearLayout) findViewById(R.id.id_tab_heise);
		mtab_mater = (LinearLayout) findViewById(R.id.id_tab_mater);
		mtab_about = (LinearLayout) findViewById(R.id.id_tab_about);
		
		// 初始化3个按钮  
        mImg1 = (ImageButton) findViewById(R.id.imageButton1);  
        mImg2 = (ImageButton) findViewById(R.id.imageButton2);  
        mImg3 = (ImageButton) findViewById(R.id.imageButton3);  
        //初始化 2个 版本
        baseband_version= (TextView)findViewById(R.id.baseband_version);
        system_version= (TextView)findViewById(R.id.system_version);
        
        mac_text = (TextView) findViewById(R.id.mac_text1);
       
	}

	//
	 class PingTast extends AsyncTask<Void, Void, Boolean>{
		String line ; 
		String s;
	    Process process;
	    int status;
	    @Override
	    protected void onPreExecute() {
	    	 pd.show() ;
	    }
	    
	    @Override
		protected Boolean doInBackground(Void... arg0) {
		try {
		//没有-c 4 -s 128 会闪退
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
	    	super.onPostExecute(result);
	    	if(result==true){
	    		toast(CurrentString.j);
	    	}else{
	    		toast(CurrentString.k);
	    	}
	    	pd.dismiss();
	    }
	}
	   
}
