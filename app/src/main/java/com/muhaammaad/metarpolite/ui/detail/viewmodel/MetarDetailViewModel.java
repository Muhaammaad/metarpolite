package com.muhaammaad.metarpolite.ui.detail.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.muhaammaad.metarpolite.persistence.entity.Metar;

public class MetarDetailViewModel extends AndroidViewModel {

    public ObservableField<Metar> metar = new ObservableField<>();

    public MetarDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void setMetar(Metar m) {
        metar.set(m);
    }
}
