package edu.wvu.solar.solarapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;


public class PickTimeRangeActivity extends ActionBarActivity {

    MutableDateTime startTime;
    MutableDateTime endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_time_range);

        //Initialize default duration to be 24 hours, ending now
        startTime = new MutableDateTime();
        startTime.addDays(-1);
        endTime = new MutableDateTime();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pick_time_range, menu);
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

    public void click1(View view){
        MutableDateTime time = new MutableDateTime();
        time.addDays(-1000);
        DatePickerFragment frag = new DatePickerFragment();
        frag.setTime(time);
        frag.show(getSupportFragmentManager(), "frag1");
    }

    public void click2(View view){

    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        private MutableDateTime time;

        public TimePickerFragment(){
            time = new MutableDateTime();
        }

        public void setTime(MutableDateTime time){
            this.time = time;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            if(time == null){
                time = new MutableDateTime();
            }
            int hour = time.getHourOfDay();
            int minute = time.getMinuteOfHour();

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            time.setHourOfDay(hourOfDay);
            time.setMinuteOfHour(minute);
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private MutableDateTime time;

        public DatePickerFragment(){
            time = new MutableDateTime();
        }

        public void setTime(MutableDateTime time){
            this.time = time;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            if(time == null){
                time = new MutableDateTime();
            }
            return new DatePickerDialog(getActivity(), this, time.getYear(), time.getMonthOfYear(), time.getDayOfMonth());
        }


        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        }
    }


}
