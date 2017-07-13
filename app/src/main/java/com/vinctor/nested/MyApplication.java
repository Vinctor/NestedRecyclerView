package com.vinctor.nested;

import android.app.Application;
import android.content.Context;


public class MyApplication extends Application {

    static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        init();
    }

    public static Context getIntance() {
        return appContext;
    }


    private void init() {
    }
}
