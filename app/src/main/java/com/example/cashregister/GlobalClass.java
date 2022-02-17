package com.example.cashregister;


import android.app.Application;

import io.easyprefs.Prefs;


public class GlobalClass extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Prefs.initializeApp(this);
    }
}