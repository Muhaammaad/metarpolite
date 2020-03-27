package com.muhaammaad.metarpolite.global.util;

import androidx.work.ListenableWorker;

import com.muhaammaad.metarpolite.di.factory.ChildWorkerFactory;

import java.util.Map;
import java.util.Objects;

import javax.inject.Provider;

public class CollectionUtils {
    /**
     *
     * @param map workers
     * @param key workers name (class name)
     * @return
     */
    public static Provider<ChildWorkerFactory> getWorkerFactoryProviderByKey(Map<Class<? extends ListenableWorker>, Provider<ChildWorkerFactory>> map, String key) {
        for (Map.Entry<Class<? extends ListenableWorker>, Provider<ChildWorkerFactory>> entry : map.entrySet()) {
            if (key.equals(entry.getKey().getName())) {
                return entry.getValue();
            }
        }
        return null;
    }
}
