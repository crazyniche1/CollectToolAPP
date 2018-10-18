package com.example.collect;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android_serialport_api.SerialPort;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.example.collect.Dl645;
import com.example.collect.Modbus;
import com.example.util.MyLog;
import com.zy.R;

public class Meterreading extends Activity{

	private TextView receivecontent;
	private EditText meterid;
	
	protected SerialPort mSerialPort;
    protected InputStream mInputStream;
    protected OutputStream mOutputStream;
	private StringBuilder sb;
	String[] dataType={"c相相电ya","c相相电ya","c相相电ya","c相相电ya","c相相电ya","c相相电ya"};
	private Button openserial;
	private Button sendserial;
	private Button cleanserial;

	@Override
	protected void onCreate(Bundle arg0) {
		
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chaobiao_page);
		OpenserialPort();
		Button1();
	}
	
	private  void toast(String text){
		Toast toast = Toast.makeText(getApplicationContext(), text , Toast.LENGTH_SHORT);
		toast.setGravity(0, 0, 0);
		toast.show();
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		receivecontent = (TextView)findViewById(R.id.receivecontent);
		meterid = (EditText)findViewById(R.id.meterid);
		openserial = (Button)findViewById(R.id.openserial);
		sendserial = (Button)findViewById(R.id.sendserial);
		cleanserial = (Button)findViewById(R.id.cleanserial);
		
		sb = new StringBuilder();
		
	}
	private void Button1(){
		openserial.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						new Thread(new Runnable() {
							public void run() {
								OpenserialPort();
							}
						});
					}
				});
		
		sendserial.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new  Thread(new Runnable() {
					private int num;
					private int idd;

					public void run() {
						String hex = meterid.getText().toString().trim();
						num = hex.length();
						idd = Integer.parseInt(hex);
						
						if(num >3){
							 new Dl645().SendDl645(idd,mOutputStream); 
							 Log.i("test", "dl645");
							 receiveThread();
						}else{
							 new Modbus().Modbus(idd, mOutputStream);
		                   	 Log.i("test", "Modbus");
		                   	 receiveThread();
						 }
					}
				});
			}
		});
		cleanserial.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				sb=sb.delete(0, sb.length());
				receivecontent.setText("结果："+sb.toString());
				
				closeserial();
			}
		});
		
	}
	private void closeserial(){
		
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
	
	private void receiveThread(){
        // 接收串口信息
         new Thread() {

			@Override
            public void run() {
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
                            Log.i("test_js",""+recinfo );
                            sb.append(recinfo+"\n");
                            handler.sendEmptyMessage(1);
                            
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        };
	}

	private void OpenserialPort(){
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
	 Handler handler = new Handler() {
	        public void handleMessage(android.os.Message msg) {
	            if (msg.what == 1) {
	                //text.setText(text.getText().toString().trim()+sb.toString());
	            	String[] data=sb.toString().split(",,");
	            	String data1 = "";
	            	String data2="";
	            	String res1;
	            	String res2;
	            	String res3;
	            	for(int h=0;h<data.length;h++){
	            		data1=data1+data[h]+"   ";
	            		res1=data[h].substring(9, 11);
	            		res2=data[h].substring(12, 14);
	            		res3=res1+res2;
	            		double x = Integer.parseInt(res3,16)*0.01;
						data2=data2+dataType[h]+":"+x+"   ";
	            	}
	            	receivecontent.setText("数据："+data1+"\n");
//	            	text1.setText("结果："+data2);
	            }
	        }
	    };
}
