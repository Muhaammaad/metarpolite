package com.muhaammaad.metarpolite.ui.main.viewmodel;

//region Imports

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.AndroidViewModel;

import com.muhaammaad.metarpolite.model.type.CountryType;
import com.muhaammaad.metarpolite.model.type.DataSourceType;
import com.muhaammaad.metarpolite.model.type.FormatType;
import com.muhaammaad.metarpolite.model.type.RequestType;
import com.muhaammaad.metarpolite.network.NetworkModule;
import com.muhaammaad.metarpolite.persistence.AviationData;
import com.muhaammaad.metarpolite.persistence.entity.Metar;
import com.muhaammaad.metarpolite.persistence.entity.MetarAndStation;
import com.muhaammaad.metarpolite.persistence.entity.Station;
import com.muhaammaad.metarpolite.persistence.repo.AviationRepo;
import com.muhaammaad.metarpolite.ui.main.util.AviationResponseParser;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
//endregion

public class MainViewModel extends AndroidViewModel implements Observer<AviationData> {

    //region Globar Members
//    private AviationData mAviationResponse = new AviationData(); // Overall aviation data responsible object
    public ObservableArrayList<Metar> mMetars = new ObservableArrayList<>(); // metars to be displayed data responsible list object
    //endregion
    //region Constructors

    /**
     * default viewModelConstructor
     *
     * @param application context of app
     */
    public MainViewModel(@NonNull Application application) {
        super(application);
        getAviationData();
    }

    //endregion
    //region Member Functions

    /**
     * Gets the data of all stations and metars. Once fetched, both are zipped into {@link #mMetars}.
     */
    private void getAviationData() {

        Observable<ResponseBody> observableMetar = NetworkModule.getInstance().provideNetworkApi().getAviationData(DataSourceType.Metars.toString(), RequestType.Retrieve.toString(), FormatType.XML.toString(), CountryType.GERMANY.toStringWtihTilda(), "1")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<ResponseBody> observableStations = NetworkModule.getInstance().provideNetworkApi().getAviationData(DataSourceType.Stations.toString(), RequestType.Retrieve.toString(), FormatType.XML.toString(), CountryType.GERMANY.toStringWtihTilda(), "1")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<AviationData> combinedAviationResponse = Observable.zip(observableMetar, observableStations, (metarResponseBody, stationResponseBody) -> {
            AviationData aviationDataResponse = new AviationData(); // current aviation data responsible object
            AviationResponseParser.parseAviationResponseStream(stationResponseBody.byteStream(), aviationDataResponse);
            AviationResponseParser.parseAviationResponseStream(metarResponseBody.byteStream(), aviationDataResponse);

            AviationRepo.getInstance().insertMetars(aviationDataResponse.metars);
            AviationRepo.getInstance().insertStations(new ArrayList<>(aviationDataResponse.stations.values()));
            AviationData aviationData = new AviationData(); // Overall aviation data responsible object
            for (MetarAndStation metarAndStation : AviationRepo.getInstance().getMetarAndStationList())
                aviationData.metars.add(metarAndStation.getMetarWithStation());
            for (Station station : AviationRepo.getInstance().getAllStations())
                aviationData.stations.put(station.stationId, station);
            return aviationData;
        });

        combinedAviationResponse.subscribe(this);
    }

    //endregion
    //region Observer delegates
    @Override
    public void onSubscribe(Disposable d) {

    }

    /**
     * fetches the metars list from the {@param aviationResponse} and inserts into {@link #mMetars}
     *
     * @param aviationResponse the result emitted by observable
     */
    @Override
    public void onNext(AviationData aviationResponse) {
        mMetars.addAll(aviationResponse.metars);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
    //endregion
}

