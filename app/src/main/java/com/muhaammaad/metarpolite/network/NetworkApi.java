package com.muhaammaad.metarpolite.network;


import com.muhaammaad.metarpolite.global.constant.Constants;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

/**
 * The interface which provides methods to get result of webservices
 */
public interface NetworkApi {
    /**
     * Get the list of the Metars from the API
     */
    @GET(Constants.Network.METAR_URL)
    Observable<ResponseBody> getMetarsData();

    /**
     * Get the list of the Stations from the API
     */
    @GET(Constants.Network.STATIONS_URL)
    Observable<ResponseBody> getStationsData();
}

