package edu.wvu.solar.solarapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends ActionBarActivity {

    public static final String DEBUG_TAG = "SolarAppMainActivity";
    public static final String ALARM_DOT_COM = "com.alarm.alarmmobile.android";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void buttonClick(View view){
        int id = view.getId();
        if(id == R.id.homeControlButton){
            Log.i(DEBUG_TAG, "HOME CONTROL!!!!!");
            Intent intent = getPackageManager().getLaunchIntentForPackage("com.alarm.alarmmobile.android");
            startActivity(intent);
        }else if(id == R.id.healthButton){
            Log.i(DEBUG_TAG, "HEEEEAAAALLLTTTHHHH!!!!!!");
        }else if(id == R.id.carChargerButton){
            Log.i(DEBUG_TAG, "CAR CHAAAARGER!!!");
        }else if(id == R.id.solarPanelButton){
            Log.i(DEBUG_TAG, "SOLAR PAAAAAANELS!!!");
        }else if(id == R.id.settingsButton){
            Log.i(DEBUG_TAG, "SETTTTTTTINGS!!!");
        }
        //view.setVisibility(View.INVISIBLE);
    }

    private boolean isPackageInstalled(String packagename, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
