package com.muhaammaad.metarpolite.persistence.repo;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.muhaammaad.metarpolite.global.MyApplication;
import com.muhaammaad.metarpolite.persistence.db.AviationDatabase;
import com.muhaammaad.metarpolite.persistence.entity.Metar;
import com.muhaammaad.metarpolite.persistence.entity.MetarAndStation;
import com.muhaammaad.metarpolite.persistence.entity.QualityControlFlags;
import com.muhaammaad.metarpolite.persistence.entity.SkyCondition;
import com.muhaammaad.metarpolite.persistence.entity.Station;
import com.muhaammaad.metarpolite.persistence.entity.StationsAndMetars;

import java.util.List;

public class AviationRepo {
    private static final AviationRepo ourInstance = new AviationRepo();

    public static AviationRepo getInstance() {
        return ourInstance;
    }

    private AviationRepo() {
    }

    // region Getters
    public List<StationsAndMetars> getStationsAndMetarsList() {
        long t1 = System.currentTimeMillis();
        List<StationsAndMetars> stationsAndMetars = AviationDatabase.getInstance(MyApplication.getInstance()).stationDao().getStationsAndMetars();
        Log.e("getStationsAndMetarList", "diff " + (System.currentTimeMillis() - t1));
        return stationsAndMetars;
    }

    public LiveData<List<MetarAndStation>> getMetarAndStationList() {
        long t1 = System.currentTimeMillis();
        LiveData<List<MetarAndStation>> metarAndStation = AviationDatabase.getInstance(MyApplication.getInstance()).metarDao().getMetarAndStation();
        Log.e("getMetarAndStationList", "diff " + (System.currentTimeMillis() - t1));
        return metarAndStation;
    }

    public LiveData<List<Station>> getAllStations() {
        return AviationDatabase.getInstance(MyApplication.getInstance()).stationDao().getStations();
    }

    public Station getStationById(String stationId) {
        return AviationDatabase.getInstance(MyApplication.getInstance()).stationDao().getStation(stationId);
    }

    public void insertStation(Station station) {
        AviationDatabase.getInstance(MyApplication.getInstance()).stationDao().insert(station);
    }

    //endregion Getters
    //region Insertions
    public void insertMetar(Metar metar) {
        AviationDatabase.getInstance(MyApplication.getInstance()).metarDao().insert(metar);
    }

    public void insertMetars(List<Metar> metars) {
        for (Metar metar : metars) {
            long metarId = AviationDatabase.getInstance(MyApplication.getInstance()).metarDao().insertId(metar);
            insertSkyCondition(metarId, metar.skyCondition);
            insertQualityFlags(metarId, metar.qualityControlFlags);
        }
    }

    private void insertQualityFlags(long metarId, QualityControlFlags qualityControlFlags) {
        if (qualityControlFlags != null) {
            qualityControlFlags.metarId = metarId;
            AviationDatabase.getInstance(MyApplication.getInstance()).qualityControlFlagsDao().insert(qualityControlFlags);
        }
    }

    private void insertSkyCondition(long metarId, List<SkyCondition> skyCondition) {
        for (SkyCondition condition : skyCondition) {
            condition.metarId = metarId;
        }
        AviationDatabase.getInstance(MyApplication.getInstance()).skyConditionDao().insert(skyCondition);
    }

    public void insertStations(List<Station> stations) {
        AviationDatabase.getInstance(MyApplication.getInstance()).stationDao().insert(stations);
    }
    //endregion Insertions
}
