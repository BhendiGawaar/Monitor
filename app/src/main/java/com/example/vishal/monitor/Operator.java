package com.example.vishal.monitor;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.content.ContentValues.TAG;

/**
 * Created by vishal on 30/11/16.
 */

public class Operator
{
    public String operate(String packageName, final Context context)
    {
        String status = "Error";
        try
        {

            String filesDir = context.getFilesDir().getAbsolutePath();
            final String apktoolDir = filesDir+"/apktool";//path to the internal apktool stored in files
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            String srcDir = appInfo.sourceDir;
            String extStoragePath = Environment.getExternalStorageDirectory().getPath();
            File MonitorDir = new File(extStoragePath, "Monitor");
            File destDir = new File(MonitorDir.getPath(), packageName);//create folder of packagename on ext storage
            if (!destDir.exists())
                destDir.mkdirs();

            String path[] = srcDir.split("/");
            String apkName = path[path.length-1];
            final String apkFile = destDir.getPath()+"/"+apkName;//absolute path of the apk file on ext storage
            final String outputDir = destDir+"/"+apkName.substring(0, apkName.length() - 4)+"_src";
            final String compiledApk = outputDir+".apk";
            final String signedApk = outputDir+"_sign.apk";
            //File appDir = context.getExternalFilesDir(null);
            Log.d(TAG, "Operate: app src file="+ srcDir+" dest file: "+apkFile+" apkName="+apkName +" outputDir="+outputDir+" outputApk="+compiledApk);

            fileCopy(srcDir,apkFile);//copy apk to external storage



            Thread thread = new Thread() {
                public void run() {
                    try {
                        TextView txtStatus=null;
                        if(MainActivity.mainActivityInstance !=null) {
                            txtStatus = MainActivity.mainActivityInstance.txtStatus;
                        }
                        txtStatus.setText("Decompiling..");
                        decompile(apktoolDir,apkFile,outputDir, context);
                        txtStatus.setText("Injecting..");
                        Injector.scanAndInject(outputDir,context);
                        txtStatus.setText("Compiling..");
                        compile(apktoolDir,compiledApk,outputDir,context);
                        txtStatus.setText("Signing..");
                        signApk(apktoolDir,compiledApk,signedApk,context);
                        txtStatus.setText("Done");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
            //thread.join();
        }
        catch (Exception e)
        {
            Log.d(TAG, "Exception in operate: ");
            e.printStackTrace();
        }
        return status;
    }

    public String decompile(final String apktoolDir,final String apkFile,final String outputDir, Context context) throws InterruptedException
    {
        String status = "Error";

        //RunExec.runCmd("sh "+filesDir+"/apktool/apktool.sh d -f /sdcard/download/cme.apk  /sdcard/download/cme_src2");
        //RunExec.runCmd("sh "+filesDir+"/apktool/apktool2.sh d -f /sdcard/download/cme.apk -o /sdcard/download/cme_src3");
        Executer.runCmd("sh "+apktoolDir+"/apktool2.sh d -f  "+apkFile+" -o "+outputDir);

        return status;
    }

    public String compile(final String apktoolDir, final String outputApk, final String outputDir, Context context) throws InterruptedException {
        String status = "Error";

                //RunExec.runCmd("sh "+filesDir+"/apktool/apktool.sh d -f /sdcard/download/cme.apk  /sdcard/download/cme_src2");
                //RunExec.runCmd("sh "+filesDir+"/apktool/apktool2.sh d -f /sdcard/download/cme.apk -o /sdcard/download/cme_src3");
                Executer.runCmd("sh " + apktoolDir + "/apktool2.sh b -f -a " + apktoolDir + "/openjdk/bin/aapt4.4 " + outputDir + " -o " + outputApk);
                //Executer.runCmd("sh "+apktoolDir+"/apktool2.sh b -f  "+apkFile+" -o "+outputDir);

        return status;
    }

    public String signApk(final String apktoolDir, final String outputApk, final String signedApk, Context context) throws InterruptedException {
        String status = "Error";

                Executer.runCmd("sh " + apktoolDir + "/signapk.sh " + outputApk +" "+ signedApk);
                //Executer.runCmd("sh "+apktoolDir+"/apktool2.sh b -f  "+apkFile+" -o "+outputDir);

        return status;
    }

    public void fileCopy(String src, String dst) throws IOException
    {
        Log.d(TAG, "COPYING..:"+src +" -> "+dst);
        try {
            File srcFile = new File(src);
            File dstFile = new File(dst);
            InputStream in = new FileInputStream(srcFile);
            OutputStream out = new FileOutputStream(dstFile);

            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }
        catch (Exception e)
        {
            Log.d(TAG, "Exception in fileCopy");
            e.printStackTrace();
        }
    }



}
