package com.muhaammaad.metarpolite.network;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * The interface which provides methods to get result of webservices
 */
public interface NetworkApi {
    /**
     * Get the list of the Stations from the API
     */
    @GET("adds/dataserver_current/httpparam")
    Observable<ResponseBody> getAviationData(@Query("dataSource") String dataSource,
                                             @Query("requestType") String requestType,
                                             @Query("format") String format,
                                             @Query("stationString") String stationString,
                                             @Query("hoursBeforeNow") String hoursBeforeNow);
}

