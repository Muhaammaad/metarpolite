package com.muhaammaad.metarpolite.global.util;

import com.muhaammaad.metarpolite.model.AviationData;
import com.muhaammaad.metarpolite.model.type.CountryType;
import com.muhaammaad.metarpolite.model.type.DataSourceType;
import com.muhaammaad.metarpolite.model.type.FormatType;
import com.muhaammaad.metarpolite.model.type.RequestType;
import com.muhaammaad.metarpolite.network.NetworkApi;
import com.muhaammaad.metarpolite.persistence.repo.AviationRepo;
import com.muhaammaad.metarpolite.ui.main.util.AviationResponseParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

@Singleton
public class AviationDataUtil {

    private NetworkApi networkApi;
    private AviationRepo database;

    @Inject
    AviationDataUtil(NetworkApi api, AviationRepo db) {
        networkApi = api;
        database = db;
    }

    /**
     * Gets the data of all stations and metars. Once fetched, both are zipped and saved into Database.
     */
    public void getAviationDataFromNetwork(Observer<? super AviationData> callback) {
        Observable<ResponseBody> observableMetar = networkApi.getAviationData(DataSourceType.Metars.toString(), RequestType.Retrieve.toString(), FormatType.XML.toString(), CountryType.GERMANY.toStringWtihTilda(), "1")
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread());

        Observable<ResponseBody> observableStations = networkApi.getAviationData(DataSourceType.Stations.toString(), RequestType.Retrieve.toString(), FormatType.XML.toString(), CountryType.GERMANY.toStringWtihTilda(), "1")
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread());

        Observable<AviationData> combinedAviationResponse = Observable.zip(observableMetar, observableStations, (metarResponseBody, stationResponseBody) -> {
            AviationData aviationDataResponse = new AviationData(); // current aviation data responsible object

            AviationResponseParser.parseAviationResponse(stationResponseBody.string(), aviationDataResponse);
            AviationResponseParser.parseAviationResponse(metarResponseBody.string(), aviationDataResponse);
            database.insertAviationResponse(aviationDataResponse);
            return aviationDataResponse;
        }).subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread());

        if (callback != null)
            combinedAviationResponse.subscribe(callback);
        else
            combinedAviationResponse.subscribe();
    }
}
