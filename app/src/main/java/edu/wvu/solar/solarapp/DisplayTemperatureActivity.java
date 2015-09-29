package edu.wvu.solar.solarapp;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;


public class DisplayTemperatureActivity extends ActionBarActivity {

    public static final String SERVER_URL = "http://192.168.0.50";
    public static final String DEBUG_TAG = "SolarAppDisplayActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_temperature);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_temperature, menu);
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

    public class RetrieveDataTask extends AsyncTask<String, Void, LinkedList<LogEntry>> {

        @Override
        protected LinkedList<LogEntry> doInBackground(String... params) {
            String sensorName = params[0];
            String startTime = params[1];
            String endTime = params[2];
            try {
                HttpResponse<JsonNode> response = Unirest.get(SERVER_URL + "/getdata").queryString("startTime", startTime)
                        .queryString("endTime", endTime).queryString("sensorName", sensorName).asJson();
                LinkedList<LogEntry> list = new LinkedList<LogEntry>();
                JSONArray array = response.getBody().getArray();
                for(int i = 0; i < array.length(); i++){
                    JSONObject obj = null;
                    try {
                        obj = array.getJSONObject(i);
                        list.add(new LogEntry(obj.getDouble("temp"), obj.getDouble("humidity"), obj.getString("time")));
                    }catch(JSONException e){
                        Log.e(DEBUG_TAG, "Error processing JSON. Object: " + obj.toString());
                        Log.e(DEBUG_TAG, "Exception: " + e.getMessage());
                    }
                }
                return list;
            } catch (UnirestException e) {
                Log.e(DEBUG_TAG, e.getMessage());
                return new LinkedList<LogEntry>();
            }
        }

        @Override
        protected void onPostExecute(LinkedList<LogEntry> result){

        }
    }
}
