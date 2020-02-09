package com.muhaammaad.metarpolite.persistence.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;

public class StationsAndMetars {
    @Embedded
    public Station stations;
    @Relation(
            parentColumn = "stationId",
            entityColumn = "stationId"
    )
    public List<Metar> metars = new ArrayList<>();
}
