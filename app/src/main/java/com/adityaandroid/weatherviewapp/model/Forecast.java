package com.adityaandroid.weatherviewapp.model;



import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;



public class Forecast {
    public final String forecastDay;
    public final String forecastTime;
    public final String weatherDescription;
    public final String lowTemp;
    public final String highTemp;
    public final String humidity;
    public final String windSpeed;
    public final String iconUrl;

    public Forecast(long timeStamp, String weatherDescription, double  lowTemp, double  highTemp, int  humidity, double  windSpeed, String iconName, String forecastTime) {
        // NumberFormat to format double temperatures rounded to integers
        NumberFormat numberFormat = NumberFormat.getInstance();

        this.forecastDay = convertTimeStampToDay(timeStamp);
        this.forecastTime = forecastTime;
        this.weatherDescription = weatherDescription;
        this.lowTemp = numberFormat.format(lowTemp)+ "\u00B0C";
        this.highTemp = numberFormat.format(highTemp)+ "\u00B0C";
        this.humidity = NumberFormat.getPercentInstance().format(humidity/100.0);
        this.windSpeed = String.format("%s m/s" ,numberFormat.format(windSpeed));
        this.iconUrl = "http://openweathermap.org/img/w/" + iconName + ".png";

    }

    public String getForecastDay() {
        return forecastDay;
    }

    public String getForecastTime() {
        return forecastTime;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public String getLowTemp() {
        return lowTemp;
    }

    public String getHighTemp() {
        return highTemp;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getIconUrl() {
        return iconUrl;
    }


    private String convertTimeStampToDay(long timeStamp) {
        // convert timestamp to a day's name (e.g., Monday, Tuesday, ...)
        Calendar calendar = Calendar.getInstance(); // create Calendar
        calendar.setTimeInMillis(timeStamp * 1000); // set time
        TimeZone tz = TimeZone.getDefault();// get device's time zone
        // adjust time for device's time zone
        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
        // SimpleDateFormat that returns the day's name
        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE");//EEEE formats date for day name eg. Monday
        return dateFormatter.format(calendar.getTime());
    }
}
