package com.muhaammaad.metarpolite.manager;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.muhaammaad.metarpolite.persistence.AviationData;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

/**
 * Worker to fetch AviationData in background
 */
public class MetarFetcher extends Worker {

    public MetarFetcher(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    /**
     * This method is called on a
     * background thread - required to <b>synchronously</b> do work and return the Result
     */
    @NotNull
    @Override
    public Result doWork() {
        AviationData.getAviationDataFromNetwork(null);
        Log.i(MetarFetcher.class.getName(), "Got Aviation Data");
        return Result.success();
    }

    /**
     * Schedules Unique Worker to run periodically Every Hour
     *
     * @param context A {@link Context} for on-demand initialization.
     */
    public static void setWork(Context context) {

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
    }
}