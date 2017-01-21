package com.example.vishal.monitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

/**
 * Created by vishal on 6/10/16.
 */
public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        //Log.d(TAG, "onReceive: "+intent.getAction());
        if(intent.getAction().equals("com.vishal.reporter"))
        {
            Log.d(TAG, "onReceive2: "+intent.getAction());
            Toast.makeText(context, "Location accessed by " + intent.getStringExtra("who") + " on " + intent.getStringExtra("date") + " at " + intent.getStringExtra("time"), Toast.LENGTH_LONG).show();
        }
       else if(intent.getAction().equalsIgnoreCase("android.intent.action.PACKAGE_ADDED"))
        {
            String packageName=intent.getData().getEncodedSchemeSpecificPart();
            Log.d(TAG, "onReceive2: "+intent.getAction()+" "+packageName);
            Toast.makeText(context,"Added package: "+packageName,Toast.LENGTH_LONG).show();
            Operator operator = new Operator();
            operator.operate(packageName,context);
        }
        else if(intent.getAction().equalsIgnoreCase("android.intent.action.PACKAGE_REMOVED"))
        {
            Log.d(TAG, "onReceive2: "+intent.getAction());
            String packageName=intent.getData().getEncodedSchemeSpecificPart();
            Toast.makeText(context,"Removed package: "+packageName,Toast.LENGTH_LONG).show();
        }
    }
}
