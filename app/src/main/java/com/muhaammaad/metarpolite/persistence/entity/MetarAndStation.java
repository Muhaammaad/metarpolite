package com.muhaammaad.metarpolite.persistence.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class MetarAndStation {
    @Relation(
            parentColumn = "stationId",
            entityColumn = "stationId"
    )
    public Station station;

    @Relation(
            parentColumn = "id",
            entityColumn = "metarId"
    )
    public List<SkyCondition> skyCondition;

     @Relation(
            parentColumn = "id",
            entityColumn = "metarId"
    )
    public QualityControlFlags flags;

    @Embedded
    public Metar metar;

    public Metar getMetarWithStation(){
        metar.station = station;
        metar.skyCondition = skyCondition;
        metar.qualityControlFlags = flags;
        return metar;
    }
}
