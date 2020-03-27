package com.muhaammaad.metarpolite.di;

import android.content.Context;

import androidx.work.Configuration;
import androidx.work.WorkManager;
import androidx.work.WorkerFactory;

import com.muhaammaad.metarpolite.di.component.DaggerAppComponent;
import com.muhaammaad.metarpolite.di.module.ApplicationModule;
import com.muhaammaad.metarpolite.global.MyApplication;

public class AppInjector {

    public static void init(MyApplication application) {
        DaggerAppComponent.builder()
                .application(application)
                .appModule(new ApplicationModule(application))
                .build().inject(application);
    }

//    public static void init(MyApplication application, WorkerFactory factory) {
//        DaggerAppComponent.builder()
//                .application(application)
//                .appModule(new ApplicationModule(application))
//                .build().inject(application);
//        configureWorkManager(application, factory);
//    }
//
//
//    private static void configureWorkManager(Context context, WorkerFactory factory) {
//        Configuration config = new Configuration.Builder()
//                .setWorkerFactory(factory)
//                .build();
//
//        WorkManager.initialize(context, config);
//    }
}
