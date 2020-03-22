package com.muhaammaad.metarpolite.persistence.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.muhaammaad.metarpolite.persistence.entity.QualityControlFlags;

@Dao
public interface QualityControlFlagsDao extends BaseDao<QualityControlFlags> {

    @Query("SELECT * FROM QualityControlFlags where metarId = :metarId")
    QualityControlFlags getMetarQualityFlags(String metarId);
}
