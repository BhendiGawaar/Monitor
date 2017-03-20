package com.example.vishal.monitor;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created by vishal on 1/12/16.
 */

public class Executer
{
    public static final String TAG = "Executer";
    public static String Cmd(String shell,String command) {

        try {
            Log.d(TAG+" Cmd", "Running command: "+command);

            Process process = Runtime.getRuntime().exec(shell);

            DataOutputStream processOutput = new DataOutputStream(process.getOutputStream());

            processOutput.writeBytes(command + "\n");

            processOutput.writeBytes("exit\n");

            processOutput.flush();
            //		processInput = process.getInputStream();
            process.waitFor();

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            //StringBuilder result=new StringBuilder();
            String result="";
            String line = "";
            while ((line = bufferedReader.readLine()) != null)
            {
                result = result+"\n"+line;


            }
            Log.d(TAG+" Console", result.toString());
            Log.d(TAG, "Cmd: Returning...");
        }
        catch (Exception e)
        {
            Log.d("output", "exception in runCMD");
            e.printStackTrace();
        }
    return "Success";
    }


    public static void runCmd( String cmd)
    {
        Log.d(TAG+" runCmd", "Running command: "+cmd);
        try {

            Process process = Runtime.getRuntime().exec(cmd);
            process.waitFor();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String result= "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null)
            {
                result = result+line +"\n";

            }

            bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getErrorStream()));

            while ((line = bufferedReader.readLine()) != null)
            {
                result = result+line +"\n";

            }
            Log.d("output", result);
            String logfile = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Monitor/log"+System.currentTimeMillis()+".txt";
            PrintWriter writer = new PrintWriter(logfile);
            writer.print(result);
            writer.close();
        }
        catch (Exception e)
        {
            Log.d("output", "exception in runCMD");
            e.printStackTrace();
        }
    }
}
