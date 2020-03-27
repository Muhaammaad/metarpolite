package com.muhaammaad.metarpolite.di.module;

import android.content.Context;

import com.muhaammaad.metarpolite.di.annotation.ApplicationContext;
import com.muhaammaad.metarpolite.global.MyApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final MyApplication mApplication;

    public ApplicationModule(MyApplication app) {
        mApplication = app;
    }

    @Singleton
    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Singleton
    @Provides
    MyApplication provideApplication() {
        return mApplication;
    }
}
