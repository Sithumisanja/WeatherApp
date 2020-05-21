package com.sithumi.dell.weatherapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

public class Settings extends Activity {

    ListView list2;
    String [] text_1={
            "City",
            "Temperature Unit"
    };

    String [] text_2={
        "Select your city",
        "Select your temperature unit"
    };

    String city_name;
    String tempmode;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        linkadapter2();

        Intent get_Mainmsgs=getIntent();
        tempmode=get_Mainmsgs.getStringExtra("tempmode");
        city_name=get_Mainmsgs.getStringExtra("city_name");
        message=get_Mainmsgs.getStringExtra("text");

        if(message==null){
            message="Please select a city or temperature unit";
        }
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }
    public void linkadapter2(){
        list2=(ListView) findViewById(R.id.list2);
        Adapter2 adapter2=new Adapter2(this,text_1,text_2);
        list2.setAdapter(adapter2);

        //on click
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if(position==0 ) {
                    String Selected_text=text_1[0];
                    //Toast.makeText(getApplicationContext(), Selected_text, Toast.LENGTH_SHORT).show();
                    openDialog1();

                }
                if(position==1) {
                    String Selected_text= text_1[1];
                   // Toast.makeText(getApplicationContext(), Selected_text, Toast.LENGTH_SHORT).show();
                    opeDialog2();
                }

            }
        });
    }
    public void openDialog1(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alert;
        // Get the layout inflater
        LayoutInflater inflater = (this).getLayoutInflater();
        final View textEntryView=inflater.inflate(R.layout.city_dialog,null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(textEntryView);
        final EditText edit_city=(EditText)textEntryView.findViewById(R.id.cityname);


        // Add action buttons
                builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        final String city=edit_city.getText().toString();
                        if(city.matches("")){
                            Toast.makeText(getApplicationContext(),"Please enter a city name",Toast.LENGTH_SHORT).show();
                        }else{

                            Toast.makeText(getApplicationContext(),city,Toast.LENGTH_SHORT).show();
                            Intent main=new Intent(getApplicationContext(),MainActivity.class);
                            main.putExtra("city_name",city);
                            main.putExtra("tempmode",tempmode);
                            startActivity(main);
                        }

                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(),"Cancelled",Toast.LENGTH_SHORT).show();
                    }
                });
        alert= builder.create();
        alert.show();


    }
    public void opeDialog2(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alert;
        // Get the layout inflater
        LayoutInflater inflater = (this).getLayoutInflater();
        final View textEntryView=inflater.inflate(R.layout.temperature_dialog,null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(textEntryView);
        final RadioButton celsius=(RadioButton) textEntryView.findViewById(R.id.celsius);
        final RadioButton fahrenheit=(RadioButton) textEntryView.findViewById(R.id.fahrenheit);


        // Add action buttons
        builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                if(celsius.isChecked()){
                    Toast.makeText(getApplicationContext(),"celsius",Toast.LENGTH_SHORT).show();
                    Intent main=new Intent(getApplicationContext(),MainActivity.class);
                    main.putExtra("tempmode","celsius");
                    main.putExtra("city_name",city_name);
                    startActivity(main);
                }else if(fahrenheit.isChecked()){
                    Toast.makeText(getApplicationContext(),"fahrenheit",Toast.LENGTH_SHORT).show();
                    Intent main=new Intent(getApplicationContext(),MainActivity.class);
                    main.putExtra("tempmode","fahrenheit");
                    main.putExtra("city_name",city_name);
                    startActivity(main);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please select an option",Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getApplicationContext(),"Cancelled",Toast.LENGTH_SHORT).show();
            }
        });
        alert= builder.create();
        alert.show();

    }

}
