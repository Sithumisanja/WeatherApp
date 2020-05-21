package com.sithumi.dell.weatherapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
//import android.widget.Toolbar;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.lang.Double.parseDouble;


public class MainActivity extends AppCompatActivity {

    ListView mylist;
    int [] img1=new int[5];

    String [] dayOfWeek=new String[5];

    String [] weather_status=new String[5];

    String [] temp=new String[5];

    String [] date=new String[5];

    String [] humidity=new String[5];

    String forecastJsonStr=null;
    String cityname=null;
    private int dayOftheWeek;
    int dayofweekinteger;
    String tempmode;
    private String city_name_selected="Colombo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get values from settings
        Intent get_msgs=getIntent();
        tempmode=get_msgs.getStringExtra("tempmode");
        city_name_selected=get_msgs.getStringExtra("city_name");

        if(city_name_selected!=null){
        Toast.makeText(getApplicationContext(),city_name_selected,Toast.LENGTH_LONG).show();}
        else{
            city_name_selected="Colombo";
            //Toast.makeText(getApplicationContext(),city_name_selected,Toast.LENGTH_LONG).show();
        }

        //selecting the temperature unit
        if(tempmode==null){
            tempmode="celsius";
            //Toast.makeText(getApplicationContext(),tempmode+"_default",Toast.LENGTH_LONG).show();
        }
        /*else if(tempmode.equals("celsius")){
            Toast.makeText(getApplicationContext(),tempmode,Toast.LENGTH_LONG).show();
        }
        else if(tempmode.equals("fahrenheit")){
            Toast.makeText(getApplicationContext(),tempmode,Toast.LENGTH_LONG).show();
        }*/
        /*else {
            Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();
        }*/

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        FetchData fetchData=new FetchData();
        fetchData.execute();

    }

    public void linkAdapter(){
        mylist = (ListView) findViewById(R.id.list);
        Adapter1 adapter = new Adapter1(this,img1, dayOfWeek, weather_status, temp, date, humidity,cityname);
        mylist.setAdapter(adapter);

        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Integer Selectedimage=img1[+position];
                //String Selectedday=dayOfWeek[+position];
                String Selectedweather= weather_status[+position];
                String Selectedtemp= temp[+position];
                String Selecteddate=date[+position];
                String Selectedhumidity=humidity[+position];
                String Selectedlocation=cityname;
                // Toast.makeText(getApplicationContext(), Selecteditem, Toast.LENGTH_SHORT).show();

                //trying to send to a new activity
                Intent second = new Intent(getApplicationContext(),ViewDetails.class);
                second.putExtra("Selected_image",Selectedimage);
                //second.putExtra("Selected_day",Selectedday);
                second.putExtra("Selected_weather",Selectedweather);
                second.putExtra("Selected_temp",Selectedtemp);
                second.putExtra("Selected_date",Selecteddate);
                second.putExtra("Selected_humidity",Selectedhumidity);
                second.putExtra("Location",Selectedlocation);

                startActivity(second);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent settings=new Intent(getApplicationContext(),Settings.class);
                settings.putExtra("city_name",city_name_selected);
                settings.putExtra("tempmode",tempmode);
                startActivity(settings);
                return true;
            case R.id.action_about:
                Intent about=new Intent(getApplicationContext(),About.class);
                startActivity(about);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
    public class FetchData extends AsyncTask<String, Void, String> {

        HttpURLConnection urlConnection=null;
        BufferedReader reader=null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {

          linkAdapter();

        }

        @Override
        protected String doInBackground(String... strings) {

            try {

                final String BASE_URL="http://api.openweathermap.org/data/2.5/forecast?q="+city_name_selected+"&appid=e0cdb2726c35feaa6347cf460bbcaf7f";
                URL url = new URL(BASE_URL);

                Log.i("Hi","Trying to connect");
                urlConnection=(HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                Log.i("Hi","Getting input stream");
                InputStream inputStream=urlConnection.getInputStream();
                StringBuffer buffer=new StringBuffer();

                if(inputStream == null){
                    Log.i("Hi","Input stream null");
                    return null;
                }

                reader=new BufferedReader(new InputStreamReader(inputStream));
                Log.i("Hi","Reading buffer");
                String line1;
                while ((line1=reader.readLine())!=null){
                    buffer.append(line1+"\n");
                    Log.i("Hi","Inside while loop of buffer");
                }

                if (buffer.length()==0){
                    Log.i("Hi","buffer length zero");
                    return null;
                }

                forecastJsonStr=buffer.toString();
                Log.i("Hi","Forecasting JSON string");

                /////////////
                try {
                    JSONObject jsonObject = new JSONObject(forecastJsonStr);

                    //call the key and get the value
                    //String base=jsonObject.getString("base");

                    JSONObject city=jsonObject.getJSONObject("city");
                    cityname=city.getString("name");
                    //txtTitle.setText(cityname);

                    Log.i("Hi","Trying to access JSONarray");
                    JSONArray list=jsonObject.getJSONArray("list");

                    //
                    int y=0;
                    for(int x=1;x<35;x+=8) {
                        JSONObject object = list.getJSONObject(x);
                        String dateval = object.getString("dt_txt");
                        JSONObject main = object.getJSONObject("main");
                        String temperature = main.getString("temp");
                        String humidityval = main.getString("humidity");
                        JSONArray weather = object.getJSONArray("weather");
                        JSONObject zero = weather.getJSONObject(0);
                        String weathermain = zero.getString("main");
                        String weather_des = zero.getString("description");
                        //String icon=zero.getString("icon");

                        Log.i("Hi", "Inside loop with index x:" + x + "and y" + y);
                        date[y] = dateval;
                        humidity[y] = humidityval;
                        weather_status[y] = weathermain + ": " + weather_des;

                        Log.i("Hi", "Temp conversion start"+ tempmode);
                        ToTempUnit toTempUnit=new ToTempUnit();
                        if(tempmode.equals("celsius")){
                           String tempval=toTempUnit.toCelsius(temperature);
                            Log.i("Hi", tempval);
                           temp[y]=tempval;
                        }
                        else if(tempmode.equals("fahrenheit")) {
                           String tempval= toTempUnit.toFahrenheit(temperature);
                            Log.i("Hi", tempval);
                           temp[y]=tempval;
                        }

                        //dayOfWeek[y]= String.valueOf(toDate(dateval));
                        ToDate obj=new ToDate();
                        dayofweekinteger=obj.toDate(dateval);


                        Log.i("Hi", "Initiate switch of day of week");
                        switch (dayofweekinteger) {
                            case 1:
                                dayOfWeek[y] = "Sunday";
                                break;
                            case 2:
                                dayOfWeek[y] = "Monday";
                                break;
                            case 3:
                                dayOfWeek[y] = "Tuesday";
                                break;
                            case 4:
                                dayOfWeek[y] = "Wednesday";
                                break;
                            case 5:
                                dayOfWeek[y] = "Thursday";
                                break;
                            case 6:
                                dayOfWeek[y] = "Friday";
                                break;
                            case 7:
                                dayOfWeek[y] = "Saturday";
                                break;
                            default:
                                dayOfWeek[y] = "Error";

                        }

                        Log.i("Hi", "Initiate switch of weather status");
                        switch (weather_status[y]) {
                            case "Rain: moderate rain":
                                img1[y] = R.drawable.a10d;
                                break;
                            case "Rain: light rain":
                                img1[y] = R.drawable.a09d;
                                break;
                            case "Rain: heavy intensity rain":
                                img1[y] = R.drawable.a09n;
                                break;
                            case "Clear: clear sky":
                                img1[y]=R.drawable.a01d;
                                break;
                            case "Clouds: scattered clouds":
                                img1[y]=R.drawable.a04d;
                                break;
                            case "Clouds: overcast clouds":
                                img1[y]=R.drawable.a04d;
                                break;
                            case "Clouds: broken clouds":
                                img1[y]=R.drawable.a03n;
                                break;
                            case "Clouds: few clouds":
                                img1[y]=R.drawable.a03n;
                                break;
                            case "Snow: light snow":
                                img1[y]=R.drawable.a13n;
                                break;
                            case "Snow: snow":
                                break;
                            default:
                                img1[y] = R.drawable.a01d;
                        }
                        y++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ///////////////

            }
            catch (IOException e){
                Log.e("Hi","Error",e);
                city_name_selected="Colombo";

                Intent mistake=new Intent(getApplicationContext(),Settings.class);
                mistake.putExtra("text","Invalid city");
                startActivity(mistake);
                android.os.Process.killProcess(android.os.Process.myPid());

                //FetchData fetchData=new FetchData();
                //fetchData.execute();
                //Toast.makeText(getApplicationContext(),"Sorry this country is not available",Toast.LENGTH_SHORT).show();

                return null;
            }
            finally {
                if (urlConnection != null) {
                    Log.i("Hi","URL Connection is not null");
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Hi", "Error closing stream");
                    }
                }
            }
            return null;

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


}