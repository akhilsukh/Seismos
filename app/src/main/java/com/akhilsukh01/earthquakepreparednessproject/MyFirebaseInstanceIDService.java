package com.akhilsukh01.earthquakepreparednessproject;

import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;


public class MyFirebaseInstanceIDService extends FirebaseMessagingService {

    static String retrieveToken = "";

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("NEW_TOKEN",s);
        retrieveToken = s;
    }

}
