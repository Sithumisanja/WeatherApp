package com.sithumi.dell.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        Intent get_msgs=getIntent();
        Integer image=get_msgs.getIntExtra("Selected_image",0);
        //String day=get_msgs.getExtras().getString("Selected_day");
        String weather=get_msgs.getExtras().getString("Selected_weather");
        String temp=get_msgs.getExtras().getString("Selected_temp");
        String date=get_msgs.getExtras().getString("Selected_date");
        String humidity=get_msgs.getExtras().getString("Selected_humidity");
        String cityname=get_msgs.getExtras().getString("Location");

        ImageView txtImage=(ImageView) findViewById(R.id.txtimage);
        TextView txtweather=(TextView) findViewById(R.id.txtweatherStatus);
        TextView txttemp=(TextView) findViewById(R.id.txttemp);
        TextView txtdate=(TextView) findViewById(R.id.txtdate1);
        TextView txthumidity=(TextView) findViewById(R.id.txthumidity);
        TextView txtlocation=(TextView) findViewById(R.id.txtlocation);

        txtImage.setImageResource(image);
        txtweather.setText(weather);
        txtdate.setText(date);
        txttemp.setText(temp);
        txthumidity.setText("Humidity: "+humidity+"%");
        txtlocation.setText(cityname);

        }
}
