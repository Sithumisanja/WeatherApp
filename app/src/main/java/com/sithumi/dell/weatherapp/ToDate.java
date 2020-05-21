package com.sithumi.dell.weatherapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ToDate {
    public int toDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //new SimpleDateFormat("EE").format(date);
        Date datex = null;
        int dayOftheweek=0;

        try {
            datex = dateFormat.parse(dateString);
            Calendar c = Calendar.getInstance();
            c.setTime(datex);
            dayOftheweek = c.get(Calendar.DAY_OF_WEEK);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dayOftheweek;
    }
}
