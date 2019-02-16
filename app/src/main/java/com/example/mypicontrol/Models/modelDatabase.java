package com.example.mypicontrol.Models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class modelDatabase {
    public int Humidity;
    public int LightLevel;
    public int Temperature;
    public int Ultrasonic;


    public modelDatabase() {
    }

    public modelDatabase(int Humidity, int LightLevel, int temperatureValue, int ultrasonicValue) {
        this.Humidity = Humidity;
        this.LightLevel = LightLevel;
        this.Temperature = temperatureValue;
        this.Ultrasonic = ultrasonicValue;

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Humidity", Humidity);
        result.put("LightLevel", LightLevel);
        result.put("Ultrasonic", Temperature);
        result.put("Temperature", Ultrasonic);


        return result;
    }
}