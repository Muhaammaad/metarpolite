package com.muhaammaad.metarpolite.persistence.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.muhaammaad.metarpolite.persistence.dao.MetarDao;
import com.muhaammaad.metarpolite.persistence.dao.QualityControlFlagsDao;
import com.muhaammaad.metarpolite.persistence.dao.SkyConditionDao;
import com.muhaammaad.metarpolite.persistence.dao.StationDao;
import com.muhaammaad.metarpolite.persistence.entity.Metar;
import com.muhaammaad.metarpolite.persistence.entity.QualityControlFlags;
import com.muhaammaad.metarpolite.persistence.entity.SkyCondition;
import com.muhaammaad.metarpolite.persistence.entity.Station;

/**
 * The Room database that contains the Users table
 */
@Database(entities = {Metar.class, Station.class, SkyCondition.class, QualityControlFlags.class}, version = 1, exportSchema = false)
public abstract class AviationDatabase extends RoomDatabase {

    private static volatile AviationDatabase INSTANCE;

    public abstract MetarDao metarDao();

    public abstract StationDao stationDao();

    public abstract SkyConditionDao skyConditionDao();

    public abstract QualityControlFlagsDao qualityControlFlagsDao();

    public static AviationDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AviationDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AviationDatabase.class, "aviation.db")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}