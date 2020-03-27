package com.muhaammaad.metarpolite.di.module;

import com.muhaammaad.metarpolite.ui.main.view.MetarListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBindingModule {
    @ContributesAndroidInjector
    abstract MetarListFragment provideListFragment();
}