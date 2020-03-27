package com.muhaammaad.metarpolite.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.muhaammaad.metarpolite.di.factory.ViewModelFactory;
import com.muhaammaad.metarpolite.di.annotation.ViewModelKey;
import com.muhaammaad.metarpolite.ui.detail.viewmodel.MetarDetailViewModel;
import com.muhaammaad.metarpolite.ui.main.viewmodel.MainViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindListViewModel(MainViewModel listViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MetarDetailViewModel.class)
    abstract ViewModel bindDetailViewModel(MetarDetailViewModel listViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
