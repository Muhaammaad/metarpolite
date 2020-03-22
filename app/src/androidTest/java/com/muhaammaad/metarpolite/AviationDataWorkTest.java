package com.muhaammaad.metarpolite;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.work.ListenableWorker;
import androidx.work.testing.TestListenableWorkerBuilder;

import com.muhaammaad.metarpolite.manager.MetarFetcher;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

/**
 * Instrumented test, which will execute on an Android device.
 * <p>
 * MetarFetcher Worker Test CLass
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AviationDataWorkTest {
    private Context context;

    @Before
    public void setup() {
        context = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void testRefreshMainDataWork() throws ExecutionException, InterruptedException {
        // Get the ListenableWorker
        MetarFetcher worker = TestListenableWorkerBuilder.from(context, MetarFetcher.class).build();
        // Start the work synchronously
        ListenableWorker.Result result = worker.startWork().get();
        Assert.assertEquals(result, ListenableWorker.Result.success());
    }
}
