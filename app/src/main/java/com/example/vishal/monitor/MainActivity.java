package com.example.vishal.monitor;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity
{
    public static final String TAG = "MainActivity";
    public static MainActivity mainActivityInstance;
    Executer RunExec = new Executer();
    String shell;
    TextView banner;
    TextView txtStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityInstance = this;
        copyResources();
        banner = (TextView) findViewById(R.id.txtBanner);
        txtStatus = (TextView) findViewById(R.id.txtStatus);
        //banner.setText("Hey !");
    }

    private String copyResources()
    {
        String status="error";
        shell = "sh ";

        //testing runexec.cmd
        String filesDir = getFilesDir().getAbsolutePath();
        Log.d(TAG, "dataDir = "+filesDir);

        //testing busybox
        /**As lenovo A6000 had internal busybox, app busybox is not copied**/
        //File busybox = privateCopy("busybox",R.raw.libb);

        //RunExec.runCmd("/system/bin/busybox rm -r /data/data/com.example.vishal.monitor/files/apktool");
        //RunExec.runCmd("/system/bin/busybox tar xpf /data/data/com.example.vishal.monitor/files/libjdk  --directory=/data/data/com.example.vishal.monitor/files/");
        //RunExec.runCmd("busybox chmod -R 755 /data/data/com.example.vishal.monitor/files/apktool");
        if(!new File(filesDir+"/apktool").exists())
        {
            File libjdk = privateCopy("apktools.tar",R.raw.apktools);
            privateCopy("Reporter.smali",R.raw.reporter);
            RunExec.runCmd("busybox tar xpf "+filesDir+"/apktools.tar --directory="+filesDir+"/");
            RunExec.runCmd("busybox chmod -R 755 "+filesDir+"/apktool");
            RunExec.runCmd("busybox rm -f "+filesDir+"/apktools.tar");
        }

        RunExec.runCmd("ls -l /system/bin/");
        //RunExec.runCmd("ls -l /system/framework/");
        //RunExec.runCmd("ls -l /data/system-framework/");
        RunExec.runCmd("ls -l /data/data/com.example.vishal.monitor/files/");
        RunExec.runCmd("ls -l /data/data/com.example.vishal.monitor/files/apktool/");

        /****** Rest commands are run from Operator class eg decompile, compile*****/
        //RunExec.runCmd("sh "+filesDir+"/apktool/apktool.sh d -f /sdcard/download/cme.apk  /sdcard/download/cme_src2");
        //RunExec.runCmd("sh "+filesDir+"/apktool/apktool2.sh d -f /sdcard/download/cme.apk -o /sdcard/download/cme_src3");
        //RunExec.runCmd("sh "+filesDir+"/apktool/apktool2.sh b -f -a /data/data/com.example.vishal.monitor/files/apktool/openjdk/bin/aapt4.4 /sdcard/download/cme_src -o /sdcard/download/cme_src3.apk");
        //RunExec.runCmd("sh /data/data/com.example.vishal.monitor/files/apktool/apktool2.sh b -f -a /data/data/com.example.vishal.monitor/files/apktool/openjdk/bin/aapt4.4 /storage/sdcard0/Monitor/com.SanchitaCreations.marathiindianlaw/base_src -o /storage/sdcard0/Monitor/com.SanchitaCreations.marathiindianlaw/base_src.apk");
        //


        return status;
    }

    private File privateCopy(String FILENAME, int rawID)
    {
        InputStream ins = getResources().openRawResource (rawID);
        byte[] buffer = new byte[0];
        try {
            buffer = new byte[ins.available()];

            ins.read(buffer);
            ins.close();
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(buffer);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = getFileStreamPath (FILENAME);
        file.setExecutable(true);
        return file;
    }

}
