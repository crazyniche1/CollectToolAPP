package com.example.collect;

import java.io.IOException;
import java.io.OutputStream;

import android.util.Log;

public class Dl645 {
	/**
	 * 
	 * @param address  ,仪表地址 eg：17002350
	 * @param meterAddress  该地址写死，1:36880，41：37136
	 */
	public void SendDl645(int idd,OutputStream mOutputStream ) {
		// TODO Auto-generated constructor stub
		int address = 36880;
		String meterAddress = addZeroForNum(idd+"",12);
		byte[] message = new byte[17];
		message[0] = (byte)254;
		message[1] = (byte)254;
		message[2] = (byte)254;
		message[3] = (byte)104;

		message[4] = (byte)Integer.parseInt(meterAddress.substring(10, 12), 16);
		message[5] = (byte)Integer.parseInt(meterAddress.substring(8, 10), 16);
		message[6] = (byte)Integer.parseInt(meterAddress.substring(6, 8), 16);
		message[7] = (byte) Integer.parseInt(meterAddress.substring(4, 6), 16);
		message[8] = (byte)Integer.parseInt(meterAddress.substring(2, 4), 16);
		message[9] = (byte)Integer.parseInt(meterAddress.substring(0, 2), 16);

		message[10] = (byte)104;
		message[11] = (byte)01;
		message[12] = (byte)02;
		int data1 = address % 256 + 0x33;
		int data2 = address / 256 + 0x33;
		message[13] = (byte) (data1);
		message[14] = (byte) (data2);
		int sum = 0;
		for (int i = 3; i < 15; i++) {
			sum += message[i];
		}

		message[15] = (byte) (sum % 0x100);
		message[16] = (byte)22;
		try {
			mOutputStream.write( message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/*
	 * 数字不足位数左补0
	 *
	 * @param str
	 * 
	 * @param strLength
	 */
	public static String addZeroForNum(String str, int strLength) {
		int strLen = str.length();

		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				sb.append("0").append(str);// 左补0
				str = sb.toString();
				strLen = str.length();
			}
		}

		return str;
	}
	
}
