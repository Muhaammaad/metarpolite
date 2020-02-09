package com.muhaammaad.metarpolite.persistence.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

public class MetarAndStation {
    @Relation(
            parentColumn = "stationId",
            entityColumn = "stationId"
    )
    public Station station;
    @Embedded
    public Metar metar;

    public Metar getMetarWithStation(){
        metar.station = station;
        return metar;
    }
}
