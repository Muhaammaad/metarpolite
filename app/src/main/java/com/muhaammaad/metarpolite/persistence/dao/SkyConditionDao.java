package com.muhaammaad.metarpolite.persistence.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.muhaammaad.metarpolite.persistence.entity.SkyCondition;

import java.util.List;

@Dao
public interface SkyConditionDao extends BaseDao<SkyCondition> {

    @Query("SELECT * FROM SkyCondition where metarId = :metarId")
    List<SkyCondition> getMetarSkyConditions(String metarId);
}
