package com.sithumi.dell.weatherapp;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Adapter2 extends ArrayAdapter<String>{
    private final Activity context;
    private final String text1[];
    private final String text2[];

    public Adapter2(Activity context, String[] text1, String[] text2) {
        super(context,R.layout.mylist2,text1);
        this.context = context;
        this.text1 = text1;
        this.text2 = text2;
    }

    public View getView(int position, View View1, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist2,null,true);

        TextView textView1=(TextView) rowView.findViewById(R.id.text1);
        TextView textView2=(TextView) rowView.findViewById(R.id.text2);

        textView1.setText(text1[position]);
        textView2.setText(text2[position]);
        Log.i("Hi","In adapter 2 all okay");
        return rowView;
    }
}
