package com.example.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Environment;
import android.util.Log;

public class MyLog {
	public static final String CACHE_DIR_NAME = "CollectToolApp";

	public static boolean isDebugModel = false;//
	public static boolean isSaveDebugInfo = false;// 
	public static boolean isSaveCrashInfo = false;// 

	public static void v(final String tag, final String msg) {
		if (isDebugModel) {
			Log.v(tag, "--> " + msg);
		}
	}

	public static void d(final String tag, final String msg) {
		if (isDebugModel) {
			Log.d(tag, "--> " + msg);
		}
	}

	public static void i(final String tag, final String msg) {
		if (isDebugModel) {
			Log.i(tag, "--> " + msg);
		}
	}

	public static void w(final String tag, final String msg) {
		if (isDebugModel) {
			Log.w(tag, "--> " + msg);
		}
	}

	/**
	 * try catch 时使用，上线产品可上传反馈�??
	 * 
	 * @param tag
	 * @param tr
	 */
	public static void e(final String tag, final Throwable tr) {
		if (isSaveCrashInfo) {
			new Thread() {
				public void run() {
					write(gettime() + tag + " [CRASH] --> " + getStackTraceString(tr) + "\n","1");
				};
			}.start();
		}
	}

	/**
	 * 调试日志，便于开发跟踪�??
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void e(final String tag, final String msg) {
		if (isDebugModel) {
			Log.e(tag, "--> " + msg);
		}

		if (isSaveDebugInfo) {
			new Thread() {
				public void run() {
					write(gettime() + tag + " --> " + msg + "\n","1");
				};
			}.start();
		}
	}

	/**
	 * 获取捕捉到的异常的字符串
	 * 
	 * @param tr
	 * @return
	 */
	public static String getStackTraceString(Throwable tr) {
		if (tr == null) {
			return "";
		}

		Throwable t = tr;
		while (t != null) {
			if (t instanceof UnknownHostException) {
				return "";
			}
			t = t.getCause();
		}

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		tr.printStackTrace(pw);
		return sw.toString();
	}

	/**
	 * 标识每条日志产生的时�?
	 * 
	 * @return
	 */
	private static String gettime() {
		return "[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date(System.currentTimeMillis())) + "] ";
	}

	/**
	 * 以年月日作为日志文件名称
	 * 
	 * @return
	 */
	private static String date() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
	}

	private static synchronized void write(String content, String type) {
		try {
			
			String log = "Log";
			content = gettime() + "    " + content;
			content+="\r\n";  
			if (type.equals("1")) {
				log = "Log";
			} else if (type.equals("2")) {
				log = "Log1";
			} else if (type.equals("3")) {
				log = "Log2";
			}
			FileWriter writer = new FileWriter(getFile(log), true);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param content
	 */
	public static synchronized void writeQT(String content) {
		write(content, "1");
	}

	/**
	 * 
	 * @param content
	 */
	public static synchronized void write485(String content) {
		write(content, "2");
	}

	/**
	 * 
	 * @param content
	 */
	public static synchronized void writeBT(String content) {
		write(content, "3");
	}

	/**
	 * 
	 * @return
	 */
	public static String getFile(String log) {
		File sdDir = null;

		if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
			sdDir = Environment.getExternalStorageDirectory();

		File cacheDir = new File(sdDir + File.separator + CACHE_DIR_NAME);

		if (!cacheDir.exists())
			cacheDir.mkdir();
		

		File cacheDir2 = new File(sdDir + File.separator + CACHE_DIR_NAME + "/"+log);

		if (!cacheDir2.exists())
			cacheDir2.mkdir();

		File filePath = new File(cacheDir + "/"+log+ File.separator + date() + ".txt");
		
		return filePath.toString();

	}

}
