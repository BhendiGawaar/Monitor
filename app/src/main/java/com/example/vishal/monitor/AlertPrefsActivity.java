package com.example.vishal.monitor;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class AlertPrefsActivity extends AppCompatActivity {

    public static final String TAG = "AlertPrefsActivity";
    Switch switchGlobal;
    ListView listview;
    SharedPreferences alertPrefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_prefs);

        //alertPrefs = getPreferences(MODE_APPEND);
        alertPrefs = getSharedPreferences("alertPrefs",MODE_APPEND );
        editor = alertPrefs.edit();
        listview= (ListView) findViewById(R.id.ApplistView);
        switchGlobal = (Switch) findViewById(R.id.switchGlobal);
        switchGlobal.setOnCheckedChangeListener(mainSwitchListen);

        createApplistview(this);

    }


    CompoundButton.OnCheckedChangeListener mainSwitchListen = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            if (isChecked) { //ON
                //setEnabledTotal(true);
                listview.setEnabled(true);
                editor.putBoolean("switchGlobal", true);
            } else { //OFF
                //setEnabledTotal(false);
                listview.setEnabled(false);
                editor.putBoolean("switchGlobal", false);
            }
            editor.commit();
        }
    };

    private void createApplistview(Context context)
    {
        listview.setChoiceMode(listview.CHOICE_MODE_MULTIPLE);


        //final LinkedList<String> packlist = Util.getInstalledApps(this);//fetches all location using apps
        Monitordb dbHelper = new Monitordb(context);
        final LinkedList<String> packlist = dbHelper.getInjectedApps();
        dbHelper.close();
        String appArray[] = new String[packlist.size()];
        HashMap<String, Integer> packNamesMap = new HashMap<String, Integer>();
        for(int i=0;i<packlist.size();i++)
        {
            appArray[i] = getAppName(packlist.get(i),this);
            packNamesMap.put(packlist.get(i),i);
        }
       //String appArray[] = (String [])applist1.toArray();

        //listview.setAdapter(new SimpleAdapter(this,datalist,android.R.layout.simple_list_item_checked,from,new int[] {android.R.id.text1,
        //        android.R.id.text2}));

        listview.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_checked,appArray));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView item = (CheckedTextView) view;
                //Toast.makeToast(this, city[position] + " checked : " +item.isChecked(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onItemClick: "+packlist.get(position) + " checked : " +item.isChecked());
                editor.putBoolean(packlist.get(position), item.isChecked());
                editor.commit();
            }
        });

        //Initialize list with saved preferences
        Map<String, Boolean> prefs = (Map<String, Boolean>) alertPrefs.getAll();
        Log.d(TAG, "createApplistview: "+ prefs.toString());
        if(prefs.containsKey("switchGlobal"))
            switchGlobal.setChecked(prefs.get("switchGlobal"));
            //switchGlobal.setEnabled(prefs.get("switchGlobal"));
        int count = listview.getCount();
        for(int i=0;i<count;i++)
        {
            if(prefs.containsKey(packlist.get(i)))
                listview.setItemChecked(i,prefs.get(packlist.get(i)));
        }
    }

    static public String getAppName(String packName, Context context) {

        final PackageManager pm = context.getApplicationContext().getPackageManager();
        ApplicationInfo ai;
        try {
            ai = pm.getApplicationInfo(packName, 0);
        } catch (final PackageManager.NameNotFoundException e) {
            ai = null;
        }
        String appName = (String) (ai != null ? pm.getApplicationLabel(ai) : packName);

        return appName;
    }
}
