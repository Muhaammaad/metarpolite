package com.muhaammaad.metarpolite.di.module;

import com.muhaammaad.metarpolite.ui.detail.view.MetarDetailActivity;
import com.muhaammaad.metarpolite.ui.main.view.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = {MainFragmentBindingModule.class})
    abstract MainActivity bindMainActivity();


    @ContributesAndroidInjector(modules = {MainFragmentBindingModule.class})
    abstract MetarDetailActivity bindDetailsActivity();
}