package edu.wvu.solar.solarapp;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.apache.http.util.ExceptionUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedList;


public class DisplayTemperatureActivity extends Activity {

    //public static final String SERVER_URL = "http://192.168.0.102:8081";
    //public static final String SERVER_URL = "http://localhost:8081";
    public static final String SERVER_URL = "http://10.255.94.7:8081";
    public static final String DEBUG_TAG = "SolarAppDisplayActivity";

    LinearLayout baseLayout;
    LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_temperature);
        baseLayout = (LinearLayout) findViewById(R.id.baseLayout);
        chart = new LineChart(this);
        chart.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        baseLayout.addView(chart);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_temperature, menu);
        return true;
    }

    @Override
    public void onStart(){
        super.onStart();
        String startTime = "20150901T161956-0400";
        String endTime = "20150901T162036-0400";
        String sensorName = "indoor1";
        showHistory(startTime, endTime, sensorName, chart);

    }

    private void showHistory(String startTime, String endTime, String sensorName, LineChart chart){
        String url = SERVER_URL + "/getdata?startTime=" + startTime + "&endTime=" + endTime + "&sensorName=" + sensorName;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new JsonResponse(chart), new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(DEBUG_TAG, "ERROR! ", error);
            }
        });
        Volley.newRequestQueue(this).add(request);
    }

    private class JsonResponse implements Response.Listener<JSONArray>{

        private LineChart chart;

        public JsonResponse(LineChart chart){
            this.chart = chart;
        }

        @Override
        public void onResponse(JSONArray response) {
            Log.i(DEBUG_TAG, "Response! " + response.toString());
            ArrayList<String> xLabels = new ArrayList<String>();
            ArrayList<Entry> tempEntries = new ArrayList<Entry>();
            ArrayList<Entry> humidEntries = new ArrayList<Entry>();
            for(int i = 0; i < response.length(); i++){
                try {
                    //Log.i(DEBUG_TAG, response.getJSONObject(i).toString());
                    JSONObject obj = response.getJSONObject(i);
                    LogEntry entry = new LogEntry(obj.getDouble("temp"), obj.getDouble("humidity"), obj.getString("time"));
                    xLabels.add(entry.getTimeAsDateTime().toString("MM/dd, hh:mm aa"));
                    tempEntries.add(new Entry((float)entry.getTemp(), i));
                    humidEntries.add(new Entry((float) entry.getHumidity(), i));

                    Log.i(DEBUG_TAG, entry.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            LineDataSet tempSet = new LineDataSet(tempEntries, "Temperature (f)");
            LineDataSet humidSet = new LineDataSet(humidEntries, "Humidity (%)");
            tempSet.setColor(R.color.icon_blue);
            humidSet.setColor(R.color.icon_gray);
            ArrayList<LineDataSet> sets = new ArrayList<LineDataSet>();
            sets.add(tempSet);
            sets.add(humidSet);
            LineData data = new LineData(xLabels, sets);
            Legend l = chart.getLegend();
            l.setCustom(new int[]{R.color.icon_blue,R.color.icon_gray},new String[] {"Temperature (F)","Humidity (%)"});
            chart.setData(data);
            chart.invalidate();

        }
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
}
