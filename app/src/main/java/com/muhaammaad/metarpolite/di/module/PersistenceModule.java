package com.muhaammaad.metarpolite.di.module;

import android.content.Context;

import androidx.room.Room;

import com.muhaammaad.metarpolite.di.annotation.ApplicationContext;
import com.muhaammaad.metarpolite.di.annotation.DatabaseInfo;
import com.muhaammaad.metarpolite.persistence.dao.MetarDao;
import com.muhaammaad.metarpolite.persistence.dao.QualityControlFlagsDao;
import com.muhaammaad.metarpolite.persistence.dao.SkyConditionDao;
import com.muhaammaad.metarpolite.persistence.dao.StationDao;
import com.muhaammaad.metarpolite.persistence.db.AviationDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PersistenceModule {

    @DatabaseInfo
    private final String mDBName = "aviation.db";

    @Singleton
    @Provides
    AviationDatabase provideDb(@ApplicationContext Context context) {
        return Room.databaseBuilder(context,
                AviationDatabase.class, mDBName)
                .build();
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return mDBName;
    }

    @Singleton
    @Provides
    MetarDao provideMetarDao(AviationDatabase db) {
        return db.metarDao();
    }

    @Singleton
    @Provides
    StationDao provideStationDao(AviationDatabase db) {
        return db.stationDao();
    }

    @Singleton
    @Provides
    QualityControlFlagsDao provideQualityControlFlagsDao(AviationDatabase db) {
        return db.qualityControlFlagsDao();
    }

    @Singleton
    @Provides
    SkyConditionDao provideSkyConditionDao(AviationDatabase db) {
        return db.skyConditionDao();
    }
}