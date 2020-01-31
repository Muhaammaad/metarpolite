package com.muhaammaad.metarpolite.ui.main.viewmodel;

//region Imports

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.AndroidViewModel;
import com.muhaammaad.metarpolite.model.AviationResponse;
import com.muhaammaad.metarpolite.model.METAR;
import com.muhaammaad.metarpolite.network.NetworkModule;
import com.muhaammaad.metarpolite.ui.main.util.AviationResponseParser;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
//endregion

public class MainViewModel extends AndroidViewModel implements Observer<AviationResponse> {

    //region Globar Members
    private AviationResponse mAviationResponse = new AviationResponse(); // Overall aviation data responsible object
    public ObservableArrayList<METAR> mMetars = new ObservableArrayList<>(); // metars to be displayed data responsible list object
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
     * Gets the data of all stations and metars. Once fetched, both are zipped into {@link #mAviationResponse}.
     */
    private void getAviationData() {
        Observable<ResponseBody> observableMetar = NetworkModule.getInstance().provideNetworkApi().getMetarsData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<ResponseBody> observableStations = NetworkModule.getInstance().provideNetworkApi().getStationsData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<AviationResponse> combinedAviationResponse = Observable.zip(observableMetar, observableStations, new BiFunction<ResponseBody, ResponseBody, AviationResponse>() {
            @Override
            public AviationResponse apply(ResponseBody metarResponseBody, ResponseBody stationResponseBody) {
                AviationResponseParser.parseAviationResponseStream(stationResponseBody.byteStream(), mAviationResponse);
                AviationResponseParser.parseAviationResponseStream(metarResponseBody.byteStream(), mAviationResponse);
                return mAviationResponse;
            }
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
    public void onNext(AviationResponse aviationResponse) {
        mMetars.addAll(aviationResponse.response.data.metars);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
    //endregion
}

