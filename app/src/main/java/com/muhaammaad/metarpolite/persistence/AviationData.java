
package com.muhaammaad.metarpolite.persistence;

import androidx.room.Embedded;
import androidx.room.Relation;
import com.muhaammaad.metarpolite.persistence.entity.Metar;
import com.muhaammaad.metarpolite.persistence.entity.Station;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AviationData {
    public List<Metar> metars = new ArrayList<>();
    public HashMap<String, Station> stations = new HashMap<>();
}
