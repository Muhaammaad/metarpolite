package com.muhaammaad.metarpolite.ui.main.viewmodel;

//region Imports

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
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

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
//endregion

public class MainViewModel extends AndroidViewModel {

    //region Globar Members
    public ObservableArrayList<Metar> mMetars = new ObservableArrayList<>(); // metars to be displayed data responsible list object
    public SingleLiveEvent<Boolean> loading = new SingleLiveEvent<>(); // metars to be displayed data responsible list object
    public ObservableBoolean showProgress = new ObservableBoolean(); // metars to be displayed data responsible list object
    private AviationData aviationData = new AviationData(); // Overall aviation data
    private AviationDataUtil aviationDataUtil;
    private NetworkApi networkApi;
    private AviationRepo database;

    //endregion
    //region Constructors

    /**
     * Default viewModelConstructor
     *
     * @param application      context of app
     * @param networkApi       injected instance for Network Api
     * @param repo             injected instance for Database Api
     * @param aviationDataUtil injected instance for
     */
    @Inject
    MainViewModel(@NonNull Application application, @NonNull NetworkApi networkApi, @NonNull AviationRepo repo, @NonNull AviationDataUtil aviationDataUtil) {
        super(application);
        this.networkApi = networkApi;
        this.database = repo;
        this.aviationDataUtil = aviationDataUtil;
        loadData();
    }
    //endregion
    //region Member Functions

    /**
     * Data loading function.
     * Loads from database and network
     */
    private void loadData() {
        isLoading(true);
        observeAviationDB();
        getAviationData();
    }

    /**
     * Gets the data of all stations and metars.
     */
    private void getAviationData() {
        aviationDataUtil.getAviationDataFromNetwork(new Observer<AviationData>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(AviationData aviationData) {

            }

            @Override
            public void onError(Throwable e) {
                isLoading(false);

            }

            @Override
            public void onComplete() {
                isLoading(false);
            }
        });
    }

    /**
     * Sets the observers to listen database changes
     */
    private void observeAviationDB() {
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

    /**
     * Loading states handler function
     */
    private void isLoading(boolean isLoading) {
        loading.postValue(isLoading);
        showProgress.set(isLoading);
    }

    //endregion
}

