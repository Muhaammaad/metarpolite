package com.muhaammaad.metarpolite.ui.main.viewmodel;

//region Imports

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.AndroidViewModel;

import com.muhaammaad.metarpolite.global.util.AviationDataUtil;
import com.muhaammaad.metarpolite.global.util.SingleLiveEvent;
import com.muhaammaad.metarpolite.model.AviationData;
import com.muhaammaad.metarpolite.network.NetworkApi;
import com.muhaammaad.metarpolite.persistence.entity.Metar;
import com.muhaammaad.metarpolite.persistence.entity.MetarAndStation;
import com.muhaammaad.metarpolite.persistence.entity.Station;
import com.muhaammaad.metarpolite.persistence.repo.AviationRepo;

import javax.inject.Inject;
//endregion

public class MainViewModel extends AndroidViewModel {

    //region Globar Members
//    private AviationData mAviationResponse = new AviationData(); // Overall aviation data responsible object
    public ObservableArrayList<Metar> mMetars = new ObservableArrayList<>(); // metars to be displayed data responsible list object
    public SingleLiveEvent<Boolean> loading = new SingleLiveEvent<>(); // metars to be displayed data responsible list object
    private AviationData aviationData = new AviationData(); // Overall aviation data
    private AviationDataUtil aviationDataUtil;
    private NetworkApi networkApi;
    private AviationRepo database;

    //endregion
    //region Constructors

    /**
     * default viewModelConstructor
     *
     * @param application context of app
     */
    @Inject
    public MainViewModel(@NonNull Application application, @NonNull NetworkApi networkApi, @NonNull AviationRepo repo, @NonNull AviationDataUtil aviationDataUtil) {
        super(application);
        this.networkApi = networkApi;
        this.database = repo;
        this.aviationDataUtil = aviationDataUtil;
        loading.setValue(true);
        getAndObserveAviationDB();
        getAviationData();
    }

    private void getAndObserveAviationDB() {
        database.getMetarAndStationList().observeForever(metars -> {
            for (MetarAndStation metarAndStation : metars)
                aviationData.metars.add(metarAndStation.getMetarWithStation());
            mMetars.addAll(aviationData.metars);
        });
        database.getAllStations().observeForever(stations -> {
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
        aviationDataUtil.getAviationDataFromNetwork(null);
        loading.setValue(false);
    }


    //endregion
}

