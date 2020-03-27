package com.muhaammaad.metarpolite.di.module;


import com.muhaammaad.metarpolite.di.annotation.WorkerKey;
import com.muhaammaad.metarpolite.di.factory.ChildWorkerFactory;
import com.muhaammaad.metarpolite.manager.MetarFetcher;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public interface WorkerBindingModule {
    @Binds
    @IntoMap
    @WorkerKey(MetarFetcher.class)
    ChildWorkerFactory bindWorker(MetarFetcher.Factory factory);
}