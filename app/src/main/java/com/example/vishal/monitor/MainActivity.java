package com.example.vishal.monitor;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity
{
    public static final String TAG = "MainActivity";
    public static MainActivity mainActivityInstance;
    Executer RunExec = new Executer();
    String shell;
    TextView banner;
    TextView txtStatus;

    Button btnAlertPrefs;
    Button btnConnect;
    Button btnExport;
    Context context;
    public static int PrivacyGuard_Installed=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityInstance = this;
        context= this;
        copyResources();
        banner = (TextView) findViewById(R.id.txtBanner);
        txtStatus = (TextView) findViewById(R.id.txtStatus);

        btnAlertPrefs = (Button) findViewById(R.id.btnAlertPrefs);
        btnConnect = (Button) findViewById(R.id.btnConnect);
        btnExport = (Button) findViewById(R.id.btnExport);
        //initialize locationAcquisitionHelper here, its constructor does the rest of the work
        //locationAcquisitionHelper = new LocationAcquisitionHelper(getApplicationContext());
        //locationAcquisitionHelper.mGoogleApiClient

        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Monitordb dbHelper = new Monitordb(context);
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor c = db.query(dbHelper.ACCESSLOG_TABLE_NAME,new String[]{dbHelper.COLUMN_PACKAGE_NAME, " count("+dbHelper.COLUMN_PACKAGE_NAME+")"},null,null,dbHelper.COLUMN_PACKAGE_NAME,null,null);
                String logfile = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Monitor/Accesslog.txt";
                PrintWriter writer = null;
                try {
                    writer = new PrintWriter(logfile);
                    String result="";
                    if(c.getCount()>0)
                    {
                        Log.d(TAG, "export: cursor is not zero "+c.getCount());
                        //c.moveToFirst();
                        while(c.moveToNext())
                        {
                            result=result + c.getString(0) +" "+c.getString(1)+"\n";
                        }
                        writer.print(result);
                        writer.close();
                    }
                    else
                        Log.d(TAG, "export: cursor is zero");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                try {
                    File sd = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Monitor");
                    File data = Environment.getDataDirectory();

                    if (sd.canWrite()) {
                        String currentDBPath = "/data/data/" + getPackageName() + "/databases/"+Monitordb.DATABASE_NAME;
                        String backupDBPath = Monitordb.DATABASE_NAME;
                        File currentDB = new File(currentDBPath);
                        File backupDB = new File(sd, backupDBPath);

                        if (currentDB.exists()) {
                            FileChannel src = new FileInputStream(currentDB).getChannel();
                            FileChannel dst = new FileOutputStream(backupDB).getChannel();
                            dst.transferFrom(src, 0, src.size());
                            src.close();
                            dst.close();
                        }
                    }
                } catch (Exception e) {
                    Log.d(TAG, "pullListen: problem in pull db");
                }
            }
        });
        btnAlertPrefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPrefsActivity();
            }
        });
        /*btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = VpnService.prepare(context);
                if (intent != null) {
                    startActivityForResult(intent, 0);
                } else {
                    onActivityResult(0, RESULT_OK, null);
                }
            }
        });*/

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                if(!isAppInstalled("com.PrivacyGuard"))
                {
                    Intent installIntent = new Intent(Intent.ACTION_VIEW);
                    installIntent.setDataAndType(Uri.fromFile(new File(getFilesDir().getAbsolutePath()+"/privacyguard.apk")), "application/vnd.android.package-archive");
                    installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //installIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivityForResult(installIntent,PrivacyGuard_Installed);

                }
                else*/
                    startPrivacyGuard();
            }
        });
        //banner.setText("Hey !");
    }
    public void startPrivacyGuard()
    {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.PrivacyGuard");
        if (launchIntent != null) {
            startActivity(launchIntent);//null pointer check in case package name was not found
        }
    }
    public boolean isAppInstalled(String packagename)
    {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packagename, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
    @Override
    protected void onActivityResult(int request, int result, Intent data)
    {
        Log.d(TAG, "onActivityResult: "+result);
        if (result == RESULT_OK) {

        }
        else if (result == PrivacyGuard_Installed) {
            startPrivacyGuard();
        }
    }




    private void startPrefsActivity() {
        Intent intent = new Intent(this, AlertPrefsActivity.class);
        String message = "start alert prefs activity";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
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
            //privateCopy("privacyguard.apk",R.raw.privacyguard);
            RunExec.runCmd("busybox tar xpf "+filesDir+"/apktools.tar --directory="+filesDir+"/");
            RunExec.runCmd("busybox chmod -R 755 "+filesDir+"/apktool");
            RunExec.runCmd("busybox rm -f "+filesDir+"/apktools.tar");
        }

        //RunExec.runCmd("ls -l /system/bin/");
        //RunExec.runCmd("ls -l /system/framework/");
        //RunExec.runCmd("ls -l /data/system-framework/");
        //RunExec.runCmd("ls -l /data/data/com.example.vishal.monitor/files/");
        //RunExec.runCmd("ls -l /data/data/com.example.vishal.monitor/files/apktool/");

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
