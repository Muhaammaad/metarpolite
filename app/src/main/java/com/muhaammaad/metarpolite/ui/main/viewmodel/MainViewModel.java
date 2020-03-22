package com.muhaammaad.metarpolite.ui.main.viewmodel;

//region Imports

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.AndroidViewModel;

import com.muhaammaad.metarpolite.global.util.SingleLiveEvent;
import com.muhaammaad.metarpolite.persistence.AviationData;
import com.muhaammaad.metarpolite.persistence.entity.Metar;
import com.muhaammaad.metarpolite.persistence.entity.MetarAndStation;
import com.muhaammaad.metarpolite.persistence.entity.Station;
import com.muhaammaad.metarpolite.persistence.repo.AviationRepo;
//endregion

public class MainViewModel extends AndroidViewModel {

    //region Globar Members
//    private AviationData mAviationResponse = new AviationData(); // Overall aviation data responsible object
    public ObservableArrayList<Metar> mMetars = new ObservableArrayList<>(); // metars to be displayed data responsible list object
    public SingleLiveEvent<Boolean> loading = new SingleLiveEvent<>(); // metars to be displayed data responsible list object
    private AviationData aviationData = new AviationData(); // Overall aviation data

    //endregion
    //region Constructors

    /**
     * default viewModelConstructor
     *
     * @param application context of app
     */
    public MainViewModel(@NonNull Application application) {
        super(application);
        loading.setValue(true);
        getAndObserveAviationDB();
        getAviationData();
    }

    private void getAndObserveAviationDB() {
        AviationRepo.getInstance().getMetarAndStationList().observeForever(metars -> {
            for (MetarAndStation metarAndStation : metars)
                aviationData.metars.add(metarAndStation.getMetarWithStation());
            mMetars.addAll(aviationData.metars);
        });
        AviationRepo.getInstance().getAllStations().observeForever(stations -> {
            for (Station station : stations)
                aviationData.stations.put(station.stationId, station);
        });
    }

    //endregion
    //region Member Functions

    /**
     * Gets the data of all stations and metars.
     */
    private void getAviationData() {
        AviationData.getAviationDataFromNetwork(null);
        loading.setValue(false);
    }


    //endregion
}

