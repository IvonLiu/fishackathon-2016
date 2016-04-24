package com.example.android.camera2basic;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Owner on 4/23/2016.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
