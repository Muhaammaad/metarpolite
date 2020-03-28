package com.muhaammaad.metarpolite;

import android.content.Context;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.work.Configuration;
import androidx.work.ListenableWorker;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.testing.SynchronousExecutor;
import androidx.work.testing.TestDriver;
import androidx.work.testing.TestListenableWorkerBuilder;
import androidx.work.testing.WorkManagerTestInitHelper;

import com.muhaammaad.metarpolite.di.component.AppComponent;
import com.muhaammaad.metarpolite.di.component.DaggerAppComponent;
import com.muhaammaad.metarpolite.di.factory.SimpleWorkerFactory;
import com.muhaammaad.metarpolite.di.module.ApplicationModule;
import com.muhaammaad.metarpolite.global.MyApplication;
import com.muhaammaad.metarpolite.manager.MetarFetcher;
import com.muhaammaad.metarpolite.ui.main.view.MainActivity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

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
        context = getApplicationContext();

        Configuration config = new Configuration.Builder()
                // Use a SynchronousExecutor to make it easier to write tests
                .setExecutor(new SynchronousExecutor())
                .build();

        // Initialize WorkManager for instrumentation tests.
        WorkManagerTestInitHelper.initializeTestWorkManager(
                context, config);
    }

    @Test
    public void testRefreshMainDataWork() throws ExecutionException, InterruptedException {
        // Get the ListenableWorker
        MetarFetcher worker = TestListenableWorkerBuilder.from(context, MetarFetcher.class).build();
        // Start the work synchronously
        ListenableWorker.Result result = worker.startWork().get();
        Assert.assertEquals(result, ListenableWorker.Result.success());
    }

    @Test
    public void testPeriodicWorkRequest() throws ExecutionException, InterruptedException {
        // Create request
        PeriodicWorkRequest request = MetarFetcher.setPeriodicWork(context);
        WorkManager workManager = WorkManager.getInstance(context);

        // Get the TestDriver
        TestDriver testDriver = WorkManagerTestInitHelper.getTestDriver(context);

        // Enqueue and wait for result. This also runs the Worker synchronously
        // because we are using a SynchronousExecutor.
        workManager.enqueue(request).getResult().get();
        // Tells the WorkManager test framework that initial delays are now met.
        assert testDriver != null;
        // Tells the WorkManager test framework that initial delays are now met.
        testDriver.setInitialDelayMet(request.getId());
        // Tells the testing framework the period delay is met
        testDriver.setPeriodDelayMet(request.getId());
        // Get WorkInfo
        WorkInfo workInfo = workManager.getWorkInfoById(request.getId()).get();
        // Assert
        Assert.assertEquals(workInfo.getState(), WorkInfo.State.ENQUEUED);
    }

    @Test
    public void testOneTimeRequest() throws ExecutionException, InterruptedException {
        // Create request
        OneTimeWorkRequest request = MetarFetcher.setOneTimeWork(context);
        WorkManager workManager = WorkManager.getInstance(context);

        // Get the TestDriver
        TestDriver testDriver = WorkManagerTestInitHelper.getTestDriver(context);

        // Enqueue and wait for result. This also runs the Worker synchronously
        // because we are using a SynchronousExecutor.
        workManager.enqueue(request).getResult().get();
        // Tells the WorkManager test framework that initial delays are now met.
        assert testDriver != null;
        // Tells the WorkManager test framework that initial delays are now met.
        testDriver.setInitialDelayMet(request.getId());
        // Tells the testing framework that Constraints are met
        testDriver.setAllConstraintsMet(request.getId());
        // Get WorkInfo
        WorkInfo workInfo = workManager.getWorkInfoById(request.getId()).get();
        workInfo.getOutputData();
        // Assert

        Assert.assertEquals(workInfo.getState(), WorkInfo.State.SUCCEEDED);
    }
}
