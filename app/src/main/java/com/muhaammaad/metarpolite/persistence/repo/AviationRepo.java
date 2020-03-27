package com.muhaammaad.metarpolite.persistence.repo;

import androidx.lifecycle.LiveData;

import com.muhaammaad.metarpolite.model.AviationData;
import com.muhaammaad.metarpolite.persistence.db.AviationDatabase;
import com.muhaammaad.metarpolite.persistence.entity.Metar;
import com.muhaammaad.metarpolite.persistence.entity.MetarAndStation;
import com.muhaammaad.metarpolite.persistence.entity.QualityControlFlags;
import com.muhaammaad.metarpolite.persistence.entity.SkyCondition;
import com.muhaammaad.metarpolite.persistence.entity.Station;
import com.muhaammaad.metarpolite.persistence.entity.StationsAndMetars;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AviationRepo {

    private AviationDatabase database;

    @Inject
    AviationRepo(AviationDatabase aviationDatabase) {
        database = aviationDatabase;
    }

    // region Getters
    public List<StationsAndMetars> getStationsAndMetarsList() {
        return database.stationDao().getStationsAndMetars();
    }

    public LiveData<List<MetarAndStation>> getMetarAndStationList() {
        return database.metarDao().getMetarAndStation();
    }

    public LiveData<List<Station>> getAllStations() {
        return database.stationDao().getStations();
    }

    public Station getStationById(String stationId) {
        return database.stationDao().getStation(stationId);
    }

    //endregion Getters
    //region Insertions
    public void insertStation(Station station) {
        database.stationDao().insert(station);
    }

    public void insertMetar(Metar metar) {
        database.metarDao().insert(metar);
    }

    public void insertMetarsWithSkyConditionsAndFlags(List<Metar> metars) {
        long millis = System.currentTimeMillis();
        List<SkyCondition> skyConditions = new ArrayList<>();
        List<QualityControlFlags> qualityControlFlags = new ArrayList<>();
        for (Metar metar : metars) {
            metar.id = millis;
            millis = millis + 1;
            if (metar.qualityControlFlags != null) {
                qualityControlFlags.add(manipulateQualityFlags(metar.id, metar.qualityControlFlags));
            }
            if (metar.skyCondition != null && !metar.skyCondition.isEmpty()) {
                skyConditions.addAll(manipulateSkyCondition(metar.id, metar.skyCondition));
            }
        }
        insertMetars(metars);
        insertSkyConditions(skyConditions);
        insertQualityFlags(qualityControlFlags);
    }

    QualityControlFlags manipulateQualityFlags(long metarId, QualityControlFlags qualityControlFlags) {
        if (qualityControlFlags != null) {
            qualityControlFlags.metarId = metarId;
        }
        return qualityControlFlags;
    }

    List<SkyCondition> manipulateSkyCondition(long metarId, List<SkyCondition> skyCondition) {
        for (SkyCondition condition : skyCondition) {
            condition.metarId = metarId;
        }
        return skyCondition;
    }

    private void insertQualityFlags(long metarId, QualityControlFlags qualityControlFlags) {
        if (qualityControlFlags != null) {
            qualityControlFlags.metarId = metarId;
            database.qualityControlFlagsDao().insert(qualityControlFlags);
        }
    }

    private void insertSkyCondition(long metarId, List<SkyCondition> skyCondition) {
        for (SkyCondition condition : skyCondition) {
            condition.metarId = metarId;
        }
        database.skyConditionDao().insert(skyCondition);
    }

    public void insertStations(List<Station> stations) {
        database.stationDao().insert(stations);
    }

    public void insertMetars(List<Metar> metars) {
        database.metarDao().insert(metars);
    }

    public void insertSkyConditions(List<SkyCondition> conditions) {
        database.skyConditionDao().insert(conditions);
    }

    public void insertQualityFlags(List<QualityControlFlags> flags) {
        database.qualityControlFlagsDao().insert(flags);
    }

    public void insertAviationResponse(AviationData aviationDataResponse) {
        database.runInTransaction(() -> {
            insertStations(new ArrayList<>(aviationDataResponse.stations.values()));
            insertMetarsWithSkyConditionsAndFlags(aviationDataResponse.metars);
        });
    }
    //endregion Insertions
}
