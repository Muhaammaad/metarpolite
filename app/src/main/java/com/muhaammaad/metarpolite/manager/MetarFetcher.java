package com.muhaammaad.metarpolite.manager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ListenableWorker;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.muhaammaad.metarpolite.di.factory.ChildWorkerFactory;
import com.muhaammaad.metarpolite.global.util.AviationDataUtil;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * Worker to fetch AviationData in background
 */
public class MetarFetcher extends Worker {

    private AviationDataUtil aviationDataUtil;

    @VisibleForTesting
    public MetarFetcher(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Inject
    public MetarFetcher(
            @NonNull Context context,
            @NonNull WorkerParameters params, @NonNull AviationDataUtil aviationDataUtil) {
        super(context, params);
        this.aviationDataUtil = aviationDataUtil;
    }

    /**
     * This method is called on a
     * background thread - required to <b>synchronously</b> do work and return the Result
     */
    @NotNull
    @Override
    public Result doWork() {
        if (aviationDataUtil != null)
            aviationDataUtil.getAviationDataFromNetwork(null);
        return Result.success();
    }

    /**
     * Schedules Unique Worker to run periodically Every Hour
     *
     * @param context A {@link Context} for on-demand initialization.
     */
    public static PeriodicWorkRequest setPeriodicWork(Context context) {

        //Constraints for the worker
        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(false)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        // Repeat interval is 1 hour and in last 15 minutes of each hour try to run the work.
        // flexInterval is always positioned at the end of the repetition period
        PeriodicWorkRequest request =
                new PeriodicWorkRequest.Builder(MetarFetcher.class,
                        1, TimeUnit.HOURS,
                        15, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .build();

        // KEEP the existing sequence and ignore your new request.
        WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(MetarFetcher.class.getName(),
                        ExistingPeriodicWorkPolicy.KEEP, request);
        return request;
    }

    /**
     * Schedules a Worker to run Once
     *
     * @param context A {@link Context} for on-demand initialization.
     */
    public static OneTimeWorkRequest setOneTimeWork(Context context) {
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MetarFetcher.class)
                .build();
        WorkManager.getInstance(context).enqueue(request);
        return request;
    }

    public static class Factory implements ChildWorkerFactory {

        private final Provider<AviationDataUtil> modelProvider;

        @Inject
        public Factory(Provider<AviationDataUtil> modelProvider) {
            this.modelProvider = modelProvider;
        }

        @Override
        public ListenableWorker create(Context context, WorkerParameters workerParameters) {
            return new MetarFetcher(context,
                    workerParameters,
                    modelProvider.get());
        }
    }
}