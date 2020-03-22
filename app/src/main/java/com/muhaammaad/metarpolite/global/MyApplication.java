package com.muhaammaad.metarpolite.global;

import android.app.Application;
import android.content.Context;

import com.muhaammaad.metarpolite.manager.MetarFetcher;

/**
 * Application Class
 */
public class MyApplication extends Application {
    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        MetarFetcher.setWork(this);
    }
}
