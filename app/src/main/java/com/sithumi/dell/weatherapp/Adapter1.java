package com.sithumi.dell.weatherapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter1 extends ArrayAdapter<String> {
    private final Activity context;
    private final int [] img1;
    private final String [] dayOfWeek;
    private final String [] weather_status;
    private final String [] temp;
    private final String [] date;
    private final String [] humidity;
    private final String cityname;

    public Adapter1(Activity context, int[] img1, String[] dayOfWeek, String[] weather_status, String[] temp, String[] date, String[] humidity, String cityname){
        super(context,R.layout.mylist,dayOfWeek);
        this.context=context;
    this.dayOfWeek=dayOfWeek;
    this.weather_status=weather_status;
    this.temp=temp;
    this.img1=img1;
    this.date=date;
    this.humidity=humidity;
    this.cityname = cityname;

    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);
        TextView dayOfweek= (TextView) rowView.findViewById(R.id.dayofweek);
        TextView weatherstatus=(TextView) rowView.findViewById(R.id.weatherstatus);
        ImageView image=(ImageView) rowView.findViewById(R.id.img);
        TextView temperature=(TextView) rowView.findViewById(R.id.temp);


        dayOfweek.setText(dayOfWeek[position]);
        weatherstatus.setText(weather_status[position]);
        image.setImageResource(img1[position]);
        temperature.setText(temp[position]);
        //put set text for each of the above
        return rowView;
    }

}
