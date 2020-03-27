package com.muhaammaad.metarpolite.global;

import android.app.Activity;
import android.app.Application;

import androidx.work.Configuration;
import androidx.work.WorkManager;

import com.muhaammaad.metarpolite.di.AppInjector;
import com.muhaammaad.metarpolite.di.factory.SimpleWorkerFactory;
import com.muhaammaad.metarpolite.manager.MetarFetcher;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Application Class
 */
public class MyApplication extends Application implements HasActivityInjector {

    @Inject
    protected DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Inject
    SimpleWorkerFactory factory;

    @Override
    public void onCreate() {
        super.onCreate();
        AppInjector.init(this);
        configureWorkManager();
        MetarFetcher.setWork(this);
    }

    private void configureWorkManager() {
        Configuration config = new Configuration.Builder()
                .setWorkerFactory(factory)
                .build();

        WorkManager.initialize(this, config);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
