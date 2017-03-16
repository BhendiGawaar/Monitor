package com.example.vishal.monitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;
import static com.example.vishal.monitor.StaticStorage.lastAdded;
import static com.example.vishal.monitor.StaticStorage.lastRemoved;

/**
 * Created by vishal on 6/10/16.
 */
public class Receiver extends BroadcastReceiver {

    SharedPreferences alertPrefs;

    @Override
    public void onReceive(Context context, Intent intent)
    {

        alertPrefs = context.getSharedPreferences("alertPrefs",Context.MODE_APPEND);

        //Log.d(TAG, "onReceive: "+intent.getAction());
        if(intent.getAction().equals("com.vishal.reporter"))
        {
            String packName = intent.getStringExtra("who");
            Log.d(TAG, "onReceive2: "+intent.getAction());
            if(alertPrefs.getBoolean("switchGlobal",true) && alertPrefs.getBoolean(packName,true))
                Toast.makeText(context, "Location accessed by " + intent.getStringExtra("who") + " on " + intent.getStringExtra("date") + " at " + intent.getStringExtra("time"), Toast.LENGTH_LONG).show();
        }
       else if(intent.getAction().equalsIgnoreCase("android.intent.action.PACKAGE_ADDED"))
        {
            String packageName=intent.getData().getEncodedSchemeSpecificPart();
            if(lastAdded != null && lastAdded.equalsIgnoreCase(packageName))
            {
                lastAdded = null;
            }
            else
                lastAdded = packageName;
            Log.d(TAG, "onReceive: "+intent.getAction()+" "+packageName);
            Toast.makeText(context,"Added package: "+packageName,Toast.LENGTH_LONG).show();
            Operator operator = new Operator();

            operator.operate(packageName,context);

        }
        else if(intent.getAction().equalsIgnoreCase("android.intent.action.PACKAGE_REMOVED"))
        {
            Log.d(TAG, "onReceive: "+intent.getAction());
            String packageName=intent.getData().getEncodedSchemeSpecificPart();
            lastRemoved=packageName;
            Log.d(TAG, "onReceive: "+packageName + "lastAdded="+lastAdded + "lastRemoved="+lastRemoved);
            Toast.makeText(context,"Removed package: "+packageName,Toast.LENGTH_LONG).show();
            Monitordb dbHelper = new Monitordb(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            if(!lastRemoved.equalsIgnoreCase(lastAdded)) {
                db.delete(dbHelper.INJECTEDAPPS_TABLE_NAME, dbHelper.COLUMN_PACKAGE_NAME + "=?", new String[]{packageName});
                Log.d(TAG, "onReceive: deleted "+packageName + "lastAdded="+lastAdded + "lastRemoved="+lastRemoved);
            }

        }
    }
}
