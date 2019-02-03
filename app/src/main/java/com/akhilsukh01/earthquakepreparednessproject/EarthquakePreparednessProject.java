package com.akhilsukh01.earthquakepreparednessproject;

import android.app.Application;

import com.firebase.client.Firebase;


public class EarthquakePreparednessProject extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
