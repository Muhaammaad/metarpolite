package com.muhaammaad.metarpolite.network;

//region Imports

import com.muhaammaad.metarpolite.BuildConfig;
import com.muhaammaad.metarpolite.global.constant.Constants;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
//endregion

public class NetworkModule {
    private static final NetworkModule ourInstance = new NetworkModule();

    public static NetworkModule getInstance() {
        return ourInstance;
    }

    private NetworkModule() {
    }

    /**
     * Provides the aviation service implementation.
     *
     * @return the service implementation.
     */
    public NetworkApi provideNetworkApi() {
        return provideRetrofitInterface().create(NetworkApi.class);
    }

    private Retrofit retrofit = null;

    /**
     * Provides the Retrofit object.
     *
     * @return the Retrofit object
     */

    private Retrofit provideRetrofitInterface() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BASIC : HttpLoggingInterceptor.Level.NONE);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectionSpecs(
                        Arrays.asList(
                                ConnectionSpec.MODERN_TLS,
                                ConnectionSpec.COMPATIBLE_TLS
                        )
                )
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .cache(null)
                .build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.Network.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
