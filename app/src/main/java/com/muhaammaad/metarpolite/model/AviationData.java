
package com.muhaammaad.metarpolite.model;

import com.muhaammaad.metarpolite.persistence.entity.Metar;
import com.muhaammaad.metarpolite.persistence.entity.Station;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Aviation Data Helper to hold all the metars and stations
 */
public class AviationData {
    public List<Metar> metars = new ArrayList<>();
    public HashMap<String, Station> stations = new HashMap<>();
}