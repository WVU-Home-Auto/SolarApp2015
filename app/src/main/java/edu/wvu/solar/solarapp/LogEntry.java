package edu.wvu.solar.solarapp;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 * Created by Timmy on 9/28/15.
 */
public class LogEntry {

    public static final DateTimeFormatter TIME_FORMAT = ISODateTimeFormat.basicDateTimeNoMillis();

    private double temp;
    private double humidity;
    private String time;

    /**
     *
     * @param temp Temperature IN FAHRENHEIT
     * @param humidity Humidity in percentage (0.0 to 100.0)
     */
    public LogEntry(double temp, double humidity) {
        super();
        this.temp = temp;
        this.humidity = humidity;
        time = new DateTime().toString(TIME_FORMAT);
    }

    /**
     *
     * @param temp Temperature IN FAHRENHEIT
     * @param humidity Humidity in percentage (0.0 to 100.0)
     * @param time Time formatted according to LogEntry.TIME_FORMAT
     */
    public LogEntry(double temp, double humidity, String time){
        this.temp = temp;
        this.humidity = humidity;
        this.time = time;
    }

    /**
     * @return Temperature in Fahrenheit
     */
    public double getTemp() {
        return temp;
    }

    /**
     * @return Relative humidity in percentage (0.0 to 100.0)
     */
    public double getHumidity() {
        return humidity;
    }

    public DateTime getTimeAsDateTime(){
        return DateTime.parse(time, TIME_FORMAT);
    }

    public String getTime(){
        return time;
    }

    @Override
    public String toString(){
        return "Temp: " + temp + " Humidity: " + humidity + " Time: " + time;
    }
}
