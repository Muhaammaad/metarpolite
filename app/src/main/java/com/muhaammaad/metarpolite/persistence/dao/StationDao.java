package com.muhaammaad.metarpolite.persistence.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;
import com.muhaammaad.metarpolite.persistence.entity.Station;
import com.muhaammaad.metarpolite.persistence.entity.StationsAndMetars;

import java.util.List;

@Dao
public interface StationDao extends BaseDao<Station> {

    @Query("SELECT * FROM Station where stationId = :stationId")
    Station getStation(String stationId);

    @Transaction
    @Query("SELECT * FROM Station")
    List<StationsAndMetars> getStationsAndMetars();

    @Query("SELECT * FROM Station ORDER BY stationId DESC")
    List<Station> getStations();
}
