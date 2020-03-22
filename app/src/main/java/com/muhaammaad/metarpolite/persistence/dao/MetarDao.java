package com.muhaammaad.metarpolite.persistence.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.muhaammaad.metarpolite.persistence.entity.Metar;
import com.muhaammaad.metarpolite.persistence.entity.MetarAndStation;

import java.util.List;

@Dao
public interface MetarDao extends BaseDao<Metar> {
    @Transaction
    @Query("SELECT * FROM Metar ORDER BY  observationTime DESC")
    LiveData<List<MetarAndStation>> getMetarAndStation();

    @Query("SELECT * FROM Metar")
    List<Metar> getMetars();
}