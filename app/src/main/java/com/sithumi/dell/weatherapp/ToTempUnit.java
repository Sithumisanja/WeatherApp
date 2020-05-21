package com.sithumi.dell.weatherapp;

import static java.lang.Double.parseDouble;

public class ToTempUnit {
    public String toFahrenheit(String temp){
        Double temp1 = parseDouble(temp);
        Double temp3=temp1-273.15;
        Double temp2=(temp3*1.8);
        temp1=temp2+32.00;
        String tempval = (String.format("%.2f", (temp1))) + " F";
        return tempval;
    }
    public String toCelsius(String temp){
        Double temp1 = parseDouble(temp);
        String tempval = (String.format("%.2f", (temp1 - 273.15))) + " C";
        return tempval;
    }
}
