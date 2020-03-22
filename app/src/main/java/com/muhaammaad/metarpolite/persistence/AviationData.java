
package com.muhaammaad.metarpolite.persistence;

import com.muhaammaad.metarpolite.model.type.CountryType;
import com.muhaammaad.metarpolite.model.type.DataSourceType;
import com.muhaammaad.metarpolite.model.type.FormatType;
import com.muhaammaad.metarpolite.model.type.RequestType;
import com.muhaammaad.metarpolite.network.NetworkModule;
import com.muhaammaad.metarpolite.persistence.entity.Metar;
import com.muhaammaad.metarpolite.persistence.entity.Station;
import com.muhaammaad.metarpolite.persistence.repo.AviationRepo;
import com.muhaammaad.metarpolite.ui.main.util.AviationResponseParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Aviation Data Helper to hold all the metars and stations
 */
public class AviationData {
    public List<Metar> metars = new ArrayList<>();
    public HashMap<String, Station> stations = new HashMap<>();

    /**
     * Gets the data of all stations and metars. Once fetched, both are zipped and saved into Database.
     */
    public static void getAviationDataFromNetwork(Observer<? super AviationData> callback) {
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
            return aviationDataResponse;
        });

        if (callback != null)
            combinedAviationResponse.subscribe(callback);
        else
            combinedAviationResponse.subscribe();
    }
}