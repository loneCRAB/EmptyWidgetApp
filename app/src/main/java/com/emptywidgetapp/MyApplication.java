package com.emptywidgetapp;

import com.activeandroid.ActiveAndroid;
import android.app.Application;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }

}
