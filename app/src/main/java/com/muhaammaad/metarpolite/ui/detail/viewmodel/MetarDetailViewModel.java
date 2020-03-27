package com.muhaammaad.metarpolite.ui.detail.viewmodel;

import android.app.Application;

import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.muhaammaad.metarpolite.persistence.entity.Metar;

import javax.inject.Inject;

public class MetarDetailViewModel extends AndroidViewModel {

    public ObservableField<Metar> metar = new ObservableField<>();

    @Inject
    public MetarDetailViewModel(Application application) {
        super(application);
    }

    public void setMetar(Metar m) {
        metar.set(m);
    }
}
