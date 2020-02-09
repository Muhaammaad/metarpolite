package com.muhaammaad.metarpolite.persistence.repo;

import android.util.Log;
import com.muhaammaad.metarpolite.global.appContext;
import com.muhaammaad.metarpolite.persistence.db.AviationDatabase;
import com.muhaammaad.metarpolite.persistence.entity.Metar;
import com.muhaammaad.metarpolite.persistence.entity.MetarAndStation;
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

    public List<StationsAndMetars> getStationsAndMetarsList() {
        long t1 = System.currentTimeMillis();
        List<StationsAndMetars> stationsAndMetars = AviationDatabase.getInstance(appContext.context).stationDao().getStationsAndMetars();
        Log.e("getStationsAndMetarList", "diff " + (System.currentTimeMillis() - t1));
        return stationsAndMetars;
    }

    public List<MetarAndStation> getMetarAndStationList() {
        long t1 = System.currentTimeMillis();
        List<MetarAndStation> metarAndStation = AviationDatabase.getInstance(appContext.context).metarDao().getMetarAndStation();
        Log.e("getMetarAndStationList", "diff " + (System.currentTimeMillis() - t1));
        return metarAndStation;
    }

    public List<Station> getAllStations() {
        return AviationDatabase.getInstance(appContext.context).stationDao().getStations();
    }

    public void insertStation(Station station) {
        AviationDatabase.getInstance(appContext.context).stationDao().insert(station);
    }

    public void insertMetar(Metar metar) {
        AviationDatabase.getInstance(appContext.context).metarDao().insert(metar);
    }

    public Station getStationById(String stationId) {
        return AviationDatabase.getInstance(appContext.context).stationDao().getStation(stationId);
    }

    public void insertMetars(List<Metar> metars) {
        AviationDatabase.getInstance(appContext.context).metarDao().insert(metars);
    }

    public void insertStations(List<Station> stations) {
        AviationDatabase.getInstance(appContext.context).stationDao().insert(stations);

    }
}
