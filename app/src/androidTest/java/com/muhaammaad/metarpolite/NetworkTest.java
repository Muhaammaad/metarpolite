package com.muhaammaad.metarpolite;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.muhaammaad.metarpolite.di.module.NetworkModule;
import com.muhaammaad.metarpolite.model.type.CountryType;
import com.muhaammaad.metarpolite.model.type.DataSourceType;
import com.muhaammaad.metarpolite.model.type.FormatType;
import com.muhaammaad.metarpolite.model.type.RequestType;
import com.muhaammaad.metarpolite.network.NetworkApi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Instrumented test, which will execute on an Android device.
 * <p>
 * Test class showcasing some {@link NetworkApi} using Retrofit.
 */
@RunWith(AndroidJUnit4.class)
public class NetworkTest {
    private final CountDownLatch latch = new CountDownLatch(1);
    private NetworkApi apiManager;

    @Before
    public void beforeTest() {
        NetworkModule api = new NetworkModule();
        apiManager = api.provideNetworkApi();
    }

    @Test
    public void retrieveMetarsTest() throws InterruptedException {
        Assert.assertNotNull(apiManager);
        final ResponseBody[] response = {null};
        apiManager.getAviationData(DataSourceType.Metars.toString(), RequestType.Retrieve.toString(), FormatType.XML.toString(), CountryType.GERMANY.toStringWtihTilda(), "1")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).safeSubscribe(new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                response[0] = responseBody;
            }

            @Override
            public void onError(Throwable e) {
                latch.countDown();
            }

            @Override
            public void onComplete() {
                latch.countDown();
            }
        });
        latch.await();
        Assert.assertNotNull(response[0]);
    }


    @Test
    public void retrieveStationsTest() throws InterruptedException {
        Assert.assertNotNull(apiManager);
        final ResponseBody[] response = {null};
        apiManager.getAviationData(DataSourceType.Stations.toString(), RequestType.Retrieve.toString(), FormatType.XML.toString(), CountryType.GERMANY.toStringWtihTilda(), "1")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).safeSubscribe(new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                response[0] = responseBody;
            }

            @Override
            public void onError(Throwable e) {
                latch.countDown();
            }

            @Override
            public void onComplete() {
                latch.countDown();
            }
        });
        latch.await();
        Assert.assertNotNull(response[0]);
    }
}
