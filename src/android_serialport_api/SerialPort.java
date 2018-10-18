package android_serialport_api;

import android.util.Log;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SerialPort {

    private static final String TAG = "SerialPort";
    private FileDescriptor mFd;
    private FileInputStream mFileInputStream;
    private FileOutputStream mFileOutputStream;

//    public SerialPort(File device, int baudrate, int flag) throws SecurityException, IOException 
    public SerialPort(File device, int baudrate, int parity, int dataBits, int stopBit) throws SecurityException, IOException
    {

        //������Ȩ�ޣ����û�ж�дȨ�ޣ������ļ��������޸��ļ�����Ȩ��
        if (!device.canRead() || !device.canWrite()) {
            try {
                //ͨ�����ڵ�linux�ķ�ʽ���޸��ļ��Ĳ���Ȩ��
                Process su = Runtime.getRuntime().exec("/system/bin/su");
                //һ��Ķ���/system/bin/su·�����е�Ҳ��/system/xbin/su
                String cmd = "chmod 777 " + device.getAbsolutePath() + "\n" + "exit\n";
                su.getOutputStream().write(cmd.getBytes());

                if ((su.waitFor() != 0) || !device.canRead() || !device.canWrite()) {
                    throw new SecurityException();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new SecurityException();
            }
        }

//        mFd = open(device.getAbsolutePath(), baudrate, flag);
        mFd = open(device.getAbsolutePath(), baudrate, parity, dataBits, stopBit);  

        if (mFd == null) {
            Log.e(TAG, "native open returns null");
            throw new IOException();
        }

        mFileInputStream = new FileInputStream(mFd);
        Log.i("test", "mFileInputStream");
        mFileOutputStream = new FileOutputStream(mFd);
    }

    // Getters and setters
    public InputStream getInputStream() {
        return mFileInputStream;
    }

    public OutputStream getOutputStream() {
        return mFileOutputStream;
    }

    // JNI(����java���ؽӿڣ�ʵ�ִ��ڵĴ򿪺͹ر�)
    /**
     * @param path     �����豸�ľݶ�·��
     * @param baudrate ������
     * @param flags    У��λ
     */
//    private native static FileDescriptor open(String path, int baudrate, int flag);
    private native static FileDescriptor open(String path, int baudrate, int parity, int dataBits, int stopBit);  

    public native void close();

    static {//����jni�µ�C�ļ���
        System.loadLibrary("serial_port");
    }
}