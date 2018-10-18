package com.example.collect;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.example.util.MyLog;

import android.util.Log;

public class Modbus {

	int[] address={41};
	final byte[] SendCommand = new byte[8];
	final int[] message1 = new int[8];
	int[] ilength1={0001,0001,0001,0001,0001,0001,0001,0001,0001};
	protected InputStream mInputStream;
	
	
	public void  Modbus(int idd,OutputStream mOutputStream ) { 
		
    	for(int a=0;a<address.length;a++){
    		 Log.i("zy", idd+"");
    		SendCommand[0] = (byte) idd;
            SendCommand[1] = (byte)0x03;
            SendCommand[2] = (byte)((address[a] - address[a] % 256) / 256);
            SendCommand[3] = (byte)(address[a] % 256);
            SendCommand[4] = (byte)0x00;
            SendCommand[5] = (byte)0x01; 
           
    		message1[0] = (byte)idd;
    		message1[1] = 3;
    		message1[2] = (int) (address[a] >> 8);
    		if (address[a] >= 1023) {
    			if ((byte) address[a] < 0) {
    				message1[3] = (int) (256 + (byte) address[a]);
    			} else {
    				message1[3] = (byte) address[a];
    			}

    		} else if (address[a] > 256) {
    			message1[3] = (short) (address[a] - 256);
    		} else {
    			message1[3] = (int) (address[a]);
    		}

    		message1[4] = (int) (ilength1[a] >> 8);
    		message1[5] = (int) ilength1[a];
    		
    		
    		
    		 //CRC校验
        	byte[] crc = ComputeCRC111(message1);
            SendCommand[6] = crc[0];
            SendCommand[7] = crc[1];
            
            Log.i( "zy","发成功："
    				+SendCommand[0]
    						+"    "+SendCommand[1]
    				+"    "+SendCommand[2]+"    "+SendCommand[3]
    				+"    "+SendCommand[4]+"    "+SendCommand[5]
    				+"    "+SendCommand[6]+"    "+SendCommand[7]
    						);
            try {
                mOutputStream.write(SendCommand);
            } catch (Exception e) {
                e.printStackTrace();
            }
    	}
    
	}

        // 接收串口信息
		Thread  receiveThread = new Thread() {
            @Override
            public void run() {
            	
                while (true) {
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
                        	
                           Log.i("test", "通讯成功");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.i("test", "接收到串口信息异常:" + e);
                        
                    }
                }
            }
        };
       
	private static byte[] ComputeCRC111(int[] message) {
		
		int crcFull = (int) 0xFFFF;
		
		for(int a = 0; a < (message.length) - 2; a++)
		{
			message[a] &= 0x00FF;
//			Log.i("zy", message[a]);
		}
		for (int i = 0; i < (message.length) - 2; i++)
		{
			crcFull = (int) (crcFull ^ message[i]);

			for (int j = 0; j < 8; j++) {
				int lsb = (int) (crcFull & 0x0001);
				crcFull = (int) ((crcFull >> 1) & 0x7FFF);

				if (lsb == 1)
					crcFull = (int) (crcFull ^ 0xA001);
			}
		}

		byte[] crc = new byte[2];
		crc[0] = (byte) (crcFull & 0xFF);
		crc[1] = (byte) ((crcFull >> 8) & 0xFF);
		return crc;
	}

}


